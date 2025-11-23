package com.sisecofi.proyectos.model.cierre;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusRcp;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "cierre_proyecto")
@Getter
@Setter
public class CierreModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCierre;
	
	@JoinColumn(name = "idEstatusRcp", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatEstatusRcp.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatEstatusRcp catEstatusRcp;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idEstatusRcp")
	private Long idEstatusRcp;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "estatus")
	private boolean estatus;
	
	@JoinColumn(name = "idProyecto", insertable = false, updatable = false)
	@OneToOne(targetEntity = ProyectoModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ProyectoModel proyectoModel;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idProyecto")
	private Long idProyecto;
	
	@JoinColumn(name = "idUser", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private Usuario usuario;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idUser")
	private Long idUser;
	
	@Column(name = "fechaEntrega")
	private LocalDateTime fechaEntrega;
	
	@Column(name = "porcentajePlaneado")
	private Double porcentajePlaneado;
	
	@Column(name = "porcentajeReal")
	private Double porcentajeReal;

}
