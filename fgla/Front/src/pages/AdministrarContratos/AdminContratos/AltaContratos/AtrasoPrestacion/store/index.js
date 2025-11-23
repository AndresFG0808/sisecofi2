import { apiSAT } from "../../../../../../store/features";

export const atrasoPrestacion = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getAtrasoPrestaciones: builder.query({
      query: (id) => ({
        url: `/admin-contratos/atrasos-prestacion/${id}`,
        method: "get",
      }),
      providesTags: ["Atraso-Prestacion-Contrato"],
    }),
    postAtrasoPrestaciones: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/atraso-prestacion",
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    putAtrasoPrestaciones: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/atraso-prestacion",
        method: "put",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    deleteAtrasoPrestaciones: builder.mutation({
      query: (ids) => ({
        url: "/admin-contratos/atrasos-prestacion",
        method: "delete",
        data: ids,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    getReporteAtrasoPrestaciones: builder.query({
      query: (id) => ({
        url: `/admin-contratos/reporte-atraso-prestacion/${id}`,
        method: "post",
      }),
    }),
  }),
  overrideExisting: false,
});

console.log(atrasoPrestacion);

export const {
  useGetAtrasoPrestacionesQuery,
  useLazyGetReporteAtrasoPrestacionesQuery,
  usePostAtrasoPrestacionesMutation,
  usePutAtrasoPrestacionesMutation,
  useDeleteAtrasoPrestacionesMutation,
} = atrasoPrestacion;
