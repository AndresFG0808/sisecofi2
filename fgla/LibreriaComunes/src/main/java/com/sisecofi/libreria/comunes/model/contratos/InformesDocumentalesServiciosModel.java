package com.sisecofi.libreria.comunes.model.contratos;


import com.sisecofi.libreria.comunes.model.catalogo.CatPeriodicidad;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "informes_documentales_servicios")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class InformesDocumentalesServiciosModel extends BaseInformesModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idServicios;

	@NotBlank
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "informe_documental", nullable = false)
	private String informeDocumental;

	@NotBlank
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "fecha_entrega", nullable = false)
	private String fechaEntrega;

	@NotBlank
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "penas_deducciones_aplicables", nullable = false)
	private String penasDeduccionesAplicables;
	
	@JoinColumn(name = "id_periodicidad", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatPeriodicidad.class, fetch = FetchType.EAGER)
	private CatPeriodicidad catPeriodicidad;

	@Column(name = "id_periodicidad")
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	private Integer idPeriodicidad;
}
