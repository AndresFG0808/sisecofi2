package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ConvenioPlantilla;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "carpetaPlantillaConvenio")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CarpetaPlantillaConvenioModel extends BaseCarpetaModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCarpetaPlantillaConvenio;

	@OneToMany(mappedBy = "carpetaPlantillaConvenioModel", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ArchivoPlantillaConvenioModel> archivos = new ArrayList<>();
	
	@JsonIgnore
	public List<ArchivoPlantillaConvenioModel> getArchivosActivos() {
	    return archivos.stream()
	                   .filter(archivo -> archivo.getArchivoBase().isEstatus())
	                   .sorted(Comparator.comparing(archivo -> archivo.getArchivoBase().getIdArchivoPlantilla()))
	                   .toList();
	    }

	@ManyToOne
	@JoinColumn(name = "carpeta_padre_id", foreignKey = @ForeignKey(name = "FK_carpeta_padre"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaConvenioModel carpetaPadre;
	
	@ManyToOne
	@JoinColumn(name = "id_carpeta_base", foreignKey = @ForeignKey(name = "FK_id_carpeta_base"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaModel carpetaBase;
	
    
    @ManyToOne
	@JoinColumn(name = "id_convenio_plantilla", foreignKey = @ForeignKey(name = "FK_id_convenio_plantilla"), nullable = false)
	@JsonIgnore
	private ConvenioPlantilla convenioPlantilla;

	public void setCarpetaPadre(CarpetaPlantillaConvenioModel carpetaPadre) {
		this.carpetaPadre = carpetaPadre; 
	}

	public void addSubCarpeta(CarpetaPlantillaConvenioModel subCarpeta) {
		subCarpetas.add(subCarpeta);
		subCarpeta.setCarpetaPadre(this); 
	}

	public void removeSubCarpeta(CarpetaPlantillaConvenioModel subCarpeta) {
		subCarpetas.remove(subCarpeta);
		subCarpeta.setCarpetaPadre(null); 
	}

	public void addArchivo(ArchivoPlantillaConvenioModel archivo) {
		archivos.add(archivo);
		archivo.setCarpetaPlantillaConvenioModel(this);
	}

	public void removeArchivo(ArchivoPlantillaConvenioModel archivo) {
		archivos.remove(archivo);
		archivo.setCarpetaPlantillaConvenioModel(null);
	}
	

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "carpeta_padre_id")
	@JsonIgnore
	private List<CarpetaPlantillaConvenioModel> subCarpetas = new ArrayList<>();

	@JsonIgnore
	public List<CarpetaPlantillaConvenioModel> getASubCarpetasActivas() {
	    return subCarpetas.stream()
	                   .filter(car -> car.getCarpetaBase().isEstatus())
	                   .toList();
	    }
	
}
