package com.sisecofi.admingeneral.service.plantillador;

import com.sisecofi.admingeneral.dto.plantillador.HtmlExcelListDto;
import com.sisecofi.libreria.comunes.dto.plantillador.ContenidoPlantilladorPdfReponseDto;
import com.sisecofi.admingeneral.dto.plantillador.RequestPlantilla;

public interface HtmlWordService {

	byte[] convertirHmltWord(RequestPlantilla plantilla);

	byte[] convertirHmltPdfSoloWindows(RequestPlantilla plantilla);
	
	byte[] convertirHmltPdf(RequestPlantilla plantilla);

	byte[] cierreProyectoPdf(HtmlExcelListDto inputs);

	ContenidoPlantilladorPdfReponseDto obtenerPdfContenidoPlantillador(Long idPlantillador);

	ContenidoPlantilladorPdfReponseDto obtenerContenidoPlantilladorDoc(Long idPlantillador);


}
