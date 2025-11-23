import React, { useState, useEffect, useCallback, useMemo } from "react";
import { Button, Col, Row } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../../components/table/TablaEditable";
import {
  useGetCatPeriodicidadServicioQuery,
  useGetInformeDocumentalServicioQuery,
  useLazyGetReporteDocumentalServicioQuery,
  usePostEliminarInformesServiciosMutation,
  usePostGuardarInformesServiciosMutation,
  usePutActualizarInformesServiciosMutation,
} from "./store";
import { useParams, useSearchParams } from "react-router-dom";
import Loader from "../../../../../components/Loader";
import { nanoid } from "nanoid";
import { DownloadFileBase64 } from "../../../../../functions/utils/base64.utils";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import BasicModal from "../../../../../modals/BasicModal";
import _ from "lodash";
import TextField from "../../../../../components/formInputs/TextField";
import Select from "../../../../../components/formInputs/Select";
import { useToast } from "../../../../../hooks/useToast";
import Authorization from "../../../../../components/Authorization";

export function InformeDocumentalServicio({ isDetalle }) {
  const [idContrato, setIdContrato] = useState(null);
  const { idContrato: contratoParam } = useParams();
  let [searchParams, setSearchParams] = useSearchParams();

  useEffect(() => {
    const q = searchParams.get("q");
    setIdContrato(contratoParam || q);
  }, [contratoParam, searchParams]);

  const permisosIniciales = {
    leer: true,
    edicion: true,
  };
  const [permisos, setPermisos] = useState(permisosIniciales);
  const [editar, setEditar] = useState(false);

  useEffect(() => {
    let { leer, edicion } = permisos;

    setEditar(edicion);
  }, [permisos]);

  const { errorToast } = useToast();
  const [showMessage, setShowMessage] = useState(false);
  const [message, setMessage] = useState("");
  const handleCloseMessage = () => {
    setMessage("");
    setShowMessage(false);
  };
  const handleShowMessage = (message) => {
    setMessage(message);
    setShowMessage(true);
  };

  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [confirmModalMessage, setConfirmModalMesage] = useState("");
  const _confirmData = {
    approve: () => {},
    deny: () => {},
  };
  const [confirmData, setConfirmData] = useState(_confirmData);
  const handleShowConfirmModal = (
    title,
    approve = _confirmData.approve,
    deny = _confirmData.deny
  ) => {
    setConfirmModalMesage(title);
    setConfirmData({ approve, deny });
    setShowConfirmModal(true);
  };
  const handleApprove = () => {
    if (confirmData?.approve) {
      confirmData.approve();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };

  const handleDenny = () => {
    if (confirmData?.deny) {
      confirmData.deny();
    }
    setShowConfirmModal(false);
    setConfirmData(_confirmData);
  };
  const [putActualizar, { isLoading: isLoadingActualizar }] =
    usePutActualizarInformesServiciosMutation();
  const [postGuardar, { isLoading: isLoadingGuardar }] =
    usePostGuardarInformesServiciosMutation();
  const [postEliminar, { isLoading: isLoadingEliminar }] =
    usePostEliminarInformesServiciosMutation();
  const { data: catalogoPeriodicidad, isLoading: isLoadingCatalogo } =
    useGetCatPeriodicidadServicioQuery();

  const {
    data: informesServicio,
    isLoading: isLoadingInformesServicio,
    refetch: getInformesServicio,
  } = useGetInformeDocumentalServicioQuery(idContrato, { skip: !idContrato });

  const [getReporte, { isLoading: isLoadingReporte }] =
    useLazyGetReporteDocumentalServicioQuery();

  const [dataTable, setDataTable] = useState([]);
  const [deleteItems, setDeleteItems] = useState([]);
  const [hasError, sethasError] = useState(false);

  const SelectCell = ({ getValue, catalogo, table, row, column }) => {
    let value = getValue();
    let { idPeriodicidad } = row.original;
    const { updateData } = table.options.meta;

    const handleChangue = (event) => {
      let { value } = event.target;
      let nombre = findValue(catalogo, value);
      updateData(row.index, column.id, nombre);
      updateData(row.index, "idPeriodicidad", value);
    };

    return (
      <Select
        name={`${column.id}-${row.id}`}
        value={idPeriodicidad}
        options={catalogo}
        keyValue="primaryKey"
        keyTextValue="nombre"
        onChange={handleChangue}
        className={hasError ? (!value ? "is-invalid" : "is-valid") : ""}
      />
    );
  };

  const validateCellSelect = (props) => {
    let { original } = props.row;

    return (
      <>
        {original.edicion ? (
          <SelectCell {...props} />
        ) : (
          <LabelCell {...props} />
        )}
      </>
    );
  };
  const LabelCell = ({ getValue, ...props }) => {
    let value = getValue();
    return <>{value}</>;
  };
  const InputTextCell = ({
    getValue,
    row,
    disabled,
    typeCell,
    table,
    column,
    required = true,
    ...props
  }) => {
    const { updateData } = table.options.meta;

    let value = getValue();

    const [tempValue, setTempValue] = useState(value);

    useEffect(() => {
      setTempValue(value);
    }, [value]);

    const handleBlur = (event) => {
      let { value } = event.target;
      updateData(row.index, column.id, value);
    };

    const handleChange = (event) => {
      let { value } = event.target;
      setTempValue(value);
    };

    return (
      <TextField
        name={`${column.id}-${row.id}`}
        value={tempValue}
        onBlur={handleBlur}
        onChange={handleChange}
        className={
          hasError && required ? (!tempValue ? "is-invalid" : "is-valid") : ""
        }
        maxLength={250}
      />
    );
  };

  const validateCellText = (props) => {
    let { original } = props.row;

    return (
      <>
        {original.edicion ? (
          <InputTextCell {...props} />
        ) : (
          <LabelCell {...props} />
        )}
      </>
    );
  };
  const ActionCell = useCallback(
    ({ row, table, column, ...props }) => {
      const { updateData, removeRow } = table.options.meta;
      let { edicion, idServicios } = row.original;
      let { index } = row;

      //#region descartar
      const discard = () => {
        if (idServicios) {
          let originalRow = {
            ...informesServicio[index],
            periodicidad: findValue(
              catalogoPeriodicidad,
              informesServicio[index].idPeriodicidad
            ),
          };
          setDataTable((prev) => {
            let _prev = [...prev];
            _prev[index] = originalRow;
            return _prev;
          });
        } else {
          setDataTable((prev) => {
            let _prev = [...prev];
            _prev.splice(index, 1);
            return _prev;
          });
        }
      };
      const handleDiscard = () => {
        handleShowConfirmModal(MODIFICAR_CONTRATO.MSG002, discard);
      };
      //#endregion

      //#region Editar
      const handleEdit = () => {
        updateData(row.index, column.id, true);
      };
      //#endregion
      //#region Borrar
      const deleteItem = () => {
        setDeleteItems((prev) => [...prev, idServicios]);
        removeRow(index);
      };
      const handleDrop = () => {
        handleShowConfirmModal(MODIFICAR_CONTRATO.MSG017, deleteItem); //MSG17
      };
      //#endregion

      return (
        <Authorization process={"CONT_IDDLS_ADMIN"}>
          {edicion ? (
            <IconButton
              type={"undo"}
              onClick={handleDiscard}
              iconSize="lg"
              // disabled={disabled}
              tableContainer
            />
          ) : (
            <>
              <IconButton
                type={"edit"}
                onClick={handleEdit}
                iconSize="lg"
                disabled={isDetalle}
                tooltip={"Editar"}
                tableContainer
              />
              <IconButton
                type={"drop"}
                onClick={handleDrop}
                iconSize="lg"
                disabled={isDetalle}
                tooltip={"Eliminar"}
                tableContainer
              />
            </>
          )}
        </Authorization>
      );
    },
    [isDetalle, informesServicio, catalogoPeriodicidad, handleShowConfirmModal]
  );
  const columns = useMemo(
    () => [
      {
        accessorKey: "idServicios",
        header: "Id",
        cell: (props) => <>{props.row.index + 1}</>,
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "informeDocumental",
        header: "Informe documental",
        cell: validateCellText,
      },
      {
        accessorKey: "periodicidad",
        header: "Periodicidad",
        cell: (props) =>
          validateCellSelect({ ...props, catalogo: catalogoPeriodicidad }),
      },
      {
        accessorKey: "fechaEntrega",
        header: "Fecha de entrega",
        cell: validateCellText,
      },
      {
        accessorKey: "penasDeduccionesAplicables",
        header: "Penas y/o deducciones aplicables",
        cell: validateCellText,
      },
      {
        accessorKey: "descripcion",
        header: "Descripción",
        cell: (props) => validateCellText({ required: false, ...props }),
      },
      {
        accessorKey: "edicion",
        header: "Acciones",
        cell: ActionCell,
        enableSorting: false,
        enableColumnFilter: false,
      },
    ],
    [catalogoPeriodicidad]
  );
  const findValue = (catalogo, id) => {
    if (!_.isEmpty(catalogo)) {
      let item = catalogo.find((s) => s.primaryKey == id);
      if (item) {
        return item.nombre;
      }
    }
    return id;
  };
  const mapData = useCallback((data, catalogo) => {
    if (_.isEmpty(data)) return [];
    return data.map((s) => {
      return {
        ...s,
        periodicidad: findValue(catalogo, s.idPeriodicidad),
      };
    });
  }, []);

  useEffect(() => {
    if (_.isArray(informesServicio))
      setDataTable(mapData(informesServicio, catalogoPeriodicidad));
  }, [informesServicio, catalogoPeriodicidad, mapData]);

  const emptyItem = {
    idContrato,
    descripcion: "",
    idPeriodicidad: "",
    informeDocumental: "",
    penaConvencionalDeductiva: "",
    fechaEntrega: "",
    estatus: true,
    edicion: true,
  };
  const handleAdd = () => {
    setDataTable([...dataTable, emptyItem]);
  };

  const handleCancel = () => {
    const cancel = () => {
      setDataTable(mapData(informesServicio, catalogoPeriodicidad) || []);
      setDeleteItems([]);
    };

    handleShowConfirmModal(MODIFICAR_CONTRATO.MSG002, cancel); //MSG02
  };
  const ValidData = () => {
    let incompleteData = dataTable.find(
      (s) =>
        s.edicion &&
        (!s.informeDocumental ||
          !s.periodicidad ||
          !s.fechaEntrega ||
          !s.penasDeduccionesAplicables)
    );
    if (incompleteData) return false;
    return true;
  };

  const cancelEdition = () => {
    setDataTable((prevItems) =>
      prevItems.map((item) => ({ ...item, edicion: false }))
    );
  };
  const handleOk = () => {
    cancelEdition();
    getInformesServicio(idContrato);
    setDeleteItems([]);
    handleShowMessage(MODIFICAR_CONTRATO.MSG004); //MSG004
  };

  const actualizar = (dataUpdate) => {
    putActualizar(dataUpdate).then((response) => {
      if (response.error) {
        handleShowMessage(MODIFICAR_CONTRATO.MSG006); //MSG006
      } else {
        handleOk();
      }
    });
  };
  const guardar = (dataCreate, dataUpdate) => {
    postGuardar(dataCreate).then((response) => {
      if (response.error) {
        handleShowMessage(MODIFICAR_CONTRATO.MSG006); //MSG006
      } else {
        if (_.isEmpty(dataUpdate)) {
          handleOk();
        } else {
          actualizar(dataUpdate);
        }
      }
    });
  };

  const borrar = (datadelete, dataCreate, dataUpdate) => {
    postEliminar(datadelete).then((response) => {
      if (response.error) {
        handleShowMessage(MODIFICAR_CONTRATO.MSG022); //MSG022
      } else {
        if (!_.isEmpty(dataCreate)) {
          guardar(dataCreate, dataUpdate);
        } else if (!_.isEmpty(dataUpdate)) {
          actualizar(dataUpdate);
        } else {
          handleOk();
        }
      }
    });
  };
  const handleSave = () => {
    let validData = ValidData();
    if (!validData) {
      sethasError(true);
      errorToast(MODIFICAR_CONTRATO.MSG001); //MSG 001
    } else {
      sethasError(false);
      let createItems = dataTable.filter((s) => s.edicion && !s.idServicios);
      let updateItems = dataTable.filter((s) => s.edicion && s.idServicios);
      if (!_.isEmpty(deleteItems)) {
        borrar(deleteItems, createItems, updateItems);
      } else if (!_.isEmpty(createItems)) {
        guardar(createItems, updateItems);
      } else if (!_.isEmpty(updateItems)) {
        actualizar(updateItems);
      }
    }
  };
  const handleDownloadReport = () => {
    getReporte(idContrato)
      .then((response) => {
        let { data } = response;
        DownloadFileBase64(
          data,
          "reporte.xlsx",
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );
      })
      .catch((error) => {
        handleShowMessage(MODIFICAR_CONTRATO.MSG010); //MSG010
      });
  };
  return (
    <>
      {isLoadingInformesServicio ||
      isLoadingActualizar ||
      isLoadingCatalogo ||
      isLoadingEliminar ||
      isLoadingReporte ||
      isLoadingGuardar ? (
        <Loader />
      ) : null}
      <SingleBasicModal
        size="md"
        show={showMessage}
        onHide={handleCloseMessage}
        title="Mensaje"
        approveText={"Aceptar"}
      >
        {message}
      </SingleBasicModal>
      <BasicModal
        size="md"
        handleApprove={handleApprove}
        handleDeny={handleDenny}
        denyText={"No"}
        approveText={"Sí"}
        show={showConfirmModal}
        title="Mensaje"
        onHide={handleDenny}
      >
        {confirmModalMessage}
      </BasicModal>
      <Row>
        <Col md={12} className="text-end mb-2 mt-4">
          <Authorization process={"CONT_IDDLS_ADMIN"}>
            <IconButton
              type={"add"}
              onClick={handleAdd}
              disabled={isDetalle}
              tooltip={"Agregar"}
            />
          </Authorization>
          <IconButton
            type={"excel"}
            onClick={handleDownloadReport}
            disabled={informesServicio?.length <= 0}
            tooltip={"Exportar a Excel"}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12}>
          <TablaEditable
            hasPagination
            dataTable={dataTable}
            columns={columns}
            onDelete={setDataTable}
            onUpdate={setDataTable}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12} className="text-end">
          {!isDetalle ? (
            <Authorization process={"CONT_IDDLS_ADMIN"}>
              {" "}
              <Button
                variant="red"
                className="btn-sm ms-2 waves-effect waves-light"
                onClick={handleCancel}
                type="submit"
                disabled={!editar}
              >
                Cancelar
              </Button>
              <Button
                variant="green"
                className="btn-sm ms-2 waves-effect waves-light"
                onClick={handleSave}
                type="submit"
                disabled={!editar}
              >
                Guardar
              </Button>
            </Authorization>
          ) : null}
        </Col>
      </Row>
    </>
  );
}
