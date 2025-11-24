package com.sisecofi.proyectos.service.adminplantrabajo.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proyectos.dto.adminplantrabajo.TareaPlanTrabajoDto;
import com.sisecofi.proyectos.model.adminplantrabajo.TareaPlanTrabajoModel;
import com.sisecofi.proyectos.repository.ProyectoRepository;
import com.sisecofi.proyectos.repository.adminplantrabajo.TareaPlanTrabajoRepository;
import com.sisecofi.proyectos.service.PistaService;
import com.sisecofi.proyectos.service.adminplantrabajo.CalculosMasivoService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class CalculosMasivoServiceImpl implements CalculosMasivoService {

    private final TareaPlanTrabajoRepository tareaPlanTrabajoRepository;
    private final ProyectoRepository proyectoRepository;
    private final PistaService pistaService;

    // Calculo masivo para todos los proyectos usando el servicio de pista
    @Transactional
    @Override
    public List<TareaPlanTrabajoDto> calculosMasivos() {
        // Obtener todos los proyectos activos
        List<ProyectoModel> proyectosActivos = proyectoRepository.findByEstatus(true);
        List<TareaPlanTrabajoDto> resultadoGlobal = new ArrayList<>();

        for (ProyectoModel proyecto : proyectosActivos) {
            Long idProyecto = proyecto.getIdProyecto();

            // Obtener todas las tareas temporales del proyecto actual
            List<TareaPlanTrabajoModel> tareasOriginales = tareaPlanTrabajoRepository
                    .findByPlanTrabajoModelProyectoModelIdProyecto(idProyecto);

            // Construir jerarquía de tareas
            List<TareaPlanTrabajoDto> tareasJerarquicas = construirJerarquiaTareasOriginal(tareasOriginales);

            // Calcular el porcentaje planeado para cada tarea en la jerarquía
            for (TareaPlanTrabajoDto tarea : tareasJerarquicas) {
                calcularPorcentajePlaneadoTareaMa(tarea, tareasOriginales);
                calcularPorcentajeCompletadoTarea(tarea, tareasOriginales);

            }

            // Guardar el cálculo en las tareas del proyecto actual
            tareaPlanTrabajoRepository.saveAll(tareasOriginales);

            // Agregar el resultado de este proyecto a la lista global
            resultadoGlobal.addAll(tareasJerarquicas);
        }

        return resultadoGlobal;
    }

    // calculo masivo interno sin pistas
    @Transactional
    @Override
    public List<TareaPlanTrabajoDto> calcularMasivosCorreos() {
        // Obtener todos los proyectos activos
        List<ProyectoModel> proyectosActivos = proyectoRepository.findByEstatus(true);
        List<TareaPlanTrabajoDto> resultadoGlobal = new ArrayList<>();

        for (ProyectoModel proyecto : proyectosActivos) {
            Long idProyecto = proyecto.getIdProyecto();

            // Obtener todas las tareas temporales del proyecto actual
            List<TareaPlanTrabajoModel> tareasTemporales = tareaPlanTrabajoRepository
                    .findByPlanTrabajoModelProyectoModelIdProyecto(idProyecto);

            // Construir jerarquía de tareas
            List<TareaPlanTrabajoDto> tareasJerarquicas = construirJerarquiaTareasOriginal(tareasTemporales);

            // Calcular el porcentaje planeado para cada tarea en la jerarquía
            for (TareaPlanTrabajoDto tarea : tareasJerarquicas) {
                calcularPorcentajePlaneadoTareaCorreo(tarea, tareasTemporales);
            }

            // Guardar el cálculo en las tareas del proyecto actual
            tareaPlanTrabajoRepository.saveAll(tareasTemporales);

            // Agregar el resultado de este proyecto a la lista global
            resultadoGlobal.addAll(tareasJerarquicas);
        }

        return resultadoGlobal;
    }

    @Override
    public List<TareaPlanTrabajoDto> construirJerarquiaTareasOriginal(
            List<TareaPlanTrabajoModel> tareasOriginales) {
        List<TareaPlanTrabajoDto> raiz = new ArrayList<>();
        Map<Long, TareaPlanTrabajoDto> tareaMap = new HashMap<>();
        Deque<TareaPlanTrabajoDto> stack = new ArrayDeque<>();

        List<TareaPlanTrabajoDto> tareasDto = tareasOriginales.stream()
                .map(this::conversionOriginalDto)
                .sorted(Comparator.comparing(TareaPlanTrabajoDto::getIdTarea))
                .toList();

        for (TareaPlanTrabajoDto tarea : tareasDto) {
            tareaMap.put(tarea.getIdTarea().longValue(), tarea);

            // Si el nivel es 0, es una tarea raíz (padre)
            if (tarea.getNivelEsquema() == 0) {
                raiz.add(tarea); // padre raíz
                stack.clear(); // se limpia la pila para empezar una nueva rama
                stack.push(tarea);
            } else {
                // Si la tarea actual está en un nivel menor o igual al último padre, se
                // desapila
                while (!stack.isEmpty() && stack.peek().getNivelEsquema() >= tarea.getNivelEsquema()) {
                    stack.pop();
                }

                // Asigna la tarea actual al padre que está en la cima de la pila
                if (!stack.isEmpty()) {
                    stack.peek().getSubRows().add(tarea); // se considera una tarea hijo del nodo en la cima de la pila
                }

                // Se apila la tarea actual para otras tareas hijas
                stack.push(tarea);
            }
        }

        return raiz;
    }

    private TareaPlanTrabajoDto conversionOriginalDto(TareaPlanTrabajoModel tareaOriginal) {

        TareaPlanTrabajoDto tareaDto = new TareaPlanTrabajoDto();
        tareaDto.setIdTarea(tareaOriginal.getIdTarea());
        tareaDto.setNivelEsquema(tareaOriginal.getNivelEsquema());
        tareaDto.setNombreTarea(tareaOriginal.getNombreTarea());
        tareaDto.setActivo(tareaOriginal.isActivo());
        tareaDto.setDuracionPlaneada(tareaOriginal.getDuracionPlaneada());
        tareaDto.setFechaInicioPlaneada(tareaOriginal.getFechaInicioPlaneada());
        tareaDto.setFechaFinPlaneada(tareaOriginal.getFechaFinPlaneada());
        tareaDto.setDuracionReal(tareaOriginal.getDuracionReal());
        tareaDto.setFechaInicioReal(tareaOriginal.getFechaInicioReal());
        tareaDto.setFechaFinReal(tareaOriginal.getFechaFinReal());
        tareaDto.setPredecesora(tareaOriginal.getPredecesora());
        tareaDto.setPlaneado(tareaOriginal.getPorcentajePlaneado());
        tareaDto.setCompletado(tareaOriginal.getPorcentajeCompletado());
        tareaDto.setSubRows(new ArrayList<>());
        return tareaDto;
    }

    // calculo con envio
    private void calcularPorcentajePlaneadoTareaCorreo(TareaPlanTrabajoDto tareaDto,
            List<TareaPlanTrabajoModel> tareasTemporales) {
        if (tareaDto.getSubRows() != null && !tareaDto.getSubRows().isEmpty()) {
            for (TareaPlanTrabajoDto subTarea : tareaDto.getSubRows()) {
                calcularPorcentajePlaneadoTareaCorreo(subTarea, tareasTemporales);
            }

        }

        TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasTemporales, tareaDto.getIdTarea());

        if ( tareaDto.isActivo()) {
            if (tareaDto.getSubRows() == null || tareaDto.getSubRows().isEmpty()) {
                // Calcular porcentaje planeado para tarea hija (sin subRows)
                calcularPorcentajePlaneadoTareaHijo(tareaDto, tareaEntidad);
            } else {
                // Calcular porcentaje planeado para tarea padre
                calcularPorcentajePlaneadoTareaPadre(tareaDto, tareasTemporales);
            }
        }
    }

    // Clase auxiliar para mantener el estado del cálculo
    private static class EstadoCalculo {
        boolean calculoRealizado = false;
    }

    private void calcularPorcentajePlaneadoTareaMa(TareaPlanTrabajoDto tareaDto,
            List<TareaPlanTrabajoModel> tareasTemporales) {
        EstadoCalculo estadoCalculo = new EstadoCalculo();
        calcularPorcentajePlaneadoTareaRecursivo(tareaDto, tareasTemporales, estadoCalculo);
    }

    private void calcularPorcentajePlaneadoTareaRecursivo(TareaPlanTrabajoDto tareaDto,
            List<TareaPlanTrabajoModel> tareasTemporales,
            EstadoCalculo estadoCalculo) {
        // Recursivamente calcular para las subRows (tareas hijas) antes de calcular el padre
        if (tareaDto.getSubRows() != null && !tareaDto.getSubRows().isEmpty()) {
            for (TareaPlanTrabajoDto subTarea : tareaDto.getSubRows()) {
                calcularPorcentajePlaneadoTareaRecursivo(subTarea, tareasTemporales, estadoCalculo);
            }
        }

        // Buscar la entidad asociada a este DTO
        TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasTemporales, tareaDto.getIdTarea());

        if (tareaDto.isActivo()) {
            if (tareaDto.getSubRows() == null || tareaDto.getSubRows().isEmpty()) {
                // Calcular porcentaje planeado para tarea hija (sin subRows)
                calcularPorcentajePlaneadoTareaHijo(tareaDto, tareaEntidad);
            } else {
                // Calcular porcentaje planeado para tarea padre
                calcularPorcentajePlaneadoTareaPadre(tareaDto, tareasTemporales);
            }

            // Genera el mensaje de auditoría y registra la pista solo una vez
            if (!estadoCalculo.calculoRealizado) {
                StringBuilder auditoriaProyecto = new StringBuilder();
                auditoriaProyecto.append("Id Proyecto: ")
                        .append(tareaEntidad.getPlanTrabajoModel().getProyectoModel().getIdProyecto())
                        .append(" - Plan de trabajo - Cálculos Masivos ");



                // pistaService.guardarPista(


                // ModuloPista.PROYECTOS.getId(),


                // TipoMovPista.ACTUALIZA_REGISTRO.getId(),


                // TipoSeccionPista.PROYECTO_DATOS_PLAN_TRABAJO.getIdSeccionPista(),


                // auditoriaProyecto.toString(),


                // Optional.empty());

                estadoCalculo.calculoRealizado = true; // Marcar que la auditoría ya fue registrada
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

    private double calcularPorcentajePlaneadoTareaPadre(TareaPlanTrabajoDto tareaDto,
            List<TareaPlanTrabajoModel> tareasEntidades) {
        double sumaPorcentajesEquivalentes = 0.0;
        double sumaDuracionesHijas = 0.0;

        for (TareaPlanTrabajoDto tareaHijaDto : tareaDto.getSubRows()) {
            if (tareaHijaDto.isActivo()) {
                double porcentajePlaneadoHijo;

                if (!tareaHijaDto.getSubRows().isEmpty()) {
                    // Calcular porcentaje planeado de tareas hijas recursivamente
                    porcentajePlaneadoHijo = calcularPorcentajePlaneadoTareaPadre(tareaHijaDto, tareasEntidades);
                } else {
                    // Calcular porcentaje planeado de una tarea sin subtareas
                    long diasTranscurridos = calcularDiasTranscurridosCorte(tareaHijaDto.getFechaInicioPlaneada());
                    porcentajePlaneadoHijo = (diasTranscurridos > 0)
                            ? Math.min(diasTranscurridos / tareaHijaDto.getDuracionPlaneada() * 100, 100)
                            : 0;
                    tareaHijaDto.setPlaneado((int) Math.round(porcentajePlaneadoHijo));

                    TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasEntidades,
                            tareaHijaDto.getIdTarea());
                        tareaEntidad.setPorcentajePlaneado((int) Math.round(porcentajePlaneadoHijo));
                }

                // Ponderación de subtareas en base a su duración planeada
                double pesoTareaHija = tareaHijaDto.getDuracionPlaneada();
                sumaPorcentajesEquivalentes += porcentajePlaneadoHijo * pesoTareaHija;
                sumaDuracionesHijas += pesoTareaHija;

            }
        }

        int porcentajePlaneadoTotal = (sumaDuracionesHijas > 0)
                ? (int) Math.round(sumaPorcentajesEquivalentes / sumaDuracionesHijas)
                : 0;

        tareaDto.setPlaneado(porcentajePlaneadoTotal);
        TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasEntidades, tareaDto.getIdTarea());
            tareaEntidad.setPorcentajePlaneado(porcentajePlaneadoTotal);

        return porcentajePlaneadoTotal;
    }

    // Método para calcular días transcurridos hasta la fecha actual, excluyendo no
    // laborables
    private int calcularDiasTranscurridosCorte(LocalDate fechaInicioPlaneada) {
        LocalDate fechaActual = LocalDate.now();

        if (!fechaInicioPlaneada.isAfter(fechaActual)) {
            return (int) fechaInicioPlaneada.datesUntil(fechaActual.plusDays(1))
                    .filter(fecha -> !esNoLaboral(fecha))
                    .count();
        } else {
            return 0;
        }
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

    // Método para calcular el porcentaje planeado de una tarea hija y sus detalles
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

    // completado:

    private void calcularPorcentajeCompletadoTarea(TareaPlanTrabajoDto tareaDto,
            List<TareaPlanTrabajoModel> tareasOriginales) {
        calcularPorcentajeTareaRecursivo(tareaDto, tareasOriginales);
    }

    private void calcularPorcentajeTareaRecursivo(TareaPlanTrabajoDto tareaDto,
            List<TareaPlanTrabajoModel> tareasTemporales) {

        // Recursivamente calcular para las subRows (tareas hijas) antes de calcular el
        // padre
        if (tareaDto.getSubRows() != null && !tareaDto.getSubRows().isEmpty()) {
            for (TareaPlanTrabajoDto subTarea : tareaDto.getSubRows()) {
                calcularPorcentajeTareaRecursivo(subTarea, tareasTemporales);
            }
        }

        // Buscar la entidad asociada a este DTO
        TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasTemporales, tareaDto.getIdTarea());

        if ( tareaDto.isActivo()) {
            if (tareaDto.getSubRows() == null || tareaDto.getSubRows().isEmpty()) {
                // Si es una tarea hija (sin subRows)
                calcularPorcentajePlaneadoTareaHijo(tareaDto, tareaEntidad);
                calcularPorcentajeCompletadoTareaHijo(tareaDto, tareaEntidad);
            } else {
                // Si es una tarea padre, calcula porcentajes basado en sus hijas
                calcularPorcentajePlaneadoTareaPadre(tareaDto, tareasTemporales);
                calcularPorcentajeCompletadoTareaPadre(tareaDto, tareasTemporales);
            }

        }
    }

    @Override
    public void calcularPorcentajeCompletadoTareaPadre(TareaPlanTrabajoDto tareaPadre,
            List<TareaPlanTrabajoModel> tareasOriginales) {

        // Variables para acumulación
        double nuevaDuracionRealPadre = 0.0;
        BigDecimal sumaPorcentajesPonderados = BigDecimal.ZERO;
        BigDecimal sumaPesos = BigDecimal.ZERO;

        // Para verificar si es un caso simple de N/M tareas
        int tareasActivas = 0;
        int tareasCompletadas = 0;

        // Recorrer todas las tareas hijas
        for (TareaPlanTrabajoDto tareaHija : tareaPadre.getSubRows()) {
            if (tareaHija.isActivo()) {
                tareasActivas++;

                // Contar tareas 100% completadas
                if (Integer.valueOf(100).equals(tareaHija.getCompletado())) {
                    tareasCompletadas++;
                }
                // Para cálculo ponderado con alta precisión
                Integer completadoHijo = tareaHija.getCompletado() != null ? tareaHija.getCompletado() : 0;

                // Usar BigDecimal para evitar problemas de precisión
                BigDecimal porcentaje = new BigDecimal(completadoHijo);
                BigDecimal duracion;

                // Priorizar duración real si existe, sino usar planeada
                if (tareaHija.getDuracionReal() != null) {
                    duracion = new BigDecimal(tareaHija.getDuracionReal().toString());
                    nuevaDuracionRealPadre += tareaHija.getDuracionReal();
                } else if (tareaHija.getDuracionPlaneada() != null) {
                    duracion = new BigDecimal(tareaHija.getDuracionPlaneada().toString());
                } else {
                    duracion = BigDecimal.ONE; // Peso predeterminado
                }

                // Acumular valores con precisión
                sumaPorcentajesPonderados = sumaPorcentajesPonderados.add(porcentaje.multiply(duracion));
                sumaPesos = sumaPesos.add(duracion);
            }
        }

        // Calcular el porcentaje completado
        int porcentajeCompletadoPadre= calcularPorcentajeCompletadoTareaPadreComplemento(tareasActivas, tareasCompletadas, sumaPesos, sumaPorcentajesPonderados, tareaPadre);
    

        if (nuevaDuracionRealPadre >= 0) {
            BigDecimal duracionRealPadre = BigDecimal.valueOf(nuevaDuracionRealPadre);
            duracionRealPadre = duracionRealPadre.setScale(0, RoundingMode.HALF_UP); // Redondear al entero más cercano
            nuevaDuracionRealPadre = duracionRealPadre.doubleValue(); // Convertir de vuelta a double
            tareaPadre.setDuracionReal(nuevaDuracionRealPadre);
        }

        // Actualizar la entidad
        TareaPlanTrabajoModel tareaEntidad = buscarTareaOriginalPorId(tareasOriginales, tareaPadre.getIdTarea());
            tareaEntidad.setPorcentajeCompletado(porcentajeCompletadoPadre);

        actualizarEntidad(tareaEntidad,nuevaDuracionRealPadre);
    }
    
    private int calcularPorcentajeCompletadoTareaPadreComplemento(int tareasActivas, int tareasCompletadas, BigDecimal sumaPesos,
    		BigDecimal sumaPorcentajesPonderados, TareaPlanTrabajoDto tareaPadre) {
    	int porcentajeCompletadoPadre;
    	// Primero verificar si es un caso simple de proporción N/M
    	if (tareasActivas > 0 && tareasCompletadas * 2 == tareasActivas) {
             // Si la proporción es exactamente
    		porcentajeCompletadoPadre= 50;
         } else if (sumaPesos.compareTo(BigDecimal.ZERO) > 0) {
             // Cálculo ponderado para tener mejor precisión usando BigDecimal
             BigDecimal resultado = sumaPorcentajesPonderados.divide(sumaPesos, 10, RoundingMode.HALF_UP);
             // Se usa setScale(0) para redondear al entero más cercano
             porcentajeCompletadoPadre= resultado.setScale(0, RoundingMode.HALF_UP).intValue();
         } else {
        	 porcentajeCompletadoPadre= 100;
         }
    	
    	// Actualizar los valores
        tareaPadre.setCompletado(porcentajeCompletadoPadre);
    	
    	return porcentajeCompletadoPadre;
    }
    
    private void actualizarEntidad(TareaPlanTrabajoModel tareaEntidad, double nuevaDuracionRealPadre) {
    	if (nuevaDuracionRealPadre > 0) {
            tareaEntidad.setDuracionReal(nuevaDuracionRealPadre);
        }
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

}
