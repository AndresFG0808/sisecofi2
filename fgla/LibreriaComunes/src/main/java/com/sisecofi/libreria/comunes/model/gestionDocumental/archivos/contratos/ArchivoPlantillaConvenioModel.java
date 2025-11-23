package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos;

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
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivoPlantillaConvenio")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonTypeName("tipoPlantillaConvenio")
@EqualsAndHashCode(callSuper = false)
public class ArchivoPlantillaConvenioModel extends Archivo {

	@ManyToOne
	@JoinColumn(name = "id_carpeta_plantilla_convenio", foreignKey = @ForeignKey(name = "FK_id_carpeta_plantilla_convenio"))
	@JsonIgnore
	private CarpetaPlantillaConvenioModel carpetaPlantillaConvenioModel;

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
		return "Ejecución";
	}

	@Override
	public Long getIdProyecto() {
		if (carpetaPlantillaConvenioModel != null && carpetaPlantillaConvenioModel.getConvenioPlantilla() != null
				&& carpetaPlantillaConvenioModel.getConvenioPlantilla().getConvenio() != null
				&& carpetaPlantillaConvenioModel.getConvenioPlantilla().getConvenio().getContratoModel() != null
				&& carpetaPlantillaConvenioModel.getConvenioPlantilla().getConvenio().getContratoModel()
						.getProyecto() != null) {

			return carpetaPlantillaConvenioModel.getConvenioPlantilla().getConvenio().getContratoModel().getProyecto()
					.getIdProyecto();
		}
		return null;
	}

	@Override
	public String getNombreCorto() {
		if (carpetaPlantillaConvenioModel != null && carpetaPlantillaConvenioModel.getConvenioPlantilla() != null
				&& carpetaPlantillaConvenioModel.getConvenioPlantilla().getConvenio() != null
				&& carpetaPlantillaConvenioModel.getConvenioPlantilla().getConvenio().getContratoModel() != null
				&& carpetaPlantillaConvenioModel.getConvenioPlantilla().getConvenio().getContratoModel()
						.getProyecto() != null
				&& carpetaPlantillaConvenioModel.getConvenioPlantilla().getConvenio().getContratoModel().getProyecto()
						.getNombreCorto() != null) {

			return carpetaPlantillaConvenioModel.getConvenioPlantilla().getConvenio().getContratoModel().getProyecto()
					.getNombreCorto();
		}
		return "";
	}

	@Override
	public String getPlantilla() {
		if (carpetaPlantillaConvenioModel != null && carpetaPlantillaConvenioModel.getCarpetaBase() != null
				&& carpetaPlantillaConvenioModel.getCarpetaBase().getPlantillaVigenteModel() != null
				&& carpetaPlantillaConvenioModel.getCarpetaBase().getPlantillaVigenteModel().getNombre() != null) {

			return carpetaPlantillaConvenioModel.getCarpetaBase().getPlantillaVigenteModel().getNombre();
		}
		return "";
	}

}
