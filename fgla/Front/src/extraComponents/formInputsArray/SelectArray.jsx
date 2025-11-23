import { useFormikContext } from "formik";
import { getClassName, getHelperText, getValue } from "./functions";
import { Col } from "react-bootstrap";
import Select from "../../components/formInputs/Select";

export const SelectArray = ({
  label,
  disabled,
  onChange,
  name,
  md,
  options,
  onBlur,
  keyValue = "primaryKey",
  keyTextValue = "nombre",
  showClasses = true,
}) => {
  const { handleBlur, errors, touched, values, handleChange } =
    useFormikContext();
  let className = "";
  let helperText = "";
  if (showClasses) {
    className = getClassName(touched, name, errors);
    helperText = getHelperText(touched, name, errors);
  }

  let responseComponent = (
    <Select
      label={label}
      name={name}
      options={options}
      keyValue={keyValue}
      keyTextValue={keyTextValue}
      onChange={onChange || handleChange}
      onBlur={onBlur || handleBlur}
      className={className}
      helperText={helperText}
      disabled={disabled}
      value={getValue(name, values)}
    />
  );

  return (
    <>
      {md ? <Col md={md}>{responseComponent}</Col> : <> {responseComponent}</>}
    </>
  );
};
