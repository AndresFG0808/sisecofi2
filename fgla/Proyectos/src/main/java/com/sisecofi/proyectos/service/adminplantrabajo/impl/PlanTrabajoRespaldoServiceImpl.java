package com.sisecofi.proyectos.service.adminplantrabajo.impl;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sisecofi.libreria.comunes.model.proyectos.adminplantrabajo.PlanTrabajoModel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.adminplantrabajo.DataDto;
import com.sisecofi.proyectos.dto.adminplantrabajo.ListaTareas;
import com.sisecofi.proyectos.dto.adminplantrabajo.ModificacionCompletadoRequest;
import com.sisecofi.proyectos.dto.adminplantrabajo.ModificacionTareaRequest;
import com.sisecofi.proyectos.dto.adminplantrabajo.TareaPlanTrabajoDto;
import com.sisecofi.proyectos.model.adminplantrabajo.TareaPlanTrabajoModel;
import com.sisecofi.proyectos.repository.adminplantrabajo.PlanTrabajoRepository;
import com.sisecofi.proyectos.repository.adminplantrabajo.TareaPlanTrabajoRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.adminplantrabajo.CalculosMasivoService;
import com.sisecofi.proyectos.service.adminplantrabajo.PlanTrabajoRespaldoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PlanTrabajoRespaldoServiceImpl implements PlanTrabajoRespaldoService {

    private final TareaPlanTrabajoRepository tareaPlanTrabajoRepository;
    private final PlanTrabajoRepository planTrabajoRepository;    
    private final PistaService pistaService;
    private final CalculosMasivoService calculosMasivoService;
    private static final String ERROR_FECHA = "La fecha de inicio real no puede ser posterior a la fecha fin real";

    @Override
    public List<ListaTareas> tablaPlanTrabajo(Long idProyecto) {

        List<TareaPlanTrabajoModel> tareaPlan = tareaPlanTrabajoRepository.findAllByIdProyecto(idProyecto);

        //se modifica para que no sea un stream 
        List<DataDto> tareaDto = new ArrayList<>(
                tareaPlan.stream()
                        .map(this::convertirDto)
                        .toList());     

        StringBuilder builder = new StringBuilder();

        builder.append("Id Proyecto: ").append(idProyecto).append("");



         // pistaService.guardarPista(ModuloPista.PROYECTOS.getId(),


         // TipoMovPista.CONSULTA_REGISTRO.getId(),


         // TipoSeccionPista.PROYECTO_DATOS_PLAN_TRABAJO.getIdSeccionPista(),


         // builder.toString(),


         // Optional.empty());

        return jerarquia(tareaDto); // Devuelve solo la lista de tareas
    }

    @Override
    public LocalDateTime obtenerUltimaModificacion(Long idProyecto) {
        PlanTrabajoModel planTrabajo = planTrabajoRepository.findFirstByProyectoModelIdProyecto(idProyecto);
        return planTrabajo.getFecha();
    }

    private List<ListaTareas> jerarquia(List<DataDto> tareas) {
        List<ListaTareas> raiz = new ArrayList<>();
        Map<Integer, ListaTareas> tareaMap = new HashMap<>();
        Deque<ListaTareas> stack = new ArrayDeque<>();

        // ordenar las tareas por idTarea
        tareas.sort(Comparator.comparing(DataDto::getIdTarea));

        for (DataDto tarea : tareas) {
            ListaTareas nodo = new ListaTareas(tarea);
            tareaMap.put(tarea.getIdTarea(), nodo);

            // si el nivel es 0 es una tarea raíz (padre)
            if (tarea.getNivelEsquema() == 0) {
                raiz.add(nodo);// padre raíz
                stack.clear(); // se limpia la pila para empezar una nueva rama
                stack.push(nodo);
            } else {
                // si la tarea actual esta en un nivel menor o igual al ultimo padre se desapila
                while (!stack.isEmpty() && stack.peek().getNivelEsquema() >= tarea.getNivelEsquema()) {
                    stack.pop();
                }

                // asigna la tarea actual al padre que esta en la raiz de la pila
                if (!stack.isEmpty()) {
                    stack.peek().getSubRows().add(nodo); // se considera una tarea hijo del nodo en la cima de la pila
                }

                // se aplia la tarea actual para ostras tareas hijos
                stack.push(nodo);
            }
        }

        return raiz;
    }

    private DataDto convertirDto(TareaPlanTrabajoModel tareaPlanTrabajoModel) {
        return new DataDto(
                tareaPlanTrabajoModel.getIdTarea(),
                tareaPlanTrabajoModel.getNivelEsquema(),
                tareaPlanTrabajoModel.getNombreTarea(),
                tareaPlanTrabajoModel.isActivo(),
                tareaPlanTrabajoModel.getDuracionPlaneada(),
                tareaPlanTrabajoModel.getFechaInicioPlaneada(),
                tareaPlanTrabajoModel.getFechaFinPlaneada(),
                tareaPlanTrabajoModel.getDuracionReal(),
                tareaPlanTrabajoModel.getFechaInicioReal(),
                tareaPlanTrabajoModel.getFechaFinReal(),
                tareaPlanTrabajoModel.getPredecesora(),
                tareaPlanTrabajoModel.getPorcentajePlaneado(),
                tareaPlanTrabajoModel.getPorcentajeCompletado());

    }

    @Transactional
    @Override
    public List<TareaPlanTrabajoDto> modificarTareasOriginales(Long idProyecto,
            List<ModificacionTareaRequest> modificaciones) {

        // obtiene todas las tareas actuales
        List<TareaPlanTrabajoModel> tareasOriginales = tareaPlanTrabajoRepository
                .findByPlanTrabajoModelProyectoModelIdProyecto(idProyecto);

        // proceso de cada modificacion
        for (ModificacionTareaRequest modificacion : modificaciones) {
            TareaPlanTrabajoModel tareaModificada = buscarTareaOriginalPorId(tareasOriginales,
                    modificacion.getIdTarea());

                // procesar fechaInicioReal si se proporciona

                LocalDate nuevaFechaInicio = tareaModificada.getFechaInicioReal();
                LocalDate nuevaFechaFin = tareaModificada.getFechaFinReal();
                Double nuevaDuracion;
                nuevaFechaInicio = convertirAFecha(modificacion.getFechaInicioReal(), modificacion, nuevaFechaInicio);
                   
                // proceso fechFinReal si se proporciona

                nuevaFechaFin = convertirAFecha(modificacion.getFechaFinReal(), modificacion, nuevaFechaFin);

                // proceso duracionReal si se proporciona

                nuevaDuracion = obtenerDuracion(modificacion.getDuracionReal(), modificacion, tareaModificada.getDuracionReal(), tareaModificada, nuevaFechaFin);
                
                switch (obtenerCaso(modificacion, tareaModificada)) {
                case 1:
                	// Prioridad 1: si ambas fechas están definidas, calcular duracion real
                	// validar que la fecha inicio no sea posterior a la fecha fin
                	validarFechas(nuevaFechaInicio, nuevaFechaFin, ERROR_FECHA);
                	
                    tareaModificada.setFechaInicioReal(nuevaFechaInicio);
                    tareaModificada.setFechaFinReal(nuevaFechaFin);
                    tareaModificada.setDuracionReal(recalcularDuracionReal(nuevaFechaInicio, nuevaFechaFin));
                    break;
                case 2:
                	// Prioridad 2: Si se proporciona explícitamente fecha inicio y duración
                	tareaModificada.setFechaInicioReal(nuevaFechaInicio);
                    tareaModificada.setDuracionReal(nuevaDuracion);

                    try {
                        LocalDate fechaFinCalulada = recalcularFechaFinReal(nuevaFechaInicio, nuevaDuracion);
                        tareaModificada.setFechaFinReal(fechaFinCalulada);
                    } catch (IllegalArgumentException e) {
                        tareaModificada.setFechaFinReal(null);
                    }
                    break;
                case 3:
                	// Caso 3: Si solo se modifica la fecha inicio y ya existe fecha fin
                	// Validar que la nueva fecha inicio no sea posterior a la fecha fin existente
                	validarFechas(nuevaFechaInicio, tareaModificada.getFechaFinReal(), ERROR_FECHA);

                    tareaModificada.setFechaInicioReal(nuevaFechaInicio);
                    // Recalcular la duración con la nueva fecha inicio y la fecha fin existente
                    tareaModificada.setDuracionReal(
                            recalcularDuracionReal(nuevaFechaInicio, tareaModificada.getFechaFinReal()));
                    break;
                case 4:
                	// Caso 4: Si solo se modifica la fecha fin y ya existe fecha inicio
                	// Validar que la fecha inicio existente no sea posterior a la nueva fecha fin
                	validarFechas(tareaModificada.getFechaInicioReal(), nuevaFechaFin, ERROR_FECHA);

                    tareaModificada.setFechaFinReal(nuevaFechaFin);
                    // Recalcular la duración con la fecha inicio existente y la nueva fecha fin
                    tareaModificada.setDuracionReal(
                            recalcularDuracionReal(tareaModificada.getFechaInicioReal(), nuevaFechaFin));
                    break;
                case 5:
                	// Caso 5: Si solo se modifica la duración y ya existen fechas
                	tareaModificada.setDuracionReal(nuevaDuracion);

                    try {
                        LocalDate fechaFinCalculada = recalcularFechaFinReal(tareaModificada.getFechaInicioReal(),
                                nuevaDuracion);
                        tareaModificada.setFechaFinReal(fechaFinCalculada);
                    } catch (IllegalArgumentException e) {
                        tareaModificada.setFechaFinReal(null);
                    }
                    break;
                default:
                	// Caso por defecto
                	validarFechas(nuevaFechaInicio, nuevaFechaFin, ERROR_FECHA);
                    
                    tareaModificada.setFechaInicioReal(nuevaFechaInicio);

                    // Validar que la fecha fin real no sea menor a la fecha inicio planeada
                    validarFechas (tareaModificada.getFechaInicioPlaneada(), nuevaFechaFin, "La fecha fin real no puede ser menor a la fecha inicio planeada");
             

                    // Si hay una fecha fin real, establecer porcentaje completado en 100%
                    if (nuevaFechaFin != null) {
                        tareaModificada.setPorcentajeCompletado(100);
                    }
                    tareaModificada.setFechaFinReal(nuevaFechaFin);
                    tareaModificada.setDuracionReal(nuevaDuracion);
            }

                tareaPlanTrabajoRepository.save(tareaModificada);


        } // for

        recalcularFechasPadre(tareasOriginales);

        // pistas de Auditoria

        StringBuilder builder = new StringBuilder();
        builder.append("Id proyecto: ").append(idProyecto).append(" - Plan de trabajo - Cálculos masivos");

        // pistaService.guardarPista(

        // ModuloPista.PROYECTOS.getId(),

        // TipoMovPista.ACTUALIZA_REGISTRO.getId(),

        // TipoSeccionPista.PROYECTO_DATOS_PLAN_TRABAJO.getIdSeccionPista(),

        // builder.toString(),

        // Optional.empty());

        return construirJerarquiaTareasOriginal(tareasOriginales);

    }
    
    private LocalDate convertirAFecha(String str, ModificacionTareaRequest modificacion, LocalDate fechaOriginal) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	if (str != null) {
            try {

            	return LocalDate.parse(str, formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("formato de fecha inicio invalida para 'fechaInicioReal' ");
            }
        } else if (modificacion.isClearFechaInicioReal()) {
            return  null;
        }else {
        	return fechaOriginal;
        }
    	
    }
    
    private Double obtenerDuracion(Double duracion, ModificacionTareaRequest modificacion, Double duracionOriginal, TareaPlanTrabajoModel tareaModificada, LocalDate nuevaFechaFin) {
    	Double nuevaDuracion;
    	if (duracion != null) {
            nuevaDuracion = modificacion.getDuracionReal();

        } else if (modificacion.isClearDuracionReal()) {
            nuevaDuracion = 0.0;
        }else {
        	nuevaDuracion= duracionOriginal;
        }

        if (nuevaDuracion == 0 && tareaModificada.getFechaInicioReal() != null) {
            tareaModificada.setFechaFinReal(tareaModificada.getFechaInicioReal());
        }
        // NUEVA REGLA: Si hay una fecha fin real, establecer porcentaje completado en
        // 100%
        if (nuevaFechaFin != null) {
            tareaModificada.setPorcentajeCompletado(100);
        }
        
        return nuevaDuracion;
    }
    
    private void validarFechas(LocalDate inicio, LocalDate fin, String str) {
    	if (inicio != null && fin != null && inicio.isAfter(fin)) {
            throw new IllegalArgumentException(
            		str);
        }
    }
    
    private int obtenerCaso(ModificacionTareaRequest modificacion, TareaPlanTrabajoModel tareaModificada) {
    	if (modificacion.getFechaInicioReal() != null && modificacion.getFechaFinReal() != null) {
            return 1;
        }

        // Prioridad 2: Si se proporciona explícitamente fecha inicio y duración
        else if (modificacion.getFechaInicioReal() != null && modificacion.getDuracionReal() != null
                && modificacion.getDuracionReal() == 0) {
        	return 2;
        }

        // Caso 3: Si solo se modifica la fecha inicio y ya existe fecha fin
        else if (modificacion.getFechaInicioReal() != null && tareaModificada.getFechaFinReal() != null) {
        	return 3;
        }

        // Caso 4: Si solo se modifica la fecha fin y ya existe fecha inicio
        else if (modificacion.getFechaFinReal() != null && tareaModificada.getFechaInicioReal() != null) {
        	return 4;
            
        }

        // Caso 5: Si solo se modifica la duración y ya existen fechas
        else if (modificacion.getDuracionReal() != null && tareaModificada.getFechaInicioReal() != null) {
        	return 5;
        }

        // Caso por defecto
        else {
        	return 6;
        }
    }

    public TareaPlanTrabajoDto convertirA(TareaPlanTrabajoModel tarea) {
        TareaPlanTrabajoDto dto = new TareaPlanTrabajoDto();
        dto.setIdTarea(tarea.getIdTarea());
        dto.setNivelEsquema(tarea.getNivelEsquema());
        dto.setNombreTarea(tarea.getNombreTarea());
        dto.setActivo(tarea.isActivo());
        dto.setDuracionPlaneada(tarea.getDuracionPlaneada());
        dto.setFechaInicioPlaneada(tarea.getFechaInicioPlaneada());
        dto.setFechaFinPlaneada(tarea.getFechaFinPlaneada());
        dto.setDuracionReal(tarea.getDuracionReal());
        dto.setFechaInicioReal(tarea.getFechaInicioReal());
        dto.setFechaFinReal(tarea.getFechaFinReal());
        dto.setPredecesora(tarea.getPredecesora());
        dto.setPlaneado(tarea.getPorcentajePlaneado());
        dto.setCompletado(tarea.getPorcentajeCompletado());
        return dto;
    }

    private void recalcularFechasPadre(List<TareaPlanTrabajoModel> tareasTemporales) {
        List<TareaPlanTrabajoDto> jerarquia = construirJerarquiaTareasOriginal(tareasTemporales);

        recalcularFechasPadreRecursivamente(jerarquia, tareasTemporales);
    }

    private void recalcularFechasPadreRecursivamente(List<TareaPlanTrabajoDto> tareas,
            List<TareaPlanTrabajoModel> tareasOriginales) {
        for (TareaPlanTrabajoDto tarea : tareas) {
            if (!tarea.getSubRows().isEmpty()) {
                recalcularFechasPadreRecursivamente(tarea.getSubRows(), tareasOriginales);

                LocalDate fechaInicioRealMin = tarea.getSubRows().stream()
                        .map(TareaPlanTrabajoDto::getFechaInicioReal)
                        .filter(Objects::nonNull)
                        .min(LocalDate::compareTo)
                        .orElse(null);

                LocalDate fechaFinRealMax = tarea.getSubRows().stream()
                        .map(TareaPlanTrabajoDto::getFechaFinReal)
                        .filter(Objects::nonNull)
                        .max(LocalDate::compareTo)
                        .orElse(null);

                // Si alguna subtarea tiene duración real = 0, el padre también debe reflejar 0
                boolean algunaDuracionEsCero = tarea.getSubRows().stream()
                        .map(TareaPlanTrabajoDto::getDuracionReal)
                        .filter(Objects::nonNull)
                        .anyMatch(duracion -> duracion == 0);

                double nuevaDuracionReal = tarea.getSubRows().stream()
                        .map(TareaPlanTrabajoDto::getDuracionReal)
                        .filter(Objects::nonNull)
                        .mapToDouble(Double::doubleValue)
                        .sum();

                if (algunaDuracionEsCero) {
                    nuevaDuracionReal = 0.0;
                }

                tarea.setFechaInicioReal(fechaInicioRealMin);
                tarea.setFechaFinReal(fechaFinRealMax);
                tarea.setDuracionReal(nuevaDuracionReal);

                TareaPlanTrabajoModel tareaPadreEntity = buscarTareaOriginalPorId(tareasOriginales, tarea.getIdTarea());
                if (tareaPadreEntity != null) {
                    tareaPadreEntity.setFechaInicioReal(fechaInicioRealMin);
                    tareaPadreEntity.setFechaFinReal(fechaFinRealMax);
                    tareaPadreEntity.setDuracionReal(nuevaDuracionReal);
                    tareaPlanTrabajoRepository.save(tareaPadreEntity);
                }
            }
        }
    }

    // busca las tareas originales
    private TareaPlanTrabajoModel buscarTareaOriginalPorId(List<TareaPlanTrabajoModel> tareas,
            Integer idTarea) {
        for (TareaPlanTrabajoModel tarea : tareas) {
            if (tarea.getIdTarea().equals(idTarea)) {
                return tarea;
            }
        }
        throw new IllegalArgumentException("No se encontró la tarea con ID: " + idTarea);
    }

    /*
     * Este métod sirve para acoplar las tareas que se envia en el excel y
     * acomodarlas de acuerdo a su nivel de esquema
     * jerarquía:
     * Nivel esquema 0 = Tarea Raiz (Padre de todas las tareas)
     * Nivel esquema 1 = Tarea padre hija de la raíz
     * Nivel esquema 2 a N =(Son las que se consideran hijas, pero a la vez se
     * condiseran padres, depende de la posición)
     */
    private List<TareaPlanTrabajoDto> construirJerarquiaTareasOriginal(
            List<TareaPlanTrabajoModel> tareasOriginales) {
        
        return calculosMasivoService.construirJerarquiaTareasOriginal(tareasOriginales);
    }

    private LocalDate recalcularFechaFinReal(LocalDate fechaInicioReal, Double duracionReal) {
        if (fechaInicioReal == null || duracionReal < 0) {
            throw new IllegalArgumentException("La fecha de inicio real no puede ser nula y la duración no puede ser negativa");
        }

        // Si la duración es 0, la fecha fin es igual a la fecha inicio
        if (duracionReal == 0) {
            return fechaInicioReal;
        }

        LocalDate fechaFinReal = fechaInicioReal.minusDays(1);
        int diasLaborablesContados = 0;

        while (diasLaborablesContados < duracionReal) {
            fechaFinReal = fechaFinReal.plusDays(1);
            if (!esNoLaboral(fechaFinReal)) {
                diasLaborablesContados++;
            }
        }

        return fechaFinReal;
    }

    private Double recalcularDuracionReal(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }

        if (fechaInicio.equals(fechaFin)) {
            return 1.0;
        }

        long duracion = fechaInicio.datesUntil(fechaFin.plusDays(1))
                .filter(dia -> !esNoLaboral(dia))
                .count();

        return Double.valueOf(duracion);
    }

    private boolean esNoLaboral(LocalDate fecha) {
        DayOfWeek diaDeLaSemana = fecha.getDayOfWeek();

        // Verificar si es sábado o domingo
        if (diaDeLaSemana == DayOfWeek.SATURDAY || diaDeLaSemana == DayOfWeek.SUNDAY) {
            return true;
        }

        List<LocalDate> festivos = Arrays.asList(
                LocalDate.of(fecha.getYear(), 1, 1),
                LocalDate.of(fecha.getYear(), 12, 25));

        return festivos.contains(fecha);
    }


    /*
     * Este metodo se utiliza para calcular el porcentaje completado de las tareas,
     * funciona automaticamente, pero tambien se modifico
     * para que se calculara dentro de una lista de que se envia en front
     */

    @Transactional
    @Override
    public List<TareaPlanTrabajoDto> calcularPorcentajeListaTareas(Long idProyecto,
            List<ModificacionCompletadoRequest> modificaciones) {
        // Obtener todas las tareas del proyecto
        List<TareaPlanTrabajoModel> tareasOriginales = tareaPlanTrabajoRepository
                .findByPlanTrabajoModelProyectoModelIdProyecto(idProyecto);

        List<TareaPlanTrabajoDto> tareasJerarquicas = construirJerarquiaTareasOriginal(tareasOriginales);

        // Procesar modificaciones manuales (si existen)
        if (modificaciones != null && !modificaciones.isEmpty()) {
        	modificarTareas(modificaciones, tareasJerarquicas, tareasOriginales);
        } else {
            // En lugar de sobrescribir el completado, verificamos si ya tiene un valor
            // antes de actualizarlo
            for (TareaPlanTrabajoModel tareaEntidad : tareasOriginales) {
                TareaPlanTrabajoDto tareaDto = buscarTareaPorId(tareasJerarquicas, tareaEntidad.getIdTarea());

                if (tareaDto != null && 
                	    (tareaDto.getSubRows().isEmpty())) {
                        // Solo actualizar si el porcentaje aún no ha sido asignado
                	actualizarPorcentajes(tareaEntidad, tareaDto);
                    
                }
            }
        }

        // Recalcular porcentajes para todas las tareas jerárquicas
        calculoPorcentajesTareas(tareasJerarquicas, tareasOriginales);

        // Guardar los cambios finales
        tareaPlanTrabajoRepository.saveAll(tareasOriginales);

        return tareasJerarquicas;
    }
    
    private void calculoPorcentajesTareas(List<TareaPlanTrabajoDto> tareasJerarquicas, List<TareaPlanTrabajoModel> tareasOriginales) {
    	 for (TareaPlanTrabajoDto tarea : tareasJerarquicas) {
             calcularPorcentajeTarea(tarea, tareasOriginales);
         }

         // Calcular porcentajes de tareas raíz (nivel esquema 0)
         for (TareaPlanTrabajoDto tareaRaiz : tareasJerarquicas ) {
             if (tareaRaiz.getNivelEsquema() == 0) {
                 calcularPorcentajeRaiz(tareaRaiz);
             }
         }
    }
    
    private void modificarTareas(List<ModificacionCompletadoRequest> modificaciones, List<TareaPlanTrabajoDto> tareasJerarquicas, List<TareaPlanTrabajoModel> tareasOriginales) {
    	for (ModificacionCompletadoRequest modificacion : modificaciones) {
            Integer idTarea = modificacion.getIdTarea();
            Integer completadoRequest = modificacion.getCompletado();

            if (idTarea != null && completadoRequest != null) {
                TareaPlanTrabajoDto tareaModificada = buscarTareaPorId(tareasJerarquicas, idTarea);

                if (tareaModificada != null) {
                    tareaModificada.setCompletado(completadoRequest);
                    TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasOriginales, idTarea);
                   
                        validarCamposPorCompletado(tareaEntidad, tareaModificada, completadoRequest);
                        tareaEntidad.setPorcentajeCompletado(completadoRequest);
                        tareaPlanTrabajoRepository.save(tareaEntidad);
                    
                }
            }
        }
    }
    
    private void actualizarPorcentajes(TareaPlanTrabajoModel tareaEntidad, TareaPlanTrabajoDto tareaDto) {
    	if (tareaEntidad.getPorcentajeCompletado() == null) {
            actualizarPorcentajeCompletadoAutomatico(tareaDto, tareaEntidad);
        }

        // Validar que el porcentaje no sea nulo antes de actualizar
        Integer porcentajeCompletado = tareaEntidad.getPorcentajeCompletado();
        if (porcentajeCompletado != null) {
            validarCamposPorCompletado(tareaEntidad, tareaDto, porcentajeCompletado);
        }
    }
    

    /**
     * Actualiza automáticamente el porcentaje completado de una tarea hija basado
     * en sus fechas reales.
     * Si la tarea tiene fechaInicioReal, fechaFinReal y duracionReal, se calcula el
     * porcentaje completado.
     * 
     * @param tareaDto     El DTO de la tarea a actualizar
     * @param tareaEntidad La entidad de la tarea a actualizar
     * 
     */
    private void actualizarPorcentajeCompletadoAutomatico(TareaPlanTrabajoDto tareaDto,
            TareaPlanTrabajoModel tareaEntidad) {

        // Validar si la tarea es de nivelEsquema 0
        if (tareaDto.getNivelEsquema() == 0) {
            // No realizar actualización automática de porcentaje para tareas raíz
            return;
        }

        // Si tiene todas las fechas reales establecidas
        if (tareaEntidad.getFechaInicioReal() != null &&
                tareaEntidad.getFechaFinReal() != null &&
                tareaEntidad.getDuracionReal() != null) {

            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaInicioReal = tareaEntidad.getFechaInicioReal();
            LocalDate fechaFinReal = tareaEntidad.getFechaFinReal();

            // Si la fecha fin real es menor o igual a la fecha actual, está 100% completado
            if (!fechaFinReal.isAfter(fechaActual)) {
                tareaEntidad.setPorcentajeCompletado(100);
                tareaDto.setCompletado(100);
            }
            // Si la fecha actual es menor a la fecha inicio real, está 0% completado
            else if (fechaActual.isBefore(fechaInicioReal)) {
                tareaEntidad.setPorcentajeCompletado(0);
                tareaDto.setCompletado(0);
            }
            // Si estamos entre fecha inicio y fin real, calcular porcentaje
            else {
                // Calcular días transcurridos excluyendo días no laborables
                long diasTranscurridos = calcularDiasTranscurridos(fechaInicioReal);

                // Calcular días totales excluyendo días no laborables
                long diasTotales = fechaInicioReal.datesUntil(fechaFinReal.plusDays(1))
                        .filter(fecha -> !esNoLaboral(fecha))
                        .count();

                if (diasTotales > 0) {
                    int porcentajeCompletado = (int) ((diasTranscurridos * 100) / diasTotales);
                    porcentajeCompletado = Math.min(100, Math.max(0, porcentajeCompletado));

                    tareaEntidad.setPorcentajeCompletado(porcentajeCompletado);
                    tareaDto.setCompletado(porcentajeCompletado);
                } else {
                    // Si no hay días laborables entre inicio y fin
                    tareaEntidad.setPorcentajeCompletado(0);
                    tareaDto.setCompletado(0);
                }
            }
        }
        // No actualizar porcentaje si falta alguna fecha real
    }

    private void calcularPorcentajeRaiz(TareaPlanTrabajoDto tareaRaiz) {
        if (tareaRaiz.getSubRows() != null && !tareaRaiz.getSubRows().isEmpty()) {
            int sumaPorcentajes = 0;
            int totalTareas = tareaRaiz.getSubRows().size();

            for (TareaPlanTrabajoDto subTarea : tareaRaiz.getSubRows()) {
                // Manejo seguro para evitar NullPointerException
                int completadoHijo = (subTarea.getCompletado() != null) ? subTarea.getCompletado() : 0;
                sumaPorcentajes += completadoHijo;
            }

            int porcentajePromedio = sumaPorcentajes / totalTareas;
            tareaRaiz.setCompletado(porcentajePromedio);
        }
    }

    private TareaPlanTrabajoDto buscarTareaPorId(List<TareaPlanTrabajoDto> tareas, Integer idTarea) {
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

    /*
     * se agrega validacion extra para pista de auditoria, ya que solo se tomara una
     * pista por modificación de tarea, esto para evitar que salga pista por tarea
     */

    // se agrega AuditoriaEstado para manetener el estado en una unica ejecución
    private static class AuditoriaEstado {
        boolean registrada = false;
    }

    private void calcularPorcentajeTarea(TareaPlanTrabajoDto tareaDto, List<TareaPlanTrabajoModel> tareasTemporales) {
        AuditoriaEstado auditoriaEstado = new AuditoriaEstado();
        calcularPorcentajeTareaRecursivo(tareaDto, tareasTemporales, auditoriaEstado);
    }

    private void calcularPorcentajeTareaRecursivo(TareaPlanTrabajoDto tareaDto,
            List<TareaPlanTrabajoModel> tareasTemporales,
            AuditoriaEstado auditoriaEstado) {

        // Recursivamente calcular para las subRows (tareas hijas) antes de calcular el
        // padre
        if (tareaDto.getSubRows() != null && !tareaDto.getSubRows().isEmpty()) {
            for (TareaPlanTrabajoDto subTarea : tareaDto.getSubRows()) {
                calcularPorcentajeTareaRecursivo(subTarea, tareasTemporales, auditoriaEstado);
            }
        }

        // Buscar la entidad asociada a este DTO
        TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasTemporales, tareaDto.getIdTarea());

        if (tareaDto.isActivo()) {
            if (tareaDto.getSubRows() == null || tareaDto.getSubRows().isEmpty()) {
                // Si es una tarea hija (sin subRows)
                calcularPorcentajePlaneadoTareaHijo(tareaDto, tareaEntidad);
                calcularPorcentajeCompletadoTareaHijo(tareaDto, tareaEntidad);
            } else {
                // Si es una tarea padre, calcula porcentajes basado en sus hijas
                calcularPorcentajePlaneadoTareaPadre(tareaDto, tareasTemporales);
                calcularPorcentajeCompletadoTareaPadre(tareaDto, tareasTemporales);
            }

            // Genera el mensaje de auditoría solo una vez
            if (!auditoriaEstado.registrada) {
                StringBuilder auditoriaProyecto = new StringBuilder();
                auditoriaProyecto.append("Id de proyecto: ")
                        .append(tareaEntidad.getPlanTrabajoModel().getProyectoModel().getIdProyecto())
                        .append(" - Plan de trabajo - Cálculos masivos");

                // pistaService.guardarPista(

                // ModuloPista.PROYECTOS.getId(),

                // TipoMovPista.ACTUALIZA_REGISTRO.getId(),

                // TipoSeccionPista.PROYECTO_DATOS_PLAN_TRABAJO.getIdSeccionPista(),

                // auditoriaProyecto.toString(),

                // Optional.empty());

                auditoriaEstado.registrada = true; // Marcar que la auditoría ya fue

            }

        }
    }

    private void calcularPorcentajeCompletadoTareaPadre(TareaPlanTrabajoDto tareaPadre,
            List<TareaPlanTrabajoModel> tareasOriginales) {
    	calculosMasivoService.calcularPorcentajeCompletadoTareaPadre(tareaPadre,tareasOriginales );
    }
    

    private void calcularPorcentajeCompletadoTareaHijo(TareaPlanTrabajoDto tareaDto,
            TareaPlanTrabajoModel tareaEntidad) {
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

    // CALCULOS PLANEADOS

    // Método para calcular el porcentaje planeado de una tarea padre basado en el
    // progreso de las subtareas
    private double calcularPorcentajePlaneadoTareaPadre(TareaPlanTrabajoDto tareaDto,
            List<TareaPlanTrabajoModel> tareasEntidades) {
        double sumaPorcentajesEquivalentes = 0.0;
        double sumaDuracionesHijas = 0.0;

        // Procesa cada subtarea activa de la tarea padre
        for (TareaPlanTrabajoDto tareaHijaDto : tareaDto.getSubRows()) {
            if (tareaHijaDto.isActivo()) {
                double porcentajePlaneadoHijo;

                if (!tareaHijaDto.getSubRows().isEmpty()) {
                    // Si tiene subtareas, calcular recursivamente
                    porcentajePlaneadoHijo = calcularPorcentajePlaneadoTareaPadre(tareaHijaDto, tareasEntidades);
                } else {
                    // Calcular el porcentaje planeado para una tarea sin subtareas hasta la fecha
                    // actual
                    long diasTranscurridos = calcularDiasTranscurridosCorte(tareaHijaDto.getFechaInicioPlaneada());
                    porcentajePlaneadoHijo = (diasTranscurridos > 0)
                            ? Math.min(diasTranscurridos / tareaHijaDto.getDuracionPlaneada() * 100, 100)
                            : 0;
                    tareaHijaDto.setPlaneado((int) Math.round(porcentajePlaneadoHijo));

                    // Actualizar el porcentaje planeado en la entidad
                    TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasEntidades,
                            tareaHijaDto.getIdTarea());
                    
                        tareaEntidad.setPorcentajePlaneado((int) Math.round(porcentajePlaneadoHijo));
                    
                }

                // Ponderar subtareas en base a su duración planeada
                double pesoTareaHija = tareaHijaDto.getDuracionPlaneada();
                sumaPorcentajesEquivalentes += porcentajePlaneadoHijo * pesoTareaHija;
                sumaDuracionesHijas += pesoTareaHija;

            }
        }

        int porcentajePlaneadoTotal= calcularPorcentajePlaneado(tareaDto, sumaDuracionesHijas, sumaPorcentajesEquivalentes);

        tareaDto.setPlaneado(porcentajePlaneadoTotal);

        // Actualizar la entidad de la tarea padre en la base de datos
        TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasEntidades, tareaDto.getIdTarea());
        
            tareaEntidad.setPorcentajePlaneado(porcentajePlaneadoTotal);
        
        return porcentajePlaneadoTotal;
    }
    
    private int calcularPorcentajePlaneado(TareaPlanTrabajoDto tareaDto, double sumaDuracionesHijas, double sumaPorcentajesEquivalentes) {
    	int porcentajePlaneadoTotal;
    	if (tareaDto.getIdTarea() > 0) {
            porcentajePlaneadoTotal = (sumaDuracionesHijas > 0)
                    ? (int) Math.round(sumaPorcentajesEquivalentes / sumaDuracionesHijas)
                    : 0;
        } else {
            porcentajePlaneadoTotal = (sumaDuracionesHijas > 0)
                    ? (int) (sumaPorcentajesEquivalentes / sumaDuracionesHijas)
                    : 0;
        }
    	
    	return porcentajePlaneadoTotal;
    }

    // Método para calcular días transcurridos hasta la fecha actual, excluyendo no
    // laborables
    private int calcularDiasTranscurridosCorte(LocalDate fechaInicioPlaneada) {
        LocalDate fechaActual = LocalDate.now();

        if (!fechaInicioPlaneada.isAfter(fechaActual)) {
            return (int) fechaInicioPlaneada.datesUntil(fechaActual.plusDays(1))
                    .filter(dia -> !esNoLaboral(dia))
                    .count();
        } else {
            return 0;
        }
    }

    private void calcularPorcentajePlaneadoTareaHijo(TareaPlanTrabajoDto tareaDto,
            TareaPlanTrabajoModel tareaEntidad) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioPlaneada = tareaDto.getFechaInicioPlaneada();
        LocalDate fechaFinPlaneada = tareaDto.getFechaFinPlaneada();

        if (fechaInicioPlaneada != null && fechaFinPlaneada != null && !fechaInicioPlaneada.isAfter(fechaActual)) {
            long totalDiasPlaneados = ChronoUnit.DAYS.between(fechaInicioPlaneada, fechaFinPlaneada) + 1;
            if (totalDiasPlaneados > 0) {
                long diasTranscurridos = calcularDiasTranscurridos(fechaInicioPlaneada);

                int porcentajePlaneado = (int) ((diasTranscurridos * 100) / totalDiasPlaneados);
                porcentajePlaneado = Math.min(100, Math.max(0, porcentajePlaneado));
                tareaEntidad.setPorcentajePlaneado(porcentajePlaneado);
                tareaDto.setPlaneado(porcentajePlaneado);

            } else {
                tareaEntidad.setPorcentajePlaneado(0);
                tareaDto.setPlaneado(0);
            }
        } else {
            tareaEntidad.setPorcentajePlaneado(0);
            tareaDto.setPlaneado(0);
        }
    }

    private int calcularDiasTranscurridos(LocalDate fechaInicioPlaneada) {
        LocalDate fechaActual = LocalDate.now();

        if (!fechaInicioPlaneada.isAfter(fechaActual)) {

            long diasTranscurridosLong = fechaInicioPlaneada
                    .datesUntil(fechaActual.plusDays(1))
                    .filter(fecha -> !esNoLaboral(fecha))
                    .count();
    
            return Math.toIntExact(diasTranscurridosLong);
        } else {
            return 0;
        }
    }

    /**
     * Valida y actualiza los campos relacionados con el porcentaje de completado de
     * una tarea.
     * Si el porcentaje es mayor a 0 y menor a 100, se asegura que tenga fecha de
     * inicio real.
     * Si el porcentaje es 100, se asegura que tenga todos los campos reales
     * establecidos.
     * 
     * @param tareaEntidad      La tarea a validar y actualizar
     * @param tareaModificada   El DTO de la tarea que se está modificando
     * @param completadoRequest El nuevo porcentaje de completado
     */
    private void validarCamposPorCompletado(TareaPlanTrabajoModel tareaEntidad, TareaPlanTrabajoDto tareaModificada,
            Integer completadoRequest) {
        // Verificar si completadoRequest es nulo
        if (completadoRequest == null) {
            return; // Salir si no hay valor válido de completadox
        }

        if (completadoRequest > 0) {
            // Asignar fecha de inicio real si es nula
            if (tareaEntidad.getFechaInicioReal() == null) {
                tareaEntidad.setFechaInicioReal(tareaEntidad.getFechaInicioPlaneada());
                tareaModificada.setFechaInicioReal(tareaEntidad.getFechaInicioPlaneada());
            }

            // Si el porcentaje es 100%
            if (completadoRequest == 100) {
            	validarCamposPorCompletadoComplemento (tareaEntidad, tareaModificada);

            } else if (completadoRequest < 100) {
                // Validación adicional cuando el porcentaje baja
                tareaEntidad.setFechaFinReal(null); // Remover fecha de fin real
                tareaModificada.setFechaFinReal(null);// Actualizar el DTO

                // Mantener el 0 si ya estaba establecido
                if (tareaEntidad.getDuracionReal() != null && tareaEntidad.getDuracionReal() == 0) {
                    tareaModificada.setDuracionReal(0.0);
                } else {
                    tareaEntidad.setDuracionReal(null);
                    tareaModificada.setDuracionReal(null);
                }
            }
        }
    }
    
    private void validarCamposPorCompletadoComplemento(TareaPlanTrabajoModel tareaEntidad, TareaPlanTrabajoDto tareaModificada) {
    	if (tareaEntidad.getFechaFinReal() == null) {
            tareaEntidad.setFechaFinReal(tareaEntidad.getFechaFinPlaneada());
            tareaModificada.setFechaFinReal(tareaEntidad.getFechaFinPlaneada());
        }

        // Recalcular duración real basada en las fechas
        if (tareaEntidad.getFechaInicioReal() != null && tareaEntidad.getFechaFinReal() != null) {
            Double duracionCalculada = recalcularDuracionReal(tareaEntidad.getFechaInicioReal(),
                    tareaEntidad.getFechaFinReal());

            // Mantener el 0 si ya estaba establecido
            if (tareaEntidad.getDuracionReal() != null && tareaEntidad.getDuracionReal() == 0) {
                duracionCalculada = 0.0;
            }

            tareaEntidad.setDuracionReal(duracionCalculada);
            tareaModificada.setDuracionReal(duracionCalculada);
        }
    }

}
