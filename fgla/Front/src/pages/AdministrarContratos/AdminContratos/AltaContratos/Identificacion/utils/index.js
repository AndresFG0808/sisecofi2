import * as yup from "yup";

const INITIAL_VALUES = {
  idContrato: null,
  idProyecto: "",
  nombreContrato: "",
  nombreCortoContrato: "",
  estatusContrato: null,
};

const identificacionSchema = yup.object({
  idProyecto: yup.string().required("Dato requerido"),
  nombreContrato: yup.string().required("Dato requerido"),
  nombreCortoContrato: yup.string().required("Dato requerido"),
});
const rearrangeContrato = (newData, oldData) => {
  return {
    ...oldData,
    idContrato: newData.idContratoFormato,
    estatusContrato: newData.estatusContrato,
  };
};

export { INITIAL_VALUES, identificacionSchema, rearrangeContrato };
