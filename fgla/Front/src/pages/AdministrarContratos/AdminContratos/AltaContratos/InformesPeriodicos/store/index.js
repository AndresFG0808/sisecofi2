import { apiSAT } from "../../../../../../store/features";

export const informePeriodicoApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getCatPeriodicidad: builder.query({
      query: () => ({
        url: "/admin-contratos/cat-periodicidad",
        method: "get",
      }),
    }),
    getInformesPeridicos: builder.query({
      query: (id) => ({
        url: `/admin-contratos/consultar-informes-documentales-periodicos/${id}`,
        method: "get",
      }),
      providesTags: ["Informes-Periodicos-Contrato"],
    }),
    getInformesPeriodicosReporte: builder.query({
      query: (id) => ({
        url: `/admin-contratos/reporte-informes-documentales-periodicos/${id}`,
        method: "get",
      }),
    }),
    postGuardarInformesPeriodicos: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/guardar-informes-documentales-periodicos",
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    putActualizarInformesPeriodicos: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/actualizar-informes-documentales-periodicos",
        method: "put",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    postEliminarInformesPeriodicos: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/eliminar-informes-documentales-periodicos",
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
  }),
  overrideExisting: false,
});

export const {
  useGetCatPeriodicidadQuery,
  useGetInformesPeridicosQuery,
  useLazyGetInformesPeriodicosReporteQuery,
  usePutActualizarInformesPeriodicosMutation,
  usePostEliminarInformesPeriodicosMutation,
  usePostGuardarInformesPeriodicosMutation,
} = informePeriodicoApi;
