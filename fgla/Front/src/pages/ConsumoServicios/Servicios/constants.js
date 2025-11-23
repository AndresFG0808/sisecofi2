export const MESSAGES = {
    CAMPOS_OBLIGATORIOS: 'Favor de ingresar los datos obligatorios marcados con un asterisco (*)',
    MSG001: 'Verifique los criterios de búsqueda.',
    MSG002: 'Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).',
    MSG008: 'Se perderá la información capturada ¿Desea continuar?',
};

export const HEADERS_ESTIMACION = [
    {
      dataField: "id",
      text: "Id estimación",
      filter: true,
      sort: true,
    },
    {
      dataField: "periodoControl",
      text: "Periodo de control",
      filter: true,
      sort: true,
    },
    {
      dataField: "periodoInicial",
      text: "Periodo inicial",
      filter: true,
      sort: true,
    },
    {
      dataField: "periodoFinal",
      text: "Periodo final",
      filter: true,
      sort: true,
    },
    {
      dataField: "proveedor",
      text: "Proveedor",
      filter: true,
      sort: true,
    },
    { dataField: "estatus", text: "Estatus", filter: true, sort: true },
    {
      dataField: "montoEstimado",
      text: "Monto estimado",
      filter: true,
      sort: true,
    },
    {
      dataField: "montoEstimadoPesos",
      text: "Monto estimado en pesos",
      filter: true,
      sort: true,
    },
    {
      dataField: "montoDictaminado",
      text: "Monto dictaminado",
      filter: true,
      sort: true,
    },
    {
      dataField: "montoDictaminadoPesos",
      text: "Monto dictaminado en pesos",
      filter: true,
      sort: true,
    },
    { dataField: "acciones", text: "Acciones", width: { width: "105px" } },
];
  
export const HEADERS_DICTAMEN = [
    {
      dataField: "id",
      text: "Id dictamen",
      filter: true,
      sort: true,
    },
    {
      dataField: "montoDictaminadoPesos",
      text: "Monto",
      filter: true,
      sort: true,
    },
    { dataField: "estatus", text: "Estatus", filter: true, sort: true },
    {
      dataField: "periodoControl",
      text: "Periodo de control",
      filter: true,
      sort: true,
    },
    {
      dataField: "periodoInicial",
      text: "Periodo inicial",
      filter: true,
      sort: true,
    },
    {
      dataField: "periodoFinal",
      text: "Periodo final",
      filter: true,
      sort: true,
    },
    {
      dataField: "comprobanteFiscal",
      tooltipMessage: "Número del o los folios",
      text: "Comprobante fiscal",
      filter: true,
      sort: true,
    },
    {
      dataField: "pendientePago",
      text: "Pendientes de pago",
      filter: true,
      sort: true,
    },
    { dataField: "acciones", text: "Acciones", width: { width: "105px" } },
];