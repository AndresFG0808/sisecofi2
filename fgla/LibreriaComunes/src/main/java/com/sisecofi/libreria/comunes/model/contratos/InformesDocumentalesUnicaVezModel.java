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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "informes_documentales_unica_vez")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class InformesDocumentalesUnicaVezModel extends BaseInformesModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fase")
	private String fase;

	@NotNull
	@NotBlank
	@Column(name = "informe_documental", nullable = false)
	private String informeDocumental;

	@NotNull
	@NotBlank
	@Column(name = "fecha_entrega", nullable = false)
	private String fechaEntrega;

	@NotNull
	@NotBlank
	@Column(name = "penas_deducciones_aplicables", nullable = false)
	private String penasDeduccionesAplicables;
}
