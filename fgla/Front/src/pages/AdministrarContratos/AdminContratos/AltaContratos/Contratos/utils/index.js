import moment from "moment";
import { FormatMoney } from "../../../../../../functions/utils";

const contratosSchema = (data = {}) => {
  const values = Object.values(data);
  if (values.length <= 0) return false;
  const threshold = (value) => value !== null && value !== undefined;
  return values.some(threshold);
};
const HEADERS = [
  {
    dataField: "idContrato",
    text: "Id",
    filter: true,
    sort: true,
  },
  {
    dataField: "nombreCorto",
    text: "Nombre corto",
    filter: true,
    sort: true,
  },
  {
    dataField: "nombreProyecto",
    text: "Nombre del proyecto",
    filter: true,
    sort: true,
  },
  {
    dataField: "numeroContrato",
    text: "Número del contrato",
    filter: true,
    sort: true,
  },
  {
    dataField: "proveedores",
    text: "Proveedores",
    filter: true,
    sort: true,
  },
  {
    dataField: "tipoProcedimiento",
    text: "Tipo de procedimiento",
    filter: true,
    sort: true,
  },
  {
    dataField: "fechaInicio",
    text: "Inicio",
    filter: true,
    sort: true,
  },
  {
    dataField: "fechaTermino",
    text: "Término",
    filter: true,
    sort: true,
  },
  {
    dataField: "ultimoCm",
    text: "Último CM",
    filter: true,
    sort: true,
  },
  {
    dataField: "montoMaximo",
    text: "Monto máximo",
    filter: true,
    sort: true,
  },
  {
    dataField: "montoMaximoUltimoCm",
    text: "Monto máximo útlimo CM",
    filter: true,
    sort: true,
  },
  {
    dataField: "montoPesos",
    text: "Monto en pesos",
    filter: true,
    sort: true,
  },
  {
    dataField: "administracionCentral",
    text: "Administración central",
    filter: true,
    sort: true,
  },
  {
    dataField: "administradorContrato",
    text: "Administrador de contrato",
    filter: true,
    sort: true,
  },
  {
    dataField: "acciones",
    text: "Acciones",
    filter: false,
    sort: false,
  },
];
const INITIAL_VALUES = {
  estatusContrato: null,
  proveedor: null,
  admonCentral: null,
  fecha_inicio: null,
  fecha_termino: null,
};
const FORMAT_DATE = "DD/MM/YYYY";
const PAGEABLE = {
  totalPages: 0,
  totalElements: 0,
  pageNumber: 0,
  size: 15,
};
const DECIMALES = 2;

const rearrangeResponse = (data) => {
  return data?.map((contrato, index) => ({
    ...contrato,
    ...(contrato?.fechaUltimoCm
      ? {
          ultimoCm: moment(contrato?.fechaUltimoCm).format(FORMAT_DATE),
        }
      : { ultimoCm: null }),
    ...(contrato?.fechaInicio !== null
      ? { fechaInicio: moment(contrato?.fechaInicio).format(FORMAT_DATE) }
      : { fechaInicio: null }),
    ...(contrato?.fechaTermino !== null
      ? { fechaTermino: moment(contrato?.fechaTermino).format(FORMAT_DATE) }
      : { fechaTermino: null }),
    proveedores: contrato?.proveedores,
    administracionCentral: contrato?.administracionCentral,
    montoPesos: contrato?.montoPesos
      ? `$${FormatMoney(contrato?.montoPesos, DECIMALES)}`
      : "",
    montoMaximo: contrato?.montoMaximo
      ? `$${FormatMoney(contrato?.montoMaximo, DECIMALES)}`
      : "",
    montoMaximoUltimoCm: contrato?.montoMaximoCm
      ? `$${FormatMoney(contrato?.montoMaximoCm, DECIMALES)}`
      : "",
  }));
};
export {
  contratosSchema,
  HEADERS,
  INITIAL_VALUES,
  FORMAT_DATE,
  PAGEABLE,
  rearrangeResponse,
};
