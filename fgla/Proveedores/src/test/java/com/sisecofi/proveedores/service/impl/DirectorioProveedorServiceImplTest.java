/*package com.sisecofi.proveedores.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.sisecofi.proveedores.dto.DirectorioProveedorDto;
import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;

import com.sisecofi.proveedores.repository.DirectorioProveedorRepository;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.service.PistaService;

@ExtendWith(MockitoExtension.class)
 class DirectorioProveedorServiceImplTest {

    @Mock
    private DirectorioProveedorRepository directorioProveedorRepository;

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private PistaService pistaService;

    private ModelMapper modelMapper;

    @InjectMocks
    private DirectorioProveedorServiceImpl directorioProveedorService;

    private DirectorioProveedorDto directorioProveedorDto;
    private DirectorioProveedorModel directorioProveedorModel;

    // private ProveedorModel proveedorModel;

    @BeforeEach
    void setUp() {

        modelMapper = new ModelMapper();
        directorioProveedorService = new DirectorioProveedorServiceImpl(pistaService, directorioProveedorRepository,
                proveedorRepository, modelMapper);

        directorioProveedorDto = new DirectorioProveedorDto();
        directorioProveedorDto.setIdProveedor(1L);

        directorioProveedorModel = new DirectorioProveedorModel();
        ProveedorModel proveedorModel = new ProveedorModel();
        proveedorModel.setIdProveedor(1L);
        directorioProveedorModel.setProveedorModel(proveedorModel);

    }

    @Test
    void testCrearDirectorioProveedor() {
        when(proveedorRepository.existsById(anyLong())).thenReturn(true);
        when(directorioProveedorRepository.save(any(DirectorioProveedorModel.class)))
                .thenReturn(directorioProveedorModel);

        DirectorioProveedorModel result = directorioProveedorService.crearDirectorioProveedor(directorioProveedorDto);

        verify(directorioProveedorRepository, times(1)).save(any(DirectorioProveedorModel.class));
        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
        assertNotNull(result);
    }

    @Test
    void testActualizarDirectorioContacto() {
        when(directorioProveedorRepository.findByidDirectorioContacto(anyLong()))
                .thenReturn(Optional.of(directorioProveedorModel));

        when(directorioProveedorRepository.save(any(DirectorioProveedorModel.class)))
                .thenReturn(directorioProveedorModel);

        DirectorioProveedorModel result = directorioProveedorService.actualizaDirectorioContacto(1L,
                directorioProveedorDto);

        verify(directorioProveedorRepository, times(1)).save(any(DirectorioProveedorModel.class));
        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
        assertNotNull(result);
    }

    @Test
    void testEliminarContactoDirectorio() {
        when(directorioProveedorRepository.findByIdDirectorioContactoAndEstatusTrue(anyLong()))
                .thenReturn(Optional.of(directorioProveedorModel));

        directorioProveedorService.eliminarContactoDirectorio(1L);

        verify(directorioProveedorRepository, times(1)).save(any(DirectorioProveedorModel.class));
        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
    }

}*/
