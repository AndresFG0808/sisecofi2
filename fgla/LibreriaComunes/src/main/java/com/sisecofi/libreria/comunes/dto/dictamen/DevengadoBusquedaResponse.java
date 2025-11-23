package com.sisecofi.libreria.comunes.dto.dictamen;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DevengadoBusquedaResponse {
	private String id;
	
	private String periodoControl;
	
	private LocalDateTime periodoInicio;
	
	private LocalDateTime periodoFin;
	
	private String proveedor;
	
	private String Estatus;
	
	private String montoEstimado;
	
	private String montoEstimadoPesos;
	
	private String montoDictaminado;
	
	private String montoDictaminadoPesos;
	
	private String comprobanteFiscal;
	
	private String pendientePago;
	
	private BigDecimal tipoCambioReferencial;
	
	private String tipo;
	
	private Long idProveedor;
	
	private Boolean estatusResponsabilidad;
	
	private Long idBd;
	
}
