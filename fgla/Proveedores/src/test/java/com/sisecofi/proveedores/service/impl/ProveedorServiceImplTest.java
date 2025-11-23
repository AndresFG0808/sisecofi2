/*package com.sisecofi.proveedores.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.sisecofi.proveedores.dto.CatalogoProveedorDto;
import com.sisecofi.proveedores.dto.ConsultaProveedorDto;
import com.sisecofi.proveedores.dto.ProveedorDto;
import com.sisecofi.libreria.comunes.model.proveedores.CatTituloServicioModel;
import com.sisecofi.libreria.comunes.model.proveedores.CatResultadoDictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.proveedores.CatEstatusTitulosServicioModel;
import com.sisecofi.libreria.comunes.model.proveedores.DictamenTecnicoModel;
import com.sisecofi.libreria.comunes.model.proveedores.DirectorioProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.GiroEmpresarialModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.proveedores.TituloServicioProveedorModel;
import com.sisecofi.proveedores.repository.GiroEmpresarialRepository;
import com.sisecofi.proveedores.repository.ProveedorRepository;
import com.sisecofi.proveedores.service.PistaService;
import com.sisecofi.proveedores.util.enums.ErroresEnum;
import com.sisecofi.proveedores.util.exception.CatalogoException;

@ExtendWith(MockitoExtension.class)
 class ProveedorServiceImplTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private GiroEmpresarialRepository giroEmpresarialRepository;

    @Mock
    private PistaService pistaService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProveedorServiceImpl proveedorServiceImpl;

    private ProveedorModel proveedorModel;
    private ProveedorDto proveedorDto;
    private ConsultaProveedorDto consultaProveedorDto;

    private ProveedorModel proveedorModel1;
    private ProveedorModel proveedorModel2;
    private CatalogoProveedorDto catalogoProveedorDto1;
    private CatalogoProveedorDto catalogoProveedorDto2;

    @BeforeEach
    public void setUp() {
        
        proveedorDto = new ProveedorDto();
        proveedorDto.setNombreProveedor("Proveedor Test 1");
        proveedorDto.setNombreComercial("nombre del comercio");
        proveedorDto.setRfc("TOMA960512RT1");
        proveedorDto.setDireccion("direccion del proveedor");
        proveedorDto.setComentarios("comentario proveedor");
        proveedorDto.setEstatus(true);
        proveedorDto.setIdAgs("9101");
        proveedorDto.setIdGiroEmpresarial(1L);

        proveedorModel = new ProveedorModel();
        proveedorModel.setNombreProveedor("Proveedor Test 1");
        proveedorModel.setNombreComercial("nombre del comercio");
        proveedorModel.setRfc("TOMA960512RT1");
        proveedorModel.setDireccion("direccion del proveedor");
        proveedorModel.setComentarios("comentario proveedor");
        proveedorModel.setEstatus(true);
        proveedorModel.setIdAgs("9101");
        GiroEmpresarialModel giroEmpresarialModel = new GiroEmpresarialModel();
        giroEmpresarialModel.setIdGiroEmpresarial(1L);
        proveedorModel.setGiroEmpresarialModel(giroEmpresarialModel);

        consultaProveedorDto = new ConsultaProveedorDto();
        consultaProveedorDto.setIdProveedor(proveedorModel.getIdProveedor());
        consultaProveedorDto.setNombreProveedor(proveedorModel.getNombreProveedor());
        consultaProveedorDto.setNombreComercial(proveedorModel.getNombreComercial());
        consultaProveedorDto.setRfc(proveedorModel.getRfc());
        consultaProveedorDto.setDireccion(proveedorModel.getDireccion());
        consultaProveedorDto.setComentarios(proveedorModel.getComentarios());
        consultaProveedorDto.setEstatus(proveedorModel.getEstatus());
        consultaProveedorDto.setIdAgs(proveedorModel.getIdAgs());
        consultaProveedorDto.setGiroEmpresarialModel(proveedorModel.getGiroEmpresarialModel());

        Mockito.lenient().when(modelMapper.map(proveedorModel, ConsultaProveedorDto.class))
                .thenReturn(consultaProveedorDto);

       
        Mockito.lenient().when(modelMapper.map(proveedorDto, ProveedorModel.class)).thenReturn(proveedorModel);
        Mockito.lenient().when(modelMapper.map(proveedorModel, ProveedorDto.class)).thenReturn(proveedorDto);

       
        Mockito.lenient().when(proveedorRepository.findByIdProveedor(anyLong()))
                .thenReturn(Optional.of(proveedorModel));
        Mockito.lenient().when(giroEmpresarialRepository.existsById(anyLong())).thenReturn(true);

        proveedorModel1 = new ProveedorModel();
        proveedorModel1.setIdProveedor(1L);
        proveedorModel1.setNombreProveedor("Proveedor Activo 1");
        proveedorModel1.setEstatus(true);

        proveedorModel2 = new ProveedorModel();
        proveedorModel2.setIdProveedor(2L);
        proveedorModel2.setNombreProveedor("Proveedor Activo 2");
        proveedorModel2.setEstatus(true);

        catalogoProveedorDto1 = new CatalogoProveedorDto();
        catalogoProveedorDto1.setIdProveedor(1L);
        catalogoProveedorDto1.setNombreProveedor("Proveedor Activo 1");

        catalogoProveedorDto2 = new CatalogoProveedorDto();
        catalogoProveedorDto2.setIdProveedor(2L);
        catalogoProveedorDto2.setNombreProveedor("Proveedor Activo 2");

        Mockito.lenient().when(modelMapper.map(proveedorModel1, CatalogoProveedorDto.class))
                .thenReturn(catalogoProveedorDto1);
        Mockito.lenient().when(modelMapper.map(proveedorModel2, CatalogoProveedorDto.class))
                .thenReturn(catalogoProveedorDto2);

        List<DirectorioProveedorModel> directorioProveedorModels = new ArrayList<>();
        DirectorioProveedorModel directorio = new DirectorioProveedorModel();
        directorio.setIdDirectorioContacto(1L);
        directorio.setNombreContacto("Nombre Contacto");
        directorio.setTelefonoOficina("5517705998");
        directorio.setTelefonoCelular("5517705978");
        directorio.setCorreoElectronico("adrian1@gmail.com");
        directorio.setRepresentanteLegal(true);
        directorio.setComentarios("comentario directorio");
        directorioProveedorModels.add(directorio);
        proveedorModel.setDirectorio(directorioProveedorModels);

        List<TituloServicioProveedorModel> tituloServicioProveedorModels = new ArrayList<>();
        TituloServicioProveedorModel tituloServicio = new TituloServicioProveedorModel();
        tituloServicio.setIdTituloServicioProveedor(1L);
        tituloServicio.setNumeroTitulo("NUM-1234");

        CatTituloServicioModel catTituloServicioModel = new CatTituloServicioModel();
        catTituloServicioModel.setIdServicioTitulo(1L);
        tituloServicio.setTituloServicioModel(catTituloServicioModel);

        CatEstatusTitulosServicioModel catEstatusTitulosServicioModel = new CatEstatusTitulosServicioModel();
        catEstatusTitulosServicioModel.setIdCatEstatusTituloServicio(1L);
        tituloServicio.setCatEstatusTitulosServicioModel(catEstatusTitulosServicioModel);

        LocalDate fechaVencimiento = LocalDate.of(2024, 12, 1);
        Date fecha = Date.valueOf(fechaVencimiento);
        tituloServicio.setVencimientoTitulo(fecha);

        tituloServicio.setVigencia("1m");
        tituloServicio.setComentarios("comentario titulos servicio");
        tituloServicioProveedorModels.add(tituloServicio);
        proveedorModel.setTituloServicioProveedores(tituloServicioProveedorModels);


        List<DictamenTecnicoModel> dictamenTecnicoModels = new ArrayList<>();
        DictamenTecnicoModel dictamen = new DictamenTecnicoModel();
        dictamen.setIdDictamenTecnicoProveedor(1L);

        tituloServicio.setIdTituloServicioProveedor(1L);
        //dictamen.setTituloServicioProveedorModel(tituloServicio);
        dictamen.setCatTituloServicioModel(catTituloServicioModel);

        dictamen.setAnio("2024");
        dictamen.setResponsable("Julio Tolentino");

        CatResultadoDictamenTecnicoModel catResultadoDictamenTecnicoModel = new CatResultadoDictamenTecnicoModel();
        catResultadoDictamenTecnicoModel.setIdCatResultadoDictamen(1L);
        dictamen.setCatResultadoDictamenTecnicoModel(catResultadoDictamenTecnicoModel);
        dictamen.setObservacion("observacion dictamen");
        dictamenTecnicoModels.add(dictamen);
        proveedorModel.setDictamenTecnico(dictamenTecnicoModels);

        Mockito.lenient().when(modelMapper.map(proveedorDto, ProveedorModel.class)).thenReturn(proveedorModel);
        Mockito.lenient().when(proveedorRepository.findByIdProveedor(anyLong()))
                .thenReturn(Optional.of(proveedorModel));
        Mockito.lenient().when(giroEmpresarialRepository.existsById(anyLong())).thenReturn(true);

    }

    @Test
    void testCrearProveedor() {
        when(proveedorRepository.save(any(ProveedorModel.class))).thenReturn(proveedorModel);
        when(giroEmpresarialRepository.existsById(anyLong())).thenReturn(true);

        ProveedorModel resultado = proveedorServiceImpl.crearProveedor(proveedorDto);

        assertNotNull(resultado, "El proveedor no debe ser nulo");
        assertEquals(proveedorDto.getNombreProveedor(), resultado.getNombreProveedor(), "Los nombres no coinciden");
        assertEquals(proveedorDto.getNombreComercial(), resultado.getNombreComercial(),
                "los nombres del comercio no coinciden");
        assertEquals(proveedorDto.getRfc(), resultado.getRfc(), "los id no coinciden");
        assertEquals(proveedorDto.getDireccion(), resultado.getDireccion(), "las direcciones no coinciden");
        assertEquals(proveedorDto.getComentarios(), resultado.getComentarios(), "los comentarios no coinciden");
        assertEquals(proveedorDto.getEstatus(), resultado.getEstatus(), "el estatus no coincide");
        assertEquals(proveedorDto.getIdAgs(), resultado.getIdAgs(), "Los IDs de AGS no coinciden");
        assertEquals(proveedorDto.getIdGiroEmpresarial(), resultado.getGiroEmpresarialModel().getIdGiroEmpresarial(),
                "Los IDs de giro empresarial no coinciden");

        verify(proveedorRepository, times(1)).save(any(ProveedorModel.class));
        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
    }

    @Test
    void testCrearProveedorExcepciones() {
        when(proveedorRepository.save(any(ProveedorModel.class)))
                .thenThrow(new CatalogoException(ErroresEnum.ERROR_AL_GUARDAR));
        when(giroEmpresarialRepository.existsById(anyLong())).thenReturn(true);

        CatalogoException exception = assertThrows(CatalogoException.class, () -> {
            proveedorServiceImpl.crearProveedor(proveedorDto);
        });

        assertNotNull(exception, "La excepción no debe ser nula");

        verify(proveedorRepository, times(1)).save(any(ProveedorModel.class));

        verify(pistaService, never()).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
    }

    @Test
    void testObtenerProveedorPorId() {

        ConsultaProveedorDto proveedorDto = proveedorServiceImpl.obtenerProveedorPorId(1L);
        assertNotNull(proveedorDto, "El proveedor consultado no debe ser nulo");
        verify(proveedorRepository, times(1)).findByIdProveedor(anyLong());
        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());

    }

    @Test
    void testObtenerProveedorPorIdNotFound() {

        when(proveedorRepository.findByIdProveedor(anyLong())).thenReturn(Optional.empty());

        CatalogoException exception = assertThrows(CatalogoException.class, () -> {
            proveedorServiceImpl.obtenerProveedorPorId(1L);
        });

        assertNotNull(exception, "La excepción no debe ser nula");

        verify(proveedorRepository, times(1)).findByIdProveedor(anyLong());

        verify(pistaService, never()).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
    }

    @Test
    void testActualizarProveedor() {

        ProveedorDto proveedorActualizado = proveedorServiceImpl.actualizarProveedor(1L, proveedorDto);

        // Verificaciones
        assertNotNull(proveedorActualizado, "El proveedor actualizado no debe ser nulo");
        assertEquals(proveedorDto.getNombreProveedor(), proveedorActualizado.getNombreProveedor(),
                "El nombre del proveedor no coincide");
        assertEquals(proveedorDto.getRfc(), proveedorActualizado.getRfc(), "El RFC del proveedor no coincide");
        assertEquals(proveedorDto.getIdAgs(), proveedorActualizado.getIdAgs(), "El ID AGS del proveedor no coincide");
        assertEquals(proveedorDto.getIdGiroEmpresarial(), proveedorActualizado.getIdGiroEmpresarial(),
                "El ID de giro empresarial del proveedor no coincide");
        verify(proveedorRepository, times(1)).findByIdProveedor(1L);
        verify(proveedorRepository, times(1)).save(proveedorModel);
        verify(pistaService, times(1)).guardarPista(anyInt(), anyInt(), anyInt(), anyString());
    }

    @Test
    void testListarProveedoresActivos() {
        List<ProveedorModel> proveedoresActivos = Arrays.asList(proveedorModel1, proveedorModel2);

        Mockito.when(proveedorRepository.findByEstatusOrderByNombreProveedorAsc(true)).thenReturn(proveedoresActivos);

        List<CatalogoProveedorDto> resultado = proveedorServiceImpl.listarProveedoresActivos();

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(2, resultado.size(), "El tamaño de la lista no coincide");
        assertEquals(catalogoProveedorDto1, resultado.get(0), "El primer proveedor no coincide");
        assertEquals(catalogoProveedorDto2, resultado.get(1), "El segundo proveedor no coincide");

        verify(proveedorRepository, times(1)).findByEstatusOrderByNombreProveedorAsc(true);
        verify(modelMapper, times(1)).map(proveedorModel1, CatalogoProveedorDto.class);
        verify(modelMapper, times(1)).map(proveedorModel2, CatalogoProveedorDto.class);
    }

}*/
