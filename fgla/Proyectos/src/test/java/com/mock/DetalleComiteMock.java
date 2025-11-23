package com.mock;

import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.proyectos.dto.ResponseComite;
import com.sisecofi.proyectos.service.impl.ServicioComiteImpl;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DetalleComiteMock {
    public static void detalleComiteMock(ServicioComiteImpl servicioComiteImpl, Integer idProyecto){

        BigDecimal montoAuth = new BigDecimal(3);
        BigDecimal monto = new BigDecimal(4);

        ComiteProyectoModel informacionComite = new ComiteProyectoModel();
        informacionComite.setAcuerdo("acuerdo");
        informacionComite.setVigencia("vigencia");
        informacionComite.setMonto(monto);
        informacionComite.setMontoAutorizado(montoAuth);
        informacionComite.setEstatus(true);

        BaseCatalogoModel comite = new BaseCatalogoModel();
        comite.setNombre("comite catalogo");
        comite.setFechaCreacion( LocalDateTime.now());
        comite.setFechaModificacion(LocalDateTime.now());
        comite.setEstatus(true);

        BaseCatalogoModel afectacion = new BaseCatalogoModel();
        afectacion.setNombre("comite catalogo");
        afectacion.setFechaCreacion( LocalDateTime.now());
        afectacion.setFechaModificacion(LocalDateTime.now());
        afectacion.setEstatus(true);

        ContratoDto contrato = new ContratoDto();


        BaseCatalogoModel tipoMoneda = new BaseCatalogoModel();
        tipoMoneda.setNombre("comite catalogo");
        tipoMoneda.setFechaCreacion( LocalDateTime.now());
        tipoMoneda.setFechaModificacion(LocalDateTime.now());
        tipoMoneda.setEstatus(true);

        BaseCatalogoModel contratoConvenio = new BaseCatalogoModel();
        contratoConvenio.setNombre("comite catalogo");
        contratoConvenio.setFechaCreacion( LocalDateTime.now());
        contratoConvenio.setFechaModificacion(LocalDateTime.now());
        contratoConvenio.setEstatus(true);

        ResponseComite responseComite = new ResponseComite();
        responseComite.setInformacionComiteProyecto(informacionComite);
        responseComite.setComite(comite);
        responseComite.setContrato(contrato);
       // responseComite.setContrato(contrato);
        responseComite.setTipoMoneda(tipoMoneda);
        responseComite.setContratoConvenio(contratoConvenio);

        Mockito.when(servicioComiteImpl.obtenerDetalleComite(idProyecto)).thenReturn(responseComite);
    }
}
