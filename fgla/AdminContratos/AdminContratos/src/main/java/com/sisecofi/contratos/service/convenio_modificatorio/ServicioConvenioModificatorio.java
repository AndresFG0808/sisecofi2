package com.sisecofi.contratos.service.convenio_modificatorio;

import com.sisecofi.contratos.dto.ConvenioDto;
import com.sisecofi.contratos.dto.ConvenioModificatorioRequest;
import com.sisecofi.contratos.dto.ReporteConvenioModificatorioDto;
import com.sisecofi.contratos.dto.ServiciosConvenioDto;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ServicioConvenioModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ServicioConvenioModificatorio {

	List<ConvenioModificatorioModel> obtenerConvenio(Long idContrato);

	Page<List<ConvenioModificatorioModel>> obtenerConvenioPage(ConvenioModificatorioRequest request);

    List<ReporteConvenioModificatorioDto> obtenerConvenioReporte(Long idContrato);

	ConvenioModificatorioModel crearConvenio(ConvenioModificatorioModel convenio, Long idContrato);

	List<ServicioConvenioModel> obtenerServicioConvenio(Long idConvenio);

	ServicioConvenioModel calcularServicio(ServicioConvenioModel convenio);

	ConvenioModificatorioModel modificarConvenio(ConvenioModificatorioModel convenio);

	ConvenioModificatorioModel obtenerConvenioId(Long idConvenio);

	ConvenioDto datosIniciales(Long idContrato);
	
	ServiciosConvenioDto guardarServicios(Set<ServicioConvenioModel> lista);

	String validar(List<ServicioConvenioModel> lista);

	String reporteServicios(Long idConvenio);

	ServiciosConvenioDto obtenerServiciosDto(Long idConvenio);
	
	String obtenerUltimaMod(Long idConvenio);
}
