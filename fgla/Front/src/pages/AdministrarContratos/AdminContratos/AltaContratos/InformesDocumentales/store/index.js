import { apiSAT } from "../../../../../../store/features";
export const informesDocumentales = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postCrearInformes: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/guardar-informes-documentales",
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    putGuardarInformes: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/actualizar-informes-documentales",
        method: "put",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    getInformes: builder.query({
      query: (id) => ({
        url: `/admin-contratos/consultar-informes-documentales/${id}`,
        method: "get",
      }),
      providesTags: ["Informes-Unica-vez-Contrato"],
    }),
    getReporteInformes: builder.query({
      query: (id) => ({
        url: `/admin-contratos/reporte-informes-documentales/${id}`,
        method: "get",
      }),
    }),
    postBorrarInformes: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/eliminar-informes-documentales",
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
  }),
});

export const {
  useGetInformesQuery,
  useLazyGetReporteInformesQuery,
  usePostCrearInformesMutation,
  usePutGuardarInformesMutation,
  usePostBorrarInformesMutation,
} = informesDocumentales;
