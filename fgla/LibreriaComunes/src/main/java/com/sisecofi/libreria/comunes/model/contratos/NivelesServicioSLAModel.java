package com.sisecofi.libreria.comunes.model.contratos;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "niveles_servicio_sla")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NivelesServicioSLAModel extends BaseInformesModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idServiciosSla;

	@NotNull
	@NotBlank(message = "SLA es obligatorio y no puede estar vacío")
	@Column(name = "sla", nullable = false)
	private String sla;

	@NotNull
	@NotBlank(message = "Las Deducciones Aplicables es obligatorio y no puede estar vacío")
	@Column(name = "deducciones-aplicables", nullable = false)
	private String deduccionesAplicables;

	@NotNull
	@NotBlank(message = "El Objetivo Minimo es obligatorio y no puede estar vacío")
	@Column(name = "objetivo-minimo", nullable = false)
	private String objectivoMinimo;
	
	public String getInformeDocumental() {
		return sla;
	}
}
