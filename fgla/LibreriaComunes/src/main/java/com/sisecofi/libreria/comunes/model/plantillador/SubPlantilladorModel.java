package com.sisecofi.libreria.comunes.model.plantillador;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

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
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "subPlantillador")
@Getter
@Setter
public class SubPlantilladorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSubPlantillador;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "status", nullable = false)
	private boolean status;

	@Column(name = "fechaModificacion")
	private LocalDateTime fechaModificacion;

	@Column(name = "comentarios")
	private String comentarios;

	@Column(name = "version")
	private String version;

	@ManyToOne
	@JoinColumn(name = "id_tipo_plantillador", foreignKey = @ForeignKey(name = "FK_id_tipo_plantillador"), nullable = true)
	private CatTipoPlantillador catTipoPlantillador;

	
	@OneToMany(mappedBy = "subPlantilladorModel", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<SubPlantilladorDatosModel> subPlantillas;

	
	@JsonIgnore
	public SubPlantilladorDatosModel getContenidoByCatSubTipoPlantillador(Long idSubTipoPlantillador) {
	    if (subPlantillas == null || subPlantillas.isEmpty()) {
	        return new SubPlantilladorDatosModel(); 
	    }
 
	    return subPlantillas.stream()
	        .filter(contenido -> contenido.getCatSubTipoPlantillador() != null && 
	                             contenido.getCatSubTipoPlantillador().getIdSubTipoPlantillador().equals(idSubTipoPlantillador))
	        .findFirst()
	        .orElse(new SubPlantilladorDatosModel());
	}


	@Override
	public String toString() {
		return "| id subplantillador: " + idSubPlantillador  + "| Nombre: " + nombre + "| Comentarios: " + comentarios  + "| Fecha de modificación: " 
	+ fechaModificacion + "| Estatus: "	+ status;
	}
	
	
}

