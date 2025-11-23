import { apiSAT } from "../../../../../../../../store/features";

export const registroServiciosConvenio = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getServiciosConvenio: builder.query({
      query: (idConvenio) => ({
        url: `/admin-contratos/convenios-modificatorios/servicios-convenio/${idConvenio}`,
        method: "get",
      }),
      providesTags: ["Registro-Servicios-Convenio"],
    }),
    getReporteServiciosConvenio: builder.query({
      query: (idConvenio) => ({
        url: `/admin-contratos/convenios-modificatorios/servicios-convenio/exportar/${idConvenio}`,
        method: "get",
      }),
    }),
    postGuardarRegistroServiciosConvenio: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/convenios-modificatorios/servicios-convenio/guardar`,
        method: "post",
        data,
      }),
      invalidatesTags: [
        "Registro-Servicios-Convenio",
        "Ultima-Modificacion-Convenio",
      ],
    }),
    postValidarServiciosConvenio: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/convenios-modificatorios/servicios-convenio/validar`,
        method: "post",
        data,
      }),
    }),
  }),
  overrideExisting: false,
});
export const {
  useGetServiciosConvenioQuery,
  usePostValidarServiciosConvenioMutation,
  usePostGuardarRegistroServiciosConvenioMutation,
  useLazyGetReporteServiciosConvenioQuery,
} = registroServiciosConvenio;
