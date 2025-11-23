import * as yup from "yup";

export const schema = yup.object().shape({
nombreTituloServicio: yup.string().required("Dato requerido"),
  anio: yup
    .string()
    .required("Dato requerido")
    .matches(/^\d{4}$/, "Dato requerido"),

    responsable: yup
    .string()
    .required("Dato requerido")
    .test("no-blank", "Dato requerido", (value) => value.trim() !== ""),
  resultado: yup.string().required("Dato requerido"),
  observacion: yup
  .string()
    .required("Dato requerido")
    .test("no-blank", "Dato requerido", (value) => value.trim() !== ""),
});

export const tableValidations = async (data, schema) => {
    console.log(data);
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




export const schemaContacto = yup.object().shape({
  nombreContacto: yup
    .string()
    .required("Dato requerido")
    .test("no-blank", "Dato requerido", (value) => value.trim() !== ""),
  // telefonoOficina: yup
  //   .string()
  //   .required("Dato requerido")
  //   .test("no-blank", "Dato requerido", (value) => value.trim() !== ""),
  correoElectronico: yup
    .string()
    .nullable()
    .notRequired()
    .test(
      "is-valid-email",
      "Formato de correo electrónico inválido",
      (value) => {
        if (value === null || value.trim() === "") {
          return true;
        }
        return /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
      }
    ),
});

export const tableValidationsContacto = async (data, schema) => {
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


export const schemaTitulo = yup.object().shape({
  vencimientoTitulo: yup
  .string()
  .required("Dato requerido")
  .test("no-undefined", "Dato requerido'", (value) => value !== "undefined/undefined/"),
numeroTitulo: yup
  .string()
  .required("Dato requerido")
  .matches(/^[a-zA-Z0-9/-]{0,10}$/, "Sólo se permiten los caracteres / y - y un máximo de 10 caracteres"),
nombreTituloServicio: yup
  .string()
  .required("Dato requerido")
  .test("no-blank", "Dato requerido", (value) => value.trim() !== ""),
idEstatusTituloServicio: yup.string().required("Dato requerido"),
    });
    
    export const tableValidationsTitulos = async (data, schema) => {
        console.log(data);
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

export const catalogFilterResultadoByText = (catalog, row, columnId, filterValue) => {
  const rowValue = row.original[columnId];
  const rowObject = catalog.find(item => item.idResultadoDictamenTecnico === rowValue);
  if (rowObject) {
    const firstWord = rowObject.resultado.trim().toLowerCase().split(' ')[0];
    const filterFirstWord = filterValue.trim().toLowerCase().split(' ')[0];
    return firstWord.includes(filterFirstWord);
  }
  return false;
};

export const catalogFilterByText = (catalog, row, columnId, filterValue) => {
  const rowValue = row.original[columnId];
  const rowObject = catalog.find(item => item.idServicioTitulo === rowValue);
  return rowObject ? rowObject.nombreTituloServicio.trim().toLowerCase().includes(filterValue.trim().toLowerCase()) : false;
};