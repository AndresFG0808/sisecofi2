import React from 'react';
import Form from 'react-bootstrap/Form';

const FormCheck = ({
  name,
  label,
  placeholder,
  disabled,
  readOnly,
  value,
  onBlur,
  onChange,
  helperText,
  className,
  type="switch"
}) => (
  <Form.Group className="mb-3">
    {label && <Form.Label>{label}</Form.Label>}
    <Form.Check
      id={name}
      name={name}
      onBlur={onBlur}
      onChange={onChange}
      placeholder={placeholder}
      className={className}
      disabled={disabled}
      readOnly={readOnly}
      type={type}
      value={value}
      checked={value}
    >
    </Form.Check>
    <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
  </Form.Group>
);

FormCheck.defaultProps = {
  helperText: '',
  placeholder: '',
  disabled: false,
  value: false
};

export default FormCheck;
