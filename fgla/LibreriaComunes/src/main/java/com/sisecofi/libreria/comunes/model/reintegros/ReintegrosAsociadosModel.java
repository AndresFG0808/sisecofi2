package com.sisecofi.libreria.comunes.model.reintegros;

import com.sisecofi.libreria.comunes.model.catalogo.CatTipoReintegro;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.reintegros.CarpetaPlantillaReintegroModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "reintegros_asociados")
@Getter
@Setter
public class ReintegrosAsociadosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReintegrosAsociados;

    @JoinColumn(name = "id_contrato", insertable = false, updatable = false)
    @ManyToOne(targetEntity = ContratoModel.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private ContratoModel contratoModel;

    @Column(name = "id_contrato", nullable = false)
    private Long idContrato;


    @JoinColumn(name="id_tipo_reintegro", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CatTipoReintegro tipoReintegro;

    
    /*@NotNull(message = ErroresEnum.MensajeValidation.MSJREINTEGRO)
    @Digits(integer = 9, fraction = 2, message = ErroresEnum.MensajeValidation.MSJIMPORTE)
    @DecimalMin(value = "0.01", inclusive = true, message = ErroresEnum.MensajeValidation.MSJCEROIMPORTE)*/

    @Column(name = "importe", nullable = false)
    private BigDecimal importe;
    
    /*@NotNull(message = ErroresEnum.MensajeValidation.MSJREINTEGRO)
    @Digits(integer = 9, fraction = 2, message = ErroresEnum.MensajeValidation.MSJINTERES)
    @DecimalMin(value = "0.01", inclusive = true, message = ErroresEnum.MensajeValidation.MSJCEROINTERES)*/
    
    @Column(name = "interes", nullable = false)
    private BigDecimal interes;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "fecha_reintegro", nullable = false)
    @NotNull(message = ErroresEnum.MensajeValidation.MSJ)
    private LocalDateTime fechaReintegro;

    @Column(name = "estatus", nullable = false)
    private Boolean estatus = true;


    @Column(name = "orden_contrato")
    private Integer ordenContrato;
    
    @OneToOne(mappedBy = "reintegro")
    @JsonIgnore
    private CarpetaPlantillaReintegroModel carpetaGestion;
    
    @Column(name = "asignado")
    private Boolean asignado;

}
