import { nanoid } from "nanoid";
import * as yup from "yup";

const INITIAL_VALUES = {
  gruposServicio: [{ grupo: "", tipoConsumo: "" }],
};

const gruposServicioSchema = yup.object({
  gruposServicio: yup.array().of(
    yup.object({
      grupo: yup
        .string()
        .required("Dato requerido")
        .max(250, "Se excedió el número de caracteres."),
      tipoConsumo: yup.string().required("Dato requerido"),
    })
  ),
});
const rearrangeData = (data) => {
  return {
    gruposServicio: data?.map((grupo) => ({
      grupo: grupo?.grupoServiciosModel?.grupo,
      tipoConsumo: grupo?.tipoConsumo?.primaryKey,
      idGrupoServicio: grupo?.grupoServiciosModel?.idGrupoServicio,
      UUID: nanoid(),
    })),
  };
};

export { INITIAL_VALUES, gruposServicioSchema, rearrangeData };
