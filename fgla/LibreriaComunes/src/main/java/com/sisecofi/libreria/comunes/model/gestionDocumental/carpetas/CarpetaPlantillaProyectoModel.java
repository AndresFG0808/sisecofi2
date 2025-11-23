package com.sisecofi.libreria.comunes.model.gestionDocumental.carpetas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.ArchivoPlantillaProyectoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.AsociacionesModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "carpetaPlantillaProyecto")
@Getter
@Setter
@ToString
public class CarpetaPlantillaProyectoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCarpetaPlantillaProyecto;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "orden")
	private int orden;

	@Column(name = "nivel")
	private int nivel;

	@Column(name = "dato")
	private int dato;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "ruta")
	private String ruta;

	@Column(name = "obligatorio")
	private boolean obligatorio;

	@Column(name = "estatus")
	private boolean estatus;

	@Column(name = "estatus_carpeta")
	private boolean estatusCarpeta;
	
	@OneToMany(mappedBy = "carpetaPlantillaModel", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ArchivoPlantillaProyectoModel> archivos = new ArrayList<>();
	
	@JsonIgnore
	public List<ArchivoPlantillaProyectoModel> getArchivosActivos() {
	    return archivos.stream()
	                   .filter(archivo -> archivo.getArchivoBase().isEstatus())
	                   .sorted(Comparator.comparing(archivo -> archivo.getArchivoBase().getIdArchivoPlantilla()))
	                   .toList();
	}


	@ManyToOne
	@JoinColumn(name = "carpeta_padre_id", foreignKey = @ForeignKey(name = "FK_carpeta_padre"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaProyectoModel carpetaPadre;
	
	@ManyToOne
	@JoinColumn(name = "id_carpeta_base", foreignKey = @ForeignKey(name = "FK_id_carpeta_base"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaModel carpetaBase;
	
	public void actualizaEstatus() {
		if (this.carpetaBase!=null) {
			this.estatus = this.carpetaBase.isEstatus();
		}
	}
    
    @ManyToOne
	@JoinColumn(name = "id_asociacion", foreignKey = @ForeignKey(name = "FK_id_asociacion"), nullable = false)
	@JsonIgnore
	private AsociacionesModel asociacionesModel;

	public void setCarpetaPadre(CarpetaPlantillaProyectoModel carpetaPadre) {
		this.carpetaPadre = carpetaPadre; 
	}

	public void addSubCarpeta(CarpetaPlantillaProyectoModel subCarpeta) {
		subCarpetas.add(subCarpeta);
		subCarpeta.setCarpetaPadre(this); 
	}

	public void removeSubCarpeta(CarpetaPlantillaProyectoModel subCarpeta) {
		subCarpetas.remove(subCarpeta);
		subCarpeta.setCarpetaPadre(null); 
	}

	public void addArchivo(ArchivoPlantillaProyectoModel archivo) {
		archivos.add(archivo);
		archivo.setCarpetaPlantillaModel(this);
	}

	public void removeArchivo(ArchivoPlantillaProyectoModel archivo) {
		archivos.remove(archivo);
		archivo.setCarpetaPlantillaModel(null);
	}
	

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "carpeta_padre_id")
	@JsonIgnore
	private List<CarpetaPlantillaProyectoModel> subCarpetas = new ArrayList<>();

	@JsonIgnore
	public List<CarpetaPlantillaProyectoModel> getASubCarpetasActivas() {
	    return subCarpetas.stream()
	                   .filter(car -> car.getCarpetaBase().isEstatus())
	                   .sorted(Comparator.comparing(car -> car.getCarpetaBase().getIdCarpetaPlantilla()))
	                   .toList();
	    }

}
