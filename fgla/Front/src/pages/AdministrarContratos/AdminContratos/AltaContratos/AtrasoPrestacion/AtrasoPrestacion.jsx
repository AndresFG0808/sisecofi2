import React, { useEffect, useState, useRef, useMemo } from "react";
import { Button, Col, Row } from "react-bootstrap";
import IconButton from "../../../../../components/buttons/IconButton";
import { TablaEditable } from "../../../../../components/table/TablaEditable";
import { useParams, useSearchParams } from "react-router-dom";
import {
  useGetAtrasoPrestacionesQuery,
  useLazyGetReporteAtrasoPrestacionesQuery,
  usePostAtrasoPrestacionesMutation,
  usePutAtrasoPrestacionesMutation,
  useDeleteAtrasoPrestacionesMutation,
} from "./store";
import Loader from "../../../../../components/Loader";
import { DownloadFileBase64 } from "../../../../../functions/utils/base64.utils";
import { useToast } from "../../../../../hooks/useToast";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import BasicModal from "../../../../../modals/BasicModal";
import TextField from "../../../../../components/formInputs/TextField";
import _ from "lodash";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
import Authorization from "../../../../../components/Authorization";
export function AtrasoPrestacion({ isDetalle }) {
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
  //Get Listado
  const {
    isLoading: isLoadingAtrasoPrestaciones,
    data: dataAtrasoPrestaciones,
    refetch: getAtrasoPrestaciones,
  } = useGetAtrasoPrestacionesQuery(idContrato, { skip: !idContrato });

  //Guardar
  const [postAtrasoPrestacion, { isLoading: isLoadingPostAtrasoPrestacion }] =
    usePostAtrasoPrestacionesMutation();

  //Actualizar
  const [putAtrasoPrestacion, { isLoading: isLoadingPutAtrasoPrestacion }] =
    usePutAtrasoPrestacionesMutation();

  //borrar
  const [deleteAtrasoPrestacion, { isLoading: isLoadingDeletePrestacion }] =
    useDeleteAtrasoPrestacionesMutation();

  //Get Reporte
  const [getReporte, { isLoading: isLoadingReporte }] =
    useLazyGetReporteAtrasoPrestacionesQuery();

  //#endregion

  //#region Tabla
  const tableReference = useRef();
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
        className={hasError ? (!tempValue ? "is-invalid" : "is-valid") : ""}

       

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

  const ActionCell = ({ row, table, column, ...props }) => {
    const { updateData, removeRow } = table.options.meta;
    let { edicion, idAtrasoPrestacion } = row.original;
    let { index } = row;

    //#region descartar
    const discard = () => {
      if (idAtrasoPrestacion) {
        let originalRow = dataAtrasoPrestaciones[index];
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
      setDeleteItems((prev) => [...prev, idAtrasoPrestacion]);
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
      <Authorization process={"CONT_AIP_ADMIN"}>
        {edicion ? (
          <IconButton
            type={"undo"}
            onClick={handleDiscard}
            iconSize="lg"
            disabled={isDetalle}
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
  };

  const columns = useMemo(
    () => [
      {
        accessorKey: "idAtrasoPrestacion",
        header: "Id",
        cell: (props) => <>{props.row.index + 1}</>,
        enableSorting: false,
        enableColumnFilter: false,
      },
      {
        accessorKey: "descripcion",
        header: "Descripción",
        cell: validateCellText,
      },
      {
        accessorKey: "fechaPrestacion",
        header: "Fecha de prestación",
        cell: validateCellText,
      },
      {
        accessorKey: "penasDeducciones",
        header: "Penas y/o deducciones aplicables",
        cell: validateCellText,
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

  //#region Acciones tabla

  //#region listado Inicial

  useEffect(() => {
    if (dataAtrasoPrestaciones) {
      setDataTable(dataAtrasoPrestaciones);
    }
  }, [dataAtrasoPrestaciones]);

  //#endregion

  //#region Agregar

  const emptyItem = {
    idContrato,
    descripcion: "",
    fechaPrestacion: "",
    penasDeducciones: "",
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
      setDataTable(dataAtrasoPrestaciones || []);
      setDeleteItems([]);
    };

    handleShowConfirmModal(MODIFICAR_CONTRATO.MSG002, cancel); //MSG02
  };
  //#endregion

  //#region  Guardar

  const ValidData = () => {
    let incompleteData = dataTable.find(
      (s) =>
        s.edicion &&
        (!s.descripcion || !s.fechaPrestacion || !s.penasDeducciones)
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
    getAtrasoPrestaciones(idContrato);
    setDeleteItems([]);
    handleShowMessage(MODIFICAR_CONTRATO.MSG004); //MSG004
  };

  const actualizar = (dataUpdate) => {
    putAtrasoPrestacion(dataUpdate).then((response) => {
      if (response.error) {
        handleShowMessage(MODIFICAR_CONTRATO.MSG006); //MSG006
      } else {
        handleOk();
      }
    });
  };
  const guardar = (dataCreate, dataUpdate) => {
    postAtrasoPrestacion(dataCreate).then((response) => {
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
    deleteAtrasoPrestacion(datadelete).then((response) => {
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
      let createItems = dataTable.filter(
        (s) => s.edicion && !s.idAtrasoPrestacion
      );
      let updateItems = dataTable.filter(
        (s) => s.edicion && s.idAtrasoPrestacion
      );
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
      {(isLoadingAtrasoPrestaciones ||
        isLoadingReporte ||
        isLoadingPostAtrasoPrestacion ||
        isLoadingPutAtrasoPrestacion ||
        isLoadingDeletePrestacion) && <Loader />}

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
        <Col md={12} className="text-end  mb-2 mt-4">
          <Authorization process={"CONT_AIP_ADMIN"}>
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
            disabled={dataAtrasoPrestaciones?.length <= 0}
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
            ref={tableReference}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12} className="text-end">
          {!isDetalle ? (
            <Authorization process={"CONT_AIP_ADMIN"}>
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
