import * as yup from "yup";
import { GENERAR_FACTURA } from "../../../../../constants/messages";
import moment from "moment";
import _ from "lodash";

export const emptyFactura = {
  edicion: false,
  idFactura: null,
  numeroFactura: 1,
  folio: "",
  comprobanteFiscal: "",
  fechaFacturacion: "",
  idEstatusFactura: "",
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
  idTasa: "",
  xmlCargado: false,
  pdfCargado: false,
  
};

//#region Validaciones

const validationString = (coditionalField = "facturaActiva") =>
  yup.string().when(coditionalField, (values, schema) => {
    if (values[0]) {
      return schema.required("Dato requerido");
    }
    return schema;
  });

const validationBool = (conditionalField = "facturaActiva") =>
  yup.boolean().when(conditionalField, (values, schema) => {
    if (values[0]) {
      return schema.oneOf([true], "Dato requerido");
    }
    return schema;
  });

const validationMontos = (conditionalField = "facturaActiva") =>
  yup.string().when(conditionalField, (values, schema) => {
    if (values[0]) {
      return schema.test(
        "monto-menor-o-igual-total",
        GENERAR_FACTURA.MSG012,
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
  facturas: yup.array().of(
    yup.object().shape({
      xmlCargado: validationBool(),
      xmlLeido:validationBool(),
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

//#region Format facturar

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
      [s.nombre, s.descripcion].some((prop) => prop == "Factura en ACCPPI")
    );
    if (catItem) return catItem.primaryKey;
  }
  return value;
};

export const FormatFactura = (
  factura,
  index,
  monedaCat,
  tasaCat,
  estatusCat,
  aplicaCC
) => {
  let _factura = { ...factura };
  let {
    fecha,
    fechaFacturacion,
    pathArchivoXml,
    pathPdf,
    idFactura,
    moneda,
    idIva,
    idTipoMoneda,
    idEstatusFactura,
    tasaoCuota,
    xmlLeido,
    archivoCargar="",
    pdf=""
  } = _factura;

  idTipoMoneda = idTipoMoneda || findCatId(monedaCat, moneda);
  let idTasa = findCatIdTasa(tasaCat, tasaoCuota || idIva);

  if (!idFactura) {
    idEstatusFactura = findIdStatus(estatusCat, idEstatusFactura);
  }
  if(idFactura){
    xmlLeido=true
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

  let _values = {
    ..._factura,
    numeroFactura: index + 1,
    fechaFacturacion: convertirFecha(fecha || fechaFacturacion),
    ...Object.fromEntries(
      camposAFormatear.flatMap((campo) => [
        [campo, FormatMoney(_factura[campo])],
        [`${campo}Value`, _factura[campo] || 0],
      ])
    ),
    idTipoMoneda,
    idTasa,
    idEstatusFactura,
    xmlCargado: !!pathArchivoXml,
    pdfCargado: !!pathPdf,
    nombrePdf:pdf,
    nombreXML:archivoCargar,
    xmlLeido
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

export function validarFechasSF(fecha1, fecha2) {
  // Verificamos que ambas fechas sean proporcionadas
  if (!fecha1 || !fecha2) return false;

  // Parseamos las fechas usando el formato "DD/MM/YYYY"
  const primeraFecha = moment(fecha1, "DD/MM/YYYY", true);
  const segundaFecha = moment(fecha2, "DD/MM/YYYY", true);

  // Verificamos si ambas fechas son válidas
  if (!primeraFecha.isValid() || !segundaFecha.isValid()) return false;

  // Verificamos si la primera fecha es mayor o igual que la segunda
  return primeraFecha.isSameOrAfter(segundaFecha);
}

//#endregion
