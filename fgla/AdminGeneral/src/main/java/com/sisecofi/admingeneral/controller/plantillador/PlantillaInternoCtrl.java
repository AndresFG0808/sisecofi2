package com.sisecofi.admingeneral.controller.plantillador;

import com.sisecofi.libreria.comunes.dto.plantillador.NotificacionDTO;
import com.sisecofi.admingeneral.service.notificacion.NotificacionPagoService;
import com.sisecofi.libreria.comunes.dto.plantillador.HtmlExcelListDto;
import com.sisecofi.libreria.comunes.model.plantillador.SubPlantilladorModel;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;
import com.sisecofi.admingeneral.service.plantillador.HtmlToExcelService;
import com.sisecofi.libreria.comunes.dto.plantillador.ContenidoPlantilladorPdfReponseDto;
import com.sisecofi.admingeneral.dto.plantillador.RequestPlantilla;
import com.sisecofi.admingeneral.service.plantillador.HtmlWordService;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoBase;
import com.sisecofi.libreria.comunes.model.plantillador.ContenidoPlantilladorModel;
import com.sisecofi.admingeneral.service.plantillador.PlantillaService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesPlantillador;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
@Slf4j
public class PlantillaInternoCtrl {

    private final PlantillaService plantillaService;
    private final HtmlWordService htmlWordService;
    private final HtmlToExcelService excelService;
    private final NotificacionPagoService notificacionPagoService;

    @GetMapping(ConstantesPlantillador.PATH_BASE_INTERNO + "/contenido-plantillador-test/{idContenidoPlantillador}")
    @ConsumoInterno
    public ResponseEntity<ContenidoPlantilladorModel> obtenerContenidoPlantillador(@PathVariable("idContenidoPlantillador") Long idPlantillador) {
        return new ResponseEntity<>(plantillaService.obtenerContenidoPlantilladorPorId(idPlantillador), HttpStatus.OK);
    }

    @PostMapping(ConstantesPlantillador.PATH_BASE_INTERNO +"/plantillador/generar-pdf")
    @ConsumoInterno
    public ResponseEntity<byte[]> generarPdf(@RequestBody RequestPlantilla plantilla) {
        return new ResponseEntity<>(htmlWordService.convertirHmltPdf(plantilla), HttpStatus.OK);
    }

    @GetMapping( ConstantesPlantillador.PATH_BASE_INTERNO+"/obtener-documento/{idContenidoPlantillador}")
    @ConsumoInterno
    public ResponseEntity<ContenidoPlantilladorPdfReponseDto> obtenerDoc(@PathVariable("idContenidoPlantillador") Long idContenidoPlantillador){
        return new ResponseEntity<>(htmlWordService.obtenerContenidoPlantilladorDoc(idContenidoPlantillador), HttpStatus.OK);
    }

    @GetMapping( ConstantesPlantillador.PATH_BASE_INTERNO+"/obtener-plantillador/{idTipoPlantillador}")
    @ConsumoInterno
    public ResponseEntity<List<PlantilladorModel>> obtenerPlantilladorPorId(@PathVariable("idTipoPlantillador") Integer idTipoPlantillador){
        return new ResponseEntity<>(plantillaService.obtenerPlantilladoresPorTipoPlantillador(idTipoPlantillador), HttpStatus.OK);
    }
    
    @GetMapping( ConstantesPlantillador.PATH_BASE_INTERNO+"/plantillador/{idPlantillador}")
    @ConsumoInterno
    public ResponseEntity<PlantilladorModel> obtenerPlantilladoresPorIdPlantillador(@PathVariable("idPlantillador") Long idPlantillador){
        return new ResponseEntity<>(plantillaService.obtenerPlantilladoresPorIdPlantillador(idPlantillador), HttpStatus.OK);
    }

    @GetMapping(ConstantesPlantillador.PATH_BASE_INTERNO + "/contenido-plantillador")
    @ConsumoInterno
    public ResponseEntity<ContenidoBase> obtenerContenidoPlantilladorPorIdPlantillador(@RequestParam(value = "idPlantillador", required = false) Long idPlantillador,
			@RequestParam(value = "idContenidoSubPlantillador", required = false) Long idContenidoSubPlantillador) {
        return new ResponseEntity<>(plantillaService.obtenerContenidoPlantillador(idPlantillador, idContenidoSubPlantillador), HttpStatus.OK);
    }

    @GetMapping(ConstantesPlantillador.PATH_BASE_INTERNO + "/contenido-sub-plantillador")
    @ConsumoInterno
    public ResponseEntity<List<SubPlantilladorModel>> obtenerContenidoPlantilladorPorIdPlantillador() {
        return new ResponseEntity<>(plantillaService.obtenerSubPlantilladores(), HttpStatus.OK);
    }

    @PostMapping(ConstantesPlantillador.PATH_BASE_INTERNO + "/proforma/html/pdf")
    @ConsumoInterno
    public ResponseEntity<byte[]> proformaPDF(@RequestBody HtmlExcelListDto inputs) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("proforma-pdf.pdf").build());
        return new ResponseEntity<>(excelService.proformaPDF(inputs.getIdSubPlantillador(), inputs.getDatosGenerales(),inputs.getDatos()), headers,
                HttpStatus.OK);
    }

    @PostMapping(ConstantesPlantillador.PATH_BASE_INTERNO + "/proforma/html/excel")
    @ConsumoInterno
    public ResponseEntity<byte[]> proforma(@RequestBody HtmlExcelListDto inputs) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("proforma-excel.xlsx").build());
        return new ResponseEntity<>(excelService.proformaExcel(inputs.getIdSubPlantillador(), inputs.getDatosGenerales(),inputs.getDatos()), headers,
                HttpStatus.OK);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(ConstantesPlantillador.PATH_BASE_INTERNO +"/notificacion/pago")
    @ConsumoInterno
    public ResponseEntity<byte[]> generarNotificacionPago(@RequestBody NotificacionDTO request) {
        log.info("inicia generar la notificacion de pago");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("notificacion." + request.getTipoDocumento().toLowerCase()).build());
        var response = new ResponseEntity(notificacionPagoService.generarDocumento(request), headers, HttpStatus.OK);
        log.info("finaliza generar la notificacion de pago");
        return response;
    }

}
