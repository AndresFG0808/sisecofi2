import { apiSAT } from "../../../../../../store/features";

export const nivelesServicioApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getNivelesServicios: builder.query({
      query: (id) => ({
        url: `/admin-contratos/consultar-niveles-servicio-sla/${id}`,
        method: "get",
      }),
      providesTags: ["Niveles-Servicio-Contrato"],
    }),
    getReporteNivelesServicio: builder.query({
      query: (id) => ({
        url: `/admin-contratos/reporte-niveles-servicio-sla/${id}`,
        method: "get",
      }),
    }),
    postCrearNivelesServicios: builder.mutation({
      query: (data) => ({
        url: `/admin-contratos/guardar-niveles-servicio-sla`,
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    putActualizarNivelesServicios: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/actualizar-niveles-servicio-sla",
        method: "put",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
    postEliminarNivelesServicio: builder.mutation({
      query: (data) => ({
        url: "/admin-contratos/eliminar-niveles-servicio-sla",
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
  }),
  overrideExisting: false,
});

console.log(nivelesServicioApi);

export const {
  useGetNivelesServiciosQuery,
  useLazyGetReporteNivelesServicioQuery,
  usePostCrearNivelesServiciosMutation,
  usePutActualizarNivelesServiciosMutation,
  usePostEliminarNivelesServicioMutation,
} = nivelesServicioApi;
