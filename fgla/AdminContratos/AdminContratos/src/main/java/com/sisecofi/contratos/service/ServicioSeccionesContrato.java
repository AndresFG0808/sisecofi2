package com.sisecofi.contratos.service;

import com.sisecofi.contratos.dto.*;
import com.sisecofi.libreria.comunes.dto.contrato.VigenciaMontoDto;
import com.sisecofi.libreria.comunes.model.contratos.GrupoServiciosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.model.contratos.ServicioContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.VigenciaMontosModel;

import java.util.List;

public interface ServicioSeccionesContrato {

    List<VigenciaMontoDto> obtenerVigenciaMontos();

    VigenciaMontoDto obtenerVigenciaMonto(Long idContrato);

    List<GrupoServicioDto> obtenerGrupoServicio(Long idContrato);

    String guardarGrupoServicio(List<GrupoServiciosModel> grupoServiciosModel);

    VigenciaMontosModel vigenciaMontos(VigenciaMontosModel vigenciaMontosModel);

    String actualizarVigenciaMontos(VigenciaMontosModel vigenciaMontosModel);

    String eliminarVigenciaMonto(EliminarVigenciaMontosDto eliminarVigenciaMontosDto);

    String eliminarGrupoiServicio(List<Long> idsGrupoServicio);

    String actualizarGrupoSetvicio(List<ActualizarGrupoServicioDto> grupoServicioDto);

    String guardarServicioContrato(List<ServicioContratoModel> grupoServiciosModel);

    List<ServicioContratoDto> obtenerServicioContrato(Long idContrato);

    String eliminarRegistroServicioContrato(List<Long> idServicioContrato);

    String actualizarServicioContrato(List<ServicioContratoModel> grupoServiciosModel);

    String validarServicioContrato(Long idContrato);
    
    List<InformesDocumentalesUnicaVezModel> obtenerInformesDocumentalesUnicaVez(Long idContrato);
}
