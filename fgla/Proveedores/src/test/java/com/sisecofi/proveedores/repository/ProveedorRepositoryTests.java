/*package com.sisecofi.proveedores.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;

@ExtendWith(MockitoExtension.class)
class ProveedorRepositoryTests {

    @Mock
    private ProveedorRepository proveedorRepository;

    private ProveedorModel proveedorModel;
    private ProveedorModel proveedorModel2;

    @BeforeEach
    public void setUp() {
        proveedorModel = new ProveedorModel();
        proveedorModel.setIdProveedor(1L);
        proveedorModel.setNombreProveedor("adrian");
        proveedorModel.setNombreComercial("Nombre del comercio");
        proveedorModel.setRfc("TOMA960512RT5");
        proveedorModel.setDireccion("");
        proveedorModel.setComentarios("");
        proveedorModel.setEstatus(true);
        proveedorModel.setIdAgs("1234");

        proveedorModel2 = new ProveedorModel();
        proveedorModel2.setIdProveedor(1L);
        proveedorModel2.setNombreProveedor("adrian 2");
        proveedorModel2.setNombreComercial("Nombre del comercio 2");
        proveedorModel2.setRfc("TOMA960512RT6");
        proveedorModel2.setDireccion("");
        proveedorModel2.setComentarios("");
        proveedorModel2.setEstatus(false);
        proveedorModel2.setIdAgs("5678");

    }

    @Test
    void testFindByIdProveedor() {
        when(proveedorRepository.findByIdProveedor(anyLong())).thenReturn(Optional.of(proveedorModel));

        Optional<ProveedorModel> found = proveedorRepository.findByIdProveedor(1L);
        assertTrue(found.isPresent(), "El proveedor no fue encontrado");
        assertEquals(proveedorModel.getIdProveedor(), found.get().getIdProveedor(), "Los IDs no coinciden");
    }

    @Test
    void testFindByNombreProveedor() {
        when(proveedorRepository.findByNombreProveedor(anyString())).thenReturn(Optional.of(proveedorModel));

        Optional<ProveedorModel> found = proveedorRepository.findByNombreProveedor("adrian");

        assertTrue(found.isPresent(), "El proveedor no fue encontrado");
        assertEquals(proveedorModel.getNombreProveedor(), found.get().getNombreProveedor(), "Los nombres no coinciden");
    }

    @Test
    void testFindByRfc() {
        when(proveedorRepository.findByRfc(anyString())).thenReturn(Optional.of(proveedorModel));

        Optional<ProveedorModel> found = proveedorRepository.findByRfc("TOMA960512RT5");

        assertTrue(found.isPresent(), "el rfc no fue encontrado");
        assertEquals(proveedorModel.getRfc(), found.get().getRfc(), "el rfc no coincide");
    }

    @Test
    void testFindByIdAgs() {
        when(proveedorRepository.findByIdAgs(anyString())).thenReturn(Optional.of(proveedorModel));

        Optional<ProveedorModel> found = proveedorRepository.findByIdAgs("1234");
        assertTrue(found.isPresent(), "El Id Ags no fue encontrado");
        assertEquals(proveedorModel.getIdAgs(), found.get().getIdAgs(), "Los IDs no coinciden");
    }

    @Test
    void testFindAllOrderByIdProveedor() {
        List<ProveedorModel> proveedores = Arrays.asList(proveedorModel);

        Page<ProveedorModel> page = new PageImpl<>(proveedores);

        when(proveedorRepository.findAllOrderedByIdProveedor(any(Pageable.class))).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 15);

        Page<ProveedorModel> resultado = proveedorRepository.findAllOrderedByIdProveedor(pageable);

        assertFalse(resultado.isEmpty(), "La pagina esta vacia");

        assertEquals(1, resultado.getTotalElements(), "el numero de elementos no coinciden");

        assertEquals(proveedorModel.getIdProveedor(), resultado.getContent().get(0).getIdProveedor(),
                "Los id no coinciden");

    }

    @Test
    void testObtenerIdsDirectoriosProveedor() {
        List<Long> directorioIds = Arrays.asList(1L, 2L, 3L);

        when(proveedorRepository.obtenerIdsDirectoriosProveedor(anyLong())).thenReturn(directorioIds);

        List<Long> resultado = proveedorRepository.obtenerIdsDirectoriosProveedor(1L);

        assertFalse(resultado.isEmpty(), "La lista de id está vacía");

        assertEquals(3, resultado.size(), "El tamaño de la lista no coincide");

        assertEquals(directorioIds, resultado, "Los id no coinciden");
    }

    @Test
    void testFindAllByOrderByIdProveedorAsc(){
        List<ProveedorModel> proveedores = Arrays.asList(proveedorModel, proveedorModel2);

        when(proveedorRepository.findAllByOrderByIdProveedorAsc()).thenReturn(proveedores);

        List<ProveedorModel> resultado = proveedorRepository.findAllByOrderByIdProveedorAsc();

        assertEquals(2, resultado.size(), "El número total de elementos no coincide");
        assertEquals(proveedorModel.getIdProveedor(), resultado.get(0).getIdProveedor(), "El primer ID no coincide");
        assertEquals(proveedorModel2.getIdProveedor(), resultado.get(1).getIdProveedor(), "El segundo ID no coincide");



    }

}*/
