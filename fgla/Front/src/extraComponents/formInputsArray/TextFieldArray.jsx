import { useFormikContext } from "formik";
import { getClassName, getHelperText, getValue } from "./functions";
import TextField from "../../components/formInputs/TextField";
import { Col } from "react-bootstrap";

export const TextFieldArray = ({
  label,
  disabled,
  onChange,
  name,
  md,
  placeholder,
  onBlur,
  maxLength,
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
    <TextField
      label={label}
      name={name}
      placeholder={placeholder}
      onChange={onChange || handleChange}
      onBlur={onBlur || handleBlur}
      className={className}
      helperText={helperText}
      disabled={disabled}
      value={getValue(name, values)}
      maxLength={maxLength}
    />
  );

  return (
    <>
      {md ? <Col md={md}>{responseComponent}</Col> : <> {responseComponent}</>}
    </>
  );
};
