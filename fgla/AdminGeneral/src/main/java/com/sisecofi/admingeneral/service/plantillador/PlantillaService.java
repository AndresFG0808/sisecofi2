package com.sisecofi.admingeneral.service.plantillador;

import java.util.List;

import com.sisecofi.admingeneral.dto.plantillador.ContenidoPlantillaDto;
import com.sisecofi.admingeneral.dto.plantillador.PlantilladorDto;
import com.sisecofi.admingeneral.dto.plantillador.RequestPlantilla;
import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorBaseModel;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoBase;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PlantillaService {

	PlantilladorDto buscarPlantillas(Integer idTipoPlantilla);

	boolean guardarPlantillas(RequestPlantilla requestPlantilla);

	byte[] vistaPreviaPlantillas(RequestPlantilla requestPlantilla);

	List<PlantilladorDto> buscarPlantillas();

	boolean guardarPlantillas(List<PlantilladorDto> requestPlantilla);

	ContenidoBase obtenerContenidoPlantillador(Long idPlantillador, Long idContenidoSubPlantillador);

	boolean guardarNuevasPlantillas(List<PlantilladorDto> requestPlantilla);

	ContenidoPlantilladorModel obtenerContenidoPlantilladorPorId(Long idPlantillador);

	List<PlantilladorModel> obtenerPlantilladoresPorTipoPlantillador(Integer idTipoPlantillador);

	ContenidoPlantilladorBaseModel obtenerBase (Integer idTipo);

	ContenidoPlantillaDto obtenerContenidoPlantilladorDto(PlantilladorDto dto);

	ContenidoPlantillaDto guardarInformacionPlantilla(ContenidoPlantillaDto dto);

	List<SubPlantilladorModel> obtenerSubPlantilladores();

	PlantilladorModel obtenerPlantilladoresPorIdPlantillador(Long idPlantillador);

	byte[] obtenerVariablesAyuda(ContenidoPlantillaDto dto);
}
