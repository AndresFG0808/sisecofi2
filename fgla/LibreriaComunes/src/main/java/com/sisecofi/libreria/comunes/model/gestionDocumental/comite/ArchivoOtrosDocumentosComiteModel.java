package com.sisecofi.libreria.comunes.model.gestionDocumental.comite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivo_otros_documentos_comite")
@Getter
@Setter
@JsonTypeName("tipoComite")
@EqualsAndHashCode(callSuper = false)
public class ArchivoOtrosDocumentosComiteModel extends Archivo {

	@Column(name = "otros_documentos_interno")
	private Boolean otrosDocumentosInterno;

	@JoinColumn(name = "id_comite_proyecto", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ComiteProyectoModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private ComiteProyectoModel comiteProyectoModel;

	@Column(name = "id_comite_proyecto")
	private Integer idComiteProyecto;

	@JoinColumn(name = "id_carpeta_plantilla", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CarpetaPlantillaModel.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private CarpetaPlantillaModel carpetaPlantillaModel;

	@Column(name = "id_carpeta_plantilla")
	private Integer idCarpetaPlantilla;

	@Override
	public String getFase() {
		return "Información de comité";
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_archivo_plantilla", insertable = false, updatable = false)
	@JsonIgnore
	private ArchivoPlantillaModel archivoPlantillaModel;

	@Column(name = "id_archivo_plantilla")
	private Integer idArchivoPlantilla;

	@Override
	public Long getIdProyecto() {
		if (comiteProyectoModel != null) {
			return comiteProyectoModel.getIdProyecto();
		}
		return null;
	}

	@Override
	public String getNombreCorto() {
		if (comiteProyectoModel != null && comiteProyectoModel.getProyectoModel() != null
				&& comiteProyectoModel.getProyectoModel().getNombreCorto() != null) {

			return comiteProyectoModel.getProyectoModel().getNombreCorto();
		}
		return "";
	}

	@Override
	public String getPlantilla() {
		return "";
	}
}
