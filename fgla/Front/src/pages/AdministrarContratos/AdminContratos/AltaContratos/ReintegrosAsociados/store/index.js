import { apiSAT } from "../../../../../../store/features";

export const reintegrosAsociadosApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getReintegrosByContrato: builder.query({
      query: (idContrato) => ({
        url: `/admin-contratos/reintegros/${idContrato}`,
        method: "get",
      }),
    }),
    getReporteReintegros: builder.query({
      query: (id) => ({
        url: `/admin-contratos/reintegros/generar-reporte-reintegros?idContrato=${id}`,
        method: "get",
      }),
    }),
  }),
  overrideExisting: false,
});

export const {
  useGetReintegrosByContratoQuery,
  useLazyGetReporteReintegrosQuery,
} = reintegrosAsociadosApi;
