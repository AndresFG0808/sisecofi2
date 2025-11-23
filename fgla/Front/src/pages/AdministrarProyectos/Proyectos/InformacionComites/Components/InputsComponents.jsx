import React, { useEffect } from "react";
import { useFormikContext } from "formik";
import { Select, TextField, TextFieldDate, FormCheckLabel } from "../../../../../components/formInputs";
import { useState } from "react";
import { Col, Row } from "react-bootstrap";
import { Form } from "react-bootstrap";

export const InitialValues = (size = 6) => {
  let initialValues = {
    //Generales
    idComiteProyecto: "",
    idProyecto: "",
    idTipoMoneda: "",
    idContratoConvenio: "",
    idsAfectacion: [],
    idComite: "",
    idPlantilla: "",
    fechaSesion: "",
    idContrato: "",
    idSesionNumero: "",
    idSesionClasificacion: "",
    acuerdo: "",
    vigencia: "",
    montoAutorizado: "",
    tipoCambio: "",
    monto: "",
    comentarios: "",
    estatus: "",
  };

  for (let index = 0; index < size; index++) {
    let name = "afectacion" + index;
    initialValues[name] = "";
  }
  return initialValues;
};
export function SelectInput({
  name,
  label,
  options,
  disableForm,
  showClases = true,
  onChange,
  onBlur,
}) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();
  let className = touched[name] && (errors[name] ? "is-invalid" : "is-valid");

  return (
    <Select
      label={label}
      name={name}
      value={values[name]}
      options={options}
      keyValue="idValue"
      keyTextValue="value"
      onChange={onChange || handleChange}
      onBlur={onBlur || handleBlur}
      className={showClases ? className : ""}
      helperText={touched[name] ? errors[name] : ""}
      disabled={disableForm}
    />
  );
}

export function DateInput({ name, label, disableForm }) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();

  return (
    <TextFieldDate
      label={label}
      name={name}
      value={values[name]}
      onChange={handleChange}
      onBlur={handleBlur}
      className={touched[name] && (errors[name] ? "is-invalid" : "is-valid")}
      helperText={touched[name] ? errors[name] : ""}
      disabled={disableForm}
    />
  );
}

export function TextInput({
  name,
  label,
  disableForm,
  onChange,
  onBlur,
  showClases = true,
}) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();

  let className = touched[name] && (errors[name] ? "is-invalid" : "is-valid");

  return (
    <TextField
      label={label}
      name={name}
      value={values[name]}
      onChange={onChange || handleChange}
      onBlur={onBlur || handleBlur}
      className={showClases ? className : ""}
      helperText={touched[name] ? errors[name] : ""}
      disabled={disableForm}
    />
  );
}

export function TextInputErrors({
  name,
  label,
  disableForm,
  onChange,
  onBlur,
  showClases = true,
}) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();

  let className = touched[name] && (errors[name] ? "is-invalid" : "");

  return (
    <TextField
      label={label}
      name={name}
      value={values[name]}
      onChange={onChange || handleChange}
      onBlur={onBlur || handleBlur}
      className={showClases ? className : ""}
      helperText={touched[name] ? errors[name] : ""}
      disabled={disableForm}
    />
  );
}

export const CheckboxGroup = ({
  options,
  labelTitle,
  name,
  disableForm,
  md = 2,
  value,
  setfieldValues,
}) => {
  const eliminarElemento = (array, elemento) =>
    array.filter((item) => item !== elemento);
  const {
    values,
    handleBlur,
    setFieldValue,
    errors,
    setFieldTouched,
    touched,
  } = useFormikContext();

  const handleCheckboxChange = (event, option) => {
    let actualValue = values[name];

    let checked = event.target.checked;
    let _name = event.target.name;
    setFieldValue(_name, checked);

    if (checked) {
      let newValue = Array.isArray(actualValue)
        ? [...actualValue, option.idValue]
        : [option.idValue];
      setFieldValue(name, [...new Set(newValue)]);
    }
    if (!checked && Array.isArray(actualValue)) {
      let newValue = eliminarElemento(actualValue, option.idValue);
      setFieldValue(name, [...new Set(newValue)]);
    }
    setFieldTouched(name, true);
  };
  const getError = () =>
    errors[name] &&
    touched[name] &&
    (!Array.isArray(values[name]) ||
      (Array.isArray(values[name]) && values[name].length === 0));

  let showError = getError();

  return (
    <>
      {/* {values[name]} */}
      {options.map((option, index) => {
        let _labelTitle = index === 0 ? labelTitle : "‎";
        let _name = "afectacion" + index;
        showError = getError();
        return (
          <Col md={2} key={index} className="check-box-black">
            <FormCheckLabel
              key={option.value}
              labelTitle={_labelTitle}
              label={option.value}
              type="checkbox"
              name={_name}
              disabled={disableForm}
              checked={values[_name]}
              handleBlur={handleBlur}
              className={showError ? "is-invalid" : "is-valid"}
              onChange={(event) => handleCheckboxChange(event, option)}
            />
          </Col>
        );
      })}
      <Form.Control.Feedback
        type="invalid"
        style={{ display: showError ? "block" : "none" }}
      >
        {errors[name]}
      </Form.Control.Feedback>
    </>
  );
};
