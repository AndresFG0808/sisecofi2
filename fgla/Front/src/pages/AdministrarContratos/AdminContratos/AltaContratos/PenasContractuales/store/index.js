import { apiSAT } from "../../../../../../store/features";

export const pencasContractuales = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getPenasContractuales: builder.query({
      query: (id) => ({
        url: `/admin-contratos/obtener-penas-contractuales/${id}`,
        method: "get",
      }),
      providesTags: ["Penas-Contractuales-Contrato"],
    }),
    getReportePenasContractuales: builder.query({
      query: (id) => ({
        url: `/admin-contratos/reporte-penas-contractuales/${id}`,
        method: "get",
      }),
    }),
    postCrearPenasContractuales: builder.mutation({
      query: ({ data, idContrato }) => ({
        url: `/admin-contratos/crear-pena-contractual/${idContrato}`,
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    putActualizarPenasContractuales: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/actualizar-pena-contractual",
        method: "put",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    putEliminarPensContractuales: builder.mutation({
      query: (params) => ({
        url: "/admin-contratos/eliminar-pena-contractual?" + params,
        method: "put",
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
  }),
  overrideExisting: false,
});

export const {
  useGetPenasContractualesQuery,
  useLazyGetReportePenasContractualesQuery,
  usePostCrearPenasContractualesMutation,
  usePutActualizarPenasContractualesMutation,
  usePutEliminarPensContractualesMutation,
} = pencasContractuales;
