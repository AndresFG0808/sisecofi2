package com.sisecofi.libreria.comunes.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.ReflectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Slf4j
public class PistaUtil {

	private PistaUtil() {
	}

	public static String cadenaPistasObjecto(Object o) {
		StringBuilder s = new StringBuilder();
		for (Field field : PistaUtil.obtenerFields(o)) {
			try {
				ReflectionUtils.makeAccessible(field);
				if (field.get(o) != null) {
					s.append(field.getName());
					s.append(":");
					s.append(field.get(o));
					s.append("|");
				}
			} catch (IllegalAccessException e) {
	            log.warn("No se pudo acceder al campo '{}': {}", field.getName());
	        } catch (RuntimeException e) {
	            log.error("Error inesperado al procesar el campo '{}': {}", field.getName());
	        }catch (Exception e) {
				log.info("error al leer dato:{}");
			}
		}
		return s.toString();
	}

	private static List<Field> obtenerFields(Object cat) {
		List<Field> lista = new ArrayList<>();
		lista.addAll(Arrays.asList(cat.getClass().getDeclaredFields()));
		lista.addAll(Arrays.asList(cat.getClass().getSuperclass().getDeclaredFields()));
		return lista;
	}
}
