import { apiSAT } from "../../../../../../store/features";

export const asignacionPlantillaApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getPlantillas: builder.query({
      query: () => ({
        url: "/admin-contratos/obtener-plantillas",
        method: "get",
      }),
    }),
    getAsociacionesContrato: builder.query({
      query: (id) => ({
        url: `/admin-contratos/obtener-asociaciones/${id}`,
        method: "get",
      }),
      providesTags: ["Asignacion-Plantillas-Contrato"],
    }),
    postAsociarPlantilla: builder.mutation({
      query: ({ data, id }) => ({
        url: `/admin-contratos/asociar-plantilla/${id}`,
        method: "post",
        data,
      }),
      invalidatesTags: [
        "Asignacion-Plantillas-Contrato",
        "Gestion-Documental-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    putEliminarAsociaciones: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/eliminar-asociaciones`,
        method: "put",
        data,
      }),
      invalidatesTags: [
        "Asignacion-Plantillas-Contrato",
        "Gestion-Documental-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
  }),
  overrideExisting: true,
});
export const {
  useGetPlantillasQuery,
  useGetAsociacionesContratoQuery,
  usePostAsociarPlantillaMutation,
  usePutEliminarAsociacionesMutation,
} = asignacionPlantillaApi;
