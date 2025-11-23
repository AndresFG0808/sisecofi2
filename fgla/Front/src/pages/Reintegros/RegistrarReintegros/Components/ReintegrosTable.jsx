import React, { useState, useCallback, useEffect, useRef } from "react";
import { TablaEditable } from "../../../../components/table/TablaEditable";
import { Row, Col, Form, Button } from "react-bootstrap";
import IconButton from "../../../../components/buttons/IconButton";
import { REGISTRAR_REINTEGROS } from "../../../../constants/messages";
import { IconInput } from "./inputs";
import { Formik } from "formik";
import { DownloadFileBase64 } from "../../../../functions/utils/base64.utils";
import {
  useLazyGetReintegrosReporteQuery,
  useGetRRTipoCatQuery,
  usePostCrearReintegrosMutation,
  usePutModificarReintegrosMutation,
  useDeleteEliminarReintegrosMutation,
} from "../Store";
import _ from "lodash";
import { Loader } from "../../../../components";
import {
  LabelCell,
  ActionCell,
  findValue,
  validateCellSelect,
  validateCellIcon,
  validateCellTotalIcon,
  validateCellDate,
  FormatMoney,
  convertirFecha,
} from "./tableCells";
import { useToast } from "../../../../hooks/useToast";
import Authorization from "../../../../components/Authorization";
import { useErrorMessages } from "../../../../hooks/useErrorMessages";
export default function ReintegrosTable({
  handleShowMessage,
  handleShowConfirmModal,
  data,
  idContrato,
  cancelarReintegros,
  obtenerReintegros,
  disableActions=false
}) {

  const {getMessageExists}=useErrorMessages(REGISTRAR_REINTEGROS);
  const { errorToast } = useToast();
  const tableReference = useRef();

  //#region servicios

  //Crear
  const [postReintegros, { isLoading: isLoadingCrear }] =
    usePostCrearReintegrosMutation();

  //Modificar
  const [putReintegros, { isLoading: isLoadingModificar }] =
    usePutModificarReintegrosMutation();

  //Eliminar
  const [deleteReintegros, { isLoading: isLoadingEliminar }] =
    useDeleteEliminarReintegrosMutation();

  //Catlogo tipo
  const { data: dataTipo, isLoading: isLoadingCatTipo } =
    useGetRRTipoCatQuery();

  const [tipoCat, setTipoCat] = useState([]);

  useEffect(() => {
    if (!_.isEmpty(dataTipo)) {
      setTipoCat(dataTipo);
    }
  }, [dataTipo]);

  //GetReporte
  const [getReporte, { isLoading: isLoadingReporte }] =
    useLazyGetReintegrosReporteQuery();

  //#endregion

  //#region Validate Data
  function safeParseFloat(value, defaultValue = 0) {
    return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
  }

  const mapItem = useCallback((item, catalogo) => {
    let { importe = 0, interes = 0, idTipoReintegro, fechaReintegro } = item;
    return {
      ...item,
      tipo: findValue(catalogo, idTipoReintegro),
      fecha: convertirFecha(fechaReintegro, "DD/MM/YYYY"),
      fechaReintegro: convertirFecha(fechaReintegro),
      fechaIso: convertirFecha(fechaReintegro, "YYYY-MM-DDTHH:mm:ss"),
      total: safeParseFloat(importe) + safeParseFloat(interes),
    };
  }, []);

  const mapData = useCallback(
    (data, catalogo) => {
      if (_.isEmpty(data)) return [];
      return data.map((s) => mapItem(s, catalogo));
    },
    [mapItem]
  );

  useEffect(() => {
    if (!_.isEmpty(data)) {
      setDataTable(mapData(data, tipoCat));
    }
  }, [data, mapData, tipoCat]);

  const [dataTable, setDataTable] = useState([]);
  const [deleteItems, setDeleteItems] = useState([]);
  const [hasError, sethasError] = useState(false);

  //#endregion

  //#region Acciones
  //#region Reporte
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
        handleShowMessage(REGISTRAR_REINTEGROS.MSG003);
      });
  };
  //#endregion

  //#region Agregar
  const emptyItem = {
    idContrato,
    tipo: "",
    idTipoReintegro: "",
    importe: "",
    interes: "",
    total: "",
    fechaReintegro: "",
    estatus: true,
    edicion: true,
  };

  const handleAdd = () => {
    setDataTable([...dataTable, emptyItem]);
  };
  //#endregion
  //#region Descartar
  const handleDiscard = (index, id) => {
    const discard = () => {
      if (id) {
        let originalRow = mapItem(data[index], tipoCat);
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

    handleShowConfirmModal(REGISTRAR_REINTEGROS.MSG007, discard);
  };
  //#endregion
  const handleDrop = (index, removeRow) => {
    const deleteItem = () => {
      setDeleteItems((prev) => [
        ...prev,
        dataTable[index].idReintegrosAsociados,
      ]);
      removeRow(index);
    };
    handleShowConfirmModal(REGISTRAR_REINTEGROS.MSG006, deleteItem);
  };

  //#endregion

  const columns = [
    {
      accessorKey: "ordenContrato",
      header: "No.",
      cell: LabelCell,
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "tipo",
      header: "Tipo",
      cell: (props) =>
        validateCellSelect({
          catalogo: tipoCat,
          keyValue: "idTipoReintegro",
          keyTextValue: "nombre",
          hasError,
          ...props,
        }),
    },
    {
      accessorKey: "importe",
      header: "Importe",
      cell: (props) => validateCellIcon({ ...props, hasError }),
      enableColumnFilter: false,
    },
    {
      accessorKey: "interes",
      header: "Interés",
      cell: (props) => validateCellIcon({ ...props, hasError }),
      enableColumnFilter: false,
    },
    {
      accessorKey: "total",
      header: "Total",
      cell: (props) =>
        validateCellTotalIcon({ ...props, hasError, disabled: true }),
      enableColumnFilter: false,
    },
    {
      accessorKey: "fecha",
      header: "Fecha de reintegro",
      cell: (props) => validateCellDate({ ...props, hasError, disabled: true }),
    },
    {
      accessorKey: "edicion",
      header: "Acciones",
      cell: (props) =>
        ActionCell({ handleDiscard, handleDrop, editar:!disableActions, ...props }),
      enableSorting: false,
      enableColumnFilter: false,
    },
  ];

  const handleCancel = () => {
    const cancel = () => {
      cancelarReintegros();
      setDataTable([]);
      setDeleteItems([]);
    };

    handleShowConfirmModal(REGISTRAR_REINTEGROS.MSG007, cancel); //MSG02
  };

  //#region  Guardar

  const handleOk = () => {
    setDataTable(dataTable.map(s=>({...s,edicion:false})))
    obtenerReintegros(idContrato,true);
    setDeleteItems([]);
    handleShowMessage(REGISTRAR_REINTEGROS.MSG010);
  };


    const errorValidations = (errorMessage) => {
      // let errorMessage = error?.response?.data?.mensaje[0];
      if (getMessageExists(errorMessage)) {
        handleShowMessage(errorMessage);
      } else {
        handleShowMessage("Ocurrió un error");
      }
    };

  
  const handleError = (error) => {
    let mensajeError = "";
    if (error?.data) {
      let { mensaje } = error.data;
      if (_.isArray(mensaje) && !_.isEmpty(mensaje)) {
        mensajeError = mensaje[0];
      } else if (_.isString(mensaje) && mensaje) {
        mensajeError = mensaje;
      }
    }
    errorValidations(mensajeError);
  };

  const actualizar = (dataUpdate) => {
    putReintegros(dataUpdate).then((response) => {
      if (response.error) {
        handleError(response.error);
      } else {
        handleOk();
      }
    });
  };
  const guardar = (dataCreate, dataUpdate) => {
    postReintegros(dataCreate).then((response) => {
      if (response.error) {
        handleError(response.error);
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
    deleteReintegros(datadelete).then((response) => {
      if (response.error) {
        handleError(response.error);
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
  const ValidData = () => {
    return !dataTable.some(
      (s) =>
        s.edicion &&
        (!s.fechaReintegro || !s.importe || !s.interes || !s.idTipoReintegro)
    );
  };

  const handleSave = () => {
    let validData = ValidData();
    if (!validData) {
      sethasError(true);
      errorToast(REGISTRAR_REINTEGROS.MSG005);
    } else {
      sethasError(false);
      let values = mapData(dataTable, tipoCat).map((s) => ({
        ...s,
        fechaReintegro: s.fechaIso,
      }));
      let createItems = values.filter(
        (s) => s.edicion && !s.idReintegrosAsociados
      );
      let updateItems = values.filter(
        (s) => s.edicion && s.idReintegrosAsociados
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

  //#region Totales

  const [totales, setTotales] = useState({
    sumImporte: "0.00",
    sumInteres: "0.00",
    sumTotal: "0.00",
  });
  useEffect(() => {
    if (!_.isEmpty(dataTable)) {
      let _sumImporte = dataTable.reduce(
        (acumulador, item) => acumulador + safeParseFloat(item.importe),
        0
      );
      let _sumInteres = dataTable.reduce(
        (acumulador, item) => acumulador + safeParseFloat(item.interes),
        0
      );
      let _sumTotal = _sumImporte + _sumInteres;

      setTotales({
        sumImporte: FormatMoney(_sumImporte),
        sumInteres: FormatMoney(_sumInteres),
        sumTotal: FormatMoney(_sumTotal),
      });
    }
  }, [dataTable]);
  //#endregion

  //#region Disable Save and report
  const [saveDisabled, setSaveDisabled] = useState(true);

  useEffect(() => {
    let edicionActiva = dataTable.find((s) => s.edicion);
    let hasDeleteItems = !_.isEmpty(deleteItems);
    setSaveDisabled(edicionActiva || hasDeleteItems);
  }, [dataTable, deleteItems]);

  const [disabledReport, setDisabledReport] = useState(true);

  useEffect(() => {
    let hasSaveItems = dataTable.find((s) => s.idReintegrosAsociados);
    setDisabledReport(!hasSaveItems);
  }, [dataTable]);
  //#endregion
  return (
    <>
      {(isLoadingCatTipo ||
        isLoadingReporte ||
        isLoadingCrear ||
        isLoadingModificar ||
        isLoadingEliminar) && <Loader />}

      <Row>
        <Col md={12} className="text-end">
          <Authorization process={"CON_SERV_DICT_REINT_ADMIN"}>
            <IconButton
              tooltip={"Nuevo registro"}
              type={"add"}
              onClick={handleAdd}
              disabled={disableActions}
            />
          </Authorization>
          <Authorization process={"CON_SERV_DICT_REINT_CONS"}>
            <IconButton
              tooltip={"Exportar a Excel"}
              type={"excel"}
              onClick={handleDownloadReport}
              disabled={disabledReport}
            />
          </Authorization>
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
        <Col md={4}>
          <IconInput
            label={"Σ Importes:"}
            value={totales.sumImporte}
            disabled={true}
          />
        </Col>
        <Col md={4}>
          <IconInput
            label={"Σ Intereses:"}
            value={totales.sumInteres}
            disabled={true}
          />
        </Col>
        <Col md={4}>
          <IconInput
            label={"Σ Totales:"}
            value={totales.sumTotal}
            disabled={true}
          />
        </Col>
      </Row>
      <Row>
        <Col md={12} className="text-end">
          <Button
            variant="red"
            className="btn-sm ms-2 waves-effect waves-light"
            onClick={handleCancel}
            type="button"
            // disabled={disableActions}
          >
            Cancelar
          </Button>
          {saveDisabled && (
            <Authorization process={"CON_SERV_DICT_REINT_ADMIN"}>
              <Button
                variant="green"
                className="btn-sm ms-2 waves-effect waves-light"
                type="button"
                onClick={handleSave}
                disabled={disableActions}
              >
                Guardar
              </Button>
            </Authorization>
          )}
        </Col>
      </Row>
    </>
  );
}
