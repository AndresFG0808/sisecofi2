import { apiSAT } from "../../../../../../store/features";
export const facturasAsociadasApi = apiSAT.injectEndpoints({
  endpoints: (builder) => ({
    getObtenerFacturas: builder.query({
      query: (id) => ({
        url: `/admin-contratos/obtener-facturas-contrato/${id}`,
        method: "get",
      }),
    }),
    getReporteFacturas: builder.query({
      query: (id) => ({
        url: `/admin-contratos/exportar-facturas-contrato/${id}`,
      }),
    }),
  }),
  overrideExisting: false,
});

export const { useGetObtenerFacturasQuery, useLazyGetReporteFacturasQuery } =
  facturasAsociadasApi;
