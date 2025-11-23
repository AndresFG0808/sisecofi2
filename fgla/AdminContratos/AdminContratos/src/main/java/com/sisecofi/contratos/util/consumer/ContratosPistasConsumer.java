package com.sisecofi.contratos.util.consumer;


import com.sisecofi.contratos.dto.CriteriosDeBusquedaContratoDto;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.model.contratos.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContratosPistasConsumer {


    public String movimientoContratoModel(ContratoModel contratoModel) {
        return Constantes.getAtributosContrato()[0] +
                (contratoModel.getIdContrato() == null ? "null" : contratoModel.getIdContrato()) + "|" +
                Constantes.getAtributosContrato()[1] +
                (contratoModel.getNumeroContrato() == null ? "null" : contratoModel.getNumeroContrato()) + "|" +
                Constantes.getAtributosContrato()[2] +
                (contratoModel.getNombreCorto() == null ? "null" : contratoModel.getNombreCorto()) + "|" +
                Constantes.getAtributosContrato()[3] +
                (contratoModel.getProyecto() == null || contratoModel.getProyecto().getIdProyecto() == null ? "null" : contratoModel.getProyecto().getIdProyecto());
    }

   public String movimientoContratoDto(ContratoDto contratoDto){

         return Constantes.getAtributosContrato()[0] + contratoDto.getIdContrato() + "|"
                 + Constantes.getAtributosContratoDto()[1] + contratoDto.getNumeroContrato() + "|"
                 + Constantes.getAtributosContratoDto()[2] + contratoDto.getNombreContrato() + "|"
                 + Constantes.getAtributosContratoDto()[3] + contratoDto.getNombreCorto() + "|"
                 + Constantes.getAtributosContratoDto()[4] + contratoDto.getIdProyecto() + "|"
                 + Constantes.getAtributosContratoDto()[5] + contratoDto.getAdministradorContrato() + "|"
                 + Constantes.getAtributosContratoDto()[6] + contratoDto.getVerificadorContrato() + "|"
                 + Constantes.getAtributosContratoDto()[7] + contratoDto.getTipoProcedimiento() + "|"
                 + Constantes.getAtributosContratoDto()[8] + contratoDto.getFecha_inicio() + "|"
                 + Constantes.getAtributosContratoDto()[9] + contratoDto.getFecha_termino() + "|"
                 + Constantes.getAtributosContratoDto()[10] + contratoDto.getMontoMaximo() + "|"
                 + Constantes.getAtributosContratoDto()[11] + contratoDto.getMontoPesos() + "|"
                 + Constantes.getAtributosContratoDto()[12] + contratoDto.getMontoMaximoUltimoCm() + "|"
                 + Constantes.getAtributosContratoDto()[13] + contratoDto.getUltimoContratoModificatorio() +"|"
                 + Constantes.getAtributosContratoDto()[14] + contratoDto.getIdIva() + "|"
                 + Constantes.getAtributosContratoDto()[15] + contratoDto.getTipoCambio() + "|"
                 + Constantes.getAtributosContratoDto()[16] + contratoDto.getNombreProyecto() + "|"
                 + Constantes.getAtributosContratoDto()[17] + contratoDto.getEstatusContrato() + "|"
                 + Constantes.getAtributosContratoDto()[18] + contratoDto.getGrupoServicio() + "|"
                 + Constantes.getAtributosContratoDto()[19] + contratoDto.getConseptosServico() +"|"
                 + Constantes.getAtributosContratoDto()[20] + contratoDto.getUnidadMedida() +"|"
                 + Constantes.getAtributosContratoDto()[21] + contratoDto.getTipoConsumo() +"|"
                 + Constantes.getAtributosContratoDto()[22] + contratoDto.getPrecioUnitario() +"|"
                 + Constantes.getAtributosContratoDto()[23] + contratoDto.getCantidadServiciosVigente() +"|"
                 + Constantes.getAtributosContratoDto()[24] + contratoDto.getMontoMaximoServicio() +"|"
                 + Constantes.getAtributosContratoDto()[25] + contratoDto.getTipoMoneda() +"|"
                 + Constantes.getAtributosContratoDto()[26] + contratoDto.getAcuerdo() +"|";
     }

   public String movimientosContratosDatosGenerales(DatosGeneralesContratoModel datosGeneralesContratoModel) {

        return Constantes.getAtributosDatosGeneralesContrato()[0] + datosGeneralesContratoModel.getIdDatosGnerales() + "|" +
                Constantes.getAtributosDatosGeneralesContrato()[1] + datosGeneralesContratoModel.getIdContrato() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[2] + datosGeneralesContratoModel.getNumeroContrato() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[3] + datosGeneralesContratoModel.getNumetoContratoCompraNet() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[4] + datosGeneralesContratoModel.getAcuerdo() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[5] + datosGeneralesContratoModel.getIdTipoProcedimiento() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[6] + datosGeneralesContratoModel.getNumeroProcedimiento() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[7] + datosGeneralesContratoModel.getIdConvenioColaboracion() + "|"
                +  Constantes.getAtributosDatosGeneralesContrato()[8] + datosGeneralesContratoModel.getIdDominosTecnologicos() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[9] + datosGeneralesContratoModel.getIdFondeoContrato() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[10] + datosGeneralesContratoModel.getObjetivoServicio() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[11] + datosGeneralesContratoModel.getAlcanceServicio() + "|"
                + Constantes.getAtributosDatosGeneralesContrato()[12] + datosGeneralesContratoModel.getTituloServicio();
    }


    public String movimientoParticipantesContrato(ParticipantesAdministracionModel participantesAdministracionModel){

        return Constantes.getAtributosParticipantesContrato()[0] + participantesAdministracionModel.getIdParticipantesAdministracionContrato() + "|" +
                Constantes.getAtributosParticipantesContrato()[1] + participantesAdministracionModel.getIdContrato() + "|"
                + Constantes.getAtributosParticipantesContrato()[2] + participantesAdministracionModel.getIdResponsabilidad() + "|"
                + Constantes.getAtributosParticipantesContrato()[3] + participantesAdministracionModel.getIdContrato() + "|"
                + Constantes.getAtributosParticipantesContrato()[4] + participantesAdministracionModel.getIdAdmonCentral() + "|"
                + Constantes.getAtributosParticipantesContrato()[5] + participantesAdministracionModel.getIdAdmonGeneral() + "|"
                + Constantes.getAtributosParticipantesContrato()[6] + participantesAdministracionModel.getIdUser() + "|"
                + Constantes.getAtributosParticipantesContrato()[7] + participantesAdministracionModel.getIdNombreServicodPublico() + "|"
                +  Constantes.getAtributosParticipantesContrato()[8] + participantesAdministracionModel.getFechaInicio() + "|"
                + Constantes.getAtributosParticipantesContrato()[9] + participantesAdministracionModel.getFechaTermino() + "|"
                + Constantes.getAtributosParticipantesContrato()[10] + participantesAdministracionModel.getVigente();
    }

    public String movimientoVigenciaMontos(VigenciaMontosModel vigenciaMontosModel){

        return Constantes.getAtributosVigenciaMontosContrato()[0] + vigenciaMontosModel.getIdVigenciaMonto() + "|" +
                Constantes.getAtributosVigenciaMontosContrato()[1] + vigenciaMontosModel.getIdContrato() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[2] + vigenciaMontosModel.getFechaInicioVigenciaServicios() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[3] + vigenciaMontosModel.getFechaFinVigenciaServicios() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[4] + vigenciaMontosModel.getFechaInicioVigenciaContrato() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[5] + vigenciaMontosModel.getFechaFinVigenciaContrato() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[6] + vigenciaMontosModel.getIdTipoMoneda() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[7] + vigenciaMontosModel.getTipoCambioMaximo() + "|"
                +  Constantes.getAtributosVigenciaMontosContrato()[8] + vigenciaMontosModel.getMontoMinimoSinImpuestos() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[9] + vigenciaMontosModel.getMontoMaximoSinImpuestos() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[10] + vigenciaMontosModel.getMontoPesosSinImpuestos() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[10] + vigenciaMontosModel.getMontoMinimoConImpuestos() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[10] + vigenciaMontosModel.getMontoMaximoConImpuestos() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[10] + vigenciaMontosModel.getMontoPesosConImpuestos() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[10] + vigenciaMontosModel.getId_iva() + "|"
                + Constantes.getAtributosVigenciaMontosContrato()[10] + vigenciaMontosModel.getIdIeps();
    }

    public String movimientoGruposServicio(GrupoServiciosModel grupoServiciosModel){

        return Constantes.getAtributosGrupoServicioContrato()[0] + grupoServiciosModel.getIdGrupoServicio() + "|" +
                Constantes.getAtributosGrupoServicioContrato()[1] + grupoServiciosModel.getIdContrato() + "|"
                + Constantes.getAtributosGrupoServicioContrato()[2] + grupoServiciosModel.getGrupo() + "|"
                + Constantes.getAtributosGrupoServicioContrato()[3] + grupoServiciosModel.getIdTipoConsumo();
    }


    public String movimientoServicioContrato(Long idContrato, List<Long> idServicioContrato){

        String ids = idServicioContrato.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return  "Id contrato: "+ idContrato.toString() + "Ids de servicio contrato" + ids;
    }

    public String movimientoGruposServicioIds(Long idContrato, List<Long> idGrupoServicio){
        String ids = idGrupoServicio.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return  "Id contrato: "+ idContrato.toString() + "Ids grupos de servicio" + ids;
    }

    public String moviientoBuscarContratos(CriteriosDeBusquedaContratoDto criterios){

        return Constantes.getAtributosBuscarContratos()[0] + ( criterios.getIdEstatusContrato() == null ? "null" :  criterios.getIdEstatusContrato()) + "|" +
                Constantes.getAtributosBuscarContratos()[1] + ( criterios.getFechaInicio() == null ? "null" : criterios.getFechaInicio()) + "|" +
                Constantes.getAtributosBuscarContratos()[2] + ( criterios.getFechaTermino() == null ? "null" : criterios.getFechaTermino()) + "|" +
                Constantes.getAtributosBuscarContratos()[3] + ( criterios.getIdProveedor() == null ? "null" :  criterios.getIdProveedor()) + "|" +
                Constantes.getAtributosBuscarContratos()[4] + ( criterios.getIdAdministracionCentral() == null ? "null" :  criterios.getIdAdministracionCentral());
    }

}
