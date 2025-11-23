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
@Table(name = ConstantesComunes.PREFIX_TRAB + "subPlantilladorDatos")
@Getter
@Setter
public class SubPlantilladorDatosModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSubPlantilladorDatos;

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


	@OneToMany(mappedBy = "subPlantilladorDatosModel", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<ContenidoSubPlantilladorModel> contenidos;
	
	@ManyToOne
	@JoinColumn(name = "id_sub_plantillador", foreignKey = @ForeignKey(name = "FK_id_sub_plantillador"), nullable = false)
	private SubPlantilladorModel subPlantilladorModel;
	
	
	@ManyToOne
	@JoinColumn(name = "id_sub_tipo_plantillador", foreignKey = @ForeignKey(name = "FK_id_sub_tipo_plantillador"), nullable = true)
	private CatSubTipoPlantillador catSubTipoPlantillador;
	
	
	@OneToOne(mappedBy = "subPlantilladorDatosModel", fetch = FetchType.EAGER)
	private ContenidoSubPlantilladorModel contenido;
	
	public ContenidoSubPlantilladorModel getContenido() {
	    if (contenido == null) {
	        return new ContenidoSubPlantilladorModel();
	    }
	    return contenido;
	}
	   
}
