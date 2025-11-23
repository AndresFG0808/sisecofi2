package com.sisecofi.reportedocumental.service.dinamico.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.sisecofi.libreria.comunes.dto.dinamico.CampoOrden;
import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;
import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.libreria.comunes.dto.dinamico.TableDto;
import com.sisecofi.libreria.comunes.repository.dinamico.DinamicoRepository;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.FilterField;
import com.sisecofi.reportedocumental.dto.dinamico.CriterioBusquedaDto;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.dinamico.ReporteDinamicoService;
import com.sisecofi.reportedocumental.util.annotations.TargetDate;
import com.sisecofi.reportedocumental.util.enums.ErroresEnum;
import com.sisecofi.reportedocumental.util.exception.ReporteException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteDinamicoServiceImpl implements ReporteDinamicoService {

	private static final String IN = " IN(VALORES)";
	private static final String AND = " AND ";
	private static final String TO_DATE = "TO_DATE('";
	private static final String FECHA = "', 'YYYY-MM-DD')";
	private static final String LOG_FECHA = "agregando filtro de fechas";
	private static final String LOG_WHERE = "where: {}";

	private final DinamicoRepository dinamicoRepository;
	private final ReportExportGenericService exportGenericService;

	@Qualifier("mapperDinamico")
	private final MapperDinamico dinamico;

	@Override
	public PageGeneric obtenerReporte(CriterioBusquedaDto busquedaDto) {
		try {
			long inicio = System.currentTimeMillis();
			validarObligatorios(busquedaDto);
			String where = generarWhere(busquedaDto);

			log.info("busquedaDto.getIdTituloServicio(): {}", busquedaDto.getIdTituloServicio());
			if (busquedaDto.getIdTituloServicio() != null && !busquedaDto.getIdTituloServicio().isEmpty()) {
				busquedaDto.getDataReporteDto().getProveedor().setTituloServicioRequerido(true);
			}
			log.info(LOG_WHERE, where);
			PageGeneric data = dinamicoRepository.generarData(busquedaDto, where, true);
			log.info("Tiempo de procesamiento: {} mls", System.currentTimeMillis() - inicio);
			return data;

		} catch (ReporteException e) {
			throw e;
		} catch (Exception e) {
			log.error("Error al generar el reporte dinámico");
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_REPORTE_DINAMICO, e);
		}
	}

	private void validarObligatorios(CriterioBusquedaDto busquedaDto) {
		try {
			Set<TableDto> tablas = new HashSet<>();
			List<CampoOrden> campOrden = new ArrayList<>();
			campOrden.addAll(dinamicoRepository.generateSelectString(busquedaDto, tablas));
			Collections.sort(campOrden);
			campOrden.forEach(data -> {
				log.info("campos: {} ", data.getCampo());
				if (obtenerPrefijo(data.getCampo()).equals("s1")) {
					busquedaDto.getDataReporteDto().getDatosGenerales().setRequerido(true);
				}
				if (obtenerPrefijo(data.getCampo()).equals("s2")) {
					busquedaDto.getDataReporteDto().getContrato().setRequerido(true);
				}
				if (obtenerPrefijo(data.getCampo()).equals("s49")) {
					busquedaDto.getDataReporteDto().getDictamen().setRequerido(true);
				}
			});
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_REPORTE_DINAMICO, e);
		}
	}

	private String obtenerPrefijo(String cadena) {
		int indicePunto = cadena.indexOf(".");
		return (indicePunto != -1) ? cadena.substring(0, indicePunto) : cadena;
	}

	@Override
	public byte[] obtenerReporteExport(CriterioBusquedaDto busquedaDto) {
		try {
			long inicio = System.currentTimeMillis();
			validarObligatorios(busquedaDto);
			String where = generarWhere(busquedaDto);
			PageGeneric data = dinamicoRepository.generarData(busquedaDto, where, false);
			log.info(LOG_WHERE, where);
			byte[] archivo = exportGenericService.exportarReporte(data, "Reporte dinámico", dinamico);
			log.info("Tiempo de procesamiento:  {} mls", System.currentTimeMillis() - inicio);
			return archivo;
		} catch (ReporteException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_REPORTE_DINAMICO, e);
		}
	}

	private String generarWhere(CriterioBusquedaDto busquedaDto) {
		try {
			Boolean encontrado = false;
			StringBuilder where = new StringBuilder();
			List<TargetDate> lista = new ArrayList<>();
			for (Field field : busquedaDto.getClass().getDeclaredFields()) {
				ReflectionUtils.makeAccessible(field);
				encontrado = generarWhereComplemento(field, busquedaDto, where,lista) || encontrado;
			}
			log.info("encontrado1 {}", encontrado);
			boolean econ = agregarFechas(lista, busquedaDto, where);
			log.info("econ {}", econ);
			if (!Boolean.TRUE.equals(encontrado) && !econ) {
			    throw new ReporteException(ErroresEnum.ERROR_SIN_RESULTADOS);
			}
			return where.toString();
		} catch (ReporteException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_REPORTE_DINAMICO, e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private Boolean generarWhereComplemento(Field field, CriterioBusquedaDto busquedaDto, StringBuilder where, List<TargetDate> lista) throws IllegalArgumentException, IllegalAccessException {
		Boolean encontrado=false;
		if (field.get(busquedaDto) != null) {
			FilterField filter = AnnotationUtils.findAnnotation(field, FilterField.class);
			if (filter != null) {
				String[] target = filter.nameTarget().split(":");
				List<String> objetos = Arrays.asList(filter.objectTarget().split(","));
				if (field.get(busquedaDto) != null && !((List) (field.get(busquedaDto))).isEmpty()
						&& validarData(busquedaDto, target[0], target[1], false, null)) {
					where.append(AND);
					where.append(filter.filter());
					where.append(IN.replace("VALORES", String.valueOf(field.get(busquedaDto))).replace("[", "")
							.replace("]", ""));
				}
				if (field.get(busquedaDto) != null && !((List) (field.get(busquedaDto))).isEmpty()
						&& validarData(busquedaDto, target[0], target[1], true, objetos)) {
					log.info("Enconrado bloque: {} -{}", field.getName(), "true");
					encontrado = true;
				}
			}
			TargetDate targetDate = AnnotationUtils.findAnnotation(field, TargetDate.class);
			if (targetDate != null) {
				lista.add(targetDate);
			}
		}
		return encontrado;
	}

	private Boolean agregarFechas(List<TargetDate> lista, CriterioBusquedaDto busquedaDto,
			StringBuilder whereOriginal) {
		try {
			Boolean encontrado = false;
			log.info("whereOriginal:{}", whereOriginal.toString());
			StringBuilder where = new StringBuilder();
			if (lista.size() == 1 && busquedaDto.getFechaInicio() != null) {
				log.info(LOG_FECHA);
				TargetDate target = lista.get(0);
				String[] tar = target.field();
				validarFechasInicio(tar, busquedaDto, where, whereOriginal, target);
			}
			if (lista.size() == 1 && busquedaDto.getFechaTermino() != null) {
				log.info(LOG_FECHA);
				TargetDate target = lista.get(1);
				String[] tar = target.field();
				where.setLength(0);
				validarFechasFin(tar, busquedaDto, where, whereOriginal, target);
			}
			if (lista.size() == 2) {
				log.info(LOG_FECHA);
				TargetDate target = lista.get(0);
				String[] tar = target.field();
				validarFechasInicio(tar, busquedaDto, where, whereOriginal, target);
				target = lista.get(1);
				tar = target.field();
				where.setLength(0);
				validarFechasFin(tar, busquedaDto, where, whereOriginal, target);
			}
			if (!where.isEmpty()) {
				log.info("Cambiando a true ");
				encontrado = true;
			}
			return encontrado;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_REPORTE_DINAMICO, e);
		}
	}

	private void validarFechasFin(String[] tar, CriterioBusquedaDto busquedaDto, StringBuilder where,
			StringBuilder whereOriginal, TargetDate target) {
		Integer contador = 0;

		for (String cadena : tar) {
			String[] campos = cadena.split(":");
			log.info("Campo012: {} Campo112: {} Campo312: {}", campos[0], campos[1], Integer.parseInt(campos[3]));
			if (validarData(busquedaDto, campos[0], campos[1], false, null)) {
				log.info("Campo22: {}", Integer.parseInt(campos[3]));

				boolean esUnica = (campos.length == 5 && "unica".equals(campos[4]));
				boolean mismoPeriodo = Integer.parseInt(campos[3]) == busquedaDto.getAplicacionPeriodo();
				log.info("esUnica2: {}", esUnica);
				log.info("mismoPeriodo2: {}", mismoPeriodo);
				if (mismoPeriodo) {
					if (esUnica) {
						where.append(TO_DATE).append(busquedaDto.getFechaTermino()).append(FECHA);
					} else {
						where.append(AND).append(target.function().replace("%valor", campos[2])).append("=")
								.append(TO_DATE).append(busquedaDto.getFechaTermino()).append(FECHA);
					}
					contador++;
					break;
				}
			}
		}
		log.info("where2: {}", where);
		log.info("Contador2: {}", contador);

		whereOriginal.append(where);

		log.info("where fechas: {}", whereOriginal.toString());
	}

	private void validarFechasInicio(String[] tar, CriterioBusquedaDto busquedaDto, StringBuilder where,
			StringBuilder whereOriginal, TargetDate target) {
		Integer contador = 0;

		for (String cadena : tar) {
			String[] campos = cadena.split(":");
			log.info("Campo01: {} Campo11: {} Campo31: {}", campos[0], campos[1], Integer.parseInt(campos[3]));
			if (validarData(busquedaDto, campos[0], campos[1], false, null)) {
				log.info("Validado: {} Campo1: {} Campo3: {}", campos[0], campos[1], Integer.parseInt(campos[3]));
				boolean esUnica = (campos.length == 5 && "unica".equals(campos[4]));
				boolean mismoPeriodo = Integer.parseInt(campos[3]) == busquedaDto.getAplicacionPeriodo();
				log.info("esUnica: {}", esUnica);
				log.info("mismoPeriodo: {}", mismoPeriodo);
				if (mismoPeriodo) {
					where.append(AND).append(target.function().replace("%valor", campos[2]));
					if (esUnica) {
						where.append(" between ").append(TO_DATE).append(busquedaDto.getFechaInicio()).append(FECHA)
								.append(AND);
					} else {
						where.append("=").append(TO_DATE).append(busquedaDto.getFechaInicio()).append(FECHA);
					}
					contador++;
					break;
				}
			}
		}
		log.info("Contador: {}", contador);
		log.info(LOG_WHERE, where);

		whereOriginal.append(where);

		log.info("where fechas: {}", whereOriginal.toString());
	}

	private boolean validarData(CriterioBusquedaDto busquedaDto, String target1, String target2, boolean objetoCompleto,
			List<String> objetos) {
		try {
			boolean bandera = false;
			for (Field field : busquedaDto.getDataReporteDto().getClass().getDeclaredFields()) {
				ReflectionUtils.makeAccessible(field);
				bandera = validarDataComplemento( field,  busquedaDto,  objetoCompleto, objetos,  target1,  target2) || bandera;
			}
			return bandera;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_GENERAR_REPORTE_DINAMICO, e);
		}
	}
	
	private boolean validarDataComplemento(Field field, CriterioBusquedaDto busquedaDto, boolean objetoCompleto, List<String> objetos, String target1, String target2) throws SecurityException, IllegalArgumentException, IllegalAccessException {
		boolean bandera = false;
		if (field.get(busquedaDto.getDataReporteDto()) != null) {
			for (Field field2 : field.get(busquedaDto.getDataReporteDto()).getClass().getDeclaredFields()) {
				Object obj = retornarObjecto(field, busquedaDto);
				ReflectionUtils.makeAccessible(field2);
				if (objetoCompleto) {
					bandera= validarContenido( field2,  obj,  objetos, field,   target1,  target2) || bandera;
				} else {
					if ((boolean) field2.get(obj) && field.getName().equals(target1)) { // &&
																						// target2.contains(field2.getName())
						log.info("Target encontrado: {} - {} - {}", field2.get(obj),
								field.getName().equals(target1), field2.getName().equals(target2));
						bandera = true;
					}
				}
			}
		}
		
		return bandera;
	}
	
	private boolean validarContenido(Field field2, Object obj, List<String> objetos,Field field,  String target1, String target2) throws IllegalArgumentException, IllegalAccessException {
		boolean cumple = (boolean) field2.get(obj) && objetos.contains(field.getName());
	    if (cumple) {
	        log.info(
	            "Target objeto encontrado: field2.get(obj):{} - field.getName().equals(target1):{} - field2.getName().equals(target2):{} - field.getName():{} - target1: {} - target2: {}",
	            field2.get(obj),
	            field.getName().equals(target1),
	            field2.getName().equals(target2),
	            field.getName(),
	            target1,
	            target2
	        );
	    }
	    return cumple;
	}

	private Object retornarObjecto(Field field, GenericReport busquedaDto) {
		try {
			String str = field.getName();
			String met = "get" + str.substring(0, 1).toUpperCase() + str.substring(1);
			Method method = busquedaDto.getDataReporteDto().getClass().getDeclaredMethod(met);
			return method.invoke(busquedaDto.getDataReporteDto());

		} catch (NoSuchMethodException e) {
			log.error("Método no encontrado");
			return null;
		} catch (IllegalAccessException e) {
			log.error("Acceso ilegal al método");
			return null;
		} catch (InvocationTargetException e) {
			log.error("Error al invocar el método");
			return null;
		} catch (NullPointerException e) {
			log.error("Objeto o método nulo");
			return null;
		}
	}
}
