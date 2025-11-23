package com.sisecofi.reportedocumental.service.pista;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.reportedinamico.EmpleadoDto;
import com.sisecofi.libreria.comunes.model.pista.CatModuloPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatSeccionPistaModel;
import com.sisecofi.libreria.comunes.model.pista.CatTipoMovPistaModel;
import com.sisecofi.reportedocumental.dto.pistareporte.BusquedaPistaDto;
import com.sisecofi.reportedocumental.dto.pistareporte.HistoricoPistaDto;
import com.sisecofi.reportedocumental.dto.pistareporte.PagePista;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PistaReporteService {

	PagePista obtenerReporte(BusquedaPistaDto busquedaDto);

	byte[] obtenerReporteExport(BusquedaPistaDto busquedaDto);

	List<EmpleadoDto> obtenerUsuarios();

	List<CatModuloPistaModel> obtenerModuloPista();

	List<CatSeccionPistaModel> obtenerSeccionPista(Integer idModulo);

	List<CatTipoMovPistaModel> obtenerTipoMovimientoPista();

	HistoricoPistaDto buscarPistas(Long idPista);

}
