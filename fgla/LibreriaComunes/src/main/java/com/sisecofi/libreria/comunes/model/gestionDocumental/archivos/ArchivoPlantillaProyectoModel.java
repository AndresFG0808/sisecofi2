package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.model.gestionDocumental.carpetas.CarpetaPlantillaProyectoModel;
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
@Table(name =ConstantesComunes.PREFIX_TRAB + "archivoPlantillaProyecto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonTypeName("tipoPlantilla")
@EqualsAndHashCode(callSuper = false)
public class ArchivoPlantillaProyectoModel extends Archivo{
    
    @ManyToOne
	@JoinColumn(name = "id_carpeta_plantilla_proyecto", foreignKey = @ForeignKey(name = "FK_id_carpeta_plantilla_proyecto"))
	@JsonIgnore
	private CarpetaPlantillaProyectoModel carpetaPlantillaModel;
    
    @ManyToOne
	@JoinColumn(name = "id_archivo_base", foreignKey = @ForeignKey(name = "FK_id_archivo_base"), nullable = true)
	@JsonIgnore
	private ArchivoPlantillaModel archivoBase;
    
    public void actualizaEstatus() {
    	if (this.archivoBase!= null) {
    		this.estatus = this.archivoBase.isEstatus();	
    	}
	}
    
    @Override
    public String getFase() {
        if (carpetaPlantillaModel != null &&
            carpetaPlantillaModel.getAsociacionesModel() != null &&
            carpetaPlantillaModel.getAsociacionesModel().getPlantillaVigenteModel() != null &&
            carpetaPlantillaModel.getAsociacionesModel().getPlantillaVigenteModel().getCatFaseProyecto() != null) {
            
            return carpetaPlantillaModel.getAsociacionesModel()
                                        .getPlantillaVigenteModel()
                                        .getCatFaseProyecto()
                                        .getNombre();
        }
        return "";
    }

    @Override
    public Long getIdProyecto() {
        if (carpetaPlantillaModel != null &&
            carpetaPlantillaModel.getAsociacionesModel() != null &&
            carpetaPlantillaModel.getAsociacionesModel().getProyectoModel() != null) {

            return carpetaPlantillaModel.getAsociacionesModel()
                                        .getProyectoModel()
                                        .getIdProyecto();
        }
        return null;
    }

    @Override
    public String getNombreCorto() {
        if (carpetaPlantillaModel != null &&
            carpetaPlantillaModel.getAsociacionesModel() != null &&
            carpetaPlantillaModel.getAsociacionesModel().getProyectoModel() != null) {

            return carpetaPlantillaModel.getAsociacionesModel()
                                        .getProyectoModel()
                                        .getNombreCorto();
        }
        return "";
    }

    @Override
    public String getPlantilla() {
        if (archivoBase != null &&
            archivoBase.getCarpetaPlantillaModel() != null &&
            archivoBase.getCarpetaPlantillaModel().getPlantillaVigenteModel() != null) {

            return archivoBase.getCarpetaPlantillaModel()
                              .getPlantillaVigenteModel()
                              .getNombre();
        }
        return "";
    }

    

}
