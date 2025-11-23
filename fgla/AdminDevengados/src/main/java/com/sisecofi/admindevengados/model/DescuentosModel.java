package com.sisecofi.admindevengados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatDesgloce;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB +"descuentos_dictamenes")
@Getter
@Setter
public class DescuentosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDescuento;

    @JoinColumn(name = "dictamenId", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private Dictamen dictamen;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "dictamenId")
    private Long dictamenId;

    @JoinColumn(name = "idDesgloce", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatDesgloce.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private CatDesgloce catDesgloce;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "idDesgloce")
    private Long idDesgloce;

    @Column(name = "monto")
    @Digits(integer = 20, fraction = 2)
    private BigDecimal monto;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "moneda")
    private String moneda;
}
