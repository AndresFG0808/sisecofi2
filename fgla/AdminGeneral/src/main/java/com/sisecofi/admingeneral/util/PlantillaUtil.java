package com.sisecofi.admingeneral.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaCarpetasDto;
import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.ArchivoPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.CarpetaPlantillaModel;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Slf4j
public class PlantillaUtil {

	private static final Pattern HLS = Pattern
			.compile("hsl\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})%\\s*,\\s*(\\d{1,3})%\\s*\\)");

	private PlantillaUtil() {
	}

	@SuppressWarnings("unchecked")
	public static <T> PlantillaDto<T> retornarEstructura(List<CarpetaPlantillaModel> carpeta,
	                                                     List<ArchivoPlantillaModel> arc,
	                                                     PlantillaVigenteModel plan,
	                                                     Integer idFase) {
	    return (PlantillaDto<T>) construirEstructura(carpeta, arc, plan, idFase, PlantillaDto::new);
	}

	@SuppressWarnings("unchecked")
	public static <T> PlantillaCarpetasDto<T> retornarEstructuraCarpeta(List<CarpetaPlantillaModel> carpeta,
	                                                                    List<ArchivoPlantillaModel> arc,
	                                                                    PlantillaVigenteModel plan,
	                                                                    Integer idFase) {
	    return (PlantillaCarpetasDto<T>) construirEstructura(carpeta, arc, plan, idFase, PlantillaCarpetasDto::new);
	}

	@SuppressWarnings("unchecked")
	private static <R> R construirEstructura(List<CarpetaPlantillaModel> carpeta,
	                                            List<ArchivoPlantillaModel> arc,
	                                            PlantillaVigenteModel plan,
	                                            Integer idFase,
	                                            Supplier<R> dtoSupplier) {

	    List<CarpetaPlantillaModel> listaMapa = new ArrayList<>();
	    Collections.sort(carpeta);
	    log.info("vigenteModel: {}", plan);
	    log.info("Carpetas totales: {}", carpeta.size());
	    log.info("Archivos totales: {}", arc.size());

	    for (int i = 0; i < carpeta.size(); i++) {
	        CarpetaPlantillaModel car = carpeta.get(i);
	        List<ArchivoPlantillaModel> archs = buscarIdCarpetaPlantilla(arc, car.getNombre());
	        if (!archs.isEmpty()) {
	            log.info("Agregando archivos: {} - {}", i, archs.size());
	            CarpetaPlantillaModel carpetaArchivo = new CarpetaPlantillaModel();
	            carpetaArchivo.setIdCarpetaPlantilla(car.getIdCarpetaPlantilla());
	            carpetaArchivo.setNivel(car.getNivel());
	            carpetaArchivo.setNombre(car.getNombre());
	            carpetaArchivo.setOrden(car.getOrden());
	            carpetaArchivo.setDato(car.getDato());
	            carpetaArchivo.setTipo(ConstantesAdminPlantilla.TIPO_CARPETA);
	            carpetaArchivo.setDescripcion(car.getDescripcion());
	            carpetaArchivo.setObligatorio(car.isObligatorio());
	            carpetaArchivo.setEstatus(car.isEstatus());
	            carpetaArchivo.setArchivos(archs);
	            listaMapa.add(carpetaArchivo);
	        }
	    }

	    Collections.reverse(listaMapa);

	    R obj = dtoSupplier.get(); 
	    if (obj instanceof PlantillaDto) {
	        ((PlantillaDto<CarpetaPlantillaModel>) obj).setPlantillaVigenteModel(plan);
	        ((PlantillaDto<CarpetaPlantillaModel>) obj).setCarpetas(listaMapa);
	        ((PlantillaDto<CarpetaPlantillaModel>) obj).setIdFase(idFase);
	    } else if (obj instanceof PlantillaCarpetasDto) {
	        ((PlantillaCarpetasDto<CarpetaPlantillaModel>) obj).setPlantillaVigenteModel(plan);
	        ((PlantillaCarpetasDto<CarpetaPlantillaModel>) obj).setCarpetas(listaMapa);
	        ((PlantillaCarpetasDto<CarpetaPlantillaModel>) obj).setIdFase(idFase);
	    }

	    return obj;
	}


	public static List<ArchivoPlantillaModel> buscarIdCarpetaPlantilla(List<ArchivoPlantillaModel> arc, String nombre) {
		List<ArchivoPlantillaModel> lista = arc.stream()
				.filter(d -> d.getCarpetaPlantillaModel().getNombre().equalsIgnoreCase(nombre))
				.collect(Collectors.toList());
		Collections.sort(lista);
		return lista;
	}

	public static String reemplazarHsl(String str) {
		Optional<List<String>> st = generarHsl(str);
		if (st.get() != null) {
			for (String dato : st.get()) {
				String[] cadena = dato.replace("hsl(", "").replace(")", "").replace("%", "").split(",");
				str = str.replace(dato.trim(), hslToHex(Double.valueOf(cadena[0]) / 100,
						Double.valueOf(cadena[1]) / 100, Double.valueOf(cadena[2]) / 100));
			}
		}
		str = str.replace("<strong>", "<b>");
		str = str.replace("</strong>", "</b>");
		return str;
	}

	public static Optional<List<String>> generarHsl(String str) {
		Matcher m = HLS.matcher(str);
		List<String> lista = new ArrayList<>();
		while (m.find()) {
			Matcher m1 = HLS.matcher(m.group(0));
			while (m1.find()) {
				lista.add(m1.group(0));
			}
		}
		return Optional.of(lista);
	}

	public static String hslToHex(double hue, double saturation, double lightness) {
		int red = 0;
		int green = 0;
		int blue = 0;

		if (saturation == 0) {
			red = (int) (lightness * 255);
			green = (int) (lightness * 255);
			blue = (int) (lightness * 255);
		} else {
			double h = hue % 360;
			if (h < 60) {
				blue = (int) ((1 - lightness) * 255);
				red = (int) ((1 - (h / 60) * saturation) * 255);
				green = (int) ((lightness + h / 60 * saturation) * 255);
			} else if (h < 120) {
				red = (int) ((1 - ((120 - h) / 60) * saturation) * 255);
				green = (int) ((lightness + (120 - h) / 60 * saturation) * 255);
				blue = (int) (lightness * 255);
			} else if (h < 180) {
				green = (int) ((1 - ((h - 120) / 60) * saturation) * 255);
				red = (int) ((lightness + (h - 120) / 60 * saturation) * 255);
				blue = (int) (lightness * 255);
			} else if (h < 240) {
				green = (int) ((1 - ((240 - h) / 60) * saturation) * 255);
				blue = (int) ((lightness + (240 - h) / 60 * saturation) * 255);
				red = (int) (lightness * 255);
			} else {
				red = (int) ((1 - ((360 - h) / 60) * saturation) * 255);
				blue = (int) ((lightness + (360 - h) / 60 * saturation) * 255);
				green = (int) (lightness * 255);
			}
		}

		int hexValue = ((red << 16) | (green << 8) | blue);
		return String.format("#%06x", hexValue);
	}

}
