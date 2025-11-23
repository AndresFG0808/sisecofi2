package com.mock;

import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.libreria.comunes.model.proyectos.ProyectoModel;
import com.sisecofi.proyectos.repository.ComiteRepository;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class ComiteRepositoryMock {
    public static void guardarComiteProyectoMock(ComiteRepository comiteRepository, Integer idComiteProyecto) {

        BigDecimal montoAuth = new BigDecimal(3);
        BigDecimal monto = new BigDecimal(4);
        BigDecimal tipoCambio = new BigDecimal(6);

        ProyectoModel proyectoModel = new ProyectoModel();
        proyectoModel.setNombreProyecto("nombre del proyecto");
        proyectoModel.setIdProyecto(2L);
        proyectoModel.setIdEstatusProyecto(3);
        proyectoModel.setIdAgp("4");

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

        comiteProyectoModel.setProyectoModel(proyectoModel);
        comiteProyectoModel.setIdComite(2);


        Mockito.when(comiteRepository.findByIdComiteProyecto(any(idComiteProyecto.getClass()))).thenReturn(Optional.of(comiteProyectoModel));
    }
}
