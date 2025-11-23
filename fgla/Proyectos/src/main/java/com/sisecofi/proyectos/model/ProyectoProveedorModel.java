package com.sisecofi.proyectos.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatRespuestaProveedor;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = ConstantesComunes.PREFIX_TRAB + "proyectoProveedor")
@Getter
@Setter
public class ProyectoProveedorModel {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProyectoProveedor;

	@ManyToOne
	@JoinColumn(name = "id_proyecto", foreignKey = @ForeignKey(name = "FK_id_proyecto"), nullable = false)
	private ProyectoModel proyectoModel;

	@JoinColumn(name = "id_proveedor", insertable = false, updatable = false)
	@ManyToOne(targetEntity = ProveedorModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private ProveedorModel proveedorModel;

	@Column(name = "id_proveedor", nullable = false)
	private Long idProveedor;

	@Column(name = "fecha")
	private LocalDate fecha;

	@JoinColumn(name = "id_investigacion_mercado", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatRespuestaProveedor.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatRespuestaProveedor catInvestigacionMercado;

	@Column(name = "id_investigacion_mercado")
	private Integer idInvestigacionMercado;

	@Column(name = "fechaIm")
	private LocalDate fechaIm;
	
	@JoinColumn(name = "id_respuesta_im", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatRespuestaProveedor.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatRespuestaProveedor catRespuestaIm;

	@Column(name = "id_respuesta_im")
	private Integer idRespuestaIm;

	@Column(name = "fechaRespuestaIm")
	private LocalDate fechaRespuestaIm;

	@JoinColumn(name = "id_junta_aclaracion", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatRespuestaProveedor.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatRespuestaProveedor catJuntaAclaracion;

	@Column(name = "id_junta_aclaracion")
	private Integer idJuntaAclaracion;

	@Column(name = "fechaJa")
	private LocalDate fechaJa;

	@JoinColumn(name = "id_licitacion_publica", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatRespuestaProveedor.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatRespuestaProveedor catLicitacionPublica;

	@Column(name = "id_licitacion_publica")
	private Integer idLicitacionPublica;

	@Column(name = "fechaLp")
	private LocalDate fechaLp;

	@Column(name = "estatus")
	private boolean estatus;

	@Column(name = "comentario")
	private String comentario;

}
