package com.sisecofi.proyectos.service.impl;
/*
import com.mock.InformacionComiteMock;
import com.sisecofi.proyectos.dto.ResponseComiteInfo;

import com.sisecofi.proyectos.dto.ResponseComiteInfoReporte;
import com.sisecofi.proyectos.util.consumer.ReporteContratoConvenioConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ServicioReporteComiteTest {

    @Mock
    private ServicioComiteImpl servicioComiteImpl;

    @Mock
    private ReporteContratoConvenioConsumer reporteContratoConvenioConsumer;

    @InjectMocks
    private ServicioReporteComiteImpl servicioReporteComiteImpl;

    @Test
    void obtenerReporteContratoConvenio() {
        InformacionComiteMock.informacionComiteMock(servicioComiteImpl);
        byte[] bytes = {};
        String base64 = "";
        List<ResponseComiteInfoReporte> responseComiteInfoList = servicioComiteImpl.obtenerComitesReporteInfo(1);

        Assertions.assertNotNull(responseComiteInfoList);
        Boolean valor = responseComiteInfoList.isEmpty();
        Assertions.assertNotEquals(true , valor);

        Mockito.when(reporteContratoConvenioConsumer.cerrarBytes()).thenReturn(bytes);

        byte[] archivo = reporteContratoConvenioConsumer.cerrarBytes();
        Assertions.assertEquals(bytes.getClass(), archivo.getClass());
    }
}*/