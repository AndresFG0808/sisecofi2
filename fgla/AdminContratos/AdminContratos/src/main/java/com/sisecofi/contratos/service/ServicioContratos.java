package com.sisecofi.contratos.service;

import com.sisecofi.contratos.dto.*;
import com.sisecofi.libreria.comunes.dto.contrato.ConsultaContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoConvenioModDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDtoLigeroComun;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoProveedorDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto;
import com.sisecofi.libreria.comunes.dto.contrato.ProveedorDto;
import com.sisecofi.libreria.comunes.dto.proyecto.ProyectoSimpleDto;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ServicioContratos {

   ContratoDto obtenerContratoPorId(Long idContrato);

   List<ContratoModel> obtenerInfoContratos();

   Page<ContratoDtoLigero> buscarContratos(CriteriosDeBusquedaContratoDto criteriosDto);

   List<ContratoDtoLigero> buscarContratosReporte(CriteriosDeBusquedaContratoDto criterios);

   String actualizarContrato(ActualizarContratoDto contratoDto);

   String cancelarContrato(Long idContrato);

   ContratoDto iniciarContrato(InicialContratoDto inicialContratoDto);

   String ejecutarContrato(Long idContrato);

   Boolean esContratoInicial(Long idContrato);

   List<ProveedorDto> obtenerProovedores(Long idContrato);
   
   List<ContratoSimpleDto> obtenerContratosVig(int rol);
   
   List<ContratoSimpleDto> obtenerContratosNoVig(int rol);
   
   List<ContratoSimpleDto> obtenerContratosModel(int rol);

   List<ProyectoSimpleDto> obtenerProyectos();

   ContratoProveedorDto obtenerContratoProveedor(Long idContrato, Long idProveedor);
   
   ContratoConvenioModDto obtenerContratoNombreCorto(String nombreCorto);

   String reegresarInicial(Long idContrato);

   List<Archivo> obtenerArchivosSeccion(Long idProyecto);

   String regresarEstatusInicialContrato(Long idContrato);

   ConvenioModificatorioModel obtenerConvenio(String numero);

   String cierreContrato(CierreContratoDto contratoDto);
   
   List<ContratoSimpleDto> obtenerContratosVigencia(ConsultaContratoDto dto);

   String cierreEstatusContrato(Long idContrato);

String utlimaMod(Long idContrato);

List<ContratoNombreDto> obtenerInfoContratosDto();

List<ProyectoSimpleDto> obtenerProyectosCompletos();

List<ContratoNombreDto> obtenerInfoContratosDtoProyecto(Long idProyecto);

ContratoDtoLigeroComun obtenerContratoDtoLigero(Long idContrato);
}
