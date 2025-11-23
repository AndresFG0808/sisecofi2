package com.sisecofi.libreria.comunes.model.dictamenes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatDesgloce;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoNotificacionPago;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB +"referencia_pago")
@Getter
@Setter
public class ReferenciaPagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReferenciaPago;

    @JoinColumn(name = "id_tipo_notificacion_pago", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatTipoNotificacionPago.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private CatTipoNotificacionPago catTipoNotificacionPago;

    @Column(name = "id_tipo_notificacion_pago")
    private Integer idTipoNotificacionPago;

    @JoinColumn(name = "id_solicitud_pago", insertable = false, updatable = false)
    @ManyToOne(targetEntity = SolicitudPagoModel.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private SolicitudPagoModel solicitudPagoModel;

    @Column(name = "id_solicitud_pago")
    private Long idSolicitudPago;

    @Column(name = "oficio_notificacion_pago")
    private String oficioNotificacionPago;

    @Column(name = "fecha_notificacion")
    private LocalDateTime fechaNotificacion;

    @Column(name = "folio_ficha_pago")
    private String folioFichaPago; //**

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago; //**

    @Column(name = "tipo_cambio_pagado")
    private BigDecimal tipoCambioPagado; //**

    @Column(name = "pagado_NAFIN")
    private BigDecimal pagadoNAFIN; //**

    @Column(name = "ruta_ficha_nafin")
    private String rutaFichaNAFIN;

    @JoinColumn(name = "id_desgloce", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatDesgloce.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private CatDesgloce catDesgloce;

    @Column(name = "id_desgloce")
    private Integer idDesglose; //**
    
    @OneToOne
	@JoinColumn(name = "id_archivo_pdf", foreignKey = @ForeignKey(name = "FK_id_archivo_pdf"))
	@JsonIgnore
	private ArchivoPlantillaDictamenModel archivoPdf;


    @JoinColumn(name = "id_factura", insertable = false, updatable = false)
    @ManyToOne(targetEntity = FacturaModel.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private FacturaModel facturaModel;
    
    @Column(name = "id_factura")
    private Long idFactura; //**

    @Column(name = "estatus")
    private Boolean estatus;

    @Column(name = "estatus_pagado")
    private Boolean estatusPagado;

    @Column(name = "convenio_colaboracion")
    private Boolean convenioColaboracion;
}
