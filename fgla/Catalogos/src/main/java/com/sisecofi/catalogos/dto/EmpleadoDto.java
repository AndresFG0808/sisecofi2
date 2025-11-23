package com.sisecofi.catalogos.dto;

import java.time.LocalDateTime;

import com.sisecofi.libreria.comunes.model.catalogo.CatTipoEmpleado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDto {

	private Integer idEmpleadoAdministracion;
	private String nombre;
	private String correo;
	private String telefono;
	private LocalDateTime fechaInicioVigencia;
	private LocalDateTime fechaFinVigencia;
	private CatTipoEmpleado catTipoEmpleado;
	private Integer idTipoEmpleado;
	private boolean modificado;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaModificacion;
	private boolean estatus;
	
	public Integer getIdTipoEmpleado() {
		if (idTipoEmpleado==null && catTipoEmpleado!=null) {
			return catTipoEmpleado.getIdTipoEmpleado();
		}
		return idTipoEmpleado;
	}
	
}
