import { useFormikContext } from "formik";
import { getClassName, getHelperText, getValue } from "./functions";
import FileField from "../../components/formInputs/FileField";
import { Col } from "react-bootstrap";

export const FileFieldArray = ({
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
    <FileField
      label={label}
      disabled={disabled}
      accept={accept}
      handleBlur={handleBlur}
      onChange={onChange || handleChange}
      name={name}
      className={className}
      helperText={helperText}
      value={getValue(name, values)}
    />
  );

  return (
    <>
      {md ? <Col md={md}>{responseComponent}</Col> : <> {responseComponent}</>}
    </>
  );
};
