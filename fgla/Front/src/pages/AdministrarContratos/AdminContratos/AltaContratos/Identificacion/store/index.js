import { apiSAT } from "../../../../../../store/features";

export const identificacionApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getContrato: builder.query({
      query: (id) => ({
        url: `/admin-contratos/contrato/${id}`,
        method: "get",
      }),
      providesTags: ["Identificacion-Contrato"],
    }),
    postGuardarContrato: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/inicial-contrato",
        method: "post",
        data,
      }),
      invalidatesTags: [
        "Identificacion-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    putEditarContrato: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/editar-contrato",
        method: "put",
        data,
      }),
      invalidatesTags: [
        "Identificacion-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    getProyectos: builder.query({
      query: () => ({
        url: "/admin-contratos/proyectos",
        method: "get",
      }),
    }),
    getProyectosCompletos: builder.query({
      query: () => ({
        url: "/admin-contratos/proyectos-completos",
        method: "get",
      }),
      providesTags: ["Identificacion-Contrato"],
    }),
    putEjecutarContrato: builder.mutation({
      query: (id) => ({
        url: `/admin-contratos/ejecutar-contrato/${id}`,
        method: "put",
      }),
      invalidatesTags: [
        "Identificacion-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    putIniciarContrato: builder.mutation({
      query: (id) => ({
        url: `/admin-contratos/regresar-contrato-inicial/${id}`,
        method: "put",
      }),
      invalidatesTags: [
        "Identificacion-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    deleteContrato: builder.mutation({
      query: (id) => ({
        url: `/admin-contratos/cancelar-contrato/${id}`,
        method: "put",
      }),
      invalidatesTags: [
        "Identificacion-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    getCancelarContrato: builder.query({
      query: (id) => ({
        url: `/admin-contratos/cancelar-contrato/${id}`,
        method: "get",
      }),
    }),
    postGetContrato: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/buscar-contrato",
        method: "post",
        data,
      }),
    }),
    getUltimaModificacionContrato: builder.query({
      query: (idContrato) => ({
        url: `/admin-contratos/contrato-ultima-mod/${idContrato}`,
        method: "get",
      }),
      providesTags: ["Ultima-Modificacion-Contrato"],
    }),
  }),
  overrideExisting: false,
});

export const {
  usePutEditarContratoMutation,
  useDeleteContratoMutation,
  useLazyGetCancelarContratoQuery,
  usePutEjecutarContratoMutation,
  usePutIniciarContratoMutation,
  useLazyGetContratoQuery,
  useGetContratoQuery,
  useGetProyectosCompletosQuery,
  usePostGuardarContratoMutation,
  useGetProyectosQuery,
  useLazyGetProyectosQuery,
  usePostGetContratoMutation,
  useGetUltimaModificacionContratoQuery,
} = identificacionApi;
