import { useFormikContext } from "formik";
import { getClassName, getHelperText, getValue } from "./functions";
import TextArea from "../../components/formInputs/TextArea";
import { Col } from "react-bootstrap";

export const TextAreadArray = ({
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
    <TextArea
      name={name}
      label={label}
      disabled={disabled}
      onChange={onChange || handleChange}
      onBlur={onBlur || handleBlur}
      className={"textarea-array " + className}
      helperText={helperText}
      value={getValue(name, values)}
      maxLength={maxLength}
      placeholder={placeholder}
    />
  );

  return (
    <>
      <style>
        {`
      .textarea-array {
        resize: both; 
        max-width: 310%;
        min-width:100%;
        max-height:200%;
      }
    `}
      </style>
      {md ? <Col md={md}>{responseComponent}</Col> : <> {responseComponent}</>}
    </>
  );
};
