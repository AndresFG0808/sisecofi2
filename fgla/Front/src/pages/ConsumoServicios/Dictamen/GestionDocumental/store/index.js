import { apiSAT } from "../../../../../store/features";

export const gestionDocumentalDictamenApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getGestionDocumentalDictamen: builder.query({
      query: (id) => ({
        url: `/admin-devengados/dictamenes/gestion-documental/${id}`,
        method: "get",
      }),
      providesTags: ["Gestion-Documental-Dictamen"],
    }),
    putDescargaArchivo: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-devengados/dictamenes/descargar-archivo`,
        method: "put",
        data,
        headers: { "content-type": "multipart/form-data" },
      }),
    }),
    postDescargaSatCloudDictamen: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-devengados/dictamenes/descarga-sat-cloud`,
        method: "post",
        data,
      }),
    }),
    postDescargaMasivaDictamen: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-devengados/dictamenes/descarga-masiva`,
        method: "post",
        data,
      }),
    }),
    postEliminarArchivoDictamen: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-devengados/dictamenes/eliminar-archivos-gestion`,
        method: "post",
        data,
      }),
      invalidatesTags: ["Gestion-Documental-Dictamen"],
    }),
    putCargarArchivoDictamen: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-devengados/dictamenes/cargar-archivo-dictamen`,
        method: "put",
        data,
        headers: { "content-type": "multipart/form-data" },
      }),
      invalidatesTags: ["Gestion-Documental-Dictamen"],
    }),
  }),
  overrideExisting: true,
});

export const {
  useGetGestionDocumentalDictamenQuery,
  useLazyGetGestionDocumentalDictamenQuery,
  usePostDescargaMasivaDictamenMutation,
  usePostDescargaSatCloudDictamenMutation,
  usePostEliminarArchivoDictamenMutation,
  usePutCargarArchivoDictamenMutation,
  usePutDescargaArchivoMutation,
} = gestionDocumentalDictamenApi;
