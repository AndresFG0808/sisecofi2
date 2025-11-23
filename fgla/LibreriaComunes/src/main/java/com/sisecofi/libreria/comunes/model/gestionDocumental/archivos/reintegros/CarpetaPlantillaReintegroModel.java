package com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.contratos.BaseCarpetaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "carpetaPlantillaReintegro")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CarpetaPlantillaReintegroModel extends BaseCarpetaModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCarpetaPlantillaReintegro;
	

	@OneToMany(mappedBy = "carpetaPlantillaModel", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ArchivoPlantillaReintegroModel> archivos = new ArrayList<>();
	
	@JsonIgnore
	public List<ArchivoPlantillaReintegroModel> getArchivosActivos() {
	    return archivos.stream()
	                   .filter(archivo -> archivo.getArchivoBase().isEstatus())
	                   .sorted(Comparator.comparing(archivo -> archivo.getArchivoBase().getIdArchivoPlantilla()))
	                   .toList();
	    }
	
	@ManyToOne
	@JoinColumn(name = "id_carpeta_base", foreignKey = @ForeignKey(name = "FK_id_carpeta_base"), nullable = true)
	@JsonIgnore
	private CarpetaPlantillaModel carpetaBase;
	
    
    @OneToOne
	@JoinColumn(name = "id_reintegros_asociados", foreignKey = @ForeignKey(name = "FK_id_reintegros_asociados"), nullable = false)
	@JsonIgnore
	private ReintegrosAsociadosModel reintegro;


	public void addArchivo(ArchivoPlantillaReintegroModel archivo) {
		archivos.add(archivo);
		archivo.setCarpetaPlantillaModel(this);
	}

	public void removeArchivo(ArchivoPlantillaReintegroModel archivo) {
		archivos.remove(archivo);
		archivo.setCarpetaPlantillaModel(null);
	}

	public CarpetaPlantillaReintegroModel(CarpetaPlantillaModel carpetaBase, ReintegrosAsociadosModel reintegro) {
		super();
		this.carpetaBase = carpetaBase;
		this.reintegro = reintegro;
		this.setEstatusCarpeta(true);
		this.setNivel(carpetaBase.getNivel());
		this.setObligatorio(carpetaBase.isObligatorio());
		this.setTipo(carpetaBase.getTipo());
		
	}
	
	public void setRenombrar() {
		if (reintegro != null && this.reintegro.getFechaReintegro() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = this.reintegro.getFechaReintegro().format(formatter);
            this.setNombre("Reintegro_" + fechaFormateada);
        } else {
            this.setNombre("Reintegro sin fecha");
        }
	}

	public CarpetaPlantillaReintegroModel() {
		super();
	}
	
}
