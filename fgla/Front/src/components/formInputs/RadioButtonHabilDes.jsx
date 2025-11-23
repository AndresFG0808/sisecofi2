import React, { useState } from 'react';
import { InputGroup, Form } from 'react-bootstrap';

const RadioButtonHabilDes= (props) =>
// label,
// id,
// name,
// value,
// helperText,
// className,
// onBlur,
// setFieldValue,
{
  return (
    <InputGroup>
      <Form.Label className="me-2">{props.label}</Form.Label>
      <div key="inline-radio" className="mb-3">
        <Form.Check
          inline
          //custom
           disabled={props.disabled}
          type="radio"
          label="Habilitado"
          id={props.id + '_si'}
          name={props.name}
          value={true}
          checked={props.value === true}
          onBlur={props.onBlur}
          onChange={() => props.setFieldValue(props.name, true)}
        />
        <Form.Check
          //custom
          inline
          // disabled={props.disabled}
          type="radio"
          disabled={props.disabled}
          label="Deshabilitado"
          id={props.id + '_no'}
          value={false}
          checked={props.value === false}
          onBlur={props.onBlur}
          onChange={() => props.setFieldValue(props.name, false)}
        />
      </div>
    </InputGroup>
  );
};

RadioButtonHabilDes.defaultProps = {
  helperText: '',
  placeholder: '',
  disabled: false,
  value: 0,
};
export default RadioButtonHabilDes;
