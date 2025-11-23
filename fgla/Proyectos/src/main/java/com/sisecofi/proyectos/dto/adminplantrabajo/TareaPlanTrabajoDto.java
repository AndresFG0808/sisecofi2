package com.sisecofi.proyectos.dto.adminplantrabajo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sisecofi.proyectos.util.functions.ComparableById;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
public class TareaPlanTrabajoDto implements ComparableById {
    
    private Integer idTarea;
    private Integer nivelEsquema;
    private String nombreTarea;

    private boolean activo;

    
    private Double duracionPlaneada;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaInicioPlaneada;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaFinPlaneada;

    
    private Double duracionReal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaInicioReal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaFinReal;

    private Integer predecesora;
    private Integer planeado;
    private Integer completado;


    private List<TareaPlanTrabajoDto> subRows = new ArrayList<>();
    

    @Override
    public Integer getIdTarea() {
        return idTarea;
    }

	public TareaPlanTrabajoDto() {
		super();
	}



   


}
