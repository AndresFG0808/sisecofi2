package com.sisecofi.proyectos.dto.adminplantrabajo;

import java.util.List;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */


public class PlantillaGeneralDto {

	private PlanDto dto;
    private List<ListaTareas> jerarquiaTareas;

    // Getters y Setters
    public PlanDto getDto() {
        return dto;
    }

    public void setDto(PlanDto dto) {
        this.dto = dto;
    }

    public List<ListaTareas> getJerarquiaTareas() {
        return jerarquiaTareas;
    }

    public void setJerarquiaTareas(List<ListaTareas> jerarquiaTareas) {
        this.jerarquiaTareas = jerarquiaTareas;
    }

}
