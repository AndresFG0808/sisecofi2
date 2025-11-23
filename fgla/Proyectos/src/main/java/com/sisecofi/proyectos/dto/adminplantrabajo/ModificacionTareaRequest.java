package com.sisecofi.proyectos.dto.adminplantrabajo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModificacionTareaRequest {

    private Integer idTarea;
    private String fechaInicioReal;
    private String fechaFinReal;
    private Double duracionReal;
    private boolean clearFechaInicioReal;
    private boolean clearFechaFinReal;
    private boolean clearDuracionReal;


}
