package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoOtroDocumentoFaseConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.ArchivoPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoOtroDocumentoFaseDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoOtroDocumentoFaseReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.ArchivoPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoOtrosDocumentosComiteModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({ @JsonSubTypes.Type(value = ArchivoPlantillaProyectoModel.class, name = "tipoPlantilla"),
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoFaseModel.class, name = "tipoFase"),
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoProyectoModel.class, name = "tipoProyecto"),
		@JsonSubTypes.Type(value = ArchivoPlantillaContratoModel.class, name = "tipoPlantillaContrato"),
		@JsonSubTypes.Type(value = ArchivoPlantillaComiteModel.class, name = "tipoPlantillaComite"),
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoFaseContratoModel.class, name = "tipoFaseContrato"),
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoContratoModel.class, name = "tipoContrato"),
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoConvenioModel.class, name = "tipoConvenio"),
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoFaseConvenioModel.class, name = "tipoFaseConvenio"),
		@JsonSubTypes.Type(value = ArchivoPlantillaConvenioModel.class, name = "tipoPlantillaConvenio"),
		@JsonSubTypes.Type(value = ArchivoPlantillaDictamenModel.class, name = "tipoPlantillaDictamen"),
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoFaseDictamenModel.class, name = "tipoFaseDictamen"),
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoDictamenModel.class, name = "tipoDictamen"),
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoReintegroModel.class, name = "tipoReintegro") ,
		@JsonSubTypes.Type(value = ArchivoOtroDocumentoFaseReintegroModel.class, name = "tipoFaseReintegro"),
		@JsonSubTypes.Type(value = ArchivoPlantillaReintegroModel.class, name = "tipoPlantillaReintegro"),
		@JsonSubTypes.Type(value = ArchivoOtrosDocumentosComiteModel.class, name = "tipoComite")})
public abstract class Archivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "nivel")
	private int nivel;

	@Column(name = "orden")
	private int orden;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "obligatorio")
	private boolean obligatorio;

	@Column(name = "no_aplica")
	private boolean noAplica;

	@Column(name = "estatus")
	protected boolean estatus;

	@Column(name = "cargado")
	private boolean cargado;

	@Column(name = "ruta")
	private String ruta;

	@Column(name = "tamano_mb")
	private Double tamanoMb;

	@Column(name = "fecha_modificacion")
	private LocalDateTime fechaModificacion;

	@Column(name = "justificacion")
	private String justificacion;

	@Column(name = "carpeta")
	private String carpeta;

	@JsonProperty("extension")
	public String getExtension() {
		return obtenerExtension(ruta);
	}
	
	public void setType(String type) {
		this.type = type;
	}

	private String obtenerExtension(String ruta) {
		if(ruta == null) {
			return null;
		}
		int indicePunto = ruta.lastIndexOf(".");
		String textoAntesDelPunto = "";
		if (indicePunto != -1 && indicePunto < ruta.length() -1) {
			textoAntesDelPunto = ruta.substring(indicePunto + 1);
		}
		return textoAntesDelPunto;
	}
	
	

	@Column(name = "fechaDocumento")
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDateTime fechaDocumento;

	@Column(name = "numeroPaginas")
	private Integer numeroPaginas;

	@Column(name = "estatusDocumento")
	private String estatusDocumento;

	@Transient
	private String type;

	public abstract String getFase();
	
	public abstract Long getIdProyecto();
	
	public abstract String getNombreCorto();
	
	public abstract String getPlantilla();

	public String getEstatusDocumento() {
	    if (estatusDocumento == null || estatusDocumento.isEmpty()) {
	        if (noAplica) {
	            return "No aplica";
	        } else if (cargado) {
	            return "Pendiente";
	        } else {
	            return "Sin documento";
	        }
	    }
	    return estatusDocumento;
	}

	
	@JsonProperty("estatusGestion")
	public Boolean getEstatusGestion() {
		return estatusDocumento == null || estatusDocumento.isEmpty();			
	}

	public void actualizaObligatorio(boolean aplica, boolean obligatorioM) {
    	if (!aplica) {
    		this.obligatorio= obligatorioM;
    	}else {
    		this.obligatorio = false;
    	}
    }
	
	@JsonProperty("type")
	public String getType() {
	    return switch (this.getClass().getSimpleName()) {
	        case "ArchivoPlantillaProyectoModel" -> "tipoPlantilla";
	        case "ArchivoOtroDocumentoFaseModel" -> "tipoFase";
	        case "ArchivoOtroDocumentoProyectoModel" -> "tipoProyecto";
	        case "ArchivoPlantillaContratoModel" -> "tipoPlantillaContrato";
	        case "ArchivoPlantillaComiteModel" -> "tipoPlantillaComite";
	        case "ArchivoOtroDocumentoFaseContratoModel" -> "tipoFaseContrato";
	        case "ArchivoOtroDocumentoContratoModel" -> "tipoContrato";
	        case "ArchivoOtroDocumentoConvenioModel" -> "tipoConvenio";
	        case "ArchivoOtroDocumentoFaseConvenioModel" -> "tipoFaseConvenio";
	        case "ArchivoPlantillaConvenioModel" -> "tipoPlantillaConvenio";
	        case "ArchivoPlantillaDictamenModel" -> "tipoPlantillaDictamen";
	        case "ArchivoOtroDocumentoFaseDictamenModel" -> "tipoFaseDictamen";
	        case "ArchivoOtroDocumentoDictamenModel" -> "tipoDictamen";
	        case "ArchivoOtroDocumentoReintegroModel" -> "tipoReintegro";
	        case "ArchivoOtroDocumentoFaseReintegroModel" -> "tipoFaseReintegro";
	        case "ArchivoPlantillaReintegroModel" -> "tipoPlantillaReintegro";
	        case "ArchivoOtrosDocumentosComiteModel" -> "tipoComite";
	        default -> "unknownType";
	    };
	}
}
