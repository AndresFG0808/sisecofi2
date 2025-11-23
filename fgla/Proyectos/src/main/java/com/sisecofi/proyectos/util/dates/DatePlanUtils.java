package com.sisecofi.proyectos.util.dates;

import com.sisecofi.proyectos.dto.adminplantrabajo.TareaPlanTrabajoDto;
import com.sisecofi.proyectos.model.adminplantrabajo.TareaPlanTrabajoModel;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class DatePlanUtils {

    private DatePlanUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static int calcularDiasTranscurridosLaborales(LocalDate fechaInicioPlaneada) {
        LocalDate fechaActual = LocalDate.now();
        if (!fechaInicioPlaneada.isAfter(fechaActual)) {
            fechaInicioPlaneada.datesUntil(fechaActual)
                    .forEach(fecha -> log.info("Fecha contada: {}, No Laboral: {}", fecha, esNoLaboral(fecha)));

            long diasTranscurridosLong = fechaInicioPlaneada.datesUntil(fechaActual.plusDays(1))
                    .filter(fecha -> !esNoLaboral(fecha)).count();
            return Math.toIntExact(diasTranscurridosLong);
        }
        return 0;
    }

    public static int calcularDiasTranscurridos(LocalDate fechaInicioPlaneada) {
        LocalDate fechaActual = LocalDate.now();
        if (!fechaInicioPlaneada.isAfter(fechaActual)) {
            return (int) fechaInicioPlaneada.datesUntil(fechaActual.plusDays(1))
                    .filter(DatePlanUtils::esNoLaboral)
                    .count();
        }
        return 0;
    }

    public static boolean esNoLaboral(LocalDate fecha) {
        DayOfWeek diaDeLaSemana = fecha.getDayOfWeek();
        if (diaDeLaSemana == DayOfWeek.SATURDAY || diaDeLaSemana == DayOfWeek.SUNDAY) {
            return true;
        }
        List<LocalDate> festivos = Arrays.asList(
                LocalDate.of(fecha.getYear(), 1, 1),
                LocalDate.of(fecha.getYear(), 12, 25)
        );
        return festivos.contains(fecha);
    }


    public static  int calcularDiasTranscurridosCorte(LocalDate fechaInicioPlaneada) {
		LocalDate fechaActual = LocalDate.now();

		if (!fechaInicioPlaneada.isAfter(fechaActual)) {
			return (int) fechaInicioPlaneada.datesUntil(fechaActual.plusDays(1)).filter(fecha -> !esNoLaboral(fecha))
					.count();
		} else {
			return 0;
		}
	}

    public static void calcularPorcentajePlaneado(TareaPlanTrabajoDto tareaDto, TareaPlanTrabajoModel tareaEntidad, boolean conDiasLaborales) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioPlaneada = tareaDto.getFechaInicioPlaneada();
        LocalDate fechaFinPlaneada = tareaDto.getFechaFinPlaneada();

        if (fechaInicioPlaneada != null && fechaFinPlaneada != null && !fechaInicioPlaneada.isAfter(fechaActual)) {
            long totalDiasPlaneados = ChronoUnit.DAYS.between(fechaInicioPlaneada, fechaFinPlaneada) + 1;

            if (totalDiasPlaneados > 0) {
                long diasTranscurridos = conDiasLaborales ?
                        calcularDiasTranscurridosLaborales(fechaInicioPlaneada) :
                        calcularDiasTranscurridos(fechaInicioPlaneada);
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

}
