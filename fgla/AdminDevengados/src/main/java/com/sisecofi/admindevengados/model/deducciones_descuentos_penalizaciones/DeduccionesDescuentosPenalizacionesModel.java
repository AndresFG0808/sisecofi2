package com.sisecofi.admindevengados.model.deducciones_descuentos_penalizaciones;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoDescuento;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "deduccion_descuentos_penalizaciones")
@Getter
@Setter
public class DeduccionesDescuentosPenalizacionesModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDdp;


   @JoinColumn(name = "idDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private Dictamen dictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDictamen")
	private Long idDictamen;
    
    //aqui catalogo descuentos

    
    @JoinColumn(name = "idTipoDescuento", insertable = false, updatable = false)
	@ManyToOne(targetEntity = CatTipoDescuento.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private CatTipoDescuento tipoDescuento;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idTipoDescuento")
	private  Integer idTipoDescuento;

    @Column (name = "moneda")
    private String moneda;

    @Column(name = "monto")
    private BigDecimal monto;

    //para renumeracion de DDP
    @Column(name = "orden_descuento")
    private Integer ordenDescuento;

    //estatus desduccion, descuento, penalizacion
    @Column(name = "estatus_deduccion")
    private Boolean estatusDeduccion;


    

    















}
