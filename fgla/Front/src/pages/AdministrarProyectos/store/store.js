import { apiSAT } from "../../../store/features";

export const admonProyectosApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getPlantillas: builder.query({
      query: () => ({ url: "/proyectos/plantilla", method: "get" }),
    }),
    getMoneda: builder.query({
      query: () => ({ url: "/proyectos/tipo-moneda", method: "get" }),
    }),
    getFinanciamiento: builder.query({
      query: () => ({ url: "/proyectos/financiamiento", method: "get" }),
    }),
    getClasificación: builder.query({
      query: () => ({
        url: "/proyectos/clasificacion-proyecto",
        method: "get",
      }),
    }),
    getPeriodos: builder.query({
      query: () => ({
        url: "/proyectos/periodos",
        method: "get",
      }),
    }),
    getObjetivos: builder.query({
      query: (catAlineacion) => ({
        url: `/proyectos/objetivos-alineacion/${catAlineacion}`,
        method: "get",
      }),
    }),
    getAreaPlaneacion: builder.query({
      query: () => ({
        url: "/proyectos/areas-planeacion",
        method: "get",
      }),
    }),
    getAdministracionPatrocinadora: builder.query({
      query: () => ({
        url: "/proyectos/administraciones-generales",
        method: "get",
      }),
    }),
    getAdministracionParticipante: builder.query({
      query: () => ({
        url: "/proyectos/administraciones-generales",
        method: "get",
      }),
    }),
    getAdministracionCentralPatrocinadora: builder.query({
      query: (idCatalogo) => ({
        url: `/proyectos/admon-central-por-general/${idCatalogo}`,
        method: "get",
      }),
    }),
    getProcedimientos: builder.query({
      query: () => ({
        url: "/proyectos/tipos-procedimientos",
        method: "get",
      }),
    }),
    getFicha: builder.query({
      query: (id) => ({
        url: `/proyectos/ficha/${id}`,
        method: "get",
      }),
      providesTags: ["Ficha"],
    }),
    getAgregarLider: builder.query({
      query: (id) => ({
        url: `/proyectos/agregar-lider/${id}`,
        method: "get",
      }),
    }),
    getAlineacion: builder.query({
      query: (id) => ({
        url: `/proyectos/alineaciones`,
        method: "get",
      }),
    }),
    getReporteAlineacion: builder.query({
      query: (id) => ({
        url: `/proyectos/reporte-alineacion/${id}`,
        method: "get",
      }),
    }),
    getReporteLideres: builder.query({
      query: (id) => ({
        url: `/proyectos/reporte-lideres/${id}`,
        method: "get",
      }),
    }),
    getProyectosUsuarios: builder.query({
      query: () => ({
        url: `/proyectos/usuarios-organigrama`,
        method: "get",
      }),
    }),
    postModificarFicha: builder.mutation({
      query: ({ data, id }) => ({
        url: `/proyectos/modificar-ficha/${id}`,
        method: "post",
        data: data,
      }),
      invalidatesTags: ["Ficha"],
    }),
    postFichaUsuarios: builder.mutation({
      query: (data) => ({
        url: `/proyectos/ficha/usuarios`,
        method: "post",
        data: data,
      }),
    }),
  }),
  overrideExisting: false,
});

export const {
  useGetReporteAlineacionQuery,
  useGetReporteLideresQuery,
  useGetPlantillasQuery,
  useGetMonedaQuery,
  useGetAdministracionParticipanteQuery,
  useGetAdministracionPatrocinadoraQuery,
  useGetClasificaciónQuery,
  useGetProcedimientosQuery,
  useGetFinanciamientoQuery,
  useLazyGetAdministracionCentralPatrocinadoraQuery,
  useLazyGetMonedaQuery,
  useLazyGetReporteLideresQuery,
  useLazyGetObjetivosQuery,
  useLazyGetReporteAlineacionQuery,
  useGetFichaQuery,
  useLazyGetFichaQuery,
  useGetAgregarLiderQuery,
  usePostFichaUsuariosMutation,
  usePostModificarFichaMutation,
  useGetAlineacionQuery,
  useGetProyectosUsuariosQuery,
  useGetAreaPlaneacionQuery,
  useGetPeriodosQuery,
  useGetObjetivosQuery,
} = admonProyectosApi;
