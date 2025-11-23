import { useFormikContext } from "formik";
import { getClassName, getHelperText, getValue } from "./functions";
import TextFieldDate from "../../components/formInputs/TextFieldDate";
import { Col } from "react-bootstrap";

export const TextFieldDateArray = ({
  label,
  disabled,
  onChange,
  accept,
  name,
  md,
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
    <TextFieldDate
      label={label}
      name={name}
      onChange={onChange || handleChange}
      onBlur={handleBlur}
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
