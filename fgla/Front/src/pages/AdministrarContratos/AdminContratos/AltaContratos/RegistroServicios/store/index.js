import { apiSAT } from "../../../../../../store/features";
export const registroServicios = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getRegistroServicios: builder.query({
      query: (id) => ({
        url: `/admin-contratos/registro-servicios/${id}`,
        method: "get",
      }),
      providesTags: ["Registro-Contrato", "Grupos-Contrato"],
    }),
    getValidarRegistroServicios: builder.query({
      query: (id) => ({
        url: `/admin-contratos/validar-registro-servicios/${id}`,
        method: "get",
      }),
    }),
    putRegistroServicios: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/registro-servicios`,
        method: "put",
        data,
      }),
      invalidatesTags: ["Registro-Contrato", "Ultima-Modificacion-Contrato"],
    }),
    postRegistroServicios: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/registro-servicios`,
        method: "post",
        data,
      }),
      invalidatesTags: ["Registro-Contrato", "Ultima-Modificacion-Contrato"],
    }),
    deleteRegistroServicios: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/eliminar-registro-servicio`,
        method: "delete",
        data,
      }),
      invalidatesTags: ["Registro-Contrato", "Ultima-Modificacion-Contrato"],
    }),
    getGrupoServicios: builder.query({
      query: (id) => ({
        url: `/admin-contratos/grupo-servicio/${id}`,
        method: "get",
      }),
      providesTags: ["Grupos-Contrato"],
    }),
    getTipoUnidad: builder.query({
      query: (id) => ({
        url: `/admin-contratos/tipo-unidad`,
        method: "get",
      }),
    }),
    getDependencias: builder.query({
      query: (idContrato) => ({
        url: `/admin-devengados/comprobar-dependencias/${idContrato}`,
        method: "get",
      }),
      providesTags: ["Dependencias"],
    }),
    postReporteRegistroServicios: builder.mutation({
      query: (id) => ({
        url: `/admin-contratos/reporte-registro-servicio/${id}`,
        method: "post",
      }),
    }),
  }),
  overrideExisting: false,
});

export const {
  useGetTipoUnidadQuery,
  useGetDependenciasQuery,
  useGetGrupoServiciosQuery,
  useGetRegistroServiciosQuery,
  useLazyGetRegistroServiciosQuery,
  useGetValidarRegistroServiciosQuery,
  useLazyGetValidarRegistroServiciosQuery,
  usePutRegistroServiciosMutation,
  usePostRegistroServiciosMutation,
  useDeleteRegistroServiciosMutation,
  usePostReporteRegistroServiciosMutation,
} = registroServicios;
