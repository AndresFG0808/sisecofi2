import { apiSAT } from "../../../../../../store/features";

export const layoutInformesApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    postCargarLayout: builder.mutation({
      query: ({ data }) => ({
        url: "/admin-contratos/carga-layout-informes",
        method: "post",
        data,
        headers: { "content-type": "multipart/form-data" },
      }),
      invalidatesTags: [
        "Atraso-Prestacion-Contrato",
        "Informes-Periodicos-Contrato",
        "Informes-Unica-vez-Contrato",
        "Informes-Servicios-Contrato",
        "Penas-Contractuales-Contrato",
        "Niveles-Servicio-Contrato",
        "Ultima-Modificacion-Contrato",
      ],
    }),
  }),
  overrideExisting: false,
});

export const { usePostCargarLayoutMutation } = layoutInformesApi;
