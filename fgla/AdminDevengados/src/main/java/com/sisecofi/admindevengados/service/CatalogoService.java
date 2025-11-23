package com.sisecofi.admindevengados.service;

import java.util.List;
import java.util.Set;

import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatClaveProducto;
import com.sisecofi.libreria.comunes.model.dictamenes.DictamenId;

public interface CatalogoService {
	
	List<BaseCatalogoModel> obtenerEstatusDictamenes();
	
	List<BaseCatalogoModel> obtenerTipoConsumos();
	
	BaseCatalogoModel obtenerEstatusInicial();
	
	List<BaseCatalogoModel> obtenerTipoPenaDeduccion(DictamenId dictamenId);
	
	BaseCatalogoModel obtenerPenaContractual();
	
	List<BaseCatalogoModel> obtenerDesglose(Long idContrato);

	List<BaseCatalogoModel> obtenerContratoVigente();
	
	List<BaseCatalogoModel> obtenerFasesDictamen();

	List<BaseCatalogoModel> obtenerTipoPenaContractual(DictamenId dictamenId);

	List<BaseCatalogoModel> obtenerTipoPenaConvencional(DictamenId dictamenId);

	List<BaseCatalogoModel> obtenerMeses();
	
	List<BaseCatalogoModel> obtenerPeriodoControlAnio();
	
	Set<BaseCatalogoModel> obtenerIva(Long idContrato);

	List<BaseCatalogoModel> obtenerEstatusFacturas();

	List<BaseCatalogoModel> obtenerMoneda();

	boolean aplicaCC(Long idContrato);

	List<BaseCatalogoModel> obtenerEstatusNotasCredito();

	List<BaseCatalogoModel> obtenerTipoPlantillador();

	List<BaseCatalogoModel> obtenerTipoNotificacionPago();

	List<BaseCatalogoModel> obtenerDesglose();

	List<BaseCatalogoModel> obtenerIvaLista();

	List<CatClaveProducto> obtenerCatalogoClaveProducto();

}
