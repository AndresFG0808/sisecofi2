import * as yup from "yup";

export function CreateValidationSchema(catalogos) {
  let notRequiredComite = ["cm", "contrato"];
  let notRequiredContratoConvenio = ["0"];
  let { comite, contratoConvenio, moneda } = catalogos.catalogos;
  let comiteOptions = mapOptionsSelect(comite);
  let monedaOptions = mapOptionsSelect(moneda);
  let contratoConvenioOptions = mapOptionsSelect(contratoConvenio);

  const requiredValidation = (
    value,
    schema,
    options,
    notRequired = notRequiredComite
  ) => {
    let option = options.find((s) => s.idValue == value[0]);

    if (option?.value && notRequired.includes(option.value.toLowerCase())) {
      return schema;
    }

    return schema.required("Dato requerido");
  };

  let validationSchema = yup.object({
    idContratoConvenio: yup.string().required("Dato requerido"),
    idContrato: yup
      .string()
      .nullable()
      .when("idContratoConvenio", (idContratoConvenio, schema) =>
        requiredValidation(
          idContratoConvenio,
          schema,
          contratoConvenioOptions,
          notRequiredContratoConvenio
        )
      ),
    fechaSesion: yup
      .date()
      .max(new Date(), "La fecha no puede ser posterior al día de hoy")
      .required("Dato requerido"),
    idComite: yup.string().required("Dato requerido"),
    acuerdo: yup
      .string()
      .nullable()

      .when("idComite", (comite, schema) =>
        requiredValidation(comite, schema, comiteOptions)
      )
      // .matches(
      //   /^[a-zA-Z0-9 ñÑ-]+$/,
      //   "El acuerdo solo puede contener caracteres alfanuméricos"
      // )
      .max(100, "Tamaño máximo 100 caracteres"),
    idSesionNumero: yup
      .string()
      .nullable()

      .when("idComite", (comite, schema) =>
        requiredValidation(comite, schema, comiteOptions)
      ),
    idSesionClasificacion: yup
      .string()
      .nullable()
      .when("idComite", (comite, schema) =>
        requiredValidation(comite, schema, comiteOptions)
      ),
    idPlantilla: yup
      .string()
      .nullable()
      .when("idComite", (comite, schema) =>
        requiredValidation(comite, schema, comiteOptions)
      ),
    vigencia: yup
      .string()
      .nullable()
      // .matches(
      //   /^[a-zA-Z0-9 ñÑ]+$/,
      //   "El acuerdo solo puede contener caracteres alfanuméricos y la letra ñ"
      // )
      .max(100, "Tamaño máximo 100 caracteres"),
    comentarios: yup.string().max(2000, "Tamaño máximo 2000 caracteres"),
    // montoAutorizado: yup
    //   .string()
    //   .nullable()
    //   .matches(
    //     /^-?(?:\d{1,3}(?:,\d{3})*|\d+)(?:\.\d{1,2})?$/,
    //     "Debe ser un número con dos decimales"
    //   ),
    // monto: yup
    //   .string()
    //   .nullable()
    //   .matches(
    //     /^-?(?:\d{1,3}(?:,\d{3})*|\d+)(?:\.\d{1,2})?$/,
    //     "Debe ser un número con dos decimales"
    //   ),
    // tipoCambio: yup.string().when("idTipoMoneda", (moneda, schema) => {
    //   let option = monedaOptions.find((s) => s.idValue == moneda[0]);
    //   if (option && option.value.toLowerCase() == "mxn") {
    //     return schema;
    //   }
    //   return schema.matches(
    //     /^-?(?:\d{1,3}(?:,\d{3})*|\d+)(?:\.\d{1,4})?$/,
    //     "Debe ser un número con cuatro decimales"
    //   );
    // }),
    // idsAfectacion: yup
    //   .array()
    //   .min(1, "Por favor, seleccione al menos un elemento")
    //   .required("Dato requerido"),
  });

  return validationSchema;
}
export const mapOptionsSelect = (
  itemList,
  idValueKey = "primaryKey",
  valueKey = "nombre"
) => {
  if (Array.isArray(itemList)) {
    var result = itemList.map((itemOption) => {
      return {
        idValue: itemOption[idValueKey],
        value: itemOption[valueKey],
      };
    });
    return result;
  }
  return [];
};
