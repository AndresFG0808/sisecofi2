const requiredFields = [
  {
    requiredField: 'idProveedor',
    helperText: '',
  },
];

export const data = [
];

export const providerModel = {
  identifier: null,
  requiredFields,
  idProveedor: '',
  idIMercado: '',
  fechaIM: null,
  idRespuestaIM: '',
  fechaRespuestaIM: null,
  idJuntaAclaracion: '',
  fechaJuntaAclaracion: null,
  idLicitacionPublica: '',
  fechaPropuesta: null,
  isNewProvider: true,
  isEditable: false,
};

export const validateText = 'Dato requerido';

export const dateFormat = 'DD/MM/YYYY';

export const commentsData = [
  {
    usuario: 'Ayuso',
    fechaIngeso: '2024-04-22',
    observaciones: 'Comentario test'
  },
  {
    usuario: 'Ayuso',
    fechaIngeso: '2024-04-21',
    observaciones: 'Comentario test 2'
  },
];

export const providerTableTitle = 'Proveedores';

export const MESSAGES = {
  MSG001: 'La información ha sido actualizada.',
  MSG002: 'Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).',
  MSG003: 'Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).',
  MSG004: 'Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).',
  MSG005: 'No se tienen proveedores para ser agregados, favor de registrar en el módulo correspondiente.',
  MSG006: 'Se perderá la información ingresada. ¿Está seguro de continuar?',
  MSG007: 'Favor de ingresar los datos obligatorios.',
  MSG008: '¿Está seguro de eliminar el registro del proveedor participante en el proyecto?',
};
