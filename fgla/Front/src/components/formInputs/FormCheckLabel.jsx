import React from "react";
import Form from "react-bootstrap/Form";

const FormCheckLabel = ({
  name,
  label,
  labelTitle,
  placeholder,
  disabled,
  readOnly,
  value,
  onBlur,
  onChange,
  helperText,
  className,
  checked,
  type = "switch",
}) => (
  <>
    <Form.Group className="mb-3">
      {labelTitle && <Form.Label>{labelTitle}</Form.Label>}
      <div className="d-flex">
        <Form.Check
          id={name}
          name={name}
          onBlur={onBlur}
          onChange={onChange}
          placeholder={placeholder}
          className={className}
          disabled={disabled}
          readOnly={readOnly}
          checked={checked}
          type={type}
          value={value}
          isInvalid={className === "is-invalid"}
          isValid={className === "is-valid"}
        />
        <label htmlFor={name} className="ms-2">
          {label}
        </label>
      </div>

      <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
    </Form.Group>
  </>
);

FormCheckLabel.defaultProps = {
  helperText: "",
  placeholder: "",
  disabled: false,
  value: false,
};

export default FormCheckLabel;
