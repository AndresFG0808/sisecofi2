import React, { useState, useEffect, useContext, useRef } from "react";
import { Form, Row, Col, Button } from "react-bootstrap";
import IconButton from "../../../components/buttons/IconButton";
import Select from "../../../components/formInputs/Select";
import { Formik } from "formik";
import * as yup from "yup";
import { Tooltip } from "../../../components/Tooltip";
import { ConsumoServiciosContext } from "../Context/ConsumoServiciosContext";
import SingleBasicModal from "../../../modals/SingleBasicModal";
import { useNavigate } from "react-router-dom";
import { useToast } from "../../../hooks/useToast";
import { MESSAGES } from "./constants";
import { DownloadFileBase64 } from "../../../functions/utils/base64.utils";
import showMessage from "../../../components/Messages";
import { getData, postData } from "../../../functions/api";
import { isEmpty } from "lodash";
import Authorization from "../../../components/Authorization";

import moment from "moment";
import { use } from "react";
const VALORES_INICIALES = {
  idContratoVigente: "",
  idContrato: "",
  idProveedor: "",
  idTipoConsumo: "",
  page: 0,
  size: 15,
};

const ConsumoServicios = () => {
  const formRef = useRef();
  const {
    setLoading,
    setDataTable,
    dataTable,
    setTipoConsumo,
    isVisibleEditable,
    setIsVisibleEditable,
    setProcessTypeByTipoConsumo,
    processTypeByTipoConsumo,
    setProcessTypeEditByTipoConsumo,
  } = useContext(ConsumoServiciosContext);

  let navigate = useNavigate();
  const { errorToast } = useToast();

  const [valoresIniciales, setValoresIniciales] = useState({
    ...VALORES_INICIALES,
  });
  const [showOpenModal, setShowOpenModal] = useState(false);
  const [textModal, setTextModal] = useState("");

  const [textDownloadFile, setTextDownloadFile] = useState("");
  const [textDownloadFileName, setTextDownloadFileName] = useState("");

  const [isVisibleButtons, setIsVisibleButtons] = useState(false);

  const [catalog1, setCatalog1] = useState([]);
  const [catalog2, setCatalog2] = useState([]);
  const [catalog3, setCatalog3] = useState([]);
  const [catalog4, setCatalog4] = useState([]);
  const [catalog5, setCatalog5] = useState([]);

  const esquema = yup.object({
    idContratoVigente: yup.string().required("Dato requerido"),
    idContrato: yup.string().required("Dato requerido"),
    idProveedor: yup.string().required("Dato requerido"),
    idTipoConsumo: yup.string().required("Dato requerido"),
  });

  useEffect(() => {
    onGetInitialData();
  }, []);

  const onVisivilityButtons = (catalog2Arg, idContratoArg) => {
    let isVisibility = false;
    if (isEmpty(catalog2Arg) === false && idContratoArg !== "") {
      isVisibility = catalog2Arg.filter(
        (item) => item.idContrato == idContratoArg
      )[0].ejecucion;
    }
    return isVisibility;
  };

  const onGetService = async (url) => {
    try {
      const catalog = await getData(url);
      return [...catalog.data];
    } catch (err) {
      if (err?.response?.status == 500) {
        showMessage(MESSAGES.MSG002);
      }
      let errorMessage =
        err?.response?.data !== "" &&
        err?.response?.data?.mensaje &&
        err?.response?.data?.mensaje[0];
      let errorIdDuplicado = errorMessage === MESSAGES.MSG001;
      if (errorIdDuplicado && err?.response?.status !== 403) {
        showMessage(errorMessage);
      } else if (err?.response?.status !== 403) {
        showMessage(MESSAGES.MSG002);
      }
      return [];
    }
  };

  const onDownloadExcel = async () => {
    setLoading(true);
    try {
      const { idStatus, idContrato, idTipoConsumo, idProveedor } =
        formRef.current.values;
      const nombre = catalog4.filter(
        (item) => item.primaryKey == idTipoConsumo
      )[0].nombre;
      const data = {
        idContrato: parseInt(idContrato, 10),
        tipoConsumo: nombre,
        idEstatus: parseInt(idStatus, 10),
        idProveedor: parseInt(idProveedor, 10),
      };
      let reportResponse = await onPostService(
        `/admin-devengados/exportar-dictamen-estimacion`,
        data
      );
      DownloadFileBase64(
        reportResponse,
        textDownloadFileName,
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      );
    } catch (err) {
      setShowOpenModal(true);
      setTextModal(
        "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."
      );
    }
    setLoading(false);
  };

  const onPostService = async (url, data) => {
    try {
      const response = await postData(url, data);
      return response.data;
    } catch (err) {
      setLoading(false);
      let errorMessage =
        err?.response?.data !== "" &&
        err?.response?.data?.mensaje &&
        err?.response?.data?.mensaje[0];
      let errorIdDuplicado = errorMessage === MESSAGES.MSG001;
      if (errorIdDuplicado) {
        showMessage(errorMessage);
      } else {
        showMessage(MESSAGES.MSG002);
      }
      return [];
    }
  };

  const onGetInitialData = async () => {
    const contratoVigenteCatalog = await onGetService(
      "/admin-devengados/contrato-vigente"
    );
    setCatalog1(contratoVigenteCatalog);
    const tipoConsumoCatalog = await onGetService(
      "/admin-devengados/tipo-consumo"
    );
    setCatalog4(tipoConsumoCatalog);
    setLoading(false);
  };

  const onGetProvidersCatalog = async (idContratoArg, setFieldValue) => {
    formRef.current.values.idContrato = idContratoArg;
    formRef.current.setFieldError("idContrato", "");
    setLoading(true);

    const contratosCatalog = await onGetService(
      `/admin-devengados/proveedores-contrato/${idContratoArg}`
    );

    const catalogResponse = [...contratosCatalog];
    if (catalogResponse.length === 1) {
      setFieldValue("idProveedor", catalogResponse[0].idProveedor.toString());
      formRef.current.touched.idProveedor = true;
      formRef.current.setFieldError("idProveedor", "");
    }
    setCatalog3(catalogResponse);
    setLoading(false);
  };

  const buscarServicios = async (data) => {
    setLoading(true);
    const {
      idStatus,
      idContrato,
      idTipoConsumo,
      idProveedor,
      idContratoVigente,
    } = data;
    const nombre = catalog4.filter(
      (item) => item.primaryKey == idTipoConsumo
    )[0].nombre;
    const dataService = {
      idContrato: parseInt(idContrato, 10),
      tipoConsumo: nombre,
      idEstatus: parseInt(idStatus, 10),
      idProveedor: parseInt(idProveedor, 10),
      contratoVigente: catalog1.filter(
        (item) => item.primaryKey == idContratoVigente
      )[0].nombre,
    };
    const response = await onPostService(
      `/admin-devengados/busqueda-dictamen-estimacion`,
      dataService
    );
    setTipoConsumo(nombre);

    setTextDownloadFile("Exportar");
    setTextDownloadFileName(
      idTipoConsumo === "2" ? "Dictámenes" : "Estimaciones"
    );
    setIsVisibleButtons(true);

    setIsVisibleEditable(
      onVisivilityButtons(catalog2, parseInt(idContrato, 10))
    );

    if (isEmpty(response) === false) {
      setDataTable(
        response.map((item, index) => {
          const {
            id,
            periodoControl,
            periodoInicio,
            periodoFin,
            proveedor,
            estatus,
            montoEstimado,
            montoEstimadoPesos,
            montoDictaminado,
            montoDictaminadoPesos,
            pendientePago,
            comprobanteFiscal,
            tipo,
            estatusResponsabilidad,
            idBd,
          } = item;
          return {
            index: index + 1,
            id,
            periodoControl,
            periodoInicial:
              periodoInicio !== null &&
              moment(periodoInicio).format("DD/MM/YYYY"),
            periodoFinal:
              periodoFin !== null && moment(periodoFin).format("DD/MM/YYYY"),
            proveedor,
            estatus,
            comprobanteFiscal,
            pendientePago,
            montoEstimado: montoEstimado,
            montoEstimadoPesos,
            montoDictaminado,
            montoDictaminadoPesos,
            type: tipo,
            idProveedor,
            idContrato: idContrato,
            estatusResponsabilidad,
            idBd,
          };
        })
      );
    } else {
      setShowOpenModal(true);
      setTextModal(MESSAGES.MSG001);
      setDataTable([]);
    }
    setLoading(false);
  };

  const onCloseModal = () => setShowOpenModal(false);

  return (
    <>
      <SingleBasicModal
        handleApprove={onCloseModal}
        handleDeny={onCloseModal}
        approveText={"Aceptar"}
        show={showOpenModal}
        title={"Mensaje"}
        size="md"
        onHide={onCloseModal}
      >
        {textModal}
      </SingleBasicModal>

      <Formik
        innerRef={(f) => (formRef.current = f)}
        initialValues={valoresIniciales}
        enableReinitialize
        validateOnMount={true}
        validationSchema={esquema}
        validate={(values) => {
          const { idContrato, idContratoVigente, idProveedor, idTipoConsumo } =
            values;
          if (
            idContrato !== "" &&
            idContratoVigente !== "" &&
            idProveedor !== "" &&
            idTipoConsumo !== ""
          ) {
            return {};
          }
          return { error: true };
        }}
        onSubmit={(e, { resetForm }) => buscarServicios(e, resetForm)}
      >
        {({
          handleSubmit,
          handleChange,
          values,
          errors,
          touched,
          isValid,
          setFormikState,
          setFieldValue,
        }) => (
          <Form autoComplete="off" onSubmit={handleSubmit}>
            <Row>
              <Col md={4}>
                <Select
                  label="Contrato vigente*:"
                  name="idContratoVigente"
                  value={values.idContratoVigente}
                  onChange={async (event) => {
                    const value = event.target.value;
                    formRef.current.values.idContrato = "";
                    setFieldValue("idProveedor", "");
                    formRef.current.touched.idProveedor = false;
                    formRef.current.touched.idContrato = false;
                    setCatalog3([]);
                    setCatalog2([]);
                    handleChange(event);
                    if (value !== "") {
                      setLoading(true);
                      setCatalog3([]);
                      const nombre = catalog1.filter(
                        (item) => item.primaryKey == value
                      )[0].nombre;
                      const contratosCatalog = await onGetService(
                        `/admin-devengados/contratos-vigentes/${nombre}`
                      );
                      setCatalog2(contratosCatalog);
                      setLoading(false);
                    }
                  }}
                  options={catalog1}
                  keyValue="primaryKey"
                  keyTextValue="nombre"
                  className={
                    touched.idContratoVigente &&
                    (errors.idContratoVigente ? "is-invalid" : "is-valid")
                  }
                  helperText={
                    touched.idContratoVigente ? errors.idContratoVigente : ""
                  }
                />
              </Col>
              <Col md={4}>
                <Select
                  label="Contratos*:"
                  name="idContrato"
                  value={values.idContrato}
                  onChange={(event) => {
                    const value = event.target.value;
                    formRef.current.setFieldError("idProveedor", "");
                    formRef.current.touched.idProveedor = false;
                    setFieldValue("idProveedor", "");
                    setCatalog3([]);
                    handleChange(event);
                    if (value !== "") {
                      onGetProvidersCatalog(value, setFieldValue);
                    }
                  }}
                  options={catalog2}
                  disabled={isEmpty(catalog2)}
                  keyValue="idContrato"
                  keyTextValue="nombreCorto"
                  className={
                    touched.idContrato &&
                    (errors.idContrato ? "is-invalid" : "is-valid")
                  }
                  helperText={touched.idContrato ? errors.idContrato : ""}
                />
              </Col>
              <Col md={4}>
                <Select
                  label="Proveedores*:"
                  name="idProveedor"
                  value={values.idProveedor}
                  onChange={handleChange}
                  options={catalog3}
                  disabled={isEmpty(catalog3)}
                  keyValue="idProveedor"
                  keyTextValue="nombreProveedor"
                  className={
                    touched.idProveedor
                      ? touched.idProveedor && errors.idProveedor
                        ? "is-invalid"
                        : "is-valid"
                      : ""
                  }
                  helperText={touched.idProveedor ? errors.idProveedor : ""}
                />
              </Col>
              <Col md={4}>
                <Select
                  label="Tipo de consumo*:"
                  name="idTipoConsumo"
                  value={values.idTipoConsumo}
                  onChange={async (event) => {
                    const value = event.target.value;
                    if (value !== "") {
                      if (value == 1) {
                        setProcessTypeByTipoConsumo("CON_SERV_EST_CONS");
                        setProcessTypeEditByTipoConsumo("CON_SERV_ADMIN_ESTIM");
                      } else {
                        setProcessTypeByTipoConsumo("CON_SERV_DICT_CONS");
                        setProcessTypeEditByTipoConsumo("CON_SERV_ADMIN_DICT");
                      }
                      setLoading(true);
                      formRef.current.values.idStatus = "";
                      handleChange(event);
                      const nombre = catalog4.filter(
                        (item) => item.primaryKey == value
                      )[0].nombre;
                      const estatusCatalog = await onGetService(
                        `/admin-devengados/estatus-dictamen-estimacion/${nombre}`
                      );
                      setCatalog5(estatusCatalog);
                      setLoading(false);
                    } else {
                      handleChange(event);
                      setCatalog5([]);
                      setProcessTypeByTipoConsumo(null);
                    }
                  }}
                  options={catalog4}
                  keyValue="primaryKey"
                  keyTextValue="nombre"
                  className={
                    touched.idTipoConsumo &&
                    (errors.idTipoConsumo ? "is-invalid" : "is-valid")
                  }
                  helperText={touched.idTipoConsumo ? errors.idTipoConsumo : ""}
                />
              </Col>
              <Col md={4}>
                <Select
                  label="Estatus:"
                  name="idStatus"
                  value={values.idStatus}
                  onChange={handleChange}
                  options={catalog5}
                  keyValue="primaryKey"
                  keyTextValue="nombre"
                  disabled={isEmpty(catalog5)}
                />
              </Col>
              <Col md={4}>
                <Authorization process={processTypeByTipoConsumo}>
                  <Button
                    variant="gray"
                    className="btn-sm ms-2 waves-effect waves-light mt-4"
                    type="submit"
                    onClick={() => {
                      if (isValid === false) {
                        errorToast(MESSAGES.CAMPOS_OBLIGATORIOS);
                      }
                    }}
                  >
                    Buscar
                  </Button>
                </Authorization>
              </Col>
            </Row>
            <Row>
              {isVisibleButtons && (
                <Col md={12} className="text-end mb-3">
                  {isVisibleEditable && (
                    <>
                      <Authorization process={"CON_SERV_ADMIN_ESTIM"}>
                        <Tooltip placement="top" text={"Alta de estimación"}>
                          <span>
                            <IconButton
                              type="fileText"
                              onClick={() => {
                                let dictamenState = {
                                  idDictamen: "",
                                  idContrato: values.idContrato,
                                  idProveedor: values.idProveedor,
                                  dictamen: {},
                                  editable: false,
                                };
                                let estimacionState = {
                                  idEstimacion: "",
                                  idContrato: values.idContrato,
                                  idProveedor: values.idProveedor,
                                  estimacion: {},
                                  editable: true,
                                };
                                navigate(
                                  "/consumoServicios/consumoServicios/estimacion",
                                  {
                                    state: {
                                      dictamenState,
                                      estimacionState,
                                    },
                                  }
                                );
                              }}
                              disabled={false}
                            />
                          </span>
                        </Tooltip>
                      </Authorization>

                      <Authorization process={"CON_SERV_ADMIN_DICT"}>
                        <Tooltip placement="top" text={"Alta de dictamen"}>
                          <span>
                            <IconButton
                              type="moneyDollar"
                              onClick={() => {
                                const proveedor =
                                  values.idProveedor !== "" &&
                                  values.idProveedor !== null
                                    ? catalog3.filter(
                                        (item) =>
                                          item.idProveedor == values.idProveedor
                                      )[0].nombreProveedor
                                    : "";
                                let dictamenState = {
                                  idDictamen: "",
                                  idContrato: values.idContrato,
                                  idProveedor: values.idProveedor,
                                  dictamen: {},
                                  editable: true,
                                  editableDuplicateButton: false,
                                  proveedor,
                                };
                                let estimacionState = {
                                  idEstimacion: "",
                                  idContrato: values.idContrato,
                                  idProveedor: values.idProveedor,
                                  proveedor,
                                  estimacion: {},
                                  editable: true,
                                };
                                navigate(
                                  "/consumoServicios/consumoServicios/dictamen",
                                  {
                                    state: {
                                      dictamenState,
                                      estimacionState,
                                    },
                                  }
                                );
                              }}
                              disabled={false}
                            />
                          </span>
                        </Tooltip>
                      </Authorization>
                    </>
                  )}

                  <Tooltip placement="top" text={textDownloadFile}>
                    <span>
                      <IconButton
                        type="excel"
                        onClick={onDownloadExcel}
                        disabled={isEmpty(dataTable)}
                      />
                    </span>
                  </Tooltip>
                </Col>
              )}
            </Row>
          </Form>
        )}
      </Formik>
    </>
  );
};

export default ConsumoServicios;
