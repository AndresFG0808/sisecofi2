package com.sisecofi.contratos.service;

import com.sisecofi.contratos.dto.EliminarParticipantesDto;
import com.sisecofi.contratos.dto.ProveedoresDto;
import com.sisecofi.libreria.comunes.dto.contrato.DatosGeneralesContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.DatosGeneralesResponseDto;
import com.sisecofi.libreria.comunes.dto.contrato.ParticipantesAdminContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.UsuarioInfoDto;
import com.sisecofi.libreria.comunes.model.contratos.DatosGeneralesContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.ParticipantesAdministracionModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;

import java.util.List;

public interface ServicioDatosGeneralesContrato {

    DatosGeneralesContratoModel guardarDatosGenerales(DatosGeneralesContratoDto datosGeneralesContratoModel);

    DatosGeneralesResponseDto obtenerDatosGnerales(Long idContrato);

    List<ProveedorModel> agregarProveedor(ProveedoresDto proveedoresDto);

    String eliminarProveedor(ProveedoresDto proveedoresDto);

    DatosGeneralesContratoModel actualizarDatosGenerales(DatosGeneralesContratoDto datosGeneralesContratoDto);

    String guardarParticipantesAdminContrato(List<ParticipantesAdministracionModel> participantesAdministracionModel);

    String editarParticipantesAdminContrato(List<ParticipantesAdministracionModel> participantesAdministracionModel);

    List<ParticipantesAdminContratoDto> obtenerParticipantesAdminContrato(Long idContrato);

    String eliminarParticipantesAdminContrato(EliminarParticipantesDto eliminarParticipantesDto);

	UsuarioInfoDto obtenerUsuarioDto(ParticipantesAdministracionModel participante);
}
