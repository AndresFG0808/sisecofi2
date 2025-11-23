package com.sisecofi.libreria.comunes.model.penasContractuales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatDesgloce;
import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class BasePenas {
	
	@JoinColumn(name = "idServicios", insertable = false, updatable = false)
    @ManyToOne(targetEntity = InformesDocumentalesServiciosModel.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private InformesDocumentalesServiciosModel informesDocumentalesServiciosModel;

    @Column(name = "idServicios")
    private Long idServicios;
    
    @JoinColumn(name = "idPeriodicos", insertable = false, updatable = false)
    @ManyToOne(targetEntity = InformesDocumentalesPeriodicosModel.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private InformesDocumentalesPeriodicosModel informesDocumentalesPeriodicosModel;

    @Column(name = "idPeriodicos")
    private Long idPeriodicos;
    
    @JoinColumn(name = "id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = InformesDocumentalesUnicaVezModel.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private InformesDocumentalesUnicaVezModel informesDocumentalesUnicaVezModel;

    @Column(name = "id")
    private Long id;    
    
    @JoinColumn(name = "idAtrasoPrestacion", insertable = false, updatable = false)
    @ManyToOne(targetEntity = AtrasoPrestacionModel.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private AtrasoPrestacionModel atrasoPrestacionModel;

    @Column(name = "idAtrasoPrestacion")
    private Long idAtrasoPrestacion;
    
    @Column(name = "estatus", length = 15)
	private Boolean estatus;
    
    @Column(name = "penaAplicable", length = 500)
	private String penaAplicable;
	
	@JoinColumn(name = "idDesgloce", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatDesgloce.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatDesgloce catDesgloce;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDesgloce")
	private Long idDesgloce;

}
