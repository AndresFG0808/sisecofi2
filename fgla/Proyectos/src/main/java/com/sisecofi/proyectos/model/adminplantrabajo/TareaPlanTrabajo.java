package com.sisecofi.proyectos.model.adminplantrabajo;

import java.time.LocalDate;

public interface TareaPlanTrabajo {
    void setFechaFinReal(LocalDate fechaFin);
    LocalDate getFechaInicioReal();
    void setDuracionReal(Double duracionReal);
    void setFechaInicioReal(LocalDate fechaInicioReal);
    LocalDate getFechaFinReal();
}
