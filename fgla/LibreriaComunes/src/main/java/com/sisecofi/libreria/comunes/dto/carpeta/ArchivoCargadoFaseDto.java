package com.sisecofi.libreria.comunes.dto.carpeta;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class ArchivoCargadoFaseDto extends RepresentationModel<ArchivoCargadoFaseDto> {
	private MultipartFile file;
    private String nombreFase;
    private Long idContrato;
    private Long idReintegro;
    private Long idProyecto;
    private Long idDictamen;
    private boolean obligatorio;
    private boolean noAplica;
    private String justificacion;
    private String type;
    private String idArchivo;
    private String carpeta;
    private String descripcion;
    private String carpetaReintegro;
    
}
