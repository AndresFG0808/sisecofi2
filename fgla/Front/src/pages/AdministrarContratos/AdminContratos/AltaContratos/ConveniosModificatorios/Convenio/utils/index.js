import moment from "moment";
import * as yup from "yup";
import { FormatMoney } from "../../../../../../../functions/utils";

const format = "YYYY-MM-DD";
function isValidDate(value) {
  return moment(value, moment.ISO_8601, true).isValid();
}
export const calculateDifferenceDays = (initDate, endDate) => {
  if (!isValidDate(initDate) || !isValidDate(endDate)) return 0;
  const start = moment(initDate, format);
  const end = moment(endDate, format);
  const result = start.diff(end, "days");
  return result < 0 ? 0 : start.diff(end, "days") + 1;
};
export const INITIAL_VALUES = {
  numeroConvenio: "",
  alcance: false,
  monto: false,
  tiempo: false,
  administrativo: false,
  fechaFirma: "",
  fechaFinServicio: "",
  fechaFinContratoCM: "",
  calculoDiasNaturales: "",
  incremento: "",
  subtotal: "",
  ieps: "",
  iva: "",
  ivaMonto: "",
  tipoCambio: "",
  montoMaximoContratoCMSinImpuestos: "",
  montoMaximoContratoCMConImpuestos: "",
  montoPesos: "",
  comentarios: "",
  tipoConvenio: "",
};
const getSelectedFields = (state) => {
  return Object.entries(state)
    .filter(([key, value]) => value === true)
    .map(([key]) => key);
};

export const rearrangeRegistro = (data, idContrato) => {
  const tipoConvenio = getSelectedFields(data);

  return {
    numeroConvenio: data?.numeroConvenio,
    fechaFirma: moment(new Date(data?.fechaFirma)).toISOString(),
    fechaInicio: moment(new Date(data?.fechaInicio)).toISOString(),
    ...(data?.fechaFin
      ? { fechaFin: moment(new Date(data?.fechaFinContratoCM)).toISOString() }
      : { fechaFin: null }),
    ...(data?.fechaFinServicio
      ? {
          fechaFinServicio: moment(
            new Date(data?.fechaFinServicio)
          ).toISOString(),
        }
      : { fechaFinServicio: null }),
    incremento: data?.incremento
      ? parseFloat(String(data?.incremento).replace(/,/g, ""))
      : "",
    tipoCambio: data?.tipoCambio
      ? parseFloat(String(data?.tipoCambio).replace(/,/g, ""))
      : "",
    subtotal: parseFloat(data?.subtotal),
    ieps: data?.ieps ? parseFloat(String(data?.ieps).replace(/,/g, "")) : 0,
    idIva: parseInt(data?.iva),
    comentarios: data?.comentarios,
    tipoConvenio: tipoConvenio,
    idContrato: parseInt(idContrato),
    ultimaModificacion: moment(new Date()).toISOString(),
    montoMaximoSinImpuestos: parseFloat(String(data?.montoMaximoContratoCMSinImpuestos).replaceAll(",", "")),
    montoMaximoConImpuestos: parseFloat(String(data?.montoMaximoContratoCMConImpuestos).replaceAll(",", "")),
    montoPesos: parseFloat(String(data?.montoPesos).replaceAll(",", ""))
  };
};

export const rearrangeConvenio = (data) => {
  const { tipoConvenio } = data;
  const result = tipoConvenio.reduce((acc, item) => {
    acc[item.toLowerCase()] = true;
    return acc;
  }, {});
  return {
    numeroConvenio: data.numeroConvenio,
    fechaFirma: moment(new Date(data.fechaFirma)).format(format),
    fechaFinServicio: data.fechaFinServicio
      ? moment(new Date(data.fechaFinServicio)).format(format)
      : "",
    fechaFinContratoCM: data.fechaFin
      ? moment(new Date(data.fechaFin)).format(format)
      : "",
    calculoDiasNaturales: data.calculoDias,
    incremento: data?.incremento ? FormatMoney(data.incremento, 2) : "",
    subtotal: data.subtotal,
    ieps: data.ieps ? data.ieps : 0,
    iva: data.idIva,
    ivaMonto: data.ivaMonto,
    tipoCambio: data.tipoCambio,
    montoMaximoContratoCMSinImpuestos: data.montoMaximoSinImpuestos,
    montoMaximoContratoCMConImpuestos: data.montoMaximoConImpuestos,
    montoPesos: data.montoPesos,
    comentarios: data.comentarios,
    ...result,
  };
};

export const rearrangeNewConvenio = (prev, data) => {
  return {
    ...prev,
    // fechaFinServicio: data.fechaFinVigenciaServicios
    //   ? moment(new Date(data.fechaFinVigenciaServicios)).format(format)
    //   : "",
    // fechaFinContratoCM: data.fechaFinVigenciaContrato
    //   ? moment(new Date(data.fechaFinVigenciaContrato)).format(format)
    //   : "",
    subtotal: data?.montoMaximoSinImpuestos,
    ieps: data.ieps ? data.ieps : 0,
    iva: data.catIva.primaryKey,
    ivaMonto: data.ivaCantidad,
    montoMaximoContratoCMSinImpuestos: data.montoMaximoSinImpuestos,
  };
};
export const REGISTRO_CONVENIO = {
  MSG001:
    "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG002: "Se agregó correctamente el convenio modificatorio al sistema.",
  MSG003: "El convenio modificatorio ya se encuentra registrado en el sistema.",
  MSG004: "Se perderá la información ingresada. ¿Está seguro de cancelar?",
  MSG005:
    "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG006: "El número de convenio debe empezar con el número de contrato.",
};
export const EDITAR_CONVENIOS = {
  MSG001:
    "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG002: "Se cargó la proyección al convenio modificatorio.",
  MSG003:
    "El “Monto máximo del contrato con CM sin impuestos” coincide con el “Monto máximo total” de los servicios.",
  MSG004: "Se perderá la información ingresada. ¿Está seguro de cancelar?",
  MSG005:
    "El layout de carga no contiene la estructura requerida, favor de verificar.",
  MSG006:
    "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG007:
    "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG008:
    "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG009: "Existe una proyección previamente cargada, ¿desea actualizarla?",
  MSG010: "El convenio modificatorio fue actualizado exitosamente.",
  MSG011: "Se guardo correctamente la información.",
  MSG012:
    "El “Monto máximo del contrato con CM sin impuestos” no coincide con el “Monto máximo total” de los servicios.",
  MSG013:
    "Verifique el layout de carga, ya que la línea(s) [Concepto de servicio] sobrepasa el “Número total de servicios”.",
  MSG014: "Se guardó correctamente la información.",
  MSG015: "Se eliminará el registro seleccionado. ¿Desea continuar?",
  MSG016:
    "El registro no se puede eliminar porque se encuentra relacionado en otro módulo.",
  MSG017:
    "Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).",
  MSG018: "Ocurrió un error.",
};

export const registroSchema = yup
  .object({
    numeroConvenio: yup.string().required("Dato requerido"),
    alcance: yup.boolean(),
    monto: yup.boolean(),
    tiempo: yup.boolean(),
    administrativo: yup.boolean(),
    fechaFirma: yup.string(),
    fechaFinServicio: yup.string(),
    fechaFinContratoCM: yup.string(),
    incremento: yup.string(),
    ieps: yup.string().required("Dato requerido"),
    iva: yup.string().required("Dato requerido"),
  })
  .test(
    "at-least-one-selected",
    "Debe seleccionar al menos uno de: alcance, monto, tiempo, administrativo",
    function (value) {
      const { alcance, monto, tiempo, administrativo } = value;
      return alcance || monto || tiempo || administrativo;
    }
  )
  .when(["alcance", "monto", "tiempo", "administrativo"], {
    is: (alcance, monto, tiempo, administrativo) =>
      alcance || monto || tiempo || administrativo,
    then: yup.object().shape({
      fechaFirma: yup.string().required("Dato requerido"),
    }),
  })
  .when("monto", {
    is: true,
    then: yup.object().shape({
      incremento: yup.string().required("Dato requerido"),
      tipoCambio: yup.string().required("Dato requerido"),
    }),
  })
  .when("tiempo", {
    is: true,
    then: yup.object().shape({
      fechaFinServicio: yup.string().required("Dato requerido"),
      fechaFinContratoCM: yup.string().required("Dato requerido"),
    }),
  });
