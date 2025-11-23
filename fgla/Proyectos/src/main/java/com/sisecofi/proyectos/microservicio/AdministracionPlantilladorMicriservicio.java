package com.sisecofi.proyectos.microservicio;

import com.sisecofi.libreria.comunes.dto.plantillador.HtmlExcelListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "administracionPlantilladorUrl", url = "${url.administracion-plantillador}")
public interface AdministracionPlantilladorMicriservicio {

    @PostMapping("/cierre-rcp/html/excel")
    ByteArrayResource descargarExcel(@RequestBody HtmlExcelListDto data);

    @PostMapping("/cierre-rcp/html/pdf")
    ByteArrayResource descargarPdf(@RequestBody HtmlExcelListDto data);
}
