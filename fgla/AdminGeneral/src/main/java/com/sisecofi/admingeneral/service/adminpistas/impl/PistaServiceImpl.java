package com.sisecofi.admingeneral.service.adminpistas.impl;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sisecofi.admingeneral.repository.adminpistas.PistasRepository;
import com.sisecofi.admingeneral.repository.plantillador.CatSubTipoPlantilladorRepository;
import com.sisecofi.admingeneral.repository.plantillador.CatTipoPlantillaRepository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoPlantillaBaseRespository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoPlantillaRepository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoSubPlantillaBaseRespository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoSubPlantilladorRespository;
import com.sisecofi.admingeneral.repository.plantillador.ContenidoVariablesRespository;
import com.sisecofi.admingeneral.repository.plantillador.PlantillaRespository;
import com.sisecofi.admingeneral.repository.plantillador.SubPlantilladorDatosRespository;
import com.sisecofi.admingeneral.repository.plantillador.SubPlantilladorRespository;
import com.sisecofi.admingeneral.service.adminpistas.PistaService;
import com.sisecofi.admingeneral.service.plantillador.HtmlWordService;
import com.sisecofi.admingeneral.util.exception.PistaException;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;
import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatSeccionPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
import com.sisecofi.libreria.comunes.model.pista.PistaModel;
import com.sisecofi.libreria.comunes.util.ObtenerIpUtil;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.sesion.Session;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class PistaServiceImpl implements PistaService {

	@Autowired
	private PistasRepository pistaRepository;
	
	@Autowired
	private Session session;

	@Autowired
	private HttpServletRequest httpRequest;

	@Override
	public PistaModel save(PistaModel entity) {
		return pistaRepository.save(entity);
	}

	@Override
	public Optional<PistaModel> findById(Long id) {
		return pistaRepository.findById(id);
	}

	@Override
	public Iterable<PistaModel> findAll() {
		return pistaRepository.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PistaModel guardarPista(PistaModel model) {
		StringBuilder s = new StringBuilder();
		if (model.getPista() != null) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = objectMapper.writeValueAsString(model.getPista());
				Map<String, Object> dataMap = objectMapper.readValue(jsonString.getBytes(), Map.class);
				dataMap.forEach((l, v) -> {
					if (v != null && noVacio(v)) {
						s.append(l);
						s.append(":");
						s.append(v);
						s.append("|");
					}
				});
				log.info("Movimiendo consulta pista: {}", s.toString());
				if (!s.toString().isEmpty()) {
					model.setMovimiento(s.toString());
				}
			} catch (Exception e1) {
				log.error("Error al generar pista: {}");
			}
		}
		model.setFechaMovimiento(horaActual());
		model.setIp(model.getIp() == null ? httpRequest.getServerName() : model.getIp());
		if (session.retornarUsuario().isPresent()) {
			model.setUsuario(session.retornarUsuario().orElseThrow(() -> new RuntimeException("El usuario no está presente")));
		} else {
			model.setUsuario(null);
		}
		return this.save(model);
	}

	@SuppressWarnings("rawtypes")
	private boolean noVacio(Object v) {
		if (v instanceof List<?> t) {
		    return !t.isEmpty();
		}

		if (v instanceof String) {
			return !v.toString().isEmpty();
		}
		return false;
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	public static List<Field> obtenerFields(Object cat) {
		List<Field> lista = new ArrayList<>();
		lista.addAll(Arrays.asList(cat.getClass().getFields()));
		lista.addAll(Arrays.asList(cat.getClass().getSuperclass().getFields()));
		return lista;
	}

	@Override
	public boolean guardarPista(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion, String movimiento,
			Optional<Object> obj) {
		try {
			PistaModel pista = crearPistaModelo(idModuloPista, idTipoMovPista, idSeccion, movimiento, obj);
			return guardarPistaConResultado(pista);
		} catch (Exception e) {
			manejarExcepcionGuardarPista(idTipoMovPista, e);
			return false;
		}
	}

	private boolean guardarPistaConResultado(PistaModel pista) {
		try {
			guardarPista(pista);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	private PistaModel crearPistaModelo(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion,
			String movimiento, Optional<Object> obj) {
		PistaModel pista = new PistaModel();
		String resultadoFinal = obj.map(this::procesarObjeto).orElse(movimiento);
		pista.setMovimiento(resultadoFinal);
		pista.setModuloPistaModel(new CatModuloPistaModel(idModuloPista));
		pista.setTipoMovPistaModel(new CatTipoMovPistaModel(idTipoMovPista));
		pista.setSeccionPistaModel(new CatSeccionPistaModel(idSeccion));
		pista.setIp(ObtenerIpUtil.obtenerIp(httpRequest));
		return pista;
	}

	private String procesarObjeto(Object modelo) {
	    StringBuilder s = new StringBuilder();
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    for (Field field : modelo.getClass().getDeclaredFields()) {
	        if (field.isAnnotationPresent(JsonIgnore.class)) {
	            continue;
	        }
	        if (field.trySetAccessible()) {
	            try {
	                Object valor = field.get(modelo);
	                agregarValorCampo(field, valor, s, dateFormatter);
	            } catch (Exception e) {
	                throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	            }
	        }
	    }

	    if (s.length() > 2) {
	        s.setLength(s.length() - 2);
	    }
	    return s.toString().replace("log: Logger| ", "");
	}

	private void agregarValorCampo(Field field, Object valor, StringBuilder s, DateTimeFormatter dateFormatter) {
		if (valor == null) {
			s.append(field.getName()).append(": null| ");
		} else if (valor instanceof String || valor instanceof Number || valor instanceof Boolean
				|| valor instanceof Enum) {
			s.append(field.getName()).append(": ").append(valor).append("| ");
		} else if (valor instanceof TemporalAccessor temporalValue) {
			s.append(field.getName()).append(": ").append(dateFormatter.format(temporalValue)).append("| ");
		} else {
			try {
				agregarObjetoComplejo(field, valor, s);
			} catch (Exception e) {
				throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
			}
		}
	}

	private void manejarExcepcionGuardarPista(Integer idTipoMovPista, Exception e) {
		if (idTipoMovPista == TipoMovPista.INSERTA_REGISTRO.getId()
				|| idTipoMovPista == TipoMovPista.ACTUALIZA_REGISTRO.getId()) {
			throw new PistaException(ErroresEnum.ERROR_AL_GUARDAR_LA_PISTA, e);
		} else if (idTipoMovPista == TipoMovPista.CONSULTA_REGISTRO.getId()) {
			throw new PistaException(ErroresEnum.ERROR_AL_CONSULTAR_LA_PISTA, e);
		} else if (idTipoMovPista == TipoMovPista.IMPRIME_REGISTRO.getId()) {
			throw new PistaException(ErroresEnum.ERROR_AL_IMPRIMIR_LA_PISTA, e);
		}
		throw new PistaException(ErroresEnum.ERROR_AL_PISTA_DESCONOCIDO, e);
	}

	private void agregarObjetoComplejo(Field field, Object valor, StringBuilder s) throws NoSuchFieldException, IllegalAccessException {
	    if (valor instanceof DictamenId) {
	        Field idField = valor.getClass().getDeclaredField("idDictamen");
	        if (idField.trySetAccessible()) {
	            s.append("idDictamen: ").append(idField.get(valor)).append("| ");
	        }
	    } else {
	        s.append(field.getName()).append(": ").append(valor.getClass().getSimpleName());
	        Optional<Field> idField = Arrays.stream(valor.getClass().getDeclaredFields())
	                .filter(f -> f.getName().equalsIgnoreCase("id"))
	                .findFirst();

	        if (idField.isPresent() && idField.get().trySetAccessible()) {
	            s.append(" (ID: ").append(idField.get().get(valor)).append(")| ");
	        }
	    }
	}

	@Override
	public boolean guardarPistaSimple(Integer idModuloPista, Integer idTipoMovPista, Integer idSeccion,
			String movimiento, Optional<Object> obj) {
		return guardarPista(idModuloPista, idTipoMovPista, idSeccion, movimiento, obj);
	}
}
