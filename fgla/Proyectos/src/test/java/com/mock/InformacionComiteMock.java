package com.mock;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.proyectos.dto.InformacionComiteDto;
import com.sisecofi.proyectos.dto.ResponseComiteInfoReporte;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ArchivoPlantillaComiteModel;
import com.sisecofi.proyectos.service.impl.ServicioComiteImpl;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InformacionComiteMock {
    public static void informacionComiteMock(ServicioComiteImpl servicioComiteImpl){

        BaseCatalogoModel baseCatalogoModel = new BaseCatalogoModel();
        baseCatalogoModel.setEstatus(true);
        baseCatalogoModel.setNombre("nombre catalogo");
        baseCatalogoModel.setFechaCreacion(LocalDateTime.now());
        baseCatalogoModel.setFechaModificacion(LocalDateTime.now());

        BigDecimal montoAuth = new BigDecimal(3);
        BigDecimal monto = new BigDecimal(4);

        InformacionComiteDto informacionComite = new InformacionComiteDto();
        informacionComite.setIdComiteProyecto(2);
        informacionComite.setIdContratoConvenio(3);
        informacionComite.setFechaSesion(LocalDateTime.now());
        informacionComite.setAcuerdo("str");
        informacionComite.setVigencia("str");
        informacionComite.setMontoAutorizado(montoAuth);
        informacionComite.setMonto(monto);
        informacionComite.setIdComite(2);

        BaseCatalogoModel comite = new BaseCatalogoModel();
        comite.setNombre("comite catalogo");
        comite.setFechaCreacion( LocalDateTime.now());
        comite.setFechaModificacion(LocalDateTime.now());
        comite.setEstatus(true);

        List<ArchivoPlantillaComiteModel>  archivoSesionComiteProyectoList = new ArrayList<>();


        ResponseComiteInfoReporte responseComiteInfo = new ResponseComiteInfoReporte();
        responseComiteInfo.setInfomacionArchivos(archivoSesionComiteProyectoList);
        responseComiteInfo.setInformacionComite(informacionComite);
        responseComiteInfo.setComite(comite);

        List<ResponseComiteInfoReporte> responseComiteInfoList = new ArrayList<>();
        responseComiteInfoList.add(responseComiteInfo);

        Mockito.when(servicioComiteImpl.obtenerComitesReporteInfo(1)).thenReturn(responseComiteInfoList);
    }
}
