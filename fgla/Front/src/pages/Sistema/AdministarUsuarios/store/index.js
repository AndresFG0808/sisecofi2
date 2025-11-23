import { apiSAT } from "../../../../store/features";

export const adminUsuarios = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postBuscarUsuarios: builder.mutation({
      query: (data) => ({
        url: "/administracion/usuarios/buscar-usuarios",
        method: "post",
        data,
      }),
    }),
    putBuscarUsuariosDirectorios: builder.mutation({
      query: (data) => ({
        url: "/administracion/usuarios/buscar-usuario-directorio",
        method: "post",
        data,
      }),
    }),
    putGuardarUsuarios: builder.mutation({
      query: (data) => ({
        url: "/administracion/usuarios/guardar-usuarios",
        method: "put",
        data,
      }),
    }),
    putGuardarUsuariosDA: builder.mutation({
      query: (data) => ({
        url: "/administracion/usuarios/guardar-usuarios",
        method: "put",
        data,
      }),
    }),
    postDescargarUsuariosReporte: builder.mutation({
      query:(data)=>({
        url:"/administracion/usuarios/buscar-usuarios-reporte",
        method:"post",
        data,
        responseHandler: (response) => response.blob() 
      })
    })
  }),
});

export const {
  usePostBuscarUsuariosMutation,
  usePutBuscarUsuariosDirectoriosMutation,
  usePutGuardarUsuariosMutation,
  usePutGuardarUsuariosDAMutation,
  usePostDescargarUsuariosReporteMutation
} = adminUsuarios;
