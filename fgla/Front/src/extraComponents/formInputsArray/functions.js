import { getIn } from "formik";

export const getClassName = (touched, name, errors) => {
  let _touched = getIn(touched, name);
  let error = getIn(errors, name);

  return _touched ? (_touched && error ? "is-invalid" : "is-valid") : "";
};

export const getHelperText = (touched, name, errors) => {
  let _touched = getIn(touched, name);
  let error = getIn(errors, name);

  return _touched ? (_touched && error ? error : "") : "";
};

export const getValue = (name, values) => getIn(values, name);
