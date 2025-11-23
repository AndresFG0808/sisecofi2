import { apiSAT } from "../../../../store/features";

const getFormData = (data) => {
  const formData = new FormData();
  Object.keys(data).forEach((key) => {
    formData.append(key, data[key]);
  });
  return formData;
};

export const tableroControlApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    putDescargarArchivoTC: builder.mutation({
      query: (data) => ({
        url: "/proyectos/descargar-archivo-tablero",
        method: "put",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    putDescargarFolderTC: builder.mutation({
      query: (data) => ({
        url: "/proyectos/descargar-folder-tablero",
        method: "put",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
    putFolderSatCloutTC: builder.mutation({
      query: (data) => ({
        url: "/proyectos/descargar-folder-sat-cloud-tablero",
        method: "put",
        data: getFormData(data),
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }),
    }),
  }),
});

export const {
  usePutDescargarArchivoTCMutation,
  usePutDescargarFolderTCMutation,
  usePutFolderSatCloutTCMutation,
} = tableroControlApi;
