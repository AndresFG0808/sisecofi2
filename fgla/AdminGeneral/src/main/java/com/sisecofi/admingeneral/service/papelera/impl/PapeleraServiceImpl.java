package com.sisecofi.admingeneral.service.papelera.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.admingeneral.service.comunes.impl.NexusImpl;
import com.sisecofi.admingeneral.service.papelera.PapeleraService;
import com.sisecofi.admingeneral.util.enums.ErroresPapeleraEnum;
import com.sisecofi.admingeneral.util.exception.PapeleraException;
import com.sisecofi.libreria.comunes.model.papelera.PapeleraModel;
import com.sisecofi.libreria.comunes.repository.papelera.PapeleraRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
public class PapeleraServiceImpl implements PapeleraService {

	private final PapeleraRepository papeleraRepository;
	private final NexusImpl nexusImpl;

	@Override
	public PapeleraModel guardarPapelera(PapeleraModel model) {
		
		PapeleraModel papelera = null;

		List<PapeleraModel> resultados = papeleraRepository.findByPathOriginal(model.getPathOriginal());
		
		if(!resultados.isEmpty()) { //Existe archivo.
			papelera = resultados.get(0);
			papelera.setIdArchivo(model.getIdArchivo());
			papelera.setTipoArchivo(model.getTipoArchivo());
			papelera.setIdUsuario(model.getIdUsuario());
			papelera.setUsuarioElimina(model.getUsuarioElimina());
			papelera.setEstatus(true);
			papelera = papeleraRepository.save(papelera);
		}else {
			model.setEstatus(true);
			papelera = papeleraRepository.save(model);
		}
		
		return papelera;
	}

	@Override
	public List<PapeleraModel> obtenerPapelera() {
		return papeleraRepository.findAll();
	}

	@Override
	public PapeleraModel obtenerArchivoPapelera(Long idPapelera) {
		return papeleraRepository.findById(idPapelera)
				.orElseThrow(() -> new PapeleraException(ErroresPapeleraEnum.ERROR_AL_CONSULTAR));
	}
	
	@Override
	public PapeleraModel restaurarArchivo(Long idPapelera) {
		try {
			PapeleraModel papeleraModel = papeleraRepository.findById(idPapelera)
					.orElseThrow(() -> new PapeleraException(ErroresPapeleraEnum.ERROR_AL_CONSULTAR));

			String path = papeleraModel.getPathNuevo();
			String pathOriginal = papeleraModel.getPathOriginal();

			nexusImpl.restautarArchivo(path, pathOriginal);

			return papeleraModel;

		} catch (Exception e) {
			throw new PapeleraException(ErroresPapeleraEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public void eliminarArchivoPapelera(Long idPapelera) {
		try {
			PapeleraModel papeleraModel = papeleraRepository.findById(idPapelera)
					.orElseThrow(() -> new PapeleraException(ErroresPapeleraEnum.ERROR_AL_CONSULTAR));
			
			papeleraModel.setEstatus(false);
			
			papeleraRepository.save(papeleraModel);
		} catch (Exception e) {
			throw new PapeleraException(ErroresPapeleraEnum.ERROR_AL_ELIMINAR);
		}
	}
}
