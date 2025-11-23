package com.sisecofi.catalogos.util.consumer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.sisecofi.catalogos.dto.TituloDto;
import com.sisecofi.catalogos.util.UtileriasCatalogos;
import com.sisecofi.catalogos.util.enums.ErroresEnum;
import com.sisecofi.catalogos.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoFront;
import com.sisecofi.libreria.comunes.util.anotaciones.CampoReporte;
import com.sisecofi.libreria.comunes.util.archivo.BaseExcel;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Component
@Slf4j
@Scope("prototype")
public class ReporteCatConsumer extends BaseExcel implements Consumer<Object> {

	private List<TituloDto> titulos;

	@Override
	public void accept(Object t) {
		Row row = sheet.createRow(numeroRenglon++);
		int contador = 0;
		for (TituloDto ti : titulos) {
			try {
				Field field = t.getClass().getDeclaredField(ti.getNombre());
				ReflectionUtils.makeAccessible(field);
				validarTipoDato(field, row, t, contador);
			} catch (NoSuchFieldException e) {
				try {
					Field field = t.getClass().getSuperclass().getDeclaredField(ti.getNombre());
					ReflectionUtils.makeAccessible(field);
					validarTipoDato(field, row, t, contador);
				} catch (Exception e1) {
					throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE, e);
				}
			} catch (Exception e) {
				throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE, e);
			}
			contador++;
		}
	}

	private void validarTipoDato(Field field, Row row, Object t, int contador) {
		try {
			if (field.get(t) != null) {
				if (field.get(t) instanceof LocalDateTime fecha) {
					crearCelda(row, contador, fecha);
				} else if (field.get(t) instanceof Boolean valor) {
					boolean activo = Boolean.TRUE.equals(valor);
					crearCelda(row, contador, activo ? "Activo" : "Inactivo");
				} else if (field.get(t) instanceof List) {
					validarAdministradores(t, row, contador);
				} else {
					/* se limpia para las listas */
					if (contador==0) {
						crearCelda(row, contador, numeroRenglon - 1); // Id consecutivo solo para exportar
					}else {
						crearCelda(row, contador, String.valueOf(field.get(t)).replace("[", "").replace("]", ""));
					}
				}
			}
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE, e);
		}
	}

	private void validarAdministradores(Object obj, Row row, int contador) {
		try {
			Method method = obj.getClass().getMethod("obtenerAdministrador");
			String administrador = (String) method.invoke(obj);
			if (administrador != null) {
				crearCelda(row, contador, administrador);
			}

		} catch (NoSuchMethodException e) {
			log.error("El método 'obtenerAdministrador' no existe en la clase del objeto ");
		} catch (IllegalAccessException e) {
			log.error("No se puede acceder al método 'obtenerAdministrador' ");
		} catch (InvocationTargetException e) {
			log.error("Error al invocar el método 'obtenerAdministrador' ");
		}
	}

	public void agregarCabeceras(Object t) {
		Row row = sheet.createRow(0);
		row.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
		int i = 0;
		titulos = new ArrayList<>();
		for (Field field : UtileriasCatalogos.obtenerFields(t)) {
			log.info("Field: {}", field.getName());
			CampoFront campoFront = AnnotationUtils.findAnnotation(field, CampoFront.class);
			CampoReporte campoReporte = AnnotationUtils.findAnnotation(field, CampoReporte.class);
			if (campoFront != null && campoReporte != null) {
				ReflectionUtils.makeAccessible(field);
				titulos.add(new TituloDto(campoFront.nombre().equals("") ? campoFront.nombre() : field.getName(),
						campoFront.orden(), campoReporte.nombre()));
			}
			if (campoFront == null && campoReporte != null) {
				ReflectionUtils.makeAccessible(field);
				titulos.add(new TituloDto(field.getName(), 100, campoReporte.nombre()));
			}
		}
		Collections.sort(titulos);
		for (TituloDto titulo : titulos) {
			Cell cell = row.createCell(i);
			CellStyle style = workbook.createCellStyle();
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			style.setWrapText(true);
			style.setFillPattern(FillPatternType.LEAST_DOTS);
			style.setFillBackgroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderBottom(BorderStyle.THIN);
			Font font = sheet.getWorkbook().createFont();
			font.setFontName("Calibri");
			font.setBold(true);
			font.setColor(HSSFColorPredefined.BLACK.getIndex());
			style.setFont(font);
			cell.setCellStyle(style);
			cell.setCellValue(titulo.getNombreReporte());
			i++;
		}

	}

}
