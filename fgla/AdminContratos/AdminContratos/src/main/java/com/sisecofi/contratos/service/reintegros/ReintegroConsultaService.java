package com.sisecofi.contratos.service.reintegros;

import java.util.List;

import com.sisecofi.contratos.dto.reintegros.ReintegrosConsultaDto;
import com.sisecofi.contratos.dto.reintegros.ReintegrosDto;
import com.sisecofi.contratos.dto.reintegros.ReintegrosModificaDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;

public interface ReintegroConsultaService {


    List<ContratoSimpleDto> obtenerContratosVigentes(String vigente);

    List<ReintegrosDto> crearReintegros(List<ReintegrosDto> reintegrosDtoList);

    List<ReintegrosModificaDto> modificarReintegros(List<ReintegrosModificaDto> reintegrosDtoList);

    void eliminarReintegros(List<Long> idReintegrosAsociadosList);

    List<ReintegrosConsultaDto> buscarReintegrosPorContratos(Long idContrato);

	String generarRutaFase(Long idContrato, String carpeta);

	String generarRutaBase(Long idContrato);

	String generarRuta(Long idContrato, String carpeta);

	void crearArchivos(ReintegrosAsociadosModel reintegroGuardado);

	String generarRutaCloud(Long idContrato);



}
