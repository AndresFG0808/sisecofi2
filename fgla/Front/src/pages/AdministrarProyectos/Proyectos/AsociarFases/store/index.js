import { apiSAT } from "../../../../../store/features";
export const asociarFasesApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getAsociaciones: builder.query({
      query: (id) => ({ url: `/proyectos/asociaciones/${id}`, method: "get" }),
      providesTags: ["Asociación"],
    }),
    getFases: builder.query({
      query: () => ({ url: "/proyectos/fases", method: "get" }),
      providesTags: ["Asociación"],
    }),
    getPlantillaFaseById: builder.query({
      query: (id) => ({
        url: `/proyectos/plantilla-fase/${id}`,
        method: "get",
      }),
    }),
    postAsociaciones: builder.mutation({
      query: ({ id, data }) => ({
        url: `/proyectos/asociaciones/guardar/${id}`,
        method: "post",
        data: data,
      }),
      invalidatesTags: ["Asociación", "Gestión"],
    }),
    postCrearAsociacion: builder.mutation({
      query: ({ id, data }) => ({
        url: `/proyectos/crear-asociacion/${id}`,
        method: "post",
        data: data,
      }),
      invalidatesTags: ["Asociación", "Gestión"],
    }),
    getReporte: builder.query({
      query: (idProyecto) => ({
        url: `/proyectos/reporte/asociaciones/${idProyecto}`,
      }),
    }),
  }),
});
export const {
  useGetAsociacionesQuery,
  useLazyGetAsociacionesQuery,
  useGetFasesQuery,
  useGetPlantillaFaseByIdQuery,
  usePostAsociacionesMutation,
  useLazyGetReporteQuery,
  useLazyGetPlantillaFaseByIdQuery,
  usePostCrearAsociacionMutation,
} = asociarFasesApi;
