package com.sisecofi.proyectos.service.adminplantrabajo.impl;

import com.sisecofi.proyectos.dto.adminplantrabajo.TareaPlanTrabajoDto;
import com.sisecofi.proyectos.model.adminplantrabajo.BaseTareaPlanTrabajo;
import com.sisecofi.proyectos.model.adminplantrabajo.TareaPlanTrabajo;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sisecofi.proyectos.util.dates.DatePlanUtils.esNoLaboral;

public abstract class AbstractPlanTrabajo {

    protected void modificarCampoTarea(TareaPlanTrabajo tareaModificada, String campoModificado,
                                     Object nuevoValor, DateTimeFormatter formatter) {
        switch (campoModificado) {
            case "fechaFinReal":
                modificarFechaFinReal(tareaModificada, nuevoValor, formatter);
                break;
            case "fechaInicioReal":
                modificarFechaInicioReal(tareaModificada, nuevoValor, formatter);
                break;
            case "duracionReal":
                modificarDuracionReal(tareaModificada, nuevoValor);
                break;
            default:
                throw new IllegalArgumentException("Campo modificado no reconocido: " + campoModificado);
        }
    }
    
    protected void asignarValoresTareaBackup(TareaPlanTrabajoDto tareaDto, BaseTareaPlanTrabajo tareaBackup) {
        tareaBackup.setIdTarea(tareaDto.getIdTarea());
        tareaBackup.setNivelEsquema(tareaDto.getNivelEsquema());
        tareaBackup.setNombreTarea(tareaDto.getNombreTarea());
        tareaBackup.setActivo(tareaDto.isActivo());
        tareaBackup.setDuracionPlaneada(tareaDto.getDuracionPlaneada());
        tareaBackup.setFechaInicioPlaneada(tareaDto.getFechaInicioPlaneada());
        tareaBackup.setFechaFinPlaneada(tareaDto.getFechaFinPlaneada());
        tareaBackup.setDuracionReal(tareaDto.getDuracionReal());
        tareaBackup.setFechaInicioReal(tareaDto.getFechaInicioReal());
        tareaBackup.setFechaFinReal(tareaDto.getFechaFinReal());
        tareaBackup.setPredecesora(tareaDto.getPredecesora());
        tareaBackup.setPorcentajePlaneado(tareaDto.getPlaneado());
        tareaBackup.setPorcentajeCompletado(tareaDto.getCompletado());
    }

    protected TareaPlanTrabajoDto buscarTareaPorId(List<TareaPlanTrabajoDto> tareas, Integer idTarea) {
		for (TareaPlanTrabajoDto tarea : tareas) {
			if (tarea.getIdTarea().equals(idTarea)) {
				return tarea;
			}
			if (tarea.getSubRows() != null && !tarea.getSubRows().isEmpty()) {
				TareaPlanTrabajoDto subTarea = buscarTareaPorId(tarea.getSubRows(), idTarea);
				if (subTarea != null) {
					return subTarea;
				}
			}
		}
		return null;
	}
    
    protected double calcularSumaCompletadoAvance(TareaPlanTrabajoDto tareaPadre) {
        double sumaCompletadoAvance = 0.0;

        // Filtrar tareas con duracionReal no nula y calcular la suma de duraciones
        double sumaDuracionesReales = tareaPadre.getSubRows().stream()
                .filter(t -> t.isActivo() && t.getDuracionReal() != null) // Excluir tareas con duracionReal nula
                .mapToDouble(TareaPlanTrabajoDto::getDuracionReal).sum();

        if (sumaDuracionesReales > 0) {
            for (TareaPlanTrabajoDto tareaHija : tareaPadre.getSubRows()) {
                if (tareaHija.isActivo() && tareaHija.getDuracionReal() != null) {
                	Integer completadoHijo = Objects.requireNonNullElse(tareaHija.getCompletado(), 0);
                    // Calcular el peso de la tarea hija
                    double duracionReal = tareaHija.getDuracionReal() != null ? tareaHija.getDuracionReal() : 0.0;
                    double pesoTareaHija = duracionReal / sumaDuracionesReales;
                    // Acumular el porcentaje de completado
                    sumaCompletadoAvance += completadoHijo * pesoTareaHija;
                }
            }
        }
        
        return sumaCompletadoAvance;
    }
    
    protected void calcularPorcentajeCompletadoTareaHijoGenerico(TareaPlanTrabajoDto tareaDto,
    		BaseTareaPlanTrabajo tareaEntidad) {
		Integer completado = tareaDto.getCompletado();

		if (completado != null) {
			if (completado == 100) {
				if (tareaEntidad.getFechaInicioReal() == null) {
					tareaEntidad.setFechaInicioReal(tareaEntidad.getFechaInicioPlaneada());
				}
				if (tareaEntidad.getFechaFinReal() == null) {
					tareaEntidad.setFechaFinReal(tareaEntidad.getFechaFinPlaneada());
				}
				if (tareaEntidad.getDuracionReal() == null) {
					tareaEntidad.setDuracionReal(tareaEntidad.getDuracionPlaneada());
				}
			}

			// Actualizar el valor de completado
			tareaEntidad.setPorcentajeCompletado(completado);
		} else {
			tareaEntidad.setPorcentajeCompletado(0);
		}
	}

    
    protected <T extends BaseTareaPlanTrabajo> T copiarTarea(BaseTareaPlanTrabajo tareaRespaldo, Class<T> tipoClase) {
        try {
            // Crear una nueva instancia de la clase especificada
            T tareaRestaurada = tipoClase.getDeclaredConstructor().newInstance();

            // Copiar los atributos comunes
            tareaRestaurada.setIdTarea(tareaRespaldo.getIdTarea());
            tareaRestaurada.setNivelEsquema(tareaRespaldo.getNivelEsquema());
            tareaRestaurada.setNombreTarea(tareaRespaldo.getNombreTarea());
            tareaRestaurada.setActivo(tareaRespaldo.isActivo());
            tareaRestaurada.setDuracionPlaneada(tareaRespaldo.getDuracionPlaneada());
            tareaRestaurada.setFechaInicioPlaneada(tareaRespaldo.getFechaInicioPlaneada());
            tareaRestaurada.setFechaFinPlaneada(tareaRespaldo.getFechaFinPlaneada());
            tareaRestaurada.setDuracionReal(tareaRespaldo.getDuracionReal());
            tareaRestaurada.setFechaInicioReal(tareaRespaldo.getFechaInicioReal());
            tareaRestaurada.setFechaFinReal(tareaRespaldo.getFechaFinReal());
            tareaRestaurada.setPredecesora(tareaRespaldo.getPredecesora());
            tareaRestaurada.setPorcentajePlaneado(tareaRespaldo.getPorcentajePlaneado());
            tareaRestaurada.setPorcentajeCompletado(tareaRespaldo.getPorcentajeCompletado());

            return tareaRestaurada;
        } catch (Exception e) {
            throw new ProyectoException(ErroresEnum.ERROR_COPIA_ARCHIVO);
        }
    }

    
    protected TareaPlanTrabajoDto convertirTareaADto(BaseTareaPlanTrabajo tarea) {
        TareaPlanTrabajoDto tareaDto = new TareaPlanTrabajoDto();
        tareaDto.setIdTarea(tarea.getIdTarea());
        tareaDto.setNivelEsquema(tarea.getNivelEsquema());
        tareaDto.setNombreTarea(tarea.getNombreTarea());
        tareaDto.setActivo(tarea.isActivo());
        tareaDto.setDuracionPlaneada(tarea.getDuracionPlaneada());
        tareaDto.setFechaInicioPlaneada(tarea.getFechaInicioPlaneada());
        tareaDto.setFechaFinPlaneada(tarea.getFechaFinPlaneada());
        tareaDto.setDuracionReal(tarea.getDuracionReal());
        tareaDto.setFechaInicioReal(tarea.getFechaInicioReal());
        tareaDto.setFechaFinReal(tarea.getFechaFinReal());
        tareaDto.setPredecesora(tarea.getPredecesora());
        tareaDto.setPlaneado(tarea.getPorcentajePlaneado());
        tareaDto.setCompletado(tarea.getPorcentajeCompletado());
        tareaDto.setSubRows(new ArrayList<>());

        return tareaDto;
    }


    private void modificarFechaFinReal(TareaPlanTrabajo tareaModificada, Object nuevoValor,
                                       DateTimeFormatter formatter) {
        if (nuevoValor == null) {
            tareaModificada.setFechaFinReal(null);
        } else if (nuevoValor instanceof String nuevoValorC) {
            try {
                LocalDate nuevaFechaFin = LocalDate.parse(nuevoValorC, formatter);
                tareaModificada.setFechaFinReal(nuevaFechaFin);
                if (tareaModificada.getFechaInicioReal() != null) {
                    tareaModificada.setDuracionReal(
                            recalcularDuracionReal(tareaModificada.getFechaInicioReal(), nuevaFechaFin));
                }
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Formato de fecha inválido para 'fechaFinReal'.");
            }
        }
    }

    private void modificarFechaInicioReal(TareaPlanTrabajo tareaModificada, Object nuevoValor,
                                          DateTimeFormatter formatter) {
        if (nuevoValor == null) {
            tareaModificada.setFechaInicioReal(null);
        } else if (nuevoValor instanceof String nuevoValorC) {
            try {
                LocalDate nuevaFechaInicio = LocalDate.parse(nuevoValorC, formatter);
                tareaModificada.setFechaInicioReal(nuevaFechaInicio);
                if (tareaModificada.getFechaFinReal() != null) {
                    tareaModificada.setDuracionReal(
                            recalcularDuracionReal(nuevaFechaInicio, tareaModificada.getFechaFinReal()));
                }
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Formato de fecha inválido para 'fechaInicioReal'.");
            }
        }
    }

    private void modificarDuracionReal(TareaPlanTrabajo tareaModificada, Object nuevoValor) {
        if (nuevoValor instanceof String && nuevoValor.equals("")) {
            tareaModificada.setDuracionReal(0.0);
        } else if (nuevoValor instanceof Double nuevaDuracion) {
            tareaModificada.setDuracionReal(nuevaDuracion);
            if (tareaModificada.getFechaInicioReal() != null) {
                tareaModificada
                        .setFechaFinReal(recalcularFechaFinReal(tareaModificada.getFechaInicioReal(), nuevaDuracion));
            }
        } else if (nuevoValor instanceof Integer nuevoValorC) {
            Double nuevaDuracion = (nuevoValorC).doubleValue();
            tareaModificada.setDuracionReal(nuevaDuracion);
            if (tareaModificada.getFechaInicioReal() != null) {
                tareaModificada
                        .setFechaFinReal(recalcularFechaFinReal(tareaModificada.getFechaInicioReal(), nuevaDuracion));
            }
        }
    }

    protected Double recalcularDuracionReal(LocalDate fechaInicio, LocalDate fechaFin) {
        long duracion = fechaInicio.datesUntil(fechaFin.plusDays(1)).filter(dia -> !esNoLaboral
                        (dia)) // Filtrar los
                // días no
                // laborables
                .count(); // Esto devuelve un long

        return Double.valueOf(duracion);
    }

    private LocalDate recalcularFechaFinReal(LocalDate fechaInicioReal, Double duracionReal) {
        if (fechaInicioReal == null || duracionReal <= 0) {
            throw new IllegalArgumentException(
                    "La fecha de inicio real no puede ser nula y la duración debe ser positiva");
        }

        LocalDate fechaFinReal = fechaInicioReal.minusDays(1); // Iniciar un día antes para calcular correctamente
        int diasLaborablesContados = 0;

        // Iterar hasta contar los días laborables correctos
        while (diasLaborablesContados < duracionReal) {
            fechaFinReal = fechaFinReal.plusDays(1); // Avanzar un día

            // Solo contar si no es un día no laboral
            if (!esNoLaboral(fechaFinReal)) {
                diasLaborablesContados++;
            }
        }

        return fechaFinReal;
    }
}
