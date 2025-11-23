import React, { useState, useEffect, useRef, useContext } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Form, Row, Col, Button } from "react-bootstrap";
import LabelValue from "../../../../components/LabelValue";
import Loader from "../../../../components/Loader";
import { Formik } from "formik";
import * as yup from "yup";
import IconButton from "../../../../components/buttons/IconButton";
import { postData, getData, putData } from "../../../../functions/api";
import { Tooltip } from "../../../../components/Tooltip";
import TextFieldDate from "../../../../components/formInputs/TextFieldDate";
import Select from "../../../../components/formInputs/Select";
import TextField from "../../../../components/formInputs/TextField";
import TextFieldWithIconLeft from "../../../../components/formInputs/TextFieldIcon";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import TableComponent from "./TableComponent";
import { BotonesAcciones } from "./BotonesAcciones";
import SingleBasicModal from "../../../../modals/SingleBasicModal";
import BasicModal from "../../../../modals/BasicModal";
import Comentarios from "../../../../components/Comentarios";
import { useToast } from "../../../../hooks/useToast";
import { MESSAGES } from "../../Servicios/constants";
import { DictamenContext } from "../context";
import showMessage from "../../../../components/Messages";
import { DownloadFileBase64 } from "../../../../functions/utils/base64.utils";
import moment from "moment";
import "moment/locale/es";
import { isEmpty, isNil } from "lodash";
import Authorization from "../../../../components/Authorization";

/*
-En estatus Dictaminado se debe poner en modo lectura esto carnal
-Tipo de cambio referencial depende que la moneda del contrato no sea mxn
-No puede duplicar un dictamen si se encuentra en estatus pagado
-EL botón siguiente y antes debe saber desde antes si tiene o no siguiente o antes (verificar con back)
-Se actualiza el resumen consolidado cuando se actualiza registro dictaminado, penas y deducciones
-Sino hay registros se pone el mensaje de no hay información
- RNA101-RNA103 Para periodo de control y periodo inicio/fin
- Habilita la sección “Registro de servicios dictaminados” y las opciones “Duplicar dictamen”, “Dictamen anterior”, “Dictamen posterior”, “Exportar a Excel” y “Cancelar dictamen”. 
-
-
-
-

*/
const MSG001 = "Se guardó correctamente la información.";
const MSG011 =
  "La estructura de la información ingresada es incorrecta. Intente nuevamente.";
const MSG002 =
  "El periodo de inicio es incorrecto. Por favor verifique la información.";
const MSG003 =
  "El periodo fin es incorrecto. Por favor verifique la información.";
const MSG004 =
  "El periodo de control debe estar dentro del periodo de inicio y periodo fin del contrato.";
const MSG005 =
  "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).";
const MSG007 = "No existe información del dictamen.";
const MSG010 =
  "Ocurrió un error al guardar la información, favor de intentar nuevamente (PA01).";
const MSG012 =
  "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).";
const ERROR_PERIOD = "Dato incorrecto";

const VALORES_INICIALES = {
  fechaInicio: "",
  fechaFin: "",
  mes: "",
  año: "",
  iva: "",
  tipoCambio: "",
  idFormateado: "",
  nombreCortoContrato: "",
  numeroContrato: "",
  proveedor: "",
  estatus: "",
  descripcion: "",
};

const regexp = /^$|^(0|[1-9]\d?)(\.\d{0,4})?$/;
const selectPlaceholder = "Seleccione una opción";

const DatosGenerales = ({ }) => {
  const location = useLocation();
  const navigate = useNavigate();

  let idDictamenState = location?.state?.dictamenState?.idDictamen;
  let idDictamenVisualState = location?.state?.dictamenState?.idDictamenVisual;
  idDictamenState = idDictamenState === undefined ? "" : idDictamenState;
  idDictamenVisualState =
    idDictamenVisualState === undefined ? "" : idDictamenVisualState;
  const generalEstatus = location?.state?.dictamenState?.estatus;
  const [editableDuplicateButton, setEditableDuplicateButton] = useState(
    isNil(location?.state?.dictamenState?.editableDuplicateButton)
      ? true
      : false
  );
  const [isNextOrBeforeDictamen, setIsNextOrBeforeDictamen] = useState(false);

  const [isActiveDuplicated, setIsActiveDuplicated] = useState(false);

  const [isSelectedBeforeOrAfterButton, setIsSelectedBeforeOrAfterButton] =
    useState(false);

  const [isOpenModal, setIsOpenModal] = useState(false);
  const [idDictamen, setIdDictamen] = useState(idDictamenState);
  const [idDictamenVisual, setIdDictamenVisual] = useState(
    idDictamenVisualState
  );
  const [idTemporalDictamen, setIdTemporalDictamen] = useState("");
  const [idTemporalDictamenVisual, setIdTemporalDictamenVisual] = useState("");

  let idContrato = location?.state?.dictamenState?.idContrato;
  // corregir mejor
  idContrato = idContrato === undefined ? "" : idContrato;

  const proveedor = location?.state?.dictamenState?.proveedor;

  const isEditableTable = location?.state?.dictamenState?.editable;

  const {
    //  isEditable,
    setSeccionesInactivas,
    SECCIONES_INICIAL,
    setUltimaModificacion,
    setFechasContrato,
    setShowSecciones,
    showSecciones,
    onReloadDictamenInfo,
    setOnReloadDictamenInfo,
    onClearPenasContractuales,
    onClearPenasConvencionales,
    onClearDeducciones,
    setIsEditable,
    isEditable,
    setDictaminadoStatusEditable,
    setDictamenInfo,
    dictamenInfo,
  } = useContext(DictamenContext);

  const formRef = useRef();
  const handleResetForm = () => {
    if (formRef.current) {
      formRef.current.resetForm(); // Resetea el formulario utilizando la referencia
    }
  };

  const { errorToast } = useToast();

  const [dataTable, setDataTable] = useState(
    idDictamen === "" || idDictamen === null
      ? [
        {
          nombreFase: "Dictaminado",
          subtotal: "0",
          deducciones: "0",
          ieps: "0",
          iva: "0",
          impuestos: "0",
          total: "0",
          totalPesos: "0",
        },
        {
          nombreFase: "Facturado",
          subtotal: "0",
          deducciones: "0",
          ieps: "0",
          iva: "0",
          impuestos: "0",
          total: "0",
          totalPesos: "0",
        },
      ]
      : []
  );

  const [readOnlyTipoDeCambio, setReadOnlyTipoDeCambio] = useState(0);

  const [generalData, setGeneralData] = useState({});

  const [dictamenData, setDictamenData] = useState({});

  const [monthCatalog, setMonthCatalog] = useState([]);
  const [yearsCatalog, setYearsCatalog] = useState([]);
  const [ivaCatCatalog, setIvaCatCatalog] = useState([]);

  const [commentValue, setCommentValue] = useState("");

  const [readOnly, setReadOnly] = useState(!isEditableTable);

  const [isOpenModalErorDate, setIsOpenModalErorDate] = useState(false);
  const [messageErrorDate, setMessageErrorDate] = useState("");

  const [errorPeriodoInicio, setErrorPeriodoInicio] = useState("");
  const [errorPeriodoFin, setErrorPeriodoFin] = useState("");
  const [errorPeriodoControlMes, setErrorPeriodoControlMes] = useState("");
  const [errorPeriodoControlAnho, setErrorPeriodoControlAnho] = useState("");
  const [errorTipoCambio, setErrorTipoCambio] = useState("");

  const [loading, setLoading] = useState(true);
  const [isOpenCommentModal, setIsOpenCommentModal] = useState(false);
  const [isOpenSuccess, setIsOpenSuccess] = useState(false);
  const [valoresIniciales, setValoresIniciales] = useState({
    ...VALORES_INICIALES,
  });

  const [selectedToDuplicateSections, setSelectedToDuplicateSections] =
    useState([]);

  const onUpdateGeneralDictamenData = (
    idDictamen,
    idDictamenVisual,
    info = {},
    editable = true,
    isClearStatus = false
  ) => {
    const { estatus } = formRef.current.values;

    let dictamenState = {
      idDictamen: idDictamen,
      idContrato: info.idContrato,
      idProveedor: info.idProovedor,
      idDictamenVisual: idDictamenVisual,
      editable,
      estatus: isClearStatus ? "" : estatus,
      tipoCambioReferencial: info.tipoCambioReferencial,
    };
    navigate("/consumoServicios/consumoServicios/dictamen", {
      state: {
        dictamenState,
      },
    });
  };

  const referenceStatus = formRef?.current?.values?.estatus;
  useEffect(() => {
    if (isNil(referenceStatus) === false && isEmpty(dictamenData) === false) {
      let dictamenState = {
        idDictamen: idDictamen,
        idDictamenVisual: idDictamenVisual,
        idContrato: dictamenData.idContrato,
        idProveedor: dictamenData.idProovedor,
        editable: isEditableTable,
        estatus: referenceStatus,
        tipoCambioReferencial: dictamenData.tipoCambioReferencial,
      };
      navigate("/consumoServicios/consumoServicios/dictamen", {
        state: {
          dictamenState,
        },
      });
    }
  }, [referenceStatus, dictamenData, isEditableTable]);

  const onHideAllSection = () => {
    const secciones = { ...showSecciones };
    for (let item in secciones) {
      secciones[item] = false;
      setShowSecciones({ ...secciones });
    }
  };

  const onClearErrorFields = () => {
    setErrorPeriodoInicio("");
    setErrorPeriodoFin("");
    setErrorPeriodoControlMes("");
    setErrorPeriodoControlAnho("");
    setErrorTipoCambio("");
    formRef.current.touched.iva = false;
  };

  const validateNumber = (input) => {
    const regex = /^\d{1,2}(\.\d{1,4})?$/;
    return regex.test(input.toString().trim());
  };

  const esquema = yup.object({
    fechaInicio: yup.string().required("Dato requerido"),
    fechaFin: yup.string().required("Dato requerido"),
    mes: yup.string().required("Dato requerido"),
    año: yup.string().required("Dato requerido"),
    iva: yup.string().required("Dato requerido"),
    tipoCambio: yup.string().required("Dato requerido"),
  });

  const onValidateFields = (values) => {
    let isError = false;
    if (values.fechaInicio === "") {
      isError = true;
      setErrorPeriodoInicio("Dato requerido");
    }
    if (values.fechaFin === "") {
      isError = true;
      setErrorPeriodoFin("Dato requerido");

      setErrorPeriodoInicio();
      setErrorPeriodoFin();
    }

    if (
      validateNumber(values.tipoCambio) === false &&
      readOnlyTipoDeCambio !== 1 &&
      values.tipoCambio !== ""
    ) {
      isError = true;
      setIsOpenModalErorDate(true);
      setMessageErrorDate(MSG011);
      setErrorTipoCambio(MSG011);
    }
    if (values.tipoCambio === "" && readOnlyTipoDeCambio !== 1) {
      isError = true;
      setErrorTipoCambio("Dato requerido");
    }

    const { isErrorValidate, isOnlyDateError } = onValidateRangeDates(
      formRef.current.values["fechaInicio"],
      formRef.current.values["fechaFin"],
      formRef.current.values["mes"],
      formRef.current.values["año"]
    );
    isError = isErrorValidate;
    if (values.mes === "") {
      isError = true;
      setErrorPeriodoControlMes("Dato requerido");
    }
    if (values.año === "") {
      isError = true;
      setErrorPeriodoControlAnho("Dato requerido");
    }

    if (
      values.fechaInicio === "" ||
      values.fechaFin === "" ||
      values.mes === "" ||
      values.año === "" ||
      values.iva === "" ||
      (values.tipoCambio === "" && readOnlyTipoDeCambio !== 1)
    ) {
      isError = true;
    }

    return { isError, isOnlyDateError };
  };

  const onValidateRangeDates = (initialDate, endDate, idMonth, idYear) => {
    let isError = false;
    let isOnlyDateError = false;
    // ---------------- validación periodo
    if (idMonth !== "" && idYear !== "") {
      const startMonthText = monthCatalog.filter(
        (item) => idMonth == item.primaryKey
      )[0].nombre;
      const startYearText = yearsCatalog.filter(
        (item) => idYear == item.primaryKey
      )[0].nombre;
      const date = startMonthText + " " + startYearText;
      const rangeDates = onGetRangeDate(date, startMonthText, startYearText);

      const isPeriodError = onRangeDates(
        rangeDates.firstDay,
        rangeDates.lastDay,
        initialDate,
        endDate
      );

      if (isPeriodError === false && initialDate !== "" && endDate !== "") {
        showMessage(MSG004);
        setErrorPeriodoControlMes(ERROR_PERIOD);
        setErrorPeriodoControlAnho(ERROR_PERIOD);
        isOnlyDateError = true;
        isError = true;
      } else if (errorPeriodoControlMes !== "" && isPeriodError) {
        setErrorPeriodoControlMes(null);
        setErrorPeriodoControlAnho(null);
        setErrorPeriodoInicio(null);
        setErrorPeriodoFin(null);
      }
    }
    // ---------------- validación periodo
    const { fecha_inicio, fecha_termino } = generalData;
    const fechaContratoInicio = moment(fecha_inicio).format("YYYY-MM-DD");
    const fechaContratoFin = moment(fecha_termino).format("YYYY-MM-DD");

    // ---------------- validación fecha inicio
    if (
      compararFechas(initialDate, fechaContratoInicio, fechaContratoFin) ===
      false &&
      initialDate !== ""
    ) {
      setErrorPeriodoInicio(ERROR_PERIOD);
      showMessage(MSG002);
      isOnlyDateError = true;
      isError = true;
    }
    // ---------------- validación fecha inicio
    // ---------------- validación fecha fin
    if (
      compararFechas(endDate, fechaContratoInicio, fechaContratoFin) ===
      false &&
      endDate !== ""
    ) {
      isOnlyDateError = true;
      setErrorPeriodoFin(ERROR_PERIOD);
      showMessage(MSG003);
      isError = true;
    }
    // ---------------- validación fecha fin
    return { isErrorValidate: isError, isOnlyDateError };
  };

  const onPostService = async (url, data, errorMssage = MSG012) => {
    try {
      const response = await postData(url, data);
      return response.data;
    } catch (err) {
      if (err?.response?.status === 404) {
        showMessage(errorMssage);
      } else {
        let errorMessage =
          err?.response?.data !== "" &&
          err?.response?.data?.mensaje &&
          err?.response?.data?.mensaje[0];
        let errorIdDuplicado = errorMessage === MSG011;
        if (errorIdDuplicado && err?.response?.status !== 403) {
          showMessage(errorMessage);
        } else if (err?.response?.status !== 403) {
          showMessage(errorMssage);
        }
      }
      return null;
    }
  };

  const onPutService = async (url, data) => {
    try {
      const response = await putData(url, data);
      return response.data;
    } catch (err) {
      if (err?.response?.status === 404) {
        showMessage(MSG010);
      } else {
        let errorMessage =
          err?.response?.data !== "" &&
          err?.response?.data?.mensaje &&
          err?.response?.data?.mensaje[0];
        let errorIdDuplicado = errorMessage === MSG011;
        if (errorIdDuplicado) {
          showMessage(errorMessage);
        } else {
          showMessage(MSG010);
        }
      }
      return null;
    }
  };

  const getInitialData = async (idDictamenArg, isActivateLoader = true) => {
    isActivateLoader && setLoading(true);
    const responseDTO = await getDataInit();
    if (idDictamenArg !== null && idDictamenArg !== "") {
      const responseInfoDictamen = await onGetDictamenData(
        idDictamenArg,
        responseDTO
      );
      onGetResumenConsolidado(idDictamenArg);
      onUpdateGeneralDictamenData(
        idDictamenArg,
        responseInfoDictamen,
        isEditableTable
      );
    }
    isActivateLoader && setLoading(false);
    return;
  };

  useEffect(() => {
    if (onReloadDictamenInfo === null)
      setOnReloadDictamenInfo(() => getInitialData);
    getInitialData(idDictamen);
    const setCatalogs = async (set, url) => {
      try {
        const responseCatalog = await onGetService(url);
        if (responseCatalog !== null) set(responseCatalog);
      } catch (err) {
        setLoading(false);
      }
    };
    if (idDictamen !== null && idContrato !== null) {
      setCatalogs(setMonthCatalog, "/admin-devengados/periodo-meses");
      setCatalogs(setYearsCatalog, "/admin-devengados/periodo-anios");
      setCatalogs(setIvaCatCatalog, "/admin-devengados/cat-iva/" + idContrato);
    } else {
      setLoading(false);
    }
  }, []);

  const onGetService = async (url) => {
    try {
      const response = await getData(url);
      return response.data;
    } catch (err) {
      if (err?.response?.status === 404) {
        showMessage(MSG012);
      } else {
        let errorMessage =
          err?.response?.data !== "" &&
          err?.response?.data?.mensaje &&
          err?.response?.data?.mensaje[0];
        let errorIdDuplicado = errorMessage === MSG011;
        if (errorIdDuplicado && err?.response?.status !== 403) {
          showMessage(errorMessage);
        } else if (err?.response?.status !== 403) {
          showMessage(MSG012);
        }
      }
      return null;
    }
  };

  const formatearPesos = (numero) => {
    if (numero === "" || numero === null) {
      return numero;
    } else {
      return numero
        .toLocaleString("es-MX", {
          style: "currency",
          currency: "MXN",
          minimumFractionDigits: 2,
          maximumFractionDigits: 2,
        })
        .replace("$", "$ ");
    }
  };

  const onGetResumenConsolidado = async (idDictamenArg) => {
    const response = await onPostService(
      "/admin-devengados/resumen-consolidado",
      {
        idDictamen: idDictamenArg,
      }
    );
    if (response !== null) {
      const { data } = response;
      if (isEmpty(data) === false) {
        setDataTable(
          data.map((item) => {
            const {
              deducciones,
              nombreFase,
              fase,
              ieps,
              iva,
              otrosImpuestos,
              subTotal,
              total,
              totalPesos,
            } = item;
            return {
              fase,
              nombreFase,
              subtotal: formatearPesos(subTotal),
              deducciones: formatearPesos(deducciones),
              ieps: formatearPesos(ieps),
              iva: formatearPesos(iva),
              impuestos: formatearPesos(otrosImpuestos),
              total: formatearPesos(total),
              totalPesos: formatearPesos(totalPesos),
            };
          })
        );
      }
    }
  };

  const getProveedores = (proveedores = null, id) => {
    let textProvider = "";
    if (proveedor && proveedor.nombreProveedor)
      textProvider = proveedores.filter((item) => item.idProveedor === id)[0]
        .nombreProveedor;
    return textProvider;
  };

  const getInitialStatus = async (idDictamenArg, idEstatusDictamen) => {
    if (idDictamenArg === null || idDictamenArg === "") {
      const statusResponse = await onGetService(
        `/admin-devengados/estatus-dictamen-inicial`
      );

      if (statusResponse !== null) {
        const { nombre } = statusResponse;
        formRef.current.values.estatus = nombre;
        if (nombre === "Dictaminado") {
          setDictaminadoStatusEditable(false);
        } else {
          setDictaminadoStatusEditable(true);
        }
        if (
          nombre === "Cancelado" ||
          nombre === "Dictaminado" ||
          nombre === "Proforma" ||
          nombre === "Solicitud de pago" ||
          nombre === "Facturado" ||
          nombre === "Pagado"
        ) {
          setEditableDuplicateButton(true);
          setReadOnly(true);
          setIsEditable(false);
        } else {
          setIsEditable(true);
        }
      }
    } else {
      const response = await onGetService(`/admin-devengados/estatus-dictamen`);
      if (isEmpty(response) === false && response !== null)
        formRef.current.values.estatus = response.filter(
          (item) => item.primaryKey == idEstatusDictamen
        )[0].nombre;
      if (formRef.current.values.estatus === "Dictaminado") {
        setDictaminadoStatusEditable(false);
      } else {
        setDictaminadoStatusEditable(true);
      }
      if (
        formRef.current.values.estatus === "Cancelado" ||
        formRef.current.values.estatus === "Dictaminado" ||
        formRef.current.values.estatus === "Proforma" ||
        formRef.current.values.estatus === "Pagado" ||
        formRef.current.values.estatus === "Facturado" ||
        formRef.current.values.estatus === "Solicitud de pago"
      ) {
        setEditableDuplicateButton(true);
        setReadOnly(true);
        setIsEditable(false);
      } else {
        setIsEditable(true);
      }
    }
    return null;
  };

  const onGetUltimaModicacion = (ultimaModificacionArg) => {
    if (ultimaModificacionArg !== null) {
      setUltimaModificacion(ultimaModificacionArg);
    }
    return null;
  };

  const onGetDictamenData = async (idDictamenArg, responseDTO) => {
    const responseDictamen = await onPostService(
      "/admin-devengados/obtener-dictamenes-id",
      {
        idDictamen: idDictamenArg,
      }
    );
    if (responseDictamen !== null && isEmpty(responseDictamen) === false) {
      const {
        periodoInicio,
        periodoFin,
        idEstatusDictamen,
        periodoMes,
        periodoAnio,
        idIva,
        tipoCambioReferencial,
        descripcion,
        ultimaModificacion,
        idProovedor,
      } = responseDictamen.data;
      formRef.current.values.fechaInicio =
        moment(periodoInicio).format("YYYY-MM-DD");
      formRef.current.values.fechaFin = moment(periodoFin).format("YYYY-MM-DD");
      formRef.current.values.mes = periodoMes;
      formRef.current.values.año = periodoAnio;
      formRef.current.values.iva = idIva;
      formRef.current.values.tipoCambio = tipoCambioReferencial;
      formRef.current.values.descripcion = descripcion;
      await getInitialStatus(idDictamenArg, idEstatusDictamen);
      onGetUltimaModicacion(ultimaModificacion);
      const response = await onPostService(
        "/admin-devengados/validarExisteAnteriorSiguiente",
        {
          idDictamen: idDictamenArg,
        }
      );
      const { proveedores } = responseDTO;
      formRef.current.values.proveedor = getProveedores(
        proveedores,
        parseInt(idProovedor, 10)
      );
      setDictamenInfo(null);
      setDictamenData({
        ...responseDictamen.data,
        indexDictamen: response.data,
      });
    }
    return responseDictamen?.data;
  };

  const onGetTipoReferencial = async (idContratoArg) => {
    const response = await onGetService(
      `/admin-devengados/validar-tipo-cambio/${idContratoArg}`
    );
    if (response !== null) setReadOnlyTipoDeCambio(response.data);
    return response;
  };

  const getDataInit = async () => {
    let responseDTO = {};
    await onGetTipoReferencial(idContrato);
    const contratoDtoResponse = await onGetService(
      `/admin-devengados/obtener-contratoDto/${idContrato}`
    );
    if (idDictamen === "" || idDictamen === null) {
      const responseIva = await getData(
        "/admin-devengados/validar-iva/" + idContrato
      );
      const { data } = responseIva;
      formRef.current.values.iva = data !== null ? data : "";
    }
    if (contratoDtoResponse !== null) {
      const { data } = contratoDtoResponse;
      setGeneralData({ ...data });
      responseDTO = { ...data };
      const { nombreCorto, numeroContrato, fecha_inicio, fecha_termino } = data;
      setFechasContrato(
        `Inicio ${moment(fecha_inicio).format("DD/MM/YYYY")} - Fin ${moment(
          fecha_termino
        ).format("DD/MM/YYYY")}`
      );
      formRef.current.values.nombreCortoContrato = nombreCorto;
      formRef.current.values.numeroContrato = numeroContrato;
      if (idDictamen === "") formRef.current.values.proveedor = proveedor;
    }
    return responseDTO;
  };

  const onGetRangeDate = (date) => {
    // Define el mes y año en el formato "enero 2024"
    const mesYano = date;

    // Convierte el mes y año a un objeto Moment.js
    const primerDiaMes = moment(mesYano, "MMMM YYYY").startOf("month");
    const ultimoDiaMes = moment(mesYano, "MMMM YYYY").endOf("month");

    // Obtén las fechas en el formato deseado
    const primerDia = primerDiaMes.format("YYYY-MM-DD");
    const ultimoDia = ultimoDiaMes.format("YYYY-MM-DD");

    return {
      firstDay: primerDia,
      lastDay: ultimoDia,
    };
  };

  const onRangeDates = (initialDate, endDate, initialRange, endRange) => {
    const initialDateControl = moment(initialDate);
    const endDateControl = moment(endDate);
    const initialDateRange = moment(initialRange);
    const endDateRange = moment(endRange);
    return (
      initialDateControl.isBefore(endDateRange) &&
      endDateControl.isAfter(initialDateRange)
    );
  };

  const compararFechas = (fecha, init, end) => {
    let isEqual = true;
    // Define las fechas de inicio y fin
    const inicio = moment(init, "YYYY-MM-DD");
    const fin = moment(end, "YYYY-MM-DD");

    // Convierte la fecha ingresada a un objeto Moment.js
    const fechaIngresada = moment(fecha, "YYYY-MM-DD");

    // Realiza la comparación
    if (fechaIngresada.isBetween(inicio, fin, undefined, "[]")) {
      isEqual = true;
    } else {
      isEqual = false;
    }
    return isEqual;
  };

  const getErrorClassName = (errorProp, propValue) => {
    return errorProp === null
      ? "is-valid"
      : errorProp !== "" ||
        (formRef.touched &&
          formRef.errors &&
          formRef.touched[propValue] &&
          formRef.errors[propValue])
        ? "is-invalid"
        : "";
  };

  const getHelperText = (errorProp, propValue) => {
    return errorProp !== ""
      ? errorProp
      : formRef.touched && formRef.touched[propValue]
        ? formRef.errors && formRef.errors[propValue]
        : "";
  };

  const DuplicarDictamen = ({ onSelected }) => {
    const [isOpenModal, setIsOpenModal] = useState(false);
    const [arrayItems, setArrayItems] = useState([
      {
        key: 1,
        textKey: "Registrar servicios dictaminados",
        selected: false,
      },
      {
        key: 2,
        textKey: "Penas contractuales",
        selected: false,
      },
      {
        key: 3,
        textKey: "Penas convencionales",
        selected: false,
      },
      {
        key: 4,
        textKey: "Deducciones",
        selected: false,
      },
    ]);
    const selectItem = (id) => {
      const checkArray = [...arrayItems];
      setArrayItems(
        checkArray.map((obj) => {
          const item = { ...obj };
          if (item.key === id) {
            item.selected = !item.selected;
          }
          return item;
        })
      );
    };

    useEffect(() => {
      if (
        isEmpty(dictamenData) === false &&
        isNil(dictamenData) === false &&
        formRef.current.values.nombreCortoContrato !== "" &&
        dictamenInfo === null
      ) {
        setDictamenInfo({
          nombreCortoContrato: formRef.current.values.nombreCortoContrato,
          IDProveedor: dictamenData.idProovedor,
        });
      }
    }, [dictamenData, formRef.current, dictamenInfo]);

    const onDuplicateDictamen = async () => {
      if (
        onClearPenasContractuales !== null &&
        arrayItems[1].selected === false
      )
        onClearPenasContractuales(idDictamen);

      if (
        onClearPenasConvencionales !== null &&
        arrayItems[2].selected === false
      )
        onClearPenasConvencionales(idDictamen);

      if (onClearDeducciones !== null && arrayItems[3].selected === false)
        onClearDeducciones(idDictamen);

      setIdTemporalDictamen(idDictamen);
      setIdTemporalDictamenVisual(idDictamenVisual);
      formRef.current.values.estatus = "";
      onUpdateGeneralDictamenData(
        idDictamen,
        {
          idContrato: idContrato,
          idProveedor: dictamenData.idProovedor,
          editable: false,
        },
        undefined,
        true
      );
      const showSections = { ...SECCIONES_INICIAL };
      showSections.Deducciones = false;
      showSections.Factura = false;
      showSections.Facturas = false;
      showSections.GestionDocumental = false;
      showSections.NotaCredito = false;
      showSections.PenasContractuales = false;
      showSections.PenasConvencionales = false;
      showSections.Proforma = false;
      showSections.RegistroServiciosDictaminados = false;
      showSections.SolicitudPago = false;
      showSections.SoporteDocumentalDictamen = false;
      await setShowSecciones({ ...showSections });

      /*
      showSections.Deducciones = arrayItems[3].selected;
      showSections.Factura = false;
      showSections.Facturas = false;
      showSections.GestionDocumental = false;
      showSections.NotaCredito = false;
      showSections.PenasContractuales = arrayItems[1].selected;
      showSections.PenasConvencionales = arrayItems[2].selected;
      showSections.Proforma = false;
      showSections.RegistroServiciosDictaminados = arrayItems[0].selected;
      showSections.SolicitudPago = false;
      showSections.SoporteDocumentalDictamen = false;
      await setShowSecciones({ ...showSections });
*/

      setIsActiveDuplicated(true);
      onSelected(arrayItems);
      setEditableDuplicateButton(false);
      setReadOnly(false);
      const secciones = { ...SECCIONES_INICIAL };
      secciones.Deducciones = arrayItems[3].selected === false;
      secciones.Factura = true;
      secciones.Facturas = true;
      secciones.GestionDocumental = true;
      secciones.NotaCredito = true;
      secciones.PenasContractuales = arrayItems[1].selected === false;
      secciones.PenasConvencionales = arrayItems[2].selected === false;
      secciones.Proforma = true;
      secciones.RegistroServiciosDictaminados =
        arrayItems[0].selected === false;
      secciones.SolicitudPago = true;
      secciones.SoporteDocumentalDictamen = true;
      setSeccionesInactivas({ ...secciones });
      setIsEditable(false);
    };
    return (
      <>
        <Tooltip placement="top" text={"Duplicar dictamen"}>
          <span>
            <IconButton
              type="twoFiles"
              onClick={() => setIsOpenModal(true)}
              disabled={
                generalEstatus === "Pagado"
                  ? true
                  : generalEstatus === "Cancelado" ||
                    generalEstatus === "Proforma" ||
                    generalEstatus === "Solicitud de pago" ||
                    generalEstatus === "Facturado" ||
                    generalEstatus === "Dictaminado"
                    ? !editableDuplicateButton
                    : !editableDuplicateButton
              }
            />
          </span>
        </Tooltip>

        {isOpenModal && (
          <BasicModal
            show={isOpenModal}
            onHide={() => {
              setIsOpenModal(false);
            }}
            size="md"
            title="Mensaje"
            denyText="Cancelar"
            handleDeny={() => {
              setIsOpenModal(false);
            }}
            approveText={"Aceptar"}
            handleApprove={onDuplicateDictamen}
          >
            {"Secciones a duplicar"}
            {arrayItems.map((item) => (
              <div
                key={item.key}
                style={{ display: "flex" }}
                className={`flex flex-wrap p-2 align-items-center gap-3 ${item.selected ? "item-selected" : "item-assign"
                  }`}
              >
                <Form.Check
                  type={"checkbox"}
                  id={item.key}
                  style={{ cursor: "pointer" }}
                  value={item.selected}
                  onClick={() => {
                    selectItem(item.key);
                  }}
                />

                <div className="flex-1 flex flex-column gap-2">
                  <span>{item.textKey}</span>
                </div>
              </div>
            ))}
            <br />
          </BasicModal>
        )}
      </>
    );
  };

  const CancelarDictamen = ({ status }) => {
    const onHandleSaveCancelDictamen = async (descripcion) => {
      setLoading(true);
      await onPutService("/admin-devengados/cancelar-estatus", {
        dictamenId: {
          idDictamen,
        },
        descripcion,
      });
      getInitialData(idDictamen);
      setEditableDuplicateButton(false);
      const secciones = { ...showSecciones };
      secciones.Deducciones = false;
      secciones.Factura = false;
      secciones.Facturas = false;
      secciones.GestionDocumental = false;
      secciones.NotaCredito = false;
      secciones.PenasContractuales = false;
      secciones.PenasConvencionales = false;
      secciones.Proforma = false;
      secciones.RegistroServiciosDictaminados = false;
      secciones.SolicitudPago = false;
      secciones.SoporteDocumentalDictamen = false;
      setShowSecciones({ ...secciones });
      setIsOpenCommentModal(false);
    };

    return (
      <>
        <div className="text-start">
          <Tooltip placement="top" text={"Cancelar dictamen"}>
            <span>
              <IconButton
                type="cancel"
                onClick={async () => {
                  setLoading(true);
                  const response = await onPostService(
                    "/admin-devengados/validar-facturas-notas-asociadas",
                    {
                      idDictamen,
                    }
                  );
                  const isValid = response;
                  if (isValid === false) {
                    setIsOpenModal(true);
                  } else {
                    setIsOpenModalErorDate(true);
                    setMessageErrorDate(
                      "Debe cancelar las facturas y/o notas de crédito."
                    );
                  }
                  setLoading(false);
                }}
                disabled={
                  // Evidencia de regla https://qualtopgroup.atlassian.net/browse/ID8309-2672
                  isEditableTable === false
                    ? true
                    : idDictamen !== "" &&
                      isEditableTable &&
                      generalEstatus !== "Inicial" &&
                      generalEstatus !== "Cancelado"
                      ? false
                      : idDictamen === "" ||
                        idDictamen === null ||
                        generalEstatus === "Cancelado" ||
                        isNextOrBeforeDictamen
                        ? true
                        : !editableDuplicateButton
                }
              />
            </span>
          </Tooltip>
        </div>{" "}
        {isOpenCommentModal && (
          <Comentarios
            title="Justificación de la cancelación de dictamen"
            placeholder=""
            cancelText="Cancelar"
            defaultValue={commentValue}
            show={isOpenCommentModal}
            comentarios={[]}
            handleCancel={() => {
              setIsOpenCommentModal(false);
            }}
            handleSave={onHandleSaveCancelDictamen}
          />
        )}
        {isOpenModal && (
          <BasicModal
            size="md"
            show={isOpenModal}
            onHide={() => {
              setIsOpenModal(false);
            }}
            title="Mensaje"
            denyText="No"
            handleDeny={() => {
              setIsOpenModal(false);
            }}
            approveText={"Si"}
            handleApprove={() => {
              setIsOpenModal(false);
              setIsOpenCommentModal(true);
            }}
          >
            {
              "El dictamen pasará a estatus cancelado. ¿Está seguro de continuar?"
            }
          </BasicModal>
        )}
      </>
    );
  };

  const onGetDisabledBefore = () => {
    let isDisabled = true;
    if (dictamenData.indexDictamen == -1) {
      isDisabled = true;
    } else if (dictamenData.indexDictamen == 2) {
      isDisabled = false;
    } else if (dictamenData.indexDictamen == 1) {
      isDisabled = true;
    } else if (dictamenData.indexDictamen == 0) {
      isDisabled = false;
    }
    return isDisabled;
  };

  const onGetDisabledAfter = () => {
    let isDisabled = true;
    if (dictamenData.indexDictamen == -1) {
      isDisabled = true;
    } else if (dictamenData.indexDictamen == 2) {
      isDisabled = false;
    } else if (dictamenData.indexDictamen == 1) {
      isDisabled = false;
    } else if (dictamenData.indexDictamen == 0) {
      isDisabled = true;
    }
    return isDisabled;
  };

  const onClickAfterDictamen = async () => {
    if (onGetDisabledAfter()) return null;
    try {
      setLoading(true);
      const response = await onPostService("/admin-devengados/siguiente", {
        idDictamen,
      });
      onHideAllSection();
      const { data } = response;
      setIdDictamen(data[0].idDictamen);
      setIdDictamenVisual(data[0].idDictamenVisual);
      await getDataInit();
      const responseInfoDictamen = await onGetDictamenData(
        data[0].idDictamen,
        generalData
      );
      if (
        responseInfoDictamen !== null &&
        isEmpty(responseInfoDictamen) === false
      ) {
        setIsSelectedBeforeOrAfterButton(true);
        setIsNextOrBeforeDictamen(true);
        setEditableDuplicateButton(true);
        onGetResumenConsolidado(data[0].idDictamen);
        onUpdateGeneralDictamenData(data[0].idDictamen, responseInfoDictamen);
      }
    } catch (err) { }
    setLoading(false);
    setReadOnly(true);
  };

  const onClickBeforeDictamen = async () => {
    if (onGetDisabledBefore()) return null;
    try {
      setLoading(true);
      const response = await onPostService("/admin-devengados/anterior", {
        idDictamen,
      });
      onHideAllSection();
      const { data } = response;
      setIdDictamen(data[0].idDictamen);
      setIdDictamenVisual(data[0].idDictamenVisual);
      await getDataInit();

      const responseInfoDictamen = await onGetDictamenData(
        data[0].idDictamen,
        generalData
      );
      if (
        responseInfoDictamen !== null &&
        isEmpty(responseInfoDictamen) === false
      ) {
        setIsSelectedBeforeOrAfterButton(true);
        setIsNextOrBeforeDictamen(true);
        setEditableDuplicateButton(true);
        onGetResumenConsolidado(data[0].idDictamen);
        onUpdateGeneralDictamenData(data[0].idDictamen, responseInfoDictamen);
      }
    } catch (err) { }
    setReadOnly(true);
    setLoading(false);
  };
  return (
    <>
      <Row>
        <Col md={6} className="text-start">
          <Authorization process={"CON_SERV_ADMIN_DICT"}>
            <Tooltip placement="top" text={"Dictamen anterior"}>
              <span
                disabled={
                  isEditableTable === false
                    ? false
                    : (generalEstatus === "Cancelado" ||
                      generalEstatus === "Proforma" ||
                      generalEstatus === "Pagado" ||
                      generalEstatus === "Facturado" ||
                      generalEstatus === "Solicitud de pago" ||
                      generalEstatus === "Dictaminado") &&
                      isActiveDuplicated === false
                      ? false
                      : !editableDuplicateButton
                }
                onClick={() => {
                  if (onGetDisabledBefore()) {
                    setIsOpenModalErorDate(true);
                    setMessageErrorDate(MSG007);
                  }
                }}
              >
                <IconButton
                  type="arrowLeft"
                  disabled={
                    isEditableTable === false
                      ? false
                      : (generalEstatus === "Cancelado" ||
                        generalEstatus === "Proforma" ||
                        generalEstatus === "Pagado" ||
                        generalEstatus === "Facturado" ||
                        generalEstatus === "Solicitud de pago" ||
                        generalEstatus === "Dictaminado") &&
                        isActiveDuplicated === false
                        ? false
                        : editableDuplicateButton === false ||
                          idDictamen === "" ||
                          idDictamen === null
                          ? true
                          : onGetDisabledBefore()
                            ? onGetDisabledBefore()
                            : onGetDisabledBefore()
                  }
                  onClick={onClickBeforeDictamen}
                />
              </span>
            </Tooltip>
          </Authorization>
        </Col>
        <Col md={6} className="text-end">
          <Authorization process={"CON_SERV_ADMIN_DICT"}>
            <Tooltip placement="top" text={"Dictamen posterior"}>
              <span
                disabled={
                  isEditableTable === false
                    ? false
                    : (generalEstatus === "Cancelado" ||
                      generalEstatus === "Proforma" ||
                      generalEstatus === "Pagado" ||
                      generalEstatus === "Facturado" ||
                      generalEstatus === "Solicitud de pago" ||
                      generalEstatus === "Dictaminado") &&
                      isActiveDuplicated === false
                      ? false
                      : !editableDuplicateButton
                }
                onClick={() => {
                  if (onGetDisabledAfter()) {
                    setIsOpenModalErorDate(true);
                    setMessageErrorDate(MSG007);
                  }
                }}
              >
                <IconButton
                  type="arrowRight"
                  disabled={
                    isEditableTable === false
                      ? false
                      : (generalEstatus === "Cancelado" ||
                        generalEstatus === "Proforma" ||
                        generalEstatus === "Pagado" ||
                        generalEstatus === "Facturado" ||
                        generalEstatus === "Solicitud de pago" ||
                        generalEstatus === "Dictaminado") &&
                        isActiveDuplicated === false
                        ? false
                        : editableDuplicateButton === false ||
                          idDictamen === "" ||
                          idDictamen === null
                          ? true
                          : onGetDisabledAfter()
                            ? onGetDisabledAfter()
                            : onGetDisabledAfter()
                  }
                  onClick={onClickAfterDictamen}
                />
              </span>
            </Tooltip>
          </Authorization>
        </Col>
      </Row>
      <Formik
        innerRef={(f) => (formRef.current = f)}
        initialValues={valoresIniciales}
        enableReinitialize
        validationSchema={esquema}
        validateOnMount={true}
        onChange={() => { }}
        onSubmit={(values, { resetForm }) =>
          () => { }}
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          resetForm,
          values,
          errors,
          touched,
          isValid,
        }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            {loading && <Loader />}
            <Row>
              <Col md={3}>
                <LabelValue
                  label="Id:"
                  value={isActiveDuplicated ? "" : idDictamenVisual}
                />
              </Col>

              <Col md={1}>
                <Authorization process={"CON_SERV_ADMIN_DICT"}>
                  <DuplicarDictamen
                    onSelected={(items) =>
                      setSelectedToDuplicateSections(items)
                    }
                  />
                </Authorization>
              </Col>

              <Col md={4}>
                <LabelValue
                  label="Nombre corto del contrato:"
                  value={values.nombreCortoContrato}
                />
              </Col>

              <Col md={4}>
                <LabelValue
                  label="Número de contrato:"
                  value={values.numeroContrato}
                />
              </Col>
            </Row>

            <Row>
              <Col md={4}>
                <LabelValue label="Proveedor:" value={values.proveedor} />
              </Col>

              <Col md={4} />

              <Col md={4}>
                <div style={{ display: "flex" }}>
                  <p style={{ paddingRight: "0px", width: "auto" }}>
                    <LabelValue
                      label="Estatus:"
                      value={isActiveDuplicated ? "" : values.estatus}
                    />
                  </p>
                  <p md={7} style={{ paddingLeft: "0px" }}>
                    <Authorization process={"CON_SERV_DICT_STA_CANCEL"}>
                      <CancelarDictamen status={values.estatus} />
                    </Authorization>
                  </p>
                </div>
              </Col>
            </Row>

            <Row>
              <Col md={4}>
                <TextFieldDate
                  label={"Periodo de inicio*:"}
                  name="fechaInicio"
                  disabled={values.estatus === "Cancelado" ? true : readOnly}
                  value={values.fechaInicio}
                  onChange={(e) => {
                    handleChange(e);
                    setErrorPeriodoInicio(null);
                  }}
                  onBlur={handleBlur}
                  className={getErrorClassName(
                    errorPeriodoInicio,
                    "fechaInicio"
                  )}
                  helperText={getHelperText(errorPeriodoInicio, "fechaInicio")}
                  minDate={
                    generalData.fecha_inicio !== ""
                      ? moment(generalData.fecha_inicio, "YYYY/MM/DD")
                        .format("YYYY-MM-DD")
                      : null
                  }
                  maxDate={
                    values.fechaFin !== ""
                      ? moment(values.fechaFin, "YYYY/MM/DD")
                        .subtract(1, "days")
                        .format("YYYY-MM-DD")
                      : null // values.fechaInicio
                  }
                />
              </Col>
              <Col md={4}>
                <TextFieldDate
                  label={"Periodo fin*:"}
                  name="fechaFin"
                  disabled={values.estatus === "Cancelado" ? true : readOnly}
                  value={values.fechaFin}
                  onChange={(e) => {
                    handleChange(e);
                    setErrorPeriodoFin(null);
                  }}
                  className={getErrorClassName(errorPeriodoFin, "fechaFin")}
                  helperText={getHelperText(errorPeriodoFin, "fechaFin")}
                  minDate={
                    values.fechaInicio !== ""
                      ? moment(values.fechaInicio, "YYYY/MM/DD")
                        .add(1, "days")
                        .format("YYYY-MM-DD")
                      : values.fechaInicio
                  }
                  maxDate={null}
                />
              </Col>

              <Col md={4}>
                <label htmlFor={""} className="form-label">
                  {"Periodo de control*:"}
                </label>
                <Row>
                  <Col md={6}>
                    <Select
                      disabled={
                        values.estatus === "Cancelado" ? true : readOnly
                      }
                      name={"mes"}
                      options={monthCatalog}
                      keyValue={"primaryKey"}
                      keyTextValue={"nombre"}
                      readOnly={false}
                      value={values.mes}
                      onChange={(e) => {
                        handleChange(e);
                        setErrorPeriodoControlMes(null);
                        if (
                          getHelperText(errorPeriodoControlMes, "mes") !== "" &&
                          getHelperText(errorPeriodoControlMes, "mes") !==
                          undefined
                        ) {
                          onValidateRangeDates(
                            values["fechaInicio"],
                            values["fechaFin"],
                            e.target.value,
                            values["año"]
                          );
                        }
                      }}
                      className={getErrorClassName(
                        errorPeriodoControlMes,
                        "mes"
                      )}
                      helperText={getHelperText(errorPeriodoControlMes, "mes")}
                      defaultOptionText={selectPlaceholder}
                    />
                  </Col>
                  <Col md={6}>
                    <Select
                      name={"año"}
                      options={yearsCatalog}
                      keyValue={"primaryKey"}
                      keyTextValue={"nombre"}
                      readOnly={false}
                      disabled={
                        values.estatus === "Cancelado" ? true : readOnly
                      }
                      value={values.año}
                      onChange={(e) => {
                        handleChange(e);
                        setErrorPeriodoControlAnho(null);
                        if (
                          getHelperText(errorPeriodoControlAnho, "año") !==
                          "" &&
                          getHelperText(errorPeriodoControlAnho, "año") !==
                          undefined
                        ) {
                          onValidateRangeDates(
                            values["fechaInicio"],
                            values["fechaFin"],
                            values["mes"],
                            e.target.value
                          );
                        }
                      }}
                      className={getErrorClassName(
                        errorPeriodoControlAnho,
                        "año"
                      )}
                      helperText={getHelperText(errorPeriodoControlAnho, "año")}
                      defaultOptionText={selectPlaceholder}
                    />
                  </Col>
                </Row>
              </Col>
            </Row>

            <Row>
              <Col md={4}>
                <Select
                  name={"iva"}
                  label={"IVA*:"}
                  options={ivaCatCatalog}
                  keyValue={"primaryKey"}
                  keyTextValue={"porcentaje"}
                  readOnly={false}
                  disabled={values.estatus === "Cancelado" ? true : readOnly}
                  value={values.iva}
                  onChange={handleChange}
                  className={
                    touched.iva && (errors.iva ? "is-invalid" : "is-valid")
                  }
                  helperText={touched.iva ? errors.iva : ""}
                  defaultOptionText={selectPlaceholder}
                />
              </Col>

              <Col md={4}>
                {readOnlyTipoDeCambio !== 1 && ( // 1 significa que es MXN
                  <TextFieldWithIconLeft
                    label={"Tipo de cambio referencial*:"}
                    startIcon={faDollarSign}
                    name="tipoCambio"
                    value={values.tipoCambio}
                    disabled={values.estatus === "Cancelado" ? true : readOnly}
                    onChange={(e) => {
                      if (regexp.test(e.target.value)) {
                        handleChange(e);
                        if (errorTipoCambio !== "") {
                          setErrorTipoCambio("");
                        }
                      }
                    }}
                    onBlur={(e) => {
                      if (validateNumber(e.target.value) === false) {
                        setErrorTipoCambio(MSG011);
                      } else {
                        setErrorTipoCambio(null);
                      }
                    }}
                    className={getErrorClassName(errorTipoCambio, "tipoCambio")}
                    helperText={getHelperText(errorTipoCambio, "tipoCambio")}
                  />
                )}
              </Col>
            </Row>

            <Row>
              <Col md={8}>
                <TextField
                  label={"Descripción:"}
                  value={values.descripcion}
                  disabled={values.estatus === "Cancelado" ? true : readOnly}
                  onChange={handleChange}
                  name={"descripcion"}
                  className="input-table"
                />
              </Col>
            </Row>

            <Row>
              <Col md={12} className="text-end">
                <Authorization process={"CON_SERV_DICT_STA_INICIAL"}>
                  {values.estatus !== "Inicial" && (
                    <Button
                      variant="green"
                      disabled={
                        (values.estatus === "Cancelado" || // Solo para administrador del sistema en Cancelado
                          values.estatus === "Proforma" ||
                          values.estatus === "Facturado" ||
                          values.estatus === "Solicitud de pago" ||
                          values.estatus === "Pagado" ||
                          values.estatus === "Proforma" ||
                          values.estatus === "Dictaminado") &&
                          isActiveDuplicated === false
                          ? !isEditableTable
                          : true
                      }
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={async () => {
                        try {
                          setLoading(true);
                          await onPutService(
                            `/admin-devengados/actualizar-estatus-inicial`,
                            {
                              idDictamen,
                            }
                          );
                          await onGetDictamenData(idDictamen, generalData);
                          const responseInfoDictamen = await onGetDictamenData(
                            idDictamen,
                            generalData
                          );
                          onUpdateGeneralDictamenData(
                            idDictamen,
                            responseInfoDictamen,
                            true
                          );
                          const secciones = { ...showSecciones };
                          secciones.Deducciones = false;
                          secciones.Factura = false;
                          secciones.Facturas = false;
                          secciones.GestionDocumental = false;
                          secciones.NotaCredito = false;
                          secciones.PenasContractuales = false;
                          secciones.PenasConvencionales = false;
                          secciones.Proforma = false;
                          secciones.RegistroServiciosDictaminados = false;
                          secciones.SolicitudPago = false;
                          secciones.SoporteDocumentalDictamen = true;
                          setShowSecciones({ ...secciones });
                          setReadOnly(false);
                          setIsEditable(true);
                          setLoading(false);
                        } catch (err) {
                          setIsOpenModalErorDate(true);
                          setMessageErrorDate(
                            "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)."
                          );
                        }
                      }}
                    >
                      Inicial
                    </Button>
                  )}
                </Authorization>
              </Col>
            </Row>

            <Row>
              <Col md={12} className="text-end">
                <Tooltip placement="top" text={"Exportar a Excel"}>
                  <span>
                    <IconButton
                      type="excel"
                      disabled={idDictamen === "" || idDictamen === null}
                      onClick={async () => {
                        setLoading(true);
                        try {
                          let reportResponse = await onPostService(
                            "/admin-devengados/generar-excel",
                            { idDictamen }
                          );
                          await DownloadFileBase64(
                            reportResponse.data,
                            "Resumen consolidado",
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                          );
                          setLoading(false);
                        } catch (err) {
                          setLoading(false);
                          if (err?.response?.status === 404) {
                            showMessage(MSG005);
                          } else {
                            let errorMessage =
                              err?.response?.data !== "" &&
                              err?.response?.data?.mensaje[0];
                            let errorIdDuplicado = errorMessage === MSG011;
                            if (
                              errorIdDuplicado &&
                              err?.response?.status !== 403
                            ) {
                              showMessage(errorMessage);
                            } else if (err?.response?.status !== 403) {
                              showMessage(MSG005);
                            }
                          }
                          return null;
                        }
                      }}
                    />
                  </span>
                </Tooltip>
              </Col>
            </Row>

            <TableComponent dataTable={dataTable} />
            <br />
            {isOpenModalErorDate && (
              <SingleBasicModal
                handleApprove={() => setIsOpenModalErorDate(false)}
                handleDeny={() => setIsOpenModalErorDate(false)}
                approveText={"Aceptar"}
                size="md"
                show={isOpenModalErorDate}
                title={"Mensaje"}
                onHide={() => setIsOpenModalErorDate(false)}
              >
                {messageErrorDate}
              </SingleBasicModal>
            )}

            {isOpenSuccess && (
              <SingleBasicModal
                handleApprove={() => setIsOpenSuccess(false)}
                handleDeny={() => setIsOpenSuccess(false)}
                approveText={"Aceptar"}
                size="md"
                show={isOpenSuccess}
                title={"Mensaje"}
                onHide={() => setIsOpenSuccess(false)}
              >
                {MSG001}
              </SingleBasicModal>
            )}

            <BotonesAcciones
              processType="2"
              process="CON_SERV_ADMIN_DICT"
              isVisible={true}
              editable={
                values.estatus === "Cancelado" && isActiveDuplicated === false
                  ? false
                  : isActiveDuplicated
                    ? true
                    : !readOnly
              } // isActiveDuplicated
              handleFetchProvider={async () => {
                const { isError, isOnlyDateError } = onValidateFields(values);
                if (isError === false) {
                  try {
                    const {
                      fechaInicio,
                      fechaFin,
                      mes,
                      año,
                      iva,
                      tipoCambio,
                      nombreCortoContrato,
                      descripcion,
                    } = formRef.current.values;
                    if (
                      (idDictamen === "" && isActiveDuplicated === false) ||
                      (idDictamen === null && isActiveDuplicated === false)
                    ) {
                      setLoading(true);
                      const idProveedor = generalData.proveedores.filter(
                        (item) => item.nombreProveedor === proveedor
                      )[0].idProveedor;
                      const responseCreateDictamen = await onPostService(
                        `/admin-devengados/dictamen-guardar/${idContrato}`,
                        {
                          nombreCortoContrato,
                          idProovedor: idProveedor,
                          ultimaModificacion: "",

                          idIva: parseInt(iva, 10),
                          periodoAnio: parseInt(año, 10),
                          idEstatusDictamen: 1,
                          estatus: true,
                          idDictamen: "",
                          idContrato: parseInt(idContrato, 10),
                          periodoInicio: moment(
                            fechaInicio,
                            "YYYY-MM-DD"
                          ).format("YYYY-MM-DDTHH:mm:ss"),
                          periodoFin: moment(fechaFin, "YYYY-MM-DD").format(
                            "YYYY-MM-DDTHH:mm:ss"
                          ),
                          periodoMes: parseInt(mes, 10),
                          mes: parseInt(mes, 10),
                          anio: parseInt(
                            yearsCatalog.filter(
                              (item) => año == item.primaryKey
                            )[0].nombre,
                            10
                          ),
                          tipoCambioReferencial:
                            readOnlyTipoDeCambio === 1 ? 0 : tipoCambio,
                          descripcion,
                          idFaseDictamen: 0,
                        },
                        MSG010
                      );
                      const { idDictamen } = responseCreateDictamen.data;
                      if (idDictamen) {
                        const idDictamenCreated = idDictamen;
                        setIdDictamen(idDictamenCreated);
                        setIdDictamenVisual(
                          responseCreateDictamen.data.idDictamenVisual
                        );
                        const responseDTO = await getDataInit();
                        const responseInfoDictamen = await onGetDictamenData(
                          idDictamenCreated,
                          responseDTO
                        );
                        if (
                          responseInfoDictamen !== null &&
                          isEmpty(responseInfoDictamen) === false
                        ) {
                          onGetResumenConsolidado(idDictamenCreated);
                          onUpdateGeneralDictamenData(
                            idDictamenCreated,
                            responseInfoDictamen,
                            true
                          );
                          onHideAllSection();
                          onClearErrorFields();
                          setEditableDuplicateButton(true);
                          setIsSelectedBeforeOrAfterButton(true);
                        }
                      }
                    } else if (isActiveDuplicated) {
                      setLoading(true);
                      const response = await onPostService(
                        "/admin-devengados/duplicar-dictamen",
                        {
                          dictamenId: idTemporalDictamen,
                          dictamenDto: {
                            idEstatusDictamen: 1,
                            idFaseDictamen: dictamenData.idFaseDictamen,

                            nombreCortoContrato,
                            idProovedor: dictamenData.idProovedor,
                            ultimaModificacion: "",

                            idIva: parseInt(iva, 10),
                            periodoAnio: parseInt(año, 10),
                            estatus: true,
                            idContrato: parseInt(idContrato, 10),
                            periodoInicio: moment(
                              fechaInicio,
                              "YYYY-MM-DD"
                            ).format("YYYY-MM-DDTHH:mm:ss"),
                            periodoFin: moment(fechaFin, "YYYY-MM-DD").format(
                              "YYYY-MM-DDTHH:mm:ss"
                            ),
                            periodoMes: parseInt(mes, 10),
                            mes: parseInt(mes, 10),
                            anio: parseInt(
                              yearsCatalog.filter(
                                (item) => año == item.primaryKey
                              )[0].nombre,
                              10
                            ),
                            tipoCambioReferencial: tipoCambio,
                            descripcion,
                          },
                          registrosDictaminados:
                            selectedToDuplicateSections[0].selected,
                          penasContractuales:
                            selectedToDuplicateSections[1].selected,
                          penasConvencionales:
                            selectedToDuplicateSections[2].selected,
                          deducciones: selectedToDuplicateSections[3].selected,
                        }
                      );
                      await onPutService(
                        "/admin-devengados/actualizar-resumen-consolidado",
                        { idDictamen: response.idDictamen }
                      );

                      setIsEditable(true);

                      setIdTemporalDictamen("");
                      setIdTemporalDictamenVisual("");
                      setIsActiveDuplicated(false);
                      setEditableDuplicateButton(
                        isNil(
                          location?.state?.dictamenState
                            ?.editableDuplicateButton
                        )
                          ? true
                          : false
                      );

                      setIdDictamen(response.idDictamen);
                      setIdDictamenVisual(response.idDictamenVisual);
                      const responseInfoDictamen = await onGetDictamenData(
                        response.idDictamen,
                        generalData
                      );
                      if (
                        responseInfoDictamen !== null &&
                        isEmpty(responseInfoDictamen) === false
                      ) {
                        onGetResumenConsolidado(response.idDictamen);
                        onUpdateGeneralDictamenData(
                          response.idDictamen,
                          { ...responseInfoDictamen, ...{ editable: true } },
                          true,
                          true
                        );
                        onHideAllSection();
                        setEditableDuplicateButton(true);
                        setIsSelectedBeforeOrAfterButton(false);
                      }
                    } else {
                      setLoading(true);
                      await onPutService(`/admin-devengados/editar-dictamen`, {
                        dictamendto: {
                          idDictamen: idDictamen,
                          idEstatusDictamen: dictamenData.idEstatusDictamen,
                          idFaseDictamen: dictamenData.idFaseDictamen,

                          nombreCortoContrato,
                          idProovedor: dictamenData.idProovedor,
                          ultimaModificacion: "",

                          idIva: parseInt(iva, 10),
                          periodoAnio: parseInt(año, 10),
                          estatus: true,
                          idContrato: parseInt(idContrato, 10),
                          periodoInicio: moment(
                            fechaInicio,
                            "YYYY-MM-DD"
                          ).format("YYYY-MM-DDTHH:mm:ss"),
                          periodoFin: moment(fechaFin, "YYYY-MM-DD").format(
                            "YYYY-MM-DDTHH:mm:ss"
                          ),
                          periodoMes: parseInt(mes, 10),
                          mes: parseInt(mes, 10),
                          anio: parseInt(
                            yearsCatalog.filter(
                              (item) => año == item.primaryKey
                            )[0].nombre,
                            10
                          ),
                          tipoCambioReferencial: tipoCambio,
                          descripcion,
                        },
                        dictamenId: {
                          idDictamen: idDictamen,
                        },
                      });
                      setEditableDuplicateButton(true);
                      setIsSelectedBeforeOrAfterButton(true);
                      onGetResumenConsolidado(idDictamen);
                      onClearErrorFields();
                    }
                    setIsOpenSuccess(true);
                  } catch (err) {
                    setIsOpenModalErorDate(true);
                    setMessageErrorDate(
                      "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)."
                    );
                  }
                  setLoading(false);
                  setIsActiveDuplicated(false);
                  setIdTemporalDictamen("");
                  setIdTemporalDictamenVisual("");
                  setIsNextOrBeforeDictamen(false);
                } else {
                  isOnlyDateError !== true &&
                    errorToast(MESSAGES.CAMPOS_OBLIGATORIOS);
                }
              }}
              modalTitle={MESSAGES.MSG008}
              handleCancelAddProviders={async () => {
                setLoading(true);
                let idDictamenParam = idDictamen;
                if (isActiveDuplicated) {
                  await getInitialData(
                    idTemporalDictamen,
                    idTemporalDictamenVisual,
                    false
                  );
                  setIdDictamen(idTemporalDictamen);
                  setIdDictamenVisual(idTemporalDictamenVisual);
                  idDictamenParam = idTemporalDictamen;
                  setIdTemporalDictamen("");
                  setIdTemporalDictamenVisual("");
                  setIsActiveDuplicated(false);
                }

                const showSections = { ...SECCIONES_INICIAL };
                showSections.Deducciones = false;
                showSections.Factura = false;
                showSections.Facturas = false;
                showSections.GestionDocumental = false;
                showSections.NotaCredito = false;
                showSections.PenasContractuales = false;
                showSections.PenasConvencionales = false;
                showSections.Proforma = false;
                showSections.RegistroServiciosDictaminados = false;
                showSections.SolicitudPago = false;
                showSections.SoporteDocumentalDictamen = false;
                await setShowSecciones({ ...showSections });

                setIsActiveDuplicated(false);
                setEditableDuplicateButton(
                  isNil(location?.state?.dictamenState?.editableDuplicateButton)
                    ? true
                    : false
                );
                setReadOnly(isEditableTable);
                setIsEditable(true);
                handleResetForm();
                setIsNextOrBeforeDictamen(false);
                setLoading(false);
              }}
            />
          </Form>
        )}
      </Formik>
    </>
  );
};

export default DatosGenerales;
