package com.sisecofi.libreria.comunes.model.proyectos.adminplantrabajo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
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

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "planTrabajo")
@Getter
@Setter
public class PlanTrabajoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPlan;

	@ManyToOne
	@JoinColumn(name = "idProyecto", foreignKey = @ForeignKey(name = "FK_id_proyecto"), nullable = false)
	private ProyectoModel proyectoModel;


	@Column(name = "fecha", nullable = false)
	private LocalDateTime fecha ;

	@Column(name = "comentarios", nullable = false)
	private String comentarios;

	
	 public LocalDateTime getFecha() {
        ZoneId zoneId = ZoneId.of("America/Mexico_City");
        ZonedDateTime zonedDateTime = this.fecha.atZone(ZoneId.systemDefault()).withZoneSameInstant(zoneId);
        return zonedDateTime.toLocalDateTime();
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
	
	

}
