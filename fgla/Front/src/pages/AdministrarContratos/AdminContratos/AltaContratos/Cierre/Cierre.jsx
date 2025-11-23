import { Form, Formik } from "formik";
import React, { useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import { FileField } from "../../../../../components/formInputs";
import { INITIAL_VALUES } from "./utils";
import {
  usePostCierreContratoMutation,
  usePutCierreContratoMutation,
} from "./store";
import { Loader } from "../../../../../components";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import { MODIFICAR_CONTRATO } from "../../../../../constants/messages";
import { useParams, useSearchParams } from "react-router-dom";
import Authorization from "../../../../../components/Authorization";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";

export function Cierre({ isDetalle }) {
  const { idContrato } = useParams();
  const [idNuevoContrato] = useSearchParams();
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const [cierre] = useState(INITIAL_VALUES);
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasicModal, setShowSingleBasicModal] = useState(false);
  const [extraInfo, setExtraInfo] = useState({
    errors: {
      actaCierre: null,
    },
    touched: {
      actaCierre: false,
    },
  });
  const { errors, touched } = extraInfo;

  const [cargarCierre, { isLoading }] = usePostCierreContratoMutation();
  const [cambiarStatus, { isLoading: isLoadingStatus }] =
    usePutCierreContratoMutation();

  const handleSubmit = async (values) => {
    try {
      if (values.actaCierre === null) {
        throw new Error("Dato requerido");
      }
      const data = {
        ...values,
        idContrato: parseInt(idContrato || idNuevoContrato),
      };
      await cargarCierre({ data }).unwrap();
      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG004);
      setShowSingleBasicModal(true);
    } catch (error) {
      const { message } = error;
      const mensaje = error?.data?.mensaje?.[0];
      if (message === "Dato requerido") {
        setExtraInfo((prev) => ({
          ...prev,
          errors: { actaCierre: "Dato requerido" },
        }));
        setSingleBasicMessage(MODIFICAR_CONTRATO.MSG018);
        setShowSingleBasicModal(true);
        return;
      }
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };
  const handleChangeStatus = async () => {
    try {
      await cambiarStatus(idContrato || idNuevoContrato).unwrap();
      setSingleBasicMessage("El contrato se ha cerrado exitosamente.");
      setShowSingleBasicModal(true);
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasicModal(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasicModal(true);
      }
    }
  };
  const handleChangeFile = (event, setFieldValue) => {
    const newFile = event.target.files[0];
    if (newFile) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const base64string = e.target.result.split(",")[1];
        const fileName = newFile.name;
        setFieldValue("actaCierre", base64string);
        setFieldValue("nombreArchivo", fileName);
      };
      reader.onerror = (error) => {};
      reader.readAsDataURL(newFile);
      setExtraInfo((prev) => ({
        ...prev,
        errors: { actaCierre: null },
      }));
    }
  };
  return (
    <>
      {isLoading || isLoadingStatus ? <Loader /> : null}
      <Formik enableReinitialize onSubmit={handleSubmit} initialValues={cierre}>
        {({ values, handleBlur, setFieldValue, handleSubmit }) => {
          return (
            <>
              <Form autoComplete="off" onSubmit={handleSubmit}>
                <Row>
                  <Col md={6}>
                    <Row className="align-items-center">
                      <Col md={6}>
                        <FileField
                          onBlur={(e) => {
                            handleBlur(e);
                            setExtraInfo((prev) => ({
                              ...prev,
                              touched: { actaCierre: true },
                              ...(values.actaCierre
                                ? { errors: { actaCierre: null } }
                                : { errors: { actaCierre: "Dato requerido" } }),
                            }));
                          }}
                          onChange={(e) => {
                            handleChangeFile(e, setFieldValue);
                          }}
                          value={values.actaCierre}
                          name={"actaCierre"}
                          label={"Cargar informes:"}
                          disabled={isDetalle}
                          helperText={
                            touched.actaCierre ? errors.actaCierre : ""
                          }
                          className={
                            touched.actaCierre &&
                            (errors.actaCierre ? "is-invalid" : "is-valid")
                          }
                        />
                      </Col>
                      <Col>
                        {!isDetalle ? (
                          <Authorization process={"CONT_STA_CIERRE"}>
                            <Button
                              variant="gray"
                              className="btn-sm ms-2 waves-effect waves-light mt-4"
                              type="submit"
                            >
                              Guardar
                            </Button>
                          </Authorization>
                        ) : null}
                      </Col>
                    </Row>
                  </Col>
                  <Col md={6} className="text-end m-auto">
                    {!isDetalle ? (
                      <>
                        <Authorization process={"CONT_STA_CIERRE"}>
                          <Button
                            variant="gray"
                            className="btn-sm ms-2 waves-effect waves-light"
                            onClick={() => handleChangeStatus()}
                          >
                            Cierre
                          </Button>
                        </Authorization>
                      </>
                    ) : null}
                  </Col>
                </Row>
              </Form>
            </>
          );
        }}
      </Formik>
      <SingleBasicModal
        show={showSingleBasicModal}
        title={"Mensaje"}
        approveText={"Aceptar"}
        onHide={() => setShowSingleBasicModal(false)}
        size={"md"}
      >
        {singleBasicMessage}
      </SingleBasicModal>
    </>
  );
}
