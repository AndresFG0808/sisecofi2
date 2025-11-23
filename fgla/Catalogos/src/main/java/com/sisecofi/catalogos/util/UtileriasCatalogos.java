package com.sisecofi.catalogos.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import com.google.gson.JsonElement;
import com.sisecofi.catalogos.util.enums.Catalogos;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoMostrar;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoOculto;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Slf4j
public class UtileriasCatalogos {

	private UtileriasCatalogos() {
	}

	public static List<Field> obtenerFields(Object cat) {
		List<Field> lista = new ArrayList<>();
		lista.addAll(Arrays.asList(cat.getClass().getDeclaredFields()));
		lista.addAll(Arrays.asList(cat.getClass().getSuperclass().getDeclaredFields()));
		return lista;
	}

	public static Map<String, Object> validarMap(Object cat, boolean id) {
		Map<String, Object> map = new HashMap<>();
		for (Field field : UtileriasCatalogos.obtenerFields(cat)) {
			Optional<Catalogos> atributos = buscarEnCatalogos(field.getGenericType().getTypeName());
			validarInterno(id, field, atributos, map);
		}
		return map;
	}

	private static void validarInterno(boolean id, Field field, Optional<Catalogos> atributos,
			Map<String, Object> map) {
		if (atributos.isPresent()) {
			CampoFront campoFront = AnnotationUtils.findAnnotation(field, CampoFront.class);
			if (campoFront != null) {
				Map<String, Object> map2 = new HashMap<>();
				map2.put(Constantes.TIPO, campoFront.tipoDato());
				map2.put(Constantes.NOMBRE_FRONT, campoFront.nombre());
				map2.put(Constantes.ID_CATALOGO, atributos.get().getCatalogosComunes().getIdCatalogo());
				map2.put(Constantes.ORDEN, campoFront.orden());
				map2.put(Constantes.CAMPO_MANDAR, buscarId(atributos.get().getType()));
				map2.put(Constantes.CAMPO_MOSTRAR, buscarCampoMostrar(atributos.get().getType()));
				map2.put(Constantes.TAMANIO, campoFront.tamanio() );
				validarVista(field, map2);
				map.put(field.getName(), map2);
			}
		} else {
			CampoFront campoFront = AnnotationUtils.findAnnotation(field, CampoFront.class);
			if (campoFront != null) {
				if (!campoFront.id()) {
					Map<String, Object> map2 = new HashMap<>();
					map2.put(Constantes.TIPO, campoFront.tipoDato());
					map2.put(Constantes.NOMBRE_FRONT, campoFront.nombre());
					map2.put(Constantes.ORDEN, campoFront.orden());
					map2.put(Constantes.TAMANIO, campoFront.tamanio());
					validarVista(field, map2);
					map.put(field.getName(), map2);
				}
				if (campoFront.id() && id) {
					Map<String, Object> map2 = new HashMap<>();
					map2.put(Constantes.TIPO, campoFront.tipoDato());
					map2.put(Constantes.NOMBRE_FRONT, campoFront.nombre());
					map2.put(Constantes.ORDEN, campoFront.orden());
					map2.put(Constantes.TAMANIO, campoFront.tamanio() );
					validarVista(field, map2);
					map.put(field.getName(), map2);
				}
			}
		}
	}

	private static void validarVista(Field field, Map<String, Object> map2) {
		CampoOculto campoOculto = AnnotationUtils.findAnnotation(field, CampoOculto.class);
		if (campoOculto != null) {
			map2.put(Constantes.VISTA, "false");
		}
	}

	public static String buscarCampoMostrar(Object type) {
		for (Field field : UtileriasCatalogos.obtenerFields(type)) {
			CampoMostrar campoMostrar = AnnotationUtils.findAnnotation(field, CampoMostrar.class);
			if (campoMostrar != null) {
				return field.getName();
			}
		}
		return "";
	}

	public static Optional<Catalogos> buscarEnCatalogos(String typeName) {
		for (Catalogos cat : Catalogos.values()) {
			String c = cat.getType().getClass().getSimpleName();
			String[] t = typeName.split("\\.");
			if (t.length > 0 && c.equalsIgnoreCase(t[t.length - 1])) {
				return Optional.of(cat);
			}
		}
		return Optional.empty();
	}

	public static Map<Integer, Object> agregarTablasHijas(Object cats, Boolean id, Map<Integer, Object> map) {
		log.info("Catalogo a buscar complementario: {} ", cats.getClass().getSimpleName());
		for (Catalogos cat : Catalogos.values()) {
			Map<String, Object> campos = new HashMap<>();
			boolean encontrado = false;
			for (Field field : UtileriasCatalogos.obtenerFields(cat.getType())) {
				log.debug("field: {} clase: {}", field.getName(), cats.getClass().getSimpleName());
				if (field.getName().equalsIgnoreCase(cats.getClass().getSimpleName())) {
					log.debug("Catalogo encontrado complementario: {} ", cat);
					campos = validarMap(cat.getType(), id);
					encontrado = true;
				}
			}
			if (encontrado) {
				map.put(cat.getCatalogosComunes().getIdCatalogo(), campos);
			}
		}
		return map;
	}

	public static boolean encontrarTag(Catalogos catalogos, String tag) {
		for (Field field : UtileriasCatalogos.obtenerFields(catalogos.getType())) {
			if (field.getName().equalsIgnoreCase(tag)) {
				return true;
			}
		}
		return false;
	}

	public static String buscarId(Object type) {
		for (Field field : UtileriasCatalogos.obtenerFields(type)) {
			Id id = AnnotationUtils.findAnnotation(field, Id.class);
			if (id != null) {
				return field.getName();
			}
		}
		return "";
	}

	public static Map<String, Object> validarCampos(Object type, Map<String, JsonElement> asMap,
			Map<String, Object> valores, String prefix) {
		for (Entry<String, JsonElement> entry : asMap.entrySet()) {
			String llave = entry.getKey();
			JsonElement valor = entry.getValue();
			for (Field field : UtileriasCatalogos.obtenerFields(type)) {
				Optional<Catalogos> atributos = buscarEnCatalogos(field.getGenericType().getTypeName());
				if (atributos.isPresent() && !valor.isJsonPrimitive()) {
					log.debug("catalogo interno: {}", valor.getAsJsonObject().asMap());
					validarCampos(atributos.get().getType(), valor.getAsJsonObject().asMap(), valores,
							String.valueOf(field.getName() + "."));
				}
				log.debug("llave : {} - Field: {} equals: {}", llave, field.getName(),
						llave.equalsIgnoreCase(field.getName()));
				if (valor.isJsonPrimitive() && llave.equalsIgnoreCase(field.getName())) {
					log.debug("Los valores coinciden");
					valores.put(String.valueOf(prefix + llave), valor);
				}
			}
		}
		return valores;
	}

	public static Map<String, Object> mapearObjeto(BaseCatalogoModel base) {
		Map<String, Object> mapa = new HashMap<>();
		for (Field field : UtileriasCatalogos.obtenerFields(base)) {
			try {
				Optional<Catalogos> atributos = UtileriasCatalogos
						.buscarEnCatalogos(field.getGenericType().getTypeName());
				if (atributos.isPresent()) {
					log.info("Data: {}", atributos);
					CampoFront campoFront = AnnotationUtils.findAnnotation(field, CampoFront.class);
					if (campoFront != null) {
						Map<String, Object> map3 = new HashMap<>();
						map3.put(Constantes.TIPO, campoFront.tipoDato());
						map3.put(Constantes.NOMBRE_FRONT, campoFront.nombre());
						map3.put(Constantes.ID_CATALOGO, atributos.get().getCatalogosComunes().getIdCatalogo());
						map3.put(Constantes.CAMPO_MANDAR, UtileriasCatalogos.buscarId(atributos.get().getType()));
						map3.put(Constantes.CAMPO_MOSTRAR,
								UtileriasCatalogos.buscarCampoMostrar(atributos.get().getType()));
						ReflectionUtils.makeAccessible(field);
						Map<String, Object> map2 = new HashMap<>();
						for (Field field2 : UtileriasCatalogos.obtenerFields(field.get(base))) {
							ReflectionUtils.makeAccessible(field2);
							map2.put(field2.getName(), field2.get(field.get(base)));
						}
						map2.put(Constantes.METADATA, map3);
						mapa.put(field.getName(), map2);
					}
				} else {
					ReflectionUtils.makeAccessible(field);
					mapa.put(field.getName(), field.get(base));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				log.error("Error al leer el campo: {}", field.getName());
			}
		}
		return mapa;
	}

	public static String generarConsulta(Catalogos cat) {
		String consulta = "select c.*,{id} primaryKey  from {tabla} c";
		Field[] fields = cat.getType().getClass().getDeclaredFields();
		Table table = AnnotationUtils.findAnnotation(cat.getType().getClass(), Table.class);
		if (table != null) {
			String nombreTabla = camelCaseToLowerHyphen(table.name());
			consulta = consulta.replace("{tabla}", nombreTabla);
			for (Field fiedl : fields) {
				Id id = AnnotationUtils.findAnnotation(fiedl, Id.class);
				if (id != null) {
					String idTabla = camelCaseToLowerHyphen(fiedl.getName());
					consulta = consulta.replace("{id}", idTabla);
					break;
				}
			}
		}
		return consulta;
	}

	public static String generarConsulta2(Catalogos cat) {
		String consulta = "select c  from {tabla} c order by {id} asc";
		Field[] fields = cat.getType().getClass().getDeclaredFields();
		consulta = consulta.replace("{tabla}", cat.getType().getClass().getCanonicalName());
		for (Field fiedl : fields) {
			Id id = AnnotationUtils.findAnnotation(fiedl, Id.class);
			if (id != null) {
				consulta = consulta.replace("{id}", fiedl.getName());
				break;
			}
		}
		log.info("Consulta para reporte {}", consulta);
		return consulta;
	}

	public static String camelCaseToLowerHyphen(String s) {
		StringBuilder parsedString = new StringBuilder(s.substring(0, 1).toLowerCase());
		for (char c : s.substring(1).toCharArray()) {
			if (Character.isUpperCase(c)) {
				parsedString.append("_").append(Character.toLowerCase(c));
			} else {
				parsedString.append(c);
			}
		}
		return parsedString.toString().toLowerCase();
	}

}
