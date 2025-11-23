import { apiSAT } from "../../../../store/features";
export const construirReporteApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postTituloServicio: builder.mutation({
      query: ({ data }) => ({
        url: `/reportes/dinamico/titulo-servicio`,
        method: "post",
        data,
      }),
    }),
    postNombreCortoProyecto: builder.mutation({
      query: ({ data }) => ({
        url: `/reportes/dinamico/nombre-corto-proyecto`,
        method: "post",
        data,
      }),
    }),
    postNombreCortoContrato: builder.mutation({
      query: ({ data }) => ({
        url: `/reportes/dinamico/nombre-corto-contrato`,
        method: "post",
        data,
      }),
    }),
    getRazonSocial: builder.query({
      query: () => ({
        url: `/reportes/dinamico/razon-social`,
        method: "get",
      }),
    }),
    getConvenioColaboracion: builder.query({
      query: () => ({
        url: `/reportes/dinamico/convenio-colaboracion`,
        method: "get",
      }),
    }),
    getEstatusProyecto: builder.query({
      query: () => ({
        url: `/reportes/dinamico/estatus-proyecto`,
        method: "get",
      }),
    }),
    getEstatusContrato: builder.query({
      query: () => ({
        url: `/reportes/dinamico/estatus-contrato`,
        method: "get",
      }),
    }),
    getDominoTecnologico: builder.query({
      query: () => ({
        url: `/reportes/dinamico/dominio-tecnologico`,
        method: "get",
      }),
    }),
    postReporteDinamico: builder.mutation({
      query: ({ data }) => ({
        url: "/reportes/dinamico/reporte-dinamico",
        method: "post",
        data,
      }),
    }),
    postExportarReporteDinamico: builder.mutation({
      query: ({ data }) => ({
        url: "/reportes/dinamico/reporte-dinamico-export",
        method: "post",
        data,
      }),
    }),
  }),
  overrideExisting: false,
});

export const {
  usePostTituloServicioMutation,
  usePostNombreCortoContratoMutation,
  usePostNombreCortoProyectoMutation,
  useGetConvenioColaboracionQuery,
  usePostExportarReporteDinamicoMutation,
  usePostReporteDinamicoMutation,
  useGetRazonSocialQuery,
  useGetEstatusProyectoQuery,
  useGetEstatusContratoQuery,
  useGetDominoTecnologicoQuery,
} = construirReporteApi;
