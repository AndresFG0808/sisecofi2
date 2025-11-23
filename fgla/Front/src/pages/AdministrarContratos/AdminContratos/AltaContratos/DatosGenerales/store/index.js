import { apiSAT } from "../../../../../../store/features";
export const datosGenerales = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getDominioTecnologico: builder.query({
      query: () => ({
        url: "/admin-contratos/dominio-tecnologico",
        method: "get",
      }),
    }),
    getFondeoContrato: builder.query({
      query: () => ({
        url: "/admin-contratos/fondeo-contrato",
        method: "get",
      }),
    }),
    getTipoProcedimiento: builder.query({
      query: () => ({
        url: "/admin-contratos/tipo-procedimiento",
        method: "get",
      }),
    }),
    getProveedor: builder.query({
      query: () => ({
        url: "/admin-contratos/proovedor",
        method: "get",
      }),
    }),
    postDatosGenerales: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/datos-generales",
        method: "post",
        data,
      }),
      invalidatesTags: [
        "Datos-Generales-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    putDatosGenerales: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/datos-generales",
        method: "put",
        data,
      }),
      invalidatesTags: [
        "Datos-Generales-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    getAdmonGeneral: builder.query({
      query: () => ({
        url: "/admin-contratos/administracion-general",
        method: "get",
      }),
    }),
    getAdmonCentral: builder.query({
      query: () => ({
        url: "/admin-contratos/administracion-central",
        method: "get",
      }),
    }),
    getUsuarios: builder.query({
      query: () => ({
        url: "/admin-contratos/usuarios-organigrama",
        method: "get",
      }),
    }),

    getConvenioColaboracion: builder.query({
      query: () => ({
        url: "/admin-contratos/convenio-colaboracion",
        method: "get",
      }),
    }),

    getResponsabilidad: builder.query({
      query: () => ({
        url: "/admin-contratos/responsabilidad",
        method: "get",
      }),
    }),
    getDatosGenerales: builder.query({
      query: (id) => ({
        url: `/admin-contratos/datos-generales/${id}`,
        method: "get",
      }),
      providesTags: ["Datos-Generales-Contrato"],
    }),
    getParticipantes: builder.query({
      query: (id) => ({
        url: `/admin-contratos/datos-generales-paricipantes/${id}`,
        method: "get",
      }),
      providesTags: ["Participantes-Contrato"],
    }),
    getUsuariosFiltro: builder.mutation({
      query: (filtros) => ({
        url: "/admin-contratos/administradores-filtro",
        method: "post",
        data: filtros,
      }),
    }),
    getAdmonCentralPorGeneral: builder.mutation({
      query: (idAdmonGeneral) => ({
        url: `/admin-contratos/admon-central-por-general/${idAdmonGeneral}`,
        method: "get",
      }),
    }),


    postParticipantesContrato: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/participantes-contrato",
        method: "post",
        data,
      }),
      invalidatesTags: [
        "Participantes-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    putParticipantesContrato: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/participantes-contrato",
        method: "put",
        data,
      }),
      invalidatesTags: [
        "Participantes-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    deleteParticipantesContrato: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/datos-generales-participantes",
        method: "delete",
        data,
      }),
      invalidatesTags: [
        "Participantes-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    postReporteParticipantes: builder.mutation({
      query: (id) => ({
        url: `/admin-contratos/reporte-participantes-contrato/${id}`,
        method: "post",
      }),
    }),
  }),
  overrideExisting: false,
});
export const {
  useGetParticipantesQuery,
  useGetDatosGeneralesQuery,
  usePutDatosGeneralesMutation,
  usePutParticipantesContratoMutation,
  useGetResponsabilidadQuery,
  useGetAdmonCentralQuery,
  useGetAdmonGeneralQuery,
  useGetUsuariosQuery,
  useGetUsuariosFiltroMutation,
  useGetAdmonCentralPorGeneralMutation,
  useGetDominioTecnologicoQuery,
  useGetFondeoContratoQuery,
  useGetTipoProcedimientoQuery,
  useGetProveedorQuery,
  usePostDatosGeneralesMutation,
  usePostParticipantesContratoMutation,
  useDeleteParticipantesContratoMutation,
  usePostReporteParticipantesMutation,
  useGetConvenioColaboracionQuery,
} = datosGenerales;
