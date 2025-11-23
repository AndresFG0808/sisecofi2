package com.sisecofi.reportedocumental.service.dinamico;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ProveedorRazonSocialDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.TituloServicioPreveedorDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.reportedocumental.dto.dinamico.Ids;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface CatalogoDinamicoService {

	List<BaseCatalogoModel> faseProyecto();

	List<BaseCatalogoModel> estatusProyecto();

	List<BaseCatalogoModel> estatusContrato();

	List<BaseCatalogoModel> dominioTecnologico();

	List<ProyectoNombreDto> buscarProyecto(Ids<Integer> estatusProyecto);

	List<ContratoNombreDto> buscarContrato(Ids<Integer> estatusContrato);

	List<ProveedorRazonSocialDto> razonSocial();

	List<TituloServicioPreveedorDto> buscarTituloServicio(Ids<Long> idProveedor);

	List<BaseCatalogoModel> convenioColoboracion();

}
