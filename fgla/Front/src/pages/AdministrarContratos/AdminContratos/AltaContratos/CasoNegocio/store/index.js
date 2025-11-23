import { apiSAT } from "../../../../../../store/features";

export const proyeccionCasoNegocio = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postProcesarProyeccionCN: builder.mutation({
      query: ({ idContrato, data }) => ({
        url: `/admin-contratos/procesar-proyeccion/${idContrato}`,
        method: "post",
        data,
      }),
      invalidatesTags: ["Ultima-Modificacion-Contrato"],
    }),
  }),
  overrideExisting: true,
});

export const { usePostProcesarProyeccionCNMutation } = proyeccionCasoNegocio;
