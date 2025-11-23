import moment from "moment";
import { FormatMoney } from "../../../../../../functions/utils";

export const rearrangeData = (data) => {
  if (!data?.content) return [];
  return data?.content?.map((convenio) => ({
    idConvenioModificatorio: convenio.idConvenioModificatorio,
    numeroConvenio: convenio?.numeroConvenio,
    tipo: convenio?.tipoConvenioFormateado,
    ...(convenio.fechaFirma
      ? {
          fechaFirma: moment(new Date(convenio.fechaFirma)).format(
            "DD/MM/YYYY"
          ),
        }
      : {}),
    ...(convenio.fechaFin
      ? { fechaFin: moment(new Date(convenio.fechaFin)).format("DD/MM/YYYY") }
      : {}),
    montoMaximo: `${
      convenio?.montoMaximoSinImpuestos
        ? "$" + FormatMoney(convenio?.montoMaximoSinImpuestos, 2)
        : ""
    }`,
  }));
};
