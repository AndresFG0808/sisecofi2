import React from 'react';
import Form from 'react-bootstrap/Form';

const TextField = ({
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
  onkeypress,
  onKeyDown,
  autoComplete,
  onKeyUp,
  list,
  onFocus
}) => (
  <Form.Group className='mb-3'>
    { label && <Form.Label>{label}</Form.Label> }
    <Form.Control
      id={name}
      name={name}
      as="input"
      style={style}
      type={type}
      value={value}
      onBlur={onBlur}
      onChange={onChange}
      placeholder={placeholder}
      className={className}
      disabled={disabled}
      readOnly={readOnly}
      maxLength={maxLength}
      onKeyPress={onkeypress}
      onKeyDown={onKeyDown}
      autoComplete={autoComplete}
      onKeyUp={onKeyUp}
      list={list}
      onFocus={onFocus}
      // isValid={}
      // isInvalid={}
    />
    <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
    {/* <Form.Control.Feedback type="valid" >{helperText}</Form.Control.Feedback> */}
  </Form.Group>
);

TextField.defaultProps = {
  helperText: '',
  placeholder: '',
  disabled: false,
  type: 'text',
};

export default TextField;
