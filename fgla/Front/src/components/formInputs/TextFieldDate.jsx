import React from "react";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarAlt } from "@fortawesome/free-regular-svg-icons/faCalendarAlt";

const style = {
  padding: {
    paddingBottom: "0px",
    paddingTop: "0px",
  },
};

const TextFieldDate = ({
  name,
  label,
  placeholder,
  disabled,
  className,
  value,
  onBlur,
  onChange,
  helperText,
  minDate = null,
  maxDate = null,
}) => (
  <Form.Group className='mb-3'>
    {label && <Form.Label>{label}</Form.Label>}
    <InputGroup>
      <Form.Control
        id={name}
        name={name}
        as="input"
        type="date"
        value={value}
        onBlur={onBlur}
        onChange={onChange}
        placeholder={placeholder}
        className={className}
        disabled={disabled}
        readOnly={disabled}
        min={minDate}
        max={maxDate}
      // isValid={}
      // isInvalid={}
      />
      {/* <InputGroup.Append>
        <InputGroup.Text style={style.padding}>
          <FontAwesomeIcon icon={faCalendarAlt} className="ms-2" />
        </InputGroup.Text>
      </InputGroup.Append> */}
      <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
    </InputGroup>
  </Form.Group>
);

TextFieldDate.defaultProps = {
  helperText: "",
  placeholder: "",
  disabled: false,
};

export default TextFieldDate;
