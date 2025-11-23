import { apiSAT } from "../../../../../../store/features";

export const cierreContratoApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postCierreContrato: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/cierre-contrato",
        method: "post",
        data,
      }),
      invalidatesTags: [
        "Identificacion-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
    putCierreContrato: builder.mutation({
      query: (id) => ({
        url: `/admin-contratos/cierre-contrato/${id}`,
        method: "put",
      }),
      invalidatesTags: [
        "Identificacion-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
  }),
  overrideExisting: false,
});

export const { usePostCierreContratoMutation, usePutCierreContratoMutation } =
  cierreContratoApi;
