import { apiSAT } from "../../../../../../../../store/features";
export const gestionDocumentalConvenioApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getEstructuraDocumentalConvenio: builder.query({
      query: (id) => ({
        url: `/admin-contratos/convenios-modificatorios/obtener-estructura-documental/${id}`,
        method: "get",
      }),
      providesTags: ["Gestion-Documental-Convenio"],
    }),
    postDescargaSatCloud: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/descarga-sat-cloud`,
        method: "post",
        data,
      }),
    }),
    postDescargaMasiva: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/descarga-masiva`,
        method: "post",
        data,
      }),
    }),
    putCargarArchivoConvenio: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/cargar-archivo-contrato`,
        method: "put",
        data,
        headers: { "content-type": "multipart/form-data" },
      }),
      invalidatesTags: [
        "Gestion-Documental-Convenio",
        "Ultima-Modificacion-Convenio",
      ],
    }),
    putDescargaArchivoConvenio: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/descargar-archivo`,
        method: "put",
        data,
        headers: { "content-type": "multipart/form-data" },
      }),
    }),
    putEliminarArchivoConvenio: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/eliminar-archivo`,
        method: "put",
        data,
      }),
      invalidatesTags: [
        "Gestion-Documental-Convenio",
        "Ultima-Modificacion-Convenio",
      ],
    }),
  }),
  overrideExisting: true,
});

export const {
  useGetEstructuraDocumentalConvenioQuery,
  usePostDescargaMasivaMutation,
  usePostDescargaSatCloudMutation,
  usePutCargarArchivoConvenioMutation,
  usePutEliminarArchivoConvenioMutation,
  usePutDescargaArchivoConvenioMutation,
} = gestionDocumentalConvenioApi;
