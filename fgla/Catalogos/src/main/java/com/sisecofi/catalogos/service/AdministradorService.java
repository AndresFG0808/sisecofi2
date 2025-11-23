package com.sisecofi.catalogos.service;

import java.util.List;

import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface AdministradorService {

	List<CatAdministradorCentral> buscarAdministradorCentral();

	CatAdministradorCentral guardarAdminsitradorCentral(CatAdministradorCentral administradorCentral);

	boolean eliminarAdminsitradorCentral(Integer id);

	List<CatAdministradorGeneral> buscarAdministradorGeneral();

	CatAdministradorGeneral guardarAdminsitradorGeneral(CatAdministradorGeneral administradorCentral);

	boolean eliminarAdminsitradorGeneral(Integer id);

	List<CatAdministradorAdministracion> buscarAdministradorAdministracion();

	CatAdministradorAdministracion guardarAdminsitradorAdministracion(
			CatAdministradorAdministracion administradorCentral);

	boolean eliminarAdminsitradorAdministracion(Integer id);

}
