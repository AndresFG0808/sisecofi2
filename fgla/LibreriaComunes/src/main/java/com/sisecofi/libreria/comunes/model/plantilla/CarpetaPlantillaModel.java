package com.sisecofi.libreria.comunes.model.plantilla;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "carpetaPlantilla")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarpetaPlantillaModel implements Comparable<CarpetaPlantillaModel> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCarpetaPlantilla;

	@ManyToOne
	@JoinColumn(name = "id_plantilla_vigente", foreignKey = @ForeignKey(name = "FK_id_plantilla_vigente"), nullable = false)
	@JsonIgnore
	private PlantillaVigenteModel plantillaVigenteModel;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "orden")
	private int orden;

	@Column(name = "nivel")
	private int nivel;

	@EqualsAndHashCode.Include
	@Column(name = "dato")
	private int dato;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "obligatorio")
	private boolean obligatorio;

	@Column(name = "estatus")
	private boolean estatus;

	
	@OneToMany(mappedBy = "carpetaPlantillaModel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ArchivoPlantillaModel> archivos = new ArrayList<>();
	
	public List<ArchivoPlantillaModel> getArchivos() {
	    return archivos.stream()
	            .sorted(Comparator.comparing(
	                    ArchivoPlantillaModel::getIdArchivoPlantilla,
	                    Comparator.nullsLast(Comparator.naturalOrder()) 
	            ))
	            .toList(); 
	}


	@ManyToOne
	@JoinColumn(name = "carpeta_padre_id", foreignKey = @ForeignKey(name = "FK_carpeta_padre"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaModel carpetaPadre;

	public void setCarpetaPadre(CarpetaPlantillaModel carpetaPadre) {
		this.carpetaPadre = carpetaPadre; // Asignar la carpeta padre
	}

	public void addSubCarpeta(CarpetaPlantillaModel subCarpeta) {
		subCarpetas.add(subCarpeta);
		subCarpeta.setCarpetaPadre(this); // Establecer la relación con la carpeta padre
	}

	public void removeSubCarpeta(CarpetaPlantillaModel subCarpeta) {
		subCarpetas.remove(subCarpeta);
		subCarpeta.setCarpetaPadre(null); // Limpiar la relación con la carpeta padre
	}

	public void addArchivo(ArchivoPlantillaModel archivo) {
		archivos.add(archivo);
		archivo.setCarpetaPlantillaModel(this);
	}

	public void removeArchivo(ArchivoPlantillaModel archivo) {
		archivos.remove(archivo);
		archivo.setCarpetaPlantillaModel(null);
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "carpeta_padre_id")
	private List<CarpetaPlantillaModel> subCarpetas = new ArrayList<>();
	
	public List<CarpetaPlantillaModel> getSubCarpetas() {
	if (idCarpetaPlantilla==null) {
		return subCarpetas;
	}else {
		return subCarpetas.stream()
	    		.sorted(Comparator.comparing(
	    				CarpetaPlantillaModel::getIdCarpetaPlantilla,
	                    Comparator.nullsLast(Comparator.naturalOrder()) 
	            ))
	            .toList(); 
	}
	    
	}


	@Override
	public int compareTo(CarpetaPlantillaModel o) {
        return Integer.compare(o.dato, this.dato);
    }

}
