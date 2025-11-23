/*package com.sisecofi.proveedores.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sisecofi.proveedores.dto.DictamenTecnicoDto;
import com.sisecofi.libreria.comunes.model.proveedores.CatResultadoDictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.proveedores.CatTituloServicioModel;
import com.sisecofi.libreria.comunes.model.proveedores.DictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.proveedores.repository.CatResultadoDictamenTecnicoRepository;
import com.sisecofi.proveedores.repository.CatTituloServicioRepository;
import com.sisecofi.proveedores.repository.DictamenTecnicoRepository;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.service.PistaService;

@ExtendWith(MockitoExtension.class)
class DictamenTecnicoServiceImplTest {
    @Mock
    private DictamenTecnicoRepository dictamenTecnicoRepository;

    @Mock
    private CatTituloServicioRepository tituloServicioProveedorServiceRepository;

    @Mock
    private CatResultadoDictamenTecnicoRepository catResultadoDictamenTecnicoRepository;

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private PistaService pistaService;

    @InjectMocks
    private DictamenTecnicoServiceImpl dictamenTecnicoService;

    @Test
    void testCrearDictamenTecnico() {
        // Mock de entrada
        DictamenTecnicoDto dictamenTecnicoDto = new DictamenTecnicoDto();
        dictamenTecnicoDto.setAnio("2024");
        dictamenTecnicoDto.setObservacion("Observación de prueba");
        dictamenTecnicoDto.setResponsable("Responsable de prueba");
        dictamenTecnicoDto.setIdServicioTitulo(1L);
        dictamenTecnicoDto.setIdCatResultadoDictamen(2L);
        dictamenTecnicoDto.setIdProveedor(3L);

        // Mockear repositorios
        when(tituloServicioProveedorServiceRepository.findByIdServicioTitulo(anyLong()))
                .thenReturn(Optional.of(new CatTituloServicioModel()));

        when(catResultadoDictamenTecnicoRepository.findByidCatResultadoDictamen(anyLong()))
                .thenReturn(Optional.of(new CatResultadoDictamenTecnicoModel()));

        when(proveedorRepository.findByIdProveedor(anyLong()))
                .thenReturn(Optional.of(new ProveedorModel()));

        // Mockear el método save del repositorio
        when(dictamenTecnicoRepository.save(any(DictamenTecnicoModel.class)))
                .thenReturn(new DictamenTecnicoModel());

        // Llamar al método bajo prueba
        dictamenTecnicoService.crearDictamenTecnico(dictamenTecnicoDto);

        // Verificar que se llamó al método save del repositorio
        verify(dictamenTecnicoRepository, times(1)).save(any(DictamenTecnicoModel.class));

        // Verificar que se llamó al método guardarPista de pistaService
        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
    }



    @Test
void testActualizarDictamenTecnico() {
    // Mock de entrada
    DictamenTecnicoDto dictamenTecnicoDto = new DictamenTecnicoDto();
    dictamenTecnicoDto.setAnio("2025");
    dictamenTecnicoDto.setObservacion("Observación actualizada");
    dictamenTecnicoDto.setResponsable("Responsable actualizado");
    dictamenTecnicoDto.setIdServicioTitulo(1L);
    dictamenTecnicoDto.setIdCatResultadoDictamen(2L);

    // Mockear repositorios
    DictamenTecnicoModel dictamenTecnicoModel = new DictamenTecnicoModel();
    ProveedorModel proveedorModelMock = new ProveedorModel();
    proveedorModelMock.setIdProveedor(3L); // Ajusta según tu caso de prueba
    dictamenTecnicoModel.setProveedorModel(proveedorModelMock);

    when(dictamenTecnicoRepository.findByIdDictamenTecnicoProveedor(anyLong()))
            .thenReturn(Optional.of(dictamenTecnicoModel));

    when(tituloServicioProveedorServiceRepository.findByIdServicioTitulo(anyLong()))
            .thenReturn(Optional.of(new CatTituloServicioModel()));

    when(catResultadoDictamenTecnicoRepository.findByidCatResultadoDictamen(anyLong()))
            .thenReturn(Optional.of(new CatResultadoDictamenTecnicoModel()));

    // Llamar al método bajo prueba
    dictamenTecnicoService.actaulizarDictamenTecnico(1L, dictamenTecnicoDto);

    // Verificar que se llamó al método save del repositorio
    verify(dictamenTecnicoRepository, times(1)).save(any(DictamenTecnicoModel.class));

    // Verificar que se llamó al método guardarPista de pistaService
    verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
}

@Test
void testEliminacionLogicaDictamenTecnico() {
   // Mock de entrada
   DictamenTecnicoModel dictamenTecnicoModel = new DictamenTecnicoModel();
    
   // Mock de TituloServicioProveedorModel
   CatTituloServicioModel tituloServicioProveedorModelMock = new CatTituloServicioModel();
   tituloServicioProveedorModelMock.setNombreTituloServicio("titulo"); // Ajusta según tu caso de prueba
   //dictamenTecnicoModel.setTituloServicioProveedorModel(tituloServicioProveedorModelMock);
   dictamenTecnicoModel.setCatTituloServicioModel(tituloServicioProveedorModelMock);

   // Mock de ProveedorModel
   ProveedorModel proveedorModelMock = new ProveedorModel();
   proveedorModelMock.setIdProveedor(3L); // Ajusta según tu caso de prueba
   dictamenTecnicoModel.setProveedorModel(proveedorModelMock);

   // Mock de CatResultadoDictamenTecnicoModel
   CatResultadoDictamenTecnicoModel catResultadoDictamenTecnicoModelMock = new CatResultadoDictamenTecnicoModel();
   catResultadoDictamenTecnicoModelMock.setResultado("Aprobado"); // Ajusta según tu caso de prueba
   dictamenTecnicoModel.setCatResultadoDictamenTecnicoModel(catResultadoDictamenTecnicoModelMock);

   when(dictamenTecnicoRepository.findByIdDictamenTecnicoProveedorAndEstatusEliminacionLogicaDictamenTrue(anyLong()))
           .thenReturn(Optional.of(dictamenTecnicoModel));

   // Llamar al método bajo prueba
   dictamenTecnicoService.eliminacionLogicaDictamenTecnico(1L);

   // Verificar que se llamó al método save del repositorio
   verify(dictamenTecnicoRepository, times(1)).save(any(DictamenTecnicoModel.class));

   // Verificar que se llamó al método guardarPista de pistaService
   verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
}



}*/
