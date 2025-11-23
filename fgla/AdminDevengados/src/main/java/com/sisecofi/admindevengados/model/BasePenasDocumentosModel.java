package com.sisecofi.admindevengados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.admindevengados.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;

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
public class BasePenasDocumentosModel {
	
	@JoinColumn(name = "id_contrato", insertable = false, updatable = false)
    @ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private ContratoModel contratoModel;

    @NotNull(message = ErroresEnum.MensajeValidation.MSJ)
    @Column(name = "id_contrato")
    private Long idContrato;
    
    private String documentoServicio;
    
    private String descripcion;
    
    private String plazoEntrega;
    
    private String penaAplicable;


}
