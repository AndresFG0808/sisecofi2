import React from 'react';
import Form from 'react-bootstrap/Form';

const TextArea = ({
  name,
  label,
  placeholder,
  disabled,
  className,
  value,
  onBlur,
  onChange,
  helperText,
  rows,
  onKeyUp,
  maxLength
}) => (
  <Form.Group className='mb-3'>
    {label && <Form.Label>{label}</Form.Label>}
    <Form.Control
      id={name}
      name={name}
      as="textarea"
      rows={rows}
      value={value}
      onBlur={onBlur}
      onChange={onChange}
      placeholder={placeholder}
      className={className}
      disabled={disabled}
      readOnly={disabled}
      onKeyUp={onKeyUp}
      maxLength={maxLength}
      // isValid={}
      // isInvalid={}
    />
    <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
    {/* <Form.Control.Feedback type="valid" >{helperText}</Form.Control.Feedback> */}
  </Form.Group>
);

TextArea.defaultProps = {
  helperText: '',
  placeholder: '',
  disabled: false,
};

export default TextArea;
