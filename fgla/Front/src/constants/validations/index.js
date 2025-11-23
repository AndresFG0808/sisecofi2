import * as Yup from 'yup';
import RULES from './rules';

const REQUIRED = 'Campo requerido';
const NUMBER = 'Introduce un número';
const CADENA = 'No se permiten caracteres especiales';
const RFC_INVALIDO = 'RFC no válido';
const DATE_START_MSG =
  'La Fecha inicio debe ser menor que la Fecha fin, favor de seleccionar un dato válido.';
const PERIODO_INICIO_MSG =
  'El ejercicio desde debe ser menor o igual que el periodo hasta, favor de seleccionar un dato válido.';

//MaxLength
const MAX_DENOMINACION = 500;
const MAX_FOLIO = 18;

//regular expression
const EXP_CADENA = /^([0-9]|[A-Z]|[a-z]|[ÑñáéíóúÑÁÉÍÓÚüÜ\-,.']|[\s])*$/;
const EXP_RFC =
  '[A-Za-z,ñ,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Za-z,0-9]?[A-Za-z,0-9]?[0-9,A-Za-z]?';

const EXP_RFC_CORTO = '[a-zA-Z0-9]';

//Custom rules
const FILE_WITHOUT_FORMAT = Yup.mixed()
  .required('Se requiere un archivo')
  .test('fileSize', 'Archivo no válido', (value) => value && value.size <= 5242880);

const ARCHIVO = Yup.mixed().required('Se requiere un archivo');
// .test(
//   'fileSize',
//   'Archivo demasiado grande, hasta 5 MB',
//   (value) => value && value.size <= 5242880,
// )
// .test(
//   'fileFormat',
//   'Formato no compatible, solo PDF',
//   (value) => value && value.type === 'application/pdf',
// )
const STRING = (max) =>
  Yup.string()
    .matches(EXP_CADENA, CADENA)
    .test({
      name: 'max',
      exclusive: false,
      params: { max },
      message: 'El valor debe ser menor a ${max} caracteres',
      test: (value) => value == null || value.length <= max,
    });

const RFC = Yup.string()
  .matches(EXP_RFC, RFC_INVALIDO)
  .test({
    exclusive: false,
    message: 'El RFC debe ser menor a 13 caracteres',
    test: (value) => value == null || value.length <= 13,
  });

const RFC_EMPLEADO = Yup.string()
  .matches(EXP_RFC, RFC_INVALIDO)
  .test({
    exclusive: false,
    message: 'El RFC debe ser igual a 12 o 13 caracteres',
    test: (value) => value == null || (value.length >= 12 && value.length <= 13),
  });

const RFC_CORTO = Yup.string()
  .matches(EXP_CADENA, RFC_INVALIDO)
  .test({
    exclusive: false,
    message: 'El RFC corto debe ser igual a 8 caracteres',
    test: (value) => value == null || value.length == 8,
  });

  const START_DATE_PRINCIPAL = Yup.date()
  .test('minDate', DATE_START_MSG, function (value) {
    let end_date_principal = this.resolve(Yup.ref('fFinVig'));
    if (end_date_principal && value) {
      return value == null || new Date(value) < new Date(end_date_principal);
    }
    return true;
  });

  const  END_DATE_PRINCIPAL =  Yup.date()
    .test('requiredDate', 'Favor de ingresar una fecha de inicio', function (end_date_principal) {
      let start_date_principal = this.resolve(Yup.ref('fInicioVig'));
      if (!start_date_principal && end_date_principal) {
        return false;
      }
      return true;
    });

    const EJERCICIO_FIS_INICIO = Yup.string()
    .test('valormayor',PERIODO_INICIO_MSG, function (ejercicio_fis_inicio) {
      let ejercicio_fis_fin = this.resolve(Yup.ref('ejercicioHasta'));
      if (ejercicio_fis_fin ) {
        return ejercicio_fis_fin == null || ejercicio_fis_inicio <= ejercicio_fis_fin;
        
      }
      return true;
    });
  
    const  EJERCICIO_FIS_FIN =  Yup.string()
    .test('ejercicio','Favor de seleccionar ejercicio desde', function (ejercicio_fis_fin) {
        let ejercicio_fis_inicio = this.resolve(Yup.ref('ejercicioDesde'));
        if (!ejercicio_fis_inicio && ejercicio_fis_fin) {
          return false;
        }
        return true;
      });


const END_DATE = Yup.date()
  .test(
    'maxDate',
    'No se permiten fechas mayor a la actual',
    (value) => value == null || value <= new Date(),
  )
  .test('requiredDate', 'Favor de ingresar un periodo desde', function (end_date) {
    let start_date = this.resolve(Yup.ref('fechaInicial'));
    if (!start_date && end_date) {
      return false; 
    }
    return true;
  })
  .test('minDate', 'El periodo hasta debe ser menor o igual que el periodo actual', function (
    value,
  ) {
    let start_date = this.resolve(Yup.ref('fechaInicial'));
    if (start_date && value) {
      return value == null || new Date(start_date) <= new Date(value);
    }
    return true;
  });

const START_DATE = Yup.date()
  .test(
    'maxDate',
    'No se permiten fechas mayor a la actual',
    (value) => value == null || value <= new Date(),
  )
  .test('requiredDate', 'Favor de ingresar un periodo hasta', function (start_date) {
    let end_date = this.resolve(Yup.ref('fechaFinal'));
    if (start_date && !end_date) {
      return false;
    }

    return true;
  })
  .test('minDate', 'El periodo desde debe ser menor o igual que el periodo hasta', function (
    value,
  ) {
    let end_date = this.resolve(Yup.ref('fechaFinal'));
    if (end_date && value) {
      return value == null || new Date(value) <= new Date(end_date);
    }
    return true;
  });


  const START_DATEPeriodos = Yup.date()
  .test(
    'maxDate',
    'No se permiten fechas menores a la actual',
    (value) => value == null || value >= new Date(),
  )
  .test('requiredDate', 'Favor de ingresar un periodo hasta', function (start_date_periodos) {
    let end_date_periodos = this.resolve(Yup.ref('fechaFin'));
    if (start_date_periodos && !end_date_periodos) {
      return false;
    }

    return true;
  })
  .test('minDate', 'El periodo desde debe ser menor o igual que el periodo hasta', function (
    value,
  ) {
    let end_date_periodos = this.resolve(Yup.ref('fechaFin'));
    if (end_date_periodos && value) {
      return value == null || new Date(value) <= new Date(end_date_periodos);
    }
    return true;
  });

  const END_DATEPeriodos = Yup.date()
  .test(
    'maxDate',
    'No se permiten fechas menor a la actual',
    (value) => value == null || value >= new Date(),
  )
  .test('requiredDate', 'Favor de ingresar un periodo desde', function (end_date_periodos) {
    let start_date_periodos = this.resolve(Yup.ref('fechaInicio'));
    if (!start_date_periodos && end_date_periodos) {
      return false; 
    }
    return true;
  })
  .test('minDate', 'El periodo hasta debe ser mayor o igual que el periodo actual', function (
    value,
  ) {
    let start_date_periodos = this.resolve(Yup.ref('fechaInicio'));
    if (start_date_periodos && value) {
      return value == null || new Date(start_date_periodos) <= new Date(value);
    }
    return true;
  });


export const publicacionEstadoSchema = Yup.object().shape({
  fPublicAnexo: Yup.date(),
});

export const buscarSchema = Yup.object().shape({
  dRfcContri: RFC,
  denominacion: STRING(MAX_DENOMINACION),
  expediente: Yup.number().typeError(NUMBER).nullable(),
  fPublicAnexo: Yup.date(),
});

export const datosGeneralesSchema = Yup.object().shape({
  idTipCertificacion: Yup.string(),
  objetoSocial: Yup.string(),
  acreditamiento: Yup.string(),
  fAcreditamiento: Yup.date(),
  fVigAutoCondici: Yup.date(),
  personaAutorizada: Yup.string(),
  emailPersonaAut: Yup.string(),
  telfonoPersonaAut: Yup.string(),
});

export const loginSchema = Yup.object().shape({
  //rfc: Yup.string().required(REQUIRED),
  //password: Yup.string().required(REQUIRED),
  //cer: FILE_WITHOUT_FORMAT,
  //key: FILE_WITHOUT_FORMAT,
});

export const consultarInforme = Yup.object().shape({
  folioInforme: STRING(MAX_FOLIO),
  fechaInicial: START_DATE,
  fechaFinal: END_DATE,
});

export const consultarInformeDonatAut = Yup.object().shape({
  rfc:RFC,
  denominacion: STRING(MAX_DENOMINACION),
  fechaDesde: RULES.START_DATE('fechaHasta'),
  fechaHasta: RULES.END_DATE('fechaDesde'),
});
export const consultarInformeTransparencia = Yup.object().shape({
  rfc: RFC,
  denominacion: STRING(MAX_DENOMINACION),
  fechaDesde: RULES.START_DATE('fechaHasta'),
  fechaHasta: RULES.END_DATE('fechaDesde'),
  montoDesde: Yup.number().typeError(NUMBER).nullable().test(
    'is-decimal',
    'solo dos decimales',
    value => (value + "").match(/^(\d+(\.\d{0,2})?|\.?\d{1,2})$/)),
  montoHasta: Yup.number().typeError(NUMBER).nullable().test(
    'is-decimal',
    'solo dos decimales',
    value => (value + "").match(/^(\d+(\.\d{0,2})?|\.?\d{1,2})$/)),
  ejercicioDesde: EJERCICIO_FIS_INICIO,
  ejercicioHasta: EJERCICIO_FIS_FIN, 

});

export const historicoSchema = Yup.object().shape({
  nAsunto: Yup.string().required(REQUIRED),
  nOficioSifen: Yup.string().required(REQUIRED),
  fOficioSIFEN: Yup.string().required(REQUIRED),
  folioSIFEN: STRING(50).required(REQUIRED),
  //documentoSIFEN: ARCHIVO,
  tipoResolucionId: Yup.string().required(REQUIRED),
  idEstadoTramite: Yup.string().required(REQUIRED),
  idAutorizacion: Yup.string().required(REQUIRED),
  fPublicacion: Yup.date().required(REQUIRED),
  //fRevocacion: Yup.date().required(REQUIRED),
  dObservacion: STRING(500),
  dEjercicio: Yup.number().typeError(NUMBER).required(REQUIRED),
});

export const usuarioSchema = Yup.object().shape({
  nombre: STRING(100).required(REQUIRED),
  apPaterno: STRING(100).required(REQUIRED),
  apMaterno: STRING(100).required(REQUIRED),
  rfc: RFC.required(REQUIRED),
  rfcCorto: STRING(10).required(REQUIRED),
});

export const usuarioSearchSchema = Yup.object().shape({
  numEmpleado: Yup.number().typeError(NUMBER).nullable(),
  nombreEmpleado: STRING(100),
  rfc: RFC_EMPLEADO,
  rfcCorto: RFC_CORTO,
});

export const catalogoSchema = Yup.object().shape({
  dvalor: STRING(300).required(REQUIRED),
  fInicioVig: START_DATE_PRINCIPAL.required(REQUIRED),
  fFinVig: END_DATE_PRINCIPAL.required(REQUIRED),
  dvalorAdicional: STRING(200).nullable(),
});

export const DonatActualizadasSchema = Yup.object().shape({
  rfc: RFC,
  denominacion: STRING(MAX_DENOMINACION),
  fInicioVig: Yup.date().required(REQUIRED),
  fFinVig: Yup.date(),
  dvalorAdicional: STRING(300).nullable(),
});

export const periodosSchema = Yup.object().shape({
  tipoInforme: RULES.STRING_REQUIRED,
  fechaInicio: START_DATEPeriodos.required(REQUIRED),
  fechaFin: END_DATEPeriodos.required(REQUIRED),

  periodoEventualidad: RULES.STRING_CONDITIONAL_REQUIRED('tipoInforme', '202'),
  periodoEventualidad: RULES.STRING_CONDITIONAL_REQUIRED('tipoInforme', '202'),
  tipoEventualidad: RULES.STRING_CONDITIONAL_REQUIRED('tipoInforme', '202'),
  tipoEventualidad: RULES.STRING_CONDITIONAL_REQUIRED('tipoInforme', '202'),
  
});



