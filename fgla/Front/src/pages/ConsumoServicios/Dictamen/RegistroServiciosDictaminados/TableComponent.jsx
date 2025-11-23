import React, { useState, useEffect, useRef, useContext } from "react";
import { useLocation } from "react-router-dom";
import TableComponent from "../../../AdministrarProyectos/Proyectos/Proveedores/components/TableComponent";
import { useToast } from "../../../../hooks/useToast";
import { getData, postData } from "../../../../functions/api";
import { Form } from "react-bootstrap";
import EditField from "./EditField";
import { Col, Row, Button } from "react-bootstrap";
import IconButton from "../../../../components/buttons/IconButton";
import Estimacion from "./Estimacion";
import { DictamenContext } from "../context";
import showMessage from "../../../../components/Messages";
import { Semaforo } from "../../../../components/Semaforo";
import { Loader } from "../../../../components";
import { DownloadFileBase64 } from "../../../../functions/utils/base64.utils";
import * as yup from "yup";
import BasicModal from "../../../../modals/BasicModal";
import Authorization from "../../../../components/Authorization";
import _ from "lodash";
import { logError } from '../../../../components/utils/logError.js';

const OPTIONS_MONEY = { style: "currency", currency: "USD" };
const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
const FORMAT_NUMBER = new Intl.NumberFormat("en-US");

function TableContainer() {
  const { state } = useLocation();
  const { idContrato, idDictamen, estatus, idProveedor } = {
    ...state?.dictamenState,
  };

  const {
    isEditable,
    setServiciosDictaminadosSeleccionados,
    onReloadDictamenInfo,
  } = useContext(DictamenContext);
  const [isBasicModalEstimacion, setIsBasicModalEstimacion] = useState(false);
  const tableReference = useRef();
  const { errorToast } = useToast();
  const [loading, setLoading] = useState(true);
  const [originalData, setOriginalData] = useState([]);
  const [dataTable, setDataTable] = useState([]);
  const [dataEstimaciones, setDataEstimaciones] = useState([]);
  const [selectedEstimacion, setSelectedEstimacion] = useState(null);
  const [hasReRenderTable, setHasReRenderTable] = useState(false);
  const [hasActiveUseStateRender, setHasActiveUseStateRender] = useState(false);
  const [mostrarColumnaCC, setMostarColumnaCC] = useState(true);
  const [showModalNoSeleccionados, setShowModalNoSeleccionados] =
    useState(false);
  const [noSelected, setNoSelected] = useState(0);
  const [cancelModal, setCancelModal] = useState(false);

  const FORMAT_NUMBER_DECIMALS = new Intl.NumberFormat("en-US", {
    minimumFractionDigits: 0,
    maximumFractionDigits: 6,
    useGrouping: true,
  });

  function formatNumberWithDecimals(number) {
    return FORMAT_NUMBER_DECIMALS.format(number);
  }
  useEffect(() => {
    if (idContrato && idDictamen && idProveedor) {
      getDataInit();
    } else {
      setLoading(false);
      showMessage("Faltan datos necesarios para cargar la información.");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const getDataInit = () => {
    try {
      let aplicaCC = getData("/admin-devengados/AplicaCC/" + idContrato);
      let servicios = postData(
        "/admin-devengados/obtener-servicios/" + idContrato,
        { idDictamen }
      );
      let estimaciones = getData(
        "/admin-devengados/obtener-estimaciones/" +
        idContrato +
        "/" +
        idProveedor
      );

      Promise.all([aplicaCC, servicios, estimaciones])
        .then(([aplicaCCResp, serviciosResp, estimacionesResp]) => {
          setMostarColumnaCC(aplicaCCResp.data.data);

          const modifiedServicios = serviciosResp.data.data.map((servicio) => ({
            ...servicio,
            cantidadServiciosSat: formatNumberWithDecimals(
              servicio.cantidadServiciosSat
            ),
            cantidadServiciosCc: formatNumberWithDecimals(
              servicio.cantidadServiciosCc
            ),
          }));

          console.log(modifiedServicios);
          processServicios(modifiedServicios);
          setDataEstimaciones(estimacionesResp.data.data);
          setLoading(false);
          const serviciosSeleccionados = [...modifiedServicios].filter(
            (item) => item.checked
          );
          setServiciosDictaminadosSeleccionados(serviciosSeleccionados);
        })
        .catch((error) => {
          logError("error => ", error);
          setLoading(false);
          showMessage("Ocurrió un error");
        });
    } catch (error) {
      console.log(error);
    }
  };

  const processServicios = (data) => {
    let processedDataTable = [];
    data.forEach((item, index) => {
      let row = {
        ...item,
        consecutivo: index + 1,
        precioUnitarioView:
          "$ " + FORMAT_MONEY.format(item.precioUnitario).split("$")[1],
        montoDictaminadoView:
          "$ " + FORMAT_MONEY.format(item.montoDictaminado).split("$")[1],
        montonDictaminadoAcumuladoView:
          "$ " +
          FORMAT_MONEY.format(item.montonDictaminadoAcumulado).split("$")[1],
        montoMaximoServicioView:
          "$ " + FORMAT_MONEY.format(item.montoMaximoServicio).split("$")[1],
        cantidadTotalServiciosView: FORMAT_NUMBER.format(
          item.cantidadTotalServicios
        ),
        cantidadServicioDictaminadoAcumuladoView: FORMAT_NUMBER.format(
          item.cantidadServicioDictaminadoAcumulado
        ),
        cantidadServiciosSat: item.cantidadServiciosSat,
        cantidadServiciosCc: item.cantidadServiciosCc,
        cantidadServiciosVigenteView: FORMAT_NUMBER.format(
          item.cantidadServiciosVigente
        ),
      };
      processedDataTable.push(row);
    });
    setOriginalData(processedDataTable);
    setDataTable(processedDataTable);
    tableReference.current.table.options.meta.revertData(processedDataTable);
  };

  const handleDownloadExcel = async () => {
  setLoading(true);

  const dataList = [...tableReference.current.table.options.meta.getDataTable()];
  const preprocessed = preprocessData(dataList);

  const processedDataTable = preprocessed.map((item) => ({
    ...item,
    idDictaminado: item.consecutivo,
  }));

  const data = { lista: processedDataTable };

  postData("/admin-devengados/generar-excel-dictaminado", data)
    .then((response) => {
      setLoading(false);
      DownloadFileBase64(
        response.data.data,
        "serviciosDictaminados",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    })
    .catch((error) => {
      logError("error => ", error);
      setLoading(false);
      showMessage("Ocurrió un error");
    });
};

  useEffect(() => {
    if (hasActiveUseStateRender === true) {
      setHasActiveUseStateRender(false);
      setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
    }
  }, [hasActiveUseStateRender, dataTable]);

  const CustomChecked = ({ getValue, onChangeChecked, row, isEditable }) => {
    const { consecutivo } = row.original;
    const checked = getValue();
    return (
      <>
        <Form.Check
          type={"checkbox"}
          id={"1"}
          disabled={!isEditable}
          onChange={() => {
            onChangeChecked(consecutivo, !checked);
          }}
          style={{ cursor: "pointer" }}
          checked={checked}
        />
      </>
    );
  };

  const onChangeChecked = async (consecutivo, value) => {
    const data = [...tableReference.current.table.options.meta.getDataTable()];

    const dataArray = [...data].map((item) => {
      const checked = item.consecutivo === consecutivo ? value : item.checked;
      const newItem = { ...item };
      newItem.checked = checked;
      return { ...newItem };
    });

    await tableReference.current.table.options.meta.revertData(dataArray);
    setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
  };

  const serviciosValidations = async (data) => {
    console.log(data);
    let dataErrors = false;

    let schema = yup.object().shape({
      checked: yup.boolean(),
      cantidadTotalServicios: yup.number(),
      cantidadServiciosSat: yup
        .number()
        .transform((value, originalValue) => {
          if (typeof originalValue === "string") {
            return parseFloat(originalValue.replace(/,/g, ""));
          }
          return value;
        })
        .when(["checked", "cantidadTotalServicios"], {
          is: (checked, cantidadTotalServicios) =>
            checked === true && cantidadTotalServicios <= 0,
          then: () => yup.number().required("Dato requerido"),
          otherwise: () => yup.number().required("Dato requerido"),
        }),
      cantidadServiciosCc: yup
        .number()
        .transform((value, originalValue) => {
          if (typeof originalValue === "string") {
            return parseFloat(originalValue.replace(/,/g, ""));
          }
          return value;
        })
        .when(["checked", "cantidadTotalServicios"], {
          is: (checked, cantidadTotalServicios) =>
            mostrarColumnaCC && checked === true && cantidadTotalServicios <= 0,
          then: () => yup.number().required("Dato requerido"),
          otherwise: () => yup.number().required("Dato requerido"),
        }),
    });

    let resultados = await Promise.all(
      data.map(async (obj) => {
        try {
          await schema.validate(obj, { abortEarly: false });
          delete obj.errors;
          return obj;
        } catch (err) {
          dataErrors = true;
          return {
            ...obj,
            errors: err.inner.reduce((acc, error) => {
              acc[error.path] = error.message;
              return acc;
            }, {}),
          };
        }
      })
    );
    return { dataErrors, resultados };
  };

  const actualizarServicios = () => {
    setLoading(true);
    setIsBasicModalEstimacion(false);

    let dataUpdate = {
      listaDictaminados:
        tableReference.current.table.options.meta.getDataTable(),
      idEstimacion: selectedEstimacion?.idEstimacion || "",
    };

    updateServices(dataUpdate);
  };

  const actualizarServiciosInterno = (servicios) => {
    setLoading(true);
    setIsBasicModalEstimacion(false);

    let dataUpdate = {
      listaDictaminados: servicios,
      idEstimacion: selectedEstimacion?.idEstimacion || "",
    };
    updateServices(dataUpdate);
  };

  const updateServices = (dataUpdate) => {
    postData("/admin-devengados/actualizar-servicio-contrato", dataUpdate)
      .then((response) => {
        processServicios(response.data.data);
        selectedEstimacion &&
          showMessage(
            "Se actualizó el registro de los servicios de acuerdo con la estimación seleccionada."
          );
        setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
        setLoading(false);
        setSelectedEstimacion(null);
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        setSelectedEstimacion(null);
        showMessage("Ocurrió un error");
      });
  };

  const CustomCircleIcon = ({ color, column, row }) => {
    const values = row.original;

    let mapCols = {
      colorPorcentajetServicios: "porcentajeServiciosDictaminadosAcumulados",
      colorPorcentajeMonto: "porcentajeMontoDictaminadosAcumulados",
    };

    let porcentaje = values[mapCols[column.id]];
    porcentaje =
      porcentaje === null || porcentaje === undefined
        ? ""
        : porcentaje.toString();

    return color ? (
      <>
        {porcentaje}
        {porcentaje && "%"}
        <br />
        <Semaforo color={color.toLowerCase()} />
      </>
    ) : null;
  };

  const preprocessData = (data) =>
  data.map((item) => ({
    ...item,
    cantidadServiciosSat: normalizeNumber(item.cantidadServiciosSat),
    cantidadServiciosCc: normalizeNumber(item.cantidadServiciosCc),
  }));

  const handleSave = async () => {
    setLoading(true);
    const servicios = [
      ...tableReference.current.table.options.meta.getDataTable(),
    ];

    if (_.isEqual(originalData, servicios)) {
      setLoading(false);
      return;
    }
    const preprocessedServicios = preprocessData(servicios);
    serviciosValidations(preprocessedServicios).then(
      ({ dataErrors, resultados }) => {
        if (dataErrors) {
          tableReference.current.table.options.meta.revertData(resultados);
          errorToast(
            "Favor de ingresar los datos obligatorios marcados con un asterisco (*)."
          );
          setLoading(false);
          return;
        } else {
          let noSeleccionados = validarNoSeleccionados(preprocessedServicios);
          if (noSeleccionados > 0) {
            setShowModalNoSeleccionados(true);
            setLoading(false);
          } else {
            guardarServicios(preprocessedServicios);
          }
        }
      }
    );
  };

  const cancelAccept = () => {
    setCancelModal(false);
    setLoading(true);
    getDataInit();
  };

  const validarNoSeleccionados = (preprocessedServicios) => {
    let noSeleccionados = preprocessedServicios.filter(
      ({ checked, cantidadTotalServicios }) =>
        checked === false && cantidadTotalServicios > 0
    );
    setNoSelected(noSeleccionados.length);
    return noSeleccionados.length;
  };

  const guardarServiciosConfirm = () => {
    setShowModalNoSeleccionados(false);
    setLoading(true);
    const data = [...tableReference.current.table.options.meta.getDataTable()];

    const dataArray = [...data].map((item) => {
      const checked =
        item.checked === false && item.cantidadTotalServicios > 0
          ? true
          : item.checked;
      const newItem = { ...item };
      newItem.checked = checked;
      return { ...newItem };
    });

    tableReference.current.table.options.meta.revertData(dataArray);
    setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
    guardarServicios(dataArray);
  };

  const guardarServiciosCancel = () => {
    setShowModalNoSeleccionados(false);
    const data = [...tableReference.current.table.options.meta.getDataTable()];

    const dataArray = [...data].map((item) => {
      const sat =
        item.checked === false && item.cantidadTotalServicios > 0
          ? 0
          : item.cantidadServiciosSat;
      const cc =
        item.checked === false && item.cantidadTotalServicios > 0
          ? 0
          : item.cantidadServiciosCc;
      const newItem = { ...item };
      newItem.cantidadServiciosSat = sat;
      newItem.cantidadServiciosCc = cc;
      return { ...newItem };
    });

    tableReference.current.table.options.meta.revertData(dataArray);
    setHasReRenderTable((prevHasReRenderTable) => !prevHasReRenderTable);
    actualizarServiciosInterno(dataArray);
  };

  const guardarServicios = (preprocessedServicios) => {
    const serviciosSeleccionados = [...preprocessedServicios].filter(
      (item) => item.checked
    );

    postData(
      "/admin-devengados/guardar-servicio-dictaminado",
      preprocessedServicios
    )
      .then((response) => {
        const modifiedServicios = response.data.data.map((servicio) => ({
          ...servicio,
          cantidadServiciosSat: formatNumberWithDecimals(
            servicio.cantidadServiciosSat
          ),
          cantidadServiciosCc: formatNumberWithDecimals(
            servicio.cantidadServiciosCc
          ),
        }));

        console.log(modifiedServicios);
        processServicios(modifiedServicios);
        setServiciosDictaminadosSeleccionados(serviciosSeleccionados);
        onReloadDictamenInfo(idDictamen).then(() => {
          setLoading(false);
          showMessage("Se registraron los servicios correctamente.");
        });
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        showMessage("Ocurrió un error");
      });
  };

  const arrayColumns = [
    {
      accessorKey: "checked",
      header: "Seleccionar",
      enableSorting: false,
      enableColumnFilter: false,
      cell: (props) => (
        <CustomChecked
          onChangeChecked={onChangeChecked}
          row={props.row}
          getValue={props.getValue}
          isEditable={isEditable}
        />
      ),
    },
    {
      enableSorting: false,
      enableColumnFilter: false,
      accessorKey: "consecutivo",
      header: "Id",
      filterFn: "includesString",
    },
    {
      accessorKey: "grupoServicio",
      header: "Grupo de servicio",
      filterFn: "includesString",
    },
    {
      accessorKey: "conseptosServico",
      header: "Conceptos de servicio",
      filterFn: "includesString",
    },
    {
      accessorKey: "unidadMedida",
      header: "Unidad de medida",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "tipoConsumo",
      header: "Tipo de consumo",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "precioUnitarioView",
      header: "Precio unitario",
      cell: (props) => <p style={{ width: "130px" }}>{props.getValue()}</p>,
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "cantidadServiciosVigenteView",
      header: "Cantidad de servicios máxima vigente",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "montoMaximoServicioView",
      header: "Monto máximo vigente",
      cell: (props) => <p style={{ width: "130px" }}>{props.getValue()}</p>,
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "cantidadServiciosSat",
      header: "Cantidad de servicios SAT",
      filterFn: "includesString",
      width: 300,
      enableSorting: false,
      enableColumnFilter: false,
      cell: (props) => (
        <EditField
          getValue={props.getValue}
          type={"select"}
          rowClassName="col-provider"
          isRequired={true}
          column={props.column}
          row={props.row}
          table={props.table}
          onEmptyActiveProviders={() => { }}
          isEditable={!isEditable || !estatus}
          setDataTable={setDataTable}
        />
      ),
    },
    {
      accessorKey: "cantidadServiciosCc",
      header: "Cantidad de servicios CC",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
      cell: (props) => (
        <EditField
          getValue={props.getValue}
          type={"select"}
          rowClassName="col-provider"
          isRequired={true}
          column={props.column}
          row={props.row}
          table={props.table}
          onEmptyActiveProviders={() => { }}
          isEditable={!isEditable || !estatus}
          setDataTable={setDataTable}
        />
      ),
      showColumn: mostrarColumnaCC,
    },
    {
      accessorKey: "cantidadTotalServiciosView",
      header: "Cantidad total de servicios",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "montoDictaminadoView",
      header: "Monto dictaminado",
      cell: (props) => <p style={{ width: "130px" }}>{props.getValue()}</p>,
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "cantidadServicioDictaminadoAcumuladoView",
      header: "Cantidad de servicios dictaminados acumulados",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "montonDictaminadoAcumuladoView",
      header: "Monto dictaminado acumulado",
      cell: (props) => <p style={{ width: "130px" }}>{props.getValue()}</p>,
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
    },
    {
      accessorKey: "colorPorcentajetServicios",
      header: "% de servicios dictaminados acumulados",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
      cell: (props) => (
        <CustomCircleIcon
          color={props.getValue()}
          column={props.column}
          row={props.row}
        />
      ),
    },
    {
      accessorKey: "colorPorcentajeMonto",
      header: "% de monto dictaminado acumulado",
      filterFn: "includesString",
      enableSorting: false,
      enableColumnFilter: false,
      cell: (props) => (
        <CustomCircleIcon
          color={props.getValue()}
          column={props.column}
          row={props.row}
        />
      ),
    },
  ];

 const normalizeNumber = (v) => {
  if (v === null || v === undefined) return null;
  if (typeof v === "number") return v;

  if (typeof v === "string") {
    const s = v.trim();
    if (s === "") return null;
    const n = Number(s.replace(/,/g, ""));
    return Number.isFinite(n) ? n : null;
  }
  return null;
};


  const getColumns = () => {
    let columns = arrayColumns.filter((column) => {
      if (column.hasOwnProperty("showColumn")) {
        if (mostrarColumnaCC) {
          return column;
        }
      } else {
        return column;
      }
    });
    return columns;
  };

  const columns = React.useMemo(
    () => {
      return getColumns();
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [hasReRenderTable, mostrarColumnaCC]
  );

  return (
    <>
      {loading && <Loader />}
      <Row>
        <Col md="6" className="mb-2">
          <IconButton
            onClick={() => {
              setIsBasicModalEstimacion(true);
            }}
            disabled={!isEditable}
            type={"twoFilesBlack"}
            tooltip={"Traer estimación"}
          />
        </Col>
        <Col md="6" className="text-end mb-2">
          <IconButton
            type={"excel"}
            onClick={handleDownloadExcel}
            tooltip={"Exportar a Excel"}
            disabled={dataTable.length === 0}
          />
        </Col>
      </Row>

      <TableComponent
        tableReference={tableReference}
        dataTable={dataTable}
        columns={columns}
        providerTableTitle=""
        setDataTable={() => { }}
      />

      <Row>
        <Col md={12} className="text-end">
          <Authorization process={"CON_SERV_ADMIN_DICT"}>
            <Button
              variant="red"
              className="btn-sm ms-2 waves-effect waves-light"
              disabled={!isEditable}
              onClick={() => setCancelModal(true)}
            >
              Cancelar
            </Button>

            <Button
              variant="green"
              className="btn-sm ms-2 waves-effect waves-light"
              onClick={handleSave}
              disabled={!isEditable}
            >
              Guardar
            </Button>
          </Authorization>
        </Col>
      </Row>

      <BasicModal
        size="md"
        handleApprove={guardarServiciosConfirm}
        handleDeny={guardarServiciosCancel}
        denyText={"No"}
        approveText={"Sí"}
        show={showModalNoSeleccionados}
        title="Mensaje"
        onHide={() => setShowModalNoSeleccionados(false)}
      >
        {"Existe(n) " +
          noSelected +
          " servicio(s) con valores. ¿Desea conservarlos?"}
      </BasicModal>

      <BasicModal
        size="md"
        handleApprove={cancelAccept}
        handleDeny={() => setCancelModal(false)}
        denyText={"No"}
        approveText={"Sí"}
        show={cancelModal}
        title="Mensaje"
        onHide={() => setCancelModal(false)}
      >
        {
          "Al cancelar se perderán los cambios realizados. ¿Está seguro de continuar?"
        }
      </BasicModal>

      {isBasicModalEstimacion && (
        <Estimacion
          setIsBasicModal={setIsBasicModalEstimacion}
          isBasicModal={isBasicModalEstimacion}
          dataEstimaciones={dataEstimaciones}
          setSelectedEstimacion={setSelectedEstimacion}
          onSuccess={actualizarServicios}
        />
      )}
    </>
  );
}

export default TableContainer;
