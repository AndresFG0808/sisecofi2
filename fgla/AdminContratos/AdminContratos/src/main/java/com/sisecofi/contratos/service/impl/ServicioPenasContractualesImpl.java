package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.service.ContratoService;
import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.ReportePenasConsumer;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.contratos.repository.contrato.PenaContractualContratoRepository;
import com.sisecofi.contratos.service.ServicioPenasContractuales;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioPenasContractualesImpl implements ServicioPenasContractuales {

	private final PenaContractualContratoRepository penaContractualContratoRepository;
	private final ReportePenasConsumer consumer;
	private final ContratoService contratoService;

	@Override
	@Transactional
	public Boolean crearPena(List<PenaContractualContratoModel> request, Long idContrato) {
		for (PenaContractualContratoModel pena : request) {
			pena.setEstatus(true);
			pena.setIdContrato(idContrato);
			guardarPena(pena);
		}
		this.contratoService.actualizarUltimaMod(idContrato);
		return true;
	}

	@Override
	@Transactional
	public Boolean editarPena(List<PenaContractualContratoModel> pena) {
		for (PenaContractualContratoModel p : pena) {
			guardarPena(p);
		}
		if (!pena.isEmpty()) {
			this.contratoService.actualizarUltimaMod(pena.get(0).getIdContrato());
		}
		return true;
	}

	private void guardarPena(PenaContractualContratoModel pena) {
		penaContractualContratoRepository.save(pena);
	}

	@Override
	@Transactional
	public Boolean eliminarPena(List<Long> id) {
		for (Long i : id) {
			PenaContractualContratoModel pena = penaContractualContratoRepository
					.findByIdPenaContractualContrato(i)
					.orElseThrow(() -> new ContratoException(ErroresEnum.PENA_NO_ENCONTRADA));
			pena.setEstatus(false);
			guardarPena(pena);
		}
		if (!id.isEmpty()) {
			PenaContractualContratoModel pena = penaContractualContratoRepository
					.findByIdPenaContractualContrato(id.get(0)).orElseThrow(() -> new ContratoException(ErroresEnum.PENA_NO_ENCONTRADA));
			this.contratoService.actualizarUltimaMod(pena.getIdContrato());
		}
		return true;
	}

	@Override
	public List<PenaContractualContratoModel> obtenerPenas(Long idContrato) {
		return penaContractualContratoRepository.findByIdContratoAndEstatusTrueOrderByIdPenaContractualContrato(idContrato);
	}

	@Override
	public String generarReporte(Long idContrato) {
		List<PenaContractualContratoModel> lista = obtenerPenas(idContrato);
		try {
			consumer.inializar("Penas contractuales");
			consumer.agregarCabeceras(Constantes.TITULOS_REPORTE_PENAS_CONTRACTUALES);
			lista.stream().forEach(consumer); 
			byte[] reporte = consumer.cerrarBytes();
			return Base64.getEncoder().encodeToString(reporte);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}
}