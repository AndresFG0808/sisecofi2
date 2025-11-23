package com.sisecofi.proyectos.service.adminplantrabajo;

import java.util.List;

import com.sisecofi.proyectos.dto.adminplantrabajo.TareaPlanTrabajoDto;
import com.sisecofi.proyectos.model.adminplantrabajo.TareaPlanTrabajoModel;

public interface CalculosMasivoService {

List<TareaPlanTrabajoDto> calculosMasivos();
List<TareaPlanTrabajoDto> calcularMasivosCorreos();
void calcularPorcentajeCompletadoTareaPadre(TareaPlanTrabajoDto tareaPadre,
		List<TareaPlanTrabajoModel> tareasOriginales);
List<TareaPlanTrabajoDto> construirJerarquiaTareasOriginal(List<TareaPlanTrabajoModel> tareasOriginales);
}
