package com.sisecofi.proveedores.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.proveedores.dto.DirectorioProveedorDto;
import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.proveedores.repository.DirectorioProveedorRepository;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.service.DirectorioProveedorService;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.util.enums.ErroresEnum;
import com.sisecofi.proveedores.util.exception.CatalogoException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectorioProveedorServiceImpl implements DirectorioProveedorService {

    private final PistaService pistaService;
    private final DirectorioProveedorRepository directorioProveedorRepository;
    private final ProveedorRepository proveedorRepository;
    private final ModelMapper modelMapper;
    private static final String PISTA_GEN = "Pista generada: {}";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String ID_PROVEEDOR = "Id Proveedor: ";
    private static final String ID_CONTACTO = "Id del contacto: ";
    private static final String NOMBRE_CONTACTO = "Nombre del contacto: ";
    private static final String TELEFONO_OFICINA = "Teléfono de oficina: ";
    private static final String TELEFONO_CELULAR = "Teléfono celular: ";
    private static final String CORREO_ELECTRONICO = "Correo electrónico: ";
    private static final String REPRESENTANTE_LEGAL = "Representante legal: ";
    private static final String COMENTARIOS = "Comentarios: ";


    @Transactional
    @Override
    public DirectorioProveedorModel crearDirectorioProveedor(DirectorioProveedorDto directorioDto) {

        validarProveedor(directorioDto.getIdProveedor());
        validarCorreoFormato(directorioDto.getCorreoElectronico());

        DirectorioProveedorModel directorioModel = modelMapper.map(directorioDto, DirectorioProveedorModel.class);
        directorioModel.setEstatus(true);

        ProveedorModel proveedorModel = new ProveedorModel();
        proveedorModel.setIdProveedor(directorioDto.getIdProveedor());
        directorioModel.setProveedorModel(proveedorModel);

        //orden vaor de orden directorio
        Integer maxOrdenDirectorio = directorioProveedorRepository.findMaxOrdenDirectorioByIdProveedor(directorioDto.getIdProveedor());
        directorioModel.setOrdenDirectorio(maxOrdenDirectorio + 1);



        try {
            log.info("Crear Directorio del proveedor {}", directorioModel);

            directorioProveedorRepository.save(directorioModel);
        } catch (CatalogoException e) {
            throw new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR_DIRECTORIO);
        }

        // pistas auditoria -Insertar
        StringBuilder builder = new StringBuilder();

        builder.append(ID_PROVEEDOR).append(proveedorModel.getIdProveedor()).append(" | ")
                .append(ID_CONTACTO).append(directorioModel.getIdDirectorioContacto()).append(" | ")
                .append(NOMBRE_CONTACTO).append(directorioModel.getNombreContacto()).append(" | ")
                .append(TELEFONO_OFICINA).append(directorioModel.getTelefonoOficina()).append(" | ")
                .append(TELEFONO_CELULAR).append(directorioModel.getTelefonoCelular()).append(" | ")
                .append(CORREO_ELECTRONICO).append(directorioModel.getCorreoElectronico()).append(" | ")
                .append(REPRESENTANTE_LEGAL).append(directorioModel.getRepresentanteLegal()).append(" | ")
                .append(COMENTARIOS).append(directorioModel.getComentarios());

        log.info(PISTA_GEN, builder.toString());



        // pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


        // TipoSeccionPista.PROVEEDOR_DIRECTORIO_CONTACTO.getIdSeccionPista(), builder.toString(),


        // Optional.empty());

        return directorioModel;
    }

    @Override
    public List<DirectorioProveedorDto> obtenerDirectorioProveedor() {
        List<DirectorioProveedorModel> directorioProveedorModel = directorioProveedorRepository.findByEstatusTrue();



        // pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


        // TipoSeccionPista.PROVEEDOR_DIRECTORIO_CONTACTO.getIdSeccionPista(),


        // TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());

        return directorioProveedorModel.stream()
                .map(model -> modelMapper.map(model, DirectorioProveedorDto.class))
                .toList();

    }

    @Override
    public DirectorioProveedorDto obtenerDirectorioProveedorPorId(Long id) {

        DirectorioProveedorModel directorioProveedorModel = directorioProveedorRepository
                .findByIdDirectorioContactoAndEstatusTrue(id)
                .orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_MSJ_SIN_DIRECTORIO));

        registrarPistaProveedor(id);

        return modelMapper.map(directorioProveedorModel, DirectorioProveedorDto.class);

    }

    // reporte directorio contacto
    public List<DirectorioProveedorDto> obtenerDirectorioPorProveedor(Long idProveedor) {
        List<DirectorioProveedorModel> directorioProveedorModel = directorioProveedorRepository.findByEstatusTrueAndIdProveedor(idProveedor);

        registrarPistaProveedor(idProveedor);

        return directorioProveedorModel.stream()
        	    .map(model -> modelMapper.map(model, DirectorioProveedorDto.class))
        	    .toList();


    }

    @Transactional
    @Override
    public DirectorioProveedorModel actualizaDirectorioContacto(Long id,
            DirectorioProveedorDto directorioProveedorDto) {
        DirectorioProveedorModel directorioExistente = directorioProveedorRepository.findByidDirectorioContacto(id)
                .orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_MSJ_SIN_DIRECTORIO));

        modelMapper.getConfiguration().setSkipNullEnabled(true).setFieldMatchingEnabled(true);

        modelMapper.map(directorioProveedorDto, directorioExistente);

        directorioExistente.setEstatus(true);
        try {
            directorioProveedorRepository.save(directorioExistente);
            log.info("Actualizar Directorio Contacto: {} ", directorioProveedorDto);

        } catch (CatalogoException e) {
            throw new CatalogoException(ErroresEnum.ERROR_ACTUALIZAR_DIRECTORIO);
        }

        // pistas auditoria - Actualizar
        StringBuilder builder = new StringBuilder();

        builder.append(ID_PROVEEDOR).append(directorioExistente.getProveedorModel().getIdProveedor()).append(" | ")
                .append(ID_CONTACTO).append(directorioExistente.getIdDirectorioContacto()).append(" | ")
                .append(NOMBRE_CONTACTO).append(directorioExistente.getNombreContacto()).append(" | ")
                .append(TELEFONO_OFICINA).append(directorioExistente.getTelefonoOficina()).append(" | ")
                .append(TELEFONO_OFICINA).append(directorioExistente.getTelefonoCelular()).append(" | ")
                .append(CORREO_ELECTRONICO).append(directorioExistente.getCorreoElectronico()).append(" | ")
                .append(REPRESENTANTE_LEGAL).append(directorioExistente.getRepresentanteLegal()).append(" | ")
                .append(COMENTARIOS).append(directorioExistente.getComentarios());

        log.info(PISTA_GEN, builder.toString());



        // pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),


        // TipoSeccionPista.PROVEEDOR_DIRECTORIO_CONTACTO.getIdSeccionPista(), builder.toString(),


        // Optional.empty());

        return directorioExistente;

    }

    @Transactional
    @Override
    public void eliminarContactoDirectorio(Long id) {
        DirectorioProveedorModel directorioProveedorModel = directorioProveedorRepository
                .findByIdDirectorioContactoAndEstatusTrue(id)
                .orElseThrow(() -> new CatalogoException(ErroresEnum.ERROR_MSJ_SIN_DIRECTORIO));

        directorioProveedorModel.setEstatus(false);
        directorioProveedorModel.setFechaModificacion(LocalDateTime.now());
        directorioProveedorRepository.save(directorioProveedorModel);


        Long idProveedor = directorioProveedorModel.getProveedorModel().getIdProveedor();

        //renumerar orden directorio
        List<DirectorioProveedorModel> ordenDirectorio = directorioProveedorRepository.findActiveProveedorDirectorioAsc(idProveedor);

        int nuevoOrden =1;
        for(DirectorioProveedorModel directorio : ordenDirectorio ){
            directorio.setOrdenDirectorio(nuevoOrden ++);
            directorioProveedorRepository.save(directorio);
        }


        // pistas auditoria - Actualizar
        StringBuilder builder = new StringBuilder();

        builder.append(ID_PROVEEDOR).append(directorioProveedorModel.getProveedorModel().getIdProveedor())
                .append(" | ")
                .append(ID_CONTACTO).append(directorioProveedorModel.getIdDirectorioContacto()).append(" | ")
                .append(NOMBRE_CONTACTO).append(directorioProveedorModel.getNombreContacto()).append(" | ")
                .append(TELEFONO_OFICINA).append(directorioProveedorModel.getTelefonoOficina()).append(" | ")
                .append(TELEFONO_CELULAR).append(directorioProveedorModel.getTelefonoCelular()).append(" | ")
                .append(CORREO_ELECTRONICO).append(directorioProveedorModel.getCorreoElectronico()).append(" | ")
                .append(REPRESENTANTE_LEGAL).append(directorioProveedorModel.getRepresentanteLegal()).append(" | ")
                .append(COMENTARIOS).append(directorioProveedorModel.getComentarios());

        log.info(PISTA_GEN, builder.toString());



        // pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(), TipoMovPista.BORRA_REGISTRO.getId(),


        // TipoSeccionPista.PROVEEDOR_DIRECTORIO_CONTACTO.getIdSeccionPista(), builder.toString(),


        // Optional.empty());

    }

    private void validarProveedor(Long idProveedor) {
        if (idProveedor == null || !proveedorRepository.existsById(idProveedor)) {
            throw new CatalogoException(ErroresEnum.ERROR_MSJ_SIN_USUARIO);
        }
    }

    public static void validarCorreoFormato(String correoElectronico) {
        if (correoElectronico == null || correoElectronico.trim().isEmpty()) {
            return; // Permitir guardar el campo vacío
        }
    
        
        if (!EMAIL_PATTERN.matcher(correoElectronico).matches()) {
            throw new CatalogoException(ErroresEnum.ERROR_FORMATO);
        }
    }
    @Transactional
    private void registrarPistaProveedor(Long idProveedor) {
        StringBuilder builder = new StringBuilder();
        builder.append(ID_PROVEEDOR).append(idProveedor);



        // pistaService.guardarPista(ModuloPista.PROVEEDORES.getId(),


        // TipoMovPista.CONSULTA_REGISTRO.getId(),


        // TipoSeccionPista.PROVEEDOR_DIRECTORIO_CONTACTO.getIdSeccionPista(),


        // builder.toString(),


        // Optional.empty());
    }

}