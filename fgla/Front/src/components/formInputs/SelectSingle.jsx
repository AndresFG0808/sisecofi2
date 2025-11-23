import React from 'react';
import Form from 'react-bootstrap/Form';
import Multiselect from 'multiselect-react-dropdown';
import { useField, useFormikContext, getIn } from "formik";

const SelectSingle = ({ ...props }) => {

  const {
    name,
    label,
    placeholder,
    disabled,
    value,
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

  const addDefaultOption = (options) => {
    let defaultOption = {
      [keyValue]: "",
      [keyTextValue]: defaultOptionText
    }
    return [defaultOption, ...options];
  }

  return (
    <>
      {label && <Form.Label>{label}</Form.Label>}
      <Multiselect
        style={"z-index:1000; position: fixed;"}
        displayValue={keyTextValue}
        placeholder='Seleccione una opción'
        singleSelect
        avoidHighlightFirstOption
        showArrow
        customArrow={true}
        onKeyPressFn={function noRefCheck() { }}
        onRemove={function noRefCheck() { }}
        onSearch={function noRefCheck() { }}
        selectedValues={value !== "" ? options.filter(element => element[keyValue] === value) : null}
        onSelect={
          ([selected]) => {
            console.log("onSelect > selected", selected);
            setFieldValue(field.name, selected[keyValue]);
          }
        }
        options={addDefaultOption(options)}
        showCheckbox
      />
    </>
  )
};

SelectSingle.defaultProps = {
  keyValue: 'value',
  keyTextValue: 'textValue',
  defaultOptionText: 'Seleccione una opción',
  helperText: '',
  placeholder: '',
  disabled: false,
};

export default SelectSingle;
