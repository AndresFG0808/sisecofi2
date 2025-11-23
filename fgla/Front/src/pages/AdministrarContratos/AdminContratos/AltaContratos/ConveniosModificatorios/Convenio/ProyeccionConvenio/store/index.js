import { apiSAT } from "../../../../../../../../store/features";

export const proyeccionConvenioApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postProcesarProyeccion: builder.mutation({
      query: ({ data, idConvenio }) => ({
        url: `/admin-contratos/convenios-modificatorios/procesar-proyeccion/${idConvenio}`,
        method: "post",
        data,
      }),
      invalidatesTags: ["Proyeccion-Convenio", "Ultima-Modificacion-Convenio"],
    }),
    getLayoutProyeccion: builder.query({
      query: (idConvenio) => ({
        url: `/admin-contratos/convenios-modificatorios/layout/${idConvenio}`,
        method: "get",
      }),
    }),
    getCasoNegocio: builder.query({
      query: (idConvenio) => ({
        url: `/admin-contratos/convenios-modificatorios/ver-caso-negocio/${idConvenio}`,
        method: "get",
      }),
      providesTags: ["Proyeccion-Convenio"],
    }),
    getReporteCasoNegocio: builder.query({
      query: (idConvenio) => ({
        url: `/admin-contratos/convenios-modificatorios/caso-negocio/exportar-excel/${idConvenio}`,
        method: "get",
      }),
    }),
  }),
  overrideExisting: false,
});

export const {
  usePostProcesarProyeccionMutation,
  useLazyGetLayoutProyeccionQuery,
  useGetCasoNegocioQuery,
  useLazyGetReporteCasoNegocioQuery,
} = proyeccionConvenioApi;
