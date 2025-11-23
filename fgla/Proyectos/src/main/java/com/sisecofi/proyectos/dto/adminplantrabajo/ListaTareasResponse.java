package com.sisecofi.proyectos.dto.adminplantrabajo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
public class ListaTareasResponse extends DataDto {



    private List<ListaTareasResponse> subRows = new ArrayList<>();


    public ListaTareasResponse() {
        super();
    }


    
    public ListaTareasResponse(DataDto dataDto) {
        super(dataDto.getIdTarea(), dataDto.getNivelEsquema(), dataDto.getNombreTarea(),
              dataDto.isActivo(), dataDto.getDuracionPlaneada(), dataDto.getFechaInicioPlaneada(),
              dataDto.getFechaFinPlaneada(), dataDto.getDuracionReal(), dataDto.getFechaInicioReal(),
              dataDto.getFechaFinReal(), dataDto.getPredecesora(), dataDto.getPlaneado(), dataDto.getCompletado());
    }


    
     public void addSubRow(ListaTareasResponse subRow) {
        this.subRows.add(subRow);
    }

    
    public List<ListaTareasResponse> getSubRows() {
        return subRows;
    }

    public void setSubRows(List<ListaTareasResponse> subRows) {
        this.subRows = subRows;
    }


}
