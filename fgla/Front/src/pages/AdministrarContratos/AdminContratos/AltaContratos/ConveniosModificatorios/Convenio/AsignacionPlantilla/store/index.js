import { apiSAT } from "../../../../../../../../store/features";

export const asignacionPlantillaConvenioApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getPlantillasConvenio: builder.query({
      query: () => ({
        url: "/admin-contratos/obtener-plantillas",
        method: "get",
      }),
    }),
    getAsociacionesConvenio: builder.query({
      query: (id) => ({
        url: `/admin-contratos/convenios-modificatorios/obtener-asociaciones/${id}`,
        method: "get",
      }),
      providesTags: ["Asignacion-Plantillas-Convenio"],
    }),
    postAsociarPlantillaConvenio: builder.mutation({
      query: ({ data, id }) => ({
        url: `/admin-contratos/convenios-modificatorios/asociar-plantilla/${id}`,
        method: "post",
        data,
      }),
      invalidatesTags: [
        "Asignacion-Plantillas-Convenio",
        "Gestion-Documental-Convenio",
        "Ultima-Modificacion-Convenio",
      ],
    }),
    putEliminarAsociacionesConvenio: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/convenios-modificatorios/eliminar-asociaciones`,
        method: "put",
        data,
      }),
      invalidatesTags: [
        "Asignacion-Plantillas-Convenio",
        "Gestion-Documental-Convenio",
        "Ultima-Modificacion-Convenio",
      ],
    }),
  }),
  overrideExisting: false,
});
export const {
  useGetPlantillasConvenioQuery,
  useGetAsociacionesConvenioQuery,
  usePostAsociarPlantillaConvenioMutation,
  usePutEliminarAsociacionesConvenioMutation,
} = asignacionPlantillaConvenioApi;
