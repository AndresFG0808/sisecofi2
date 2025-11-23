package com.sisecofi.proyectos.dto.adminplantrabajo;

import java.util.List;



/**
 * 
 * @author ayuso2104@gmail.com
 *
 */


public class PlanDto {

	private List<ListaTareas> tareas;

    // Getters y Setters
    public List<ListaTareas> getTareas() {
        return tareas;
    }

    public void setTareas(List<ListaTareas> tareas) {
        this.tareas = tareas;
    }
}
