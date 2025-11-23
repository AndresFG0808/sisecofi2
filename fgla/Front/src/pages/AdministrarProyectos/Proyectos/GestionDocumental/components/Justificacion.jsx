import React, { useEffect, useState } from "react";
import { Modal, Button } from "react-bootstrap";
import TextArea from "../../../../../components/formInputs/TextArea";
import { Form } from "react-bootstrap";
import { logError } from '../../../../../components/utils/logError.js';
import * as yup from "yup";

import { Formik } from "formik";

const validationSchema = yup.object({
  justificacion: yup
    .string()
    .required("Dato requerido")
    .typeError("Dato requerido"),
});

export function Justificacion({
  editable,
  show,
  justificacion,
  handleCancel,
  handleSave,
  title = "Justificación",
}) {
  const [value, setValue] = useState(justificacion);
  const [errors, setErrors] = useState(false);
  useEffect(() => {
    setValue(justificacion);
    setErrors(!!!justificacion);
  }, [justificacion]);
  const handleSubmit = (event) => {
    event.preventDefault();
    validationSchema
      .validate({ justificacion: value })
      .then((res) => {
        setErrors(false);
        handleSave(value);
      })
      .catch((erro) => {
        setErrors(true);
        handleSave(value);
      });
  };
  const handleChange = (event) => {
    setErrors(!!!event.target.value);
    setValue(event.target.value);
  };
  const handleBlur = (event) => {
    validationSchema
      .validate({ justificacion: event.target.value })
      .then((res) => {
        setErrors(false);
      })
      .catch((erro) => {
        logError(erro);
        setErrors(true);
      });
  };
  return (
    <>
      <Modal
        show={show}
        onHide={() => {
          handleCancel(value);
        }}
        size="lg"
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>{title}</Modal.Title>
        </Modal.Header>
        <Form autoComplete="off" onSubmit={handleSubmit}>
          <Modal.Body className="py-1">
            <div className="content-bitacora-dictaminador"></div>
            <TextArea
              placeholder=""
              name="justificacion"
              value={value}
              onChange={handleChange}
              onBlur={handleBlur}
              rows={4}
              maxLength={500}
              className={errors ? "is-invalid" : "is-valid"}
              helperText={errors ? "Dato requerido." : ""}
              disabled={!editable}
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
      </Modal>
    </>
  );
}
