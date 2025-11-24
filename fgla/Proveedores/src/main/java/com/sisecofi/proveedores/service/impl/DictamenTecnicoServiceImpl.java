package com.sisecofi.proveedores.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.catalog.CatalogException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proveedores.dto.DictamenTecnicoDto;
import com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto;
import com.sisecofi.proveedores.microservicio.CatalogoMicroservicio;
import com.sisecofi.libreria.comunes.model.catalogo.CatResultadoDictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;
import com.sisecofi.libreria.comunes.model.proveedores.DictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.proveedores.repository.DictamenTecnicoRepository;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.repository.ResultadoDictamenTecnicoRepository;
import com.sisecofi.proveedores.repository.TituloServicioRepository;
import com.sisecofi.proveedores.service.DictamenTecnicoService;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.util.enums.ErroresEnum;
import com.sisecofi.proveedores.util.exception.CatalogoException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DictamenTecnicoServiceImpl implements DictamenTecnicoService {

    private final DictamenTecnicoRepository dictamenTecnicoRepository;
    private final PistaService pistaService;
    private final CatalogoMicroservicio catalogoMicroservicio;
    private final ProveedorRepository proveedorRepository;
    private final TituloServicioRepository tituloServicioRepository;
    private final ResultadoDictamenTecnicoRepository resultadoDictamenTecnicoRepository;
    private static final String PISTA_GEN = "Pista generada: {}";
    private static final String ID_PROVEEDOR= "Id Proveedor: ";
    private static final String ID_DICTAMEN_TECNICO = "Id del dictamen técnico: ";
    private static final String SERVICIO= "Servicio: ";
    private static final String ANIO ="Año: ";
    private static final String RESPONSABLE = "Responsable: ";

    @Transactional
@Override
public DictamenTecnicoResponseDto crearDictamenTecnico(DictamenTecnicoDto dictamenTecnicoDto) {
    // Crear el modelo de dictamen técnico
    DictamenTecnicoModel dictamenTecnicoModel = new DictamenTecnicoModel();
    dictamenTecnicoModel.setAnio(dictamenTecnicoDto.getAnio());
    dictamenTecnicoModel.setObservacion(dictamenTecnicoDto.getObservacion());
    dictamenTecnicoModel.setResponsable(dictamenTecnicoDto.getResponsable());
    dictamenTecnicoModel.setEstatusEliminacionLogicaDictamen(true);
    dictamenTecnicoModel.setIdResultadoDictamenTecnico(dictamenTecnicoDto.getIdResultadoDictamenTecnico());
    dictamenTecnicoModel.setIdTituloServicio(dictamenTecnicoDto.getIdServicioTitulo());

    // Obtener el catálogo de títulos de servicio
    CatTituloServicio catTituloServicio = tituloServicioRepository.findByIdTituloServicio(dictamenTecnicoDto.getIdServicioTitulo())
            .orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_ID_TITULOSERVICIO));
    dictamenTecnicoModel.setCatTituloServicioModel(catTituloServicio);

    // Obtener el catálogo de resultados del dictamen técnico
    CatResultadoDictamenTecnicoModel catResultadoDictamenTecnico = resultadoDictamenTecnicoRepository.findById(dictamenTecnicoDto.getIdResultadoDictamenTecnico())
            .orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_MSJ_CATALOGO_NO_ENCONTRADO));
    dictamenTecnicoModel.setCatResultadoDictamenTecnicoModel(catResultadoDictamenTecnico);

    // Asociar el proveedor
    ProveedorModel proveedorModel = proveedorRepository.findByIdProveedor(dictamenTecnicoDto.getIdProveedor())
            .orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_MSJ_SIN_USUARIO));
    dictamenTecnicoModel.setProveedorModel(proveedorModel);

    Integer maxOrdenDictamen = dictamenTecnicoRepository.findMaxOrdenDictamenProveedorByIdProveedor(dictamenTecnicoDto.getIdProveedor());

    int ordenDictamenProveedor = (maxOrdenDictamen == null) ? 1 : maxOrdenDictamen + 1;

    dictamenTecnicoModel.setOrdenDictamenProveedor(ordenDictamenProveedor);



    // Guardar el dictamen técnico
    try {
        dictamenTecnicoRepository.save(dictamenTecnicoModel);
    } catch (Exception e) {
    	log.error("Error al guardar dictamen técnico. Datos: {}", dictamenTecnicoModel);
        throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_DICTAMEN, e);
    }

    // Crear la respuesta
    DictamenTecnicoResponseDto responseDto = new DictamenTecnicoResponseDto();
    responseDto.setOrdenDictamenProveedor(dictamenTecnicoModel.getOrdenDictamenProveedor());
    responseDto.setIdDictamenTecnicoProveedor(dictamenTecnicoModel.getIdDictamenTecnicoProveedor());
    responseDto.setIdTituloServicio(catTituloServicio.getIdTituloServicio());
    responseDto.setNombreTituloServicio(catTituloServicio.getNombre());
    responseDto.setAnio(dictamenTecnicoModel.getAnio());
    responseDto.setIdResultadoDictamenTecnico(catResultadoDictamenTecnico.getIdResultadoDictamenTecnico());
    responseDto.setResultado(catResultadoDictamenTecnico.getResultado());
    responseDto.setObservacion(dictamenTecnicoModel.getObservacion());
    responseDto.setResponsable(dictamenTecnicoModel.getResponsable());

    // Registrar la pista de auditoría
    StringBuilder builder = new StringBuilder();
    builder.append(ID_PROVEEDOR).append(proveedorModel.getIdProveedor()).append(" | ")
           .append(ID_DICTAMEN_TECNICO).append(dictamenTecnicoModel.getIdDictamenTecnicoProveedor()).append(" | ")
           .append(SERVICIO).append(catTituloServicio.getNombre()).append(" | ")
           .append(ANIO).append(dictamenTecnicoModel.getAnio()).append(" | ")
           .append("Cumple: ").append(catResultadoDictamenTecnico.getResultado()).append(" | ")
           .append(RESPONSABLE).append(dictamenTecnicoModel.getResponsable()).append(" | ")
           .append("Observación ").append(dictamenTecnicoModel.getObservacion());

    log.info(PISTA_GEN, builder.toString());



    // pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


    // TipoSeccionPista.PROVEEDOR_DICTAMEN_TECNICO.getIdSeccionPista(), builder.toString(),


    // Optional.empty());

    return responseDto;
}

    // Actualizacion dictamen tecnico
    @Transactional
    @Override
    public DictamenTecnicoResponseDto actaulizarDictamenTecnico(Long idDictamenTecnicoProveedor,
            DictamenTecnicoDto dictamenTecnicoDto) {

        DictamenTecnicoModel dictamenTecnicoExiste = dictamenTecnicoRepository
                .findByIdDictamenTecnicoProveedor(idDictamenTecnicoProveedor)
                .orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_DICTAMEN));

        dictamenTecnicoExiste.setAnio(dictamenTecnicoDto.getAnio());
        dictamenTecnicoExiste.setObservacion(dictamenTecnicoDto.getObservacion());
        dictamenTecnicoExiste.setResponsable(dictamenTecnicoDto.getResponsable());

        CatTituloServicio catTituloServicioModel = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
                CatalogosComunes.PROVEEDOR_TITULO_SERVICIO.getIdCatalogo(), dictamenTecnicoDto.getIdServicioTitulo(),
                new CatTituloServicio());

        dictamenTecnicoExiste.setCatTituloServicioModel(catTituloServicioModel);

        CatResultadoDictamenTecnicoModel catResultadoDictamenTecnicoModel = catalogoMicroservicio
                .obtenerInformacionCatalogoIdReference(
                        CatalogosComunes.RESULTADO_DICTAMEN_TECNICO.getIdCatalogo(),
                        dictamenTecnicoDto.getIdResultadoDictamenTecnico(),
                        new CatResultadoDictamenTecnicoModel());

        dictamenTecnicoExiste.setCatResultadoDictamenTecnicoModel(catResultadoDictamenTecnicoModel);

        try {
            dictamenTecnicoRepository.save(dictamenTecnicoExiste);
            log.info("Actualizar Dictamen tecnico {}", dictamenTecnicoDto);

        } catch (CatalogException e) {
            throw new CatalogoException(ErroresEnum.ERROR_AL_ACTUALIZAR_DICTAMEN, e);
        }

        DictamenTecnicoResponseDto responseDto = new DictamenTecnicoResponseDto();
        responseDto.setIdDictamenTecnicoProveedor(dictamenTecnicoExiste.getIdDictamenTecnicoProveedor());
        responseDto.setIdTituloServicio(catTituloServicioModel.getIdTituloServicio());
        responseDto.setNombreTituloServicio(catTituloServicioModel.getNombre());
        responseDto.setAnio(dictamenTecnicoExiste.getAnio());
        responseDto.setIdResultadoDictamenTecnico(catResultadoDictamenTecnicoModel.getIdResultadoDictamenTecnico());
        responseDto.setResultado(catResultadoDictamenTecnicoModel.getResultado());
        responseDto.setObservacion(dictamenTecnicoExiste.getObservacion());
        responseDto.setResponsable(dictamenTecnicoExiste.getResponsable());

        // pistas auditoria -Insertar
        StringBuilder builder = new StringBuilder();

        builder.append(ID_PROVEEDOR).append(dictamenTecnicoExiste.getProveedorModel().getIdProveedor())
                .append(" | ")
                .append(ID_DICTAMEN_TECNICO).append(dictamenTecnicoExiste.getIdDictamenTecnicoProveedor())
                .append(" | ")
                .append(SERVICIO).append(catTituloServicioModel.getNombre()).append(" | ")
                .append(ANIO).append(dictamenTecnicoExiste.getAnio()).append(" | ")
                .append("Resultado: ").append(dictamenTecnicoExiste.getCatResultadoDictamenTecnicoModel().getNombre())
                .append(" | ")
                .append(RESPONSABLE).append(dictamenTecnicoExiste.getResponsable()).append(" | ")
                .append("Obervación: ").append(dictamenTecnicoExiste.getObservacion());

        log.info(PISTA_GEN, builder.toString());



        // pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


        // TipoSeccionPista.PROVEEDOR_DICTAMEN_TECNICO.getIdSeccionPista(), builder.toString(),


        // Optional.empty());

        return responseDto;

    }

    // eliminacion dictamen tecnico

    @Transactional
    @Override
    public void eliminacionLogicaDictamenTecnico(Long idDictamenTecnicoProveedor) {
        DictamenTecnicoModel dictamenTecnicoModel = dictamenTecnicoRepository
                .findByIdDictamenTecnicoProveedorAndEstatusEliminacionLogicaDictamenTrue(idDictamenTecnicoProveedor)
                .orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_DICTAMEN));

        dictamenTecnicoModel.setEstatusEliminacionLogicaDictamen(false);
        dictamenTecnicoModel.setFechaBajaDictamen(LocalDateTime.now());
        dictamenTecnicoRepository.save(dictamenTecnicoModel);

        Long idProveedor = dictamenTecnicoModel.getProveedorModel().getIdProveedor();

        List<DictamenTecnicoModel> dictamenRestante = dictamenTecnicoRepository
                .findActiveDictamenesByProveedor(idProveedor);

        int nuevoOrden = 1;
        for (DictamenTecnicoModel dictamen : dictamenRestante) {
            dictamen.setOrdenDictamenProveedor(nuevoOrden++);
            dictamenTecnicoRepository.save(dictamen);

        }

        // pistas auditoria - Eliminar
        StringBuilder builder = new StringBuilder();

        builder.append(ID_PROVEEDOR).append(dictamenTecnicoModel.getProveedorModel().getIdProveedor()).append(" | ")
                .append(ID_DICTAMEN_TECNICO).append(dictamenTecnicoModel.getIdDictamenTecnicoProveedor())
                .append(" | ")
                .append(SERVICIO).append(dictamenTecnicoModel.getCatTituloServicioModel().getNombre())
                .append(" | ")
                .append(ANIO).append(dictamenTecnicoModel.getAnio()).append(" | ")
                .append("Resultado: ").append(dictamenTecnicoModel.getCatResultadoDictamenTecnicoModel().getNombre())
                .append(" | ")
                .append(RESPONSABLE).append(dictamenTecnicoModel.getResponsable()).append(" | ")
                .append("Obervación: ").append(dictamenTecnicoModel.getObservacion());

        log.info(PISTA_GEN, builder.toString());



        // pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.BORRA_REGISTRO.getId(),


        // TipoSeccionPista.PROVEEDOR_DICTAMEN_TECNICO.getIdSeccionPista(), builder.toString(),


        // Optional.empty());
    }

    @SuppressWarnings("unused")
    private void validarNumeroTitulo(Long idServicioTitulo) {
        Integer idSer = Integer.parseInt("" + idServicioTitulo);

        if (idServicioTitulo == null) {
            throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_TITULO);
        }

        CatTituloServicio lista = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
                CatalogosComunes.PROVEEDOR_TITULO_SERVICIO.getIdCatalogo(), idSer, new CatTituloServicio());

        if (lista == null) {
            throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_TITULO);
        }

    }

    // consultar
    @Override
    public DictamenTecnicoResponseDto consultarDictamenPorId(Long idDictamenTecnicoProveedor) {
        DictamenTecnicoModel dictamenTecnicoModel = dictamenTecnicoRepository
                .findByIdDictamenTecnicoProveedorAndEstatusEliminacionLogicaDictamenTrue(idDictamenTecnicoProveedor)
                .orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_DICTAMEN));

        Long idProveedor = dictamenTecnicoModel.getProveedorModel().getIdProveedor();

        CatTituloServicio catTituloServicioModel = dictamenTecnicoModel
                .getCatTituloServicioModel();

        CatResultadoDictamenTecnicoModel catResultadoDictamenTecnicoModel = dictamenTecnicoModel
                .getCatResultadoDictamenTecnicoModel();

        DictamenTecnicoResponseDto responseDto = new DictamenTecnicoResponseDto();
        responseDto.setIdDictamenTecnicoProveedor(dictamenTecnicoModel.getIdDictamenTecnicoProveedor());
        responseDto.setIdTituloServicio(catTituloServicioModel.getIdTituloServicio());
        responseDto.setNombreTituloServicio(catTituloServicioModel.getNombre());
        responseDto.setAnio(dictamenTecnicoModel.getAnio());
        responseDto.setIdResultadoDictamenTecnico(catResultadoDictamenTecnicoModel.getIdResultadoDictamenTecnico());
        responseDto.setResultado(catResultadoDictamenTecnicoModel.getResultado());
        responseDto.setObservacion(dictamenTecnicoModel.getObservacion());
        responseDto.setResponsable(dictamenTecnicoModel.getResponsable());

        // pistas auditoria - consulta

        StringBuilder builder = new StringBuilder();
        builder.append("Id Proveedor ").append(idProveedor);



        // pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


        // TipoSeccionPista.PROVEEDOR_DICTAMEN_TECNICO.getIdSeccionPista(),


        // builder.toString(), Optional.empty());

        return responseDto;

    }

    // reporte excel dictamen
    public List<DictamenTecnicoResponseDto> obtenerReporteDictamenProveedor(Long idProveedor) {
        List<DictamenTecnicoModel> dictamenLista = dictamenTecnicoRepository
                .findByIdDictamenTecnicoProveedorAndEstatusEliminacionLogicaDictamenTrue();

        return dictamenLista.stream()
                .map(model -> {
                    DictamenTecnicoResponseDto dto = new DictamenTecnicoResponseDto();
                    dto.setOrdenDictamenProveedor(model.getOrdenDictamenProveedor());
                    dto.setAnio(model.getAnio());
                    dto.setObservacion(model.getObservacion());
                    dto.setResponsable(model.getResponsable());

                    if (model.getProveedorModel() != null) {
                        dto.setIdProveedor(model.getProveedorModel().getIdProveedor());

                    }

                    if (model.getCatTituloServicioModel() != null) {
                        dto.setNombreTituloServicio(model.getCatTituloServicioModel().getNombre());
                    }

                    if (model.getCatResultadoDictamenTecnicoModel() != null) {
                        dto.setResultado(model.getCatResultadoDictamenTecnicoModel().getResultado());
                    }

                    return dto;

                })
                .filter(dto -> dto.getIdProveedor().equals(idProveedor))
                .toList();

    }

    @SuppressWarnings("unused")
    private void validarResultado(Long idCatResultadoDictamen) {
        Integer idSer = Integer.parseInt("" + idCatResultadoDictamen);

        if (idCatResultadoDictamen == null) {
            throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_RESULTADO);
        }

        CatResultadoDictamenTecnicoModel lista = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
                CatalogosComunes.RESULTADO_DICTAMEN_TECNICO.getIdCatalogo(), idSer,
                new CatResultadoDictamenTecnicoModel());

        if (lista == null) {
            throw new CatalogoException(ErroresEnum.ERROR_AL_BUSCAR_RESULTADO);
        }

    }

}
