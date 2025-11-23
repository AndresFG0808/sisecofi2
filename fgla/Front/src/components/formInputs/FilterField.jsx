import React from 'react';
import Form from 'react-bootstrap/Form';

const FilterField = ({
  name,
  label,
  placeholder,
  disabled,
  readOnly,
  className,
  value,
  onBlur,
  onChange,
  type,
  style,
  maxLength,
  onkeypress,
  autoComplete,
  onKeyUp,
  list
}) => (
  <Form.Group className='mt-1'>
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
      autoComplete={autoComplete}
      onKeyUp={onKeyUp}
      list={list}
      // isValid={}
      // isInvalid={}
    />
  </Form.Group>
);

FilterField.defaultProps = {
  helperText: '',
  placeholder: '',
  disabled: false,
  type: 'text',
};

export default FilterField;
