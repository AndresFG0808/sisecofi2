package com.sisecofi.proyectos.util.consumer;

import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.proyectos.util.adminplantrabajo.constantes.Constantes;
import org.springframework.stereotype.Component;

@Component
public class PistasInformacionComiteConsumer {

    public String movimientoContratoModel(ComiteProyectoModel comiteProyectoModel) {
        return Constantes.getAtributosComiteProyecto()[0] + (comiteProyectoModel.getIdComiteProyecto() == null ? "null" : comiteProyectoModel.getIdContrato()) + "|" +
                Constantes.getAtributosComiteProyecto()[1] + (comiteProyectoModel.getIdProyecto() == null ? "null" : comiteProyectoModel.getIdProyecto()) + "|" +
                Constantes.getAtributosComiteProyecto()[2] + (comiteProyectoModel.getIdTipoMoneda() == null ? "null" : comiteProyectoModel.getIdTipoMoneda()) + "|" +
                Constantes.getAtributosComiteProyecto()[3] + (comiteProyectoModel.getIdContratoConvenio() == null ? "null" : comiteProyectoModel.getIdContratoConvenio()) + "|" +
                Constantes.getAtributosComiteProyecto()[4] + (comiteProyectoModel.getIdComite() == null ? "null" : comiteProyectoModel.getIdComite()) + "|" +
                Constantes.getAtributosComiteProyecto()[5] + (comiteProyectoModel.getIdSesionNumero() == null ? "null" : comiteProyectoModel.getIdContrato()) + "|" +
                Constantes.getAtributosComiteProyecto()[6] + (comiteProyectoModel.getIdSesionClasificacion() == null ? "null" : comiteProyectoModel.getFechaSesion()) + "|" +
                Constantes.getAtributosComiteProyecto()[7] + (comiteProyectoModel.getAcuerdo() == null ? "null" : comiteProyectoModel.getIdSesionNumero()) + "|" +
                Constantes.getAtributosComiteProyecto()[8] + (comiteProyectoModel.getVigencia() == null ? "null" : comiteProyectoModel.getVigencia()) + "|" +
                Constantes.getAtributosComiteProyecto()[9] + (comiteProyectoModel.getMontoAutorizado() == null ? "null" : comiteProyectoModel.getMontoAutorizado()) + "|" +
                Constantes.getAtributosComiteProyecto()[10] + (comiteProyectoModel.getTipoCambio() == null ? "null" : comiteProyectoModel.getTipoCambio()) + "|" +
                Constantes.getAtributosComiteProyecto()[11] + (comiteProyectoModel.getMonto() == null ? "null" : comiteProyectoModel.getMonto()) + "|" +
                Constantes.getAtributosComiteProyecto()[12] + (comiteProyectoModel.getComentarios() == null ? "null" : comiteProyectoModel.getComentarios());
    }
}
