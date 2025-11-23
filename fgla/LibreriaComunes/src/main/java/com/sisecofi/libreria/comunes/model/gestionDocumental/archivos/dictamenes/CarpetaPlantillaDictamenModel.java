package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.BaseCarpetaModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.CarpetaPlantillaConvenioModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "carpetaPlantillaDictamen")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CarpetaPlantillaDictamenModel extends BaseCarpetaModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCarpetaPlantillaDictamen;

	

	@OneToMany(mappedBy = "carpetaPlantillaModel", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ArchivoPlantillaDictamenModel> archivos = new ArrayList<>();
	
	@JsonIgnore
	public List<ArchivoPlantillaDictamenModel> getArchivosActivos() {
	    return archivos.stream()
	                   .filter(archivo -> archivo.getArchivoBase() == null || archivo.getArchivoBase().isEstatus())
	                   .sorted(Comparator.comparing(archivo -> archivo.getArchivoBase().getIdArchivoPlantilla()))
	                   .toList();
	} 

	@ManyToOne
	@JoinColumn(name = "carpeta_padre_id", foreignKey = @ForeignKey(name = "FK_carpeta_padre"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaDictamenModel carpetaPadre;
	
	@ManyToOne
	@JoinColumn(name = "id_carpeta_base", foreignKey = @ForeignKey(name = "FK_id_carpeta_base"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaModel carpetaBase;
	
    
	@JoinColumn(name = "idDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private Dictamen dictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDictamen")
	private Long idDictamen;
    


	public void setCarpetaPadre(CarpetaPlantillaDictamenModel carpetaPadre) {
		this.carpetaPadre = carpetaPadre; 
	}

	public void addSubCarpeta(CarpetaPlantillaDictamenModel subCarpeta) {
		subCarpetas.add(subCarpeta);
		subCarpeta.setCarpetaPadre(this); 
	}

	public void removeSubCarpeta(CarpetaPlantillaDictamenModel subCarpeta) {
		subCarpetas.remove(subCarpeta);
		subCarpeta.setCarpetaPadre(null); 
	}

	public void addArchivo(ArchivoPlantillaDictamenModel archivo) {
		archivos.add(archivo);
		archivo.setCarpetaPlantillaModel(this);
	}

	public void removeArchivo(ArchivoPlantillaDictamenModel archivo) {
		archivos.remove(archivo);
		archivo.setCarpetaPlantillaModel(null);
	}
	

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "carpeta_padre_id")
	@JsonIgnore
	private List<CarpetaPlantillaDictamenModel> subCarpetas = new ArrayList<>();

	@JsonIgnore
	public List<CarpetaPlantillaDictamenModel> getASubCarpetasActivas() {
	    return subCarpetas.stream()
	                   .filter(car -> car.getCarpetaBase().isEstatus())
	                   .toList();
	    }
	
}
