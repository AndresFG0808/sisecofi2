package com.sisecofi.libreria.comunes.model.plantilla;

import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.dto.reportecontroldoc.PlantillaVigenteDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatFaseProyecto;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "plantillaVigente")
@Getter
@Setter
@ToString
@NamedNativeQuery(name = "PlantillaVigenteModel.findByFaseProyecto", query = "select p.id_plantilla_vigente as idPlantillaVigente,p.nombre as nombre from sisecofi.sscft_plantilla_vigente p where p.id_fase_proyecto=:idFaseProyecto", resultSetMapping = "plantillaVigenteDto")
@SqlResultSetMapping(name = "plantillaVigenteDto", classes = @ConstructorResult(targetClass = PlantillaVigenteDto.class, columns = {
		@ColumnResult(name = "idPlantillaVigente"), @ColumnResult(name = "nombre") }))
public class PlantillaVigenteModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPlantillaVigente;

	@JoinColumn(name = "id_fase_proyecto", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatFaseProyecto.class, fetch = FetchType.EAGER)
	private CatFaseProyecto catFaseProyecto;

	@Column(name = "id_fase_proyecto", nullable = false)
	private Integer idFaseProyecto;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "fecha")
	private LocalDateTime fecha;

	@Column(name = "fecha_modificacion")
	private LocalDateTime fechaModificacion;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "estado")
	private boolean estado;

	@Column(name = "asignado")
	private boolean asignado;

	@Column(name = "prefijo")
	private String prefijo;
}
