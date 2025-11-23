package com.sisecofi.contratos.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresEnum implements DefinitionMessage {

	ERROR_MSJ_SIN_USUARIO("001", "No se encontro usuario"),
	ERROR_AL_GUARDAR("002", "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)."),
	ERROR_AL_CONSULTAR("003", "No se pudo consultar la informacion"),
	ERROR_NOMBRES_REPETIDOS("004", "No se pudo guardar la inforamcion, nombre corto o nombre del contrato repetidos"),
	CONTRATO_NO_ENCONTRADO("004", "No existen resultados que coincidan con los criterios de búsqueda (MSGOO2)"),
	ERROR_AL_GENERAR_LAYOUT("005", "Ocurrio Un error al generar el layout base."),
	ERROR_ARCHIVO_NO_COMPATIBLE("006", "El layout de carga no contiene la estructura requerida, favor de verificar."),
	FECHA_NO_PERMITIDA("005", "Fecha "),
	CANTIDAD_DE_SERVICIOS("006", "Verifique el layout de carga, ya que la línea(s) %s no cumple con la “Cantidad de servicios máxima”."),
	CASO_DE_NEGOCIO_NO_ENCONTRADO("007", "El caso de negocio de deseas consultar o modificar no esta registrado."),
	CONTRATOS_NO_ENCONTRADOS("008", "No existen resultados que coincidan con los criterios de búsqueda ingresados."),
	PROYECTO_NO_ENCONTRADO("0010", "No se ecnontro un proyecto"),
	ESTIMACION_ASOCIADA("0011", "Para poder cancelar el contrato, no deben existir estimaciones no pagadas. Favor de verificar."),
	DICTAMEN_ASOCIADO("0012", "Para poder cerrar el contrato, no deben existir dictámenes pendientes de pago. Favor de verificar."),
	ERROR_ID_ESTATUS_CONTRATO("0013", "Dictamen asociado al contrato, cancele los dictamenes asociadas"),
	ERROR_VIGENCIA_MONTOS("0014", "No se ah encontrato un registro de vigencia y montos aún"),
	ERROR_REGISTRO_SERVICIOS("0014", "Se nececita registrar por lo menos un servicio"),
	ERROR_USUARIO_NO_ENCONTRADO("0016", "No se encontro el usuario"),
	ERROR_AL_ELIMINAR("0017", "No se pudo eliminar la informacion"),
	GRUPO_SERVICIO_NO_ENCONTRADO("0018", "No se encontro el grupo de servicio"),
	SERVICIO_CONTRATO_NO_ENCONTRADO("0019", "No se encontro el registro de servicio"),
	ERROR_REGISTROS_NO_ENCONTRADO("0020", "No se encontraron registros"),
	ERROR_AL_RENOMBRAR_ARCHIVO("0021", "No se encontraron registros"),
	ERROR_AL_ACTUALIZAR_ARCHIVO("0022", "Es necesario mandar el id del archivo"),
	CONTRATO_CON_VIGENCIA_MONTOS("0023", "El contrato ya tiene vigencia y montos guardado"),
	ERROR_CONTRATO_CADUCADO("0023", "El contrato ya expiro"),
	ERROR_ULTIMO_PROVEEDOR("0024", "Debe haber por lo menos un proveedor"),
	ERROR_DATOS_GENERALES_REGISTRADO("0025", "Solo debe haber un registro de datos generales por contrato"),
	USUARIO_NO_ENCONTRADO("0026", "Usuario no encontrado"),
	PENA_NO_ENCONTRADA("027", "Usuario no encontrado"),
	ERROR_AL_GUARDAR_PROVEEDORES("028", "Ocurrió un error al guardar los proveedores."),
	ASOCIACION_NO_ENCONTRADA("029", "Registro no encontrado."),
	ERROR_ARCHIVO_NO_CARGADO("030", "Ocurrió un error al tratar de cargar el archivo."),
	ERROR_RUTA_OCUPADA("031", "La ruta es repetida por favor carga un archivo con otro nombre."),
	ERROR_DATOS_OBLIGATORIOS("031", "Faltan datos obligatiorios "),
	ERROR_ESTATUS_CONTRATO("032", "No se encontro el estatus del contrato"),
	ERROR_NOMBRES_CONTRATO("033", "Nombre de contrato y nombre corto son iguales"),
	ERROR_NOMBRES_CONTRATO_EN_DB("034", "Nombre de contrato o nombre corto ya existen"),
	ERROR_ESTATUS_CAT_TIPO_CONSUMO("035", "Error en el nombre del catalogo tipo consumo"),
	ERROR_AL_ACTUALIZAR_MODIFICADOR_CONTRATO("036", "No se guardo la informacion del ultimo modificador del contrato"),
    ERROR_CONVENIO_YA_REGISTRADO("037", "El convenio modificatorio ya se encuentra registrado en el sistema."),
	ERROR_NUEMERO_CONVENIO_INCORRECTO("038", "El número de convenio debe empezar con el número de contrato."),
	ERROR_DICTAMENES_ASOCIADOS("39","No se puede cargar la información del layout porque tiene dictámenes asociados."),
	ERROR_ESTRUCTURA_LAYOUT("40","El archivo cargado no contiene la misma estructura que la “Sección a cargar” seleccionada. Favor de verificar."),
	ERROR_AL_ELIMINAR_ARCHIVO("41","El archivo no se ha podido eliminar."),
	ERROR_VALIDAR_DATOS_REINTEGROS("42", ErroresEnum.MensajeValidation.MSJREINTEGRO),
	ERROR_BUSCAR_REINTEGRO("43", "No se encontro ningún reintegro"),
	ERROR_BUSCAR_CAT_TIPOREINTEGRO("44", "No existe el Tipo de reintegro en el catalogo"),
	ERROR_CONTRATO_VACIO("45", "el Id Contrato no existe"),
	
	ERROR_IMPORTE("46", ErroresEnum.MensajeValidation.MSJIMPORTE),
	ERROR_INTERES("47", ErroresEnum.MensajeValidation.MSJINTERES),
	ERROR_CERO_IMPORTE("48", ErroresEnum.MensajeValidation.MSJCEROIMPORTE),
	ERROR_CERO_INTERES("49", ErroresEnum.MensajeValidation.MSJCEROINTERES),

	CANTIDAD_DE_SERVICIOS_CONVENIO("50", "Verifique el layout de carga, ya que la línea(s) %s sobrepasa el “Número total de servicios”."),
	ERROR_MONTO_MAXIMO("51", "El “Monto máximo del contrato con CM sin impuestos” no coincide con el “Monto máximo total” de los servicios."),
	ERROR_CONTRATO_SIN_REINTEGRO("52", "El contrato no contiene reintegros."),
	ERROR_CONTRATO_SIN_VIGENCIA("53", "El contrato no tiene vigencia y montos"),
	ERROR_AL_ASIGNAR_PLANTILLA("54", "Ocurrio un error al asignar la plantilla."),
	ERROR_AL_GUARDAR_PARTICIPANTE("55", "El contrato solo puede tener un 'Administrador del contrato'"),
	ERROR_ARCHIVOS_ASOCIADOS("56", "El Reintegro tiene archivos asociados, no se puede eliminar"),
	ERROR_CONTRATOS_NO_ENCONTRADOS("57", "No hay registros de contratos"),
	ESTIMACION_NO_CANCELADA("58", "Existen estimaciones no canceladas"),
	DICTAMEN_NO_CANCELADO("59", "Existen dictamenes no cancelados o no pagados"),
	DICTAMEN_NO_PAGADO("60", "Existen dictamenes no pagados"),
	COINCIDEN_MONTO_MAXIMO("61", "El “Monto máximo del contrato con CM sin impuestos” coincide con el “Monto máximo total” de los servicios"),
	ERROR_SERVICIO_TIPO_BOLSA("62", "El “Monto máximo de los servicios tipo bolsa para cada grupo de servicio deben ser iguales"),
	ERROR_INTERNO("63", "Error interno"),
	CONEXION_PERDIDA("64", "Se perdió la conexión con Sat Cloud, favor de intentar nuevamente"),
	ERROR_AL_DESCARGAR_ARCHIVO("65", "Ocurrio un error al momento de descargar el archivo."),
	ERROR_BODY("66","Error reading response body"),
	ERROR_AL_GUARDAR_LA_PISTA("067","Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)."),
	DATOS_OBLIGATORIOS("068",MensajeValidation.MSJ),
	FECHAS_VALIDACION("069","La fecha de termino, no puede ser menor a la fecha de inicio."),
	ERROR_DUPLICIDAD_GRUPO("070","El grupo ya se encuentra registrado, por favor intente nuevamente.");


	public String getMessage(Object... args) {
        return String.format(this.message, args);
    }

	private String clave;
	private String message;

	ErroresEnum(String clave, String message) {
		this.clave = clave;
		this.message = message;
	}


	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getClave() {
		return clave;
	}
	
	public static class MensajeValidation {

		private MensajeValidation() {
			throw new IllegalStateException("Utility class");
		}

		public static final String MSJ = "Favor de ingresar los datos obligatorios marcados con un asterisco (*).";
		public static final String MSJREINTEGRO = "Favor de ingresar los datos obligatorios";
		
		public static final String MSJIMPORTE = "El importe debe tener como máximo 9 dígitos enteros y 2 decimales.";
		public static final String MSJINTERES = "El interés debe tener como máximo 9 dígitos enteros y 2 decimales.";
		public static final String MSJCEROIMPORTE = "El importe no puede ser cero o menor.";
		public static final String MSJCEROINTERES = "El interés no puede ser cero o menor.";

		
	}

}


