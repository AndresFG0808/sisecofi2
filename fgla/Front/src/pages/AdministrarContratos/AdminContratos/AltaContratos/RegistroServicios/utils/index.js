import { nanoid } from "nanoid";
import * as yup from "yup";
import { formatCurrency } from "../../../../../../functions/utils";

const template = () => ({
  UUID: nanoid(),
  isNewRow: true,
  id: "",
  grupo: "",
  tipoConsumo: "",
  claveProductoServicio: "",
  conceptoServicio: "",
  tipoUnidad: "",
  cantidadServiciosMinima: "",
  cantidadServicios: "",
  montoMinimo: "",
  montoMaximo: "",
  aplicaIEPS: false,
});

const curateNewRegistros = (data = [], idContrato, isModified = false) => {
  if (data.length <= 0) return [];

  return data.map((registro, index) => ({
    ...(isModified ? { idServicioContrato: registro.idServicioContrato } : {}),
    idContrato: parseInt(idContrato),
    idGrupoServicio: parseInt(registro.grupo),
    concepto: registro.conceptoServicio,
    clave: registro.claveProductoServicio,
    idTipoUnidad: parseInt(registro.tipoUnidad),
    precioUnitario: parseFloat(
      String(registro.precioUnitario).replace(/,/g, "")
    ),
    cantidadMaxima:
      registro?.tipoConsumo === "Volumetría"
        ? parseFloat(String(registro.cantidadServicios).replace(/,/g, ""))
        : parseFloat(String(registro?.montoMaximo).replace(/,/g, "")) /
          parseFloat(String(registro?.precioUnitario).replace(/,/g, "")),
    cantidadMinima:
      registro?.tipoConsumo === "Volumetría"
        ? parseFloat(String(registro.cantidadServiciosMinima).replace(/,/g, ""))
        : parseFloat(String(registro?.montoMinimo).replace(/,/g, "")) /
          parseFloat(String(registro?.precioUnitario).replace(/,/g, "")),

    montoMinimo:
      registro?.tipoConsumo === "Bolsa"
        ? parseFloat(String(registro.montoMinimo).replace(/,/g, ""))
        : parseFloat(
            String(registro?.cantidadServiciosMinima).replace(/,/g, "")
          ) * parseFloat(String(registro?.precioUnitario).replace(/,/g, "")),
    montoMaximo:
      registro?.tipoConsumo === "Bolsa"
        ? parseFloat(String(registro.montoMaximo).replace(/,/g, ""))
        : parseFloat(String(registro?.cantidadServicios).replace(/,/g, "")) *
          parseFloat(String(registro?.precioUnitario).replace(/,/g, "")),
    ieps: registro.aplicaIEPS,
    orden: registro.orden,
  }));
};
const rearrangeServicios = (data, idContrato) => {
  if (!data) return [];
  return data
    .map((registro) => ({
      isNewRow: false,
      UUID: nanoid(),
      idContrato: parseInt(idContrato),
      grupo: registro.idGrupoServicio,
      grupoDisplay: registro.grupo,
      tipoConsumo: registro.tipoConsumo,
      claveProductoServicio: registro.claveProductosServicios,
      conceptoServicio: registro.conseptosServicio,
      tipoUnidad: registro.idTipoUnidad,
      tipoUnidadDisplay: registro.tipoUnidad,
      precioUnitario: registro.precioUnitario,
      cantidadServiciosMinima: registro.cantidadServiciosMinima,
      cantidadServicios: registro.cantidadServicios,
      montoMinimo: registro.montoMinimo,
      montoMaximo: registro.montoMaximo,
      aplicaIEPS: registro.aplicaDns,
      idServicioContrato: registro.idServicioContrato,
      orden: registro.orden,
    }))
    .sort((a, b) => a?.orden - b?.orden);
};

const validationTipoConsumo = (conditional = [], type = "") =>
  yup.string().when(conditional, (values, schema) => {
    if (values.find((s) => s && s === type)) {
      return schema.required("Dato requerido");
    }
    return schema.nullable();
  });

const serviciosSchema = yup.object().shape({
  grupo: yup.string().required("Dato requerido.").typeError("Dato requerido"),
  claveProductoServicio: yup
    .string()
    .max(12, "Se excedió el número de caracteres."),
  conceptoServicio: yup
    .string()
    .required("Dato requerido.")
    .max(250, "Se excedió el número de caracteres.")
    .typeError("Dato requerido"),
  tipoUnidad: yup
    .string()
    .required("Dato requerido.")
    .typeError("Dato requerido"),
  precioUnitario: yup
    .string()
    .required("Dato requerido.")
    .typeError("Dato requerido"),
  cantidadServiciosMinima: validationTipoConsumo(["tipoConsumo"], "Volumetría"),
  cantidadServicios: validationTipoConsumo(["tipoConsumo"], "Volumetría"),
  montoMinimo: validationTipoConsumo(["tipoConsumo"], "Bolsa"),
  montoMaximo: validationTipoConsumo(["tipoConsumo"], "Bolsa"),
});

const registroServiciosValidation = async (data) => {
  let dataErrors = false;
  let resultados = await Promise.all(
    data.map(async (obj) => {
      try {
        await serviciosSchema.validate(obj, { abortEarly: false });
        return obj;
      } catch (error) {
        dataErrors = true;
        return {
          ...obj,
          errors: error.inner.reduce((prev, currentError) => {
            prev[currentError.path] = currentError.message;
            return prev;
          }, {}),
        };
      }
    })
  );
  return { dataErrors, resultados };
};
export {
  template,
  curateNewRegistros,
  rearrangeServicios,
  registroServiciosValidation,
};
