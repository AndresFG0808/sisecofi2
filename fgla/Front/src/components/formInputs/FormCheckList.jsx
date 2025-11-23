import React from "react";
import { useFormikContext } from "formik";
import FormCheckLabel from "./FormCheckLabel";
import { Col, Form } from "react-bootstrap";

const FormCheckList = ({
  options,
  labelTitle,
  name,
  disabled,
  optionLabel,
  optionValue,
  showClasses,
}) => {
  const { values, handleBlur, setFieldValue, errors, touched } =
    useFormikContext();

  const handleChange = (option) => {
    const selectedValues = values[name] || [];
    const optionValueExists = selectedValues.includes(option[optionValue]);

    const newValues = optionValueExists
      ? selectedValues.filter((s) => s !== option[optionValue])
      : [...selectedValues, option[optionValue]];

    setFieldValue(name, newValues);
  };

  const getError = () =>
    errors[name] &&
    touched[name] &&
    (!Array.isArray(values[name]) ||
      (Array.isArray(values[name]) && values[name].length === 0));

  let showError = getError();

  let className = showClasses ? (showError ? "is-invalid" : "is-valid") : "";

  const MapOptions = (options) => {
    if (!Array.isArray(options)) return <></>;

    return options.map((option, index) => {
      let _labelTitle = index === 0 ? labelTitle : "‎";
      let _name = `${name}.${index}`;
      let _checked = values[name]?.includes(option[optionValue]);
      return (
        <Col md={2} key={index} className="check-box-black">
          <FormCheckLabel
            labelTitle={_labelTitle}
            name={_name}
            type="checkbox"
            label={option[optionLabel]}
            checked={_checked}
            onChange={() => handleChange(option)}
            onBlur={handleBlur}
            disabled={disabled}
            className={className}
          />
        </Col>
      );
    });
  };

  return (
    <>
      {MapOptions(options)}
      <Form.Control.Feedback
        type="invalid"
        style={{ display: showError ? "block" : "none" }}
      >
        {errors[name]}
      </Form.Control.Feedback>
    </>
  );
};

FormCheckList.defaultProps = {
  helperText: "",
  placeholder: "",
  disabled: false,
  optionLabel: "nombre",
  optionValue: "primaryKey",
  showClasses: true,
};

export default FormCheckList;
