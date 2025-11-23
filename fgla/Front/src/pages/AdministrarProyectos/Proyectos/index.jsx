import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Form, Row, Col, Button } from "react-bootstrap";
import * as yup from "yup";
import { Select, TextField } from "../../../components/formInputs";
import { MainTitle, Loader, Accordion } from "../../../components";
import { TablaPaginada } from "../../../components/table";
import { Formik } from "formik";
import { injectActions, downloadExcelBlob } from "../../../functions/utils";
import IconButton from "../../../components/buttons/IconButton";
import { logError } from '../../../components/utils/logError.js';
import {
  downloadDocumentPost,
  getData,
  postData,
} from "../../../functions/api";
import moment from "moment";
import showMessage from "../../../components/Messages";
import { useToast } from "../../../hooks/useToast";
import { ALTA_PROYECTOS } from "../../../constants/messages";
import Authorization from "../../../components/Authorization";
import { useGetAuthorization } from "../../../hooks/useGetAuthorization";
import _ from "lodash";

const VALORES_INICIALES = {
  idEstatusProyecto: "",
  nombreCorto: "",
  idProyecto: "",
  areaSolicitante: "",
  areaResponsable: "",
  liderProyecto: "",

  page: 0,
  size: 15,
};

const PROCESOS = {
  altaProyecto: "alta",
  editarProyecto: "editar",
  verDetalle: "read",
  planTrabajo: "plan",
};

const FORMAT_DATE = "DD/MM/YYYY";

const HEADERS = [
  { dataField: "idProyecto", text: "Id proyecto", filter: true, sort: true },
  {
    dataField: "nombreCorto",
    text: "Nombre corto del proyecto",
    filter: true,
    sort: true,
  },
  { dataField: "fechaInicio", text: "Fecha inicio", filter: true, sort: true },
  { dataField: "fechaFin", text: "Fecha fin", filter: true, sort: true },
  {
    dataField: "liderProyecto",
    text: "Líder de proyecto",
    filter: true,
    sort: true,
  },
  {
    dataField: "areaSolicitante",
    text: "Área solicitante",
    filter: true,
    sort: true,
  },
  {
    dataField: "areaResponsable",
    text: "Área responsable",
    filter: true,
    sort: true,
  },
  { dataField: "monto", text: "Monto solicitado", filter: true, sort: true },
  { dataField: "statusName", text: "Estatus", filter: true, sort: true },
  { dataField: "planTrabajo", text: "Plan de trabajo" },
  { dataField: "acciones", text: "Acciones", width: { width: "105px" } },
];

const PAGEABLE = {
  totalPages: 0,
  totalElements: 0,
  pageNumber: 0,
  size: 15,
};

const Proyectos = () => {
  const ID_KEY_NAME = "idProyecto";
  const { errorToast } = useToast();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [valuesConsulta, setValuesConsulta] = useState({});
  const [dataTable, setDataTable] = useState([]);
  const [pageable, setPageable] = useState(PAGEABLE);
  const [estatusProyectoOptions, setEstatusProyectoOptions] = useState([]);
  const [areaSolicitanteOptions, setAreaSolicitanteOptions] = useState([]);
  const [areaResponsableOptions, setAreaResponsableOptions] = useState([]);
  const [nombreCortoOptions, setNombreCortoOptions] = useState([]);
  const [disabledFilters, setDisabledFilters] = useState(false);
  const { isAuthorized: canEdit } = useGetAuthorization("PROY_MODIF");

  const esquema = yup.object({
    idEstatusProyecto: yup.string().required("Dato requerido"),
  });

  const OPTIONS_MONEY = { style: "currency", currency: "USD" };
  const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);

  useEffect(() => {
    getDataInit();
  }, []);

  const getDataInit = () => {
    let estatusProyecto = getData("/proyectos/estatus-proyecto");
    let areaSolicitante = getData("/proyectos/administraciones-centrales");

    Promise.all([estatusProyecto, areaSolicitante])
      .then(([estatusProyectoResp, areaSolicitanteResp]) => {
        setEstatusProyectoOptions(estatusProyectoResp.data);
        setAreaSolicitanteOptions(areaSolicitanteResp.data);
        setLoading(false);
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        showMessage(ALTA_PROYECTOS.ERROR);
      });
  };

  const onChageStatus = (e, setFieldValue) => {
    if (e.target.value !== "") {
      setLoading(true);
      getData("/proyectos/nombres-cortos/" + e.target.value)
        .then((response) => {
          setNombreCortoOptions(nombreCortoProcess(response.data));
          setLoading(false);
        })
        .catch((error) => {
          logError("error => ", error);
          setLoading(false);
          showMessage(ALTA_PROYECTOS.ERROR);
        });
    }
    setFieldValue("idProyecto", "");
    setFieldValue("nombreCorto", "");
  };

  const onChangeNombreCorto = (e, setFieldValue) => {
    if (e.target.value !== "" && !_.isEmpty(nombreCortoOptions)) {
      let { value } = e.target;
      let nombreOption = nombreCortoOptions.find((s) => s.nombreCorto == value);
      if (nombreOption) {
        setFieldValue("idProyecto", nombreOption.idProyecto);
      }
    }
    if (e.target.value === "") {
      setFieldValue("idProyecto", "");
    }
  };

  const nombreCortoProcess = (data) => {
    let processedData = [];
    data.forEach((item) => {
      let { idProyecto, nombreCorto } = item;
      let row = {
        nombreCorto,
        idProyecto,
      };
      processedData.push(row);
    });
    return processedData;
  };

  const buscarProyectos = (data) => {
    setLoading(true);
    setValuesConsulta(data);
    postData("/proyectos/buscar", data)
      .then((response) => {
        processData(response.data);
        if (response.data.content.length === 0) {
          showMessage(ALTA_PROYECTOS.MSG002);
        }
        setLoading(false);
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        showMessage(ALTA_PROYECTOS.ERROR);
      });
  };

  const processData = (data) => {
    let processedDataTable = [];
    data.content.forEach((item) => {
      let row = {
        ...item,
        fechaInicio: dateFormat(item.fechaInicio),
        fechaFin: dateFormat(item.fechaFin),
        areaResponsable: item?.areaResponsable?.administracion,
        monto: FORMAT_MONEY.format(item.monto),
        statusName: item.catEstatus?.nombre,
        planTrabajo: getLinkPlanTrabajo(item),
      };
      processedDataTable.push(row);
    });

    let pageable = {
      totalPages: data.totalPages,
      totalElements: data.totalElements,
      pageNumber: data.number,
      size: data.size,
    };

    setPageable(pageable);
    setDataTable(processedDataTable);
  };

  const dateFormat = (date) => {
    let formatedDateTime =
      date !== null && date !== "" ? moment(date).format(FORMAT_DATE) : "";
    return formatedDateTime;
  };

  const getLinkPlanTrabajo = (proyecto) => {
    let link = "";
    if (proyecto.plan) {
      link = (
        <u>
          <span
            onClick={verPlanTrabajo(proyecto)}
            style={{ cursor: "pointer" }}
          >
            ver
          </span>
        </u>
      );
    }
    return link;
  };

  const verPlanTrabajo = (proyecto) => () => {
    console.log("verPlanTrabajo > proyecto => ", proyecto);
    let path = "/proyectos/proyectos/editar";
    let objStr = JSON.stringify(proyecto);
    let state = {
      idProyecto: proyecto.idProyecto,
      proyecto: objStr,
      editable: true,
      planTrabajo: true,
    };
    navigate(path, { state });
  };

  const handleEdit = (id) => () => {
    console.log("handleEdit > id => ", id);
    let obj = dataTable.find(({ idProyecto }) => idProyecto === id);
    let isCanceled = obj.catEstatus?.nombre.toLowerCase().includes("cancelado");
    let path = "/proyectos/proyectos/editar";
    let objStr = JSON.stringify(obj);
    let state = {
      idProyecto: id,
      proyecto: objStr,
      editable: isCanceled ? false : true,
    };
    navigate(path, { state });
  };

  const handleShow = (id) => () => {
    console.log("handleShow > id => ", id);
    let obj = dataTable.find(({ idProyecto }) => idProyecto === id);
    let path = "/proyectos/proyectos/verDetalle";
    let objStr = JSON.stringify(obj);
    let state = { idProyecto: id, proyecto: objStr };
    navigate(path, { state });
  };

  const handleDownloadExcel = (data) => () => {
    setLoading(true);
    setValuesConsulta(data);
    downloadDocumentPost("/proyectos/reporte-proyectos", data)
      .then((response) => {
        setLoading(false);
        downloadExcelBlob(response.data, "Proyectos");
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
      });
  };

  const onChangeStatusProyecto = (id) => () => {
    console.log("onChangeStatusProyecto > id => ", id);
  };

  const handleChangeSolicitante = (e, setFieldValue) => {
    setLoading(true);
    console.log("handleChangeSolicitante", e.target.value);
    getData("/proyectos/area-responsable/" + e.target.value)
      .then((response) => {
        setAreaResponsableOptions(response.data);
        setLoading(false);
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
      });
    if (e.target.value === "") {
      setFieldValue("areaResponsable", "");
      setFieldValue("liderProyecto", "");
    }
  };

  const updateDataTable = (values) => {
    console.log("updateDataTable > ", values);

    let data = {
      ...valuesConsulta,
      ...values,
    };
    setValuesConsulta(data);
    buscarProyectos(data);
  };

  const validateFilters = (values, e) => {
    let { name, value } = e.target;
    values = { ...values, [name]: value };
    let { idEstatusProyecto, nombreCorto, idProyecto } = values;

    if (idEstatusProyecto !== "" && nombreCorto !== "" && idProyecto !== "") {
      setDisabledFilters(true);
    } else {
      setDisabledFilters(false);
    }
  };

  const handleAltaProyecto = () => {
    let path = "/proyectos/proyectos/alta";
    const state = { editable: true };
    navigate(path, { state });
  };

  return (
    <Container className="mt-3 px-3">
      {loading && <Loader />}

      <MainTitle title="Proyectos" />

      <Accordion title="Búsqueda" collapse={false} showChevron={false}>
        <Formik
          initialValues={{ ...VALORES_INICIALES }}
          enableReinitialize
          validationSchema={esquema}
          validateOnMount={true}
          onSubmit={(e, { resetForm }) => buscarProyectos(e, resetForm)}
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
            setFieldValue,
          }) => (
            <Form autoComplete="off" onSubmit={handleSubmit}>
              <Row>
                <Col md={4}>
                  <Select
                    label="Estatus*:"
                    name="idEstatusProyecto"
                    value={values.idEstatusProyecto}
                    onChange={(e) => {
                      handleChange(e);
                      onChageStatus(e, setFieldValue);
                      validateFilters(values, e);
                    }}
                    options={estatusProyectoOptions}
                    keyValue="primaryKey"
                    keyTextValue="nombre"
                    className={
                      touched.idEstatusProyecto &&
                      (errors.idEstatusProyecto ? "is-invalid" : "is-valid")
                    }
                    helperText={
                      touched.idEstatusProyecto ? errors.idEstatusProyecto : ""
                    }
                  />
                </Col>
                <Col md={4}>
                  <Select
                    label="Nombre corto del proyecto:"
                    name="nombreCorto"
                    value={values.nombreCorto}
                    onChange={(e) => {
                      handleChange(e);
                      onChangeNombreCorto(e, setFieldValue);
                      validateFilters(values, e);
                    }}
                    options={nombreCortoOptions}
                    keyValue="nombreCorto"
                    keyTextValue="nombreCorto"
                    disabled={!values.idEstatusProyecto}
                  />
                </Col>
                <Col md={4}>
                  <TextField
                    label="Id proyecto:"
                    name="idProyecto"
                    value={values.idProyecto}
                    onChange={(e) => {
                      handleChange(e);
                      validateFilters(values, e);
                    }}
                  />
                </Col>
                <Col md={4}>
                  <Select
                    label="Área solicitante:"
                    name="areaSolicitante"
                    value={values.areaSolicitante}
                    onChange={(e) => {
                      handleChange(e);
                      handleChangeSolicitante(e, setFieldValue);
                    }}
                    options={areaSolicitanteOptions}
                    keyValue="primaryKey"
                    keyTextValue="administracion"
                    disabled={disabledFilters}
                  />
                </Col>
                <Col md={4}>
                  <Select
                    label="Área responsable:"
                    name="areaResponsable"
                    value={values.areaResponsable}
                    onChange={handleChange}
                    options={areaResponsableOptions}
                    keyValue="primaryKey"
                    keyTextValue="administracion"
                    disabled={disabledFilters || !values.areaSolicitante}
                  />
                </Col>
                <Col md={4}>
                  <TextField
                    label="Líder de proyecto:"
                    name="liderProyecto"
                    value={values.liderProyecto}
                    onChange={handleChange}
                    disabled={disabledFilters}
                  />
                </Col>
              </Row>
              <Row>
                <Col md={12} className="text-end mb-2">
                  <Authorization process={"PROY_CONS"}>
                    <Button
                      variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      onClick={() => {
                        !isValid && errorToast(ALTA_PROYECTOS.MSG001);
                      }}
                      type="submit"
                    >
                      Buscar
                    </Button>
                  </Authorization>
                </Col>
              </Row>
              <Row>
                <Col md={12} className="text-end mb-2">
                  <Authorization process={"PROY_ALTA"}>
                    <IconButton
                      type="add"
                      onClick={handleAltaProyecto}
                      tooltip={"Nuevo proyecto"}
                    />
                  </Authorization>
                  <Authorization process={"PROY_CONS"}>
                    <IconButton
                      type="excel"
                      onClick={handleDownloadExcel(values)}
                      disabled={dataTable.length === 0}
                      tooltip={"Exportar a Excel"}
                    />
                  </Authorization>
                </Col>
              </Row>
            </Form>
          )}
        </Formik>

        <TablaPaginada
          idKeyName={ID_KEY_NAME}
          idKeyLink={ID_KEY_NAME}
          headers={HEADERS}
          header="Proyectos registrados"
          data={injectActions(dataTable, { edit: canEdit, show: true })}
          actionFns={{ handleEdit, handleShow }}
          onChangeStatus={onChangeStatusProyecto}
          pageable={pageable}
          updateData={updateDataTable}
        />
      </Accordion>
    </Container>
  );
};

export default Proyectos;
