import * as Yup from 'yup';

const REQUIRED = 'Campo requerido';
const NUMBER = 'Introduce un número';
const STRING = 'Introduce un texto';
const NUMBER_POSITIVE = 'Introduce un número positivo';
const MIN_MSG = 'Debe ser mayor o igual a:';
const MAX_MSG = 'Debe ser menor o igual a:';
const CADENA = 'No se permiten caracteres especiales';
const RFC_INVALIDO = 'RFC no válido';
const DATE_START_MSG =
  'La Fecha inicial debe ser menor o igual que la Fecha final y la Fecha final debe ser menor o igual que la fecha actual, favor de seleccionar un dato válido.';

const EXP_RFC =
  '[A-Za-z,ñ,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Za-z,0-9]?[A-Za-z,0-9]?[0-9,A-Za-z]?';

const RULES = {
  STRING: Yup.string().typeError(STRING).nullable(),
  STRING_REQUIRED: Yup.string().typeError(REQUIRED).required(REQUIRED),

  STRING_FORMAT_REQUIRED: (regularExpression) =>
    Yup.string().required(REQUIRED).matches(regularExpression, CADENA),

  STRING_CONDITIONAL_REQUIRED: (fieldName, fieldValue) =>
    Yup.mixed().when(fieldName, {
      is: fieldValue,
      then: Yup.string().typeError(STRING).required(REQUIRED),
      otherwise: Yup.string().nullable(),
    }),

  NUMBER_REQUIRED: Yup.number().typeError(NUMBER).required(REQUIRED),
  NUMBER: Yup.number().typeError(NUMBER).nullable(),
  NUMBER_POSITIVE: Yup.number().typeError(NUMBER).positive(NUMBER_POSITIVE).nullable(),
  NUMBER_POSITIVE_REQUIRED: Yup.number()
    .typeError(NUMBER)
    .positive(NUMBER_POSITIVE)
    .required(REQUIRED),

  NUMBER_MIN_REQUIRED: (min) =>
    Yup.number()
      .typeError(NUMBER)
      .positive(NUMBER_POSITIVE)
      .min(min, `${MIN_MSG} ${min}`)
      .required(REQUIRED),

  NUMBER_MAX_REQUIRED: (max) =>
    Yup.number()
      .typeError(NUMBER)
      .positive(NUMBER_POSITIVE)
      .max(max, `${MAX_MSG} ${max}`)
      .required(REQUIRED),

  CONDITIONAL_OPTIONAL: (fieldName, fieldValue, conditionalIfTrue) =>
    Yup.mixed().when(fieldName, {
      is: fieldValue,
      then: conditionalIfTrue,
      otherwise: Yup.string(),
    }),

  DATE: Yup.date().nullable(),

  START_DATE: (fieldName) =>
    Yup.date()
      .required(REQUIRED)
      .test('minDate', DATE_START_MSG, function (value) {
        let end_date = this.resolve(Yup.ref(fieldName));
        if (end_date && value) {
          return value == null || new Date(value) <= new Date(end_date);
        }
        return true;
      }),

  END_DATE: (fieldName) =>
    Yup.date().test('requiredDate', 'Favor de ingresar una fecha de inicio', function (end_date) {
      let start_date = this.resolve(Yup.ref(fieldName));
      if (!start_date && end_date) {
        return false;
      }
      return true;
    }),

  END_DATE_REQUIERED: (fieldName) =>
    Yup.date()
      .required(REQUIRED)
      .test('requiredDate', 'Favor de ingresar una fecha de inicio', function (end_date) {
        let start_date = this.resolve(Yup.ref(fieldName));
        if (!start_date && end_date) {
          return false;
        }
        return true;
      }),

  RFC: Yup.string()
    .required(REQUIRED)
    .matches(EXP_RFC, RFC_INVALIDO)
    .test({
      exclusive: false,
      message: 'El RFC debe ser menor a 13 caracteres',
      test: (value) => value == null || value.length <= 13,
    }),

  ARCHIVO: Yup.mixed()
    .required('Se requiere un archivo')
    .test(
      'fileSize',
      'Archivo demasiado grande, hasta 5 MB',
      (value) => value && value.size <= 5242880,
    )
    .test(
      'fileFormat',
      'Formato no compatible, solo PDF',
      (value) => value && value.type === 'application/pdf',
    ),

  FILE_OPTIONAL: (fileByteSize, fileFormat) =>
    Yup.mixed()
      .test(
        'fileSize',
        `Archivo demasiado grande, hasta ${parseInt(fileByteSize / 1000000)} MB`,
        (value) => (value ? value.size <= fileByteSize : true),
      )
      .test('fileFormat', 'Formato no compatible, solo PDF', (value) =>
        value ? value.type === fileFormat : true,
      ),

  FILE_WITHOUT_FORMAT: Yup.mixed()
    .required('Se requiere un archivo')
    .test('fileSize', 'Archivo no válido', (value) => value && value.size <= 5242880),
};

export default RULES;
