import * as yup from "yup";
import { nanoid } from "nanoid";
import _ from "lodash";
import { FormatMoney, formatCurrency } from "../../../../../functions/utils";

const isWeekday = (date) => {
  const day = date.getDay();
  return day !== 0 && day !== 6;
};

const lideresSchema = yup
  .array()
  .of(
    yup.object().shape({
      idReferencia: yup
        .number()
        .typeError("Seleccionar líder")
        .required("Seleccionar líder"),
      nivel: yup
        .number()
        .typeError("Seleccionar nivel")
        .required("Seleccionar nivel"),

      estatus: yup.bool().required(),

      fechaInicio: yup
        .string()
        .required("Fecha inicio de líder necesaria")
        .test(
          "not-weekend",
          "La fecha de inicio del líder no puede ser en fin de semana",
          (value) => {
            const date = new Date(value);
            return isWeekday(date);
          }
        ),
      fechaFin: yup
        .string()
        .nullable(null)
        .defined()
        .notRequired()
        .when("estatus", {
          is: false,
          then: (schema) =>
            schema
              .required("Seleccionar fecha fin del líder")
              .test(
                "not-weekend",
                "La fecha de fin del líder no puede ser en fin de semana",
                (value) => {
                  if (value) {
                    const date = new Date(value);
                    return isWeekday(date);
                  }
                  return true;
                }
              ),
          otherwise: (schema) => schema.nullable(null).defined().notRequired(),
        }),
    })
  )
  .test("unique-estatus", "Solo puede haber un líder activo", function (value = []) {
    const estatusCount = value.filter((item) => item?.estatus === true).length;
    return estatusCount <= 1;
  });

function safeParseFloat(value, defaultValue = 0) {
  value = "" + value;
  value = value.replaceAll(",", "");
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}

// === ALINEACIÓN (igual) ===
const alineacionSchema = yup.object().shape({
  mapa: yup.string().required("Se requiere seleccionar un mapa"),
  periodo: yup.string().required("Se requiere seleccionar un periodo"),
  objetivo: yup.string().required("Se requiere seleccionar un objetivo"),
});

// === FICHA (igual) ===
const fichaSchema = yup.object({
  idAdmonPatrocinadora: yup.string().required("Dato requerido"),
  idFinanciamiento: yup.string().required("Dato requerido"),
  idTipoProcedimiento: yup.string().required("Dato requerido"),
  idAdmonCentrales: yup.array().of(yup.string().required("Dato requerido")),
  idClasificacionProyecto: yup.string().required("Dato requerido"),
  idAreaPlaneacion: yup.string().required("Dato requerido"),
  idTipoMoneda: yup.string().required("Dato requerido"),
  fechaInicio: yup.date().required("Dato requerido"),
  fechaTermino: yup
    .date()
    .required("Dato requerido")
    .test(
      "is-greater",
      "Fecha de fin debe ser mayor o igual a la fecha de inicio",
      function (value) {
        const { fechaInicio } = this.parent;
        if (!fechaInicio || !value) return true;
        return value >= fechaInicio;
      }
    ),
});

const VALORES_INICIALES = {
  //**********FICHA************ */
  idAdmonPatrocinadora: "",
  idAdmonParticipante: "",
  idFinanciamiento: "",
  idTipoProcedimiento: "",
  idAdmonCentrales: [""],
  idClasificacionProyecto: "",
  idAreaPlaneacion: "",
  idTipoMoneda: "",
  objetivoGeneral: "",
  alcance: "",
  montoSolicitado: "",
  fechaInicio: null,
  fechaTermino: null,
  //**********FICHA************ */
  idAdmonCentralesPost: [],
  lideres: [],
  lideresEliminados: [],
  alineacionesEliminadas: [],
  alineaciones: [],
};

const handleReadOnlyValues = (
  admonCentralId,
  name,
  data,
  setState,
  patrocinadora,
  patrocinador
) => {
  const admn = data?.find((admon) => admon.primaryKey == admonCentralId);
  setState((prev) => ({
    ...prev,
    [name]: {
      [`${patrocinadora}`]: admn?.administracion,
      [`${patrocinador}`]:
        admn?.administradores?.find((admon) => admon?.estatus === true)
          ?.administrador || "",
    },
  }));
};

const arrangeFicha = (fichaTecnica) => {
  return {
    ...fichaTecnica,
    montoSolicitado: !!fichaTecnica.montoSolicitado
      ? FormatMoney(safeParseFloat(fichaTecnica.montoSolicitado), 2)
      : "",
    idAdmonCentralesPost: [],
    lideres: [],
    lideresEliminados: [],
    alineacionesEliminadas: [],
    alineaciones: [],
  };
};

// === LÍDERES: rearrange (ya incluye idReferencia/nivel) ===
const rearrangeLideres = (data) => {
  if (!data) return [];

  return data.map((user) => ({
    UUID: nanoid(),
    nombre: user.nombre,
    puesto: user.puesto,
    correo: user.correo,
    nombreDisplay: user.nombre,
    fechaInicio: user.fechaInicio,
    fechaFin: user.fechaFin,
    estatus: user.estatus,
    isNewRow: false,
    idHistorico: user.idHistorico,

    idUsuario: user.idUsuario ?? null,
    idUsuarioStr: user.idUsuarioStr ?? "",
    idReferencia: user.idReferencia ?? user.idUsuario ?? null,
    nivel: user.nivel ?? null,
  }));
};

const rearrangeAlineacion = (data) => {
  if (!data) return [];
  return data.map((alineacion) => {
    if (!!alineacion.UUID) {
      return {
        ...alineacion,
        ...(!!alineacion?.errors ? { errors: alineacion.errors } : {}),
      };
    } else {
      return {
        idAlineacion: alineacion.id,
        UUID: nanoid(),
        mapa: alineacion.mapa.primaryKey,
        periodo: alineacion.periodo.primaryKey,
        objetivo: alineacion.objetivo.primaryKey,
        mapaDisplay: alineacion.mapa.nombre,
        periodoDisplay: alineacion.periodo.nombre,
        objetivoDisplay: alineacion.objetivo.objetivo,
        edit: true,
        isNewRow: alineacion.isNewRow ? alineacion.isNewRow : false,
        ...(!!alineacion?.errors ? { errors: alineacion.errors } : {}),
      };
    }
  });
};

const splitData = (dataTable = [], memoizedData = new Map()) => {
  const modifiedData = dataTable.reduce((prevRows, currentRow) => {
    const memoizedRow = memoizedData.get(currentRow.UUID);
    if (!currentRow.isNewRow && !_.isEqual(currentRow, memoizedRow)) {
      prevRows.push(currentRow);
    }
    return prevRows;
  }, []);
  const newData = dataTable.filter((row) => row.isNewRow);
  return { modifiedData, newData };
};

const requiredValidation = async (data, schema) => {
  let dataErrors = false;
  let resultados = await Promise.all(
    data.map(async (obj) => {
      try {
        await schema.validate(obj, { abortEarly: false });
        return obj;
      } catch (error) {
        dataErrors = true;
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

const checkMemoizedDataAndAddData = (map = new Map(), data = []) => {
  if (map.size <= 0) {
    return data;
  }
};

// ⚠️ Actualiza mensajes para coincidir con el nuevo schema
const liderTableErrors = [
  "Fecha inicio de líder necesaria",
  "La fecha de inicio del líder no puede ser en fin de semana",
  "La fecha de fin del líder no puede ser en fin de semana",
  "Seleccionar fecha fin del líder",
  "Seleccionar líder",
  "Seleccionar nivel",
];

export {
  handleReadOnlyValues,
  arrangeFicha,
  fichaSchema,
  VALORES_INICIALES,
  lideresSchema,
  alineacionSchema,
  rearrangeLideres,
  rearrangeAlineacion,
  splitData,
  requiredValidation,
  liderTableErrors,
};
