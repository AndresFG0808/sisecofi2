import React from 'react';
import Form from 'react-bootstrap/Form';

const Select = ({
  name,
  label,
  placeholder,
  disabled,
  readOnly,
  value,
  onBlur,
  onChange,
  options,
  defaultOptionText,
  helperText,
  className,
  keyValue,
  keyTextValue,
  keyStatus,
  onClick,
  hideDisabledOptions
}) => (
  <Form.Group className="mb-3">
    {label && <Form.Label>{label}</Form.Label>}
    <Form.Select
      id={name}
      name={name}
      value={value}
      onBlur={onBlur}
      onChange={onChange}
      onClick={onClick}
      placeholder={placeholder}
      className={className}
      disabled={disabled}
      readOnly={readOnly}
    >
      <option value="">{defaultOptionText}</option>
      {options &&
        options.length > 0 &&
        options.map((item) => {
          const valueOption =
            typeof keyValue === "function" ? keyValue(item) : item[keyValue];
          const labelOption =
            typeof keyTextValue === "function" ? keyTextValue(item) : item[keyTextValue];

          return (
            <option
              key={valueOption}
              value={valueOption}
              style={{
                display: hideDisabledOptions
                  ? item[keyStatus]
                    ? "block"
                    : "none"
                  : null,
              }}
            >
              {labelOption}
            </option>
          );
        })}

    </Form.Select>
    <Form.Control.Feedback type="invalid">{helperText}</Form.Control.Feedback>
    {/* <Form.Control.Feedback type="valid" >{helperText}</Form.Control.Feedback> */}
  </Form.Group>
);

Select.defaultProps = {
  keyValue: 'value',
  keyTextValue: 'textValue',
  defaultOptionText: 'Seleccione una opción',
  helperText: '',
  placeholder: '',
  disabled: false,
  keyStatus: 'estatus',
  hideDisabledOptions: false,
  onClick: () => { },
};

export default Select;
