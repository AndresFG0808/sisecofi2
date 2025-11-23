package com.sisecofi.proyectos.service;

import com.sisecofi.libreria.comunes.model.gestionDocumental.comite.ComiteProyectoModel;
import com.sisecofi.proyectos.dto.*;


import java.util.List;

public interface ServicioComite {

    List<ResponseComiteInfo> obtenerComiteInformacion(Integer idProyecto);

    ResponseComite obtenerDetalleComite(Integer idComiteProyecto);

    ComiteProyectoModel guardarComiteProyecto(ComiteProyectoDto comiteProyectoModel);

    InformacionComiteDto actualizarComiteProyecto(InformacionComiteDto comiteProyectoModel);

    String eliminarInformacionComite(Integer idComiteProyecto);

    List<ResponseComiteInfoReporte> obtenerComitesReporteInfo(Integer idProyecto);
}
