package com.sisecofi.proyectos.dto.adminplantrabajo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ListaTareas extends DataDto {

    private List<ListaTareas> subRows = new ArrayList<>();

    public ListaTareas() {
        super();
    }

    public ListaTareas(DataDto dataDto) {
        super(dataDto.getIdTarea(), dataDto.getNivelEsquema(), dataDto.getNombreTarea(),
              dataDto.isActivo(), dataDto.getDuracionPlaneada(), dataDto.getFechaInicioPlaneada(),
              dataDto.getFechaFinPlaneada(), dataDto.getDuracionReal(), dataDto.getFechaInicioReal(),
              dataDto.getFechaFinReal(), dataDto.getPredecesora(), dataDto.getPlaneado(), dataDto.getCompletado());
    }

    public void addSubRow(ListaTareas subRow) {
        this.subRows.add(subRow);
    }

    public List<ListaTareas> getSubRows() {
        return subRows;
    }

    public void setSubRows(List<ListaTareas> subRows) {
        this.subRows = subRows;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ListaTareas)) return false;
        if (!super.equals(obj)) return false; 
        ListaTareas that = (ListaTareas) obj;
        return Objects.equals(subRows, that.subRows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subRows); 
    }
}


