import React from "react";
import { Form, Col, Row, Button } from "react-bootstrap";
import { useFormikContext } from "formik";
import TextField from "../../../../components/formInputs/TextField";
import { ADMINISTRAR_USUARIOS_SISTEMA } from "../../../../constants/messages";
import { useToast } from "../../../../hooks/useToast";
import AdministrarUsuariosDownload from "../AdministarUsuariosDownload.jsx/AdministrarUsuariosDownload";

export default function AdministrarUsuariosFilter({ dataTable, actionType }) {
  const { errorToast } = useToast();

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
    }
  };

  const { handleSubmit, values, setFieldValue, isValid } = useFormikContext();
  return (
    <>
      <Form autoComplete="off" onSubmit={handleSubmit}>
        <Row>
          <Col md={8}>
            <Row>
              <Col md={6}>
                <TextInput
                  label={"Nombre(s):"}
                  name={"nombre"}
                  value={values.nombre}
                  onKeyDown={handleKeyDown}
                />
              </Col>
              <Col md={6}>
                <TextInput
                  label={"Primer apellido:"}
                  name={"apellidoPaterno"}
                  value={values.apellidoPaterno}
                  onKeyDown={handleKeyDown}
                />
              </Col>
              <Col md={6}>
                <TextInput
                  label={"Segundo apellido:"}
                  name={"apellidoMaterno"}
                  value={values.apellidoMaterno}
                  onKeyDown={handleKeyDown}
                />
              </Col>
              <Col md={6} className="center">
                <TextInput
                  label={"RFC corto:"}
                  name={"rfcCorto"}
                  value={values.rfcCorto}
                  onKeyDown={handleKeyDown}
                />
              </Col>
            </Row>
          </Col>
          <Col md={4} className="text-end">
            <Row>
              <Col md={12} className="d-flex justify-content-center">
                <Form.Label>Buscar:</Form.Label>
              </Col>
            </Row>
            <Row>
              <Col md={3}></Col>
              <Col md={3} className="d-flex">
                <Button
                  variant="gray"
                  className="btn-sm ms-2 waves-effect waves-light"
                  type="submit"
                  onClick={() => {
                    setFieldValue("actionType", "directorio");
                    if (!isValid) {
                      errorToast(ADMINISTRAR_USUARIOS_SISTEMA.MSG001);
                    }
                  }}
                >
                  DA
                </Button>
              </Col>
              <Col md={3} className="d-flex ">
                <Button
                  variant="gray"
                  className="btn-sm ms-2 waves-effect waves-light"
                  type="submit"
                  onClick={() => {
                    setFieldValue("actionType", "sistema");
                    if (!isValid) {
                      errorToast(ADMINISTRAR_USUARIOS_SISTEMA.MSG001);
                    }
                  }}
                >
                  Sistema
                </Button>
              </Col>
              <Col md={3}></Col>
            </Row>
          </Col>
        </Row>
        <AdministrarUsuariosDownload
          dataTable={dataTable}
          actionType={actionType}
          values={values}
        />
      </Form>
    </>
  );
}

function TextInput({ name, label, disableForm, onChange, onBlur, onKeyDown }) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();

  let hasError = errors && errors[name];
  return (
    <TextField
      label={label}
      name={name}
      value={values[name]}
      onChange={onChange || handleChange}
      onBlur={onBlur || handleBlur}
      className={touched[name] && (hasError ? "is-invalid" : "")}
      helperText={hasError ? errors[name] : ""}
      disabled={disableForm}
      onKeyDown={onKeyDown}
    />
  );
}
