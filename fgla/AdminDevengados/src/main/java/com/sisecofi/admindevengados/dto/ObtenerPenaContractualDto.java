package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ObtenerPenaContractualDto {
	
private Long idPenaPrimary;
	
	private Long dictamenId;
	
	private Long idTipoPena; 
	
	private String tipoPena;
	
	private String penaAplicable;
	
	private String desglose;
	
	private Long idDesglose;
	
	private String conceptoServicio;
	
	private BigDecimal monto;
	
    private Long idDocumento;
    
    private String documentoNombre;

}
