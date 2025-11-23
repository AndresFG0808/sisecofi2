import { apiSAT } from "../../../../../../../store/features";
export const registroConvenioApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postFindConvenios: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/convenios-modificatorios`,
        method: "post",
        data,
      }),
    }),
    getConvenioById: builder.query({
      query: (idConvenio) => ({
        url: `/admin-contratos/convenios-modificatorios/obtener-convenio/${idConvenio}`,
        method: "get",
      }),
      providesTags: ["Convenio-Modificatorio"],
    }),
    postRegistrarConvenio: builder.mutation({
      query: ({ data, idContrato }) => ({
        url: `/admin-contratos/convenios-modificatorios/registrar/${idContrato}`,
        method: "post",
        data,
      }),
      invalidatesTags: [
        "Convenio-Modificatorio",
        "Ultima-Modificacion-Convenio",
      ],
    }),
    postReporteConvenios: builder.mutation({
      query: (id) => ({
        url: `/admin-contratos/reporte-convenio-modificatorio/${id}`,
        method: "post",
      }),
    }),
    putModificarConvenio: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/convenios-modificatorios/modificar`,
        method: "put",
        data,
      }),
      invalidatesTags: [
        "Convenio-Modificatorio",
        "Ultima-Modificacion-Convenio",
      ],
    }),
    getPorcentajeIva: builder.query({
      query: () => ({
        url: `/admin-contratos/porcentaje-iva`,
        method: "get",
      }),
    }),
    getVigenciaMontos: builder.query({
      query: (id) => ({
        url: `/admin-contratos/vigencia-montos/${id}`,
        method: "get",
      }),
      providesTags: ["Vigencia-Contrato"],
    }),
    getDatosIniciales: builder.query({
      query: (idContrato) => ({
        url: `/admin-contratos/convenios-modificatorios/datos-iniciales/${idContrato}`,
        method: "get",
      }),
      keepUnusedDataFor: 10,
    }),
    getTipoMonedaCM: builder.query({
      query: () => ({
        url: "/admin-contratos/tipo-moneda",
        method: "get",
      }),
    }),
    getUltimaModificacionConvenio: builder.query({
      query: (idConvenio) => ({
        url: `/admin-contratos/convenios-modificatorios/ultima-mod/${idConvenio}`,
        method: "get",
      }),
      providesTags: ["Ultima-Modificacion-Convenio"],
    }),
  }),
  overrideExisting: false,
});

export const {
  usePostFindConveniosMutation,
  usePostRegistrarConvenioMutation,
  usePutModificarConvenioMutation,
  useGetConvenioByIdQuery,
  useGetPorcentajeIvaQuery,
  useGetVigenciaMontosQuery,
  useGetDatosInicialesQuery,
  useGetTipoMonedaCMQuery,
  usePostReporteConveniosMutation,
  useGetUltimaModificacionConvenioQuery,
} = registroConvenioApi;
