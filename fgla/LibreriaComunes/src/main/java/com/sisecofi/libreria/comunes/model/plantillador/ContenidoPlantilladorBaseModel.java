package com.sisecofi.libreria.comunes.model.plantillador;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = ConstantesComunes.PREFIX_TRAB + "contenidoPlantilladorBase")
@Getter
@Setter
public class ContenidoPlantilladorBaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContenidoPlantilladorBase;

	@Column(name = "header", nullable = false, length = 200000)
	private String header;

	@Column(name = "footer", nullable = false, length = 200000)
	private String footer;

	@Column(name = "contenido", nullable = false, length = 200000)
	private String contenido;
	
	@OneToOne
	@JoinColumn(name = "id_tipo_plantillador", foreignKey = @ForeignKey(name = "FK_id_tipo_plantillador"), nullable = false)
	private CatTipoPlantillador catTipoPlantillador;

}
