import React from 'react';
import Form from 'react-bootstrap/Form';
import InputMask from 'react-input-mask';
import { NumericFormat } from 'react-number-format';

const TextFieldNumber = ({
  as,
  mask,
  name,
  label,
  placeholder,
  disabled,
  readOnly,
  className,
  value,
  onBlur,
  onChange,
  helperText,
  type,
  style,
  maxLength,
}) => (
  <Form.Group className='mb-3'>
    {label && <Form.Label>{label}</Form.Label>}
    <Form.Control
      id={name}
      mask={mask}
      name={name}
      as={NumericFormat}
      allowNegative={false}
      displayType="input"
      onValueChange={(values) => {
        value = values.value;
      }}
      style={style}
      type={type}
      value={value}
      onBlur={onBlur}
      onChange={onChange}
      placeholder={placeholder}
      disabled={disabled}
      readOnly={readOnly}
      maxLength={maxLength}
      className={className}
    // isValid={}
    // isInvalid={}
    />
    <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
    {/* <Form.Control.Feedback type="valid" >{helperText}</Form.Control.Feedback> */}
  </Form.Group>
);

TextFieldNumber.defaultProps = {
  helperText: '',
  placeholder: '',
  disabled: false,
  type: 'text',
};

export default TextFieldNumber;
