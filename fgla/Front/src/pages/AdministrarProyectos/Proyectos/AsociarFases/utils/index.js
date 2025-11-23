import _ from "lodash";
import * as yup from "yup";
const maxDateAsociaciones = new Date();
maxDateAsociaciones.setHours(0, 0, 0, 0);
const asociacionSchema = yup.object().shape({
  fase: yup.string().required("Dato requerido").typeError("Dato requerido"),
  plantilla: yup
    .string()
    .min(1)
    .required("Dato requerido")
    .typeError("Dato requerido"),
});
const asociacionesRequiredValidations = async (data) => {
  let dataErrors = false;
  let resultados = await Promise.all(
    data.map(async (obj) => {
      try {
        await asociacionSchema.validate(obj, { abortEarly: false });
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
const isIdPlantillaRepeated = (array = []) => {
  const counts = _.countBy(array, "plantilla");
  const repeated = _.filter(
    _.keys(counts),
    (plantilla) => counts[plantilla] > 1
  );
  return repeated?.length > 0;
};

export {
  asociacionSchema,
  asociacionesRequiredValidations,
  isIdPlantillaRepeated,
};
