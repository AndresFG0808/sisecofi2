package com.sisecofi.admingeneral.model.plantillador;

import com.sisecofi.libreria.comunes.model.plantillador.CatSubTipoPlantillador;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoBase;
import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;
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
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
public class ContentSubPlantilladorModel extends ContenidoBase implements Comparable<ContentSubPlantilladorModel>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContenidoSubPlantillador;

	
	@Column(name = "orden")
	private Integer orden;

	@ManyToOne
	@JoinColumn(name = "id_sub_plantillador", foreignKey = @ForeignKey(name = "FK_id_sub_plantillador"), nullable = false)
	private SubPlantilladorModel subPlantilladorModel;
	
	@ManyToOne
	@JoinColumn(name = "id_sub_tipo_plantillador", foreignKey = @ForeignKey(name = "FK_id_sub_tipo_plantillador"), nullable = true)
	private CatSubTipoPlantillador catSubTipoPlantillador;

	@Override
	public String toString() {
		return "ContenidoSubPlantilladorModel [idContenidoSubPlantillador=" + idContenidoSubPlantillador + ", orden="
				+ orden + "]";
	}

	@Override
	public int compareTo(ContentSubPlantilladorModel o) {		
		return orden-o.getOrden();
	}
	
}
