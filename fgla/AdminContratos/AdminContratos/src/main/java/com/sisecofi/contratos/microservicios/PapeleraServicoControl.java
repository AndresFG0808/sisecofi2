package com.sisecofi.contratos.microservicios;

import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "papeleraControlholder", url = "${url.papelera.internos.control}")
public interface PapeleraServicoControl {
    @PutMapping("")
    PapeleraDto generarId(@RequestBody PapeleraDto model);

    @DeleteMapping("/papelera-reciclaje/enviar-papelera")
    Boolean enviarPapelera(@RequestBody List<Archivo> archivos);
   
}
