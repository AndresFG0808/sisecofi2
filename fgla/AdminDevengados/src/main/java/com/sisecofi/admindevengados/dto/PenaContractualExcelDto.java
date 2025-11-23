package com.sisecofi.admindevengados.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PenaContractualExcelDto {
	
	private Long idPenaPrimary;
	
	private String dictamenId;
	
	private String tipoPena; // ESTATUS
	
	private String documentoNombre; // ESTATUS
	
	private String penaAplicable;
	
	private String desglose;
	
	private String conceptoServicio;
	
	private BigDecimal monto;

	@Override
	public String toString() {
	    return String.join("|",
	        "idPenaPrimary:" + (idPenaPrimary != null ? idPenaPrimary : ""),
	        "dictamenId:" + (dictamenId != null ? dictamenId : ""),
	        "tipoPena:" + (tipoPena != null ? tipoPena : ""),
	        "documentoNombre:" + (documentoNombre != null ? documentoNombre : ""),
	        "penaAplicable:" + (penaAplicable != null ? penaAplicable : ""),
	        "desglose:" + (desglose != null ? desglose : ""),
	        "conceptoServicio:" + (conceptoServicio != null ? conceptoServicio : ""),
	        "monto:" + (monto != null ? monto : "")
	    ) + "|"; 
	}

	
	

}
