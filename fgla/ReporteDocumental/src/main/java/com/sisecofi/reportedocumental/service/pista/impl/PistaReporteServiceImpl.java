package com.sisecofi.reportedocumental.service.pista.impl;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.libreria.comunes.dto.reportedinamico.EmpleadoDto;
import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatSeccionPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
import com.sisecofi.libreria.comunes.model.pista.PistaModel;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.libreria.comunes.repository.dinamico.DinamicoRepository;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.FilterField;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.reportedocumental.dto.pistareporte.BusquedaPista;
import com.sisecofi.reportedocumental.dto.pistareporte.BusquedaPistaDto;
import com.sisecofi.reportedocumental.dto.pistareporte.HistoricoPistaDto;
import com.sisecofi.reportedocumental.dto.pistareporte.PagePista;
import com.sisecofi.reportedocumental.dto.pistareporte.PistaData;
import com.sisecofi.reportedocumental.repository.pistas.ModuloPistaRepository;
import com.sisecofi.reportedocumental.repository.pistas.PistaReporteRepository;
import com.sisecofi.reportedocumental.repository.pistas.SeccionPistaRepository;
import com.sisecofi.reportedocumental.repository.pistas.TipoMovientoPistaRepository;
import com.sisecofi.reportedocumental.service.PistaService;
import com.sisecofi.reportedocumental.service.ReportExportGenericService;
import com.sisecofi.reportedocumental.service.dinamico.impl.MapperDinamico;
import com.sisecofi.reportedocumental.service.pista.PistaReporteService;
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
public class PistaReporteServiceImpl implements PistaReporteService {

	private static final String CONSULTA = "Consulta";
	private static final String IMPRIME = "Exportar archivo";
	private static final String IN = " IN(VALORES)";
	private final DinamicoRepository dinamicoRepository;
	private final ReportExportGenericService exportGenericService;
	private final UsuarioRepository usuarioRepository;
	private final ModuloPistaRepository moduloPistaRepository;
	private final SeccionPistaRepository seccionPistaRepository;
	private final TipoMovientoPistaRepository tipoMovientoPistaRepository;
	private final PistaService microservicio;
	private final PistaReporteRepository pistaReporteRepository;

	@Qualifier("mapperDinamico")
	private final MapperDinamico mapperPistas;

	@Override
	public PagePista obtenerReporte(BusquedaPistaDto busquedaDto) {
		try {
			validarFechas(busquedaDto);
			busquedaDto.setDataReporteDto(new BusquedaPista(new PistaData()));
			PageGeneric page = dinamicoRepository.generarData(busquedaDto, generarWhere(busquedaDto), true);
			if (page.getContent() != null && page.getContent().isEmpty()) {
				throw new ReporteException(ErroresEnum.ERROR_NO_RESULT);
			}
			microservicio.guardarPista(ModuloPista.SISTEMA.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),
					TipoSeccionPista.PISTAS.getIdSeccionPista(), CONSULTA);
			return new PagePista(page);
		} catch (ReporteException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_GENERAL_PISTAS, e);
		}
	}

	private void validarFechas(BusquedaPistaDto busquedaDto) {
		if (busquedaDto.getFechaInicio() != null) {
			if (busquedaDto.getFechaFin() == null) {
				throw new ReporteException(ErroresEnum.ERROR_FECHAS);
			}
			if (busquedaDto.getFechaInicio().isAfter(busquedaDto.getFechaFin())) {
				throw new ReporteException(ErroresEnum.ERROR_FECHAS);
			}
		}
	}

	@Override
	public byte[] obtenerReporteExport(BusquedaPistaDto busquedaDto) {
		try {
			validarFechas(busquedaDto);
			busquedaDto.setDataReporteDto(new BusquedaPista(new PistaData()));
			PageGeneric generic = dinamicoRepository.generarData(busquedaDto, generarWhere(busquedaDto), false);
			if (generic.getContent() != null && generic.getContent().isEmpty()) {
				throw new ReporteException(ErroresEnum.ERROR_NO_RESULT);
			}
			generic.getEtiquetas().add("Detalle de movimiento");
			generic.getEtiquetas().add("Detalle de movimiento anterior");
			generic.getEtiquetas().add("Última modificación");
			List<Object[]> objetos = new ArrayList<>();
			generic.getContent().stream().forEach(obj -> {
				HistoricoPistaDto h = buscarPistas(Long.parseLong(String.valueOf(obj[0])));
				Object[] copiedArray = Arrays.copyOf(obj, obj.length + 3);
				if (h != null) {
					copiedArray[obj.length] = h.getDetalleMovimiento();
					copiedArray[obj.length + 1] = h.getDetalleMovimientoAnterior();
					copiedArray[obj.length + 2] = h.getUltimaModificacion();
				}
				objetos.add(copiedArray);
			});
			generic.setContent(objetos);

			byte[] archivo = exportGenericService.exportarReporte(generic, "Pistas auditoria", mapperPistas);

			microservicio.guardarPista(ModuloPista.SISTEMA.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.PISTAS.getIdSeccionPista(), IMPRIME);
			return archivo;
		} catch (ReporteException e) {
			throw e;
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_EXPORTAR, e);
		}
	}

	@SuppressWarnings("unchecked")
	private String generarWhere(BusquedaPistaDto busquedaDto) {
		try {
			StringBuilder where = new StringBuilder();
			for (Field field : busquedaDto.getClass().getDeclaredFields()) {
				ReflectionUtils.makeAccessible(field);
				if (field.get(busquedaDto) != null) {
					FilterField filter = AnnotationUtils.findAnnotation(field, FilterField.class);
					if (filter != null) {
						List<Integer> lista = (List<Integer>) field.get(busquedaDto);
						if (!lista.isEmpty()) {
							where.append(" AND ");
							where.append(filter.filter());
							where.append(IN.replace("VALORES", String.valueOf(field.get(busquedaDto))).replace("[", "")
									.replace("]", ""));
						}
					}
				}
			}
			if (busquedaDto.getFechaInicio() != null && busquedaDto.getFechaFin() != null) {
				where.append(" AND ");
				where.append(" s1.fecha_movimiento between ");
				where.append("TO_TIMESTAMP('");
				where.append(busquedaDto.getFechaInicio());
				where.append(" 00:00:00");
				where.append("','YYYY/MM/DD HH24:MI:SS')");
				where.append(" and ");
				where.append("TO_TIMESTAMP('");
				where.append(busquedaDto.getFechaFin());
				where.append(" 23:59:59");
				where.append("','YYYY/MM/DD HH24:MI:SS')");
			}
			log.info("Where generado: {}", where.toString());
			return where.toString();
		} catch (Exception e) {
			throw new ReporteException(ErroresEnum.ERROR_AL_EXPORTAR, e);
		}
	}

	@Override
	public List<EmpleadoDto> obtenerUsuarios() {
		return usuarioRepository.buscarUsuariosActivos(true)
			    .stream()
			    .sorted(Comparator.comparing(EmpleadoDto::getNombre, String.CASE_INSENSITIVE_ORDER))
			    .toList();
	}

	@Override
	public List<CatModuloPistaModel> obtenerModuloPista() {
		return moduloPistaRepository.findAll()
			    .stream()
			    .sorted(Comparator.comparing(CatModuloPistaModel::getNombre, String.CASE_INSENSITIVE_ORDER))
			    .toList();
	}

	@Override
	public List<CatSeccionPistaModel> obtenerSeccionPista(Integer idModulo) {
		return seccionPistaRepository.findByCatModuloPistaModelIdModuloPista(idModulo)
			    .stream()
			    .sorted(Comparator.comparing(CatSeccionPistaModel::getNombre, String.CASE_INSENSITIVE_ORDER))
			    .toList();
	}

	@Override
	public List<CatTipoMovPistaModel> obtenerTipoMovimientoPista() {
		return tipoMovientoPistaRepository.findAll();
	}

	@Override
	public HistoricoPistaDto buscarPistas(Long idPista) {
		HistoricoPistaDto h = new HistoricoPistaDto();
		Optional<PistaModel> op = pistaReporteRepository.findByIdNativeQuery(idPista);
		if (op.isPresent()) {
			Long encontrado = pistaReporteRepository.buscarMaximoMenor(op.get().getModuloPistaModel().getIdModuloPista(),
					op.get().getSeccionPistaModel().getIdSeccionPista(), op.get().getIdPista());

			PistaModel ultimaModificacion = pistaReporteRepository.buscarPista(
					pistaReporteRepository.buscarMaximoConFiltro(op.get().getModuloPistaModel().getIdModuloPista(),
							op.get().getSeccionPistaModel().getIdSeccionPista()),
					op.get().getModuloPistaModel().getIdModuloPista(),
					op.get().getSeccionPistaModel().getIdSeccionPista());

			if (encontrado != null && encontrado>0) {
				Optional<PistaModel> anterior = pistaReporteRepository.findByIdNativeQuery(encontrado);
				if (anterior.isPresent()) {
					h.setDetalleMovimiento(generarPista(op.get()));
					h.setDetalleMovimientoAnterior(generarPista(anterior.get()));
					h.setUltimaModificacion(generarPista(ultimaModificacion));
				}
			} else {
				h.setDetalleMovimiento(generarPista(op.get()));
				h.setDetalleMovimientoAnterior(generarPista(op.get()));
				h.setUltimaModificacion(generarPista(ultimaModificacion));
			}
		}
		return h;
	}

	private String generarPista(PistaModel p) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		StringBuilder s = new StringBuilder();
		if (p != null) {
			s.append("Id: ");
			s.append(p.getIdPista());
			s.append("|");
			s.append(" Módulo: ");
			s.append(p.getModuloPistaModel().getDescripcion());
			s.append("|");
			s.append(" Sección: ");
			s.append(p.getSeccionPistaModel().getDescripcion());
			s.append("|");
			s.append(" Fecha: ");
			s.append(dtf.format(p.getFechaMovimiento()));
			s.append("|");
			s.append(" Nombre: ");
			s.append(p.getUsuario().getNombre());
			s.append("|");
			s.append(" RFC: ");
			s.append(p.getUsuario().getRfcLargo());
			s.append("|");
			s.append(" Tipo movimiento: ");
			s.append(p.getTipoMovPistaModel().getDescripcion());
			s.append("|");
			s.append(" Comentarios: ");
			s.append(p.getMovimiento());
		}
		return s.toString();
	}

}
