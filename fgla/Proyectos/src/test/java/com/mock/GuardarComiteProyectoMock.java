package com.mock;

import com.sisecofi.proyectos.dto.ComiteProyectoDto;
import com.sisecofi.proyectos.dto.InformacionComiteDto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.proyectos.service.impl.ServicioComiteImpl;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;


public class GuardarComiteProyectoMock {
    public static void guardarComiteProyectoMock(ServicioComiteImpl servicioComiteImpl) {

        BigDecimal montoAuth = new BigDecimal(3);
        BigDecimal monto = new BigDecimal(4);
        BigDecimal tipoCambio = new BigDecimal(6);

        ComiteProyectoDto comiteProyectoDto = new ComiteProyectoDto();
        comiteProyectoDto.setIdComiteProyecto(2);
        comiteProyectoDto.setIdTipoMoneda(3);
        comiteProyectoDto.setIdContratoConvenio(4);


        comiteProyectoDto.setFechaSesion(LocalDateTime.now());
        comiteProyectoDto.setAcuerdo("acuerdo");
        comiteProyectoDto.setVigencia("vigencia");
        comiteProyectoDto.setMontoAutorizado(montoAuth);
        comiteProyectoDto.setTipoCambio(tipoCambio);
        comiteProyectoDto.setMonto(monto);
        comiteProyectoDto.setComentarios("comentarios");

        comiteProyectoDto.setIdComite(2);

        ComiteProyectoModel comiteProyectoModel = new ComiteProyectoModel();
        comiteProyectoModel.setIdComiteProyecto(2);
        comiteProyectoModel.setIdTipoMoneda(3);
        comiteProyectoModel.setIdContratoConvenio(4);
        comiteProyectoModel.setFechaSesion(LocalDateTime.now());
        comiteProyectoModel.setAcuerdo("acuerdo");
        comiteProyectoModel.setVigencia("vigencia");
        comiteProyectoModel.setMontoAutorizado(montoAuth);
        comiteProyectoModel.setTipoCambio(tipoCambio);
        comiteProyectoModel.setMonto(monto);
        comiteProyectoModel.setComentarios("comentarios");
        comiteProyectoModel.setIdComite(2);

        InformacionComiteDto comiteProyectoModelDto = new InformacionComiteDto();
        comiteProyectoModel.setIdComiteProyecto(2);
        comiteProyectoModel.setIdTipoMoneda(3);
        comiteProyectoModel.setIdContratoConvenio(4);


        comiteProyectoModel.setFechaSesion(LocalDateTime.now());
        comiteProyectoModel.setAcuerdo("acuerdo");
        comiteProyectoModel.setVigencia("vigencia");
        comiteProyectoModel.setMontoAutorizado(montoAuth);
        comiteProyectoModel.setTipoCambio(tipoCambio);
        comiteProyectoModel.setMonto(monto);
        comiteProyectoModel.setComentarios("comentarios");

        comiteProyectoModel.setIdComite(2);

        Mockito.when(servicioComiteImpl.guardarComiteProyecto(any(ComiteProyectoDto.class))).thenReturn(comiteProyectoModel);
        Mockito.when(servicioComiteImpl.actualizarComiteProyecto(any(InformacionComiteDto.class))).thenReturn(comiteProyectoModelDto);
    }
}
