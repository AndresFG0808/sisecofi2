package com.sisecofi.libreria.comunes.model.contratos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.*;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "vigencia_montos")
@Getter
@Setter
public class VigenciaMontosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVigenciaMonto;

    @JoinColumn(name = "id_contrato", insertable = false, updatable = false)
    @OneToOne(targetEntity = ContratoModel.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private ContratoModel contratoModel;
  
    @Column(name = "id_contrato", nullable = false)
    private Long idContrato;

    @NotNull(message = "fecha inicio vigencia servicios obligatorio")
    @Column(name = "fecha_inicio_vigencia_servicios", nullable = false)
    private LocalDateTime fechaInicioVigenciaServicios;

    @NotNull(message = "fecha fin vigencia servicios obligatorio")
    @Column(name = "fecha_fin_vigencia_servicios", nullable = false)
    private LocalDateTime fechaFinVigenciaServicios;

    @NotNull(message = "fecha inicio vigencia contrato obligatorio")
    @Column(name = "fecha_inicio_vigencia_contrato", nullable = false)
    private LocalDateTime fechaInicioVigenciaContrato;

    @NotNull(message = "fecha fin vigencia contrato obligatorio")
    @Column(name = "fecha_fin_vigencia_contrato", nullable = false)
    private LocalDateTime fechaFinVigenciaContrato;

    @JoinColumn(name = "id_tipo_moneda", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatTipoMoneda.class, fetch = FetchType.EAGER)
    @JsonIgnore  
    private CatTipoMoneda catTipoMoneda;

    @NotNull(message = "tipo moneda obligatorio")
    @Column(name = "id_tipo_moneda", nullable = false)
    private Integer idTipoMoneda;

    @Column(name = "tipo_cambio_maximo")
    private BigDecimal tipoCambioMaximo;

    @NotNull(message = "monto minimo sin impuestos obligatorio")
    @Column(name = "monto_minimo_sin_impuestos", nullable = false)
    private BigDecimal montoMinimoSinImpuestos;

    @NotNull(message = "monto maximo sin impuestos obligatorio")
    @Column(name = "monto_maximo_sin_impuestos", nullable = false)
    private BigDecimal montoMaximoSinImpuestos;

    @NotNull(message = "monto pesos sin impuestos obligatorio")
    @Column(name = "monto_pesos_sin_impuestos", nullable = false)
    private BigDecimal montoPesosSinImpuestos;

    @NotNull(message = "monto minimo con impuestos obligatorio")
    @Column(name = "monto_minimo_con_impuestos", nullable = false)
    private BigDecimal montoMinimoConImpuestos;

    @NotNull(message = "monto maximo con impuestos obligatorio")
    @Column(name = "monto_maximo_con_impuestos", nullable = false)
    private BigDecimal montoMaximoConImpuestos;

    @NotNull(message = "monto pesos con impuestos obligatorio")
    @Column(name = "monto_pesos_con_impuestos", nullable = false)
    private BigDecimal montoPesosConImpuestos;

    
    @JoinColumn(name = "id_iva", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatIva.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private CatIva catIva;

    @NotNull(message = "Identificador del iva obligatorio")
    @Column(name = "id_iva")
    private Integer id_iva;

    @JoinColumn(name = "id_ieps", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatIeps.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private CatIeps catIeps;

    @Column(name = "id_ieps")
    private Integer idIeps;

    @Column(name = "estatus")
    private Boolean estatus;
}
