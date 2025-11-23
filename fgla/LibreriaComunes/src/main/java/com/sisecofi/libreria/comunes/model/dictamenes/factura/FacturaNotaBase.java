package com.sisecofi.libreria.comunes.model.dictamenes.factura;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.catalogo.CatIva;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoMoneda;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class FacturaNotaBase {
	
	@JoinColumn(name = "idDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.EAGER)
	@JsonIgnore
	private Dictamen dictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDictamen")
	private Long idDictamen;
	
	@ManyToOne
	@JoinColumn(name = "id_archivo_pdf", foreignKey = @ForeignKey(name = "FK_id_archivo_pdf"))
	@JsonIgnore
	private ArchivoPlantillaDictamenModel archivoPdf;
	
	@ManyToOne
	@JoinColumn(name = "id_archivo_xml", foreignKey = @ForeignKey(name = "FK_id_archivo_xml"))
	@JsonIgnore
	private ArchivoPlantillaDictamenModel archivoXml;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "estatus")
	private boolean estatus;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "archivoCargar")
	private String archivoCargar;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "pdf")
	private String pdf;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "folio", length = 18)//<--
	private String folio;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "comprobanteFiscal", length = 40)
	private String comprobanteFiscal;
	
	@JoinColumn(name = "id_tipo_moneda", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatTipoMoneda.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private CatTipoMoneda catTipoMoneda;

    @NotNull(message = "tipo moneda obligatorio")
    @Column(name = "id_tipo_moneda", nullable = false)
    private Integer idTipoMoneda;
	
    @JoinColumn(name = "idIva", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CatIva.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private CatIva catIva;

    @NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
    @Column(name = "idIva", nullable = false)
    private Integer idIva;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "subTotal")
	private BigDecimal subTotal;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "ieps")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal ieps;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "iva")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal iva;
	
	@Column(name = "otrosImpuestos")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal otrosImpuestos;
	
	@Column(name = "total")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal total;
	
	@Column(name = "totalPesos")
	@Digits(integer = 20, fraction = 2)
	private BigDecimal totalPesos;
	
	@Column(name = "comentarios", length = 1050)
	private String comentarios;
	
	private String porcentajeSat;
	private BigDecimal montoSat;
	private BigDecimal montoPesosSat;
	private String porcentajeCC;
	private BigDecimal montoCC;
	private BigDecimal montoPesosCC;
	
	private String pathArchivoXml;
	private String pathPdf;

	@Column(name = "consecutivo")
	private Integer consecutivo;
	
	
}
