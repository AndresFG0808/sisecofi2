import { nanoid } from "nanoid";

export const rearrange = (data) => {
  if (!data) return [];
  return data?.servicios
    ?.map((values) => ({
      ...values,
      UUID: nanoid(),
    }))
    .sort(
      (a, b) =>
        a.servicioBase.idServicioContrato - b.servicioBase.idServicioContrato
    );
};
