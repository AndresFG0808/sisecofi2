import { apiSAT } from "../../../../../../store/features";
export const dictamenesAsociadosApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getDictamenAsociado: builder.query({
      query: (id) => ({
        url: `/admin-contratos/dictamenes-asociados/${id}`,
        method: "get",
      }),
    }),
    getReporteDictamenes: builder.query({
      query: (id) => ({
        url: `/admin-contratos/exportar-dictamenes-asociados/${id}`,
        method: "get",
      }),
    }),
  }),
  overrideExisting: false,
});

export const { useGetDictamenAsociadoQuery, useLazyGetReporteDictamenesQuery } =
  dictamenesAsociadosApi;
