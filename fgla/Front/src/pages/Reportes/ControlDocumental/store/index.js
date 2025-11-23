import { apiSAT } from "../../../../store/features";

export const controlDocumentalApi = apiSAT.injectEndpoints({
    endpoints: (builder) => ({
      getMatrizDocumental: builder.query({
       query: ({ identificador, idReferencia }) => ({
         url: `/reportes/controldoc/matriz-doc/${identificador}/${idReferencia}`,
         method: "GET",
      }),
      providesTags: ["Gestión"],
      }),
      postDescargaSatCloud: builder.mutation({
        query: ({ data }) => ({
          url: "/proyectos/descarga-sat-cloud",
          method: "post",
          data: data,
        }),
      }),
      postDescargaMasiva: builder.mutation({
        query: ({ data }) => ({
          url: "/proyectos/descarga-masiva",
          method: "post",
          data: data,
        }),
      }),
      putCargarArchivoFase: builder.mutation({
        query: ({ data }) => ({
          url: "/proyectos/cargar-archivo-fase",
          method: "put",
          data: data,
        }),
        invalidatesTags: ["Gestión"],
      }),
      putDescargarArchivo: builder.mutation({
        query: ({ data }) => ({
          url: "/proyectos/descargar-archivo",
          method: "put",
          data: data,
          headers: { "content-type": "multipart/form-data" },
        }),
      }),
      deleteArchivo: builder.mutation({
        query: ({ data }) => ({
          url: "/proyectos/eliminar-archivos-gestion",
          method: "post",
          data,
        }),
        invalidatesTags: ["Gestión"],
      }),
      saveArchivo: builder.mutation({
        query: ({ data }) => ({
          url: "/proyectos/cargar-archivo-fase/individual",
          method: "put",
          headers: { "content-type": "multipart/form-data" },
          data,
        }),
        invalidatesTags: ["Gestión"],
      }),
    }),
    overrideExisting: false,
  });
  export const {
    useGetMatrizDocumentalQuery,
    useLazyGetMatrizDocumentalQuery,
    usePostDescargaSatCloudMutation,
    usePostDescargaMasivaMutation,
    usePutCargarArchivoFaseMutation,
    usePutDescargarArchivoMutation,
    useDeleteArchivoMutation,
    useSaveArchivoMutation,
  } = controlDocumentalApi;