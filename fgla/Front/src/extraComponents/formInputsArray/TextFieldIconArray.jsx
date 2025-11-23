import { useFormikContext } from "formik";
import { getClassName, getHelperText, getValue } from "./functions";
import { Col } from "react-bootstrap";
import TextFieldIcon from "../../components/formInputs/TextFieldIcon";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
export const TextFieldIconArray = ({
  label,
  disabled,
  onChange,
  name,
  md,
  onBlur,
  placeholder,
  startIcon,
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
    <TextFieldIcon
      label={label}
      name={name}
      placeholder={placeholder}
      startIcon={startIcon || faDollarSign}
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
