/*package com.sisecofi.proveedores.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sisecofi.proveedores.dto.TituloServicioProveedorDto;
import com.sisecofi.proveedores.dto.TituloServicioResponseDto;
import com.sisecofi.libreria.comunes.model.proveedores.CatEstatusTitulosServicioModel;
import com.sisecofi.libreria.comunes.model.proveedores.CatTituloServicioModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.TituloServicioProveedorModel;
import com.sisecofi.proveedores.repository.CatEstatusTituloServicioRepository;
import com.sisecofi.proveedores.repository.CatTituloServicioRepository;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.repository.TituloServicioProveedorServiceRepository;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.util.consumer.VigenciaScheduler;

@ExtendWith(MockitoExtension.class)
 class TituloServicioProveedorServiceImplTest {

    @Mock
    private CatTituloServicioRepository catTituloServicioRepository;

    @Mock
    private CatEstatusTituloServicioRepository catEstatusTituloServicioRepository;

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private TituloServicioProveedorServiceRepository tituloServicioProveedorServiceRepository;

    @Mock
    private VigenciaScheduler vigenciaScheduler;    


    @Mock
    private PistaService pistaService;

    @InjectMocks
    private TituloServicioProveedorServiceImpl tituloServicioProveedorService;

    private TituloServicioProveedorDto tituloServicioProveedorDto;
    private TituloServicioProveedorModel tituloServicioProveedorModel;

    @BeforeEach
    void setUp() {
        tituloServicioProveedorDto = new TituloServicioProveedorDto();
        tituloServicioProveedorDto.setNumeroTitulo("12345");
        tituloServicioProveedorDto.setVencimientoTitulo(Date.valueOf("2024-12-31"));
        tituloServicioProveedorDto.setComentarios("Comentario de prueba");
        tituloServicioProveedorDto.setColorVigencia("Rojo");
        tituloServicioProveedorDto.setIdServicioTitulo(1L);
        tituloServicioProveedorDto.setIdCatEstatusTituloServicio(2L);
        tituloServicioProveedorDto.setIdProveedor(3L);

        tituloServicioProveedorModel = new TituloServicioProveedorModel();
        tituloServicioProveedorModel.setIdTituloServicioProveedor(1L);


    }

    @Test
    void testCrearTituloServicioProveedor() {
        when(catTituloServicioRepository.findByIdServicioTitulo(anyLong()))
                .thenReturn(Optional.of(new CatTituloServicioModel()));

        when(catEstatusTituloServicioRepository.findByIdCatEstatusTituloServicio(anyLong()))
                .thenReturn(Optional.of(new CatEstatusTitulosServicioModel()));

        when(proveedorRepository.findByIdProveedor(anyLong()))
                .thenReturn(Optional.of(new ProveedorModel()));

        when(tituloServicioProveedorServiceRepository.save(any(TituloServicioProveedorModel.class)))
                .thenReturn(tituloServicioProveedorModel);

        TituloServicioResponseDto response = tituloServicioProveedorService
                .crearTituloServicioProveedor(tituloServicioProveedorDto);

        verify(catTituloServicioRepository, times(1)).findByIdServicioTitulo(anyLong());
        verify(catEstatusTituloServicioRepository, times(1)).findByIdCatEstatusTituloServicio(anyLong());
        verify(proveedorRepository, times(1)).findByIdProveedor(anyLong());
        verify(tituloServicioProveedorServiceRepository, times(1)).save(any(TituloServicioProveedorModel.class));
        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());

        assertNotNull(response);
    }

    @Test
    void testActualizarTituloServicioProveedor() {

        TituloServicioProveedorDto tituloServicioProveedorDto = new TituloServicioProveedorDto();
        tituloServicioProveedorDto.setNumeroTitulo("12345");
        tituloServicioProveedorDto.setVencimientoTitulo(Date.valueOf("2024-12-31"));
        tituloServicioProveedorDto.setComentarios("Comentario de prueba");
        tituloServicioProveedorDto.setColorVigencia("Rojo");
        tituloServicioProveedorDto.setIdServicioTitulo(1L);
        tituloServicioProveedorDto.setIdCatEstatusTituloServicio(2L);
        tituloServicioProveedorDto.setIdProveedor(3L);

        when(catTituloServicioRepository.findByIdServicioTitulo(anyLong()))
                .thenReturn(Optional.of(new CatTituloServicioModel()));
        when(catEstatusTituloServicioRepository.findByIdCatEstatusTituloServicio(anyLong()))
                .thenReturn(Optional.of(new CatEstatusTitulosServicioModel()));

        TituloServicioProveedorModel tituloServicioProveedorModelMock = new TituloServicioProveedorModel();
        tituloServicioProveedorModelMock.setIdTituloServicioProveedor(1L);

        ProveedorModel proveedorModelMock = new ProveedorModel();
        proveedorModelMock.setIdProveedor(3L);
        tituloServicioProveedorModelMock.setProveedorModel(proveedorModelMock);

        when(tituloServicioProveedorServiceRepository.findByIdTituloServicioProveedor(anyLong()))
                .thenReturn(Optional.of(tituloServicioProveedorModelMock));
        when(tituloServicioProveedorServiceRepository.save(any(TituloServicioProveedorModel.class)))
                .thenReturn(tituloServicioProveedorModelMock);

        TituloServicioResponseDto response = tituloServicioProveedorService.actualizarTituloServicioProveedor(1L,
                tituloServicioProveedorDto);

        assertNotNull(response);
        assertEquals(tituloServicioProveedorModelMock.getIdTituloServicioProveedor(),
                response.getIdTituloServicioProveedor());

        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());

    }

    @Test
    void testEliminacionLogicaTituloServicioProveedor() {
        when(tituloServicioProveedorServiceRepository
                .findByIdTituloServicioProveedorAndEstatusEliminacionLogicaTituloTrue(anyLong()))
                .thenReturn(Optional.of(tituloServicioProveedorModel));

        ProveedorModel proveedorModelMock = new ProveedorModel();
        proveedorModelMock.setIdProveedor(3L);

        tituloServicioProveedorModel.setProveedorModel(proveedorModelMock);

        CatTituloServicioModel catTituloServicioModelMock = new CatTituloServicioModel();
        catTituloServicioModelMock.setNombreTituloServicio("Nombre de Título de Servicio");
        tituloServicioProveedorModel.setTituloServicioModel(catTituloServicioModelMock);

        CatEstatusTitulosServicioModel catEstatusTitulosServicioModelMock = new CatEstatusTitulosServicioModel();
        catEstatusTitulosServicioModelMock.setSemaforoEstatus("Vigente");
        tituloServicioProveedorModel.setCatEstatusTitulosServicioModel(catEstatusTitulosServicioModelMock);

        tituloServicioProveedorService.eliminacionLogicaTituloServicioProveedor(1L);

        verify(tituloServicioProveedorServiceRepository, times(1))
                .findByIdTituloServicioProveedorAndEstatusEliminacionLogicaTituloTrue(anyLong());
        verify(tituloServicioProveedorServiceRepository, times(1)).save(any(TituloServicioProveedorModel.class));
        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
    }
    
    
   
}*/




