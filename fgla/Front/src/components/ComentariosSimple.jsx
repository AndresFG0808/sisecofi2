import React, { useEffect, useState } from "react";
import { Modal, Button } from "react-bootstrap";
import TextArea from "./formInputs/TextArea";
import { Form } from "react-bootstrap";
import * as yup from "yup";

import { Formik } from "formik";
const ComentariosSimple = ({
  show,
  comentario = "",
  handleCancel,
  handleSave,
  title = "Justificación",
  maxLength=50
}) => {
  let _initialValues = {
    comentario: "",
  };

  let validationSchema = yup.object({
    comentario: yup.string().required("Dato requerido"),
  });

  const [initialValues, setinitialValues] = useState(_initialValues);
  useEffect(() => {
    setinitialValues({
      comentario: comentario,
    });
  }, [comentario]);

  return (
    <Modal show={show} onHide={handleCancel} size="lg" centered>
      <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>
      <Formik
        validationSchema={validationSchema}
        initialValues={initialValues}
        onSubmit={(values) => {
          if (values?.comentario) {
            handleSave(values.comentario);
          }
        }}
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          values,
          errors,
          touched,
        }) => {
          return (
            <Form autoComplete="off" onSubmit={handleSubmit}>
              <Modal.Body className="py-1">
                <div className="content-bitacora-dictaminador"></div>
                <TextArea
                  placeholder=""
                  name="comentario"
                  value={values.comentario}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  rows={4}
                  maxLength={maxLength}
                  className={
                    touched.comentario &&
                    (errors.comentario ? "is-invalid" : "is-valid")
                  }
                  helperText={touched.comentario ? errors.comentario : ""}
                />
              </Modal.Body>
              <Modal.Footer>
                <Button
                  variant="green"
                  className="btn-sm ms-2 waves-effect waves-light"
                  type="submit"
                >
                  Aceptar
                </Button>
              </Modal.Footer>
            </Form>
          );
        }}
      </Formik>
    </Modal>
  );
};

export default ComentariosSimple;
