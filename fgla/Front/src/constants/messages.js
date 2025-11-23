export const LOGIN = {
  error: 'Favor de intentarlo más tarde',
  errorLogin: 'Datos de acceso incorrectos',
}

export const ADMIN_CATALOGOS = {
  MSG001: "Información guardada correctamente.",
  MSG002: "No se encontró información relacionada con el catálogo",
  MSG003: "Se perderá toda la información no guardada.\n¿Está seguro de que desea continuar?",
  MSG004: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG005: "La estructura de la información ingresada es incorrecta.\nIntente nuevamente.",
  MSG006: "Registro duplicado. Intente nuevamente.",
  MSG007: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG008: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG009: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG010: "Se actualizará el estatus del registro.\n¿Está seguro de continuar?",
  MSG011: "Al cambiar el estatus de este registro, cambiará todos los estatus de los registros dependientes a éste.\n¿Desea continuar?",
  error: "Ocurrió un error, favor de intentar nuevamente",


  MSG8_MOD: "Existe más de un administrador para este puesto. Favor de validar."
}

export const ADMIN_CATALOGOS_MOD = {
  MSG001: "Información actualizada correctamente."
}

export const ALTA_PROYECTOS = {
  MSG001: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG002: "No existen resultados que coincidan con los criterios de búsqueda ingresados.",
  MSG003: "Se perderá la información que no haya guardado, ¿Desea regresar?",
  MSG004: "El dato ingresado en “Id AGP” es incorrecto.",
  MSG005: "El “Id AGP” ya se encuentra registrado en un proyecto. Ingrese uno válido.",
  MSG006: "Proyecto creado exitosamente.",
  MSG007: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG008: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG009: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  ERROR: "Ocurrió un error",
}

export const MODIFICAR_PROYECTOS = {
  MSG001: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG002: "Ya se cuenta con un líder de proyecto activo. Favor de verificar.",
  MSG003: "Se perderá la información que no haya guardado, ¿Desea salir?",
  MSG004: "El dato ingresado en “Id AGP” es incorrecto.",
  MSG005: "El “Id AGP” ya se encuentra registrado en un proyecto. Ingresar uno válido.",
  MSG006: "Se han actualizado los datos generales.",
  MSG007: "¿Seguro de eliminar la información seleccionada?.",
  MSG008: "Se requiere capturar la información obligatoria en todas las secciones del proyecto.",
  MSG009: "Seleccione una fecha correcta.",
  MSG010: "Se ha registrado la información de la Ficha técnica.",
  MSG011: "Para avanzar el estatus del proyecto, se requiere capturar la información obligatoria de la Ficha técnica.",
  MSG012: "Para avanzar el estatus del proyecto, se requiere un plan de trabajo cargado.",
  MSG013: "No es posible iniciar el proceso de cierre. Existen Contratos en estatus Inicial o Ejecución",
  MSG014: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG015: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG016: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG017: "El proyecto quedará con estatus “Cancelado”, ¿Desea continuar?.",
  MSG018: "¿Confirma que desea cancelar la captura de la información?",
  MSG019: "Se ha actualizado el estatus correctamente.",
  MSG020: "Capture como mínimo una “Administración patrocinadora” y su “Administración Central patrocinadora”.",
  MSG021: "Para cancelar el proyecto, se requiere cancelar contratos y dictámenes relacionados, favor de validar.",
  MSG022: "Se actualizará el estatus. ¿Está seguro de continuar?",
  MSG023: "Favor de seleccionar la fecha fin del Líder del proyecto.",
  MSG024: "La fecha de fin del líder no puede estar vacía ni ser posterior a la actual.",
  MSG025: "Ya se cuenta con un líder de proyecto activo.",
  MSG026: "La fecha de inicio del líder debe ser el siguiente día hábil después de la fecha de fin del último líder.",
  MSG027: "La fecha de inicio del líder debe ser un día hábil y no puede ser posterior a la actual.",
  MSG028: "Solo un líder debe estar activo."
};


export const ENCABEZADO = {
  MSG001: "Solo se permite cargar archivo con formato JPG o PNG.",
  MSG002: "El tamaño máximo de la imagen debe ser de 2.5 x 2.5 cm.",
  MSG003: "Se perderá la información capturada, ¿Está seguro de regresar?",
  MSG004: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG005: "Se almacenó la información correctamente.",
  MSG006: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)",
  MSG007: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG008: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG009: "Se actualizará el estatus del encabezado, ¿Está seguro de continuar?",
}
export const ASOCIAR_FASES = {
  MSG001: "Se guardó la plantilla asociada correctamente.",
  MSG002: "Se perderá la información ingresada. ¿Está seguro de continuar?",
  MSG003: "La fecha de asignación no puede ser mayor que la fecha del día actual.",
  MSG004: "Por favor ingresa toda la información obligatoria.",
  MSG005: "Esta asociación se eliminará.¿Está seguro de que desea continuar?",
  MSG006: "No se puede realizar la acción ya que la plantilla contiene documentos cargados. Por favor, primero elimine los documentos cargados en la sección “Gestión documental” para continuar.",
  MSG007: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG008: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG009: "Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).",
  MSG010: "La plantilla ya se encuentra asociada al proyecto",
}

export const ADMINISTRAR_INFO_COMITES = {
  MSG001: 'La estructura de la información ingresada es incorrecta. Intente nuevamente.',
  MSG002: 'Información guardada correctamente.',
  MSG003: '¿Está seguro de que desea continuar?',
  MSG004: 'Se perderán todos los cambios realizados. ¿Está seguro de que desea continuar?',
  MSG005: 'Favor de ingresar los datos obligatorios marcados con un asterisco (*).',
  MSG006: 'La fecha de sesión no puede ser mayor a la fecha actual.',
  MSG007: 'Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).',
  MSG008: 'Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).',
  MSG009: 'Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).',
  MSG010: 'Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).',
  MSG011: 'No se puede realizar la acción, ya que la plantilla contiene documentos cargados.\n Para continuar, elimine primero los documentos cargados.',
  MSG012: 'Error al generar el enlace y contraseña. Intente nuevamente.'
};

export const MATRIZ_DOCUMENTAL = {
  MSG001: "Los nombres de archivos o carpetas no cumplen con la estructura solicitada. Consulta la “Plantilla tipo”.",
  MSG002: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG003: "El archivo no cumple con la estructura, por favor consulte el archivo de ayuda.",
  MSG004: "Plantilla guardada correctamente.",
  MSG005: "Ya existe una plantilla con el mismo nombre. Intente de nuevo por favor.",
  MSG006: "La estructura de los campos es incorrecta, por favor verifíquela.",
  MSG007: "Se perderá toda la información no guardada. ¿Está seguro de que desea continuar? ",
  MSG008: "La plantilla está asignada a un proyecto activo. No se puede modificar.",
  MSG009: "Se actualizará el estatus, ¿Está seguro de continuar?",
  MSG010: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)",
  MSG011: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG012: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG013: "Por cada “Carpeta nivel” o columna “Archivos” deberá contener una “Descripción” en la fila correspondiente. Consulta la “Plantilla tipo”.",
  MSG014: "El límite de niveles creados será de 10. Consulta la “Plantilla tipo”.",
  MSG015: 'El nombre de la pestaña “Nombre_Fase” en el archivo cargado debe coincidir exactamente con el nombre de una fase registrada en el catálogo de Fases. Consulta la “Plantilla tipo”.',
  MSG016: "No puede haber duplicidad de archivos o carpetas en el mismo nivel. Consulta la “Plantilla tipo”.",
  MSG017: "No se pueden realizar modificaciones en los encabezados, añadir o eliminar columnas. Consulta la “Plantilla tipo”.",
  error: "Ocurrió un error"
}

export const PROVEEDORES = {
  MSG001: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG002: "Al cancelar se perderán los cambios realizados. ¿Está seguro de continuar?.",
  MSG003: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG004: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG005: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG006: "No se encontraron registros de proveedores.",
  MSG007: "El registro ya se encuentra en la BD. Favor de verificar.",
  MSG008: "Los datos se guardaron correctamente.",
  MSG009: "El estatus cambiará a “Inactivo” y no podrá ser usado en otros procesos. ¿Desea continuar?",
  MSG0010: "¿Está seguro de eliminar el registro?",
  MSG0011: "El registro ha sido eliminado correctamente.",
  ERROR: "Ocurrió un error",
}

export const GESTION_DOCUMENTAL = {
  MSG001: "Se perderá el documento seleccionado. ¿Desea continuar?",
  MSG002: "El registro ha sido eliminado correctamente.",
  MSG003: "Debe agregar una justificación",
  MSG004: "Favor de ingresar los datos obligatorios marcados con un asterisco (*)",
  MSG005: "Los datos se guardaron correctamente.",
  MSG006: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG007: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG008: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG009: "Se perderá la información ingresada. ¿Está seguro de cancelar?",
  MSG0010: "Se sustituirá el archivo previamente cargado. ¿Desea continuar?",
  MSG0011: "Error al generar el enlace y contraseña. Intente nuevamente.",
  MSG0012: "Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).",
  MSG0013: "Ocurrió un error al tratar de cargar el archivo.La ruta es repetida por favor carga un archivo con otro nombre.",
  MSG0014: "Se perdió la conexión con Sat Cloud, favor de intentar nuevamente",
  MSG0015: "El archivo no se pudo cargar porque la ruta ya existe. Por favor, sube un archivo con un nombre diferente.",
}


export const ADMINISTRAR_USUARIOS_SISTEMA = {
  MSG001: "Favor de ingresar como mínimo un criterio de búsqueda.",
  MSG002: "Se agregó el o los usuarios al sistema correctamente.",
  MSG003: "Usuario actualmente registrado en el sistema.",
  MSG004: "Se perderá la información ingresada. ¿Está seguro de cancelar?",
  MSG005: "Al inactivar el estatus, los usuarios relacionados no podrán ingresar a los módulos del sistema. ¿Está seguro de continuar?",
  MSG006: "RFC corto no cumple con el formato correcto, favor de validar e intente nuevamente.",
  MSG007: "No existen usuarios relacionados con los criterios de búsqueda ingresados.",
  MSG008: "Se registraron los cambios correctamente.",
  MSG009: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG010: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG011: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."
};

export const CARGA_PLAN_TRABAJO = {
  MSG001: 'Se está adjuntando un nuevo plan de trabajo y sobrescribirá el existente. ¿Está seguro de que desea continuar?',
  MSG002: 'Plan de trabajo guardado correctamente.',
  MSG003: 'No se encontró un plan de trabajo asignado al proyecto.',
  MSG004: 'Se debe adjuntar un archivo de Excel con extensión (.xlsx).',
  MSG005: 'Se perderá toda la información no guardada. ¿Está seguro de que desea continuar?',
  MSG006: 'Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).',
  MSG007: 'Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).',
  MSG008: 'Por cada tarea se tendrán que ingresar valores en los siguientes campos obligatoriamente: "Id tarea", "Nivel de esquema", "Nombre de la tarea", "Activo", "Duración planeada", "Fecha de inicio planeada" y "Fecha fin planeada". Consulte el "Plan tipo".',
  MSG009: 'El "Id tarea" debe ser único e irrepetible. Consulte el "Plan tipo".',
  MSG010: 'Solo puede haber un nivel de esquema con el valor "Cero". Consulte el "Plan tipo".',
  MSG011: 'Por cada tarea(hijo) debe haber una tarea (padre). Consulte el "Plan tipo".',
  MSG012: 'Los valores para "Activo" deben ser "Sí" o "No". Consulte el "Plan tipo".',
  MSG013: 'Los porcentajes deben ser enteros (Aplicar reglas de redondeo). Consulte el "Plan tipo".',
  MSG014: 'Ninguna tarea (Padre) puede tener la fecha de inicio planeada menor que su conjunto sus tareas (hijos). Consulte el "Plan tipo".',
  MSG015: 'Ninguna tarea (padre) puede tener la fecha fin planeada mayor que su conjunto de tareas (hijos). Consulte el "Plan tipo".',
  MSG016: 'Para calcular los valores de los campos "Duración real", obligatoriamente se necesita los valores de los campos "Fecha inicio real" y "Fecha fin real".',
  MSG017: 'Para calcular los valores de los campos "Fecha inicio real", obligatoriamente se necesita los valores de los campos "Duración real" y "Fecha fin real".',
  MSG018: 'Para calcular los valores de los campos "Fecha fin real", obligatoriamente se necesita los valores de los campos "Fecha inicio real" y "Duración real".',
  MSG019: 'El valor para el campo "Predecesora" debe tener un id menor que la tarea que la invoca. Consulte el "Plan tipo".',
  MSG020: 'No se pueden realizar modificaciones en los encabezados, añadir o eliminar columnas. Consulte el "Plan tipo".',
  ERROR_CONSULTA: "Ocurrió un error al consultar la información",
  ERROR: "Ocurrió un error"
};

export const PLAN_MODIFICAR = {
  MSG001: "Plan de trabajo guardado correctamente.",
  MSG002: "Se perderá toda la información que no esté guardada. ¿Está seguro de que desea continuar?",
  MSG003: "La información fue guardada y almacenada con éxito.",
  MSG004: "La estructura de los campos es incorrecta, por favor verifíquela.",
  MSG005: "Hay un valor en el campo “Fecha fin real” sin valor en el campo “Fecha inicio real”. ¿Está seguro de que desea continuar?",
  MSG006: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG007: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG008: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG009: "¿Desea calcular el porcentaje “Completado” para las tareas padre?",
}

export const CASO_DE_NEGOCIO = {
  MSG001: "Verifique el layout de carga, ya que la línea(s) [Concepto de servicio] no cumple con la “Cantidad de servicios máxima”.",
  MSG002: "El layout de carga no contiene la estructura requerida, favor de verificar.",
  MSG003: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG004: "La proyección de caso de negocio fue actualizado exitosamente.",
  MSG005: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG006: "Existe una proyección previamente cargada, ¿desea actualizar la proyección?",
  MSG007: "Los datos se exportaron correctamente."
};

export const ALTA_CONTRATOS = {
  MSG001: "Favor de ingresar los datos obligatorios marcados con (*).",
  MSG002: "No existen resultados que coincidan con los criterios de búsqueda ingresados.",
  MSG003: "Se perderá la información que no haya guardado. ¿Desea cancelar?",
  MSG004: "Contrato creado exitosamente.",
  MSG005: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG006: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG007: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG008: "Introduzca al menos un criterio de búsqueda.",
  MSG009: "Ya existe un contrato con los datos ingresados. Favor de revisar.",
  MSG0010: "El contrato solo puede tener un 'Administrador del contrato'."
}

export const CREAR_ESTIMACION = {
  MSG001: "Información almacenada exitosamente.",
  MSG002: "Se perderá la información capturada ¿Desea continuar?.",
  MSG003: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG004: "El periodo seleccionado es incorrecto.",
  MSG005: "Ya existe una estimación, favor de verificar los datos.",
  MSG006: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG007: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG008: "¿Desea cancelar la estimación?.",
  MSG009: "¿Desea rellenar los campos vacíos con cero?.",
  MSG010: "¿Está seguro de reabrir la estimación?).",
};

export const ADMIN_FORMATOS = {
  MSG001: "Se guardó la información de la plantilla correctamente.",
  MSG002: "El archivo seleccionado no es un Excel. Por favor seleccione un archivo con la extensión correcta.",
  MSG003: "Se perderán todos los cambios no guardados. ¿Está seguro que desea continuar? ",
  MSG004: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG005: "Se actualizará el estatus de la plantilla. ¿Está seguro de continuar?",
  MSG006: "La plantilla se encuentra asignada, favor de desasociar antes de inactivar.",
};

export const MODIFICAR_PLANTILLA = {
  MSG001: "Se perderán todos los cambios capturados. ¿Está seguro de que desea continuar?",
  MSG002: "Ocurrió un error al guardar la información, favor de intentar nuevamente (PA01).",
  MSG003: "El nombre de la plantilla ya existe. Favor de modificarlo.",
  MSG004: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG005: "Plantilla actualizada correctamente.",
  MSG006: "Favor de ingresar los datos obligatorios.",
  MSG007: "La estructura de la información ingresada es incorrecta. Por favor verifique los datos y vuelva a intentarlo."
}

export const PROFORMA = {
  MSG001: 'El estatus del dictamen ha cambiado a “Proforma”.',
  MSG002: 'Ocurrió un error al guardar la información, favor de intentar nuevamente (PA01).',
  MSG003: 'Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).',
  MSG004: '¿Está seguro de rechazar este dictamen?',
  MSG005: 'Favor de ingresar los datos obligatorios.',
  MSG006: 'La información ingresada no cumple con el formato esperado. Por favor verifíquela y vuelva a intentarlo.',
  MSG007: 'El registro se va a eliminar de la tabla ¿Está seguro de que desea continuar?',
  MSG008: 'La fecha de recepción ingresada debe ser mayor o igual a la fecha de solicitud. Por favor verifique el dato y vuelva a intentarlo.',
  MSG009: 'Se perderá toda la información no guardada. ¿Está seguro de que desea continuar?.',
  MSG010: 'Ocurrió un error en la generación de la proforma. Inténtelo nuevamente.',
  MSG011: 'Los datos se guardaron correctamente.',
  MSG012: 'El archivo existente se reemplazará. ¿Está seguro de que desea continuar?',
  MSG013: 'El archivo seleccionado no contiene la extensión .xlsx. Favor seleccione un archivo con la extensión correcta.',
  MSG014: 'El archivo seleccionado no contiene la extensión .pdf. Favor seleccione un archivo con la extensión correcta.',
  MSG015: 'Ninguna tarea (padre) puede tener la fecha fin planeada mayor que su conjunto de tareas (hijos). Consulte el "Plan tipo".',
  MSG016: 'Para calcular los valores de los campos "Duración real", obligatoriamente se necesita los valores de los campos "Fecha inicio real" y "Fecha fin real".',
  MSG017: 'Para calcular los valores de los campos "Fecha inicio real", obligatoriamente se necesita los valores de los campos "Duración real" y "Fecha fin real".',
  MSG018: 'Para calcular los valores de los campos "Fecha fin real", obligatoriamente se necesita los valores de los campos "Fecha inicio real" y "Duración real".',
  MSG019: 'El valor para el campo "Predecesora" debe tener un id menor que la tarea que la invoca. Consulte el "Plan tipo".',
  MSG020: 'No se pueden realizar modificaciones en los encabezados, añadir o eliminar columnas. Consulte el "Plan tipo".',
  MSG021: 'El dictamen pasara a estatus PAGADO, esta accion no se podra revertir. Esta seguro que desea continuar?'
};

export const GENERAR_FACTURA = {
  MSG001: "La extensión del archivo no es correcta. Favor de verificar la extensión.",
  MSG002: "El comprobante fiscal y/o folio ya se encuentra registrado para este proveedor.",
  MSG003: "La factura no corresponde a los datos del proveedor del contrato.",
  MSG004: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG005: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG006: "Los datos de la factura se guardaron satisfactoriamente.",
  MSG007: "La estructura de la información ingresada es incorrecta. Intente nuevamente.",
  MSG008: "Se perderá la información no guardada, ¿Desea cancelar?",
  MSG009: "¿Está seguro de actualizar la factura?",
  MSG010: "El estatus de la factura pasará a 'Cancelado' y no se podrá realizar ninguna acción posterior. ¿Está seguro de cancelar?",
  MSG011: "Se ha realizado la cancelación de la factura.",
  MSG012: "El monto ingresado es diferente al total de la factura, favor de verificar.",
  MSG013: "La extensión del archivo no es correcta. Favor de verificar y cargar archivo con extensión PDF.",
  MSG014: "Para continuar debe seleccionar la opción recepción de facturas, ¿Desea aplicarlo?",
  MSG015: "Para regresar el estatus a proforma debe cancelar las Facturas y/o Notas de crédito.",
  MSG016: "El dictamen cambiará al estatus 'Proforma' ¿Está seguro de continuar?",
  MSG017: "La estructura de los datos no es correcta de acuerdo al anexo 20, Favor de validar.",
  MSG018: "Mensaje de rechazo"
}
export const MODIFICAR_CONTRATO = {
  MSG001: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG002: "Al cancelar se perderán los cambios realizados. ¿Está seguro de continuar?",
  MSG003: "Precaución el registro se encuentra relacionado a otro(s) módulo(s). ¿Desea continuar?",
  MSG004: "Se guardó correctamente la información.",
  MSG005: "Para poder cancelar el contrato, no deben existir estimaciones no pagadas. Favor de verificar.",
  MSG006: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG007: "No se puede cancelar el contrato porque tiene dictámenes asociados.",
  MSG008: "El estatus cambiará a “Inactivo” y no podrá ser usado en otros procesos. ¿Desea continuar?",
  MSG009: "La sumatoria de los campos “Monto máximo” de la tabla “Registro de servicios” no es igual al campo “Monto máximo en pesos con impuestos” de la sección “Vigencia y montos”. Favor de verificar los datos ingresados.",
  MSG010: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG011: "El registro no se puede eliminar porque se encuentra relacionado en otro módulo.",
  MSG012: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG013: "Si se desactiva la vigencia, es necesario ingresar una fecha de término. Verifique por favor.",
  MSG014: "Existe información previamente cargada. ¿Desea actualizarla?",
  MSG015: "La información ha sido actualizada exitosamente.",
  MSG016: "El archivo cargado no contiene la misma estructura que la “Sección a cargar” seleccionada. Favor de verificar.",
  MSG017: "Se eliminará el registro seleccionado. ¿Desea continuar?",
  MSG018: "Para poder cerrar el contrato, se tiene que cargar el acta de cierre. Favor de verificar.",
  MSG019: "Para poder cerrar el contrato, no deben existir dictámenes pendientes de pago. Favor de verificar.",
  MSG020: "El Monto máximo de los servicios coincide con el Monto máximo sin impuestos del contrato.",
  MSG021: "No se puede cargar la información del layout porque tiene dictámenes asociados.",
  MSG022: "Ocurrió un error al eliminar el registro, favor de intentar nuevamente (PA01).",
  MSG023: "El contrato no cuenta con registros de servicio, favor de verificar.",
  MSG024: "El estatus del contrato cambiará a “Ejecución”. ¿Desea continuar?",
  MSG025: "El estatus del contrato cambiará a “Inicial”. ¿Desea continuar?",
  MSG026: "El estatus del contrato cambiará a “Cancelado”. ¿Desea continuar?",
  MSG027: "El grupo ya se encuentra registrado, por favor intente nuevamente.",
};

export const NOTA_CREDITO = {
  MSG001: "La nota de crédito se registró exitosamente.",
  MSG002: "¿Está seguro de actualizar el XML de la nota de crédito?",
  MSG003: "La nota de crédito se cancelará y no se podrá modificar la información posteriormente. ¿Desea continuar?",
  MSG004: "La nota de crédito ha sido cancelada exitosamente.",
  MSG005: "El tipo de archivo seleccionado no es un archivo válido, favor de verificarlo y seleccionar un archivo con extensión XML.",
  MSG006: "El comprobante fiscal del archivo seleccionado ya fue utilizado previamente, favor de verificarlo.",
  MSG007: "El comprobante fiscal no corresponde al RFC y/o razón social del proveedor del contrato-dictamen, favor de verificarlo.",
  MSG008: "Se perderá la información no guardada, ¿Desea continuar?",
  MSG009: "No se ingresaron todos los campos obligatorios en el formulario, favor de verificarlo.",
  MSG010: "El monto ingresado es diferente al monto total de la nota de crédito, favor de verificarlo.",
  MSG011: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG012: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG013: "La extensión del archivo no es correcta. Favor de verificar y cargar un archivo con extensión PDF.",
  MSG014: "La estructura de la información es incorrecta. Intente nuevamente.",
  MSG015: "La estructura de los datos no es correcta de acuerdo al anexo 20, favor de validar.",
  MSG016: "Mensaje de rechazo."
}

export const NOTA_PAGO = {
  MSG001: "Ocurrió un error al guardar el registro. Favor de intentar nuevamente (PA01).",
  MSG002: "Ocurrió un error al generar el documento. Favor de intentar nuevamente (PA01).",
  MSG003: "Se perderá la información ingresada. ¿Desea continuar?",
  MSG004: "Debe ingresar un archivo PDF. Favor de verificar.",
  MSG005: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG006: "El estatus del Dictamen ha cambiado a “Pagado”.",
  MSG007: "Falta ingresar el archivo PDF. Favor de verificar.",
  MSG008: "El estatus del Dictamen ha cambiado a “Solicitud de Pago”.",
  MSG009: "Se cancelará la acción en curso. ¿Está seguro de continuar?",
  MSG010: "La estructura de la información es incorrecta. Favor de validar.",
  MSG011: "Ya existe un documento previamente cargado. ¿Desea reemplazarlo"
}

export const REGISTRAR_REINTEGROS = {
  MSG001: "Ocurrió un error al guardar la información, favor de intentar nuevamente (PA01).",
  MSG002: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG003: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG004: "El contrato no contiene reintegros.",
  MSG005: "Favor de ingresar los datos obligatorios.",
  MSG006: "Se perderá toda la información relacionada a este reintegro. ¿Está seguro de que desea continuar?",
  MSG007: "Se perderá toda la información no guardada. ¿Está seguro de que desea continuar?",
  MSG008: "La información ingresada no cumple con el formato esperado. Por favor verifique los datos y vuelva a intentarlo.",
  MSG009: "El usuario no tiene proyectos asignados. Favor de validar con el administrador del sistema.",
  MSG010: "Los datos se guardaron correctamente."
}

export const PISTAS_AUDITORIA = {
  MSG001: "Es necesario seleccionar la sección.",
  MSG002: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG003: "No se encontraron resultados de la búsqueda.",
  MSG004: "Las fechas ingresadas son incorrectas. Favor de verificar.",
  MSG005: "No se han encontrado roles asignados a su usuario.",
  MSG006: "Procesando…",
  MSG007: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  REQUERIDOS: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  error: "Ocurrió un error"
}

export const PAPELERA_RECICLAJE = {
  MSG001: "El rango de fechas ingresado es incorrecto. Favor de verificar.",
  MSG002: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG003: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG004: "Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).",
  MSG005: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG006: "Procesando…",
  MSG007: "Introduzca al menos un criterio de búsqueda.",
  MSG008: "No se encontraron resultados de la búsqueda. Intente nuevamente.",
  MSG009: "El documento será eliminado de forma permanente. ¿Desea continuar?",
  MSG010: "Se ha producido un error al generar el enlace y contraseña. Intente nuevamente",
  MSG011: "El documento será restaurado a su ubicación original. ¿Desea continuar?",
  MSG012: "El documento ha sido eliminado definitivamente.",
  MSG013: "El documento ha sido restaurado correctamente.",
  MSG014: "No se han encontrado roles asignados a su usuario."
}

export const CONTROL_DOCUMENTAL = {
  MSG001: "No se cuenta con proyectos asignados.",
  MSG002: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG003: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG004: "No se encontraron resultados en la búsqueda.",
  MSG005: "Error al generar el enlace y contraseña. Intente nuevamente.",
  MSG006: "Procesando…",
}
export const REGISTRO_CONVENIO = {
  MSG001: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG002: "Se agregó correctamente el convenio modificatorio al sistema.",
  MSG003: "El convenio modificatorio ya se encuentra registrado en el sistema.",
  MSG004: "Se perderá la información ingresada. ¿Está seguro de cancelar?",
  MSG005: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG006: "El número de convenio debe empezar con el número de contrato.",
};

export const EDITAR_CONVENIOS = {
  MSG001: "Favor de ingresar los datos obligatorios marcados con un asterisco (*).",
  MSG002: "Se cargó la proyección al convenio modificatorio.",
  MSG003: "El “Monto máximo del contrato con CM sin impuestos” coincide con el “Monto máximo total” de los servicios.",
  MSG004: "Se perderá la información ingresada. ¿Está seguro de cancelar?",
  MSG005: "El layout de carga no contiene la estructura requerida, favor de verificar.",
  MSG006: "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01).",
  MSG007: "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01).",
  MSG008: "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01).",
  MSG009: "Existe una proyección previamente cargada, ¿desea actualizarla?",
  MSG010: "El convenio modificatorio fue actualizado exitosamente.",
  MSG011: "Se guardo correctamente la información.",
  MSG012: "El “Monto máximo del contrato con CM sin impuestos” no coincide con el “Monto máximo total” de los servicios.",
  MSG013: "Verifique el layout de carga, ya que la línea(s) [Concepto de servicio] sobrepasa el “Número total de servicios”.",
  MSG014: "Se guardó correctamente la información.",
  MSG015: "Se eliminará el registro seleccionado. ¿Desea continuar?",
  MSG016: "El registro no se puede eliminar porque se encuentra relacionado en otro módulo.",
  MSG017: "Ocurrió un error al eliminar la información, favor de intentar nuevamente (PA01).",
  MSG018: "Ocurrió un error, favor de intentar más tarde.",
};