import { apiSAT } from "../../../../store/features";
export const gestionDocumentalReintegrosApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getEstructuraDocumentalReintegro: builder.query({
      query: (id) => ({
        url: `/admin-contratos/reintegros/gestion-documental/${id}`,
        method: "get",
      }),
      providesTags: ["Gestion-Documental-Reintegros"],
    }),
    postDescargaSatCloudReintegro: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/descarga-sat-cloud`,
        method: "post",
        data,
      }),
    }),
    postDescargaMasivaReintegro: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/descarga-masiva`,
        method: "post",
        data,
      }),
    }),
    putCargarArchivoReintegro: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/cargar-archivo-contrato`,
        method: "put",
        data,
        headers: { "content-type": "multipart/form-data" },
      }),
      invalidatesTags: ["Gestion-Documental-Reintegros"],
    }),
    putDescargaArchivoReintegro: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/descargar-archivo`,
        method: "put",
        data,
        headers: { "content-type": "multipart/form-data" },
      }),
    }),
    putEliminarArchivoReintegro: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/eliminar-archivo`,
        method: "put",
        data,
      }),
      invalidatesTags: ["Gestion-Documental-Reintegros"],
    }),
  }),
  overrideExisting: true,
});

export const {
  useGetEstructuraDocumentalReintegroQuery,
  usePostDescargaMasivaReintegroMutation,
  usePostDescargaSatCloudReintegroMutation,
  usePutCargarArchivoReintegroMutation,
  usePutEliminarArchivoReintegroMutation,
  usePutDescargaArchivoReintegroMutation,
} = gestionDocumentalReintegrosApi;
