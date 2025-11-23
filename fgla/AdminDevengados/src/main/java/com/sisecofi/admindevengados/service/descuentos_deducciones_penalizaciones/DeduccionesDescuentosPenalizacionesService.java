package com.sisecofi.admindevengados.service.descuentos_deducciones_penalizaciones;

import java.util.List;

import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.admindevengados.dto.deducciones_descuentos_penalizaciones_dto.*;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;

public interface DeduccionesDescuentosPenalizacionesService {

    // crear

    List<DeduccionesDPDto> crearDdp(List<DeduccionesDPDto> ddpList);

    // modificar

    List<DeduccionesDPModiDto> modificarDdp(List<DeduccionesDPModiDto> ddpList);

    // soft delete

    void eliminacionLogicaDdp(List<Long> idDdpList);

    // busqueda
    List<DeduccionesDpConsultaDto> buscarPorIdDictamen(Long dictamenId);

    List<MonedaResponseDto> buscarMonedasPorIdDictamen(Long dictamenId);

    ArchivoPlantillaDictamenModel procesarArchivoPenasDeducciones(MultipartFile detallePenasDeducciones,
            Long idDictamen);

    String descargarArchivo(String path, Long dictamenId);

    ArchivoDto obtenerArchivoPorIdDictamen(Long idDictamen);

    Boolean rechazarProforma(Long idDictamen, String justificacion);

    Boolean validarDictamen(Long idDictamen);

    List<SubPlantilladorModel> obtenerPlantillasProforma();

    String obtenerArchivoProforma(ProformaArchivoRequestDto request);

}
