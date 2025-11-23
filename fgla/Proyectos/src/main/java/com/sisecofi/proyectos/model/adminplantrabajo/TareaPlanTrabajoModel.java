package com.sisecofi.proyectos.model.adminplantrabajo;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.model.proyectos.adminplantrabajo.PlanTrabajoModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "tareaPlanTrabajo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TareaPlanTrabajoModel extends BaseTareaPlanTrabajo implements TareaPlanTrabajo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTareaPlanTrabajo;

	@ManyToOne
	@JoinColumn(name = "id_plan", foreignKey = @ForeignKey(name = "FK_id_plan"), nullable = false)
	private PlanTrabajoModel planTrabajoModel;

}
