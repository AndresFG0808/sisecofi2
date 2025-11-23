import React, { useEffect, forwardRef } from 'react';
import { Form } from 'react-bootstrap';
import bsCustomFileInput from 'bs-custom-file-input';

const FileField = forwardRef(
  (
    { name,
      label,
      className,
      disabled,
      onBlur,
      onChange,
      helperText,
      accept,
      multiple,
      placeholder
    },
    ref
  ) => {

    useEffect(() => {
      bsCustomFileInput.init();
    }, []);

    return (
      <Form.Group className='mb-3'>
        {label && <Form.Label>{label}</Form.Label>}
        <Form.Control
          ref={ref}
          custom
          type="file"
          id={name}
          name={name}
          onBlur={onBlur}
          onChange={onChange}
          className={className}
          disabled={disabled}
          readOnly={disabled}
          accept={accept}
          multiple={multiple}
          placeholder={placeholder}
        />
        <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
      </Form.Group>
    );
  });

FileField.defaultProps = {
  helperText: '',
  placeholder: 'Elegir',
  disabled: false,
  accept: "application/pdf",
  multiple: false
};

export default FileField;
