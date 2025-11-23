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
@Table(name = ConstantesComunes.PREFIX_TRAB + "contenidoSubPlantillador")
@Getter
@Setter
public class ContenidoSubPlantilladorModel extends ContenidoBase implements Comparable<ContenidoSubPlantilladorModel>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContenidoSubPlantillador;

	
	@Column(name = "orden")
	private Integer orden;

	@OneToOne
	@JoinColumn(name = "id_sub_plantillador_datos", foreignKey = @ForeignKey(name = "FK_id_sub_plantillador_datos"), nullable = false)
	private SubPlantilladorDatosModel subPlantilladorDatosModel;
	
	@Override
	public String toString() {
		return "ContenidoSubPlantilladorModel [idContenidoSubPlantillador=" + idContenidoSubPlantillador + ", orden="
				+ orden + "]";
	}

	@Override
	public int compareTo(ContenidoSubPlantilladorModel o) {		
		return orden-o.getOrden();
	}
	
}
