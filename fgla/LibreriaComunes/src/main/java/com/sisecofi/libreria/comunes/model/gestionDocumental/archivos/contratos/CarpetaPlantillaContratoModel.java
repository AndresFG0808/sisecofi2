package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ContratoPlantilla;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
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
@Table(name = ConstantesComunes.PREFIX_TRAB + "carpetaPlantillaContrato")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CarpetaPlantillaContratoModel extends BaseCarpetaModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCarpetaPlantillaContrato;

	

	@OneToMany(mappedBy = "carpetaPlantillaModel", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ArchivoPlantillaContratoModel> archivos = new ArrayList<>();
	
	@JsonIgnore
	public List<ArchivoPlantillaContratoModel> getArchivosActivos() {
	    return archivos.stream()
	                   .filter(archivo -> archivo.getArchivoBase().isEstatus())
	                   .sorted(Comparator.comparing(archivo -> archivo.getArchivoBase().getIdArchivoPlantilla()))
	                   .toList();
	    }

	@ManyToOne
	@JoinColumn(name = "carpeta_padre_id", foreignKey = @ForeignKey(name = "FK_carpeta_padre"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaContratoModel carpetaPadre;
	
	@ManyToOne
	@JoinColumn(name = "id_carpeta_base", foreignKey = @ForeignKey(name = "FK_id_carpeta_base"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaModel carpetaBase;
	
    
    @ManyToOne
	@JoinColumn(name = "id_contrato_plantilla", foreignKey = @ForeignKey(name = "FK_id_contrato_plantilla"), nullable = false)
	@JsonIgnore
	private ContratoPlantilla contratoPlantilla;

	public void setCarpetaPadre(CarpetaPlantillaContratoModel carpetaPadre) {
		this.carpetaPadre = carpetaPadre; 
	}

	public void addSubCarpeta(CarpetaPlantillaContratoModel subCarpeta) {
		subCarpetas.add(subCarpeta);
		subCarpeta.setCarpetaPadre(this); 
	}

	public void removeSubCarpeta(CarpetaPlantillaContratoModel subCarpeta) {
		subCarpetas.remove(subCarpeta);
		subCarpeta.setCarpetaPadre(null); 
	}

	public void addArchivo(ArchivoPlantillaContratoModel archivo) {
		archivos.add(archivo);
		archivo.setCarpetaPlantillaModel(this);
	}

	public void removeArchivo(ArchivoPlantillaContratoModel archivo) {
		archivos.remove(archivo);
		archivo.setCarpetaPlantillaModel(null);
	}
	

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "carpeta_padre_id")
	@JsonIgnore
	private List<CarpetaPlantillaContratoModel> subCarpetas = new ArrayList<>();

	@JsonIgnore
	public List<CarpetaPlantillaContratoModel> getASubCarpetasActivas() {
	    return subCarpetas.stream()
	                   .filter(car -> car.getCarpetaBase().isEstatus())
	                   .toList();
	    }
	
}
