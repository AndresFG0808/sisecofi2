import { apiSAT } from "../../../../../../store/features";

export const gruposServicioApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    putActualizarGrupos: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/actualizar-grupo-servicio",
        method: "put",
        data,
      }),
      invalidatesTags: ["Grupos-Contrato", "Ultima-Modificacion-Contrato"],
    }),
    postGuardarGrupos: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/guardar-grupo-servicio",
        method: "post",
        data,
      }),
      invalidatesTags: ["Grupos-Contrato", "Ultima-Modificacion-Contrato"],
    }),
    deleteGrupos: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/eliminar-grupo-servicio",
        method: "delete",
        data,
      }),
      invalidatesTags: ["Grupos-Contrato", "Ultima-Modificacion-Contrato"],
    }),
    getGrupo: builder.query({
      query: (id) => ({
        url: `/admin-contratos/grupo-servicio/${id}`,
        method: "get",
      }),
      providesTags: ["Grupos-Contrato"],
    }),
    getTipoConsumo: builder.query({
      query: () => ({ url: "/admin-contratos/tipo-consumo", method: "get" }),
    }),
  }),

  overrideExisting: false,
});
export const {
  usePutActualizarGruposMutation,
  usePostGuardarGruposMutation,
  useLazyGetGrupoQuery,
  useGetGrupoQuery,
  useGetTipoConsumoQuery,
  useDeleteGruposMutation,
} = gruposServicioApi;
