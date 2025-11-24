package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.dto.ContratoDtoLigero;
import com.sisecofi.contratos.dto.CriteriosDeBusquedaContratoDto;
import com.sisecofi.contratos.dto.ReporteReintegroAsociadoDto;
import com.sisecofi.contratos.dto.ServicioContratoDto;
import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import com.sisecofi.libreria.comunes.model.reintegros.ReintegrosAsociadosModel;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.ServicioReintegrosAsociados;
import com.sisecofi.contratos.service.ServicioReporteContratos;
import com.sisecofi.contratos.service.convenio_modificatorio.ServicioConvenioModificatorio;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.util.consumer.*;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.dto.contrato.ParticipantesAdminContratoDto;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ServicioReporteContratosImpl implements ServicioReporteContratos {

    private static final String LOG_ERROR_GET_COMITE_PROYECTO = "Error al obtener comite-proyecto: {}";

    private final PistaService pistaService;
    private final ServicioContratosImpl servicioContratos;
    private final ReporteContratoConsumer reporteContratoConsumer;
    private final ServicioSeccionesContratoImpl servicioSeccionesContrato;
    private final ReporteServicioContratoConsumer reporteServicioContratoConsumer;
    private final ServicioSeccionAtrasoPrestacionImpl servicioSeccionAtrasoPresentacion;
    private final ReporteAtrasoPrestacionConsumer reporteAtrasoPrestacionConsumer;
    private final ServicioDatosGeneralesContratoImpl servicioDatosGeneralesContrato;
    private final ReporteParticipantesContratoConsumer reporteParticipantesContratoConsumer;
    private final ReporteReintegrosAsociadosConsumer reporteReintegrosAsociadosConsumer;
    private final ServicioReintegrosAsociados servicioReintegrosAsociados;
    private final ServicioConvenioModificatorio servicioConvenioModificatorio;
    private final ReporteConvenioModificatorioConsumer reporteConvenioModificatorioConsumer;

    @Override
    public String obtenerReporteContratosRegistrados(CriteriosDeBusquedaContratoDto contrato){
        List<ContratoDtoLigero> contratoDtoList = servicioContratos.buscarContratosReporte(contrato);
        
        StringBuilder ids = new StringBuilder();

        for (ContratoDtoLigero cont : contratoDtoList) {
            ids.append(" | ").append(cont.getIdContrato());
        }

        try {
            reporteContratoConsumer.inializar("Informacion del contrato");
            reporteContratoConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_CONTRATO);

            contratoDtoList.stream().forEach(reporteContratoConsumer);
            byte[] reporte = reporteContratoConsumer.cerrarBytes();



            // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


            // TipoSeccionPista.CONTRATOS_TABLA.getIdSeccionPista(), ids.toString(), Optional.empty());

            return Base64.getEncoder().encodeToString(reporte);

        }catch (Exception e){
            log.info(LOG_ERROR_GET_COMITE_PROYECTO);
            throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);

        }
    }

    @Override
    public String obtenerReporteServicioConttrato(Long idContrato) {
        List<ServicioContratoDto> servicioContratoDtos = servicioSeccionesContrato.obtenerServicioContrato(idContrato);

        try {
            reporteServicioContratoConsumer.inializar("Informacion de servicio contrato");
            reporteServicioContratoConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_SERVICIO_CONTRATO);

            servicioContratoDtos.stream().forEach(reporteServicioContratoConsumer);
            byte[] reporte = reporteServicioContratoConsumer.cerrarBytes();



            // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


            // TipoSeccionPista.CONTRATOS_REGISTRO_SERVICIOS.getIdSeccionPista(), TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());



            // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


            // TipoSeccionPista.CONTRATOS_REGISTRO_SERVICIOS.getIdSeccionPista(), TipoMovPista.IMPRIME_REGISTRO.getClave(), Optional.empty());

            return Base64.getEncoder().encodeToString(reporte);

        }catch (Exception e){
            log.info(LOG_ERROR_GET_COMITE_PROYECTO);
            throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
        }
    }

    @Override
    public String obtenerReporteAtrasoPrestacion(Long idContrato) {
        List<AtrasoPrestacionModel> servicioContratoDtos = servicioSeccionAtrasoPresentacion.obtenerAtrasosPrestacionesPorIdContrato(idContrato);

        if ( servicioContratoDtos.isEmpty()){
            throw new ContratoException(ErroresEnum.ERROR_REGISTROS_NO_ENCONTRADO);
        }

        try {
            reporteAtrasoPrestacionConsumer.inializar("Informacion de servicio contrato");
            reporteAtrasoPrestacionConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_ATRASO_PRESENTACION);

            servicioContratoDtos.stream().forEach(reporteAtrasoPrestacionConsumer);
            byte[] reporte = reporteAtrasoPrestacionConsumer.cerrarBytes();



            // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


            // TipoSeccionPista.CONTRATOS_ATRASO.getIdSeccionPista(), TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());



            // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


            // TipoSeccionPista.CONTRATOS_ATRASO.getIdSeccionPista(), TipoMovPista.IMPRIME_REGISTRO.getClave(), Optional.empty());

            return Base64.getEncoder().encodeToString(reporte);

        }catch (Exception e){
            log.info(LOG_ERROR_GET_COMITE_PROYECTO);
            throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
        }
    }

    @Override
    public String obtenerReporteParticipantesContrato(Long idContrato) {

        List<ParticipantesAdminContratoDto> participantesAdminContratoDtos = servicioDatosGeneralesContrato.obtenerParticipantesAdminContrato(idContrato);

        reporteParticipantesContratoConsumer.inializar("Informacion participantes del contrato");
        reporteParticipantesContratoConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_PARTICIPANTES_CONTRATO);

        participantesAdminContratoDtos.stream().forEach(reporteParticipantesContratoConsumer);
        byte[] reporte = reporteParticipantesContratoConsumer.cerrarBytes();



        // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


        // TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());



        // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


        // TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), TipoMovPista.IMPRIME_REGISTRO.getClave(), Optional.empty());

        return Base64.getEncoder().encodeToString(reporte);
    }

    @Override
    public String obtenerReporteReintegroAsociado(Long idContrato) {
        ReporteReintegroAsociadoDto reporteReintegroAsociadoDto = servicioReintegrosAsociados.obtenerReintegrosAsociadosReporte(idContrato);

        List<ReintegrosAsociadosModel> reintegros =  reporteReintegroAsociadoDto.getReintegrosAsociados();

        reporteReintegrosAsociadosConsumer.inializar("Informacion participantes del contrato");
        reporteReintegrosAsociadosConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_REINTEGROS_ASOCIADOS);

        reintegros.stream().forEach(reporteReintegrosAsociadosConsumer);
        reporteReintegrosAsociadosConsumer.agregarValoresTotales(reporteReintegroAsociadoDto.getListaTotales().toArray(new BigDecimal[0]));
        byte[] reporte = reporteReintegrosAsociadosConsumer.cerrarBytes();



        // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


        // TipoSeccionPista.CONTRATOS_REINTEGROS_ASOCIADOS.getIdSeccionPista(), TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());



        // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


        // TipoSeccionPista.CONTRATOS_REINTEGROS_ASOCIADOS.getIdSeccionPista(), TipoMovPista.IMPRIME_REGISTRO.getClave(), Optional.empty());

        return Base64.getEncoder().encodeToString(reporte);
    }

    @Override
    public String obtenerReporteConvenioModificatorio(Long idContrato) {

        List<ConvenioModificatorioModel> convenioModificatorio = servicioConvenioModificatorio.obtenerConvenio(idContrato);

        reporteConvenioModificatorioConsumer.inializar("Informacion convenio modificatorio");
        reporteConvenioModificatorioConsumer.agregarCabeceras(Constantes.TITULOS_REPORTE_CONVENIO_MODIFICATORIO);

        convenioModificatorio.stream().forEach(reporteConvenioModificatorioConsumer);
        byte[] reporte = reporteConvenioModificatorioConsumer.cerrarBytes();



        // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


        // TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());



        // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


        // TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), TipoMovPista.IMPRIME_REGISTRO.getClave(), Optional.empty());

        return Base64.getEncoder().encodeToString(reporte);
    }
}
