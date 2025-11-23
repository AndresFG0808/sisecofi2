package com.sisecofi.admindevengados.util.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class Factura {

    @Column(length = 18)//<--
    @JacksonXmlProperty(localName = "Folio")
    private String folio;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(length = 40)
    @JacksonXmlProperty(localName = "Comprobante")
    private String comprobanteFiscal;

    @JacksonXmlProperty(localName = "Emisor")
    private Emisor emisor;
    
    @JacksonXmlProperty(localName = "Receptor")
    private Emisor receptor;

    @JacksonXmlProperty(localName = "Fecha")
    private String fechaFacturacion;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @JacksonXmlProperty(localName = "Moneda")
    private String moneda;
    
    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @JacksonXmlProperty(localName = "TipoDeComprobante")
    private String tipoDeComprobante;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @JacksonXmlProperty(localName = "TipoCambio")
    private String tipoCambio;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Digits(fraction = 2, integer = 20)
    @JacksonXmlProperty(localName = "SubTotal")
    private BigDecimal subtotal;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Digits(fraction = 2, integer = 20)
    @JacksonXmlProperty(localName = "Total")
    private BigDecimal total;

    @JacksonXmlProperty(localName = "Impuestos")
    private Impuestos impuestos;

    @JacksonXmlElementWrapper(localName = "Conceptos")
    @JacksonXmlProperty(localName = "Concepto")
    private List<Concepto> conceptos;

    @JacksonXmlProperty(localName = "Complemento")
    private Complemento complemento;

    public static class Emisor extends FacturaProveedorBase {
    }
    
    public static class Receptor extends FacturaProveedorBase {
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Impuestos {

        @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
        @Digits(fraction = 2, integer = 12)
        @JacksonXmlProperty(localName = "TotalImpuestosTrasladados")
        private BigDecimal totalImpuestosTrasladados;

        @JacksonXmlProperty(localName = "Traslados")
        private Traslados traslados;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Traslados {
            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "Traslado")
            private List<Traslado> trasladoList;

            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Traslado {

                @JacksonXmlProperty(localName = "Impuesto")
                private String impuesto;

                @JacksonXmlProperty(localName = "Base")
                private BigDecimal base;

                @JacksonXmlProperty(localName = "Importe")
                private BigDecimal importe;

                @JacksonXmlProperty(localName = "TasaOCuota")
                private BigDecimal tasaCuota;
            }
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Complemento {
        @JacksonXmlProperty(localName = "TimbreFiscalDigital")
        private TimbreFiscalDigital timbreFiscalDigital;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TimbreFiscalDigital {

            @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
            @Column(length = 36)  // UUID length is 36 <--
            @JacksonXmlProperty(localName = "UUID")
            private String uuid;

            @JacksonXmlProperty(localName = "NoCertificadoSAT")
            private String noCertificadoSAT;

            @JacksonXmlProperty(localName = "SelloCFD")
            private String selloCFD;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Concepto {
        @JacksonXmlProperty(localName = "ClaveProdServ")
        private String claveProdServ;

        @JacksonXmlProperty(localName = "ClaveUnidad")
        private String claveUnidad;

        @JacksonXmlProperty(localName = "Descripcion")
        private String descripcion;

        @JacksonXmlProperty(localName = "Importe")
        private BigDecimal importe;

        @JacksonXmlProperty(localName = "NoIdentificacion")
        private String noIdentificacion;

        @JacksonXmlProperty(localName = "Unidad")
        private String unidad;

        @JacksonXmlProperty(localName = "ValorUnitario")
        private BigDecimal valorUnitario;
    }
}