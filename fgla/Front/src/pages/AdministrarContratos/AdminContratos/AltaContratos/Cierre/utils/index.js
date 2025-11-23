import * as yup from "yup";

export const cierreSchema = yup.object().shape({
  actaCierre: yup.mixed().required("Dato requerido."),
});
export const INITIAL_VALUES = {
  actaCierre: null,
  nombreArchivo: "",
};
