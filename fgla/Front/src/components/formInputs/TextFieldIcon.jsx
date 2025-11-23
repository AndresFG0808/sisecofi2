import React from 'react';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const TextFieldIcon = ({
  name,
  label,
  placeholder,
  disabled,
  readOnly,
  className,
  value,
  onBlur,
  onChange,
  onFocus,
  helperText,
  startIcon,
  endIcon,
  type,
}) => (
  <Form.Group className='mb-3'>
    {label && <Form.Label>{label}</Form.Label>}
    <InputGroup>
      {
        startIcon &&
        <InputGroup.Text className="form-icon-start">
          <FontAwesomeIcon icon={startIcon} />
        </InputGroup.Text>
      }
      <Form.Control
        id={name}
        name={name}
        as="input"
        type={type}
        value={value}
        onBlur={onBlur}
        onChange={onChange}
        placeholder={placeholder}
        className={`${startIcon ? 'input-icon-left' : ''}${endIcon ? 'input-icon-right' : ''}${className ? ' ' + className : ''}`}
        disabled={disabled}
        readOnly={readOnly}
        onFocus={onFocus}
      />
      {
        endIcon &&
        <InputGroup.Text className='input-icon-end' >
          <FontAwesomeIcon icon={endIcon} />
        </InputGroup.Text>
      }
      <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
    </InputGroup>
  </Form.Group>
);

TextFieldIcon.defaultProps = {
  type: 'text',
  helperText: '',
  placeholder: '',
  disabled: false,
};

export default TextFieldIcon;
