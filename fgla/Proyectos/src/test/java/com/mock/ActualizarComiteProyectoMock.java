package com.mock;

import com.sisecofi.proyectos.dto.InformacionComiteDto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.proyectos.service.impl.ServicioComiteImpl;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

public class ActualizarComiteProyectoMock {
    public static void actualizarComiteProyectoMock(ServicioComiteImpl servicioComiteImpl, ComiteProyectoModel comiteProyectoModelActualizado) {

        ProyectoModel proyectoModel = new ProyectoModel();
        proyectoModel.setNombreProyecto("nombre del proyecto");
        proyectoModel.setIdProyecto(2L);
        proyectoModel.setIdEstatusProyecto(3);
        proyectoModel.setIdAgp("4");

        InformacionComiteDto comiteProyectoModel = new InformacionComiteDto();
        comiteProyectoModel.setIdComiteProyecto(2);
    //    comiteProyectoModel.setIdTipoMoneda(3);
        comiteProyectoModel.setIdContratoConvenio(4);

        comiteProyectoModel.setFechaSesion(LocalDateTime.now());
        comiteProyectoModel.setAcuerdo("acuerdo");
        comiteProyectoModel.setVigencia("vigencia");
        comiteProyectoModel.setMontoAutorizado(comiteProyectoModelActualizado.getMontoAutorizado());
     //   comiteProyectoModel.setTipoCambio(tipoCambio);
        comiteProyectoModel.setMonto(comiteProyectoModelActualizado.getMonto());
    //    comiteProyectoModel.setComentarios("comentarios");

     //   comiteProyectoModel.setProyectoModel(proyectoModel);
        comiteProyectoModel.setIdComite(2);

        Mockito.when(servicioComiteImpl.actualizarComiteProyecto(any(InformacionComiteDto.class))).thenReturn(comiteProyectoModel);
    }
}
