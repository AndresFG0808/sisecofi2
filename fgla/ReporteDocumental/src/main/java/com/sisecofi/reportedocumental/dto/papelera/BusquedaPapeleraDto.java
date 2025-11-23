package com.sisecofi.reportedocumental.dto.papelera;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.FilterField;
import com.sisecofi.libreria.comunes.util.enums.TypeObject;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author omartinezj
 *
 */
@Data
@Builder
public class BusquedaPapeleraDto implements GenericReport {
	
	private Long idUsuario;
	
	private LocalDateTime fecha;
	
	private LocalDateTime fechaFinal;

	@FilterField(filter = "s1.id_papelera", type = TypeObject.TYPE_LIST)
	private List<Long> idPapelera;

	@JsonIgnore
	private BusquedaPapelera dataReporteDto;
	
	private boolean acumulada;
	private int page;
	private int size;
	
	public String criterios(){
        String criterios = "";

        if (idUsuario != null){
            criterios += "Id Usuario :" + idUsuario + "|";
        }
        if (fecha!=null){
            criterios += " fecha de inicio :" + fecha + "|";
        }
        if (fechaFinal!=null){
            criterios += " fecha de termino :" + fechaFinal + "|";
        }
        
        return criterios;
    }
	
	@Override
	public int getSize() {
		if(this.size == 0)
			return 1;
		else
			return this.size;
	}

}
