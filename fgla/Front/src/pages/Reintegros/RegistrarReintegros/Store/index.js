import { apiSAT } from "../../../../store/features";

const registrarReintegro = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getRRContratosVigentesCat: builder.query({
      query: () => ({
        url: "/admin-devengados/contrato-vigente",
        method: "get",
      }),
    }),
    postRRContratosCat: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/contratos-vigentes",
        method: "post",
        data
      }),
    }),
    getRRTipoCat: builder.query({
      query: () => ({
        url: "/admin-contratos/reintegros/tipo-reintegros",
        method: "get",
      }),
    }),
    getReintegros: builder.query({
      query: (id) => ({
        url:
          "/admin-contratos/reintegros/buscar-reintegros-asociados?idContrato=" +
          id,
        method: "get",
      }),
    }),
    getReintegrosReporte: builder.query({
      query: (id) => ({
        url:
          "/admin-contratos/reintegros/generar-reporte-reintegros?idContrato=" +
          id,
        method: "get",
        responseType: "arraybuffer",
      }),
    }),
    postCrearReintegros: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/reintegros/crear-reintegro",
        method: "post",
        data,
      }),
      invalidatesTags: ["Gestion-Documental-Reintegros"],
    }),
    putModificarReintegros: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/reintegros/modificar-reintegro",
        method: "put",
        data,
      }),
      invalidatesTags: ["Gestion-Documental-Reintegros"],
    }),
    deleteEliminarReintegros: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/reintegros/eliminar-reintegros",
        method: "delete",
        data,
      }),
      invalidatesTags: ["Gestion-Documental-Reintegros"],
    }),
  }),
});

export const {
  useGetRRContratosCatQuery,
  usePostRRContratosCatMutation,
  useGetRRTipoCatQuery,
  useLazyGetReintegrosQuery,
  useLazyGetReintegrosReporteQuery,
  usePostCrearReintegrosMutation,
  usePutModificarReintegrosMutation,
  useDeleteEliminarReintegrosMutation,
  useGetRRContratosVigentesCatQuery
} = registrarReintegro;
