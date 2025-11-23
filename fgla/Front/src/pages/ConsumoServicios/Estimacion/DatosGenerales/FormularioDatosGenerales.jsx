import React, { useState, useEffect, useRef, useContext } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Form, Row, Col, Button, } from "react-bootstrap";
import LabelValue from "../../../../components/LabelValue";
import Loader from "../../../../components/Loader";
import CancelModal from "../componets/CancelModal";
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
import { BotonesAcciones } from "../componets/BotonesAcciones";
import BasicModal from "../../../../modals/BasicModal";
import Comentarios from "./../componets/Comentarios";
import { DictamenContext } from "../../Dictamen/context";
import moment from "moment";
import "moment/locale/es";
import _, { isEmpty } from "lodash";
import showMessage from "../../../../components/Messages";
import { CREAR_ESTIMACION, CREAR_ESTIMACION as MESSAGES } from "../../../../constants/messages";
import TextArea from "../../../../components/formInputs/TextArea";
import { useToast } from "../../../../hooks/useToast";
import Authorization from "../../../../components/Authorization";

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
    justificacion: "",
    montoEstimado: "0",
    montoEstimadoPesos: "0",
};

const regexp = /^$|^(0|[1-9]\d?)(\.\d{0,4})?$/;

const DatosGenerales = ({ setEstadoInicial, reloadRegistro, setReloadRegistroServicios, idDEstimacionDuplicado, actualizaFecha, setReloadRegistro, setIdDesencriptado, setShowVolumetriaActiva, showVolumetriaActiva, dicRelacionados, setupdateMonto, updateMonto, setIsDuplicado, setVolumetríaActiva, setEstimacionCancelada, volumetriaActiva, setIdEstimacion, setlastModificacion, setFechasContrato, getIdProveedor, setIdContrato }) => {
    const location = useLocation();
    const navigate = useNavigate();
    const [id, setId] = useState("");
    const [dictamenState, setDictamenState] = useState(id || idDEstimacionDuplicado || location?.state?.estimacionState?.idEstimacion);
    const { errorToast } = useToast();
    const idRevertir = location?.state?.estimacionState?.idEstimacion;
    const editable = location?.state?.estimacionState?.editable;
    const [idEstimacion, setIdEstimaciones] = useState(dictamenState || "");
    let idContrato = location?.state?.estimacionState?.idContrato || null;
    idContrato = idContrato === undefined ? "" : idContrato;
    setIdContrato(idContrato);
    let idProveedor = location?.state?.estimacionState?.idProveedor;
    idProveedor = idProveedor === undefined ? "" : idProveedor;
    getIdProveedor(idProveedor)

    const actualizarIdEstimacion = (nuevoId) => {
        setIdEstimacion(nuevoId);
    };

    const {
        isEditable,
        setSeccionesInactivas,
        SECCIONES_INICIAL,
        setUltimaModificacion,
    } = useContext(DictamenContext);
    const [isCancelado, setIsCancelado] = useState(false);
    const formRef = useRef();
    const handleResetForm = async (closeModal) => {
        setIdEstimacionDuplicado(idRevertir)
        setIdEstimacion(idRevertir)
        setId(idRevertir)
        setIdDesencriptado(idRevertir)
        setValue(idRevertir)
        setIdEstimacion(idRevertir)
        setIdEstimaciones(idRevertir);
        setDictamenState(idRevertir);
        setReloadRegistroServicios(true)
    };

    const [readOnlyTipoDeCambio, setReadOnlyTipoDeCambio] = useState(0);
    const [generalData, setGeneralData] = useState({});
    const [duplicated, setDuplicated] = useState(false);
    const [monthCatalog, setMonthCatalog] = useState([]);
    const [yearsCatalog, setYearsCatalog] = useState([]);
    const [ivaCatCatalog, setIvaCatCatalog] = useState([]);
    const [readOnly, setReadOnly] = useState(isEditable);
    const [tipoMoneda, setTipoMoneda] = useState("");
    const [anterior, setAnterior] = useState("");
    const [duplicado, setDuplicado] = useState(false);
    const [errorPeriodoInicio, setErrorPeriodoInicio] = useState("");
    const [errorPeriodoFin, setErrorPeriodoFin] = useState("");
    const [errorPeriodoControlMes, setErrorPeriodoControlMes] = useState("");
    const [errorPeriodoControlAnho, setErrorPeriodoControlAnho] = useState("");
    const [errorTipoCambio, setErrorTipoCambio] = useState("");
    const [idEstimacionDuplicado, setIdEstimacionDuplicado] = useState("");
    const [value, setValue] = useState("");
    const [loading, setLoading] = useState(false);
    const [isDuplicated, setIsDuplicated] = useState(false);
    const [showTipoCambio, setShowTipoCambio] = useState(false);
    const [activate, setActivate] = useState(false);
    const [isCancelData, setIsCancelData] = useState(false);
    const [desbloqueoDuplicado, setDesbloqueoDuplicado] = useState(false);
    const [errorFechas, setErrorFechas] = useState(false);
    const [valoresIniciales, setValoresIniciales] = useState({
        ...VALORES_INICIALES,
        estatus: idEstimacion ? "" : "Inicial",
    });

    const [reload, setReload] = useState(false);
    const [montoEstimadoPesosError, setMontoEstimadoPesosError] = useState("");
    const [fechaInicio, setFechaInicio] = useState("");
    const [fechaFin, setFechaFin] = useState("");
    const [messageErrorDate, setMessageErrorDate] = useState("");
    const [isOpenModalErorDate, setIsOpenModalErorDate] = useState(false);
    const [forceReload, setForceReload] = useState(false);
    const [idEstimacioncode, setIdEstimacioncode] = useState("");
    const [idToChange, setIdToChange] = useState("");
    const onActiveAllSections = () => {
        const secciones = { ...SECCIONES_INICIAL };
        for (let item in secciones) {
            secciones[item] = !secciones[item];
            setSeccionesInactivas({ ...secciones });
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

    const validateNumber = (input, regexPattern) => {
        if (typeof input !== "string") {
            input = String(input);
        }

        const regex = new RegExp(regexPattern);
        return regex.test(input.trim());
    };

    const onUpdateGeneralEstimacionData = (
        values,
        idEstimacion,
        info = {},
        editable = false,
        isClearStatus = false
    ) => {
        const { estatus } = formRef.current.values;
        let estimacionState = {
            idEstimacion: idEstimacion,
            idContrato: values.idContrato,
            idProveedor: values.idProveedor,
            editable,
            estatus: isClearStatus ? "" : estatus,
            tipoCambioReferencial: values.tipoCambioReferencial,
        };

        console.log(estimacionState);
        navigate("/consumoServicios/consumoServicios/estimacion", {
            state: {
                estimacionState,
            },
        });
    };

    const esquema = yup.object({
        fechaInicio: yup.string().required("Dato requerido"),
        fechaFin: yup.string().required("Dato requerido"),
        mes: yup.string().required("Dato requerido"),
        año: yup.string().required("Dato requerido"),
        iva: yup.string().required("Dato requerido"),
        tipoCambio: yup
            .string()
            .when('showTipoCambio', {
                is: true,
                then: yup.string().required('Dato requerido'),
                otherwise: yup.string().notRequired()
            })
    });

    const onValidateFields = (values) => {
        let isError = false;
        let isErrorFechas = false;

        if (values.fechaInicio === "") {
            isError = true;
            isErrorFechas = true;
            setErrorFechas(true);
            setErrorPeriodoInicio("Dato requerido");
        }
        if (values.fechaFin === "") {
            isError = true;
            isErrorFechas = true;
            setErrorFechas(true);
            setErrorPeriodoFin("Dato requerido");
        }
        if (values.mes === "") {
            isError = true;
            isErrorFechas = true;
            setErrorFechas(true);
            setErrorPeriodoControlMes("Dato requerido");
        }
        if (values.año === "") {
            isError = true;
            isErrorFechas = true;
            setErrorFechas(true);
            setErrorPeriodoControlAnho("Dato requerido");
        }
        if (showTipoCambio && values.tipoCambio === "") {
            isError = true;
            isErrorFechas = false;
            setErrorTipoCambio("Dato requerido");
        }

        const hasDateRangeError = onValidateRangeDates(
            formRef.current.values["fechaInicio"],
            formRef.current.values["fechaFin"],
            formRef.current.values["mes"],
            formRef.current.values["año"]
        );

        isError = isError || hasDateRangeError;
        isErrorFechas = isErrorFechas || hasDateRangeError;

        console.log("Is there an error?", isError);
        return { isError, isErrorFechas };
    };


    const reloadDatosGenerales = async () => {
        try {

            await getDataInit();
            setForceReload(!forceReload);

        } catch (error) {

            showMessage("Error al recargar los datos");
        }
    };


    const onValidateRangeDates = (initialDate, endDate, idMonth, idYear) => {
        let isError = false;
        if (!initialDate || !endDate || !idMonth || !idYear) {
            /* errorToast("t njsnjk"); */
            setErrorFechas(true);
            return true;
        }
        if (idMonth !== "" && idYear !== "") {
            const startMonthText = monthCatalog.filter(
                (item) => idMonth == item.primaryKey
            )[0].nombre;
            const startYearText = yearsCatalog.filter(
                (item) => idYear == item.primaryKey
            )[0].nombre;
            const date = startMonthText + " " + startYearText;
            const rangeDates = onGetRangeDate(date, startMonthText, startYearText);

            let isPeriodError =
                compararFechas(initialDate, rangeDates.firstDay, rangeDates.lastDay) ||
                compararFechas(endDate, rangeDates.firstDay, rangeDates.lastDay);

            if (isPeriodError === false && initialDate !== "" && endDate !== "") {
                setIsOpenModalErorDate(true);
                setMessageErrorDate("Dato incorrecto.");
                setErrorPeriodoControlMes("Dato incorrecto.");
                setErrorPeriodoControlAnho("Dato incorrecto.");
                showMessage("El periodo seleccionado es incorrecto.");
                setErrorFechas(true);
                isError = true;
            } else if (errorPeriodoControlMes !== "" && isPeriodError) {
                setErrorPeriodoControlMes(null);
                setErrorPeriodoControlAnho(null);
                setErrorPeriodoInicio(null);
                setErrorPeriodoFin(null);
            }
        }

        const { fecha_inicio, fecha_termino } = generalData;
        const fechaContratoInicio = moment(fecha_inicio).format("YYYY-MM-DD");
        const fechaContratoFin = moment(fecha_termino).format("YYYY-MM-DD");

        if (compararFechas(initialDate, fechaContratoInicio, fechaContratoFin) === false) {
            setErrorPeriodoInicio("Dato incorrecto.");
            showMessage("El periodo seleccionado es incorrecto.");
            setErrorFechas(true);
            isError = true;
        }

        if (compararFechas(endDate, fechaContratoInicio, fechaContratoFin) === false) {
            setErrorPeriodoFin("Dato incorrecto.");
            showMessage("El periodo seleccionado es incorrecto.");
            setErrorFechas(true);
            isError = true;
        }

        return isError;
    };


    const getInitialData = async (idEstimacionArg) => {
    /* setLoading(true) */;
        await getDataInit();

        if (idEstimacionArg !== null && idEstimacionArg !== "") {
            setIdEstimacioncode(idEstimacionArg)
            await onGetDictamenData(idEstimacionArg);
        }

        setLoading(false);
    };

    useEffect(() => {
        const fetchData = async () => {
            setReloadRegistroServicios(false);
            setLoading(true);

            try {
                if (idEstimacion || value) {
                    setReloadRegistro(false);
                    const idTochange = idEstimacion || value;
                    setIdToChange(idTochange)

                    const encodedIdToUse = idEstimacion ? idEstimacion : encodeURIComponent(value);
                    setIdEstimacion(encodedIdToUse);
                    setReload(false)
                    await getInitialData(encodedIdToUse);
                    setForceReload(false);
                } else {
                    formRef.current.setFieldValue("estatus", "Inicial");
                    formRef.current.setFieldValue("fechaInicio", "");
                    formRef.current.setFieldValue("fechaFin", "");
                    formRef.current.setFieldValue("mes", "");
                    formRef.current.setFieldValue("año", "");
                    formRef.current.setFieldValue("iva", "");
                    formRef.current.setFieldValue("tipoCambio", "");
                    formRef.current.setFieldValue("justificacion", "");
                    formRef.current.setFieldValue("montoEstimado", "0");
                    formRef.current.setFieldValue("montoEstimadoPesos", "0");

                }
                if (forceReload) {
                    reloadDatosGenerales();
                    setForceReload(false);
                }

                if (updateMonto) {
                    reloadDatosGenerales();
                    setForceReload(false);
                    setupdateMonto(false)
                }

                if (idContrato) {
                    await getDataInit();
                } else {
                    console.log("No se encontró un idContrato válido");
                }

                if (
                    idEstimacion !== "" &&
                    idEstimacion !== null &&
                    idContrato !== "" &&
                    idContrato !== null &&
                    !isEmpty(generalData.proveedores)
                ) {
                    onActiveAllSections();
                }
            } catch (error) {
                setLoading(false);
            }
            finally {
                setLoading(false);

            }
        };
        fetchData();
    }, [reloadRegistro, reload, isDuplicated, updateMonto, forceReload, volumetriaActiva, showVolumetriaActiva, id, dicRelacionados, actualizaFecha]);



    const onGetService = async (url) => {
        try {
            const response = await getData(url);
            return response.data;
        } catch (err) {

            setIsOpenModalErorDate(true);
            setMessageErrorDate(
                err.message
                    ? err.message
                    : "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."
            );
            return null;
        }
    };

    const getProveedores = (proveedores, idProveedorHeredado) => {
        if (isEmpty(proveedores)) return "";

        const proveedorSeleccionado = proveedores.find(
            (item) => String(item.idProveedor) === String(idProveedorHeredado)
        );
        if (proveedorSeleccionado) {
            return proveedorSeleccionado.nombreProveedor;
        }
        return proveedores[0]?.nombreProveedor || "";
    };

    const onGetDictamenData = async (idEstimacionArg) => {
        /* setLoading(true) */
        if (isDuplicated === false) {
            /* setLoading(true) */
            if (isCancelData === false) {
                /* setLoading(true) */
                try {
                    let dataToUse;
                    if (dicRelacionados && Object.keys(dicRelacionados).length > 0) {
                        dataToUse = dicRelacionados;
                    } else {
                        let estimacionLimpia = encodeURIComponent(idEstimacionArg);
                        const responseDictamen = await getData(`/admin-devengados/obtener-estimacion?id=${estimacionLimpia}`);
                        console.log(responseDictamen);
                        dataToUse = responseDictamen.data;
                    }

                    setValue(dataToUse.idEstimacion);
                    setIdDesencriptado(dataToUse.idEstimacion)
                    setIdEstimacion(dataToUse.idEstimacion);
                    setIsCancelData(false)

                    const {
                        periodoInicio,
                        periodoFin,
                        idPeriodoControlMes,
                        idPeriodoControlAnio,
                        tipoCambioReferencial,
                        justificacion,
                        ultimaModificacion,
                        catEstatusEstimacion,
                        montoEstimado,
                        montoEstimadoPesos,
                        idIva,
                    } = dataToUse;

                    let justificacionValue = "";
                    try {
                        if (justificacion) {
                            const parsedJustificacion = JSON.parse(justificacion);
                            justificacionValue = parsedJustificacion.justificacion || "";
                        }
                    } catch (jsonError) {
                        console.error("Error al analizar el JSON de 'justificacion':", jsonError);
                        justificacionValue = justificacion;
                    }
                    const formattedMontoEstimado = formatWithCommas(montoEstimado);
                    const formattedMontoEstimadoPesos = formatWithCommas(montoEstimadoPesos);

                    formRef.current.setFieldValue("justificacion", justificacionValue);
                    formRef.current.setFieldValue("montoEstimado", formattedMontoEstimado);
                    formRef.current.setFieldValue("montoEstimadoPesos", formattedMontoEstimadoPesos);
                    formRef.current.setFieldValue("fechaInicio", moment(periodoInicio).format("YYYY-MM-DD"));
                    formRef.current.setFieldValue("fechaFin", moment(periodoFin).format("YYYY-MM-DD"));
                    formRef.current.setFieldValue("mes", idPeriodoControlMes);
                    formRef.current.setFieldValue("año", idPeriodoControlAnio);
                    formRef.current.setFieldValue("tipoCambio", tipoCambioReferencial);
                    formRef.current.setFieldValue("iva", idIva);

                    if (volumetriaActiva === true) {
                        formRef.current.setFieldValue("estatus", "Estimado");
                    }

                    if (!catEstatusEstimacion || !catEstatusEstimacion.nombre) {
                        formRef.current.setFieldValue("estatus", "Inicial");
                    } else {
                        formRef.current.setFieldValue("estatus", catEstatusEstimacion.nombre);
                    }

                    if (tipoMoneda && tipoMoneda !== 'MXN') {
                        formRef.current.setFieldValue("tipoCambio", tipoCambioReferencial);
                        setShowTipoCambio(true);
                        setReadOnlyTipoDeCambio(false);
                    } else {
                        setShowTipoCambio(false);
                        formRef.current.setFieldValue("tipoCambio", tipoCambioReferencial);
                    }

                    if (catEstatusEstimacion?.nombre === "Inicial") {
                        setEstimacionCancelada(false);
                        setIsCancelado(false);
                        setEstadoInicial(true);
                        setReadOnly(true);
                    } else {
                        setReadOnly(false);
                    }

                    if (catEstatusEstimacion?.nombre === "Cancelado") {
                        setIsCancelado(true);
                        setReadOnly(true);
                        setEstimacionCancelada(true);
                        setEstadoInicial(false);
                    } else {
                        setReadOnly(false);
                    }

                    if (catEstatusEstimacion?.nombre === "Estimado") {
                        setIsCancelado(false);
                        setEstimacionCancelada(false);
                        setEstadoInicial(false);
                        setVolumetríaActiva(true);
                    }


                    let regex = /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d+/;
                    let fechaHora = ultimaModificacion ? ultimaModificacion.match(regex)?.[0] : null;
                    let nombre = ultimaModificacion ? ultimaModificacion.replace(regex, "").trim() : "";

                    if (fechaHora) {
                        setUltimaModificacion(
                            `${nombre} ${moment(fechaHora).subtract(6, 'hours').format("YYYY/MM/DD HH:mm")}`
                        );
                    } else {
                        setlastModificacion(ultimaModificacion || "");
                        console.log("No se pudo extraer la fecha de la última modificación.");
                    }
                } catch (error) {

                    showMessage('Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).');
                    console.error("Error fetching dictamen data:", error);
                } finally {
                    setLoading(false)
                }
            }

        }
    };


    const onGetTipoReferencial = async (idContratoArg) => {
        try {
            const response = await onGetService(
                `/admin-devengados/validar-tipo-cambio/${idContratoArg}`
            );
            setReadOnlyTipoDeCambio(response.data);

            return response.data;
        } catch (error) {

        }
    };

    const getDataInit = async () => {
        try {
            setLoading(true)
            await onGetTipoReferencial(idContrato);
            const contratoDtoResponse = await onGetService(`/admin-devengados/obtener-contrato-mod/${idContrato}`);
            console.log(contratoDtoResponse.data);

            if (contratoDtoResponse !== null) {
                setGeneralData({ ...contratoDtoResponse.data });
                const {
                    idContratoFormato,
                    nombreCorto,
                    numeroContrato,
                    proveedores,
                    fecha_inicio,
                    fecha_termino,
                    tipoMoneda,
                    tipoCambio,
                    idIva,
                } = contratoDtoResponse.data;

                formRef.current.setFieldValue("idFormateado", idContratoFormato);
                formRef.current.setFieldValue("nombreCortoContrato", nombreCorto);
                formRef.current.setFieldValue("numeroContrato", numeroContrato)

                if (!idEstimacion) {
                    formRef.current.setFieldValue("iva", idIva);
                }


                const proveedorSeleccionado = getProveedores(proveedores, idProveedor);
                formRef.current.setFieldValue("proveedor", proveedorSeleccionado);

                setFechaInicio(fecha_inicio);
                setFechaFin(fecha_termino);
                setFechasContrato(`${moment(fecha_inicio).format("DD/MM/YYYY")} - ${moment(fecha_termino).format("DD/MM/YYYY")}`);
                setTipoMoneda(tipoMoneda);


                if (tipoMoneda && tipoMoneda !== 'MXN') {
                    setShowTipoCambio(true);
                    setReadOnlyTipoDeCambio(false);
                } else {
                    setShowTipoCambio(false);
                    formRef.current.setFieldValue("tipoCambio", "");
                }
            }

            const setCatalogs = async (set, url) => {
                const responseCatalog = await onGetService(url);
                if (responseCatalog !== null) set(responseCatalog);
            };
            setCatalogs(setMonthCatalog, "/admin-devengados/periodo-meses");
            setCatalogs(setYearsCatalog, "/admin-devengados/periodo-anios");
            setCatalogs(setIvaCatCatalog, `/admin-devengados/cat-iva/${idContrato}`);

        } catch (err) {

            setIsOpenModalErorDate(true);
            setMessageErrorDate("Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).");
        }
        return null;
    };


    const onGetRangeDate = (date) => {
        const mesYano = date;
        const primerDiaMes = moment(mesYano, "MMMM YYYY").startOf("month");
        const ultimoDiaMes = moment(mesYano, "MMMM YYYY").endOf("month");
        const primerDia = primerDiaMes.format("YYYY-MM-DD");
        const ultimoDia = ultimoDiaMes.format("YYYY-MM-DD");

        return {
            firstDay: primerDia,
            lastDay: ultimoDia,
        };
    };

    const compararFechas = (fecha, init, end) => {
        let isEqual = true;
        const inicio = moment(init, "YYYY-MM-DD");
        const fin = moment(end, "YYYY-MM-DD");
        const fechaIngresada = moment(fecha, "YYYY-MM-DD");
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
    const getErrorClassCoins = (errorProp, propValue) => {
        return errorProp === null
            ? " "
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

    const DuplicarEstimacion = ({ editable }) => {
        const duplicarEst = async () => {
            try {
                setLoading(true)
                let estimacionLimpia = encodeURIComponent(id);
                const duplicar = await getData(`/admin-devengados/duplicar-estimacion?id=${estimacionLimpia}`);
                setIsDuplicated(true);
                setIsDuplicado(true)
                setDesbloqueoDuplicado(true)
                setDuplicado(duplicar.data.duplicado)
                setAnterior(duplicar.data.anterior)
                actualizarIdEstimacion(duplicar.data.idEstimacion);
                setIdEstimacionDuplicado(duplicar.data.idEstimacion)
                setIdEstimacion(duplicar.data.idEstimacion)
                setId(duplicar.data.idEstimacion)
                setIdDesencriptado(duplicar.data.idEstimacion)
                setValue(duplicar.data.idEstimacion)
                setIdEstimacion(duplicar.data.idEstimacion)
                setIdEstimaciones(duplicar.data.idEstimacion);
                setDictamenState(duplicar.data.idEstimacion);
                const {
                    periodoInicio, periodoFin, idPeriodoControlMes, idPeriodoControlAnio,
                    idIva, tipoCambioReferencial, justificacion, montoEstimado, montoEstimadoPesos
                } = duplicar.data;

                formRef.current.setFieldValue("estatus", "Inicial");
                formRef.current.setFieldValue("fechaInicio", moment(periodoInicio).format("YYYY-MM-DD"));
                formRef.current.setFieldValue("fechaFin", moment(periodoFin).format("YYYY-MM-DD"));
                formRef.current.setFieldValue("mes", idPeriodoControlMes);
                formRef.current.setFieldValue("año", idPeriodoControlAnio);
                formRef.current.setFieldValue("iva", idIva);
                formRef.current.setFieldValue("tipoCambio", tipoCambioReferencial);
                formRef.current.setFieldValue("justificacion", justificacion);
                formRef.current.setFieldValue("montoEstimado", montoEstimado);
                formRef.current.setFieldValue("montoEstimadoPesos", montoEstimadoPesos);

                setVolumetríaActiva(false)
                setLoading(false)
                showMessage(MESSAGES.MSG001);
            } catch (error) {
                if (error?.response?.data?.mensaje?.includes("Ya existe una estimación")) {
                    setLoading(false)
                    showMessage(MESSAGES.MSG005);
                } else {
                    setLoading(false)
                    showMessage(MESSAGES.MSG006);
                }
                console.error("Error duplicando estimación:", error);
            }
        };

        return (

            <>
                {(id || isDuplicated || idEstimacion || activate) && (
                    <Tooltip placement="top" text={"Duplicar estimación"}>
                        <span>
                            <IconButton
                                type="twoFiles"
                                onClick={() => duplicarEst()}
                                disabled={readOnly || !editable || isDuplicated}
                            />
                        </span>
                    </Tooltip>
                )}
            </>


        );
    };

    const CancelarEstimacion = ({ idEstimacion, editable, setIdEstimaciones, onReload, setIsCancelado }) => {
        const [isOpenModal, setIsOpenModal] = useState(false);
        const [isOpenCommentModal, setIsOpenCommentModal] = useState(false);
        const [messageErrorDate, setMessageErrorDate] = useState("");
        const [isOpenModalErorDate, setIsOpenModalErorDate] = useState(false);
        const [justificacion, setJustificacion] = useState("");
        const [loading, setLoading] = useState(false);


        const handleSaveJustificacion = async (justificacion) => {
            const formattedJustificacion = justificacion.replace(/\n/g, ' ');

            try {
                setLoading(true)
                let estimacionLimpia = encodeURIComponent(id || idEstimacioncode);
                const responseCancelEstimacion = await putData(`/admin-devengados/cancelar-estimacion?id=${estimacionLimpia}`, {
                    justificacion: formattedJustificacion,
                });

                setIsDuplicated(false);
                /*         showMessage(MESSAGES.MSG001); */
                setIdEstimaciones(responseCancelEstimacion.data.idEstimacion);

                if (onReload) {
                    onReload();
                }
                setLoading(false)
            } catch (error) {
                console.error(error);
                showMessage(MESSAGES.MSG006);
                setIsCancelado(false);
                setLoading(false)
            } finally {

                setIsOpenCommentModal(false);
                setLoading(false)
            }
        };

        const handleConfirmJustificacion = () => {
            setIsOpenCommentModal(true);
        };


        return (
            <>
                <div className="text-end">
                    {(id || isDuplicated || idEstimacion || activate) && (
                        <Authorization process={"CON_SERV_ESTIM_STA_CANCEL"}>
                            <Tooltip placement="top" text={"Cancelar estimación"} >
                                <span >
                                    <IconButton
                                        type="cancel"
                                        onClick={() => {
                                            setIsOpenModal(true);
                                        }}
                                        disabled={readOnly || !editable || isDuplicated}
                                    />
                                </span>
                            </Tooltip>
                        </Authorization>
                    )}
                </div>


                {isOpenCommentModal && (
                    <Comentarios
                        title="Justificación de la cancelación"
                        placeholder="Escribe la justificación aquí..."
                        cancelText="Cancelar"
                        defaultValue={justificacion}
                        show={isOpenCommentModal}
                        comentarios={[]}
                        handleCancel={() => {
                            setIsOpenCommentModal(false);
                        }}
                        handleSave={(value) => {
                            setIsOpenCommentModal(false);
                            handleSaveJustificacion(value);
                        }}
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
                        approveText={"Sí"}
                        handleApprove={() => {
                            setIsOpenModal(false);
                            handleConfirmJustificacion();
                        }}
                    >
                        {"¿Desea cancelar la estimación?"}
                    </BasicModal>
                )}

                {isOpenModalErorDate && (
                    <BasicModal
                        size="md"
                        show={isOpenModalErorDate}
                        onHide={() => {
                            setIsOpenModalErorDate(false);
                        }}
                        title={messageErrorDate}
                        approveText={"Ok"}
                        handleApprove={() => {
                            setIsOpenModalErorDate(false);
                        }}
                    />
                )}
            </>
        );
    };

    const handleFetchProvider = async (values, setLoading, setIsDuplicado, setReload, setReloadRegistro, setIsDuplicated, setDesbloqueoDuplicado, showMessage, setIdEstimacion, setIdDesencriptado, setlastModificacion, getDataInit, onGetDictamenData, onClearErrorFields, idToChange, idContrato, generalData, yearsCatalog, ivaCatCatalog, value, isDuplicated, idEstimacionDuplicado, duplicado, anterior, actualizarIdEstimacion, setForceReload, setShowVolumetriaActiva, setVolumetríaActiva, setActivate, errorToast) => {
        const { isError, isErrorFechas } = onValidateFields(values);
        if (!isError) {
            const {
                fechaInicio,
                fechaFin,
                mes,
                año,
                iva,
                tipoCambio,
                idFormateado,
                nombreCortoContrato,
                numeroContrato,
                proveedor,
                estatus,
                setIdEstimaciones,
                montoEstimado,
                montoEstimadoPesos,
                justificacion,
            } = values;

            const cleanedMontoEstimado = parseFloat(montoEstimado.toString().replace(/,/g, ''));
            const cleanedMontoEstimadoPesos = parseFloat(montoEstimadoPesos.toString().replace(/,/g, ''));

            if (!idEstimacion && isDuplicated === false) {
                setLoading(true);
                try {
                    const responseCreateDictamen = await postData(`/admin-devengados/crear-estimacion`, {
                        idEstimacion: "",
                        idContrato: idContrato,
                        estatus: generalData.proveedores[0].idProveedor,
                        nombreCortoContrato: generalData.nombreCorto,
                        idEstatusEstimacion: 1,
                        idProveedor: idProveedor,
                        periodoInicio: moment(fechaInicio, 'YYYY-MM-DD').format('YYYY-MM-DDTHH:mm:ss'),
                        periodoFin: moment(fechaFin, 'YYYY-MM-DD').format('YYYY-MM-DDTHH:mm:ss'),
                        montoEstimado: cleanedMontoEstimado,
                        montoEstimadoPesos: cleanedMontoEstimadoPesos,
                        idPeriodoControlMes: parseInt(mes, 10),
                        idPeriodoControlAnio: yearsCatalog.find(item => parseInt(año) === item.primaryKey).primaryKey,
                        idIva: ivaCatCatalog.find(item => parseInt(iva) === item.primaryKey).primaryKey,
                        tipoCambioReferencial: tipoCambio,
                        justificacion,
                    });
                    let values = responseCreateDictamen.data;
                    setForceReload(true);
                    setReload(true);
                    setDesbloqueoDuplicado(false);
                    setReloadRegistro(true);
                    setEstadoInicial(true);
                    setShowVolumetriaActiva(false);
                    setVolumetríaActiva(false);
                    setIsDuplicado(false);
                    setIsDuplicated(false);

                    actualizarIdEstimacion(responseCreateDictamen.data.idEstimacion);
                    setValue(responseCreateDictamen.data.idEstimacion);
                    showMessage(MESSAGES.MSG001, () => {
                        window.location.reload();
                    });
                    setId(responseCreateDictamen.data.idEstimacion);
                    setlastModificacion(responseCreateDictamen.data.ultimaModificacion);
                    setIdEstimacion(responseCreateDictamen.data.idEstimacion);
                    setIdDesencriptado(responseCreateDictamen.data.idEstimacion);
                    onUpdateGeneralEstimacionData(values, responseCreateDictamen.data.idEstimacion, {}, true, true);
                } catch (error) {
                    setLoading(false);
                    const erroresEstimacion = Object.values(CREAR_ESTIMACION);
                    const errorMessage = error?.response?.data?.mensaje[0];
                    setLoading(false);
                    const errorEstimacion = erroresEstimacion.includes(errorMessage);
                    if (errorEstimacion) {
                        showMessage(errorMessage);
                        setLoading(false);
                    } else {
                        showMessage(MESSAGES.MSG006);
                        setLoading(false);
                    }
                }
            } if (idEstimacion && isDuplicated === false) {
                setLoading(true);
                try {
                    const responseUpdateDictamen = await putData(`/admin-devengados/modificar-estimacion`, {
                        idEstimacion: idToChange,
                        idContrato: idContrato,
                        estatus: generalData.proveedores[0].idProveedor,
                        nombreCortoContrato: generalData.nombreCorto,
                        idEstatusEstimacion: 1,
                        idProveedor: idProveedor,
                        periodoInicio: moment(fechaInicio, 'YYYY-MM-DD').format('YYYY-MM-DDTHH:mm:ss'),
                        periodoFin: moment(fechaFin, 'YYYY-MM-DD').format('YYYY-MM-DDTHH:mm:ss'),
                        montoEstimado: cleanedMontoEstimado,
                        montoEstimadoPesos: cleanedMontoEstimadoPesos,
                        idPeriodoControlMes: parseInt(mes, 10),
                        idPeriodoControlAnio: yearsCatalog.find(item => parseInt(año) === item.primaryKey).primaryKey,
                        idIva: ivaCatCatalog.find(item => parseInt(iva) === item.primaryKey).primaryKey,
                        tipoCambioReferencial: tipoCambio,
                        justificacion,
                    });
                    setIsDuplicado(false)
                    setReload(true)
                    setReloadRegistro(true)
                    setIsDuplicated(false)
                    setDesbloqueoDuplicado(false)
                    showMessage(MESSAGES.MSG001);
                    setIdEstimacion(responseUpdateDictamen.data.idEstimacion);
                    setIdDesencriptado(responseUpdateDictamen.data.idEstimacion)
                    setlastModificacion(responseUpdateDictamen.data.ultimaModificacion);
                    await getDataInit();
                    await onGetDictamenData(idEstimacion);
                    onClearErrorFields();
                } catch (error) {
                    const errorMessage = error?.response?.data?.mensaje[0];
                    const erroresEstimacion = Object.values(CREAR_ESTIMACION);
                    if (erroresEstimacion.includes(errorMessage)) {
                        showMessage(errorMessage);
                        setLoading(false);
                    } else {
                        showMessage(MESSAGES.MSG006);
                        setLoading(false);
                    }
                    setLoading(false);
                }
            }
            else if (isDuplicated === true) {
                setLoading(true);
                try {
                    const responseUpdateDictamen = await postData(`/admin-devengados/crear-estimacion`, {
                        idEstimacion: idEstimacionDuplicado,
                        idContrato: idContrato,
                        estatus: generalData.proveedores[0].idProveedor,
                        nombreCortoContrato: generalData.nombreCorto,
                        idEstatusEstimacion: 1,
                        idProveedor: idProveedor,
                        periodoInicio: moment(fechaInicio, 'YYYY-MM-DD').format('YYYY-MM-DDTHH:mm:ss'),
                        periodoFin: moment(fechaFin, 'YYYY-MM-DD').format('YYYY-MM-DDTHH:mm:ss'),
                        montoEstimado: cleanedMontoEstimado,
                        montoEstimadoPesos: cleanedMontoEstimadoPesos,
                        idPeriodoControlMes: parseInt(mes, 10),
                        idPeriodoControlAnio: yearsCatalog.find(item => parseInt(año) === item.primaryKey).primaryKey,
                        idIva: ivaCatCatalog.find(item => parseInt(iva) === item.primaryKey).primaryKey,
                        tipoCambioReferencial: tipoCambio,
                        duplicado: duplicado,
                        anterior: anterior,
                        justificacion,
                    });
                    actualizarIdEstimacion(responseUpdateDictamen.data.idEstimacion);
                    setIdEstimacion(responseUpdateDictamen.data.idEstimacion);
                    setIdDesencriptado(responseUpdateDictamen.data.idEstimacion);
                    setReload(true);
                    setIsDuplicado(false);
                    setId(responseUpdateDictamen.data.idEstimacion);
                    setDesbloqueoDuplicado(false);
                    setForceReload(true);
                    setReloadRegistro(true);
                    setShowVolumetriaActiva(false);
                    setVolumetríaActiva(false);
                    setIsDuplicated(false);
                    setActivate(true);
                    showMessage(MESSAGES.MSG001);
                    setlastModificacion(responseUpdateDictamen.data.ultimaModificacion);
                    await getDataInit();
                    await onGetDictamenData(idEstimacion);
                    onClearErrorFields();
                } catch (error) {
                    setLoading(false);
                    const errorMessage = error?.response?.data?.mensaje[0];
                    const erroresEstimacion = Object.values(CREAR_ESTIMACION);
                    if (erroresEstimacion.includes(errorMessage)) {
                        setLoading(false);
                        showMessage(errorMessage);
                    } else {
                        setLoading(false);
                        showMessage(MESSAGES.MSG006);
                    }
                }
            }
        } else {
            setLoading(false);
            if (isErrorFechas === false) {
                errorToast(MESSAGES.MSG003);
            } else {
                console.log("Error en las fechas")
            }

        }
    };

    const formatWithCommas = (value) => {
        if (value === null || value === undefined || value === "") return value;
        const numberValue = parseFloat(value.toString().replace(/,/g, ''));
        if (isNaN(numberValue)) return value;
        const formattedValue = numberValue.toFixed(2);
        const parts = formattedValue.split('.');
        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        return parts.join('.');
    };

    return (
        <>
            {loading && <Loader zIndex={`${loading ? "10" : "9999"}`} />}
            <Formik
                innerRef={(f) => (formRef.current = f)}
                initialValues={valoresIniciales}
                enableReinitialize={true}
                validationSchema={esquema}
                validateOnMount={true}
                onChange={() => { }}
                onSubmit={(values, { resetForm }) =>
                    () => {

                    }}
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
                        <Row>
                            <Col md={3}>
                                <LabelValue label="Id:" value={value || idEstimacion} />
                            </Col>

                            <Col md={1}>
                                <DuplicarEstimacion editable={editable} />
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
                                <Row>

                                    <Col md={4}>
                                        <LabelValue label="Estatus:" value={volumetriaActiva ? "Estimado" : values.estatus} />
                                    </Col>
                                    <Col md={8}>
                                        <CancelarEstimacion
                                            setIsCancelado={setIsCancelado}
                                            idEstimacion={idEstimacion}
                                            editable={editable}
                                            setIdEstimaciones={setIdEstimaciones}
                                            onReload={reloadDatosGenerales}
                                        />
                                    </Col>
                                </Row>
                            </Col>
                        </Row>
                        <Row>
                            <Col md={4}>
                                <TextFieldDate
                                    label={"Periodo de inicio*:"}
                                    name="fechaInicio"
                                    disabled={readOnly || volumetriaActiva || !editable}
                                    value={values.fechaInicio}
                                    onChange={(e) => {
                                        handleChange(e);
                                        setErrorPeriodoInicio(null);
                                    }}
                                    className={getErrorClassName(
                                        errorPeriodoInicio,
                                        "fechaInicio"
                                    )}
                                    helperText={getHelperText(errorPeriodoInicio, "fechaInicio")}
                                    minDate={null}
                                    maxDate={
                                        values.fechaFin !== ""
                                            ? moment(values.fechaFin, "YYYY/MM/DD")
                                                .subtract(1, "days")
                                                .format("YYYY-MM-DD")
                                            : values.fechaFin
                                    }
                                />
                            </Col>
                            <Col md={4}>
                                <TextFieldDate
                                    label={"Periodo fin*:"}
                                    name="fechaFin"
                                    disabled={readOnly || volumetriaActiva || !editable}
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
                                            disabled={readOnly || volumetriaActiva || !editable}
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
                                            defaultOptionText="Seleccione una opción"
                                        />
                                    </Col>
                                    <Col md={6}>
                                        <Select
                                            name={"año"}
                                            options={yearsCatalog}
                                            keyValue={"primaryKey"}
                                            keyTextValue={"nombre"}
                                            readOnly={false}
                                            disabled={readOnly || volumetriaActiva || !editable}
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
                                            defaultOptionText="Seleccione una opción"
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
                                    disabled={readOnly || volumetriaActiva || !editable}
                                    value={values.iva}
                                    onChange={handleChange}
                                    className={
                                        touched.iva && (errors.iva ? "is-invalid" : "is-valid")
                                    }
                                    helperText={touched.iva ? errors.iva : ""}
                                    defaultOptionText="Seleccione una opción"
                                />
                            </Col>
                            {showTipoCambio && (
                                <Col md={4}>
                                    <TextFieldWithIconLeft
                                        label={"Tipo de cambio referencial*:"}
                                        startIcon={faDollarSign}
                                        name="tipoCambio"
                                        value={values.tipoCambio}
                                        disabled={volumetriaActiva || isCancelado}
                                        onChange={(e) => {
                                            const { value } = e.target;
                                            const regex = /^\d{0,3}(\.\d{0,4})?$/;
                                            if (regex.test(value) || value === "") {
                                                handleChange(e);
                                            }

                                            if (errorTipoCambio !== "") {
                                                setErrorTipoCambio("");
                                            }
                                        }}
                                        onBlur={(e) => {
                                            setErrorTipoCambio(null);
                                        }}
                                        className={getErrorClassName(errorTipoCambio, "tipoCambio")}
                                        helperText={getHelperText(errorTipoCambio, "tipoCambio")}
                                    />
                                </Col>

                            )}

                        </Row>
                        <Row>
                            <Col md={4}>
                                <TextFieldWithIconLeft
                                    label={"Monto estimado total:"}
                                    startIcon={faDollarSign}
                                    name="montoEstimado"
                                    value={formatWithCommas(values.montoEstimado)}
                                    disabled
                                    onChange={(e) => {
                                        let { value } = e.target;

                                        value = value.replace(/,/g, '');
                                        if (/^\d*\.?\d{0,2}$/.test(value) || value === "") {
                                            formRef.current.setFieldValue("montoEstimado", value);
                                        }
                                    }}
                                    onBlur={(e) => {
                                        const { value } = e.target;
                                        const rawValue = value.replace(/,/g, '');
                                        formRef.current.setFieldValue("montoEstimado", rawValue);
                                    }}
                                />
                            </Col>

                            <Col md={4}>
                                <TextFieldWithIconLeft
                                    label={"Monto estimado total en pesos:"}
                                    startIcon={faDollarSign}
                                    disabled
                                    name="montoEstimadoPesos"
                                    value={formatWithCommas(values.montoEstimadoPesos)}
                                    onChange={(e) => {
                                        let { value } = e.target;
                                        value = value.replace(/,/g, '');
                                        if (/^\d{0,20}(\.\d{0,2})?$/.test(value) || value === "") {
                                            formRef.current.setFieldValue("montoEstimadoPesos", value);
                                        }
                                    }}
                                    onBlur={(e) => {
                                        const { value } = e.target;
                                        const rawValue = value.replace(/,/g, '');
                                        formRef.current.setFieldValue("montoEstimadoPesos", rawValue);
                                    }}
                                />
                            </Col>
                        </Row>
                        <Row>
                            {editable && values.estatus === "Cancelado" && (
                                <Col md={8}>
                                    <TextArea
                                        label={"Justificación de la cancelación:"}
                                        value={values.justificacion}
                                        disabled={readOnly}
                                        onChange={handleChange}
                                        name={"justificacion"}
                                        className="input-table"
                                    />
                                </Col>
                            )}
                        </Row>

                        <Row>
                            <Col md={8} className="text-start"></Col>
                            <Col md={4} className="text-end">
                                <BotonesAcciones
                                    desbloqueoDuplicado={desbloqueoDuplicado}
                                    setIsDuplicated={setIsDuplicated}
                                    setIsDuplicado={setIsDuplicado}
                                    isVisible={true}
                                    editable={editable}
                                    idEstimacion={idEstimacion}
                                    idEstimacioncode={idEstimacioncode}
                                    disabled={readOnly}
                                    setIdEstimaciones={setIdEstimaciones}
                                    onReload={reloadDatosGenerales}
                                    isCancelado={isCancelado}
                                    estatus={values.estatus}
                                    setVolumetríaActiva={setVolumetríaActiva}
                                    showVolumetriaActiva={showVolumetriaActiva}
                                    setShowVolumetriaActiva={setShowVolumetriaActiva}
                                    setReload={setReload}
                                    setReloadRegistro={setReloadRegistro}
                                    volumetriaActiva={volumetriaActiva}
                                    handleFetchProvider={() => handleFetchProvider(
                                        values,
                                        setLoading,
                                        setIsDuplicado,
                                        setReload,
                                        setReloadRegistro,
                                        setIsDuplicated,
                                        setDesbloqueoDuplicado,
                                        showMessage,
                                        setIdEstimacion,
                                        setIdDesencriptado,
                                        setlastModificacion,
                                        getDataInit,
                                        onGetDictamenData,
                                        onClearErrorFields,
                                        idToChange,
                                        idContrato,
                                        generalData,
                                        yearsCatalog,
                                        ivaCatCatalog,
                                        value,
                                        isDuplicated,
                                        idEstimacionDuplicado,
                                        duplicado,
                                        anterior,
                                        actualizarIdEstimacion,
                                        setForceReload,
                                        setShowVolumetriaActiva,
                                        setVolumetríaActiva,
                                        setActivate,
                                        errorToast
                                    )}
                                    modalTitle="Se perderá la información capturada ¿Desea continuar?"
                                    handleCancelAddProviders={() => handleResetForm()}
                                />
                            </Col>
                        </Row>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default DatosGenerales;
