import moment from "moment";
import { nanoid } from "nanoid";
import * as yup from "yup";

const alphanumericRegex = /^[a-zA-Z0-9]*$/;
const INITIAL_VALUES = {
  numeroContrato: "",
  numeroContratoCompraNet: "",
  acuerdo: "",
  proveedores: [{ id: "", rfc: "", disabled: false }],
  tipoProcedimiento: "",
  numeroProcedimiento: "",
  convenioColaboracion: "",
  dominioTecnologico: "",
  fondeoContrato: "",
  tituloServicio: "",
  objetivoServicio: "",
  alcanceServicio: "",
  admonContrato: [],
  proveedoresEliminados: [],
};
const dateFormat = "YYYY-MM-DD";

const datosGeneralesSchema = yup.object({
  numeroContrato: yup
    .string()
    .required("Dato requerido")
    .max(25, "Se excedió el número de caracteres."),
  tipoProcedimiento: yup.string().required("Dato requerido"),
  numeroProcedimiento: yup
    .string()
    .required("Dato requerido")
    .max(50, "Se excedió el número de caracteres."),
  fondeoContrato: yup.string().required("Dato requerido"),
  tituloServicio: yup.string().required("Dato requerido"),
  numeroContratoCompraNet: yup
    .string()
    .max(40, "Se excedió el número de caracteres."),
  objetivoServicio: yup
    .string()
    .required("Dato requerido")
    .max(1000, "Se excedió el número de caracteres."),
  alcanceServicio: yup
    .string()
    .required("Dato requerido")
    .max(1000, "Se excedió el número de caracteres."),
  proveedores: yup
    .array()
    .of(
      yup.object({
        id: yup.string().required("Dato requerido"),
      })
    )
    .test(
      "unique-numbers",
      "Los proveedores no pueden repetirse",
      (proveedores) => {
        const seen = new Set();
        return proveedores.every(({ id }) => {
          if (seen.has(id)) {
            return false; // If the number already exists, return false
          }
          seen.add(id);
          return true; // Otherwise, add it to the set and continue
        });
      }
    ),
});

const template = () => ({
  UUID: nanoid(),
  isNewRow: true,
  id: "",
  responsabilidad: "",
  administracionGeneral: "",
  administraciónCentral: "",
  nombreServidorPublico: "",
  telefono: "",
  correo: "",
  fechaInicio: "",
  fechaTermino: "",
  vigente: false,
});
const rearrangeResponse = (data) => {
  return {
    numeroContratoCompraNet: data?.numeroContratoCompraNet,
    numeroContrato: data?.numeroContrato,
    acuerdo: data?.acuerdo,
    proveedores: data?.proveedores?.map((proveedor) => ({
      id: proveedor?.idProveedor,
      rfc: proveedor?.rfc,
      disabled: true,
    })),
    tipoProcedimiento: data?.catTipoProcedimiento?.primaryKey,
    numeroProcedimiento: data?.numeroProcedimiento,
    convenioColaboracion: data?.idCatConvenioColaboracion,
    dominioTecnologico: data?.catDominiosTecnologicos?.primaryKey,
    fondeoContrato: data?.catFondeoContrato?.primaryKey,
    tituloServicio: data?.titulosServicio,
    objetivoServicio: data?.objetivoServicio,
    alcanceServicio: data?.alcanceServicio,
    admonContrato: [],
    proveedoresEliminados: [],
  };
};
const rearrangeParticipantes = (data) => {
  if (!data) return [];
  return data.map((participante) => ({
    UUID: nanoid(),
    isNewRow: false,
    responsabilidad: participante?.responsabolidad?.primaryKey,
    responsabilidadDisplay: participante?.responsabolidad?.nombre,
    idParticipantesAdministracionContrato:
      participante?.idParticipantesAdministracionContrato,
    administracionGeneral: participante?.administracionGeneral?.primaryKey,
    administracionGeneralDisplay: participante?.administracionGeneral?.acronimo,
    nombreServidorPublico: participante?.usuarioInformacion?.idUsuario,
    nombreServidorPublicoDisplay: participante?.usuarioInformacion?.nombre,
    telefono: participante?.usuarioInformacion?.telefono,
    correo: participante?.usuarioInformacion?.correo,
    nivel: participante?.usuarioInformacion?.nivel,
    fechaInicio: moment(new Date(participante?.fechaInicio)).format(dateFormat),
    fechaTermino: participante?.fechaTermino
      ? moment(new Date(participante?.fechaTermino)).format(dateFormat)
      : "",
    vigente: participante?.vigente,
    administracionCentral: participante?.administracionCentral?.primaryKey,
    administracionCentralDisplay: participante?.administracionCentral?.acronimo,
  }));
};
const curatedNewParticipantes = (data, idContrato) => {
  if (data.length <= 0) return [];
  return data.map((participante) => ({
    idResponsabilidad: parseInt(participante.responsabilidad),
    idContrato: parseInt(idContrato),
    idAdmonCentral: parseInt(participante.administracionCentral),
    idAdmonGeneral: parseInt(participante.administracionGeneral),
    idReferencia: parseInt(participante.nombreServidorPublico),
    nivel: participante.nivel ? parseInt(participante.nivel) : null,
    idNombreServicodPublico: parseInt(participante.nombreServidorPublico),
    fechaInicio: moment(new Date(participante.fechaInicio)).toISOString(),
    ...(participante.fechaTermino
      ? {
          fechaTermino: moment(
            new Date(participante.fechaTermino)
          ).toISOString(),
        }
      : {}),
    vigente: participante.vigente,
  }));
};

const curatedModifiedParticipantes = (data, idContrato) => {
  if (data.length <= 0) return [];
  return data.map((participante) => ({
    idParticipantesAdministracionContrato: parseInt(
      participante.idParticipantesAdministracionContrato
    ),
    idResponsabilidad: parseInt(participante.responsabilidad),
    idContrato: parseInt(idContrato),
    idAdmonCentral: parseInt(participante.administracionCentral),
    idAdmonGeneral: parseInt(participante.administracionGeneral),
    idReferencia: parseInt(participante.nombreServidorPublico),
    nivel: participante.nivel ? parseInt(participante.nivel) : null,
    idNombreServicodPublico: parseInt(participante.nombreServidorPublico),
    fechaInicio: moment(new Date(participante.fechaInicio)).toISOString(),
    ...(participante.fechaTermino
      ? {
          fechaTermino: moment(
            new Date(participante.fechaTermino)
          ).toISOString(),
        }
      : {}),
    vigente: participante.vigente,
  }));
};
const curateDeletedParticipantes = (data) => {
  return data.map((participante) =>
    parseInt(participante.idParticipantesAdministracionContrato)
  );
};
const participantesSchema = yup.object().shape({
  responsabilidad: yup
    .string()
    .required("Dato requerido")
    .typeError("Dato requerido"),
  administracionGeneral: yup
    .string()
    .required("Dato requerido")
    .typeError("Dato requerido"),
  administracionCentral: yup
    .string()
    .when(['responsabilidadDisplay', 'administracionGeneralDisplay'], {
      is: (responsabilidad, adminGeneral) => {
        return !(responsabilidad === "Administrador General" && adminGeneral === "AGCTI");
      },
      then: (schema) => schema.required("Dato requerido").typeError("Dato requerido"),
      otherwise: (schema) => schema.notRequired()
    })
    .typeError("Dato requerido"),
  nombreServidorPublico: yup
    .string()
    .required("Dato requerido")
    .typeError("Dato requerido"),
  fechaInicio: yup
    .string()
    .required("Dato requerido")
    .typeError("Dato requerido"),
  fechaTermino: yup
    .string()
    .nullable(null)
    .defined()
    .notRequired()
    .when("vigente", {
      is: false,
      then: (schema) =>
        schema
          .required("Seleccionar fecha fin del participante")
          .test(
            "is-greater",
            "Fecha de término debe ser mayor o igual a la fecha de inicio",
            function (value) {
              const { fechaInicio } = this.parent;
              if (!fechaInicio || !value) return true;
              return value >= fechaInicio;
            }
          ),
      otherwise: (schema) => schema.nullable(null).defined().notRequired(),
    }),
  vigente: yup.bool(),
});

const participantesValidation = async (data) => {
  let dataErrors = false;
  let resultados = await Promise.all(
    data.map(async (obj, idx) => {
      try {
        await participantesSchema.validate(obj, { abortEarly: false });
        return obj;
      } catch (error) {
        dataErrors = true;

        error.inner.forEach((err) => {
          console.warn(`- Campo: ${err.path}, Valor:`, obj[err.path], "->", err.message);
        });

        return {
          ...obj,
          errors: error.inner.reduce((prev, currentError) => {
            prev[currentError.path] = currentError.message;
            return prev;
          }, {}),
        };
      }
    })
  );
  return { dataErrors, resultados };
};


export {
  INITIAL_VALUES,
  datosGeneralesSchema,
  alphanumericRegex,
  template,
  curatedNewParticipantes,
  curateDeletedParticipantes,
  rearrangeResponse,
  rearrangeParticipantes,
  curatedModifiedParticipantes,
  participantesValidation,
};
