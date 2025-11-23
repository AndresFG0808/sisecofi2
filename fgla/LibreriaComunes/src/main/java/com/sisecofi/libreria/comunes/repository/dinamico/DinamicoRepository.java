package com.sisecofi.libreria.comunes.repository.dinamico;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import com.sisecofi.libreria.comunes.dto.dinamico.CampoOrden;
import com.sisecofi.libreria.comunes.dto.dinamico.GenericReport;
import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.libreria.comunes.dto.dinamico.TableDto;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.Joiny;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.NameField;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.TableJoin;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
@RequiredArgsConstructor
@Repository
public class DinamicoRepository {

	private static final String REQUERIDO = "Requerido";
	private static final String SUM = "SUM";
	private static final String AND = " AND ";
	private static final String GROUP_BY = " group by ";
	private static final String WHERE = " where 1=1 ";
	private static final String COUNT = " count(*) ";
	private static final String SELECT = "select ";
	private static final String FROM = " from ";
	private static final String INNER_JOIN = " inner join ";
	private static final String ON = " on ";
	private static final String EQUALS = "=";
	private static final String POINT = ".";
	private final EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public PageGeneric generarData(GenericReport busquedaDto, String where, boolean pagination)
			throws IllegalArgumentException, IllegalAccessException, SecurityException {
		Set<TableDto> tablas = new HashSet<>();

		Set<CampoOrden> campOrden = generateSelectString(busquedaDto, tablas);
		Set<CampoOrden> campoJoinTabla = generateJoin(busquedaDto);
		log.info("campoJoinTabla: {}", campoJoinTabla);

		List<CampoOrden> listaOrden = new ArrayList<>(campOrden);
		List<String> camposSelect = listaOrden.stream().sorted().map(CampoOrden::getCampo).toList();
		List<String> camposFront = listaOrden.stream().sorted().map(CampoOrden::getNombreFront)
				.toList();

		List<CampoOrden> campoJoinTablaLista = new ArrayList<>(campoJoinTabla);
		List<String> campos = campoJoinTablaLista.stream().sorted().map(CampoOrden::getCampo)
				.toList();

		String select = StringUtils.join(camposSelect, ",");

		log.info("select generado con join: {}", select);

		int secuencia = 0;
		TableJoin tableAnterior = null;
		List<TableDto> newLista = new ArrayList<>(tablas);
		Collections.sort(newLista);

		StringBuilder builder = new StringBuilder();
		log.info("Tablas: {}", newLista.size());
		log.info("Campos: {}", listaOrden.size());

		String orderBy = "";
		if (!newLista.isEmpty()) {
			for (TableDto t : newLista) {
				TableJoin table = t.getJoin();
				Set<Joiny> joiny = t.getJoinys();
				if (secuencia == 0) {
					builder.append(table.tableJoin());
					builder.append(" " + table.alias() + " ");
					tableAnterior = table;
					orderBy = table.orderyBy();

					validarExtraJoiny(builder, joiny, table, campos);
				} else {
					if (!table.extraTableJoin().equals("")) {
						builder.append(INNER_JOIN);
						builder.append(table.extraTableJoin());
						builder.append(" " + table.extraAlias() + " ");
						builder.append(ON);
						builder.append(table.extraAlias());
						builder.append(POINT);
						builder.append(table.fieldJoin());
						builder.append(EQUALS);
						if (tableAnterior != null) {
							builder.append(tableAnterior.alias());
							builder.append(POINT);
							builder.append(tableAnterior.fieldJoin());
						}

						if (!table.extraJoinCondition().equals("")) {
							builder.append(" ");
							builder.append(table.extraJoinCondition());
							builder.append(" ");
						}

						builder.append(INNER_JOIN);
						builder.append(table.tableJoin());
						builder.append(" " + table.alias() + " ");
						builder.append(ON);
						builder.append(table.alias());
						builder.append(POINT);
						builder.append(table.extraFieldJoin());
						builder.append(EQUALS);
						builder.append(table.extraAlias());
						builder.append(POINT);
						builder.append(table.extraFieldJoin());

						if (!table.joinCondition().equals("")) {
							builder.append(" ");
							builder.append(table.joinCondition());
							builder.append(" ");
						}

						validarExtraJoiny(builder, joiny, table, campos);

					} else {
						builder.append(INNER_JOIN);
						builder.append(table.tableJoin());
						builder.append(" " + table.alias() + " ");
						builder.append(ON);
						builder.append(table.alias());
						builder.append(POINT);
						builder.append(table.fieldJoin());
						builder.append(EQUALS);
						if (!table.specificJoin().equals("")) {
							builder.append(table.specificJoin());
						} else {
							if (tableAnterior != null) {
								builder.append(tableAnterior.alias());
							}
						}
						builder.append(POINT);
						if (!table.specificFieldJoin().equals("")) {
							builder.append(table.specificFieldJoin());
						} else {
							if (tableAnterior != null) {
								builder.append(tableAnterior.fieldJoin());
							}
						}

						if (!table.joinCondition().equals("")) {
							builder.append(" ");
							builder.append(table.joinCondition());
							builder.append(" ");
						}

						validarExtraJoiny(builder, joiny, table, campos);
					}
				}
				secuencia++;
			}
		}
		Pageable pageable = PageRequest.of(busquedaDto.getPage(), busquedaDto.getSize());
		Page<Object[]> page;
		if (!newLista.isEmpty()) {

			StringBuilder consulta = new StringBuilder();
			consulta.append(SELECT);
			consulta.append(select);
			consulta.append(FROM);
			consulta.append(builder.toString());
			consulta.append(WHERE);
			consulta.append(where);			

			/** Agrega la condicion al where por ser la primera tabla la del from **/
			if (tableAnterior != null && !tableAnterior.joinCondition().equals("")) {
				if (!tableAnterior.joinCondition().toUpperCase().contains(AND.trim())) {
					consulta.append(AND);
				}
				consulta.append(tableAnterior.joinCondition());
			}

			if (!orderBy.equals("") && !busquedaDto.isAcumulada()) {
				consulta.append(" order by ");
				consulta.append(orderBy);
			}

			StringBuilder consultaCount = new StringBuilder();
			consultaCount.append(SELECT);
			consultaCount.append(COUNT);
			consultaCount.append(FROM);
			consultaCount.append(builder.toString());
			consultaCount.append(WHERE);
			consultaCount.append(where);
			
			Long conteo = (Long) entityManager.createNativeQuery(consultaCount.toString()).getSingleResult();
			log.info("Query count(): {}", conteo);
			boolean allMatch = true;
			if (busquedaDto.isAcumulada()) {
				List<String> camposSolos = new ArrayList<>(
					    listaOrden.stream().sorted().map(CampoOrden::getCampoSolo).toList()
					);
				log.debug("Campos solos: {}", orderBy);
				if (!listaOrden.stream().sorted().map(CampoOrden::getCampo).toList().stream()
						.allMatch(s -> s.contains(SUM))) {
					consulta.append(GROUP_BY);
					// agregar el order by al group by
					if (!orderBy.equals("") && busquedaDto.isAcumulada()) {
						camposSolos.add(orderBy);
					}
					String selectGroup = StringUtils.join(camposSolos, ",");
					log.debug("selectGroup: {}", selectGroup);
					consulta.append(selectGroup);
					allMatch = true;
				} else {
					allMatch = false;
				}
			}

			if (!orderBy.equals("") && busquedaDto.isAcumulada() && allMatch) {
				consulta.append(" order by ");
				consulta.append(orderBy);
			}

			if (pagination) {
				log.debug("setTotalPages: {}-{}", conteo.intValue() / busquedaDto.getSize(),
						conteo.intValue() % busquedaDto.getSize());

				consulta.append(" LIMIT ");
				consulta.append(busquedaDto.getSize());
				consulta.append(" OFFSET ");
				if (conteo.intValue() == busquedaDto.getSize()) {
					consulta.append((busquedaDto.getPage() - ((conteo.intValue() % busquedaDto.getSize()) == 1 ? 0 : 0))
							* busquedaDto.getSize());
				} /*
					 * else if (busquedaDto.getPage() > 0) { consulta.append((busquedaDto.getPage()
					 * - ((conteo.intValue() % busquedaDto.getSize()) == 0 ? 0 : 1))
					 * busquedaDto.getSize()); }
					 */ else {
					consulta.append(busquedaDto.getPage() * busquedaDto.getSize());
				}
			}

			if (listaOrden.size() == 1) {
				List<Object> lista = entityManager.createNativeQuery(consulta.toString()).getResultList();
				List<Object[]> lista2 = new ArrayList<>();
				for (Object l : lista) {
					lista2.add(new Object[] { l });
				}
				log.info("Query1: {}", consulta.toString());
				log.info("Objetos encontrados1: {}", lista.size());
				page = new PageImpl<>(lista2, pageable, conteo);
			} else {
				List<Object[]> lista = entityManager.createNativeQuery(consulta.toString()).getResultList();
				log.info("Query2: {}", consulta.toString());
				log.info("Objetos encontrados2: {}", lista.size());
				page = new PageImpl<>(lista, pageable, conteo);
			}
			return new PageGeneric(page, camposFront);
		}
		return null;
	}

	public Set<CampoOrden> generateSelectString(GenericReport busquedaDto, Set<TableDto> tablas)
			throws IllegalArgumentException, IllegalAccessException, SecurityException {
		Set<CampoOrden> campos = new HashSet<>();
		for (Field field : busquedaDto.getDataReporteDto().getClass().getDeclaredFields()) {
			ReflectionUtils.makeAccessible(field);
			if (field.get(busquedaDto.getDataReporteDto()) != null) {
				for (Field field2 : field.get(busquedaDto.getDataReporteDto()).getClass().getDeclaredFields()) {
					if (!field2.getName().contains(REQUERIDO)) {
						agregarCampoTabla(busquedaDto, tablas, campos, field, field2);
					}

				}
			}
		}
		return campos;
	}

	@SuppressWarnings("deprecation")
	private void agregarCampoTabla(GenericReport busquedaDto, Set<TableDto> tablas, Set<CampoOrden> campos, Field field,
			Field field2) throws IllegalArgumentException, IllegalAccessException {
		Object obj = retornarObjecto(field, busquedaDto);
		ReflectionUtils.makeAccessible(field2);
		if ((boolean) field2.get(obj)) {
			TableJoin tabla = AnnotationUtils.findAnnotation(field, TableJoin.class);
			if (tabla != null) {
				NameField name = AnnotationUtils.findAnnotation(field2, NameField.class);
				String alias = tabla.alias();
				if (name != null) {
					log.debug("Alias: {}", alias);
					tablas.add(new TableDto(alias, tabla,
							AnnotationUtils.getDeclaredRepeatableAnnotations(field, Joiny.class)));
					if (name.fieldComposite()) {
						log.debug("fieldComposite:{}", name.nameField());
						CampoOrden campo = CampoOrden.builder().campo(name.nameField()).orden(name.order())
								.nombreFront(name.nombreFront()).acumulada(busquedaDto.isAcumulada())
								.function(name.function()).build();
						campos.add(campo);
					} else {
						log.debug("field:{}", name.nameField());
						StringBuilder builder = new StringBuilder();
						builder.append(alias);
						builder.append(POINT);
						builder.append(name.nameField());
						CampoOrden campo = CampoOrden.builder().campo(builder.toString()).orden(name.order())
								.nombreFront(name.nombreFront()).acumulada(busquedaDto.isAcumulada())
								.function(name.function()).build();
						campos.add(campo);
					}
				}
				if (field2.getName().equals("requerido")) {
					/* se agrega la tabla de la cual depende sin necesidad de seleccionar un dato */
					tablas.add(new TableDto(alias, tabla,
							AnnotationUtils.getDeclaredRepeatableAnnotations(field, Joiny.class)));
				}
			}
		}
	}

	public Set<CampoOrden> generateJoin(GenericReport busquedaDto)
			throws IllegalArgumentException, IllegalAccessException, SecurityException {
		Set<CampoOrden> campos = new HashSet<>();
		for (Field field : busquedaDto.getDataReporteDto().getClass().getDeclaredFields()) {
			ReflectionUtils.makeAccessible(field);
			if (field.get(busquedaDto.getDataReporteDto()) != null) {
				for (Field field2 : field.get(busquedaDto.getDataReporteDto()).getClass().getDeclaredFields()) {
					if (field2.getName().contains(REQUERIDO)) {
						ReflectionUtils.makeAccessible(field2);
						Object obj = retornarObjecto(field, busquedaDto);
						log.info("Requerido: {}", obj);
						log.info("Requerido: {}", (boolean) field2.get(obj));
						agregarDatoJoin(busquedaDto, field, field2, campos);
					} else {
						ReflectionUtils.makeAccessible(field2);
						agregarDatoJoin(busquedaDto, field, field2, campos);
					}
				}
			}
		}
		return campos;
	}

	private void agregarDatoJoin(GenericReport busquedaDto, Field field, Field field2, Set<CampoOrden> campos)
			throws IllegalArgumentException, IllegalAccessException {
		Object obj = retornarObjecto(field, busquedaDto);
		if ((boolean) field2.get(obj)) {
			TableJoin tabla = AnnotationUtils.findAnnotation(field, TableJoin.class);
			if (tabla != null) {
				NameField name = AnnotationUtils.findAnnotation(field2, NameField.class);
				String alias = tabla.alias();
				if (name != null) {
					log.debug("Alias: {}", alias);
					if (name.fieldComposite()) {
						log.debug("fieldComposite:{}", name.nameField());
						CampoOrden campo = CampoOrden.builder().campo(name.nameField()).orden(name.order())
								.nombreFront(name.nombreFront()).acumulada(busquedaDto.isAcumulada())
								.function(name.function()).build();
						campos.add(campo);
					} else {
						log.debug("field:{}", name.nameField());
						StringBuilder builder = new StringBuilder();
						builder.append(alias);
						builder.append(POINT);
						builder.append(name.nameField());
						CampoOrden campo = CampoOrden.builder().campo(builder.toString()).orden(name.order())
								.nombreFront(name.nombreFront()).acumulada(busquedaDto.isAcumulada())
								.function(name.function()).build();
						campos.add(campo);
					}
				}
			}
		}
	}

	private void validarExtraJoiny(StringBuilder builder, Set<Joiny> joinys, TableJoin table, List<String> campos) {
		log.info("Tamaño joiny: {}", joinys.size());
		for (Joiny joiny : joinys) {
			log.debug("Joiny: {}-{}", validarAgregarTabla(campos, joiny), joiny.tableJoin());
			if (validarAgregarTabla(campos, joiny)) {
				builder.append(INNER_JOIN);
				builder.append(joiny.tableJoin());
				builder.append(" " + joiny.alias() + " ");
				builder.append(ON);
				builder.append(joiny.alias());
				builder.append(POINT);

				if (!joiny.fieldJoinReverse().equals("")) {
					builder.append(joiny.fieldJoinReverse());
				} else {
					builder.append(joiny.fieldJoin());
				}

				builder.append(EQUALS);

				if (!joiny.specificJoin().equals("")) {
					builder.append(joiny.specificJoin());
					builder.append(POINT);
					builder.append(joiny.fieldJoin());
				} else {
					builder.append(table.alias());
					builder.append(POINT);
					builder.append(joiny.fieldJoin());
				}
				/* Agregar una condicion de alguna tabla de la union */
				if (!joiny.joinCondition().equals("")) {
					builder.append(" ");
					builder.append(joiny.joinCondition());
					builder.append(" ");
				}
				if (!joiny.extraTableJoin().equals("")) {
					builder.append(INNER_JOIN);
					builder.append(joiny.extraTableJoin());
					builder.append(" " + joiny.extraAlias() + " ");
					builder.append(ON);
					builder.append(joiny.extraAlias());
					builder.append(POINT);
					builder.append(joiny.extraFieldJoin());
					builder.append(EQUALS);
					builder.append(joiny.alias());
					builder.append(POINT);
					builder.append(joiny.extraFieldJoin());
				}
			}
		}
	}

	private boolean validarAgregarTabla(List<String> campos, Joiny joiny) {
		for (String dato : joiny.campos()) {
			log.debug("Campos: {} dato: {} contains: {}", campos, dato, campos.contains(dato));
			for (String campo : campos) {
				if (campo.contains(dato)) {
					return true;
				}
			}
		}
		return false;
	}

	private Object retornarObjecto(Field field, GenericReport busquedaDto) {
		try {
			String str = field.getName();
			String met = "get" + str.substring(0, 1).toUpperCase() + str.substring(1);
			Method method = busquedaDto.getDataReporteDto().getClass().getDeclaredMethod(met);
			return method.invoke(busquedaDto.getDataReporteDto());
		} catch (NoSuchMethodException e) {
			log.error("El método especificado no existe");
		} catch (InvocationTargetException e) {
			log.error("Error al invocar el método");
		} catch (IllegalAccessException e) {
			log.error("No se tiene acceso al método");
		} catch (Exception e) {
			log.error("Error inesperado");
		}
		return null;
	}

}
