package com.sisecofi.catalogos.service.impl;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sisecofi.catalogos.dto.CatalogoMetaDto;
import com.sisecofi.catalogos.service.PistaService;
import com.sisecofi.catalogos.service.ServicioCatalogo;
import com.sisecofi.catalogos.util.Constantes;
import com.sisecofi.catalogos.util.UtileriasCatalogos;
import com.sisecofi.catalogos.util.enums.Catalogos;
import com.sisecofi.catalogos.util.enums.ErroresEnum;
import com.sisecofi.catalogos.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatMapaObjetivo;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.interfaces.Fechable;
import com.sisecofi.libreria.comunes.util.interfaces.Unicable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OrderBy;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
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
public class ServicioCatalogoImpl implements ServicioCatalogo {

	private static final String CAT = "Catalogo: {}";
	private static final String QUERY_ALL = "select c from {catalogo} c where 1=1";
	private static final String QUERY = "select c from {catalogo} c where c.estatus IS NOT NULL";
	private static final String CATALOGO = "{catalogo}";
	private static final String AND = " and ";
	private static final String ORDER = " order by ";
	private static final String CONSULTA = "Consulta generada: {}";

	private final EntityManager entityManager;
	private final PistaService pistaService;
	private final Gson gson;
	private final Validator validator;

	@Override
	public List<CatalogoMetaDto> obtenerCatalogos() {
		List<CatalogoMetaDto> lista = new ArrayList<>();
		for (Catalogos cat : Catalogos.values()) {
			if (cat.getType() != null && !cat.isComplementario() && cat.isVisible()) {
				CatalogoMetaDto obj = new CatalogoMetaDto();
				obj.setIdCatalogo(cat.getCatalogosComunes().getIdCatalogo());
				obj.setCatalogo(cat.getCatalogosComunes().getNombreCatalogo());
				obj.setClase(cat.getType().getClass().getSimpleName());
				lista.add(obj);
			}
		}
		Collections.sort(lista, (o1, o2) -> o1.getCatalogo().compareTo(o2.getCatalogo()));

		return lista;
	}

	@Override
	public List<CatalogoMetaDto> obtenerCatalogosComplementarios() {
		List<CatalogoMetaDto> lista = new ArrayList<>();
		for (Catalogos cat : Catalogos.values()) {
			if (cat.getType() != null && cat.isComplementario() && cat.isVisible()) {
				CatalogoMetaDto obj = new CatalogoMetaDto();
				obj.setIdCatalogo(cat.getCatalogosComunes().getIdCatalogo());
				obj.setCatalogo(cat.getCatalogosComunes().getNombreCatalogo());
				obj.setClase(cat.getType().getClass().getSimpleName());
				lista.add(obj);
			}
		}
		Collections.sort(lista, (o1, o2) -> o1.getCatalogo().compareTo(o2.getCatalogo()));

		return lista;
	}

	@Override
	public Map<String, Object> metaCatalogo(int idCatalogo, Boolean id) {
		Map<String, Object> map = new HashMap<>();
		CatalogoMetaDto catalogoMetaDto = new CatalogoMetaDto();
		Catalogos cats = null;
		for (Catalogos cat : Catalogos.values()) {
			if (cat.getCatalogosComunes().getIdCatalogo() == idCatalogo) {
				cats = cat;
				map = UtileriasCatalogos.validarMap(cat.getType(), id);
				catalogoMetaDto.setCatalogo(cat.getCatalogosComunes().getNombreCatalogo());
				catalogoMetaDto.setIdCatalogo(cat.getCatalogosComunes().getIdCatalogo());
				catalogoMetaDto.setMetaData(map);
				break;
			}
		}
		if (cats != null) {
			log.info("Buscando hijos: {}-{}", cats.getCatalogosComunes(), id);
			Map<Integer, Object> hijos = new HashMap<>();
			UtileriasCatalogos.agregarTablasHijas(cats.getType(), id, hijos);
			log.info("Hijos encontrados: {}", hijos);
			map.put(Constantes.HIJO, hijos);
		}
		log.info("Catalogo metada: {}", map);
		map.put(Constantes.METADATA, catalogoMetaDto);
		return map;
	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public BaseCatalogoModel guardarCatalogo(int idCatalogo, String json) {
		try {
			log.info("Json recibido: {}", json);
			Optional<Catalogos> cat = Catalogos.obtenerCatalogo(idCatalogo);
			if (cat.isPresent()) {
				Object obj = gson.fromJson(json, cat.get().getType().getClass());

				Set<ConstraintViolation<Object>> valores = validator.validate(obj);
				log.info("Java bean validation: {}", valores);
				if (!valores.isEmpty()) {
					throw new ConstraintViolationException(valores);
				}

				agregarFechaSistema((Fechable) obj, LocalDateTime.now());
				validarRegistroExistente(obj, cat);
				log.info("Catalogo a guardar: {}", obj);
				BaseCatalogoModel base = (BaseCatalogoModel) entityManager.merge(obj);

				int seccion = TipoSeccionPista.CATALOGOS_GENERALES.getIdSeccionPista();
				if (cat.get().isComplementario()) {
					seccion = TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista();
				}



				// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


				// seccion, cat.get().getCatalogosComunes().getNombreCatalogo() + "|" + base.getPrimaryKey(),


				// Optional.of(obj));

				return base;
			}
		} catch (CatalogoException | ConstraintViolationException e1) {
			throw e1;
		} catch (Exception e1) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR, e1);
		}
		throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR);
	}

	private void agregarFechaSistema(Fechable obj, LocalDateTime fecha) {
		obj.setFechaCreacion(fecha);
	}

	private void validarRegistroExistente(Object obj, Optional<Catalogos> cat) {
	    if (cat.isPresent()) {
	        try {
	            Unicable unicable = (Unicable) obj;
	            log.info("Retornando objetos: {}", unicable.returnObject());

	            if (unicable instanceof CatMapaObjetivo ) {
	                log.info("No hay duplicados encontrados para catAliniacion diferente.");
	            } else {
	            	validarRegistroExistenteComplemento(unicable);
	            }
	        } catch (CatalogoException e) {
	            throw e;
	        } catch (NoResultException e) {
	            log.error("Sin registro en el catálogo: {}", cat.get().getCatalogosComunes().getNombreCatalogo());
	        }
	    }
	}

	@SuppressWarnings("unchecked")
	private void validarRegistroExistenteComplemento(Unicable unicable) {
		Class<?> claseConcreta = unicable.getClass();

        if (!BaseCatalogoModel.class.isAssignableFrom(claseConcreta)) {
        	throw new CatalogoException(ErroresEnum.ERRO_MSJ_REGISTRO_DUPLICADO);
        }

        Object valor = unicable.returnObject();
        if (!(valor instanceof Map<?, ?> mapa)) {
        	throw new CatalogoException(ErroresEnum.ERRO_MSJ_REGISTRO_DUPLICADO);
        }

        List<String> comparables= obtenerValorClave(mapa);
        String nombre = comparables.get(1);
        String clave= comparables.get(0);
        if (nombre == null) {
            throw new CatalogoException(ErroresEnum.ERRO_MSJ_REGISTRO_DUPLICADO);
        }

        String nombreEntidad = claseConcreta.getSimpleName(); 
        String jpql = "SELECT c FROM " + nombreEntidad + " c WHERE c." + clave +"= :nombre";

        log.info(CONSULTA, jpql);

        List<BaseCatalogoModel> encontrados = entityManager
            .createQuery(jpql, (Class<BaseCatalogoModel>) claseConcreta)
            .setParameter("nombre", nombre)
            .getResultList();

        if (!encontrados.isEmpty()) {
            throw new CatalogoException(ErroresEnum.ERRO_MSJ_REGISTRO_DUPLICADO);
        }
	}

	private List<String> obtenerValorClave(Map<?, ?> mapa) {
	    String[] claves = { "nombre", "porcentaje", "objetivo", "clave" };
	    
	    List<String> response = new ArrayList<>();

	    for (String clave : claves) {
	        Object valor = mapa.get(clave);
	        if (valor instanceof String s && !s.isBlank()) {
	        	response.add(clave);
	        	response.add(s);
	            return response;
	        }
	    }
	    return null; 
	}
	 

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseCatalogoModel> List<T> obtenerInformacion(int idCatalogo, String filtro) {
		Optional<Catalogos> cat = Catalogos.obtenerCatalogo(idCatalogo);
		log.info(CAT, cat);
		if (cat.isPresent()) {
			Session session = entityManager.unwrap(Session.class);
			session.enableFilter("estatusAdminCentral");
			session.enableFilter("estatusAdminGeneral");
			session.enableFilter("estatusAdminAdministracion");
			List<T> lista = entityManager.createQuery(
					generarConsultaOrden(QUERY_ALL.replace(CATALOGO, cat.get().getType().getClass().getCanonicalName()),
							cat.get().getType(), filtro))
					.getResultList();

			int seccion = TipoSeccionPista.CATALOGOS_GENERALES.getIdSeccionPista();
			if (cat.get().isComplementario()) {
				seccion = TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista();
			}

			// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),

			// seccion, cat.get().getCatalogosComunes().getNombreCatalogo(), Optional.empty());
			session.disableFilter("estatusAdminCentral");
			session.disableFilter("estatusAdminGeneral");
			session.disableFilter("estatusAdminAdministracion");
			if (lista.isEmpty()) {
				throw new CatalogoException(ErroresEnum.ERROR_MSJ_BUSQUEDA,
						cat.get().getCatalogosComunes().getNombreCatalogo());
			}
			
			lista.forEach(item -> {
				if (item != null) {
				    item.setPrimaryKey((Integer) item.returnId());
				}
			});

			lista.sort(Comparator.comparing(BaseCatalogoModel::getPrimaryKey,
					Comparator.nullsLast(Comparator.naturalOrder())));

			return lista;
		}
		throw new CatalogoException(ErroresEnum.ERROR_MSJ_BUSQUEDA);
	}

	private String agregarOrder(Object object) {
		for (Field field : object.getClass().getDeclaredFields()) {
			OrderBy orderBy = AnnotationUtils.findAnnotation(field, OrderBy.class);
			log.info("Anotacion orderBy : {}-{}", orderBy, field.getName());
			if (orderBy != null && !orderBy.value().trim().equals("")) {
				StringBuilder s = new StringBuilder();
				s.append(ORDER);
				s.append(orderBy.value());
				log.info("Order consulta: {}", s.toString());
				return s.toString();
			}
		}
		return orderById(object);
	}

	private String generarConsultaOrden(String sql, Object o, String filtro) {
		OrderBy orderBy = AnnotationUtils.findAnnotation(o.getClass(), OrderBy.class);
		log.info("Anotacion orderBy: {}", orderBy);
		if (filtro != null && !filtro.trim().equals("") && !filtro.equals("id")) {
			StringBuilder s = new StringBuilder();
			s.append(sql);
			s.append(ORDER);
			s.append(filtro);
			log.info("Order consulta parameter: {}", s.toString());
			validarConsulta(s.toString());
			return s.toString();
		} else if (orderBy != null && !orderBy.value().trim().equals("")) {
			StringBuilder s = new StringBuilder();
			s.append(sql);
			s.append(ORDER);
			s.append(orderBy.value());
			log.info("Order consulta: {}", s.toString());
			validarConsulta(s.toString());
			return s.toString();
		}
		validarConsulta(sql);
		return sql;
	}
	
	private void validarConsulta(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
        	throw new CatalogoException(ErroresEnum.ERROR_CONSULTA_INVALIDA);
        }

        String sqlNormalizado = sql.trim().toLowerCase()
            .replaceAll("\\s+", " "); 

        // Valida que comience con "select" y no contenga instrucciones peligrosas
        if (!sqlNormalizado.startsWith("select ")) {
        	throw new CatalogoException(ErroresEnum.ERROR_CONSULTA_INVALIDA);
        }

        // Palabras clave no permitidas
        String[] noPermitidos = {"update", "delete", "insert", "merge", "drop", "alter", "truncate", "exec", "execute"};

        for (String keyword : noPermitidos) {
            if (sqlNormalizado.matches(".*\\b" + keyword + "\\b.*")) {
            	throw new CatalogoException(ErroresEnum.ERROR_CONSULTA_INVALIDA);
            }
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseCatalogoModel> List<T> obtenerInformacionReporte(int idCatalogo) {
		Optional<Catalogos> cat = Catalogos.obtenerCatalogo(idCatalogo);
		log.info(CAT, cat);
		if (cat.isPresent()) {
			String sql= UtileriasCatalogos.generarConsulta2(cat.get());
			validarConsulta(sql);
			List<T> lista = entityManager.createQuery(sql).getResultList();

			int seccion = TipoSeccionPista.CATALOGOS_GENERALES.getIdSeccionPista();
			if (cat.get().isComplementario()) {
				seccion = TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista();
			}



			// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),


			// seccion, cat.get().getCatalogosComunes().getNombreCatalogo(), Optional.empty());
			return lista;
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public <T extends BaseCatalogoModel> T actualizarCatalogo(int idCatalogo, String json) {
		try {
			log.info("Json recibido: {}", json);
			Optional<Catalogos> cat = Catalogos.obtenerCatalogo(idCatalogo);
			if (cat.isPresent()) {
				Object obj = transformarObjecto(json, cat.get().getType());

				Set<ConstraintViolation<Object>> valores = validator.validate(obj);
				log.info("Java bean validation: {}", valores);
				if (!valores.isEmpty()) {
					throw new ConstraintViolationException(valores);
				}

				((BaseCatalogoModel) obj).setFechaModificacion(horaActual());

				String adicional = generarIdConsultaJson((BaseCatalogoModel) obj, "=", json);

				StringBuilder s = new StringBuilder();
				s.append(QUERY_ALL.replace(CATALOGO, cat.get().getType().getClass().getCanonicalName()));
				s.append(adicional);
				validarConsulta(s.toString());
				log.info("Consulta generada para actualizacion: {}", s.toString());

				Object encontrado = entityManager.createQuery(s.toString()).getSingleResult();
				log.info("Registro encontrado: {}", encontrado);
				agregarFechaSistema((Fechable) obj, ((BaseCatalogoModel) encontrado).getFechaCreacion());

				adicional = generarIdConsultaJson((BaseCatalogoModel) obj, "!=", json);
				//validarRegistroExistente(obj, cat);
				log.info("Catalogo a actualizar: {}", obj);
				BaseCatalogoModel base = (BaseCatalogoModel) entityManager.merge(obj);

				int seccion = TipoSeccionPista.CATALOGOS_GENERALES.getIdSeccionPista();
				if (cat.get().isComplementario()) {
					seccion = TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista();
				}



				// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


				// seccion, cat.get().getCatalogosComunes().getNombreCatalogo() + "|" + base.getPrimaryKey(),


				// Optional.of(obj));
				return (T) base;
			}
		} catch (CatalogoException e1) {
			throw e1;
		} catch (Exception e1) {
			throw new CatalogoException(ErroresEnum.RRROR_AL_ACTUALIZAR);
		}
		throw new CatalogoException(ErroresEnum.RRROR_AL_ACTUALIZAR);
	}

	private Object transformarObjecto(String json, Object obj) {
		StringBuilder s = new StringBuilder();
		for (Field field : obj.getClass().getDeclaredFields()) {
			Id id = AnnotationUtils.findAnnotation(field, Id.class);
			if (id != null) {
				s.append(field.getName());
			}
		}
		log.info("Json antes: {}", json);
		json = json.replace("primaryKey", s.toString());
		log.info("Json despues: {}", json);
		return gson.fromJson(json, obj.getClass());
	}

	private String generarIdConsultaJson(BaseCatalogoModel obj, String operacion, String json) {
		StringBuilder s = new StringBuilder();
		Object file = JSONValue.parse(json);
		JSONObject jsonObjectdecode = (JSONObject) file;
		for (Field field : obj.getClass().getDeclaredFields()) {
			Id id = AnnotationUtils.findAnnotation(field, Id.class);
			if (id != null) {
				s.append(AND);
				s.append(field.getName());
				s.append(operacion);
				s.append(jsonObjectdecode.get("primaryKey"));
			}
		}
		return s.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseCatalogoModel> List<T> obtenerInformacionCampo(int idCatalogo, String json) {
		Optional<Catalogos> cat = Catalogos.obtenerCatalogo(idCatalogo);
		log.info(CAT, cat);
		if (cat.isPresent()) {
			JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
			Map<String, Object> mapa = UtileriasCatalogos.validarCampos(cat.get().getType(), jsonObject.asMap(),
					new HashMap<>(), "");
			log.info("Mapa generado: {}", mapa);
			StringBuilder s = new StringBuilder();
			s.append(QUERY.replace(CATALOGO, cat.get().getType().getClass().getCanonicalName()));
			validarConsulta(s.toString());
			mapa.forEach((llave, valor) -> {
				s.append(AND).append(llave).append("=").append(valor);
				log.info("Agregando llave valor: {}-{}", llave, valor);
			});
			s.append(agregarOrder(cat.get().getType()));
			log.info(CONSULTA, s.toString());
			if (!mapa.isEmpty()) {
				List<T> lista = entityManager.createQuery(s.toString()).getResultList();
				int seccion = TipoSeccionPista.CATALOGOS_GENERALES.getIdSeccionPista();
				if (cat.get().isComplementario()) {
					seccion = TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista();
				}

				lista.forEach(item -> {
					if (item != null) {
					    item.setPrimaryKey((Integer) item.returnId());
					}
				});


			




			

				// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),



			

				// seccion, cat.get().getCatalogosComunes().getNombreCatalogo(), Optional.empty());
				return lista;
			}
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseCatalogoModel> List<T> obtenerInformacionCampoCompleta(int idCatalogo, String json) {
		Optional<Catalogos> cat = Catalogos.obtenerCatalogo(idCatalogo);
		log.info(CAT, cat);
		if (cat.isPresent()) {
			JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
			Map<String, Object> mapa = UtileriasCatalogos.validarCampos(cat.get().getType(), jsonObject.asMap(),
					new HashMap<>(), "");
			log.info("Mapa generado: {}", mapa);
			StringBuilder s = new StringBuilder();
			s.append(QUERY_ALL.replace(CATALOGO, cat.get().getType().getClass().getCanonicalName()));
			validarConsulta(s.toString());
			mapa.forEach((llave, valor) -> {
				s.append(AND).append(llave).append("=").append(valor);
				log.info("Agregando llave valor: {}-{}", llave, valor);
			});
			String orderBy = orderById(cat.get().getType());
			log.info("Ordenamiendo: {}", orderBy);
			s.append(orderBy);
			if (!mapa.isEmpty()) {
				List<T> lista = entityManager.createQuery(s.toString()).getResultList();

				int seccion = TipoSeccionPista.CATALOGOS_GENERALES.getIdSeccionPista();
				if (cat.get().isComplementario()) {
					seccion = TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista();
				}



				// pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


				// seccion, cat.get().getCatalogosComunes().getNombreCatalogo(), Optional.empty());
				return lista;
			}
		}
		return Collections.emptyList();
	}

	@Override
	public BaseCatalogoModel obtenerInformacionId(int idCatalogo, Integer id) {
		Optional<Catalogos> cat = Catalogos.obtenerCatalogo(idCatalogo);
		if (cat.isPresent()) {
			try {
				String name = UtileriasCatalogos.buscarId(cat.get().getType());
				StringBuilder s = new StringBuilder();
				s.append(QUERY.replace(CATALOGO, cat.get().getType().getClass().getCanonicalName())).append(AND)
						.append(name).append("=").append(id);
				validarConsulta(s.toString());
				return (BaseCatalogoModel) entityManager.createQuery(s.toString()).getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> obtenerInformacionMeta(int idCatalogo, Integer id) {
		Map<String, Object> mapa = new HashMap<>();
		BaseCatalogoModel base = obtenerInformacionId(idCatalogo, id);
		log.info("Encontrado: {}", base);
		if (base != null) {
			mapa = UtileriasCatalogos.mapearObjeto(base);
		}
		return mapa;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseCatalogoModel> T obtenerInformacionIdInterno(int idCatalogo, Integer id) {
		Optional<Catalogos> cat = Catalogos.obtenerCatalogo(idCatalogo);
		if (cat.isPresent()) {
			try {
				String name = UtileriasCatalogos.buscarId(cat.get().getType());
				StringBuilder s = new StringBuilder();
				s.append(QUERY.replace(CATALOGO, cat.get().getType().getClass().getCanonicalName())).append(AND)
						.append(name).append("=").append(id);
				validarConsulta(s.toString());
				return (T) entityManager.createQuery(s.toString()).getResultList().get(0);
			} catch (NoResultException e) {
				return null;
			}
		}
		return null;
	}

	private String orderById(Object object) {
		StringBuilder s = new StringBuilder();
		s.append(ORDER);
		for (Field field : object.getClass().getDeclaredFields()) {
			Id id = AnnotationUtils.findAnnotation(field, Id.class);
			if (id != null) {
				s.append(field.getName());
			}
		}
		return s.toString();
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

}
