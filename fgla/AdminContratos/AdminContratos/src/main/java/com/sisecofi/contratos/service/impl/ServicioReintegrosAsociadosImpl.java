package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.dto.ReintegrosAsociadosDto;
import com.sisecofi.contratos.dto.ReporteReintegroAsociadoDto;
import com.sisecofi.contratos.service.ContratoService;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;
import com.sisecofi.contratos.dto.reintegros.ReintegrosDto;
import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import com.sisecofi.contratos.repository.contrato.ReintegrosAsociadosRepository;
import com.sisecofi.contratos.service.ServicioReintegrosAsociados;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.model.catalogo.CatTipoReintegro;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServicioReintegrosAsociadosImpl implements ServicioReintegrosAsociados {

    private final ReintegrosAsociadosRepository reintegrosAsociadosRepository;
    private final CatalogoMicroservicio catalogoMicroservicio;
    private final ContratoService contratoService;

    @Override
    public ReintegrosAsociadosModel guardarReintegrosAsociados(ReintegrosAsociadosModel reintegrosAsociadosModel) {
        try {

            reintegrosAsociadosModel.setEstatus(true);
            reintegrosAsociadosRepository.save(reintegrosAsociadosModel);
            this.contratoService.actualizarUltimaMod(reintegrosAsociadosModel.getIdContrato());
            return reintegrosAsociadosModel;

        }catch (Exception e){
            throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
        }
    }

    @Override
    public ReintegrosAsociadosDto obtenerReintegrosAsociados(Long idContrato) {
        List<ReintegrosAsociadosModel> reintegrosAsociadosModels = reintegrosAsociadosRepository.findByIdContratoAndEstatusTrue(idContrato);

        if (reintegrosAsociadosModels.isEmpty()){
            throw new ContratoException(ErroresEnum.ERROR_REGISTROS_NO_ENCONTRADO);
        }

        try { 
            return sumaReintegrosAsociados(reintegrosAsociadosModels);
        }catch (Exception e){
            throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
        }
    }

    @Override
    public ReporteReintegroAsociadoDto obtenerReintegrosAsociadosReporte(Long idContrato) {

        List<ReintegrosAsociadosModel> reintegrosAsociadosModels = reintegrosAsociadosRepository.findByIdContratoAndEstatusTrue(idContrato);

        ReintegrosAsociadosDto reintegrosAsociadosDto = sumaReintegrosAsociados(reintegrosAsociadosModels);
        List<BigDecimal> reintegros = new ArrayList<>();

        reintegros.add(reintegrosAsociadosDto.getImportes());
        reintegros.add(reintegrosAsociadosDto.getIntereses());
        reintegros.add(reintegrosAsociadosDto.getTotales());

        ReporteReintegroAsociadoDto reporteReintegroAsociadoDto = new ReporteReintegroAsociadoDto();
        reporteReintegroAsociadoDto.setListaTotales(reintegros);
        reporteReintegroAsociadoDto.setReintegrosAsociados(reintegrosAsociadosModels);

        return reporteReintegroAsociadoDto;
    }


    private ReintegrosAsociadosDto sumaReintegrosAsociados(List<ReintegrosAsociadosModel> reintegrosAsociadosModels){
        BigDecimal sumaTotal = BigDecimal.ZERO;
        BigDecimal sumaInteres = BigDecimal.ZERO;
        BigDecimal sumaImportres = BigDecimal.ZERO;

        List<ReintegrosDto> reintegrosDtos = new ArrayList<>();

        for (ReintegrosAsociadosModel reintegroAsociado : reintegrosAsociadosModels){
            sumaTotal = sumaTotal.add(reintegroAsociado.getTotal());
            sumaInteres = sumaInteres.add(reintegroAsociado.getInteres());
            sumaImportres = sumaImportres.add(reintegroAsociado.getImporte());

            CatTipoReintegro catalogo = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
                    CatalogosComunes.TIPO_REINTEGRO.getIdCatalogo(), reintegroAsociado.getTipoReintegro().getIdTipoReintegro(),
                    new CatTipoReintegro());

            ReintegrosDto reintegrosDto = new ReintegrosDto();
            reintegrosDto.setIdReintegrosAsociados(reintegroAsociado.getIdReintegrosAsociados());
            reintegrosDto.setFechaReintegro(reintegroAsociado.getFechaReintegro());
            reintegrosDto.setNombreTipoReintegro(catalogo.getNombre());
            reintegrosDto.setImporte(reintegroAsociado.getImporte());
            reintegrosDto.setInteres(reintegroAsociado.getInteres());
            reintegrosDto.setTotal(reintegroAsociado.getTotal());
            reintegrosDto.setIdContrato(reintegroAsociado.getIdContrato());
            reintegrosDto.setIdTipoReintegro(reintegroAsociado.getTipoReintegro().getIdTipoReintegro());

            reintegrosDtos.add(reintegrosDto);
        }

        BigDecimal totalRedondeado = sumaTotal.setScale(2, RoundingMode.HALF_UP);
        BigDecimal interesRedondeado = sumaInteres.setScale(2, RoundingMode.HALF_UP);
        BigDecimal importeRedondeado = sumaImportres.setScale(2, RoundingMode.HALF_UP);



        ReintegrosAsociadosDto reintegrosAsociadosDto = new ReintegrosAsociadosDto();
        reintegrosAsociadosDto.setImportes(importeRedondeado);
        reintegrosAsociadosDto.setTotales(totalRedondeado);
        reintegrosAsociadosDto.setIntereses(interesRedondeado);
        reintegrosAsociadosDto.setReintegrosAsociados(reintegrosDtos);

        return reintegrosAsociadosDto;
    }
}
