package com.sisecofi.proyectos.util.enums;

import com.sisecofi.libreria.comunes.util.enums.DefinitionMessage;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ErroresEnum implements DefinitionMessage { 

	ERROR_MSJ_SIN_USUARIO("001", "No se encontro usuario"),
	ERROR_DATOS_OBLIGATORIOS("002", ErroresEnum.MensajeValidation.MSJ),
	ERROR_LISTA_VACIA("003", "No existen resultados que coincidan con los criterios de búsqueda ingresados."),
	REGRESAR("004", "Se perderá la información que no haya guardado, ¿Desea regresar?"),
	ERROR_IDAGP_INCORRECTO("005", ErroresEnum.MensajeValidation.PATRON),
	ERROR_IDAGP_OCUPADO("006", "El “Id AGP” ya se encuentra registrado en un proyecto. Ingrese uno válido."),
	PROYECTO_CREADO("007", "Proyecto creado exitosamente."),
	ERROR_AL_GUARDAR("008", "Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)."),
	ERROR_AL_CONSULTAR("009", "Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_EXPORTAR("010", "Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_OBTENER_ESTATUS("011","Ocurrió un error al consultar los estatus de proyectos, favor de intentar nuevamente."),
	ERROR_AL_CONSULTAR_CATALOGOS("012", "Ocurrio un error al consultar los catalogos"),
	ERROR_AL_OBTENER_ID("013", "Ocurrió un error al obtener el id."),
	ERROR_AL_OBTENER_PROYECTO("014", "Ocurrió un error al obtener el proyecto seleccionado."),
	PROYECTO_NO_ENCONTRADO("015", "El proyecto que deseas consultar o modificar no se encuentra registrado."),
	ERROR_AL_ACTUALIZAR_PROYECTO("016", "Ocurrió un error al actualizar el proyecto seleccionado."),
	COMITE_PROYECTO_CREADO("017","Comite-Proyecto creado exitosamente."),
	COMITE_PROYECTO_OBTENIDO("018","Comite-Proyecto obtenido exitosamente."),
	COMITE_PROYECTO_ACTUALIZADO("019","Comite-Proyecto actualizado exitosamente."),
	ERROR_AL_CONSULTAR_USUARIOS("020", "Ocurrio un error al consultar los usuarios del SAT"),
	ARCHIVO_CARGADO("021","Archivo creado exitosamente."),
	ERROR_AL_ELIMINAR("022","Ocurrio un error al eliminar"),
	INFORMACION_DEL_COMITE_ELIMINADA("023","Informacion del comite eliminada exitosamente"),
	ERROR_AL_BUSCAR_ADMON_CENTRAL("024", "Alguno(s) administradores centrales seleccionados no existen"),
	FICHA_NO_ENCONTRADA("025", "La ficha que deseas consultar no se encuentra registrado."),
	COMITE_PROYECTO_NO_ENCONTRADO("026", "El Comite-proyecto que deseas consultar o modificar no se encuentra registrado."),
	ARCHIVO_NO_ENCONTRADO("027", "El Archivo que deseas consultar o modificar no se encuentra registrado."),
	PLANTILLAS_NO_ENCONTRADAS("028", "Las Plantillas que deseas consultar o modificar no se encuentran registradas."),
	ERROR_AL_DESCARGAR_ARCHIVO("029","Ocurrió un error al descargar el archivo, favor de intentar nuevamente."),
	ERROR_AL_DESCARGAR_FOLDER("030","Ocurrió un error al descargar el folder, favor de intentar nuevamente."),
	LIDER_ACTUAL_ACTIVO("031","Ya se cuenta con un líder de proyecto activo."),
	USUARIO_SAT_NO_EXISTE("032", "El líder capturado no existe en el Directorio activo."),
	ERROR_AL_AVAZAR_A_PLANEACION("033", "Para avanzar el estatus del proyecto, se requiere capturar la información obligatoria de la Ficha técnica."),
	ERROR_AL_AVAZAR_A_EJECUCUCION("034", "Para avanzar el estatus del proyecto, se requiere un plan de trabajo cargado."),
	ERROR_AL_AVAZAR_A_PROCESO_DE_CIERRE("035", "No es posible iniciar el proceso de cierre. Existen Contratos en estatus Inicial o Ejecución"),
	ERROR_AL_AVAZAR_A_CERRADO("036", "Se requiere capturar la información obligatoria en todas las secciones del proyecto."),
	AVANCE_NO_PERMITIDO("037", "El estatus al que deseas actualizar no esta en las opciones a actualizar."),
	HISTORICO_NO_DISPONIBLE("038", "El historico que deseas consultar no esta disponible para esta ficha."),
	ERROR_AL_ACTUALIZAR_ADMON_CENTRAL("039", "Es probable que algun admon central que deseas consultar no exista."),
	ERROR_AL_ACTUALIZAR("040", "Ocurrió un error al actualizar."),
	ERROR_ASIGNAR_PROYECTO("041","Se requiere al menos un dato para buscar"),
	ERROR_PROYECTO_NO_ENCONTRADO("042","Proyecto no encontrado"),
	ERROR_AL_GUARDAR_LA_PISTA("043","Ocurrió un error al guardar el registro, favor de intentar nuevamente (PA01)."),
	ERROR_AL_CONSULTAR_LA_PISTA("044","Ocurrió un error al consultar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_IMPRIMIR_LA_PISTA("045","Ocurrió un error al exportar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_BORRAR_LA_PISTA("046","Ocurrió un error al borrar la información, favor de intentar nuevamente (PA01)."),
	ERROR_AL_PISTA_DESCONOCIDO("047","Ocurrió un error, favor de intentar nuevamente (PA01)."),
	NOMBRE_NO_EDITABLE("048","No es posible actualizar el nombre del proyecto si el estatus es en Ejecución."),
	NOMBRE_CORTO_NO_EDITABLE("049","El campo nombre corto no se puede modificar."),
	HISTORICO_NO_ENCONTRADO("050", "El historico que deseas consultar o modificar no se encuentra registrado."),
	ALINEACION_NO_ENCONTRADA("051", "La alineacion que deseas consultar o modificar no se encuentra registrado."),
	ERROR_AL_GUARDAR_PROYECTO_REGISTRADO("052", "Ya existe un comite con este id proyecto (PA01)."),
	ERROR_FECHA_INICIO_CON_LIDER("053", "La fecha de inicio del líder debe ser el siguiente día hábil después de la fecha de fin del último líder."),
	ERROR_FECHA_INICIO("054", "La fecha de inicio del líder debe ser un día hábil y no puede ser posterior a la actual."),
	ERROR_FECHA_FIN("055", "La fecha de fin del líder no puede estar vacía ni ser posterior a la actual."),
	FECHA_NO_PERMITIDA("056", "La fecha es diferente al dia de hoy (PA01)."),
	COMITE_PROYECTO_NO_VALIDO("057", " ComiteProyecto no cumple con ninguna de las condiciones. (PA01)."),
	DECIMALES_INCORRECTOS("058", " El 'monto', 'monto autorizado' deben tener hasta 2 decimales y 'tipo de cambio' 4 decimales."),
	ALINEACION_NO_DISPONIBLE("059", "No hay alineaciones registradas para esta ficha."),
	LIDER_UNICO("060", "Solo un líder debe estar activo."),
	ASOCIACION_NO_ENCONTRADA("061", "La asociacion que deseas consultar o modificar no se encuentra registrado."),
	USUARIO_NO_ENCONTRADO("062", "El usuario que deseas consultar no existe."),
	ERROR_ARCHIVOS_CARGADOS("063", "No se puede realizar la acción ya que la plantilla contiene documentos cargados. Por favor, primero elimine los documentos cargados en la sección “Gestión documental” para continuar." ),
	ERROR_FECHA_ASIGNACION("064", "La fecha de asignación no puede ser mayor que la fecha del día actual." ),
	ESTATUS_DE_CONSULTA_OK("065", "OK"),
	ERROR_AL_ELIMINAR_COMITE_POR_ARCHIVOS("066","El comite que desea eliminar contiene archivos asociados, eliminelos e intente de nuevo"),
	ERROR_FALTAN_MAPAS("067","La ficha debe tener por lo menos un mapa especifico y un mapa estrategico"),	
	ERROR_AL_ELIMINAR_ARCHIVO("068","Ocurrió un error al borrar el archivo, favor de intentar nuevamente (PA01)"),
	ERROR_AL_GUARDAR_ARCHIVO("069", "Ocurrió un error al guardar el registro, campo 'no aplica' debe ser diferente a 'estatus'."),
	ERROR_NOMBRE_ARCHIVO_DUPLICADO("070", "Ocurrió un error al guardar el registro, el nombre de archivo ya existe'."),
	ERROR_ID_CARPETA("0071", "Ocurrió un error al guardar el registro, el idCarpeta ya esta registrado con otro comite'."),
	ERROR_ARCHIVO("072", "Ocurrió un error al convertir el archivo'."),
	ERROR_USUARIO_NO_ENCONTRADO("072", "Ocurrió un error al tratar de obtener la sesion."),
	ERROR_ARCHIVO_NO_CARGADO("072", "Ocurrió un error al tratar de cargar el archivo."),
	ERROR_RUTA_OCUPADA("072", "El archivo no se pudo cargar porque la ruta ya existe. Por favor, sube un archivo con un nombre diferente."),
	ERROR_AL_ELIMINAR_ARCHIVO_CLOUD("068","Ocurrió un error al borrar el archivo en la nube favor de intentar nuevamente (PA01)"),
	CATALOGO_NO_ENCONTRADO("068","No se encontro el catalogo"),
	PLANTILLA_YA_ASOCIADA("068","La plantilla ya se encuentra asociada al proyecto"),
	NO_PROCESO_CIERRE("069","No puedes continuar si el “Estatus del proyecto” está en “Proceso de cierre”."),
	CIERRE_NOT_FOUND("070","No se encontro un cierre proyecto"),
	NO_PLANTILLA_DOCUMENTAL("071","El proyecto no tiene asignado ninguna plantilla documental."),
	NO_ENTREGABLES("072","Aún existen entregables con estatus “Pendiente”. Intente nuevamente."),
	FECHAS_MAYOR_PROYECTO("073","Las fechas no pueden ser menor a la fecha de inicio del proyecto o mayor a la fecha fin del proyecto."),
	PLANTILLA_CARGA_NO_ENCONTRADA("074", "La plantilla no se encuntra"),
	ERROR_PLANTILLA_ARCHIVO("075", "No se puede leer el archivo"),
	ERROR_PLANTILLA_NO_ENCONTRADA("076", "No se encontro la plantillas"),
	FECHAS_ERROR_PROYECTO("077","Las fechas no pueden ser menor a la fecha de inicio del proyecto o mayor a la fecha fin del proyecto."),
	DATOS_OBLIGATORIOS_CIERRE("078","Favor de ingresar los datos obligatorios "),
	ARCHIVO_EXISTENTE("079","El archivo ya contiene un documento, eliminelo antes de hacer un registro"),
	ERROR_TIPO_ARCHIVO("080","Especifique un tipo de archivo válido [\"pdf\" o \"excel\"]"),
	ACCESO_DENEGADO("081", "Access Denied"),
	CONEXION_PERDIDA("082", "Se perdió la conexión con Sat Cloud, favor de intentar nuevamente"),
	ERROR_URL_POWERBY("083", "Ocurrió un error al cargar el vínculo, favor de contactar al administrador del sistema."),
	ERROR_COPIA_ARCHIVO("084","Error al copiar la tarea"),
	ERROR_CANCELAR_PROYECTO("085", "Para cancelar el proyecto, se requiere cancelar contratos y dictámenes relacionados, favor de validar.");
	
	



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
		public static final String PATRON = "El dato ingresado en “Id AGP” es incorrecto.";
		public static final String LONGITUD_NOMBRE_CORTO = "El nombre corto puede tener una longitud maxima de 50 posiciones.";
		public static final String LONGITUD_NOMBRE_COMPLETO = "El nombre completo puede tener una longitud maxima de 250 posiciones.";
		public static final String LONGITUD_LISTA_PROVEEDOR = "La lista de proveedores asignados no debe estar vacia.";
		public static final String LONGITUD_COMENTARIO_PROVEEDOR = "El comentario no debe estar vacia.";
	}

}
