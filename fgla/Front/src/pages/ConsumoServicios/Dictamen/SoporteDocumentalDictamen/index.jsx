import React, { useState, useEffect, useRef, useContext } from "react";
import { Formik } from "formik";
import { useLocation } from "react-router-dom";
import { Form, Row, Col } from "react-bootstrap";
import * as yup from "yup";
import moment from "moment";
import "moment/locale/es";
import { isEmpty } from "lodash";
import Loader from "../../../../components/Loader";
import FileField from "../../../../components/formInputs/FileField";
import TextField from "../../../../components/formInputs/TextField";
import TextFieldDate from "../../../../components/formInputs/TextFieldDate";
import { BotonesAcciones } from "../DatosGenerales/BotonesAcciones";
import { DownloadFileBase64 } from "../../../../functions/utils/base64.utils";
import { DictamenContext } from "../context";
import { postData, getData, putDataForm } from "../../../../functions/api";
import showMessage from "../../../../components/Messages";
import { useToast } from "../../../../hooks/useToast";
import { Tooltip } from "../../../../components/Tooltip";
import Authorization from "../../../../components/Authorization";
import { useGetGestionDocumentalDictamenQuery } from "../GestionDocumental/store";

const MSG002 = "Se guardaron los datos de forma correcta.";
const MSG003 =
  "El archivo seleccionado no contiene la extensión .xlsx. Favor de seleccionar un archivo con la extensión correcta.";
const MSG004 =
  "El archivo seleccionado no contiene la extensión .pdf. Favor de seleccionar un archivo con la extensión correcta.";
const MSG011 =
  "El archivo seleccionado no contiene la extensión .zip. Favor de seleccionar un archivo con la extensión correcta.";
const MSG007 =
  "La estructura de la información ingresada es incorrecta. Intente nuevamente.";
const MSG014 = "El dictamen cambió a estatus Dictaminado";
const MSG018 = "Falta información en soporte documental.";
const MSG012 = "Debe seleccionar al menos un servicio.";
const MSG_ERROR_SAT_CLOUD =
  "Se perdió la conexión con Sat Cloud, favor de intentar nuevamente";
const MSG_ERROR_FILE =
  "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).";
const VALORES_INICIALES = {
  detallePenasDeducciones: "",
  entregables: "",
  oficio: "",
  numeroOficio: "",
  fechaSolicitudDictamen: "",
  fechaRecepcionDictamen: "",
};

const esquema = yup.object({
  //  detallePenasDeducciones: yup.string().required("Dato requerido"),
  //  entregables: yup.string().required("Dato requerido"),
  oficio: yup.string().required("Dato requerido"),
  fechaSolicitudDictamen: yup.string().required("Dato requerido"),
  numeroOficio: yup.string(),
});

const SoporteDocumentalDictamen = ({ }) => {
  const location = useLocation();
  const { errorToast } = useToast();
  const {
    serviciosDictaminadosSeleccionados,
    onReloadDictamenInfo,
    setOnReloadPenasInfo,
    onReloadPenasInfo,
    isEditable,
    setSeccionesInactivas,
    SECCIONES_INICIAL,
    setShowSecciones,
    showSecciones,
    dictamenInfo,
  } = useContext(DictamenContext);
  const [filesErrors, setFilesErrors] = useState({
    detallePenasDeducciones: "",
    entregables: "",
    oficio: "",
  });

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

  let idDictamen = location?.state?.dictamenState?.idDictamen;
  const estatus = location?.state?.dictamenState?.estatus;
  const editable = location?.state?.dictamenState?.editable;
  let idContrato = location?.state?.dictamenState?.idContrato;

  const { refetch } = useGetGestionDocumentalDictamenQuery(
    encodeURIComponent(idDictamen)
  );

  const [
    isDisabledFechaRecepcionDictamen,
    setIsDisabledFechaRecepcionDictamen,
  ] = useState(true);
  const [isDisabled, setIsDisabled] = useState(
    estatus === "Dictaminado" ? true : false
  );
  const [fechaRecepcionDictamenErrorText, setFechaRecepcionDictamenErrorText] =
    useState(null);

  const [validarFechaRecepcion, setValidarFechaRecepcion] = useState(true);
  const [isParticipanteContrato, setIsParticipanteContrato] = useState(true);
  const [isDictaminadoRol, setIsDictaminadoRol] = useState(true);

  const [isUpladedFile, setIsUpladedFile] = useState(false);

  const getDocumentFileNameFormat = (fileName, fileFormat) =>
    `${fileName}${dictamenInfo.nombreCortoContrato}/${dictamenInfo.IDProveedor}/${idDictamen}${fileFormat}`.replace(
      /\|/g,
      "/"
    );

  const OFICIO_FILE_NAME = dictamenInfo
    ? getDocumentFileNameFormat("OfDicAcepSer_", ".pdf")
    : "archivo_oficio.pdf";

  const ENTREGABLES_FILE_NAME = dictamenInfo
    ? getDocumentFileNameFormat("Entregables_", ".zip")
    : "archivo_entregables.zip";

  const PENAS_DEDUCCIONES_FILE_NAME = dictamenInfo
    ? getDocumentFileNameFormat("PYD_", ".xlsx")
    : "archivo_deducciones.zip";

  const [loading, setLoading] = useState(true);
  const [numeroOficioErrorText, setNumeroOficioErrorText] = useState(null);
  const [fechaSolicitudDictamenErrorText, setFechaSolicitudDictamenErrorText] =
    useState(null);
  const [oficioErrorText, setOficioErrorText] = useState(null);

  const [soporteDocumental, setSoporteDocumental] = useState({});

  const [idSoporteDocumentoState, setIdSoporteDocumento] = useState("");

  const [valoresIniciales, setValoresIniciales] = useState({
    ...VALORES_INICIALES,
  });

  useEffect(() => {
    getDataInit(idDictamen);
  }, []);

  const onSetDate = (date) =>
    date !== "" ? moment(date, "DD-MM-YYYY").format("YYYY-MM-DD") : "";

  const onGetValidatePenas = async (idDictamenArg = idDictamen) => {
    const response = await onPostService(
      "/admin-devengados/validar-existe-penas-deducciones",
      {
        idDictamen: idDictamenArg,
      }
    );
    setIsDisabled(!response);
    return response;
  };

  const onGetValidateParticipanteContrato = async () => {
    const response = await onGetService(
      `/admin-devengados/validar-responsabilidad/${idContrato}`
    );
    setIsParticipanteContrato(response);
    return response;
  };

  const onGetValidateParticipanteDictamen = async () => {
    const response = await onPostService(
      `/admin-devengados/validar-responsabilidad-dictaminado`,
      { idDictamen }
    );
    setIsDictaminadoRol(response);
    return response;
  };

  useEffect(() => {
    if (!!idContrato) {
      onGetValidateParticipanteDictamen();
      onGetValidateParticipanteContrato();
    }
  }, [estatus, idContrato]);

  const onGetValidarFechaRecepcion = async () => {
    const response = await onPostService(
      `/admin-devengados/validar-fecha-recepcion`,
      { idDictamen }
    );
    // true es obligatorio activo, es inhabilita no obligatorio
    setValidarFechaRecepcion(response);
  };

  const getDataInit = async (idDictamenArg) => {
    onGetValidarFechaRecepcion(); // mejora => 104, 117
    if (onReloadPenasInfo === null)
      setOnReloadPenasInfo(() => onGetValidatePenas);
    await onGetValidatePenas(idDictamenArg);
    const responseSoporteDocumental = await onGetSoporteDocumental(
      idDictamenArg
    );
    if (isEmpty(responseSoporteDocumental) === false) {
      const {
        idSoporteDocumento,
        nombrePenasDeducciones,
        nombreEntregables,
        numeroOficio,
        nombreOficio,
        fechaSolicitudDictamen,
        fechaRecepcionDictamen,
      } = responseSoporteDocumental;
      setSoporteDocumental({ ...responseSoporteDocumental });
      formRef.current.values.fechaSolicitudDictamen =
        fechaSolicitudDictamen === null
          ? ""
          : onSetDate(fechaSolicitudDictamen);
      formRef.current.values.fechaRecepcionDictamen =
        fechaRecepcionDictamen === null
          ? ""
          : onSetDate(fechaRecepcionDictamen);
      formRef.current.values.numeroOficio =
        numeroOficio === "null" ? "" : numeroOficio;
      setIdSoporteDocumento(idSoporteDocumento);
      if (nombrePenasDeducciones !== "" && nombrePenasDeducciones !== null)
        await onSetFile(
          nombrePenasDeducciones.replace(/\|/g, "/"),
          "detallePenasDeducciones"
        );
      if (nombreOficio !== "" && nombreOficio !== null)
        await onSetFile(nombreOficio.replace(/\|/g, "/"), "oficio");
      if (nombreEntregables !== "" && nombreEntregables !== null)
        await onSetFile(nombreEntregables.replace(/\|/g, "/"), "entregables");
    }
    setLoading(false);
    return;
  };

  const onSetFile = async (fileName, elementId) => {
    await formRef.current.setFieldTouched(elementId, true);
    await setFilesErrors((prevErrors) => ({
      ...prevErrors,
      [elementId]: null,
    }));
    formRef.current.values[elementId] = null;
    setTimeout(() => {
      const file = new File([""], fileName, {
        type: "text/plain",
      });
      const dataTransfer = new DataTransfer();
      dataTransfer.items.add(file);
      const fileInput = document.getElementById(elementId);
      fileInput.files = dataTransfer.files;
    }, [200]);
    return null;
  };

  const onPostService = async (url, data, errorMssage = null) => {
    try {
      const response = await postData(url, data);
      return response.data;
    } catch (err) {
      let errorMessage =
        err?.response?.data !== "" && err?.response?.data?.mensaje[0];
      let errorIdDuplicado = errorMessage === "MSG011";
      if (errorIdDuplicado && err?.response?.status !== 403) {
        showMessage(errorMessage);
      } else if (err?.response?.status !== 403) {
        showMessage(errorMssage === null ? errorMessage : errorMssage);
      }
      return null;
    }
  };

  const onGetService = async (url, errorMssage = null) => {
    try {
      const response = await getData(url);
      return response.data;
    } catch (err) {
      let errorMessage =
        err?.response?.data !== "" && err?.response?.data?.mensaje[0];
      let errorIdDuplicado = errorMessage === "MSG011";
      if (errorIdDuplicado && err?.response?.status !== 403) {
        showMessage(errorMessage);
      } else if (err?.response?.status !== 403) {
        showMessage(errorMssage === null ? errorMessage : errorMssage);
      }
      return null;
    }
  };

  const onGetSoporteDocumental = async (idDictamenArg) => {
    const data = {
      idDictamen: idDictamenArg,
    };
    let response = await onPostService(
      "/admin-devengados/obtener-soporte-documental",
      data
    );
    return { ...response };
  };

  const formRef = useRef();
  const handleResetForm = () => {
    if (formRef.current) {
      formRef.current.resetForm(); // Resetea el formulario utilizando la referencia
    }
  };

  const handleChangeFile = async (
    event,
    setFieldValue,
    key,
    msg,
    ext,
    setFieldTouched,
    fileName
  ) => {
    setLoading(true);
    const newFile = event.target.files[0];
    await setFieldTouched(key, true);

    if (newFile.name.indexOf(ext) <= -1) {
      showMessage(msg);
      await setFilesErrors((prevErrors) => ({
        ...prevErrors,
        [key]: msg,
      }));
    } else {
      setFilesErrors((prevErrors) => ({
        ...prevErrors,
        [key]: null,
      }));
      await setFieldValue(key, newFile);
    }
    const file = new File([newFile], fileName, {
      type: newFile.type,
    });
    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(file);
    document.getElementById(key).files = dataTransfer.files;
    setLoading(false);
  };

  const DownloadFile = ({
    soporteDocumental,
    idDictamen,
    DownloadFileBase64,
    path,
    fileName,
  }) => {
    const onClickDownloadFile = async () => {
      setLoading(true);
      try {
        const formData = new FormData();
        formData.append("path", soporteDocumental[path]);
        formData.append("dictamenId", idDictamen);
        const response = await putDataForm(
          "/admin-devengados/descargar-archivo",
          formData
        );
        const { data } = response;
        DownloadFileBase64(
          data,
          soporteDocumental[fileName],
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );
      } catch (e) {
        if (e.response.status === 400) {
          const { data } = e.response;
          if (data.mensaje[0]) {
            showMessage(data.mensaje[0]);
          }
        }
      }
      setLoading(false);
    };

    return (
      <a href="javascript:void(0)" onClick={onClickDownloadFile}>
        Ver
      </a>
    );
  };

  const uploadFile = async (id, file, filePropName, fileName, url) => {
    try {
      const formData = new FormData();
      formData.append("idSoporteDocumental", id);
      formData.append(filePropName, file);
      formData.append("nombreDocumento", fileName.replace(/\|/g, "/"));
      await putDataForm(url, formData);
      return true;
    } catch (e) {
      if (e.response.status === 400) {
        const { data } = e.response;
        if (data.mensaje[0]) {
          showMessage(
            data.mensaje[0] === null || data.mensaje[0] === ""
              ? MSG_ERROR_FILE
              : data.mensaje[0]
          );
          if (
            filePropName === "entregables" &&
            data.mensaje[0] !== MSG_ERROR_FILE &&
            data.mensaje[0] !== MSG_ERROR_SAT_CLOUD
          ) {
            await setFilesErrors((prevErrors) => ({
              ...prevErrors,
              ["entregables"]: data.mensaje[0],
            }));
          }
        }
      }
    }
    return false;
  };

  const onValidateRequiredFiles = async () => {
    try {
      const { values, setFieldTouched } = formRef.current;
      let isErrorFiles = false;
      let filesErrorsObj = { ...filesErrors };


      if (
        (values.numeroOficio === "" && isParticipanteContrato === true) ||
        (values.numeroOficio === null && isParticipanteContrato === true)
      ) {
        isErrorFiles = true;
        setNumeroOficioErrorText("Campo requerido");
      }
      if (
        (values.fechaSolicitudDictamen === "" &&
          isParticipanteContrato === true) ||
        (values.fechaSolicitudDictamen === null &&
          isParticipanteContrato === true)
      ) {
        isErrorFiles = true;
        setFechaSolicitudDictamenErrorText("Campo requerido");
      }

      if (
        (values.oficio === "" || values.oficio === null) &&
        (!soporteDocumental.nombreOficio || soporteDocumental.nombreOficio === "")
      ) {
        isErrorFiles = true;
        setOficioErrorText("Archivo requerido");
      }

      setFilesErrors(filesErrorsObj);
      return isErrorFiles;
    } catch (e) {
      console.log("Error: ", e);
    }
  };

  const onGetDate = (date) =>
    date === "" ? "" : moment(date, "YYYY-MM-DD").format("YYYY-MM-DDTHH:mm:ss");

  const saveDictamen = async () => {
    let responseUploadFirstFile = true;
    let responseUploadSecondFile = true;
    try {
      const { values } = await formRef.current;
      const {
        entregables,
        numeroOficio,
        fechaSolicitudDictamen,
        fechaRecepcionDictamen,
        oficio,
        detallePenasDeducciones,
      } = values;
      const formData = new FormData();
      formData.append(
        "detallePenasDeducciones",
        detallePenasDeducciones || new File([""], "archivo_vacio.pdf", { type: "application/pdf" })
      );
      formData.append("idSoporteDocumento", idSoporteDocumentoState);
      formData.append("nombrePenasDeducciones", PENAS_DEDUCCIONES_FILE_NAME);
      formData.append("numeroOficio", numeroOficio);
      formData.append(
        "fechaSolicitudDictamen",
        onGetDate(fechaSolicitudDictamen)
      );
      formData.append(
        "fechaRecepcionDictamen",
        onGetDate(fechaRecepcionDictamen)
      );
      formData.append("idDictamen", idDictamen);

      const response = await putDataForm(
        "/admin-devengados/registrar-actualizar-soporte-documental",
        formData
      );
      const { data } = response.data;
      const { idSoporteDocumento } = data;
      setIdSoporteDocumento(idSoporteDocumento);
      responseUploadFirstFile = await uploadFile(
        idSoporteDocumento,
        oficio || new File([""], "archivo_vacio.pdf", { type: "application/pdf" }),
        "oficio",
        OFICIO_FILE_NAME,
        "/admin-devengados/actualizar-oficio-soporte-documental"
      );

      responseUploadSecondFile = await uploadFile(
        idSoporteDocumento,
        entregables || new File([""], "archivo_vacio.pdf", { type: "application/pdf" }),
        "entregables",
        ENTREGABLES_FILE_NAME,
        "/admin-devengados/actualizar-entregable-soporte-documental"
      );


      onPostService("/admin-devengados/registrar-pista-soporte-documental", {
        idDictamen,
        idSoporteDocumental: idSoporteDocumentoState,
        estatusPeticiones: true,
      });
      if (isUpladedFile) refetch(); // Actualiza gestión documental con Redux toolkit query
      return {
        response,
        isActiveSucessModal:
          responseUploadFirstFile && responseUploadSecondFile ? true : false,
      };
    } catch (err) {
      onPostService("/admin-devengados/registrar-pista-soporte-documental", {
        idDictamen,
        idSoporteDocumental: idSoporteDocumentoState,
        estatusPeticiones: false,
      });

      setLoading(false);
      if (err?.response?.status === 404) {
        showMessage(MSG018);
      } else {
        let errorMessage = err?.response?.data?.mensaje[0];
        let errorIdDuplicado = errorMessage === MSG007;
        if (errorIdDuplicado === MSG018) {
          showMessage(errorMessage);
        } else {
          showMessage(errorMessage);
        }
      }
      return null;
    }
  };

  const handleFetchProvider = async () => {
    setLoading(true);
    const isErrorFiles = await onValidateRequiredFiles();
    if (isErrorFiles) {
      showMessage(MSG007);
      setLoading(false);
      return;
    }
    const { values } = await formRef.current;
    if (
      validarFechaRecepcion === true &&
      estatus === "Dictaminado" &&
      (values.fechaRecepcionDictamen === "" ||
        values.fechaRecepcionDictamen === null ||
        values.fechaRecepcionDictamen === undefined)
    ) {
      setFechaRecepcionDictamenErrorText("Dato requerido");
      showMessage(MSG007);
      setLoading(false);
      return;
    }
    const respondeSave = await saveDictamen();
    const { isActiveSucessModal, response } = respondeSave;
    if (response !== null) {
      await getDataInit(idDictamen);
      isActiveSucessModal && showMessage(MSG002);
    }
    setFechaSolicitudDictamenErrorText(null);
    setOficioErrorText(null);
    setNumeroOficioErrorText(null);
    setFechaRecepcionDictamenErrorText(null);
    setLoading(false);
    return;
  };

  const onGetServiciosDictaminados = async () => {
    let servicios = await onPostService(
      `/admin-devengados/obtener-servicios/${idContrato}`,
      { idDictamen }
    );

    return servicios;
  };

  const handleCancelAddProviders = async () => {
    setLoading(true);
    let serviciosDictaminadosSeleccionadosArray =
      serviciosDictaminadosSeleccionados !== null
        ? [...serviciosDictaminadosSeleccionados]
        : [];
    if (isEmpty(serviciosDictaminadosSeleccionadosArray)) {
      const responseServicios = await onGetServiciosDictaminados();
      if (
        isEmpty(responseServicios.data) === false &&
        responseServicios.data !== null
      ) {
        serviciosDictaminadosSeleccionadosArray = [
          ...responseServicios.data,
        ].filter((item) => item.checked);
      }
    }
    let isNotEmptyServicios =
      serviciosDictaminadosSeleccionadosArray !== null &&
      isEmpty(serviciosDictaminadosSeleccionadosArray) === false;
    if (isNotEmptyServicios) {
      let isErrorFiles = false;
      isErrorFiles = await onValidateRequiredFiles();
      if (isErrorFiles) {
        showMessage(MSG007);
        setLoading(false);
        return;
      }
      if (idSoporteDocumentoState === "" || idSoporteDocumentoState === null) {
        const respondeSave = await saveDictamen();
        const { isActiveSucessModal, response } = respondeSave;
        if (response === null) {
          setLoading(false);
          return;
        }
      }

      // NO se puede dictaminar hasta que este seleccionado un servicio (FA07)
      const response = await onPostService("/admin-devengados/dictaminado", {
        idDictamen,
      });
      if (response !== null) {
        await onReloadDictamenInfo(idDictamen);
        await getDataInit(idDictamen);
        setTimeout(async () => {
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
          await setShowSecciones({ ...secciones });
          showMessage(MSG014);
        }, 500);
      } else {
        setLoading(false);
      }
    } else {
      setLoading(false);
      showMessage(MSG012);
    }
    setFechaSolicitudDictamenErrorText(null);
    setOficioErrorText(null);
    setNumeroOficioErrorText(null);
  };

  const onGetPermission = () =>
    estatus === "Cancelado" ||
      estatus === "Proforma" ||
      estatus === "Pagado" ||
      estatus === "Facturado" ||
      estatus === "Solicitud de pago" ||
      estatus === "Dictaminado"
      ? true
      : !editable;

  return (
    <>
      {loading && <Loader />}
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
          values,
          setFieldValue,
          setFieldTouched,
          touched,
          errors,
        }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            <Row>
              <Col md={4}>
                Detalle de penas y deducciones:
                <Authorization
                  process={"CON_SERV_ADMIN_DICT"}
                  redirect={
                    <FileField
                      name="detallePenasDeducciones"
                      accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                      value={values.detallePenasDeducciones}
                      disabled={true}
                      onChange={async (e) => {
                        handleChangeFile(
                          e,
                          setFieldValue,
                          "detallePenasDeducciones",
                          MSG003,
                          ".xlsx",
                          setFieldTouched,
                          PENAS_DEDUCCIONES_FILE_NAME
                        );
                      }}
                      className={getErrorClassName(
                        filesErrors.detallePenasDeducciones,
                        "detallePenasDeducciones"
                      )}
                      helperText={getHelperText(
                        filesErrors.detallePenasDeducciones,
                        "detallePenasDeducciones"
                      )}
                    />
                  }
                >
                  <FileField
                    name="detallePenasDeducciones"
                    accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                    value={values.detallePenasDeducciones}
                    disabled={isDisabled ? true : onGetPermission()}
                    onChange={async (e) => {
                      handleChangeFile(
                        e,
                        setFieldValue,
                        "detallePenasDeducciones",
                        MSG003,
                        ".xlsx",
                        setFieldTouched,
                        PENAS_DEDUCCIONES_FILE_NAME
                      );
                      setIsUpladedFile(true);
                    }}
                    className={getErrorClassName(
                      filesErrors.detallePenasDeducciones,
                      "detallePenasDeducciones"
                    )}
                    helperText={getHelperText(
                      filesErrors.detallePenasDeducciones,
                      "detallePenasDeducciones"
                    )}
                  />
                </Authorization>
                {values.detallePenasDeducciones === null && (
                  <DownloadFile
                    soporteDocumental={soporteDocumental}
                    idDictamen={idDictamen}
                    DownloadFileBase64={DownloadFileBase64}
                    path={"pathPenasDeducciones"}
                    fileName={"nombrePenasDeducciones"}
                  />
                )}
              </Col>
              <Col md={4}>
                <Tooltip
                  placement="top"
                  text={
                    "Añadir a un archivo zip todos los entregables firmados en formato PDF."
                  }
                >
                  <span>
                    Entregables:
                    <Authorization
                      process={"CON_SERV_ADMIN_DICT"}
                      redirect={
                        <FileField
                          accept="zip,application/octet-stream,application/zip,application/x-zip,application/x-zip-compressed"
                          value={values.entregables}
                          name="entregables"
                          onChange={(e) => {
                            handleChangeFile(
                              e,
                              setFieldValue,
                              "entregables",
                              MSG011,
                              ".zip",
                              setFieldTouched,
                              ENTREGABLES_FILE_NAME
                            );
                          }}
                          disabled={true}
                          className={getErrorClassName(
                            filesErrors.entregables,
                            "entregables"
                          )}
                          helperText={getHelperText(
                            filesErrors.entregables,
                            "entregables"
                          )}
                        />
                      }
                    >
                      <FileField
                        accept="zip,application/octet-stream,application/zip,application/x-zip,application/x-zip-compressed"
                        value={values.entregables}
                        name="entregables"
                        onChange={(e) => {
                          handleChangeFile(
                            e,
                            setFieldValue,
                            "entregables",
                            MSG011,
                            ".zip",
                            setFieldTouched,
                            ENTREGABLES_FILE_NAME
                          );
                          setIsUpladedFile(true);
                        }}
                        disabled={isDisabled ? true : onGetPermission()}
                        className={getErrorClassName(
                          filesErrors.entregables,
                          "entregables"
                        )}
                        helperText={getHelperText(
                          filesErrors.entregables,
                          "entregables"
                        )}
                      />
                    </Authorization>
                  </span>
                </Tooltip>
                {values.entregables === null && (
                  <DownloadFile
                    soporteDocumental={soporteDocumental}
                    idDictamen={idDictamen}
                    DownloadFileBase64={DownloadFileBase64}
                    path={"pathEntregables"}
                    fileName={"nombreEntregables"}
                  />
                )}
              </Col>
            </Row>

            <Row>
              <Col md={4}>
                <Authorization
                  process={"CON_SERV_ADMIN_DICT"}
                  redirect={
                    <TextField
                      label={"Número de oficio:"}
                      value={
                        values.numeroOficio === null ? "" : values.numeroOficio
                      }
                      disabled={true}
                      onChange={(e) => {
                        handleChange(e);
                        setNumeroOficioErrorText(null);
                      }}
                      name={"numeroOficio"}
                      className={
                        numeroOficioErrorText === null
                          ? ""
                          : numeroOficioErrorText !== ""
                            ? "is-invalid"
                            : "is-valid"
                      }
                      helperText={numeroOficioErrorText}
                    />
                  }
                >
                  <TextField
                    label={"Número de oficio:"}
                    value={
                      values.numeroOficio === null ? "" : values.numeroOficio
                    }
                    disabled={
                      onGetPermission() ? true : !isParticipanteContrato
                    }
                    onChange={(e) => {
                      handleChange(e);
                      setNumeroOficioErrorText(null);
                    }}
                    name={"numeroOficio"}
                    className={
                      numeroOficioErrorText === null
                        ? ""
                        : numeroOficioErrorText !== ""
                          ? "is-invalid"
                          : "is-valid"
                    }
                    helperText={numeroOficioErrorText}
                  />
                </Authorization>
              </Col>
              <Col md={4}>
                <Authorization
                  process={"CON_SERV_ADMIN_DICT"}
                  redirect={
                    <TextFieldDate
                      label={"Fecha de solicitud de dictamen:"}
                      name="fechaSolicitudDictamen"
                      disabled={true}
                      value={values.fechaSolicitudDictamen}
                      onChange={(e) => {
                        setFechaSolicitudDictamenErrorText(null);
                        handleChange(e);
                      }}
                      className={
                        fechaSolicitudDictamenErrorText === null
                          ? ""
                          : fechaSolicitudDictamenErrorText !== ""
                            ? "is-invalid"
                            : "is-valid"
                      }
                      helperText={fechaSolicitudDictamenErrorText}
                    />
                  }
                >
                  <TextFieldDate
                    label={"Fecha de solicitud de dictamen:"}
                    name="fechaSolicitudDictamen"
                    disabled={
                      onGetPermission() ? true : !isParticipanteContrato
                    }
                    value={values.fechaSolicitudDictamen}
                    onChange={(e) => {
                      setFechaSolicitudDictamenErrorText(null);
                      handleChange(e);
                    }}
                    className={
                      fechaSolicitudDictamenErrorText === null
                        ? ""
                        : fechaSolicitudDictamenErrorText !== ""
                          ? "is-invalid"
                          : "is-valid"
                    }
                    helperText={fechaSolicitudDictamenErrorText}
                  />
                </Authorization>
              </Col>
              <Col md={4}>
                <div style={{ marginTop: "4px" }}>
                  Oficio/Dictamen*:
                  <Authorization
                    process={"CON_SERV_ADMIN_DICT"}
                    redirect={
                      <FileField
                        name="oficio"
                        accept="application/pdf"
                        value={values.oficio}
                        onChange={(e) => {
                          setOficioErrorText(null);
                          handleChangeFile(
                            e,
                            setFieldValue,
                            "oficio",
                            MSG004,
                            ".pdf",
                            setFieldTouched,
                            OFICIO_FILE_NAME
                          );
                        }}
                        className={
                          oficioErrorText === null
                            ? ""
                            : oficioErrorText !== ""
                              ? "is-invalid"
                              : "is-valid"
                        }
                        helperText={oficioErrorText}
                        disabled={true}
                      />
                    }
                  >
                    <FileField
                      name="oficio"
                      accept="application/pdf"
                      value={values.oficio}
                      onChange={(e) => {
                        setOficioErrorText(null);
                        handleChangeFile(
                          e,
                          setFieldValue,
                          "oficio",
                          MSG004,
                          ".pdf",
                          setFieldTouched,
                          OFICIO_FILE_NAME
                        );
                        setIsUpladedFile(true);
                      }}
                      className={
                        oficioErrorText === null
                          ? ""
                          : oficioErrorText !== ""
                            ? "is-invalid"
                            : "is-valid"
                      }
                      helperText={oficioErrorText}
                      disabled={onGetPermission()}
                    />
                  </Authorization>
                  {values.oficio === null && (
                    <DownloadFile
                      soporteDocumental={soporteDocumental}
                      idDictamen={idDictamen}
                      DownloadFileBase64={DownloadFileBase64}
                      path={"pathOficio"}
                      fileName={"nombreOficio"}
                    />
                  )}
                </div>
              </Col>
            </Row>

            <Row>
              <Col md={4}>
                <TextFieldDate
                  label={
                    validarFechaRecepcion
                      ? "Fecha de recepción/generación de dictamen* :"
                      : "Fecha de recepción/generación de dictamen:"
                  }
                  name="fechaRecepcionDictamen"
                  disabled={
                    isDictaminadoRol === false // ID8309-3121 pato validarFechaRecepcion
                      ? !isDictaminadoRol
                      : estatus !== "Dictaminado" ||
                        estatus === "Cancelado" ||
                        estatus === "Solicitud de pago" ||
                        estatus === "Facturado" ||
                        estatus === "Proforma"
                        ? true
                        : editable === true
                          ? !validarFechaRecepcion
                          : true
                  }
                  value={values.fechaRecepcionDictamen}
                  onChange={(e) => {
                    setFechaRecepcionDictamenErrorText("");
                    handleChange(e);
                  }}
                  className={
                    fechaRecepcionDictamenErrorText === null
                      ? ""
                      : fechaRecepcionDictamenErrorText !== ""
                        ? "is-invalid"
                        : "is-valid"
                  }
                  helperText={fechaRecepcionDictamenErrorText}
                  minDate={
                    // 2025-01-20
                    values.fechaSolicitudDictamen !== ""
                      ? moment(
                        values.fechaSolicitudDictamen,
                        "YYYY/MM/DD"
                      ).format("YYYY-MM-DD")
                      : null
                  }
                />
              </Col>

              <Authorization process={"CON_SERV_ADMIN_DICT"}>
                <BotonesAcciones
                  processType="3"
                  process={"CON_SERV_DICT_STA_DICT"}
                  isVisible={true}
                  editable={
                    estatus === "Proforma" ||
                      estatus === "Facturado" ||
                      estatus === "Solicitud de pago" ||
                      estatus === "Cancelado" ||
                      estatus === "Pagado"
                      ? false
                      : !editable === false
                  }
                  isVisibleCancelButton={
                    estatus !== "Dictaminado" &&
                    estatus !== "Pagado" &&
                    estatus !== "Solicitud de pago" &&
                    estatus !== "Proforma" &&
                    estatus !== "Facturado"
                  }
                  showCancelModal={false}
                  handleFetchProvider={handleFetchProvider}
                  modalTitle={MSG002}
                  secondaryButton="Dictaminado"
                  cancelButtonType={
                    serviciosDictaminadosSeleccionados !== null &&
                      isEmpty(serviciosDictaminadosSeleccionados) === false
                      ? "submit"
                      : "button"
                  }
                  handleCancelAddProviders={handleCancelAddProviders}
                />
              </Authorization>
            </Row>
          </Form>
        )}
      </Formik>
    </>
  );
};

export default SoporteDocumentalDictamen;
