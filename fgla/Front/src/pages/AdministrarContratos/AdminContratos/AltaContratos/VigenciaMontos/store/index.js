import { apiSAT } from "../../../../../../store/features";
export const vigenciaMontosApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postVigenciaMontos: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/vigencia-montos",
        method: "post",
        data,
      }),
      invalidatesTags: ["Vigencia-Contrato", "Ultima-Modificacion-Contrato"],
    }),
    putVigenciaMontos: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/vigencia-montos",
        method: "put",
        data,
      }),
      invalidatesTags: ["Vigencia-Contrato", "Ultima-Modificacion-Contrato"],
    }),
    getVigenciaMontos: builder.query({
      query: (id) => ({
        url: `/admin-contratos/vigencia-montos/${id}`,
        method: "get",
      }),
      providesTags: ["Vigencia-Contrato"],
    }),
    getTipoMoneda: builder.query({
      query: () => ({
        url: `/admin-contratos/tipo-moneda`,
        method: "get",
      }),
    }),
    getPorcentajeIeps: builder.query({
      query: () => ({
        url: `/admin-contratos/porcentaje-ieps`,
        method: "get",
      }),
    }),
    getPorcentajeIva: builder.query({
      query: () => ({
        url: `/admin-contratos/porcentaje-iva`,
        method: "get",
      }),
    }),
  }),
});

export const {
  useGetPorcentajeIepsQuery,
  useGetPorcentajeIvaQuery,
  useGetTipoMonedaQuery,
  usePostVigenciaMontosMutation,
  useGetVigenciaMontosQuery,
  useLazyGetVigenciaMontosQuery,
  usePutVigenciaMontosMutation,
} = vigenciaMontosApi;
