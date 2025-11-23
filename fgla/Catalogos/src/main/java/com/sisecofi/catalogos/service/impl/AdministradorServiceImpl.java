package com.sisecofi.catalogos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.catalogos.repository.AdministradorAdministracionRepository;
import com.sisecofi.catalogos.repository.AdministradorCentralRepository;
import com.sisecofi.catalogos.repository.AdministradorGeneralRepository;
import com.sisecofi.catalogos.service.AdministradorService;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements AdministradorService {

	private final AdministradorGeneralRepository administradorGeneralRepository;
	private final AdministradorCentralRepository administradorCentralRepository;
	private final AdministradorAdministracionRepository administradorAdministracionRepository;

	@Override
	public List<CatAdministradorCentral> buscarAdministradorCentral() {
		return administradorCentralRepository.findAll();
	}

	@Override
	@Transactional
	public CatAdministradorCentral guardarAdminsitradorCentral(CatAdministradorCentral administradorCentral) {
		administradorCentralRepository.borrarLogicoTodos(false);
		return administradorCentralRepository.save(administradorCentral);
	}

	@Override
	public boolean eliminarAdminsitradorCentral(Integer id) {
		administradorCentralRepository.borrarLogico(true, id);
		return true;
	}

	@Override
	public List<CatAdministradorGeneral> buscarAdministradorGeneral() {
		return administradorGeneralRepository.findAll();
	}

	@Override
	@Transactional
	public CatAdministradorGeneral guardarAdminsitradorGeneral(CatAdministradorGeneral administradorGeneral) {
		administradorGeneralRepository.borrarLogicoTodos(false);
		return administradorGeneralRepository.save(administradorGeneral);
	}

	@Override
	public boolean eliminarAdminsitradorGeneral(Integer id) {
		administradorGeneralRepository.borrarLogico(true, id);
		return true;
	}

	@Override
	public List<CatAdministradorAdministracion> buscarAdministradorAdministracion() {
		return administradorAdministracionRepository.findAll();
	}

	@Override
	public CatAdministradorAdministracion guardarAdminsitradorAdministracion(
			CatAdministradorAdministracion administradorCentral) {
		administradorAdministracionRepository.borrarLogicoTodos(false);
		return administradorAdministracionRepository.save(administradorCentral);
	}

	@Override
	public boolean eliminarAdminsitradorAdministracion(Integer id) {
		administradorAdministracionRepository.borrarLogico(true, id);
		return true;
	}

}
