package com.sisecofi.libreria.comunes.model.gestionDocumental.comite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivo_plantilla_comite")
@Getter
@Setter
@JsonTypeName("tipoPlantillaComite")
@EqualsAndHashCode(callSuper = false)
public class ArchivoPlantillaComiteModel extends Archivo{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asociacion_comite_plantilla", insertable = false, updatable = false)
    @JsonIgnore
    private AsociasionComitePlantillaModel asociasionComitePlantillaModel;

    @Column(name = "id_asociacion_comite_plantilla")
    private Integer idAsociacionComiteProyecto;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_archivo_plantilla", insertable = false, updatable = false)
    @JsonIgnore
    private ArchivoPlantillaModel archivoPlantillaModel;

    @Column(name = "id_archivo_plantilla")
    private Integer idArchivoPlantilla;

    @Column(name = "ruta_carpeta")
    private String rutaCarpeta;

	@Override
	public String getFase() {
		return "Comites";
	}
	
	@Override
	public boolean isObligatorio() {
		if(archivoPlantillaModel != null) {
			return archivoPlantillaModel.isObligatorio();
		}else {
			return false;
		}
	}

	@Override
	public Long getIdProyecto() {
	    if (asociasionComitePlantillaModel != null &&
	        asociasionComitePlantillaModel.getComiteProyectoModel() != null) {
	        
	        return asociasionComitePlantillaModel.getComiteProyectoModel().getIdProyecto();
	    }
	    return null;
	}

	@Override
	public String getNombreCorto() {
	    if (asociasionComitePlantillaModel != null &&
	        asociasionComitePlantillaModel.getComiteProyectoModel() != null &&
	        asociasionComitePlantillaModel.getComiteProyectoModel().getProyectoModel() != null &&
	        asociasionComitePlantillaModel.getComiteProyectoModel().getProyectoModel().getNombreCorto() != null) {
	        
	        return asociasionComitePlantillaModel.getComiteProyectoModel()
	                                             .getProyectoModel()
	                                             .getNombreCorto();
	    }
	    return "";
	}

	@Override
	public String getPlantilla() {
	    if (archivoPlantillaModel != null &&
	        archivoPlantillaModel.getCarpetaPlantillaModel() != null &&
	        archivoPlantillaModel.getCarpetaPlantillaModel().getPlantillaVigenteModel() != null &&
	        archivoPlantillaModel.getCarpetaPlantillaModel().getPlantillaVigenteModel().getNombre() != null) {
	        
	        return archivoPlantillaModel.getCarpetaPlantillaModel()
	                                    .getPlantillaVigenteModel()
	                                    .getNombre();
	    }
	    return "";
	}

}
