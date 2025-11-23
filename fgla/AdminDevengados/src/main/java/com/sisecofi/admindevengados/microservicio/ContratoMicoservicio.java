package com.sisecofi.admindevengados.microservicio;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.sisecofi.libreria.comunes.dto.ActualizarTipoCambioDto;
import com.sisecofi.libreria.comunes.dto.contrato.ConsultaContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoConvenioModDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoDtoLigeroComun;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoProveedorDto;
import com.sisecofi.libreria.comunes.dto.contrato.ContratoSimpleDto;
import com.sisecofi.libreria.comunes.dto.contrato.DatosGeneralesResponseDto;
import com.sisecofi.libreria.comunes.dto.contrato.ProveedorDto;
import com.sisecofi.libreria.comunes.dto.contrato.VigenciaMontoDto;
import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesServiciosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;
import com.sisecofi.libreria.comunes.model.contratos.NivelesServicioSLAModel;
import com.sisecofi.libreria.comunes.model.contratos.PenaContractualContratoModel;
import com.sisecofi.libreria.comunes.model.convenioModificatorio.ConvenioModificatorioModel;

import jakarta.validation.Valid;

@FeignClient(value = "contratoholder", url = "${url.contratos}")
public interface ContratoMicoservicio {
	
	@GetMapping("/admin-contratos-internos/obtener-contratos")
    List<ContratoModel> obtenerContratos();
	
	@GetMapping("/admin-contratos-internos/obtener-contrato/{idContrato}")
	ContratoDto obtenerContratoId(@PathVariable("idContrato") Long idContrato);
	
	@GetMapping("/admin-contratos-internos/obtener-contrato-ligero/{idContrato}")
	ContratoDtoLigeroComun obtenerContratoIdLigero(@PathVariable("idContrato") Long idContrato);
	
	@GetMapping("/admin-contratos-internos/datos-generales/{idContrato}")
	DatosGeneralesResponseDto obtenerDatosGenerales(@PathVariable("idContrato") Long idContrato);

	@PutMapping("/admin-contratos-internos/actualizar-contrato")
	String actuializarTipoCmbioContrato(@Valid @RequestBody ActualizarTipoCambioDto actualizarTipoCambioDto);
	
	@GetMapping("/admin-contratos-internos/obtener-proovedores/{idContrato}")
	List<ProveedorDto> obtenerProvedoresP(@PathVariable("idContrato") Long idContrato);
	
	@GetMapping("/admin-contratos-internos/contratos-vigentes/{rol}")
	List<ContratoSimpleDto> obtenerContratosVig(@PathVariable("rol") int rol);
	
	@GetMapping("/admin-contratos-internos/contratos-no-vigentes/{rol}")
	List<ContratoSimpleDto> obtenerContratosNoVig(@PathVariable("rol") int rol);
	
	@GetMapping("/admin-contratos-internos/todos-los-contratos/{rol}")
	List<ContratoSimpleDto> obtenerContratosModel(@PathVariable("rol") int rol);
	
	@GetMapping("/admin-contratos-internos/vigencia-monto/{idContrato}")
	VigenciaMontoDto obtenerVigenciaMonto(@PathVariable("idContrato") Long idContrato);
	
	@GetMapping("/admin-contratos-internos/obtener-contrato-proveedor/{idContrato}/{idProveedor}")
	ContratoProveedorDto obtenerContratoProveedor(@PathVariable("idContrato") Long idContrato, @PathVariable("idProveedor") Long idProveedor);
	
	@GetMapping("/admin-contratos-internos/obtener-contrato-nombre/{nombreCorto}")
	ContratoConvenioModDto obtenerContratoNombreCorto(@PathVariable("nombreCorto") String nombreCorto);
	
	@GetMapping("/consultar-informes-documentales-servicios/{idContrato}")
	List<InformesDocumentalesServiciosModel> obtenerTodosInformesDocumentalesServicios(@PathVariable Long idContrato);
	
    @GetMapping("/consultar-informes-documentales-periodicos/{id_contrato}")
	List<InformesDocumentalesPeriodicosModel> obtenerTodosInformesDocumentalesPeriodicos(@PathVariable("id_contrato") Long id);
    
    @GetMapping("/consultar-informes-documentales/{id_contrato}")
    List<InformesDocumentalesUnicaVezModel> obtenerTodosInformesDocumentales(@PathVariable("id_contrato") Long id);
    
    @GetMapping("/obtener-penas-contractuales/{idContrato}")
    List<PenaContractualContratoModel> obtenerPenas(@PathVariable Long idContrato);
    
    @GetMapping("/consultar-niveles-servicio-sla/{id_contrato}")
    List<NivelesServicioSLAModel> obtenerTodosNivelesServicioSLA(@PathVariable("id_contrato") Long id);
    
    @GetMapping("/admin-contratos-internos/obtener-atraso-prestacion/{id_contrato}")
    List<AtrasoPrestacionModel> obtenerAtrasoPrestacion(@PathVariable("id_contrato") Long id);
    
    @GetMapping("/admin-contratos-internos/obtener-convenio/{numero}")
    ConvenioModificatorioModel obtenerConvenio(@PathVariable("numero") String numero);
    
    @PostMapping("/admin-contratos-internos/consulta-de-contratos")
    List<ContratoModel> obtenerContratosVigencia(@RequestBody ConsultaContratoDto dto);
    
    @GetMapping("/admin-contratos-internos/consultar-informe-documental-servicio/{idInforme}")
    InformesDocumentalesServiciosModel obtenerInformeDocumental(@PathVariable("idInforme") Long idInforme);
    
    @GetMapping("/admin-contratos-internos/consultar-informe-documental-periodico/{idInforme}")
    InformesDocumentalesPeriodicosModel obtenerInformeDocumentalPeriodico(@PathVariable("idInforme") Long idInforme);
    
    @GetMapping("/admin-contratos-internos/consultar-informe-documental-uv/{idInforme}")
    InformesDocumentalesUnicaVezModel obtenerInformeDocumentalUv(@PathVariable("idInforme") Long idInforme);
    
    @GetMapping("/admin-contratos-internos/obtener-atraso-prestacion-individual/{idAtraso}")
    AtrasoPrestacionModel obtenerAtrasoPrestacionIndividual(@PathVariable("idAtraso") Long idAtraso);
}
