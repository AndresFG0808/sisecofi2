import * as yup from "yup";
import { NOTA_PAGO } from "../../../../../constants/messages";
import moment from "moment";

export const emptyNotaPago = {
  edicion: false,
  idNotaPago: null,
  numeroNotaPago: 0,
  folio: "",
  comprobanteFiscal: "",
  fechaNotaPagocion: "",
  estatus: "",
  moneda: "",
  tasa: "",
  subtotal: "",
  ieps: "",
  iva: "",
  otrosImpuestos: "",
  total: "",
  totalPesos: "",
  comentarios: "",
  porcentajeSat: "",
  montoSat: "",
  montoPesosSat: "",
  porcentajeConvenio: "",
  montoConvenio: "",
  montoPesosConvenio: "",
};

export const validationSchema = yup.object().shape({
  notapagos: yup.array().of(
    yup.object().shape({
      archivoXML: yup.string().required("Dato requerido"),
      archivoPdf: yup.string().required("Dato requerido"),
      folio: yup.string().required("Dato requerido"),
      comprobanteFiscal: yup.string().required("Dato requerido"),
      fechaNotaPagocion: yup.string().required("Dato requerido"),
      moneda: yup.string().required("Dato requerido"),
      tasa: yup.string().required("Dato requerido"),
      subtotal: yup.string().required("Dato requerido"),
      ieps: yup.string().required("Dato requerido"),
      iva: yup.string().required("Dato requerido"),

      montoConvenio: yup
        .string()
        .when("porcentajeConvenio", (value, schema) => {
          if (value[0]) return schema.required("Dato requerido");
          return schema;
        })
        .test(
          "monto-menor-o-igual-total",
          NOTA_PAGO.MSG012,
          function (value) {
            const { totalValue = 0, montoConvenioValue = 0 } = this.parent;
            return !(parseFloat(montoConvenioValue) > parseFloat(totalValue));
          }
        ),
      montoSat: yup
        .string()
        .when("porcentajeConvenio", (value, schema) => {
          if (value[0]) return schema.required("Dato requerido");
          return schema;
        })
        .test(
          "monto-menor-o-igual-total",
          NOTA_PAGO.MSG012,
          function (value) {
            const { totalValue = 0, montoSatValue = 0 } = this.parent;
            return !(parseFloat(montoSatValue) > parseFloat(totalValue));
          }
        ),
      montoPesosSat: yup.string().required("Dato requerido"),
      montoPesosConvenio: yup.string().required("Dato requerido"),
    })
  ),
});

export const FormatMoney = (value, decimales = 2) => {
  try {
    value = value.toString();
    const OPTIONS_MONEY = {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: decimales,
      maximumFractionDigits: decimales,
    };
    const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
    let _value = value.replaceAll(",", "");
    _value = parseFloat(_value, decimales);
    let formatMoney = FORMAT_MONEY.format(_value).split("$")[1];
    return formatMoney;
  } catch (error) {
    return "0.00";
  }
};

function convertirFecha(fecha) {
  const fechaMoment = moment(fecha, "DD/MM/YYYY");
  return fechaMoment.format("YYYY-MM-DD");
}

export const FormatSolicitudPago = (notapago, index) => {
  let { fechaNotaPagocion } = notapago;

  const camposAFormatear = [
    "subtotal",
    "ieps",
    "iva",
    "otrosImpuestos",
    "total",
    "totalPesos",
    "porcentajeSat",
    "montoSat",
    "montoPesosSat",
    "porcentajeConvenio",
    "montoConvenio",
    "montoPesosConvenio",
  ];

  let _values = {
    ...notapago,
    numeroNotaPago: index + 1,
    fechaNotaPagocion: convertirFecha(fechaNotaPagocion),
    fechaNotaPagocionValue: fechaNotaPagocion,
    ...Object.fromEntries(
      camposAFormatear.flatMap((campo) => [
        [campo, notapago[campo] ? FormatMoney(notapago[campo]) : ""],
        [`${campo}Value`, notapago[campo]],
      ])
    ),
  };

  return _values;
};
