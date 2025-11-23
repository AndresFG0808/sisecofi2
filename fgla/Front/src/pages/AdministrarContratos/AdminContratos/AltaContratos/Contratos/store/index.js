import { apiSAT } from "../../../../../../store/features";

export const altaContratosApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postContratos: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/buscar-contrato",
        method: "post",
        data,
      }),
    }),
    getProveedores: builder.query({
      query: () => ({ url: "/admin-contratos/proovedor", method: "get" }),
    }),
    getAdmonCentrales: builder.query({
      query: () => ({
        url: "/proyectos/administraciones-centrales",
        method: "get",
      }),
    }),
    postReporteContrato: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/reporte-contrato",
        method: "post",
        data,
      }),
    }),
    getEstatusContratos: builder.query({
      query: () => ({
        url: "/admin-contratos/estatus-contrato",
        method: "get",
      }),
    }),
  }),
  overrideExisting: false,
});
export const {
  usePostReporteContratoMutation,
  useGetEstatusContratosQuery,
  usePostContratosMutation,
  useGetAdmonCentralesQuery,
  useLazyGetAdmonCentralesQuery,
  useLazyGetProveedoresQuery,
  useGetProveedoresQuery,
} = altaContratosApi;
