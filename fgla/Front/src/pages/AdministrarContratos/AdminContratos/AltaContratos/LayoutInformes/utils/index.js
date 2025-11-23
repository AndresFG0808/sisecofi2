import * as yup from "yup";
const INITIAL_VALUES = {
  idContrato: null,
  idSeccionLayout: "",
  nombre: "",
  archivo: null,
};
const layoutSchema = yup.object({
  archivo: yup.mixed().required("Archivo requerido"),
  idSeccionLayout: yup.string().required("Dato requerido"),
});

const options = [
  { display: "Informes documentales única vez", key: "informesDocUni" },
  { display: "Informes documentales periódicos", key: "informesDocPer" },
  { display: "Informes documentales servicios", key: "informesDocSer" },
  { display: "Niveles de servicio(SLA)", key: "nivelesSla" },
  { display: "Atraso en el inicio de la prestación", key: "atrasoPrestacion" },
  { display: "Penas contractuales", key: "penasContractuales" },
];

export { INITIAL_VALUES, options, layoutSchema };
