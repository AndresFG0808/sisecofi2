package com.sisecofi.contratos.controller;

import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import com.sisecofi.contratos.service.*;
import com.sisecofi.contratos.service.carpetas.ServicioGestionDocumental;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;
import com.sisecofi.libreria.comunes.dto.contrato.*;
import com.sisecofi.libreria.comunes.dto.reportedinamico.ContratoNombreDto;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;
import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class ContratosInternoCtr {
    private final ServicioContratos contratos;
    private final ServicioSeccionesContrato seccionesContrato;
    private final ServicioInformesDocumentalesPeriodicos servicioDocPeriodicos;
    private final ServicioInformesDocumentalesServicios servicioDosServicios;
    private final ServicioDatosGeneralesContrato servicioDatosGeneralesContrato;
    private final ServicioNivelesServicioSLA servicioNivelesServicioSla;
    private final ServicioPenasContractuales servicioPenasContractuales;
    private final ServicioSeccionAtrasoPresentacion servicioAtrasoPresentacion;
    private final ServicioGestionDocumental servicioGestionDocumental;
    private final ServicioInformesDocumentalesUnicaVez servicioInformesDocumentalesUnicaVez;
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/obtener-contratos")
    @ConsumoInterno
    public ResponseEntity<List<ContratoNombreDto>> obtenerContratos() {
        return new ResponseEntity<>(contratos.obtenerInfoContratosDto(), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/obtener-contratos/{idProyecto}")
    @ConsumoInterno
    public ResponseEntity<List<ContratoNombreDto>> obtenerContratosIdProyecto(@PathVariable Long idProyecto) {
        return new ResponseEntity<>(contratos.obtenerInfoContratosDtoProyecto(idProyecto), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/obtener-contrato/{idContrato}")
    @ConsumoInterno
    public ResponseEntity<ContratoDto> obtenerContratosPorId(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(contratos.obtenerContratoPorId(idContrato), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/obtener-contrato-ligero/{idContrato}")
    @ConsumoInterno
    public ResponseEntity<ContratoDtoLigeroComun> obtenerContratosPorIdLigero(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(contratos.obtenerContratoDtoLigero(idContrato), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/obtener-proovedores/{idContrato}")
    @ConsumoInterno
    public ResponseEntity<List<ProveedorDto>> obtenerProvedoresP(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(contratos.obtenerProovedores(idContrato), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/contratos-vigentes/{rol}")
    @ConsumoInterno
    public ResponseEntity<List<ContratoSimpleDto>> vigentes(@PathVariable("rol") int rol) {

        return new ResponseEntity<>(contratos.obtenerContratosVig(rol), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/contratos-no-vigentes/{rol}")
    @ConsumoInterno
    public ResponseEntity<List<ContratoSimpleDto>> noVigentes(@PathVariable("rol") int rol) {
        return new ResponseEntity<>(contratos.obtenerContratosNoVig(rol), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/todos-los-contratos/{rol}")
    @ConsumoInterno
    public ResponseEntity<List<ContratoSimpleDto>> obtenerContratosModel(@PathVariable("rol") int rol) {
        return new ResponseEntity<>(contratos.obtenerContratosModel(rol), HttpStatus.OK);
    }
    
    @PostMapping("/" + Constantes.PATH_BASE_INTERNO + "/consulta-de-contratos")
    @ConsumoInterno              
    public ResponseEntity<List<ContratoSimpleDto>> obtenerContratosVigencia(@RequestBody ConsultaContratoDto dto) {
        return new ResponseEntity<>(contratos.obtenerContratosVigencia(dto), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO +"/vigencia-montos")
    @ConsumoInterno
    public ResponseEntity<List<VigenciaMontoDto>> obtenerVigenciaMontos() {
        return new ResponseEntity<>(seccionesContrato.obtenerVigenciaMontos(), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/vigencia-monto/{idContrato}")
    @ConsumoInterno
    public ResponseEntity<VigenciaMontoDto> obtenerVigenciaMonto(@PathVariable("idContrato") Long idContrato) {
        return new ResponseEntity<>(seccionesContrato.obtenerVigenciaMonto(idContrato), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/consultar-informes-documentales/{id_contrato}")
    @ConsumoInterno
    public ResponseEntity<List<InformesDocumentalesUnicaVezModel>> obtenerTodosInformesDocumentales(@PathVariable("id_contrato") Long id) {
        return new ResponseEntity<>(seccionesContrato.obtenerInformesDocumentalesUnicaVez(id), HttpStatus.OK);
    }        
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/obtener-contrato-proveedor/{idContrato}/{idProveedor}")
    @ConsumoInterno
    public ResponseEntity<ContratoProveedorDto> obtenerContratoProveedor(@PathVariable("idContrato") Long idContrato, @PathVariable("idProveedor") Long idProveedor) {
        return new ResponseEntity<>(contratos.obtenerContratoProveedor(idContrato, idProveedor), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/obtener-contrato-nombre/{nombreCorto}")
    @ConsumoInterno
    public ResponseEntity<ContratoConvenioModDto> obtenerContratoNombreCorto(@PathVariable("nombreCorto") String nombreCorto) {
        return new ResponseEntity<>(contratos.obtenerContratoNombreCorto(nombreCorto), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/consultar-informes-documentales-periodicos/{id_contrato}")
    @ConsumoInterno
    public ResponseEntity<List<InformesDocumentalesPeriodicosModel>> obtenerTodosInformesDocumentalesPeriodicos(@PathVariable("id_contrato") Long id) {
        return new ResponseEntity<>(servicioDocPeriodicos.obtenerInformesDocumentalesPeriodicos(id), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/consultar-informes-documentales-servicios/{id_contrato}")
    @ConsumoInterno
    public ResponseEntity<List<InformesDocumentalesServiciosModel>> obtenerTodosInformesDocumentalesServicios(@PathVariable("id_contrato") Long id) {
        return new ResponseEntity<>(servicioDosServicios.obtenerInformesDocumentalesServicios(id), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/datos-generales/{id_contrato}")
    @ConsumoInterno
    public ResponseEntity<DatosGeneralesResponseDto> obtenerDatosGenerales(@PathVariable("id_contrato") Long id) {
        return new ResponseEntity<>( servicioDatosGeneralesContrato.obtenerDatosGnerales(id), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/participantes/{id_contrato}")
    @ConsumoInterno
    public ResponseEntity<List<ParticipantesAdminContratoDto>> obtenerParticipantes(@PathVariable("id_contrato") Long id) {
        return new ResponseEntity<>( servicioDatosGeneralesContrato.obtenerParticipantesAdminContrato(id), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/consultar-niveles-servicio-sla/{id_contrato}")
    @ConsumoInterno
    public ResponseEntity<List<NivelesServicioSLAModel>> obtenerTodosNivelesServicioSLA(@PathVariable("id_contrato") Long id) {
        return new ResponseEntity<>(servicioNivelesServicioSla.obtenerNivelesServicioSLA(id), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO +"/obtener-penas-contractuales/{idContrato}")
    @ConsumoInterno
    public ResponseEntity<List<PenaContractualContratoModel>> obtenerPenas(@PathVariable Long idContrato){
        return new ResponseEntity<>(servicioPenasContractuales.obtenerPenas(idContrato), HttpStatus.OK);
    }

    @GetMapping("/" + Constantes.PATH_BASE_INTERNO +"/obtener-atraso-prestacion/{idContrato}")
    @ConsumoInterno
    public ResponseEntity<List<AtrasoPrestacionModel>> obtenerAtrasoPrestacion(@PathVariable Long idContrato){
        return new ResponseEntity<>(servicioAtrasoPresentacion.obtenerAtrasosPrestaciones(idContrato), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO +"/obtener-archivos-contratos/{idProyecto}")
    @ConsumoInterno
    public ResponseEntity<List<Archivo>> obtenerArchivosSeccion(@PathVariable Long idProyecto){
        return new ResponseEntity<>(contratos.obtenerArchivosSeccion(idProyecto), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO +"/obtener-convenio/{numero}")
    @ConsumoInterno
    public ResponseEntity<ConvenioModificatorioModel> obtenerConvenio(@PathVariable String numero){
        return new ResponseEntity<>(contratos.obtenerConvenio(numero), HttpStatus.OK);
    }
    
    
    @SuppressWarnings("rawtypes")
	@GetMapping("/" + Constantes.PATH_BASE_INTERNO +"/matriz-documental-contrato/{idContrato}")
    @ConsumoInterno
    public ResponseEntity<List<CarpetaDtoResponse>> obtenerEstructuraDocumentalContrato(@PathVariable Long idContrato) {
		return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocumental(idContrato), HttpStatus.OK);
	}
    
    @SuppressWarnings("rawtypes")
	@GetMapping("/"+Constantes.PATH_BASE_INTERNO+"/matriz-documental-convenio/{idConvenio}")
    @ConsumoInterno
    public ResponseEntity<List<CarpetaDtoResponse>> obtenerEstructuraDocumentalConvenio(@PathVariable Long idConvenio) {
        return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocumentalConvenio(idConvenio), HttpStatus.OK);
    }
    
    @SuppressWarnings("rawtypes")
    @ConsumoInterno
    @GetMapping("/"+Constantes.PATH_BASE_INTERNO+ "/matriz-documental-reintegro/{idContrato}")
    public ResponseEntity<List<CarpetaDtoResponse>> obtenerEstructuraDocumentalReintegros(@PathVariable Long idContrato) {
        return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocReintegros(idContrato), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/consultar-informe-documental-servicio/{idInforme}")
    @ConsumoInterno
    public ResponseEntity<InformesDocumentalesServiciosModel> obtenerInformeDocumental(@PathVariable("idInforme") Long idInforme) {
        return new ResponseEntity<>(servicioDosServicios.obtenerInformeDocumental(idInforme), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/consultar-informe-documental-periodico/{idInforme}")
    @ConsumoInterno
    public ResponseEntity<InformesDocumentalesPeriodicosModel> obtenerInformeDocumentalPeriodico(@PathVariable("idInforme") Long idInforme) {
        return new ResponseEntity<>(servicioDocPeriodicos.obtenerInformeDocumentalPeriodico(idInforme), HttpStatus.OK);
    }
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO + "/consultar-informe-documental-uv/{idInforme}")
    @ConsumoInterno
    public ResponseEntity<InformesDocumentalesUnicaVezModel> obtenerInformeDocumentalUv(@PathVariable("idInforme") Long idInforme) {
        return new ResponseEntity<>(servicioInformesDocumentalesUnicaVez.obtenerInformeDocumentalUv(idInforme), HttpStatus.OK);
    }   
    
    @GetMapping("/" + Constantes.PATH_BASE_INTERNO +"/obtener-atraso-prestacion-individual/{idAtraso}")
    @ConsumoInterno
    public ResponseEntity<AtrasoPrestacionModel> obtenerAtrasoPrestacionIndividual(@PathVariable("idAtraso") Long idAtraso){
        return new ResponseEntity<>(servicioAtrasoPresentacion.obtenerAtrasoPrestacionIndividual(idAtraso), HttpStatus.OK);
    }

}
