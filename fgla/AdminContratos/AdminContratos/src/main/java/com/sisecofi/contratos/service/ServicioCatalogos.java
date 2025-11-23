package com.sisecofi.contratos.service;

import com.sisecofi.contratos.dto.CatalogoProveedorDto;
import com.sisecofi.contratos.dto.FiltroSelect;
import com.sisecofi.libreria.comunes.dto.contrato.UsuarioInfoDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;

import java.util.List;

public interface ServicioCatalogos {

    List<BaseCatalogoModel> obtenerAdministracionCentral ();

    List<BaseCatalogoModel> obtenerEstatatusContrato();

    List<CatalogoProveedorDto> obtenerProovedor();

    List<BaseCatalogoModel> obtenerTipoConsumo();

    List<BaseCatalogoModel> obtenerContraroVigente();

    List<BaseCatalogoModel> obtenerTipoUnidad();

    List<BaseCatalogoModel> obtenerTipoMoneda();

    List<BaseCatalogoModel> obtenerIeps();

    List<BaseCatalogoModel> obtenerIva();

    List<BaseCatalogoModel> obtenetTipoProcedimiento();

    List<BaseCatalogoModel> obtenerDominioTecnologico();

    List<BaseCatalogoModel> obtenerFondeContrato();

    List<Usuario> obtenerUsuarios();

    List<BaseCatalogoModel> obtenerAdministracionGeneral ();

    List<BaseCatalogoModel> obtenerResponsabilidad ();
    
    List<BaseCatalogoModel> obtenerPeriodicidad ();

    List<BaseCatalogoModel> obtenerConvenioColaboracion ();

	List<BaseCatalogoModel> obtenerAdmonCentralPorGeneral(Integer idAdmonGeneral);

	List<UsuarioInfoDto> obtenerAdministradoresCentrales(FiltroSelect filtro);

	List<UsuarioInfoDto> obtenerTodosLosUsuarios();

}
