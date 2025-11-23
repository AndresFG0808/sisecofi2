import React from 'react';
import Form from 'react-bootstrap/Form';
import Multiselect from 'multiselect-react-dropdown';
import { useField, useFormikContext } from "formik";

const SelectMultiple = ({ ...props }) => {

  const {
    name,
    label,
    placeholder,
    disabled,
    values,
    onBlur,
    onChange,
    options,
    defaultOptionText,
    helperText,
    className,
    keyValue,
    keyTextValue
  } = props;
  const { setFieldValue } = useFormikContext();
  const [field] = useField(props);

  return (
    <>
      {label && <Form.Label>{label}</Form.Label>}
      <Multiselect
        className="select-multiple-field"
        style={"z-index:1000; position: fixed;"}
        displayValue={keyTextValue}
        placeholder='Seleccione una opción'
        hideSelectedList
        avoidHighlightFirstOption
        disable={disabled}
        showArrow
        customArrow={true}
        onKeyPressFn={function noRefCheck() { }}
        onRemove={
          (options) => {
            const selectedOptions = options?.map(option => option[keyValue]);
            setFieldValue(field.name, selectedOptions);
            onChange(selectedOptions);
            console.log("onRemove selectedOptions ==>", selectedOptions);
          }
        }
        onSearch={function noRefCheck() { }}
        selectedValues={(values !== null && values !== "") && options?.filter(element => values?.includes(element[keyValue]))}
        onSelect={
          (options) => {
            const selectedOptions = options?.map(option => option[keyValue]);
            setFieldValue(field.name, selectedOptions);
            onChange(selectedOptions);
            console.log("onRemove selectedOptions ==>", selectedOptions);
          }
        }
        options={options}
        showCheckbox
      />
    </>
  )
};

SelectMultiple.defaultProps = {
  keyValue: 'value',
  keyTextValue: 'textValue',
  defaultOptionText: 'Seleccione una opción',
  helperText: '',
  placeholder: '',
  disabled: false,
};

export default SelectMultiple;
