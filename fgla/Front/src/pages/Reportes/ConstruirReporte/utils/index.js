import _, { values } from "lodash";
import moment from "moment";
import * as yup from "yup";
import { formatCurrency } from "../../../../functions/utils";

let idCounter = 1; // Start the counter at 1
const assignIds = (nodes) => {
  return nodes.map((node) => {
    const newNode = { ...node, id: idCounter++ }; // Add the ID
    if (newNode.nodes) {
      newNode.nodes = assignIds(newNode.nodes); // Recurse for child nodes
    }
    return newNode;
  });
};
export const data = assignIds([
  {
    omitFromObject: true,
    name: "Proyecto",
    bodyName: "proyecto",
    nodes: [
      {
        firstHierarchy: true,
        name: "Datos Generales",
        bodyName: "datosGenerales",
        nodes: [
          { name: "Id", nodes: [], checked: false, bodyName: "id" },
          {
            name: "Nombre corto",
            nodes: [],
            checked: false,
            bodyName: "nombreCorto",
          },
          { name: "Estatus", nodes: [], checked: false, bodyName: "estatus" },
          {
            name: "Nombre del proyecto",
            nodes: [],
            checked: false,
            bodyName: "nombreProyecto",
          },
          { name: "Id AGP", nodes: [], checked: false, bodyName: "idAgp" },
          {
            name: "Ficha Técnica",
            bodyName: "fichaTecnica",
            firstHierarchy: true,
            nodes: [
              {
                name: "Administración patrocinadora",
                nodes: [],
                checked: false,
                bodyName: "administracionPatrocinadora",
              },
              {
                name: "Nombre de la administración patrocinadora",
                nodes: [],
                checked: false,
                bodyName: "nombreAdministracionPatrocinadora",
              },
              {
                name: "Administrador patrocinador",
                nodes: [],
                checked: false,
                bodyName: "administracionPatrocinador",
              },
              {
                name: "Administración central patrocinadora",
                nodes: [],
                checked: false,
                bodyName: "administracionCentralPatrocinadora",
              },
              {
                name: "Nombre de la admón. central patrocinadora",
                nodes: [],
                checked: false,
                bodyName: "nombreAdministracionCentralPatrocinadora",
              },
              {
                name: "Administrador central patrocinador",
                nodes: [],
                checked: false,
                bodyName: "administradorCentralPatrocinador",
              },
              {
                name: "Administración participante",
                nodes: [],
                checked: false,
                bodyName: "administracionParticipante",
              },
              {
                name: "Nombre de la administración participante",
                nodes: [],
                checked: false,
                bodyName: "nombreAdmonParticipante",
              },
              {
                name: "Administrador participante",
                nodes: [],
                checked: false,
                bodyName: "administradorParticipante",
              },
              {
                name: "Clasificación del proyecto",
                nodes: [],
                checked: false,
                bodyName: "clasificacionProyecto",
              },
              {
                name: "Financiamiento",
                nodes: [],
                checked: false,
                bodyName: "financiamiento",
              },
              {
                name: "Tipo de procedimiento",
                nodes: [],
                checked: false,
                bodyName: "tipoProcedimiento",
              },
              {
                name: "Líder del proyecto",
                nodes: [],
                checked: false,
                bodyName: "liderProyecto",
              },
              { name: "Puesto", nodes: [], checked: false, bodyName: "puesto" },
              { name: "Correo", nodes: [], checked: false, bodyName: "correo" },
              {
                name: "Fecha inicio",
                nodes: [],
                checked: false,
                bodyName: "fechaInicio",
              },
              {
                name: "Fecha fin",
                nodes: [],
                checked: false,
                bodyName: "fechaFin",
              },
              {
                name: "Estatus",
                nodes: [],
                checked: false,
                bodyName: "estatus",
              },
              {
                name: "Alineación del proyecto",
                nodes: [],
                checked: false,
                bodyName: "alineacionProyecto",
              },
              { name: "Mapa", nodes: [], checked: false, bodyName: "mapa" },
              {
                name: "Periodo",
                nodes: [],
                checked: false,
                bodyName: "periodo",
              },
              {
                name: "Objetivo",
                nodes: [],
                checked: false,
                bodyName: "objetivo",
              },
              {
                name: "Fecha de inicio del proyecto",
                nodes: [],
                checked: false,
                bodyName: "fechaInicioProyecto",
              },
              {
                name: "Fecha fin del proyecto",
                nodes: [],
                checked: false,
                bodyName: "fechaFinProyecto",
              },
              {
                name: "Área de planeación",
                nodes: [],
                checked: false,
                bodyName: "areaPlaneacion",
              },
              {
                name: "Monto solicitado",
                nodes: [],
                checked: false,
                bodyName: "montoSolicitado",
              },
              {
                name: "Tipo de moneda",
                nodes: [],
                checked: false,
                bodyName: "tipoMoneda",
              },
              {
                name: "Objetivo general",
                nodes: [],
                checked: false,
                bodyName: "objetivoGeneral",
              },
              {
                name: "Alcance",
                nodes: [],
                checked: false,
                bodyName: "alcance",
              },
            ],
            checked: false,
          },
        ],
        checked: false,
      },
    ],
  },
  {
    name: "Proveedor",
    bodyName: "proveedor",
    nodes: [
      {
        name: "Nombre del proveedor",
        nodes: [],
        checked: false,
        bodyName: "nombreProveedor",
      },
      {
        name: "Nombre comercial",
        nodes: [],
        checked: false,
        bodyName: "nombreComercial",
      },
      {
        name: "Giro de la empresa",
        nodes: [],
        checked: false,
        bodyName: "giroEmpresa",
      },
      {
        name: "Directorio de contacto",
        nodes: [],
        checked: false,
        bodyName: "directorioContacto",
      },
      { name: "RFC", nodes: [], checked: false, bodyName: "rfc" },
      {
        name: "Representante legal",
        nodes: [],
        checked: false,
        bodyName: "representanteLegal",
      },
      {
        name: "Título de servicio",
        nodes: [],
        checked: false,
        bodyName: "tituloServicio",
      },
      { name: "Vigencia", nodes: [], checked: false, bodyName: "vigencia" },
      {
        name: "Fecha vencimiento",
        nodes: [],
        checked: false,
        bodyName: "fechaVencimiento",
      },
      {
        name: "Cumple dictamen",
        nodes: [],
        checked: false,
        bodyName: "cumpleDictamen",
      },
    ],
    checked: false,
  },
  {
    name: "Contrato",
    bodyName: "contrato",
    nodes: [
      {
        name: "Estatus del contrato",
        nodes: [],
        checked: false,
        bodyName: "estatusContrato",
      },
      { name: "Id", nodes: [], checked: false, bodyName: "id" },
      {
        name: "Nombre del contrato",
        nodes: [],
        checked: false,
        bodyName: "nombreContrato",
      },
      {
        name: "Nombre del proyecto",
        nodes: [],
        checked: false,
        bodyName: "nombreProyecto",
      },
      {
        name: "Número de contrato",
        nodes: [],
        checked: false,
        bodyName: "numeroContrato",
      },
      { name: "Proveedor", nodes: [], checked: false, bodyName: "provedor" },
      {
        name: "Tipo de procedimiento",
        nodes: [],
        checked: false,
        bodyName: "tipoProcedimiento",
      },
      {
        name: "Fecha inicio del contrato",
        nodes: [],
        checked: false,
        bodyName: "fechaInicioContrato",
      },
      {
        name: "Fecha término del contrato",
        nodes: [],
        checked: false,
        bodyName: "fechaTerminoContrato",
      },
      { name: "Último CM", nodes: [], checked: false, bodyName: "ultimoCm" },
      {
        name: "Monto máximo",
        nodes: [],
        checked: false,
        bodyName: "montoMaximo",
      },
      {
        name: "Monto máximo de último CM",
        nodes: [],
        checked: false,
        bodyName: "montoMaximoMc",
      },
      {
        name: "Monto en pesos",
        nodes: [],
        checked: false,
        bodyName: "montoPesos",
      },
      {
        name: "Administración central",
        nodes: [],
        checked: false,
        bodyName: "administracionCentral",
      },
      {
        name: "Administrador del contrato",
        nodes: [],
        checked: false,
        bodyName: "administradorContrato",
      },
      {
        name: "Convenio Modificatorio",
        firstHierarchy: true,
        bodyName: "convenioModificatorio",
        nodes: [
          {
            name: "Número de convenio",
            nodes: [],
            checked: false,
            bodyName: "numeroConvenio",
          },
          {
            name: "Tipo de convenio",
            nodes: [],
            checked: false,
            bodyName: "tipoConvenio",
          },
          {
            name: "Fecha de firma",
            nodes: [],
            checked: false,
            bodyName: "fechaFirma",
          },
          {
            name: "Fecha fin de servicio",
            nodes: [],
            checked: false,
            bodyName: "fechaFinServicio",
          },
          {
            name: "Fecha fin de contrato con CM",
            nodes: [],
            checked: false,
            bodyName: "fechaFinContratoCm",
          },
          {
            name: "Cálculo de días naturales",
            nodes: [],
            checked: false,
            bodyName: "calculoDiasNaturales",
          },
          {
            name: "Incremento",
            nodes: [],
            checked: false,
            bodyName: "incremento",
          },
          { name: "Subtotal", nodes: [], checked: false, bodyName: "subtotal" },
          { name: "IEPS", nodes: [], checked: false, bodyName: "ieps" },
          { name: "IVA", nodes: [], checked: false, bodyName: "iva" },
          {
            name: "Tipo de cambio",
            nodes: [],
            checked: false,
            bodyName: "tipoCambio",
          },
          {
            name: "Monto máximo del contrato con CM sin impuestos",
            nodes: [],
            checked: false,
            bodyName: "montoMaximoContratoCMSin",
          },
          {
            name: "Monto máximo del contrato con CM con impuestos",
            nodes: [],
            checked: false,
            bodyName: "montoMaximoContratoCMCon",
          },
          {
            name: "Monto en pesos",
            nodes: [],
            checked: false,
            bodyName: "montoPesos",
          },
          {
            name: "Comentarios",
            nodes: [],
            checked: false,
            bodyName: "comentarios",
          },
        ],
        checked: false,
      },
    ],
    checked: false,
  },
  {
    name: "Concepto de servicio",
    bodyName: "conceptoServicio",
    nodes: [
      { name: "Id", nodes: [], checked: false, bodyName: "id" },
      { name: "Grupo", nodes: [], checked: false, bodyName: "grupo" },
      {
        name: "Tipo de consumo",
        nodes: [],
        checked: false,
        bodyName: "tipoConsumo",
      },
      {
        name: "Conceptos de servicio",
        nodes: [],
        checked: false,
        bodyName: "precioUnitario",
      },
      {
        name: "Tipo de unidad",
        nodes: [],
        checked: false,
        bodyName: "tipoUnidad",
      },
      {
        name: "Precio unitario",
        nodes: [],
        checked: false,
        bodyName: "precioUnitario",
      },
      {
        name: "Cantidad de servicios mínima",
        nodes: [],
        checked: false,
        bodyName: "cantidadServicioMinima",
      },
      {
        name: "Cantidad de servicios máxima",
        nodes: [],
        checked: false,
        bodyName: "cantidadServicioMaximo",
      },
      {
        name: "Monto mínimo",
        nodes: [],
        checked: false,
        bodyName: "montoMinimo",
      },
      {
        name: "Monto máximo",
        nodes: [],
        checked: false,
        bodyName: "montoMaximo",
      },
      { name: "Aplica IEPS", nodes: [], checked: false, bodyName: "ieps" },
      {
        name: "Cantidad de servicios máximos del último CM",
        nodes: [],
        checked: false,
        bodyName: "cantidadMaximaUltimoCm",
      },
      {
        name: "Monto máximo del último CM",
        nodes: [],
        checked: false,
        bodyName: "montoMaximoUltimoCm",
      },
      {
        name: "Planeado",
        firstHierarchy: true,
        bodyName: "planeado",
        nodes: [
          {
            name: "Cantidad de servicios planeados",
            nodes: [],
            checked: false,
            bodyName: "cantidadServicioPlaneado",
          },
          {
            name: "Monto planeado",
            nodes: [],
            checked: false,
            bodyName: "montoPlaneado",
          },
          {
            name: "% Servicios planeados acumulados",
            nodes: [],
            checked: false,
            bodyName: "servicioPlaneadoAcumulado",
          },
          {
            name: "% Monto planeado acumulado",
            nodes: [],
            checked: false,
            bodyName: "montoPlaneado",
          },
        ],
        checked: false,
      },
      {
        name: "Estimado",
        bodyName: "estimado",
        firstHierarchy: true,
        nodes: [
          {
            name: "Cantidad de servicios estimado",
            nodes: [],
            checked: false,
            bodyName: "cantidadServicioEstimado",
          },
          {
            name: "Monto estimado",
            nodes: [],
            checked: false,
            bodyName: "montoEstimado",
          },
          {
            name: "% Servicios estimados acumulados",
            nodes: [],
            checked: false,
            bodyName: "servicioEstimadoAcumulado",
          },
          {
            name: "% Monto estimado acumulado",
            nodes: [],
            checked: false,
            bodyName: "monotoEstimadoAcumulado",
          },
        ],
        checked: false,
      },
      {
        name: "Dictaminado",
        bodyName: "dictaminado",
        firstHierarchy: true,
        nodes: [
          {
            name: "Cantidad de servicios dictaminado",
            nodes: [],
            checked: false,
            bodyName: "cantidadServicioDictaminado",
          },
          {
            name: "Monto dictaminado",
            nodes: [],
            checked: false,
            bodyName: "montoDictaminado",
          },
          {
            name: "% Servicios dictaminados acumulados",
            nodes: [],
            checked: false,
            bodyName: "servicioEstimadoDictaminado",
          },
          {
            name: "% Monto dictaminado acumulado",
            nodes: [],
            checked: false,
            bodyName: "monotoEstimadoDictaminado",
          },
        ],
        checked: false,
      },
      {
        name: "Pagado",
        bodyName: "pagado",
        firstHierarchy: true,
        nodes: [
          {
            name: "Cantidad de servicios pagados",
            nodes: [],
            checked: false,
            bodyName: "cantidadServicioPagado",
          },
          {
            name: "Monto pagado",
            nodes: [],
            checked: false,
            bodyName: "montoPagado",
          },
          {
            name: "% Servicios pagados acumulados",
            nodes: [],
            checked: false,
            bodyName: "servicioPagadoDictaminado",
          },
          {
            name: "% Monto pagado acumulado",
            nodes: [],
            checked: false,
            bodyName: "monotoPagadoDictaminado",
          },
        ],
        checked: false,
      },
    ],
    checked: false,
  },
  {
    name: "Estimaciones",
    bodyName: "estimaciones",
    nodes: [
      { name: "Id", nodes: [], checked: false, bodyName: "id" },
      {
        name: "Nombre corto del contrato",
        nodes: [],
        checked: false,
        bodyName: "nombreCortoContrato",
      },
      {
        name: "Número de contrato",
        nodes: [],
        checked: false,
        bodyName: "numeroContrato",
      },
      { name: "Proveedor", nodes: [], checked: false, bodyName: "proveedor" },
      { name: "Estatus", nodes: [], checked: false, bodyName: "estatus" },
      {
        name: "Periodo de inicio",
        nodes: [],
        checked: false,
        bodyName: "periodoInicio",
      },
      {
        name: "Periodo fin",
        nodes: [],
        checked: false,
        bodyName: "periodoFin",
      },
      {
        name: "Periodo de control",
        nodes: [],
        checked: false,
        bodyName: "periodoControl",
      },
      { name: "IVA", nodes: [], checked: false, bodyName: "iva" },
      {
        name: "Tipo de cambio referencial",
        nodes: [],
        checked: false,
        bodyName: "tipoCambioReferencial",
      },
      {
        name: "Monto estimado total",
        nodes: [],
        checked: false,
        bodyName: "montoEstimadoTotal",
      },
      {
        name: "Monto estimado total en pesos",
        nodes: [],
        checked: false,
        bodyName: "montoEstimadoTotalPesos",
      },
      {
        name: "Justificación",
        nodes: [],
        checked: false,
        bodyName: "justificacion",
      },
    ],
    checked: false,
  },

  {
    name: "Dictamen",
    bodyName: "dictamen",
    nodes: [
      { name: "Id", nodes: [], checked: false, bodyName: "id" },
      {
        name: "Nombre corto del contrato",
        nodes: [],
        checked: false,
        bodyName: "nombreCortoContrato",
      },
      {
        name: "Número de contrato",
        nodes: [],
        checked: false,
        bodyName: "numeroContrato",
      },
      { name: "Proveedor", nodes: [], checked: false, bodyName: "proveedor" },
      { name: "Estatus", nodes: [], checked: false, bodyName: "estatus" },
      {
        name: "Periodo de inicio",
        nodes: [],
        checked: false,
        bodyName: "periodoInicio",
      },
      {
        name: "Periodo fin",
        nodes: [],
        checked: false,
        bodyName: "periodoFin",
      },
      {
        name: "Periodo de control",
        nodes: [],
        checked: false,
        bodyName: "periodoControl",
      },
      { name: "IVA", nodes: [], checked: false, bodyName: "iva" },
      {
        name: "Tipo de cambio referencial",
        nodes: [],
        checked: false,
        bodyName: "tipoCambioReferencial",
      },
      {
        name: "Descripción",
        nodes: [],
        checked: false,
        bodyName: "descripcion",
      },
      { name: "Fase", nodes: [], checked: false, bodyName: "fase" },
      { name: "Subtotal", nodes: [], checked: false, bodyName: "subTotal" },
      {
        name: "Deducciones",
        nodes: [],
        checked: false,
        bodyName: "deducciones",
      },
      { name: "IEPS", nodes: [], checked: false, bodyName: "ieps" },
      {
        name: "Otros impuestos",
        nodes: [],
        checked: false,
        bodyName: "otrosImpuestos",
      },
      { name: "Total", nodes: [], checked: false, bodyName: "total" },
      {
        name: "Total en pesos",
        nodes: [],
        checked: false,
        bodyName: "totalpesos",
      },
    ],
    checked: false,
  },
  {
    name: "Penalizaciones / Deducciones",
    bodyName: "penalizacionDeduccion",
    nodes: [
      { name: "Tipo", nodes: [], checked: false, bodyName: "tipo" },
      {
        name: "Tipo de informe",
        nodes: [],
        checked: false,
        bodyName: "tipoInforme",
      },
      {
        name: "Documento",
        nodes: [],
        checked: false,
        bodyName: "documento"
      },
      {
        name: "Descripción",
        nodes: [],
        checked: false,
        bodyName: "descripcion",
      },
      { name: "Desglose", nodes: [], checked: false, bodyName: "desglose" },
      {
        name: "Concepto de servicio",
        nodes: [],
        checked: false,
        bodyName: "conceptoServicio",
      },
      { name: "Monto", nodes: [], checked: false, bodyName: "monto" },
      {
        name: "Periodo inicio",
        nodes: [],
        checked: false,
        bodyName: "periodoInicio",
      },
      {
        name: "Periodo término",
        nodes: [],
        checked: false,
        bodyName: "periodoTermino",
      },
      {
        name: "Periodo control",
        nodes: [],
        checked: false,
        bodyName: "periodoControl",
      },
    ],
    checked: false,
  },
  {
    name: "Facturas",
    bodyName: "facturas",
    nodes: [
      { name: "Folio", nodes: [], checked: false, bodyName: "folio" },
      {
        name: "Comprobante fiscal",
        nodes: [],
        checked: false,
        bodyName: "comprobanteFiscal",
      },
      {
        name: "Fecha de facturación",
        nodes: [],
        checked: false,
        bodyName: "fechaFacturacion",
      },
      {
        name: "Estatus",
        nodes: [],
        checked: false,
        bodyName: "estatus",
      },
      { name: "Moneda", nodes: [], checked: false, bodyName: "moneda" },
      { name: "Tasa", nodes: [], checked: false, bodyName: "tasa" },
      {
        name: "Subtotal",
        nodes: [],
        checked: false,
        bodyName: "subTotal",
      },
      { name: "IEPS", nodes: [], checked: false, bodyName: "ieps" },
      { name: "IVA", nodes: [], checked: false, bodyName: "iva" },
      {
        name: "Otros impuestos",
        nodes: [],
        checked: false,
        bodyName: "otrosImpuestos",
      },
      {
        name: "Total facturado",
        nodes: [],
        checked: false,
        bodyName: "totalFacturado",
      },
      {
        name: "Total facturado en pesos",
        nodes: [],
        checked: false,
        bodyName: "totalFacturadoPesos",
      },
      {
        name: "Total pagado en pesos",
        nodes: [],
        checked: false,
        bodyName: "totalPagadoPesos",
      },
      {
        name: "Comentarios",
        nodes: [],
        checked: false,
        bodyName: "comentarios",
      },
      {
        name: "Desglose de Montos",
        firstHierarchy: true,
        bodyName: "desgloseMonto",
        nodes: [
          { name: "% SAT", nodes: [], checked: false, bodyName: "sat" },
          {
            name: "Monto",
            nodes: [],
            checked: false,
            bodyName: "monto",
          },
          {
            name: "Monto en pesos",
            nodes: [],
            checked: false,
            bodyName: "montoPesos",
          },
          {
            name: "% Convenio de colaboración",
            nodes: [],
            checked: false,
            bodyName: "convenioColaboracion",
          },
          {
            name: "Monto",
            nodes: [],
            checked: false,
            bodyName: "montoConveio",
          },
          {
            name: "Monto en pesos",
            nodes: [],
            checked: false,
            bodyName: "montoPesosConvenio",
          },
          {
            name: "Ficha NAFIN",
            nodes: [],
            checked: false,
            bodyName: "fichaNaFin",
          },
          {
            name: "Fecha NAFIN",
            nodes: [],
            checked: false,
            bodyName: "fechaNaFin",
          },
          {
            name: "Tipo de cambio NAFIN",
            nodes: [],
            checked: false,
            bodyName: "tipoCambioNaFin",
          },
        ],
        checked: false,
      },
    ],
    checked: false,
  },
  {
    name: "Notas de Crédito",
    bodyName: "notaCredito",
    nodes: [
      { name: "Folio", nodes: [], checked: false, bodyName: "folio" },
      {
        name: "Comprobante fiscal",
        nodes: [],
        checked: false,
        bodyName: "comprobanteFiscal",
      },
      {
        name: "Fecha de generación",
        nodes: [],
        checked: false,
        bodyName: "fechaGeneracion",
      },
      { name: "Estatus", nodes: [], checked: false, bodyName: "estatus" },
      { name: "Moneda", nodes: [], checked: false, bodyName: "moneda" },
      { name: "Tasa", nodes: [], checked: false, bodyName: "tasa" },
      { name: "Subtotal", nodes: [], checked: false, bodyName: "subTotal" },
      { name: "IEPS", nodes: [], checked: false, bodyName: "ieps" },
      { name: "IVA", nodes: [], checked: false, bodyName: "iva" },
      {
        name: "Otros impuestos",
        nodes: [],
        checked: false,
        bodyName: "otrosImpuestos",
      },
      { name: "Total", nodes: [], checked: false, bodyName: "total" },
      {
        name: "Total en pesos",
        nodes: [],
        checked: false,
        bodyName: "totalPesos",
      },
      {
        name: "Comentarios",
        nodes: [],
        checked: false,
        bodyName: "comentarios",
      },
      {
        name: "Desglose de Montos",
        bodyName: "desgloseMonto",
        firstHierarchy: true,
        nodes: [
          { name: "% SAT", nodes: [], checked: false, bodyName: "sat" },
          { name: "Monto", nodes: [], checked: false, bodyName: "monto" },
          {
            name: "Monto en pesos",
            nodes: [],
            checked: false,
            bodyName: "montoPesos",
          },
          {
            name: "% Convenio de colaboración",
            nodes: [],
            checked: false,
            bodyName: "convenioColaboracion",
          },
          {
            name: "Monto",
            nodes: [],
            checked: false,
            bodyName: "montoConveio",
          },
          {
            name: "Monto en pesos",
            nodes: [],
            checked: false,
            bodyName: "montoPesosConvenio",
          },
          {
            name: "Ficha NAFIN",
            nodes: [],
            checked: false,
            bodyName: "fichaNaFin",
          },
          {
            name: "Fecha NAFIN",
            nodes: [],
            checked: false,
            bodyName: "fechaNaFin",
          },
          {
            name: "Tipo de cambio NAFIN",
            nodes: [],
            checked: false,
            bodyName: "tipoCambioNaFin",
          },
        ],
        checked: false,
      },
    ],
    checked: false,
  },
]);

export const INITIAL_DATA = {
  idEstatusProyecto: [],
  idProyectos: [],
  idEstatusContratoProyecto: [],
  idContratos: [],
  idDominioTecnologicos: [],
  idConveniosColaboracion: [],
  idRazonSocial: [],
  idTituloServicio: [],
  fechaInicio: "",
  fechaTermino: "",
  acumulada: true,
  mensual: false,
  aplicacionPeriodo: 0,
};
export function toCamelCase(str) {
  return String(str)
    .toLowerCase()
    .replace(/(?:^\w|[A-Z]|\b\w|\s+)/g, (match, index) =>
      index === 0 ? match.toLowerCase() : match.toUpperCase()
    )
    .replace(/\s+/g, "");
}

export function convertTreeToObj(data) {
  const result = {};

  data.forEach((item) => {
    const traverse = (node, parentObj) => {
      // Create an object for the current node if it has a bodyName
      if (node.bodyName) {
        parentObj[node.bodyName] = node.checked;
      }

      // Recur for child nodes
      node.nodes.forEach((childNode) => {
        if (!parentObj[node.bodyName]) {
          parentObj[node.bodyName] = {}; // Initialize if not already done
        }
        traverse(childNode, parentObj[node.bodyName]);
      });
    };

    traverse(item, result);
  });

  return result;
}
function representValue(value) {
  if (typeof value === "boolean") {
    return value.toString();
  } else if (typeof value === "number") {
    return formatCurrency(value, 6);
  } else {
    return value;
  }
}
export function generateHeaders(data) {
  if (!data) return [];
  const { etiquetas } = data;
  return etiquetas?.map((etiqueta) => ({
    accessorKey: etiqueta,
    header: etiqueta,
    cell: (props) => (
      <>
        {etiqueta?.toLowerCase().includes("fecha")
          ? props.getValue()
            ? moment(new Date(props.getValue())).format("DD/MM/YYYY")
            : ""
          : representValue(props.getValue())}
      </>
    ),
    filterFn: (row, columnId, inputValue) =>
      String(row.getValue(columnId))
        .toLowerCase()
        .trim()
        .includes(inputValue.toLowerCase().trim()),
  }));
}
export function rearrangeSendData(data, params) {
  const {
    proyecto,
    contrato,
    conceptoServicio,
    facturas,
    notaCredito,
    proveedor,
    estimaciones,
    dictamen,
    penalizacionDeduccion,
  } = data;
  const { datosGenerales } = proyecto;
  const { fichaTecnica } = datosGenerales;
  const { convenioModificatorio } = contrato;
  const { planeado, estimado, dictaminado } = conceptoServicio;
  const { desgloseMonto: desgloseFacturas } = facturas;
  const { desgloseMonto } = notaCredito;

  return {
    ...params,
    dataReporteDto: {
      datosGenerales: _.omit(datosGenerales, ["fichaTecnica"]),
      fichaTecnica,
      proveedor,
      contrato: _.omit(contrato, "convenioModificatorio"),
      convenioModificatorio,
      conceptoServicio: {
        ..._.omit(conceptoServicio, [
          "planeado",
          "estimado",
          "dictaminado",
          "pagado",
        ]),
        ...planeado,
      },
      estimado,
      dictaminado: { ...dictaminado },
      estimaciones,
      dictamen,
      penalizacionDeduccion,
      facturas: { ..._.omit(facturas, ["desgloseMonto"]), ...desgloseFacturas },
      notaCredito: {
        ..._.omit(notaCredito, "desgloseMonto"),
        ...desgloseMonto,
      },
    },
  };
}
export function rearrangeReporte(data) {
  if (!data) return [{}];
  const { content, etiquetas } = data;
  return content.map((item) => {
    return etiquetas.reduce((obj, label, index) => {
      obj[label] = item[index];
      return obj;
    }, {});
  });
}
export const construirReporteSchema = yup.object({
  fechaInicio: yup.date(),
  fechaTermino: yup
    .date()
    .test(
      "is-greater",
      "Fecha de término debe ser mayor o igual a la fecha de inicio",
      function (value) {
        const { fechaInicio } = this.parent;
        if (!fechaInicio || !value) return true;
        return value >= fechaInicio;
      }
    ),
});
export const PAGEABLE = {
  totalPages: 0,
  totalElements: 0,
  pageNumber: 0,
  size: 15,
};

export const CONSTRUIR_REPORTES = {
  MSG001:
    "Favor de ingresar como mínimo un criterio de búsqueda y un campo para reporte.",
  MSG002:
    "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG003:
    "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG004: "No se encontraron resultados de la búsqueda.",
  MSG005: "La fecha ingresada es incorrecta.",
  MSG006: "Procesando...",
};
