import React, { useRef, useState } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import { FileField, Select } from "../../../../../components/formInputs";
import { Formik } from "formik";
import { usePostCargarLayoutMutation } from "./store";
import Loader from "../../../../../components/Loader";
import { INITIAL_VALUES, layoutSchema, options } from "./utils";
import { useParams, useSearchParams } from "react-router-dom";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import {
  ALTA_CONTRATOS,
  MODIFICAR_CONTRATO,
} from "../../../../../constants/messages";
import { useErrorMessages } from "../../../../../hooks/useErrorMessages";
import { useToast } from "../../../../../hooks/useToast";
import FileFieldValue from "../../../../../extraComponents/formInputsArray/FileFieldValue";

export function LayoutInformes({ isDetalle }) {
  const fileField = useRef();
  const { errorToast } = useToast();
  const { getMessageExists } = useErrorMessages(MODIFICAR_CONTRATO);
  const { idContrato } = useParams();
  const [searchParams] = useSearchParams();
  const idNuevoContrato = searchParams.get("q");
  const [singleBasicMessage, setSingleBasicMessage] = useState("");
  const [showSingleBasic, setShowSingleBasic] = useState(false);
  const [layoutInformes, setLayoutInformes] = useState(INITIAL_VALUES);
  const [cargarLayout, { isLoading: isLoadingCargar }] =
    usePostCargarLayoutMutation();

  const handleChangeFile = (event, setFieldValue) => {
    const newFile = event.target.files[0];
    if (!!newFile) {
      setFieldValue("archivo", newFile);
    } else {
      setFieldValue("archivo", null);
    }
  };
  const handleSubmit = async (values, { resetForm }) => {
    try {
      const data = new FormData();
      data.append("archivo", values.archivo);

      data.append("tipoLayout", values.idSeccionLayout);
      data.append("idContrato", parseInt(idContrato || idNuevoContrato));
      await cargarLayout({ data }).unwrap();
      setSingleBasicMessage(MODIFICAR_CONTRATO.MSG004);
      setShowSingleBasic(true);
      resetForm();
    } catch (error) {
      const mensaje = error?.data?.mensaje?.[0];
      if (getMessageExists(mensaje)) {
        setSingleBasicMessage(mensaje);
        setShowSingleBasic(true);
      } else {
        setSingleBasicMessage("Ocurrió un error.");
        setShowSingleBasic(true);
      }
    }
  };

  return (
    <>
      {isLoadingCargar ? <Loader /> : null}
      <Formik
        enableReinitialize
        initialValues={layoutInformes}
        onSubmit={handleSubmit}
        validationSchema={layoutSchema}
        validateOnMount
      >
        {({
          values,
          isValid,
          touched,
          errors,
          handleSubmit,
          handleChange,
          setFieldValue,
          handleBlur,
        }) => {
          return (
            <>
              <Form autoComplete="off" onSubmit={handleSubmit}>
                <Row>
                  <Col md={4}>
                    <FileFieldValue
                      ref={fileField}
                      name={"archivo"}
                      label={"Cargar informes:"}
                      value={values.archivo}
                      onChange={(e) => {
                        handleChangeFile(e, setFieldValue);
                      }}
                      accept={
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                      }
                      disabled={isDetalle}
                      helperText={touched.archivo ? errors.archivo : ""}
                      onBlur={handleBlur}
                      className={
                        touched.archivo &&
                        (errors.archivo ? "is-invalid" : "is-valid")
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <Select
                      name={"idSeccionLayout"}
                      label={"Sección a cargar:"}
                      options={options}
                      keyValue={"key"}
                      keyTextValue={"display"}
                      value={values.idSeccionLayout}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      disabled={isDetalle}
                      helperText={
                        touched.idSeccionLayout ? errors.idSeccionLayout : ""
                      }
                      className={
                        touched.idSeccionLayout &&
                        (errors.idSeccionLayout ? "is-invalid" : "is-valid")
                      }
                    />
                  </Col>
                  <Col md={4} className="text-end">
                    <Button
                      variant="gray"
                      className="btn-sm ms-2 waves-effect waves-light"
                      type="submit"
                      disabled={isDetalle}
                      onClick={() => {
                        !isValid && errorToast(ALTA_CONTRATOS.MSG001);
                      }}
                    >
                      Cargar
                    </Button>
                  </Col>
                </Row>
              </Form>
            </>
          );
        }}
      </Formik>
      <SingleBasicModal
        size={"md"}
        title={"Mensaje"}
        approveText={"Aceptar"}
        show={showSingleBasic}
        onHide={() => {
          setShowSingleBasic(false);
        }}
      >
        {singleBasicMessage}
      </SingleBasicModal>
    </>
  );
}
