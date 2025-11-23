import * as yup from "yup";
import { NOTA_CREDITO } from "../../../../../constants/messages";
import moment from "moment";
import _ from "lodash";

export const emptyNotaCredito = {
  edicion: false,
  idNotaCredito: null,
  numeroNotaCredito: 1,
  folio: "",
  comprobanteFiscal: "",
  fechaFacturacion: "",
  estatus: "",
  moneda: "",
  idTipoMoneda: "",
  tasa: "",
  subTotal: "",
  ieps: "",
  iva: "",
  otrosImpuestos: "",
  total: "",
  totalPesos: "",
  comentarios: "",
  porcentajeSat: "",
  montoSat: "",
  montoPesosSat: "",
  porcentajeCC: "",
  montoCC: "",
  montoPesosCC: "",
  notaActiva: false,
  idEstatusNotaCredito: "",
  idEstatusNotaCreditoValue: "",
  idTasa: "",
};

//#region FormatData

export const FormatMoney = (value, decimales = 2) => {
  try {
    value = value ? value.toString() : "0";
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
  // Formatos permitidos
  const formatos = ["DD/MM/YYYY", "YYYY-MM-DDTHH:mm:ss"];

  const fechaMoment = moment(fecha, formatos, true);
  if (!fechaMoment.isValid()) {
    return "";
  }
  return fechaMoment.format("YYYY-MM-DD");
}

const findCatId = (cat, value) => {
  if (!_.isEmpty(cat) && value) {
    let catItem = cat.find((s) =>
      [s.nombre, s.descripcion].some(
        (prop) => prop?.toLowerCase() == value.toLowerCase()
      )
    );
    if (catItem) return catItem.primaryKey;
  }
  return "";
};
const findCatIdTasa = (cat, value) => {
  if (!_.isEmpty(cat) && value) {
    let catItem = cat.find((s) =>
      [s.porcentaje, s.descripcion].some(
        (prop) => parseFloat(prop) == parseFloat(value)
      )
    );
    if (catItem) return catItem.primaryKey;
  }
  return value;
};

const findIdStatus = (cat, value) => {
  if (!_.isEmpty(cat)) {
    let catItem = cat.find((s) =>
      [s.nombre, s.descripcion].some(
        (prop) => prop?.toLowerCase() == "aprobada"
      )
    );
    if (catItem) return catItem.primaryKey;
  }
  return value;
};

export const FormatNotaCredito = (
  notaCredito,
  index,
  monedaCat,
  tasaCat,
  estatusCat,
  aplicaCC
) => {
  let _notaCredito = { ...notaCredito };
  let {
    fecha,
    fechaFacturacion,
    fechaGeneracion,
    pathArchivoXml,
    pathPdf,
    idNotaCredito,
    moneda,
    idIva,
    idTipoMoneda,
    idEstatusNotaCredito,
    tasaoCuota,
    xmlLeido,
     archivoCargar="",
    pdf=""
  } = _notaCredito;

  idTipoMoneda = idTipoMoneda || findCatId(monedaCat, moneda);
  let idTasa = findCatIdTasa(tasaCat, tasaoCuota || idIva);

  if (!idNotaCredito) {
    idEstatusNotaCredito = findIdStatus(estatusCat, idEstatusNotaCredito);
  }
  if (idNotaCredito) {
    xmlLeido = true;
  }

  const camposAFormatear = [
    "subTotal",
    "ieps",
    "iva",
    "otrosImpuestos",
    "total",
    "totalPesos",
    "porcentajeSat",
    "montoSat",
    "montoPesosSat",
    "porcentajeCC",
    "montoCC",
    "montoPesosCC",
  ];

  //Validar si se obtiene el tipo de cambio en caso de que no sean pesos
  let _values = {
    ..._notaCredito,
    numeroNotaCredito: index + 1,
    fechaFacturacion: convertirFecha(
      fecha || fechaFacturacion || fechaGeneracion
    ),
    ...Object.fromEntries(
      camposAFormatear.flatMap((campo) => [
        [campo, FormatMoney(_notaCredito[campo])],
        [`${campo}Value`, _notaCredito[campo] || 0],
      ])
    ),
    idTipoMoneda,
    idTasa,
    idEstatusNotaCredito,
    xmlCargado: !!pathArchivoXml,
    pdfCargado: !!pathPdf,
    xmlLeido,
    nombrePdf:pdf,
    nombreXML:archivoCargar,
  };
  if (!aplicaCC) {
    let { total, totalValue, totalPesos, totalPesosValue } = _values;
    _values = {
      ..._values,
      porcentajeSat: 100,
      porcentajeSatValue: 100,
      montoSat: total,
      montoSatValue: totalValue,
      montoPesosSat: totalPesos,
      montoPesosSatValue: totalPesosValue,
    };
  }
  return _values;
};

//#endregion

//#region Validaciones

const validationString = (coditionalField = "notaActiva") =>
  yup.string().when(coditionalField, (values, schema) => {
    if (values[0]) {
      return schema.required("Dato requerido");
    }
    return schema;
  });

const validationBool = (conditionalField = "notaActiva") =>
  yup.boolean().when(conditionalField, (values, schema) => {
    if (values[0]) {
      return schema.oneOf([true], "Dato requerido");
    }
    return schema;
  });

const validationMontos = (conditionalField = "notaActiva") =>
  yup.string().when(conditionalField, (values, schema) => {
    if (values[0]) {
      return schema.test(
        "monto-menor-o-igual-total",
        NOTA_CREDITO.MSG010,
        function (value) {
          const {
            totalValue = 0,
            montoSatValue = 0,
            montoCCValue = 0,
          } = this.parent;
          let sum = (
            parseFloat(montoCCValue) + parseFloat(montoSatValue)
          ).toFixed(2);
          let minorValue = parseFloat(sum) === parseFloat(totalValue);
          return minorValue;
        }
      );
    }
    return schema;
  });

export const validationSchema = yup.object().shape({
  notasCredito: yup.array().of(
    yup.object().shape({
      xmlCargado: validationBool(),
      xmlLeido: validationBool(),
      pdfCargado: validationBool(),
      folio: validationString(),
      comprobanteFiscal: validationString(),
      fechaFacturacion: validationString(),
      idTipoMoneda: validationString(),
      idTasa: validationString(),
      subTotal: validationString(),
      ieps: validationString(),
      iva: validationString(),
      montoCC: validationMontos(),
      montoSat: validationMontos(),
      montoPesosSat: validationString(),
      montoPesosCC: validationString(),
    })
  ),
});

//#endregion
