package com.sisecofi.reportedocumental.dto.papelera;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Papelera {

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
}
