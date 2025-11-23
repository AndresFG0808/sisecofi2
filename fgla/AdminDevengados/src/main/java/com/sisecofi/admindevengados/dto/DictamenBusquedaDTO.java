package com.sisecofi.admindevengados.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DictamenBusquedaDTO {
    
    private Long idDictamen;
	private Integer idPeriodoControlAnio;
    private Integer idPeriodoControlMes;
    private String nombreMes;
    private String nombreAnio;
    private String periodoControl;  
	private LocalDateTime periodoInicio;
	private LocalDateTime periodoFin;
    private String estatus;
    private Long idContrato;
    private Long idProveedor;
    private String nombreCorto;
    private Integer consecutivo;
	

	
	public String getIdDictamenVisual() {
    		return nombreCorto+"|"+  String.format("%05d", idProveedor) + "|" + String.format("%05d", consecutivo);
    }
}
