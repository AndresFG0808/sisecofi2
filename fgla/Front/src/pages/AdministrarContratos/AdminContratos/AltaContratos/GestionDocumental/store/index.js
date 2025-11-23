import { apiSAT } from "../../../../../../store/features";
export const gestionDocumentalApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getEstructuraDocumental: builder.query({
      query: (id) => ({
        url: `/admin-contratos/obtener-estructura-documental/${id}`,
        method: "get",
      }),
      providesTags: ["Gestion-Documental-Contrato"],
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
    putCargarArchivoContrato: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/cargar-archivo-contrato`,
        method: "put",
        data,
        headers: { "content-type": "multipart/form-data" },
      }),
      invalidatesTags: [
        "Gestion-Documental-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    putDescargaArchivo: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/descargar-archivo`,
        method: "put",
        data,
        headers: { "content-type": "multipart/form-data" },
      }),
    }),
    putEliminarArchivo: builder.mutation({
      query: ({ data }) => ({
        url: `/admin-contratos/eliminar-archivo`,
        method: "put",
        data,
      }),
      invalidatesTags: [
        "Gestion-Documental-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
  }),
  overrideExisting: true,
});

export const {
  useGetEstructuraDocumentalQuery,
  usePostDescargaMasivaMutation,
  usePostDescargaSatCloudMutation,
  usePutCargarArchivoContratoMutation,
  usePutEliminarArchivoMutation,
  usePutDescargaArchivoMutation,
} = gestionDocumentalApi;
