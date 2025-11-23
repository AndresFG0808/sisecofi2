import React, { useEffect, useState } from "react";
import { Loader, MainTitle } from "../../../components";
import { Button, Col, Container, Form, Row } from "react-bootstrap";
import { Select } from "../../../components/formInputs";
import { Formik } from "formik";
import VerComites from "./VerComites";
import { getData } from "../../../functions/api";
import { useNavigate } from "react-router-dom";
import Authorization from "../../../components/Authorization";
import { MESSAGES } from "./utils";
import { useErrorMessages } from "../../../hooks/useErrorMessages";
import showMessage from "../../../components/Messages";
import { logError } from '../../../components/utils/logError.js';

const VALORES_INICIALES = {
  idProyecto: "",
  estatusProyecto: ""
}

export function TableroControl() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [estatusOptions, setEstatusOptions] = useState([]);
  const [proyectosOptions, setProyectosOptions] = useState([]);
  const [verComite, setVerComite] = useState(false);
  const [urlTablero, setUrlTablero] = useState("");
  const [dataComites, setDataComites] = useState([]);
  const { getMessageExists } = useErrorMessages(MESSAGES);

  useEffect(() => {
    getDataInit();
  }, []);

  const getDataInit = () => {
    let estatus = getData("/proyectos/estatus-proyecto-todos");
    let urlPbi = getData("/proyectos/url-power-bi");
    Promise.all([estatus, urlPbi])
      .then(([estatusResp, urlPbiResp]) => {
        setEstatusOptions(estatusResp.data);
        setUrlTablero(urlPbiResp.data[0].url);
        setLoading(false);
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
        let errorMessage = error?.response?.data?.mensaje[0];
        if (getMessageExists(errorMessage)) {
          showMessage(errorMessage);
        } else {
          showMessage("Ocurrió un error");
        }
      })
  }

  const getProyectos = (e) => {
    setLoading(true);
    let idEstatus = e.target.value;
    if (idEstatus) {
      getData("/proyectos/nombres-cortos-tablero/" + idEstatus)
        .then((response) => {
          console.log("getProyectos => ", response);
          setLoading(false);
          setProyectosOptions(response.data);
        })
        .catch((error) => {
          logError("error => ", error);
          setLoading(false);
          let errorMessage = error?.response?.data?.mensaje[0];
          if (getMessageExists(errorMessage)) {
            showMessage(errorMessage);
          } else {
            showMessage("Ocurrió un error");
          }
        })
    }
  }

  const buscarComites = (values) => {
    const { idProyecto } = values;
    console.log("buscarComites");
    setLoading(true);

    getData("/proyectos/tablero-control-comites/" + idProyecto)
      .then((response) => {
        setDataComites(response.data);
        setLoading(false);
        if (response.data.data.length === 0) {
          showMessage(MESSAGES.MSG004);
        } else {
          setVerComite(true);
        }
      })
      .catch((error) => {
        logError("error => ", error);
        setLoading(false);
      })
  }

  const handleGoBack = () => {
    const path = "/";
    navigate(path);
  };

  return (
    <Container className="mt-3 px-3">
      {loading && <Loader />}

      <MainTitle title="Tablero de control" />

      <Formik
        initialValues={{ ...VALORES_INICIALES }}
        enableReinitialize
        onSubmit={(e, { resetForm }) => buscarComites(e, resetForm)}
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
            <Authorization process={"REP_TAB_CONT_CONS"}>
              <Row>
                <Col md={4}>
                  <Select
                    label="Estatus de proyecto:"
                    name="estatusProyecto"
                    value={values.estatusProyecto}
                    onChange={(e) => {
                      handleChange(e);
                      getProyectos(e);
                    }}
                    options={estatusOptions}
                    keyValue="primaryKey"
                    keyTextValue="nombre"
                  />
                </Col>

                <Col md={4}>
                  <Select
                    label="Proyecto:"
                    name="idProyecto"
                    value={values.idProyecto}
                    onChange={handleChange}
                    options={proyectosOptions}
                    keyValue="idProyecto"
                    keyTextValue="nombreCorto"
                  />
                </Col>

                <Col md={4} className="mt-4">
                  <Button
                    variant="green"
                    className="btn-sm ms-2 waves-effect waves-light"
                    type="submit"
                    disabled={values.estatusProyecto === "" || values.idProyecto === ""}
                  >
                    Buscar comités
                  </Button>
                </Col>
              </Row>
            </Authorization>
          </Form>
        )}
      </Formik>

      <Row>
        <Col>
          En caso de no responder, dar{" "}
          <a href={urlTablero} target="blank">
            clic aquí
          </a>
        </Col>
      </Row>

      <iframe
        src={urlTablero}
        className="iframe-tablero-control"
        title="Tablero de control"
      ></iframe>

      <Row>
        <Col md={12} className="text-end my-4">
          <Button
            variant="red"
            className="btn-sm ms-2 waves-effect waves-light"
            onClick={handleGoBack}
          >
            Regresar
          </Button>
        </Col>
      </Row>

      <VerComites show={verComite} setShow={setVerComite} data={dataComites} />
    </Container>
  );
}
