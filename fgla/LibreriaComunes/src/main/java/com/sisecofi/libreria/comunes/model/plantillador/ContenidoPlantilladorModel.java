package com.sisecofi.libreria.comunes.model.plantillador;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;

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
@Table(name = ConstantesComunes.PREFIX_TRAB + "contenidoPlantillador")
@Getter
@Setter
public class ContenidoPlantilladorModel extends ContenidoBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContenidoPlantillador;


	@OneToOne
	@JoinColumn(name = "id_plantillador", foreignKey = @ForeignKey(name = "FK_id_plantillador"), nullable = false)//debe ser un valor unico para cafa contenido plantillador ....
	private PlantilladorModel plantillaModelPlantilladorModel;

}
