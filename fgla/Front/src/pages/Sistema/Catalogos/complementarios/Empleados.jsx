import React, { useState, useEffect, useRef, useMemo } from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';
import { Accordion, Loader } from '../../../../components/index.js';
import showMessage from '../../../../components/Messages.jsx';
import { useToast } from "../../../../hooks/useToast.js";
import { ADMIN_CATALOGOS as MESSAGES } from "../../../../constants/messages.js";
import IconButton from '../../../../components/buttons/IconButton.jsx';
import moment from "moment";
import { downloadExcelBlob } from '../../../../functions/utils/index.js';
import { errorValidations } from '../utils.js';
import { getEmpleados, putEmpleados, downloadEmpleados, getTipoEmpleado } from '../Catalogos.jsx';
import { useErrorMessages } from '../../../../hooks/useErrorMessages.js';
import { logError } from '../../../../components/utils/logError.js';
import { TablaEditable } from '../../../../components/table/TablaEditable.jsx';
import SwitchButton from "../../../../components/buttons/SwitchButton.jsx";
import BasicModal from '../../../../modals/BasicModal.jsx';

const FORMAT_DATE = "DD/MM/YYYY";

const Empleados = ({ administracionesList }) => {
  const { errorToast } = useToast();
  const [loading, setLoading] = useState(true);
  const [dataTable, setDataTable] = useState([]);
  const [showTable, setShowTable] = useState(false);
  const [tiposEmpleado, setTiposEmpleado] = useState([]);
  const { getMessageExists } = useErrorMessages(MESSAGES);
  const tableRef = useRef();
  const [selectedRow, setSelectedRow] = useState(null);
  const memoizedData = useRef(new Map());
  const [showGlobalCancel, setShowGlobalCancel] = useState(false);
  const originalData = useRef([]); 
  const [showUndo, setShowUndo] = useState(false);
  const [administraciones, setAdministraciones] = useState([]);
  const [idAdministracionSeleccionada, setIdAdministracionSeleccionada] = useState('');

  // cargar tipos de empleado una sola vez
useEffect(() => {
  setLoading(true);
  getTipoEmpleado()
    .then((response) => {
      setTiposEmpleado(response.data);
      setLoading(false);
    })
    .catch((error) => {
      logError("Error cargando tipos de empleado", error);
      setLoading(false);
    });
}, []);

// escuchar cambios de administracionesList
useEffect(() => {
  if (administracionesList) {
    setAdministraciones(administracionesList);
  }
}, [administracionesList]);

  const dateFormatNoTime = (date) => {
    return date ? moment(date).format(FORMAT_DATE) : "";
  };

  const toLocalDateTime = (dateString) => {
    if (!dateString) return null;
    return moment(dateString, "YYYY-MM-DD").format("YYYY-MM-DD[T]HH:mm:ss");
  };

  // Generador de UUID compatible sin librerías externas
const generateUUID = () => {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    const r = crypto.getRandomValues(new Uint8Array(1))[0] & 15;
    const v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
};


  const handleAddRow = () => {
    const newRow = {
      UUID: generateUUID(),
      id: dataTable.length + 1,
      primaryKey: null,
      nombre: "",
      correo: "",
      telefono: "",
      idTipoEmpleado: "",
      puesto: "",
      estatus: true,
      fechaInicioVigencia: "", 
      fechaFinVigencia: "",
      fechaCreacion: null,
      fechaModificacion: null,
      isNewRow: true,
    };
    setDataTable([...dataTable, newRow]);
    setShowTable(true);
  };

  const handleBuscar = () => {
    if (!idAdministracionSeleccionada) {
      errorToast(MESSAGES.MSG004);
      return;
    }
    setLoading(true);
    getEmpleados(idAdministracionSeleccionada)
      .then((response) => {
        const dataTableTemp = response.data.map((item, index) => ({
          ...item,
          UUID: generateUUID(),
          id: index + 1,
          puesto: item.catTipoEmpleado?.nombre || "",
          correo: item.correo || "",
          fechaInicioVigencia: item.fechaInicioVigencia
            ? moment(item.fechaInicioVigencia).format("YYYY-MM-DD")
            : "",
          fechaFinVigencia: item.fechaFinVigencia
            ? moment(item.fechaFinVigencia).format("YYYY-MM-DD")
            : "",
          fechaCreacion: dateFormatNoTime(item.fechaCreacion),
          fechaModificacion: dateFormatNoTime(item.fechaModificacion),
          fechaCreacionRaw: item.fechaCreacion, // Guardar original
          fechaModificacionRaw: item.fechaModificacion, // Guardar original
          isNewRow: false,
        }));
        setDataTable(dataTableTemp);
        originalData.current = JSON.parse(JSON.stringify(dataTableTemp));
        setShowTable(true);
        setLoading(false);
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        errorValidations(getMessageExists, error);
      });
  };

  const downloadExcel = () => {
    setLoading(true);
    downloadEmpleados(idAdministracionSeleccionada)
      .then((response) => {
        downloadExcelBlob(response.data, "Empleados-Administracion");
        setLoading(false);
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        errorValidations(getMessageExists, error);
      });
  };

  const handleSaveAll = () => {
    setLoading(true);
    const toSave = dataTable.filter((row) => row.isNewRow || row._modified);

    if (toSave.length === 0) {
      showMessage("No hay cambios para guardar.");
      setLoading(false);
      return;
    }

    const empleadosDto = toSave.map((empleado) => ({
      idEmpleadoAdministracion: empleado.idEmpleadoAdministracion || null,
      nombre: empleado.nombre,
      correo: empleado.correo,
      telefono: empleado.telefono,
      idTipoEmpleado: empleado.idTipoEmpleado,
      modificado: true,
      fechaInicioVigencia: toLocalDateTime(empleado.fechaInicioVigencia),
      fechaFinVigencia: toLocalDateTime(empleado.fechaFinVigencia),
      fechaCreacion: empleado.idEmpleadoAdministracion
        ? originalData.current.find((orig) => orig.UUID === empleado.UUID)?.fechaCreacionRaw
        : null,
      fechaModificacion: empleado.idEmpleadoAdministracion
        ? originalData.current.find((orig) => orig.UUID === empleado.UUID)?.fechaModificacionRaw
        : null,
      estatus: empleado.estatus,
    }));

    putEmpleados(idAdministracionSeleccionada, empleadosDto)
      .then(() => {
        showMessage(MESSAGES.MSG001);
        handleBuscar();
        setLoading(false);
      })
      .catch((error) => {
        setLoading(false);
        logError("error => ", error);
        errorValidations(getMessageExists, error);
      });
  };

  const meta = {
    updateData: (rowIndex, columnId, value) => {
      setDataTable((old) =>
        old.map((row, index) =>
          index === rowIndex
            ? { ...row, [columnId]: value, _modified: true }
            : row
        )
      );
    },
  };

  const columns = useMemo(
    () => [
      { accessorKey: "id", header: "Id", cell: (props) => <>{props.row.index + 1}</> },
      {
        accessorKey: "nombre",
        header: "Nombre",
        cell: (props) =>
          props.row.getIsSelected() || props.row.original.isNewRow ? (
            <input
              type="text"
              className="form-control form-control-sm"
              value={props.getValue() || ""}
              onChange={(e) =>
                meta.updateData(props.row.index, props.column.id, e.target.value)
              }
            />
          ) : (
            props.getValue()
          ),
      },
      {
        accessorKey: "idTipoEmpleado",
        header: "Puesto",
        cell: (props) =>
          props.row.getIsSelected() || props.row.original.isNewRow ? (
            <select
              className="form-select form-select-sm"
              value={props.getValue() || ""}
              onChange={(e) =>
                meta.updateData(props.row.index, props.column.id, e.target.value)
              }
            >
              <option value="">Seleccione</option>
              {tiposEmpleado.map((t) => (
                <option key={t.primaryKey} value={t.primaryKey}>
                  {t.nombre}
                </option>
              ))}
            </select>
          ) : (
            props.row.original.puesto
          ),
      },
      {
        accessorKey: "correo",
        header: "Correo electrónico",
        cell: (props) =>
          props.row.getIsSelected() || props.row.original.isNewRow ? (
            <input
              type="email"
              className="form-control form-control-sm"
              value={props.getValue() || ""}
              onChange={(e) =>
                meta.updateData(props.row.index, props.column.id, e.target.value)
              }
            />
          ) : (
            props.getValue()
          ),
      },
      {
        accessorKey: "telefono",
        header: "Teléfono",
        cell: (props) =>
          props.row.getIsSelected() || props.row.original.isNewRow ? (
            <input
              type="text"
              className="form-control form-control-sm"
              value={props.getValue() || ""}
              onChange={(e) =>
                meta.updateData(props.row.index, props.column.id, e.target.value)
              }
            />
          ) : (
            props.getValue()
          ),
      },
      {
        accessorKey: "fechaInicioVigencia",
        header: "Inicio Vigencia",
        cell: (props) =>
          props.row.getIsSelected() || props.row.original.isNewRow ? (
            <input
              type="date"
              className="form-control form-control-sm"
              value={props.getValue() || ""}
              onChange={(e) =>
                meta.updateData(props.row.index, props.column.id, e.target.value)
              }
            />
          ) : (
            props.getValue() && moment(props.getValue()).format(FORMAT_DATE)
          ),
      },
      {
        accessorKey: "fechaFinVigencia",
        header: "Fin Vigencia",
        cell: (props) =>
          props.row.getIsSelected() || props.row.original.isNewRow ? (
            <input
              type="date"
              className="form-control form-control-sm"
              value={props.getValue() || ""}
              onChange={(e) =>
                meta.updateData(props.row.index, props.column.id, e.target.value)
              }
            />
          ) : (
            props.getValue() && moment(props.getValue()).format(FORMAT_DATE)
          ),
      },
      { accessorKey: "fechaCreacion", header: "Fecha de creación" },
      { accessorKey: "fechaModificacion", header: "Última modificación" },
      {
        accessorKey: "estatus",
        header: "Estatus",
        cell: (props) => (
          <SwitchButton
            value={!!props.getValue()}
            disabled={!props.row.getIsSelected() && !props.row.original.isNewRow}
            onChange={() =>
              meta.updateData(props.row.index, props.column.id, !props.getValue())
            }
          />
        ),
      },
      {
        accessorKey: "acciones",
        header: "Acciones",
        cell: (props) =>
          props.row.getIsSelected() || props.row.original.isNewRow ? (
            <IconButton
              type={"undo"}
              iconSize="lg"
              tooltip={"Cancelar"}
              onClick={() => {
                setSelectedRow({
                  ...props.row,
                  UUID: props.row.original.UUID,
                });
                setShowUndo(true);
              }}
            />
          ) : (
            <IconButton
              type={"edit"}
              iconSize="lg"
              tooltip={"Editar"}
              onClick={() => {
                if (!memoizedData.current.has(props.row.original.UUID)) {
                  memoizedData.current.set(props.row.original.UUID, { ...props.row.original });
                }
                props.row.toggleSelected(true, { selectChildren: false });
              }}
            />
          ),
      },
    ],
    [tiposEmpleado]
  );

  return (
    <Container className="mt-4 px-0">
      {loading && <Loader />}
      <Accordion title="Empleados">
        <Row>
           <Col md={4}>
            <label>Administración*:</label>
            <select
              className="form-select"
              value={idAdministracionSeleccionada}
              onChange={(e) => setIdAdministracionSeleccionada(e.target.value)}
            >
              <option value="">Seleccione una opción</option>
              {administraciones.map((admin) => (
                <option key={admin.primaryKey} value={admin.primaryKey}>
                  {admin.administracion}
                </option>
              ))}
            </select>
          </Col>
          <Col md={4}>
            <div style={{ paddingTop: "25px" }}>
              <Button
                variant="gray"
                className="btn-sm ms-2 waves-effect waves-light"
                onClick={handleBuscar}
              >
                Buscar
              </Button>
            </div>
          </Col>
        </Row>
        {showTable && (
          <>
            <Row>
              <Col md={12} className="text-end mb-2">
                <IconButton type="add" onClick={handleAddRow} tooltip="Nuevo" />
                <IconButton
                  type="excel"
                  disabled={dataTable.length === 0}
                  onClick={downloadExcel}
                  tooltip="Exportar a Excel"
                />
              </Col>
            </Row>
            <TablaEditable
              columns={columns}
              dataTable={dataTable}
              hasPagination
              ref={tableRef}
              onUpdate={setDataTable}
              rowId={"UUID"}
              meta={meta}
            />

            <BasicModal
              show={showUndo}
              onHide={() => setShowUndo(false)}
              title="Mensaje"
              approveText="Sí"
              denyText="No"
              handleDeny={() => setShowUndo(false)}
              handleApprove={() => {
                if (!selectedRow) return;
                if (selectedRow.original?.isNewRow) {
                  tableRef.current.table.options.meta.removeRowById(selectedRow.original.UUID);
                  setSelectedRow(null);
                  setShowUndo(false);
                  return;
                }
                const memoizedRow = memoizedData.current.get(selectedRow.UUID);
                if (memoizedRow) {
                  tableRef.current.table.options.meta.updateRowById(selectedRow.UUID, memoizedRow);
                  selectedRow.toggleSelected(false);
                  memoizedData.current.delete(selectedRow.UUID);
                }
                setSelectedRow(null);
                setShowUndo(false);
              }}
            >
              Se perderá toda la información no guardada. ¿Está seguro de que desea continuar?
            </BasicModal>

            <Row className="mt-3">
              <Col md={12} className="text-end">
                <Button
                  variant="red"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={() => setShowGlobalCancel(true)}
                >
                  Cancelar
                </Button>
                <Button
                  variant="green"
                  className="btn-sm ms-2 waves-effect waves-light"
                  onClick={handleSaveAll}
                >
                  Guardar Cambios
                </Button>
              </Col>
            </Row>

            <BasicModal
              show={showGlobalCancel}
              onHide={() => setShowGlobalCancel(false)}
              title="Mensaje"
              approveText="Sí"
              denyText="No"
              handleDeny={() => setShowGlobalCancel(false)}
              handleApprove={() => {
                setDataTable([...originalData.current]);
                tableRef.current.table.getRowModel().rows.forEach((row) => {
                  if (row.getIsSelected()) {
                    row.toggleSelected(false);
                  }
                });
                memoizedData.current.clear();
                setShowGlobalCancel(false);
              }}
            >
              Se perderá toda la información no guardada. ¿Está seguro de que desea continuar?
            </BasicModal>
          </>
        )}
      </Accordion>
    </Container>
  );
};

export default Empleados;
