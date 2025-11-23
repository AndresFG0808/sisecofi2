import { apiSAT } from "../../../../../store/features";

const getFormData = (data) => {
  const formData = new FormData();
  Object.keys(data).forEach((key) => {
    formData.append(key, data[key]);
  });
  return formData;
};

export const informeProformaApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    ggetCatDesgloce: builder.query({
      query: () => ({
        url: "/admin-devengados/catalogo-desgloce",
        method: "get",
      }),
    }),
    postGuardarProforma: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/guardar",
        method: "post",
        data,
      }),
    }),
    postResumenDeduccionesDescuentosPenalizaciones: builder.mutation({
      query: (data) => ({
        url: `/admin-devengados/resumen-deducciones-descuentos-penalizaciones`,
        method: "post",
        data,
      }),
    }),
    postObtenerPlantillaProforma: builder.mutation({
      query: (data) => ({
        url: `/admin-devengados/obtener-plantilla-proforma`,
        method: "post",
        data,
      }),
    }),

    postBuscarSolicitudFactura: builder.mutation({
      query: (data) => ({
        url: `/admin-devengados/buscar-solicitud-factura`,
        method: "post",
        data,
      }),
    }),
    getValidarConvenioProforma: builder.query({
      query: (id) => ({
        url: "/admin-devengados/AplicaCC/" + id,
        method: "get",
      }),
    }),
    getCatDescuentosProforma: builder.query({
      query: () => ({
        url: "/admin-devengados/proforma/ddp/tipo-descuento",
        method: "get",
      }),
    }),
    postRegresarProformaTemp: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/regresar-proforma",
        method: "post",
        data,
      }),
    }),

    postBuscarDp: builder.mutation({
       query: ({ idDictamen }) => ({
        url: "/admin-devengados/proforma/ddp/buscar-ddp",
        method: "post",
        params: { idDictamen: idDictamen },
      }),
    }),
    postGuardarDp: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/crear-ddp",
        method: "post",
        data,
      }),
    }),
    putActualizarDp: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/modificar-ddp",
        method: "put",
        data,
      }),
    }),
    deleteEliminarDp: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/eliminar-ddp",
        method: "delete",
        data,
      }),
    }),
    postMonedaDDp: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/buscar-moneda",
        method: "post",
        data,
      }),
    }),
    postCargarArchivoDDp: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/examinar-ddp",
        method: "put",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    postConsultarArchivoDdp: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/obtener-archivo-ddp",
        method: "post",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    putDescargarArchivoDdp: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/descargar-ddp",
        method: "put",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    putRechazarProforma: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/rechazar-proforma",
        method: "put",
        data,
      }),
    }),
    putValidarDictamen: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/validar-dictamen",
        method: "put",
        data,
      }),
    }),
    getPlantillasDdp: builder.query({
      query: () => ({
        url: "/admin-devengados/proforma/ddp/obtener-plantillas",
        method: "get",
      }),
    }),
    postPlantillaDdp: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/proforma/ddp/reporte",
        method: "post",
        data,
      }),
    }),
  }),
  overrideExisting: false,
});

export const {
  useGgetCatDesgloceQuery, // Correcto: corresponde a ggetCatDesgloce
  usePostGuardarProformaMutation, // Correcto: corresponde a postGuardarProforma
  usePostResumenDeduccionesDescuentosPenalizacionesMutation, // Nuevo: corresponde a postResumenDeduccionesDescuentosPenalizaciones
  usePostObtenerPlantillaProformaMutation, // Nuevo: corresponde a postObtenerPlantillaProforma
  usePostBuscarSolicitudFacturaMutation, // Nuevo: corresponde a postBuscarSolicitudFactura
  useGetValidarConvenioProformaQuery, //valida cc
  useGetCatDescuentosProformaQuery, //Catalogo descuentos
  usePostRegresarProformaTempMutation,

  usePostBuscarDpMutation,
  usePostGuardarDpMutation,
  usePutActualizarDpMutation,
  useDeleteEliminarDpMutation,

  usePostMonedaDDpMutation,

  usePostCargarArchivoDDpMutation,
  usePostConsultarArchivoDdpMutation,
  usePutDescargarArchivoDdpMutation,

  usePutRechazarProformaMutation,
  usePutValidarDictamenMutation,

  useLazyGetPlantillasDdpQuery,
  useGetPlantillasDdpQuery,
  usePostPlantillaDdpMutation
} = informeProformaApi;
