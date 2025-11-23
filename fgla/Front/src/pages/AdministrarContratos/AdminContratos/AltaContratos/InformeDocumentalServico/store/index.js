import { apiSAT } from "../../../../../../store/features";

export const informeDocumentalServicioApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getInformeDocumentalServicio: builder.query({
      query: (id) => ({
        url: `/admin-contratos/consultar-informes-documentales-servicios/${id}`,
        method: "get",
      }),
      providesTags: ["Informes-Servicios-Contrato"],
    }),
    getReporteDocumentalServicio: builder.query({
      query: (id) => ({
        url: `/admin-contratos/reporte-informes-documentales-servicios/${id}`,
        method: "get",
      }),
    }),
    getCatPeriodicidadServicio: builder.query({
      query: () => ({
        url: "/admin-contratos/cat-periodicidad",
        method: "get",
      }),
    }),
    postGuardarInformesServicios: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/guardar-informes-documentales-servicios",
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    putActualizarInformesServicios: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/actualizar-informes-documentales-servicios",
        method: "put",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    postEliminarInformesServicios: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/eliminar-informes-documentales-servicios",
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
  }),

  overrideExisting: false,
});

export const {
  usePutActualizarInformesServiciosMutation,
  useGetInformeDocumentalServicioQuery,
  useLazyGetReporteDocumentalServicioQuery,
  useGetCatPeriodicidadServicioQuery,
  usePostEliminarInformesServiciosMutation,
  usePostGuardarInformesServiciosMutation,
} = informeDocumentalServicioApi;
