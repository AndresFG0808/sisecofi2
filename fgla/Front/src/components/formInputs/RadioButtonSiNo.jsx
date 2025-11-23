import React, { useState } from 'react';
import { InputGroup, Form } from 'react-bootstrap';

const RadioButtonSiNo = (
  {
    name,
    label,
    disabled,
    value,
    onBlur,
    onChange,
    helperText
  }
) => {
  return (

    <Form.Group>
      { label && <Form.Label>{label}</Form.Label> }
      <div key="inline-radio" className="mb-3 py-1">
        <Form.Check
          inline
          //custom
          disabled={disabled}
          type="radio"
          label="Si"
          id={name+ "-"+ true}
          name={name}
          value={true}
          checked={value === true}
          onBlur={onBlur}
          onChange={onChange}
        />
        <Form.Check
          //custom
          inline
          disabled={disabled}
          type="radio"
          label="No"
          id={name + "-"+ false}
          value={false}
          checked={value === false}
          onBlur={onBlur}
          onChange={onChange}
        />
      </div>
    </Form.Group>
  );
};

RadioButtonSiNo.defaultProps = {
  helperText: '',
  placeholder: '',
  disabled: false,
  value: false,
};
export default RadioButtonSiNo;
