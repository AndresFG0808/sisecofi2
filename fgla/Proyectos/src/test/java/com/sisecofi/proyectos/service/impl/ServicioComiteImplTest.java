package com.sisecofi.proyectos.service.impl;
/*
import com.mock.*;
import com.sisecofi.proyectos.dto.*;
import com.sisecofi.proyectos.model.ComiteProyectoModel;
import com.sisecofi.proyectos.repository.ComiteRepository;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.math.BigDecimal;
import java.util.List;

import static com.util.Constantes.ID_COMITE_PROYECTO;
import static com.util.Constantes.ID_PROYECTO;



@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ServicioComiteImplTest {

    @MockBean
    private ServicioComiteImpl servicioComiteImpl;

    @MockBean
    private ComiteRepository comiteRepository;

    @Test
    void obtenerComiteInfo() {
        InformacionComiteMock.informacionComiteMock(servicioComiteImpl);

        List<ResponseComiteInfo> responseComiteInfoList = servicioComiteImpl.obtenerComiteInformacion(1);

        for (ResponseComiteInfo responseComiteInfo: responseComiteInfoList){
            Assertions.assertNotNull(responseComiteInfo.getInformacionComite().getMonto());
            Assertions.assertNotNull(responseComiteInfo.getComite().getNombre());
        }
    }

    @Test
    void obtenerDetalleComite() {
        DetalleComiteMock.detalleComiteMock(servicioComiteImpl, ID_PROYECTO);
        ResponseComite responseComite = servicioComiteImpl.obtenerDetalleComite(ID_PROYECTO);

        Assertions.assertNotNull(responseComite.getInformacionComiteProyecto());
        Assertions.assertNotNull(responseComite.getComite().getNombre());
        Assertions.assertNotNull(responseComite.getTipoMoneda().getNombre());
        Assertions.assertNotNull(responseComite.getContrato().getNombre());
        Assertions.assertNotNull(responseComite.getContratoConvenio().getNombre());
    }

    @Test
    void guardarComiteProyecto() {
        GuardarComiteProyectoMock.guardarComiteProyectoMock(servicioComiteImpl);
        ComiteProyectoDto mockComiteProyectoModel = Mockito.mock(ComiteProyectoDto.class);

        ComiteProyectoModel comiteProyectoModel = servicioComiteImpl.guardarComiteProyecto(mockComiteProyectoModel);

        Assertions.assertNotNull(comiteProyectoModel.getIdComite());
        Assertions.assertNotNull(comiteProyectoModel.getIdComiteProyecto());
        Assertions.assertNotNull(comiteProyectoModel.getIdComiteProyecto());
    }

    @Test
    void actualizarComiteProyecto() {
        GuardarComiteProyectoMock.guardarComiteProyectoMock(servicioComiteImpl);
        ComiteRepositoryMock.guardarComiteProyectoMock(comiteRepository, ID_COMITE_PROYECTO);

        ComiteProyectoModel comiteProyectoModel = comiteRepository.findByIdComiteProyecto(ID_COMITE_PROYECTO)
                .orElseThrow(() -> new ProyectoException(ErroresEnum.COMITE_PROYECTO_NO_ENCONTRADO));

        BigDecimal montoActualizado = new BigDecimal(2222);
        BigDecimal montoAuth = new BigDecimal(3333);

        ComiteProyectoModel informacionComiteDto = new ComiteProyectoModel();
        informacionComiteDto.setAcuerdo("Acruedo actualizado");
        informacionComiteDto.setMonto(montoActualizado);
        informacionComiteDto.setMontoAutorizado(montoAuth);

        ActualizarComiteProyectoMock.actualizarComiteProyectoMock(servicioComiteImpl,informacionComiteDto);

        InformacionComiteDto comiteProyectoModelActualizado = servicioComiteImpl.actualizarComiteProyecto(Mockito.mock(InformacionComiteDto.class));

        Assertions.assertNotEquals(comiteProyectoModelActualizado.getMonto(),comiteProyectoModel.getMonto());
        Assertions.assertNotEquals(comiteProyectoModelActualizado.getMontoAutorizado(),comiteProyectoModel.getMontoAutorizado());
    }

    @Test
    void eliminarInformacionComite() {
        Mockito.when(servicioComiteImpl.eliminarInformacionComite(ID_COMITE_PROYECTO)).thenReturn("false");
        String response = servicioComiteImpl.eliminarInformacionComite(ID_COMITE_PROYECTO);

        Assertions.assertEquals("false", response);
    }
}*/