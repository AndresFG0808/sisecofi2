package com.sisecofi.libreria.comunes.dto.plantillador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContenidoPlantilladorPdfReponseDto {

    String documentoBase64;
    private Long idPlantillador;
    private String header;
    private String footer;
    private String contenido;
}
