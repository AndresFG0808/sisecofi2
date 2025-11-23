import { nanoid } from "nanoid";
import * as yup from "yup";

export const INITIAL_VALUES = {
  plantillas: [{ plantilla: "", idConvenioPlantilla: null, UUID: nanoid() }],
};

export const rearrange = (data) => {
  return {
    plantillas: data.map((el) => ({
      plantilla: el.idPlantillaVigente,
      idConvenioPlantilla: el.idConvenioPlantilla,
      UUID: nanoid(),
    })),
  };
};

export const plantillaSchema = yup.object({
  plantillas: yup
    .array()
    .of(
      yup.object({
        plantilla: yup.string().required("Dato requerido"),
      })
    )
    .test(
      "unique-numbers",
      "Las plantillas no pueden repetirse",
      (plantillas) => {
        const seen = new Set();
        return plantillas.every(({ plantilla }) => {
          if (seen.has(plantilla)) {
            return false; // If the number already exists, return false
          }
          seen.add(plantilla);
          return true; // Otherwise, add it to the set and continue
        });
      }
    ),
});
