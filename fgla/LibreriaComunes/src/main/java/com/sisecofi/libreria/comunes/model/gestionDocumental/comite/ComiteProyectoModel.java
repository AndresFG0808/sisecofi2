package com.sisecofi.libreria.comunes.model.gestionDocumental.comite;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.*;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "comiteProyecto")
@Getter
@Setter
@AllArgsConstructor
public class ComiteProyectoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idComiteProyecto;

	@ManyToOne
	@JoinColumn(name = "id_proyecto", insertable = false, updatable = false )
	@JsonIgnore
	private ProyectoModel proyectoModel;

	@NotNull(message = ErroresEnum.MensajeValidation.MSJ)
	@Column(name = "id_proyecto", nullable = false)
	private Long idProyecto;

	@JoinColumn(name = "id_tipo_moneda", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatTipoMoneda.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatTipoMoneda catTipoMoneda;

	@Column(name = "id_tipo_moneda", nullable = false)
	private Integer idTipoMoneda;

	@JoinColumn(name = "id_contrato_convenio", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatContratoConvenio.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatContratoConvenio catContratoConvenio;

	@NotNull(message = ErroresEnum.MensajeValidation.MSJ)
	@Column(name = "id_contrato_convenio", nullable = false)
	private Integer idContratoConvenio;

	@JoinColumn(name = "id_comite", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatComite.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatComite catComite;

	@NotNull(message = ErroresEnum.MensajeValidation.MSJ)
	@Column(name = "id_comite", nullable = false)
	private Integer idComite;

	@JoinColumn(name = "id_contrato", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ContratoModel contratoModel;

	@Column(name = "id_contrato", nullable = false)
	private Integer idContrato;

	@NotNull(message = ErroresEnum.MensajeValidation.MSJ)
	@Column(name = "fechaSesion")
	private LocalDateTime fechaSesion;

	@Column(name = "id_sesion_numero")
	private Integer idSesionNumero;

	@Column(name = "id_sesion_clasificacion")
	private Integer idSesionClasificacion;

	@Size(max = 100)
	@Column(name = "acuerdo", nullable = true)
	private String acuerdo;

	@Size(max = 100)
	@Column(name = "vigencia", nullable = true)
	private String vigencia;

	@Column(name = "montoAutorizado")
	private BigDecimal montoAutorizado;

	@Column(name = "tipoCambio")
	private BigDecimal tipoCambio;

	@Column(name = "monto", nullable = true)
	private BigDecimal monto;

	@Size(max = 3000)
	@Column(name = "comentarios")
	private String comentarios;

	@Column(name = "estatus")
	private Boolean estatus;

	public ComiteProyectoModel() {
		super();
	}

	

}
