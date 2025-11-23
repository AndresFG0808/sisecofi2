import { apiSAT } from "../../../../../store/features";

const getFormData = (data) => {
  const formData = new FormData();
  Object.keys(data).forEach((key) => {
    formData.append(key, data[key]);
  });
  return formData;
};

export const generarNotaCredito = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getValidarConvenioNota: builder.query({
      query: (id) => ({
        url: "/admin-devengados/AplicaCC/" + id,
        method: "get",
      }),
    }),
    postLeerXMLNota: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/obtener-datosXML",
        method: "post",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    postNotascredito: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/obtener-notas-credito",
        method: "post",
        data,
      }),
    }),
    postGuardarNota: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/guardar-nota",
        method: "post",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    postActualizarNota: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/editar-factura",
        method: "post",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    postCancelarNota: builder.mutation({
      query: ({ id, data }) => ({
        url: "/admin-devengados/cancelar-nota-credito/" + id,
        method: "post",
        data,
      }),
    }),
    getCatStatusNotas: builder.query({
      query: () => ({
        url: "/admin-devengados/estatus-nota-credito",
        method: "get",
      }),
    }),
    getCatIvaNotas: builder.query({
      query: () => ({
        url: "/admin-devengados/cat-iva",
        method: "get",
      }),
    }),
    getCatTipoMonedaNotas: builder.query({
      query: () => ({
        url: "/admin-devengados/tipo-moneda",
        method: "get",
      }),
    }),
    postActualizarResumenNotas: builder.mutation({
      query: (data) => ({
        url: "/admin-devengados/actualizar-resumen-facturado",
        method: "put",
        data,
      }),
    }),
  }),
});

export const {
  useGetValidarConvenioNotaQuery,
  usePostLeerXMLNotaMutation,
  usePostNotascreditoMutation,
  usePostGuardarNotaMutation,
  usePostActualizarNotaMutation,
  usePostCancelarNotaMutation,
  useGetCatStatusNotasQuery,
  useGetCatIvaNotasQuery,
  useGetCatTipoMonedaNotasQuery,
  usePostActualizarResumenNotasMutation
} = generarNotaCredito;
