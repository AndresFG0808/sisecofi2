package com.sisecofi.admindevengados.microservicio;

import com.sisecofi.libreria.comunes.dto.plantillador.ContenidoPlantilladorPdfReponseDto;

import com.sisecofi.libreria.comunes.dto.plantillador.HtmlExcelListDto;
import com.sisecofi.libreria.comunes.dto.plantillador.NotificacionDTO;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "administracionUrl", url = "${url.administracion}")
public interface PlantilladorMicriservicio {

    @GetMapping("/plantillador/obtener-pdf/{idContenidoPlantillador}")
    ContenidoPlantilladorPdfReponseDto obtenerContenidoPlantilladorPDF(@PathVariable("idContenidoPlantillador") Long idContenidoPlantillador);

    @GetMapping("/obtener-documento/{idContenidoPlantillador}")
    ContenidoPlantilladorPdfReponseDto obtenerContenidoPlantilladorDocumento(@PathVariable("idContenidoPlantillador") Long idContenidoPlantillador);

    @GetMapping("/obtener-plantillador/{idTipoPlantillador}")
    List<PlantilladorModel> obtenerPlantillador(@PathVariable("idTipoPlantillador") Integer idTipoPlantillador);

    @GetMapping("/contenido-plantillador")
    ContenidoPlantilladorModel obtenerContenidoPlantillador(@RequestParam(value = "idPlantillador", required = false) Long idPlantillador,
			@RequestParam(value = "idContenidoSubPlantillador", required = false) Long idContenidoSubPlantillador);


    @GetMapping("/contenido-sub-plantillador")
    List<SubPlantilladorModel> obtenerContenidoSubPlantillador();

    @PostMapping("/proforma/html/pdf")
    byte[] obtenerArchivoProforma(@RequestBody HtmlExcelListDto htmlExcelListDto);

    @PostMapping("/proforma/html/excel")
    byte[] obtenerArchivoProformaExcel(@RequestBody HtmlExcelListDto htmlExcelListDto);

    @PostMapping("/notificacion/pago")
    byte[] obtenerArchivoNotificacionWord(@RequestBody NotificacionDTO request);

}
