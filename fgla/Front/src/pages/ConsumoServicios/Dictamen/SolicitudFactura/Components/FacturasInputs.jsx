import { Col } from "react-bootstrap";
import FileField from "../../../../../components/formInputs/FileField";
import TextArea from "../../../../../components/formInputs/TextArea";
import TextFieldDate from "../../../../../components/formInputs/TextFieldDate";
import TextFieldIcon from "../../../../../components/formInputs/TextFieldIcon";
import { useField } from 'formik';
import Select from "../../../../../components/formInputs/Select";
import TextField from "../../../../../components/formInputs/TextField";
import { faDollarSign } from "@fortawesome/free-solid-svg-icons";
import { useFormikContext } from "formik";

export function FileFieldCol({ label, name, md = 3, disabled, onChange }) {

  const [, , helpers] = useField(name);

  const handleChange = (event) => {
    const file = event.currentTarget.files[0];
    helpers.setValue(file);
  };
  return (
    <Col md={3}>
      <FileField
        label={label}
        name={name}
        onChange={handleChange}
      />
    </Col>
  );
}

export function TextAreaCol({ name, label, disabled, md = 4 }) {
  return (
    <Col md={md}>
      <TextArea name={name} label={label} disabled={disabled} />
    </Col>
  );
}

export function DateInputCol({ name, label, disabled, md = 3 }) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();

  return (
    <Col md={md}>
      <TextFieldDate
        label={label}
        name={name}
        value={values[name]}
        onChange={handleChange}
        onBlur={handleBlur}
        // className={touched[name] && (errors[name] ? "is-invalid" : "is-valid")}
        helperText={touched[name] ? errors[name] : ""}
        disabled={disabled}
      />
    </Col>
  );
}

export function TextInputCol({
  name,
  label,
  disabled,
  onChange,
  onBlur,
  showClases = false,
  md = 3,
  placeholder = "",
}) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();

  let className = touched[name] && (errors[name] ? "is-invalid" : "is-valid");

  return (
    <Col md={md}>
      <TextField
        label={label}
        name={name}
        placeholder={placeholder}
        value={values[name]}
        onChange={onChange || handleChange}
        onBlur={onBlur || handleBlur}
        className={showClases ? className : ""}
        helperText={touched[name] ? errors[name] : ""}
        disabled={disabled}
      />
    </Col>
  );
}

export function SelectInputCol({
  name,
  label,
  options,
  disabled,
  showClases = false,
  onChange,
  onBlur,
  md = 3,
}) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();
  let className = touched[name] && (errors[name] ? "is-invalid" : "is-valid");

  return (
    <Col md={md}>
      <Select
        label={label}
        name={name}
        value={values[name]}
        options={options}
        keyValue="primaryKey"
        keyTextValue="nombre"
        onChange={onChange || handleChange}
        onBlur={onBlur || handleBlur}
        className={showClases ? className : ""}
        helperText={touched[name] ? errors[name] : ""}
        disabled={disabled}
      />
    </Col>
  );
}

export function TextFieldIconCol({
  name,
  label,
  onChange,
  onBlur,
  disabled,
  md = 3,
  placeholder = "0.00",
}) {
  const { values, handleChange, handleBlur, errors, touched } =
    useFormikContext();
  return (
    <Col md={md}>
      <TextFieldIcon
        label={label}
        name={name}
        placeholder={placeholder}
        startIcon={faDollarSign}
        value={values[name]}
        onChange={onChange || handleChange}
        onBlur={onBlur || handleBlur}
        className={
          touched.montoAutorizado && errors.montoAutorizado ? "is-invalid" : ""
        }
        helperText={touched.montoAutorizado ? errors.montoAutorizado : ""}
        disabled={disabled}
      />
    </Col>
  );
}
