package com.sisecofi.contratos.service;

import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;

import java.util.List;

public interface ServicioSeccionAtrasoPresentacion {

    AtrasoPrestacionModel obtenerAtrasoPresentacion(Long idAtrasoPresentacion);

    String guardarAtrasoPresentacion(List<AtrasoPrestacionModel> atrasoPresentacionModel);

    String editarAtrasoPresnetacion(List<AtrasoPrestacionModel> atrasoPresentacionModel);

    String eliminarAtrasoPresentacion(List<Long> idAtrasoPrestacion);

    List<AtrasoPrestacionModel> obtenerAtrasosPrestaciones(Long idContrato);

    List<AtrasoPrestacionModel> obtenerAtrasosPrestacionesPorIdContrato(Long idContrato);

	AtrasoPrestacionModel obtenerAtrasoPrestacionIndividual(Long idInforme);
}
