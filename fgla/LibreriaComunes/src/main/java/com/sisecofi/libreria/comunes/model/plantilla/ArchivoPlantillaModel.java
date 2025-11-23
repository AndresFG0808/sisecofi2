package com.sisecofi.libreria.comunes.model.plantilla;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.ToString;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "archivoPlantilla")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArchivoPlantillaModel implements Comparable<ArchivoPlantillaModel> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idArchivoPlantilla;

	@ManyToOne
	@JoinColumn(name = "id_carpeta_plantilla", foreignKey = @ForeignKey(name = "FK_id_carpeta_plantilla"), nullable = false)
	@JsonIgnore
	private CarpetaPlantillaModel carpetaPlantillaModel;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "nivel")
	private int nivel;

	@Column(name = "orden")
	@EqualsAndHashCode.Include
	private int orden;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "obligatorio")
	private boolean obligatorio;

	@Column(name = "estatus")
	private boolean estatus;

	@Override
	 public int compareTo(ArchivoPlantillaModel o) {
        return Integer.compare(this.orden, o.getOrden());
    }

}
