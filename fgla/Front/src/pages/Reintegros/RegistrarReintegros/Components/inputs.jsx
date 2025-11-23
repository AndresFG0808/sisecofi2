import { useFormikContext } from "formik";
import Select from "../../../../components/formInputs/Select";
import TextFieldIcon from "../../../../components/formInputs/TextFieldIcon";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";

export function SelectInput({
  name,
  label,
  options,
  disabled,
  showClasses = true,
  onChange,
  onBlur,
  keyValue = "primaryKey",
  keyTextValue = "nombre",
}) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();

  let className = "";
  let helperText = "";

  if (showClasses) {
    const isTouched = touched[name];
    const hasError = errors[name];
  
    className = isTouched 
      ? (hasError ? "is-invalid" : "is-valid") 
      : "";
  
    helperText = isTouched 
      ? (hasError ? hasError : "") 
      : "";
  }

  return (
    <Select
      label={label}
      name={name}
      value={values[name]}
      options={options}
      keyValue={keyValue}
      keyTextValue={keyTextValue}
      onChange={onChange || handleChange}
      onBlur={onBlur || handleBlur}
      className={className}
      helperText={helperText}
      disabled={disabled}
    />
  );
}

export function IconInput({
  name,
  label,
  options,
  disabled,
  showClasses = true,
  onChange,
  onBlur,
  placeholder,
  value,
  hasError=false
}) {


  let className = "";
  let helperText = "";

  if (hasError) {
    className = hasError ? (!value ? "is-invalid" : "is-valid") : ""
  }

  return (
    <TextFieldIcon
      label={label}
      name={name}
      value={value}
      options={options}
      onChange={onChange }
      onBlur={onBlur }
      className={className}
      helperText={helperText}
      disabled={disabled}
      startIcon={ faDollarSign}
      placeholder={placeholder}
    />
  );
}
