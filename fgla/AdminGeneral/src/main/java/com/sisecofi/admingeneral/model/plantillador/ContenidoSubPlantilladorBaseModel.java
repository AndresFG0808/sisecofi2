package com.sisecofi.admingeneral.model.plantillador;

import com.sisecofi.libreria.comunes.model.plantillador.CatSubTipoPlantillador;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "contenidoSubPlantilladorBase")
@Getter
@Setter
public class ContenidoSubPlantilladorBaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContenidoSubPlantilladorBase;

	@Column(name = "header", nullable = false, length = 2000000)
	private String header;

	@Column(name = "footer", nullable = false, length = 2000000)
	private String footer;

	@Column(name = "contenido", nullable = false, length = 2000000)
	private String contenido;
	
	@ManyToOne
	@JoinColumn(name = "id_sub_tipo_plantillador", foreignKey = @ForeignKey(name = "FK_id_sub_tipo_plantillador"), nullable = false)
	private CatSubTipoPlantillador catTipoPlantillador;

}
