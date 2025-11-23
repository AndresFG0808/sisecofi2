package com.sisecofi.admingeneral.service.papelera;

import java.util.List;

import com.sisecofi.libreria.comunes.model.papelera.PapeleraModel;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PapeleraService {

	PapeleraModel guardarPapelera(PapeleraModel model);

	List<PapeleraModel> obtenerPapelera();
	
	PapeleraModel obtenerArchivoPapelera(Long idPapelera);

	PapeleraModel restaurarArchivo(Long idPapelera);
	
	void eliminarArchivoPapelera(Long idPapelera);
}
