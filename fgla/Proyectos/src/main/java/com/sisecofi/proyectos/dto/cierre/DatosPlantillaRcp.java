package com.sisecofi.proyectos.dto.cierre;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosPlantillaRcp {
	
	private Long idContenidoPlantillador;
	private Map<String, String> datosGenerales;
	private List<Map<String, String>> listaEncabezado;
	private List<Map<String, String>> listaDocumentos;
	
}
