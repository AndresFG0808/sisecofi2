package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivoPlantillaReintegro")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonTypeName("tipoPlantillaReintegro")
@EqualsAndHashCode(callSuper = false)
public class ArchivoPlantillaReintegroModel extends Archivo {

	@ManyToOne
	@JoinColumn(name = "id_carpeta_plantilla_reintegro", foreignKey = @ForeignKey(name = "FK_id_carpeta_plantilla_reintegro"))
	@JsonIgnore
	private CarpetaPlantillaReintegroModel carpetaPlantillaModel;

	@ManyToOne
	@JoinColumn(name = "id_archivo_base", foreignKey = @ForeignKey(name = "FK_id_archivo_base"), nullable = true)
	@JsonIgnore
	private ArchivoPlantillaModel archivoBase;

	public void actualizaEstatus() {
		if (this.archivoBase != null) {
			this.estatus = this.archivoBase.isEstatus();
		}
	}

	@Override
	public String getFase() {
		return "Reintegros";
	}

	@Override
	public Long getIdProyecto() {
		if (carpetaPlantillaModel != null && carpetaPlantillaModel.getReintegro() != null
				&& carpetaPlantillaModel.getReintegro().getContratoModel() != null
				&& carpetaPlantillaModel.getReintegro().getContratoModel().getProyecto() != null) {

			return carpetaPlantillaModel.getReintegro().getContratoModel().getProyecto().getIdProyecto();
		}
		return null;
	}

	@Override
	public String getNombreCorto() {
		if (carpetaPlantillaModel != null && carpetaPlantillaModel.getReintegro() != null
				&& carpetaPlantillaModel.getReintegro().getContratoModel() != null
				&& carpetaPlantillaModel.getReintegro().getContratoModel().getProyecto() != null
				&& carpetaPlantillaModel.getReintegro().getContratoModel().getProyecto().getNombreCorto() != null) {

			return carpetaPlantillaModel.getReintegro().getContratoModel().getProyecto().getNombreCorto();
		}
		return "";
	}

	@Override
	public String getPlantilla() {
		if (carpetaPlantillaModel != null && carpetaPlantillaModel.getCarpetaBase() != null
				&& carpetaPlantillaModel.getCarpetaBase().getPlantillaVigenteModel() != null
				&& carpetaPlantillaModel.getCarpetaBase().getPlantillaVigenteModel().getNombre() != null) {

			return carpetaPlantillaModel.getCarpetaBase().getPlantillaVigenteModel().getNombre();
		}
		return "";
	}

}
