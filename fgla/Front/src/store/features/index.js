import { createApi } from "@reduxjs/toolkit/query/react";
import client from "axios";
const baseUrl = process.env.REACT_APP_BASE_URL;
const mainHeaders = {
  /*   'Access-Control-Allow-Origin': '*', */
  "Content-Type": "application/json",
  Authorization: "Bearer " + sessionStorage.getItem("access_token"),
};
export const axiosBaseQuery =
  ({ baseUrl, mainHeaders } = { baseUrl: "" }) =>
  async ({ url, method, data, params, headers }) => {
    try {
      const result = await client({
        url: baseUrl + url,
        method,
        data,
        params,
        headers: { ...mainHeaders, ...headers },
      });
      return { data: result.data };
    } catch (error) {
      const err = error;
      return {
        error: {
          status: err.response?.status,
          data: err.response?.data || err.message,
        },
      };
    }
  };

export const apiSAT = createApi({
  reducerPath: "apiSAT",
  baseQuery: axiosBaseQuery({ baseUrl, mainHeaders }),
  tagTypes: [
    "Ficha",
    "Gestión",
    "Asociación",
    "Identificacion-Contrato",
    "Datos-Generales-Contrato",
    "Vigencia-Contrato",
    "Grupos-Contrato",
    "Registro-Contrato",
    "Atraso-Prestacion-Contrato",
    "Informes-Unica-vez-Contrato",
    "Informes-Periodicos-Contrato",
    "Informes-Servicios-Contrato",
    "Penas-Contractuales-Contrato",
    "Niveles-Servicio-Contrato",
    "Asignacion-Plantillas-Contrato",
    "Gestion-Documental-Contrato",
    "Participantes-Contrato",
    "Asignacion-Plantillas-Convenio",
    "Gestion-Documental-Convenio",
    "Gestion-Documental-Dictamen",
    "Gestion-Documental-Reintegros",
    "Registro-Servicios-Convenio",
    "Proyeccion-Convenio",
    "Reintegros",
    "Convenio-Modificatorio",
    "Ultima-Modificacion-Contrato",
    "Ultima-Modificacion-Convenio",
    "Ultima-Modificacion-Proyecto",
    "Dependencias",
  ],
  endpoints: (builder) => ({}),
});
