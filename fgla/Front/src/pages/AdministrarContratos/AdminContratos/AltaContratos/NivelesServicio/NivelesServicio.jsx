import React, { useMemo, useState, useEffect, useCallback } from "react";
import { Col, Row, Button } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../../components/table/TablaEditable";
import { useParams, useSearchParams } from "react-router-dom";
import {
  useGetNivelesServiciosQuery,
  useLazyGetReporteNivelesServicioQuery,
  usePostCrearNivelesServiciosMutation,
  usePutActualizarNivelesServiciosMutation,
  usePostEliminarNivelesServicioMutation,
} from "./store";
import BasicModal from "../../../../../modals/BasicModal";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { useToast } from "../../../../../hooks/useToast";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
import TextField from "../../../../../components/formInputs/TextField";
import { DownloadFileBase64 } from "../../../../../functions/utils/base64.utils";
import _ from "lodash";
import Loader from "../../../../../components/Loader";
import Authorization from "../../../../../components/Authorization";

export function NivelesServicio({ isDetalle }) {
  const [idContrato, setIdContrato] = useState(null);
  const { idContrato: contratoParam } = useParams();
  let [searchParams, setSearchParams] = useSearchParams();

  useEffect(() => {
    const q = searchParams.get("q");
    setIdContrato(contratoParam || q);
  }, [contratoParam, searchParams]);

  //#region Permisos
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

  //#endregion

  //#region Message Modal
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
  //#endregion

  //#region Confirm Modal

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

  //#endregion

  //#region Servicios
  //Get Data
  const {
    data: nivelesServicios,
    isLoading: isLoadingNivelesServicio,
    refetch: getNiveles,
  } = useGetNivelesServiciosQuery(idContrato, { skip: !idContrato });

  //Reporte
  const [getReporte, { isLoading: isLoadingReporte }] =
    useLazyGetReporteNivelesServicioQuery();
  //Guardar
  const [postGuardarNiveles, { isLoading: isLoadingGuardar }] =
    usePostCrearNivelesServiciosMutation();
  //actualizar
  const [putActualizarNiveles, { isLoading: isLoadingActualizar }] =
    usePutActualizarNivelesServiciosMutation();
  //Eliminar
  const [postEliminarNiveles, { isLoading: isLoadingEliminar }] =
    usePostEliminarNivelesServicioMutation();

  //#endregion

  //#region Tabla
  const [dataTable, setDataTable] = useState([]);
  const [deleteItems, setDeleteItems] = useState([]);
  const [hasError, sethasError] = useState(false);

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



        maxLength={3500}

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
      let { edicion, idServiciosSla } = row.original;
      let { index } = row;

      //#region descartar
      const discard = () => {
        if (idServiciosSla) {
          let originalRow = nivelesServicios[index];
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
        setDeleteItems((prev) => [...prev, idServiciosSla]);
        // setDataTable((prev) => {
        //   let _prev = [...prev];
        //   _prev.splice(index, 1);
        //   return _prev;
        // });
        removeRow(index);
      };
      const handleDrop = () => {
        handleShowConfirmModal(MODIFICAR_CONTRATO.MSG017, deleteItem); //MSG17
      };
      //#endregion

      return (
        <Authorization process={"CONT_NS_ADMIN"}>
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
    [handleShowConfirmModal, isDetalle, nivelesServicios]
  );

  const columns = useMemo(
    () => [
      {
        accessorKey: "idServiciosSla",
        header: "Id",
        cell: (props) => <>{props.row.index + 1}</>,
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "sla",
        header: "SLA",
        cell: validateCellText,
      },
      {
        accessorKey: "deduccionesAplicables",
        header: "Deducciones aplicables",
        cell: validateCellText,
      },
      {
        accessorKey: "objectivoMinimo",
        header: "Objetivo mínimo",
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
    []
  );

  //#endregion

  //#region Acciones Tabla

  //#region  Listado Inicial
  useEffect(() => {
    if (nivelesServicios) {
      setDataTable(nivelesServicios);
    }
  }, [nivelesServicios]);
  //#endregion

  //#region Agregar

  const emptyItem = {
    idContrato,
    descripcion: "",
    sla: "",
    deduccionesAplicables: "",
    objectivoMinimo: "",
    estatus: true,
    edicion: true,
  };

  const handleAdd = () => {
    setDataTable([...dataTable, emptyItem]);
  };

  //#endregion
  //#region  Cancelar

  const handleCancel = () => {
    const cancel = () => {
      setDataTable(nivelesServicios || []);
      setDeleteItems([]);
    };

    handleShowConfirmModal(MODIFICAR_CONTRATO.MSG002, cancel); //MSG02
  };
  //#endregion
  //#region  Guardar

  const ValidData = () => {
    let incompleteData = dataTable.find(
      (s) =>
        s.edicion && (!s.sla || !s.deduccionesAplicables || !s.objectivoMinimo)
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
    getNiveles(idContrato);
    setDeleteItems([]);
    handleShowMessage(MODIFICAR_CONTRATO.MSG004); //MSG004
  };

  const actualizar = (dataUpdate) => {
    putActualizarNiveles(dataUpdate).then((response) => {
      if (response.error) {
        handleShowMessage(MODIFICAR_CONTRATO.MSG006); //MSG006
      } else {
        handleOk();
      }
    });
  };
  const guardar = (dataCreate, dataUpdate) => {
    postGuardarNiveles(dataCreate).then((response) => {
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
    postEliminarNiveles(datadelete).then((response) => {
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
      let createItems = dataTable.filter((s) => s.edicion && !s.idServiciosSla);
      let updateItems = dataTable.filter((s) => s.edicion && s.idServiciosSla);
      if (!_.isEmpty(deleteItems)) {
        borrar(deleteItems, createItems, updateItems);
      } else if (!_.isEmpty(createItems)) {
        guardar(createItems, updateItems);
      } else if (!_.isEmpty(updateItems)) {
        actualizar(updateItems);
      }
    }
  };
  //#endregion

  //#region reporte

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

  //#endregion

  //#endregion
  return (
    <>
      {(isLoadingNivelesServicio ||
        isLoadingReporte ||
        isLoadingGuardar ||
        isLoadingActualizar ||
        isLoadingEliminar) && <Loader />}
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
        <Col md={12} className={"text-end mb-2 mt-4"}>
          <Authorization process={"CONT_NS_ADMIN"}>
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
            disabled={nivelesServicios?.length <= 0}
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
            <Authorization process={"CONT_NS_ADMIN"}>
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
