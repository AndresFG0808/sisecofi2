package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacturaGuardarDto {

	private Long idFacturaNota;
	
	private String archivoPdf;
	
	private String archivoCargar;

	private Long dictamenId;
	
	private Integer idTipoMoneda;
	
	private Integer idIva;

	private Long idContrato;

	private Long idProveedor;

	private String comentarios;
	
	private String porcentajeSat;
	
	private BigDecimal montoSat;
	
	private BigDecimal montoPesoSat;
	
	private String porcentajeCC;
	
	private BigDecimal montoCC;
	
	private BigDecimal montoPesosCC;
	
	private String tipoDocumento;

}
