package com.sisecofi.proyectos.service.adminplantrabajo;

import org.springframework.stereotype.Service;

@Service
public interface ReportePlanTrabajoService {

    byte [] obtenerReportePlanTrabajo(Long idProyecto);

}
