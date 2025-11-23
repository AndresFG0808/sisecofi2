package com.sisecofi.contratos.service.reintegros.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.contratos.dto.reintegros.ReintegrosConsultaDto;

import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.reintegros.ReintegroConsultaService;
import com.sisecofi.contratos.service.reintegros.ReporteReintegroService;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.reintegros.ReporteReintegrosAsociados;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteReintegroServiceImpl implements ReporteReintegroService {

    private final ReporteReintegrosAsociados reporteReintegros;
    private final ReintegroConsultaService reintegroConsultaService;
    private final PistaService pistaService;

    @Override
    public byte[] obtenerReporteReintegro(Long idContrato) {

        List<ReintegrosConsultaDto> lista = reintegroConsultaService.buscarReintegrosPorContratos(idContrato);
        reporteReintegros.inializar("Reporte Reintegro cargado");
        reporteReintegros.agregarCabeceras(Constantes.TITULO_REINTEGROS);
        lista.stream().forEach(reporteReintegros);

        registrarPistaReintegroAsociado(lista);

        return reporteReintegros.cerrarBytes();

    }

    @Transactional
    private void registrarPistaReintegroAsociado(List<ReintegrosConsultaDto> lista) {
        lista.forEach(listReintegros -> {

            StringBuilder builder = new StringBuilder();
            builder.append("Nombre Corto: ").append(listReintegros.getNombreCorto()).append(" | ")
                    .append("Id Reintegro Asociado: ").append(listReintegros.getIdReintegrosAsociados()).append(" | ");

            pistaService.guardarPista(ModuloPista.REINTEGRO.getId(),
                    TipoMovPista.IMPRIME_REGISTRO.getId(),
                    TipoSeccionPista.REINTEGROS.getIdSeccionPista(),
                    builder.toString(),
                    Optional.empty());

        });

    }

}
