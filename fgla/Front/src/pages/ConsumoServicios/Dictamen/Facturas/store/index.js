import { apiSAT } from "../../../../../store/features";
const getFormData = (data) => {
  const formData = new FormData();
  Object.keys(data).forEach((key) => {
    formData.append(key, data[key]);
  });
  return formData;
};

export const generarFacturas = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getValidarConvenio: builder.query({
      query: (id) => ({
        url: "/admin-devengados/AplicaCC/" + id,
        method: "get",
      }),
    }),
    postFacturas: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/obtener-facturas",
        method: "post",
        data,
      }),
    }),
    getFacturasRecibidas: builder.query({
      query: (id) => ({
        url: `/admin-devengados/validar-factura-recibida/${id}`,
        method: "get",
      }),
    }),
    postGuardarFactura: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/guardar-factura",
        method: "post",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    postActualizarFactura: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/editar-factura",
        method: "post",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    postCancelarFactura: builder.mutation({
      query: ({ idFactura, data }) => ({
        url: `/admin-devengados/cancelar-factura/${idFactura}`,
        method: "post",
        data,
      }),
    }),
    postLeerXMLFactura: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/obtener-datosXML",
        method: "post",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    getCatEstatusFacturas: builder.query({
      query: () => ({
        url: "/admin-devengados/estatus-factura",
        method: "get",
      }),
    }),
    getCatTasaFacturas: builder.query({
      query: () => ({
        url: "/admin-devengados/cat-iva",
        method: "get",
      }),
    }),
    getCatMonedaFacturas: builder.query({
      query: () => ({
        url: "/admin-devengados/tipo-moneda",
        method: "get",
      }),
    }),
    postValidarRegresarProforma: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/validar-facturas-notas-asociadas",
        method: "post",
        data,
      }),
    }),
    postRegresarProforma: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/regresar-proforma",
        method: "post",
        data,
      }),
    }),
    postBuscarSolicitudFactura: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/buscar-solicitud-factura",
        method: "post",
        data,
      }),
    }),
    postActualizarResumenFacturas: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/actualizar-resumen-facturado",
        method: "put",
        data,
      }),
    }),
    postGuardarFacturasRecibidas: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/solicitud-factura/actualizar",
        method: "post",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
  }),
});

export const {
  usePostFacturasMutation,
  useGetFacturasRecibidasQuery,
  usePostGuardarFacturaMutation,
  usePostActualizarFacturaMutation,
  usePostLeerXMLFacturaMutation,
  usePostCancelarFacturaMutation,
  useGetCatEstatusFacturasQuery,
  useGetCatMonedaFacturasQuery,
  useGetCatTasaFacturasQuery,
  useGetValidarConvenioQuery,
  usePostValidarRegresarProformaMutation,
  usePostRegresarProformaMutation,
  usePostBuscarSolicitudFacturaMutation,
  usePostActualizarResumenFacturasMutation,
  usePostGuardarFacturasRecibidasMutation
} = generarFacturas;
