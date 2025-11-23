package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name =ConstantesComunes.PREFIX_TRAB + "archivoPlantillaDictamen")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonTypeName("tipoPlantillaDictamen")
@EqualsAndHashCode(callSuper = false)
public class ArchivoPlantillaDictamenModel extends Archivo{
    
    @JoinColumn(name = "id_archivo_base", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ArchivoPlantillaModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ArchivoPlantillaModel archivoBase;

	@Column(name = "id_archivo_base")
	private Integer idArchivoBase;
    
    public void actualizaEstatus() {
    	if (this.archivoBase!= null) {
    		this.estatus = this.archivoBase.isEstatus();	
    	}
	}
    
    @ManyToOne
	@JoinColumn(name = "id_carpeta_plantilla_dictamen", foreignKey = @ForeignKey(name = "FK_id_carpeta_plantilla_dictamen"))
	@JsonIgnore
	private CarpetaPlantillaDictamenModel carpetaPlantillaModel;
    
    @Override
    public String getFase() {
    	return "Verificación";
    }
    
    @Override
    public String getPlantilla() {
    	if(archivoBase != null) {
    		return archivoBase.getCarpetaPlantillaModel().getPlantillaVigenteModel().getNombre();
    	}else {
    		return "";
    	}
    }

    @Override
    public Long getIdProyecto() {
        if (carpetaPlantillaModel == null ||
            carpetaPlantillaModel.getDictamen() == null ||
            carpetaPlantillaModel.getDictamen().getContratoModel() == null ||
            carpetaPlantillaModel.getDictamen().getContratoModel().getProyecto() == null) {
            return null;
        }
        return carpetaPlantillaModel.getDictamen().getContratoModel().getProyecto().getIdProyecto();
    }

    @Override
    public String getNombreCorto() {
        if (carpetaPlantillaModel == null ||
            carpetaPlantillaModel.getDictamen() == null ||
            carpetaPlantillaModel.getDictamen().getContratoModel() == null ||
            carpetaPlantillaModel.getDictamen().getContratoModel().getProyecto() == null) {
            return null;
        }
        return carpetaPlantillaModel.getDictamen().getContratoModel().getProyecto().getNombreCorto();
    }

}
