package com.sisecofi.admindevengados.util.consumer;

import com.sisecofi.libreria.comunes.dto.dictamen.DevengadoBusquedaResponse;
import com.sisecofi.admindevengados.model.ResumenConsolidadoModel;
import com.sisecofi.admindevengados.repository.ResumenConsolidadoRepository;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class DictamenMap implements Function<Dictamen, DevengadoBusquedaResponse> {

	private final ResumenConsolidadoRepository resumenConsolidadoRepository;
	private static final String CERO= "$0.00";

	@Override
	public DevengadoBusquedaResponse apply(Dictamen t) {
	    DevengadoBusquedaResponse response = new DevengadoBusquedaResponse();
	    setBasicResponseData(response, t);
	    setMontoDictaminado(response, t);
	    response.setTipo("Dictamen");
	    return response;
	}

	private void setBasicResponseData(DevengadoBusquedaResponse response, Dictamen t) {
	    response.setEstatus(t.getCatEstatusDictamen().getNombre());
	    response.setId(t.getIdDictamenVisual());
	    response.setMontoDictaminadoPesos(CERO);
	    response.setMontoEstimado(CERO);
	    response.setMontoEstimadoPesos(CERO);
	    response.setPeriodoControl(t.getCatPeriodoControlMes().getNombre() + " " + t.getCatPeriodoControlAnio().getNombre());
	    response.setPeriodoFin(t.getPeriodoFin());
	    response.setPeriodoInicio(t.getPeriodoInicio());
	    response.setPendientePago("0");
		response.setIdBd(t.getIdDictamen());
	    response.setProveedor(t.getProveedorModel().getNombreProveedor());
	    response.setTipoCambioReferencial(
	            t.getTipoCambioReferencial().compareTo(BigDecimal.ZERO) != 0 ? t.getTipoCambioReferencial() : null);
	    response.setIdProveedor(t.getIdProveedor());
	}

	private void setMontoDictaminado(DevengadoBusquedaResponse response, Dictamen t) {
	    List<ResumenConsolidadoModel> resumenConsolidado = resumenConsolidadoRepository
	            .findByIdDictamenOrderByIdResumenConsolidadoAsc(t.getIdDictamen());
	    ResumenConsolidadoModel primerElemento = resumenConsolidado.isEmpty() ? null : resumenConsolidado.get(0);
	    BigDecimal montoDictaminado = primerElemento != null ? primerElemento.getTotal() : BigDecimal.ZERO;
	    BigDecimal montoDictaminadoPesos = primerElemento != null ? primerElemento.getTotalPesos() : BigDecimal.ZERO;

	    response.setMontoDictaminado(String.format("$%,.2f", montoDictaminado));
	    response.setMontoDictaminadoPesos(String.format("$%,.2f", montoDictaminadoPesos));
	}
	

}
