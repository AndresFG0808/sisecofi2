import * as yup from "yup";
import moment from "moment";
import { FormatMoney } from "../../../../../../functions/utils";
const format = "YYYY-MM-DD";

const INITIAL_VALUES = {
  idContrato: null,
  fechaInicioVigenciaServicios: "",
  fechaFinVigenciaServicios: "",
  duracionServicios: 0,
  fechaInicioVigenciaContrato: "",
  fechaFinVigenciaContrato: "",
  idTipoMoneda: "",
  tipoCambioMaximo: "",
  aplicaIva: false,
  id_iva: "",
  idIeps: "",
  montoMinimoSinImpuestos: "",
  montoMaximoSinImpuestos: "",
  montoPesosSinImpuestos: "",
  montoMinimoConImpuestos: "",
  montoMaximoConImpuestos: "",
  montoPesosConImpuestos: "",
};
const vigenciaMontosSchema = yup.object({
  fechaInicioVigenciaServicios: yup.date().required("Dato requerido"),
  fechaFinVigenciaServicios: yup
    .date()
    .required("Dato requerido")
    .test(
      "is-greater",
      "Fecha de fin debe ser mayor o igual a la fecha de inicio",
      function (value) {
        const { fechaInicioVigenciaServicios } = this.parent;
        if (!fechaInicioVigenciaServicios || !value) return true;
        return value >= fechaInicioVigenciaServicios;
      }
    ),
  fechaInicioVigenciaContrato: yup.date().required("Dato requerido"),
  fechaFinVigenciaContrato: yup
    .date()
    .required("Dato requerido")
    .test(
      "is-greater",
      "Fecha de fin debe ser mayor o igual a la fecha de inicio",
      function (value) {
        const { fechaInicioVigenciaContrato } = this.parent;
        if (!fechaInicioVigenciaContrato || !value) return true;
        return value >= fechaInicioVigenciaContrato;
      }
    ),
  idTipoMoneda: yup.string().required("Dato requerido"),
  aplicaIva: yup
    .boolean()
    .oneOf([true], "Dato requerido")
    .required("Dato requerido"),
  id_iva: yup.string().required("Dato requerido"),
  montoMinimoSinImpuestos: yup.string().required("Dato requerido"),
  montoMaximoSinImpuestos: yup.string().required("Dato requerido"),
  montoMinimoConImpuestos: yup.string().required("Dato requerido"),
  montoMaximoConImpuestos: yup.string().required("Dato requerido"),
});
const calculateDifferenceDays = (initDate, endDate) => {
  if (!isValidDate(initDate) || !isValidDate(endDate)) return 0;
  const start = moment(initDate, format);
  const end = moment(endDate, format);
  const result = end.diff(start, "days");
  return result < 0 ? 0 : end.diff(start, "days") + 1;
};
const rearrangeVigenciaMontos = (data) => {
  return {
    ...data?.vigenciaMontosModel,
    tipoCambioMaximo:
      data.vigenciaMontosModel?.tipoCambioMaximo === null
        ? ""
        : FormatMoney(`${data?.vigenciaMontosModel?.tipoCambioMaximo}`, 4),
    montoMinimoSinImpuestos: FormatMoney(
      `${data?.vigenciaMontosModel?.montoMinimoSinImpuestos}`,
      2
    ),
    montoMaximoSinImpuestos: FormatMoney(
      `${data?.vigenciaMontosModel?.montoMaximoSinImpuestos}`,
      2
    ),
    montoPesosSinImpuestos: FormatMoney(
      `${data?.vigenciaMontosModel?.montoPesosSinImpuestos}`,
      2
    ),
    montoMinimoConImpuestos: FormatMoney(
      `${data?.vigenciaMontosModel?.montoMinimoConImpuestos}`,
      2
    ),
    montoMaximoConImpuestos: FormatMoney(
      `${data?.vigenciaMontosModel?.montoMaximoConImpuestos}`,
      2
    ),
    montoPesosConImpuestos: FormatMoney(
      `${data?.vigenciaMontosModel?.montoPesosConImpuestos}`,
      2
    ),

    fechaInicioVigenciaServicios: moment(
      new Date(data?.vigenciaMontosModel?.fechaInicioVigenciaServicios)
    ).format("YYYY-MM-DD"),
    fechaFinVigenciaServicios: moment(
      new Date(data?.vigenciaMontosModel?.fechaFinVigenciaServicios)
    ).format("YYYY-MM-DD"),
    fechaInicioVigenciaContrato: moment(
      new Date(data?.vigenciaMontosModel?.fechaInicioVigenciaContrato)
    ).format("YYYY-MM-DD"),
    fechaFinVigenciaContrato: moment(
      new Date(data?.vigenciaMontosModel?.fechaFinVigenciaContrato)
    ).format("YYYY-MM-DD"),
    aplicaIva: data.vigenciaMontosModel.id_iva ? true : false,
    idVigenciaMonto: parseInt(data.vigenciaMontosModel.idVigenciaMonto),
  };
};
function filtrarNumeros(cadena) {
  const regex = /[\d.,]+/g;
  const numeros = cadena.match(regex);
  if (!numeros) {
    return ""; // Si no se encuentran números, devolvemos una cadena vacía
  }
  let result = numeros.join("");

  const numerosFiltrados = result;
  return numerosFiltrados;
}
function isValidDate(value) {
  return moment(value, moment.ISO_8601, true).isValid();
}

export {
  INITIAL_VALUES,
  vigenciaMontosSchema,
  calculateDifferenceDays,
  rearrangeVigenciaMontos,
  filtrarNumeros,
};
