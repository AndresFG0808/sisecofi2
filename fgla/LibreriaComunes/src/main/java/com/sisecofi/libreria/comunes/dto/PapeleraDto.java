package com.sisecofi.libreria.comunes.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PapeleraDto {

	private Long idPapelera;

	private LocalDateTime fecha;

	private String pathOriginal;

	private String pathNuevo;

	private String comentarios;
	
	private Long idProyecto;
	
	private String idProyectoFormateado;
	
	private String nombreCorto;
	
	private String fase;
	
	private String plantilla;
	
	private String nombreDocumento;
	
	private String descripcion;
	
	private String fechaEliminacion;
	
	private Long idUsuario;
	
	private String usuarioElimina;
	
	private String tamano;
	
	private LocalDateTime fechaFinal;
	
	private Integer idArchivo;
	
	private String tipoArchivo;
	
	
	
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



	public PapeleraDto(Long idPapelera,
					   Long idProyecto,
					   String idProyectoFormateado,
					   String nombreCorto,
					   String fase,
					   String plantilla,
					   String nombreDocumento,
					   String descripcion,
					   String fechaEliminacion,
					   Long idUsuario,
					   String usuarioElimina,
					   String tamano, 
			           String pathNuevo,
			           String pathOriginal,
			           LocalDateTime fecha, 
			           String comentarios) {
		super();
		this.idPapelera = idPapelera;
		this.fecha = fecha;
		this.pathOriginal = pathOriginal;
		this.pathNuevo = pathNuevo;
		this.comentarios = comentarios;
		this.idProyecto = idProyecto;
		this.idProyectoFormateado = idProyectoFormateado;
		this.nombreCorto = nombreCorto;
		this.fase = fase;
		this.plantilla = plantilla;
		this.nombreDocumento = nombreDocumento;
		this.descripcion = descripcion;
		this.fechaEliminacion = fechaEliminacion;
		this.idUsuario = idUsuario;
		this.usuarioElimina = usuarioElimina;
		this.tamano = tamano;
	}
}
