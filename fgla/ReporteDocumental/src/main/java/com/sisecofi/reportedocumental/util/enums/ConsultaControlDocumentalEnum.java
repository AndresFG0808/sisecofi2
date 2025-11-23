package com.sisecofi.reportedocumental.util.enums;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public enum ConsultaControlDocumentalEnum {	
	SIN_ARCHIVO(0, ""), 
	ARCHIVO_PROYECTO(1, Constanes.RUTA_ARCHIVO_PROYECTO),
	ARCHIVO_OTRO_DOCUMENTO_PROYECTO(2, Constanes.RUTA_ARCHIVO_OTRO_PROYECTO),
	ARCHIVO_OTRO_DOCUMENTO_FASE_PROYECTO(3,Constanes.RUTA_ARCHIVO_FASE_PROYECTO),
	ARCHIVO_PLANTILLA_COMITE(4, Constanes.RUTA_ARCHIVO_COMITE),
	ARCHIVO_PLANTILLA_COMITE_OTRO(5, Constanes.RUTA_ARCHIVO_OTROS_COMITE),
	ARCHIVO_PLANTILLA_CONTRATO(6, Constanes.RUTA_ARCHIVO_CONTRATO),
	ARCHIVO_PLANTILLA_CONTRATO_FASE(7, Constanes.RUTA_OTRO_DOCUMENTO_FASE), 
	ARCHIVO_PLANTILLA_CONTRATO_OTRO(8, Constanes.RUTA_OTRO_DOCUMENTO_FASE),
	ARCHIVO_PLANTILLA_CONVENIO(9, Constanes.RUTA_ARCHIVO_CONVENIO),
	ARCHIVO_PLANTILLA_CONVENIO_FASE(10,Constanes.RUTA_ARCHIVO_FASE_CONVENIO ),
	ARCHIVO_PLANTILLA_CONVENIO_OTRO(11,Constanes.RUTA_ARCHIVO_OTRO_CONVENIO),
	ARCHIVO_PLANTILLA_REINTEGRO(12, Constanes.RUTA_ARCHIVO_REINTREGO),
	ARCHIVO_PLANTILLA_REINTEGRO_FASE(13, Constanes.RUTA_ARCHIVO_FASE_REINTREGO),
	ARCHIVO_PLANTILLA_REINTEGRO_OTRO(14, Constanes.RUTA_ARCHIVO_OTRO_REINTREGO),
	ARCHIVO_PLANTILLA_DICTAMEN(15,  Constanes.RUTA_PLATILLA_DICTAMEN),
	ARCHIVO_PLANTILLA_DICTAMEN_FASE(16,Constanes.RUTA_ARCHIVO_DICTAMEN ),
	ARCHIVO_PLANTILLA_DICTAMEN_OTRO(17, Constanes.RUTA_ARCHIVO_FASE_DICTAMEN),
	ARCHIVO_FACTUTA(18, Constanes.RUTA_PLATILLA_DICTAMEN),
	ARCHIVO_NOTAS(19,Constanes.RUTA_PLATILLA_DICTAMEN);
	

	private int id;
	private String consulta;

	ConsultaControlDocumentalEnum(int id, String consulta) {
		this.id = id;
		this.consulta = consulta;
	}

	public int getId() {
		return id;
	}

	public String getConsulta() {
		return consulta;
	}

	public String obtenerConsulta(int identificador) {
		for (ConsultaControlDocumentalEnum valor : values()) {
			if (identificador == valor.getId()) {
				return valor.getConsulta();
			}
		}
		return SIN_ARCHIVO.getConsulta();
	}
	
	private final class Constanes{
		public static final String RUTA_ARCHIVO_PROYECTO = "select a.ruta from sisecofi.sscft_archivo_plantilla_proyecto a where a.id=:id";		
		public static final String RUTA_ARCHIVO_OTRO_PROYECTO = "select a.ruta from sisecofi.sscft_archivo_otro_documento_proyecto a where a.id=:id";
		public static final String RUTA_ARCHIVO_FASE_PROYECTO = "select a.ruta from sisecofi.sscft_archivo_otro_documento_fase a where a.id=:id";		
		public static final String RUTA_ARCHIVO_COMITE ="select a.ruta from sisecofi.sscft_archivo_plantilla_comite a where a.id=:id";	
		public static final String RUTA_ARCHIVO_OTROS_COMITE ="select a.ruta from sisecofi.sscft_archivo_otros_documentos_comite a where a.id=:id";		
		public static final String RUTA_ARCHIVO_CONTRATO ="select a.ruta from sisecofi.sscft_archivo_plantilla_contrato a where a.id=:id";		
		public static final String RUTA_OTRO_DOCUMENTO_FASE = "select a.ruta from sisecofi.sscft_archivo_plantilla_dictamen a where a.id=:id";		
		public static final String RUTA_ARCHIVO_CONVENIO ="select a.ruta from sisecofi.sscft_archivo_plantilla_convenio a where a.id=:id";
		public static final String RUTA_ARCHIVO_FASE_CONVENIO ="select a.ruta from sisecofi.sscft_archivo_otro_documento_fase_convenio a where a.id=:id";
		public static final String RUTA_ARCHIVO_OTRO_CONVENIO ="select a.ruta from sisecofi.sscft_archivo_otro_documento_convenio a where a.id=:id";		
		public static final String RUTA_ARCHIVO_REINTREGO ="select a.ruta from sisecofi.sscft_archivo_plantilla_reintegro a where a.id=:id";
		public static final String RUTA_ARCHIVO_FASE_REINTREGO ="select a.ruta from sisecofi.sscft_archivo_otro_documento_fase_reintegro a where a.id=:id";
		public static final String RUTA_ARCHIVO_OTRO_REINTREGO ="select a.ruta from sisecofi.sscft_archivo_otro_documento_reintegro a where a.id=:id";		
		public static final String RUTA_ARCHIVO_DICTAMEN ="select a.ruta from sisecofi.sscft_archivo_otro_documento_fase_dictamen a where a.id=:id";
		public static final String RUTA_ARCHIVO_FASE_DICTAMEN ="select a.ruta from sisecofi.sscft_archivo_otro_documento_dictamen a where a.id=:id";		
		public static final String RUTA_PLATILLA_DICTAMEN = "select a.ruta from sisecofi.sscft_archivo_plantilla_dictamen a where a.id=:id";
		private Constanes(){}
	}

}
