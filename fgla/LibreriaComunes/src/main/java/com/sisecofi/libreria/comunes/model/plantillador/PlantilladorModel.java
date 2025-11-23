package com.sisecofi.libreria.comunes.model.plantillador;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import com.sisecofi.libreria.comunes.util.anotaciones.CampoReporte;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "plantillador")
@Getter
@Setter
public class PlantilladorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPlantillador;

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

	@Column(name = "path_file", length = 150)
	@CampoReporte(nombre = "path file")
	private String pathFile;

	@ManyToOne
	@JoinColumn(name = "id_tipo_plantillador", foreignKey = @ForeignKey(name = "FK_id_tipo_plantillador"), nullable = false)
	private CatTipoPlantillador catTipoPlantillador;
	
	@OneToOne(mappedBy = "plantillaModelPlantilladorModel", fetch = FetchType.EAGER)
	@JsonIgnore
    private ContenidoPlantilladorModel contenidoPlantilladorModel;

	@Override
	public String toString() {
		return "| idPlantillador: " + idPlantillador + "| Nombre: " + nombre + "| Comentarios: " + comentarios 
				+ "| Fecha de modificación: " + fechaModificacion  + "| Estatus: " + status;
	}
	
	
	
		
}
