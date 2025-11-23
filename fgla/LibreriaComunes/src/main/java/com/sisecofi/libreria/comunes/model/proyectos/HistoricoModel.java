package com.sisecofi.libreria.comunes.model.proyectos;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.interfaces.ValidacionCompleta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "historico")
@Getter
@Setter
public class HistoricoModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idHistorico;
	
	@JoinColumn(name = "id_ficha_tecnica", insertable = false, updatable = false)
	@OneToOne(targetEntity = FichaTecnicaModel.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private FichaTecnicaModel fichaTecnicaModel;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	@Column(name = "id_ficha_tecnica")
	private Long idFichaTecnica;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	@Column(name = "nombre")
	private String nombre;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	@Column(name = "puesto")
	private String puesto;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	@Column(name = "correo")
	private String correo;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	@Column(name = "fecha_inicio")
	private LocalDateTime fechaInicio;
	
	@Column(name = "fecha_fin")
	private LocalDateTime fechaFin;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE, groups= ValidacionCompleta.class)
	@Column(name = "estatus")
	private boolean estatus;
	
	@Column(name = "estatusHistorico")
	private boolean estatusHistorico;
	
	@Column(name = "nivel")
    private Integer nivel;
    
    @Column(name = "id_referencia")
    private Integer idReferencia;
}
