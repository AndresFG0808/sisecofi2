package com.sisecofi.admindevengados.dto;

import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResumenConsolidadoDto {
	
	private String fase;
	
	private String nombreFase;
	
	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Digits(integer = 20, fraction = 2)
	private String subTotal;
	
	private String deducciones;
	private String ieps;
	private String iva;
	private String otrosImpuestos;
	private String total;
	private String totalPesos;
	private Long idDictamen;
	
	@Override
	public String toString() {
	    return String.join("|",
	        "fase:" + (fase != null ? fase : ""),
	        "nombreFase:" + (nombreFase != null ? nombreFase : ""),
	        "subTotal:" + (subTotal != null ? subTotal : ""),
	        "deducciones:" + (deducciones != null ? deducciones : ""),
	        "ieps:" + (ieps != null ? ieps : ""),
	        "iva:" + (iva != null ? iva : ""),
	        "otrosImpuestos:" + (otrosImpuestos != null ? otrosImpuestos : ""),
	        "total:" + (total != null ? total : ""),
	        "totalPesos:" + (totalPesos != null ? totalPesos : ""),
	        "idDictamen:" + (idDictamen != null ? idDictamen : "")
	    );
	}

	
	

}
