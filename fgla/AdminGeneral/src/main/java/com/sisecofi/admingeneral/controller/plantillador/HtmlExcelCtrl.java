package com.sisecofi.admingeneral.controller.plantillador;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.admingeneral.dto.plantillador.HtmlExcelDto;
import com.sisecofi.libreria.comunes.dto.plantillador.HtmlExcelListDto;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;
import com.sisecofi.admingeneral.service.plantillador.HtmlToExcelService;
import com.sisecofi.admingeneral.util.Constantes;
import com.sisecofi.admingeneral.util.ConstantesPlantillador;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class HtmlExcelCtrl {

	private final HtmlToExcelService excelService;

	@PostMapping(ConstantesPlantillador.PATH_BASE + "/excel/html-to-excel")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> vistaPreviaPlantillas(@RequestBody HtmlExcelDto dto) {
		return new ResponseEntity<>(excelService.htmlToExcel(dto.getIdSubPlantillador(), dto.getDatos()),
				HttpStatus.OK);
	}
	
	@PostMapping(ConstantesPlantillador.PATH_BASE + "/cierre-rcp/excel/html-to-excel")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> vistaPreviaPlantillasRCP(@RequestBody HtmlExcelListDto dto) {
		return new ResponseEntity<>(excelService.htmlToExcelRCP(dto.getIdSubPlantillador(), dto.getDatosGenerales(), dto.getDatos()),
				HttpStatus.OK);
	}
	@PostMapping(ConstantesPlantillador.PATH_BASE + "/cierre-rcp/html/excel")
	@ConsumoInterno
	public ResponseEntity<ByteArrayResource> cierreProyecto(@RequestBody HtmlExcelListDto inputs) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rcp-excel.xlsx");
		return new ResponseEntity<>(new ByteArrayResource(excelService.cierreProyectoExcel(inputs.getIdSubPlantillador(), inputs.getDatosGenerales(), inputs.getDatos())),
				headers, HttpStatus.OK);
	}
	
	@PostMapping(ConstantesPlantillador.PATH_BASE + "/cierre-rcp/pdf/html-to-pdf")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> vistaPreviaPlantillasRcpPdf(@RequestBody HtmlExcelListDto dto) {
		return new ResponseEntity<>(excelService.htmlToPdfRCP(dto.getIdSubPlantillador(), dto.getDatos()),
				HttpStatus.OK);
	}
	
	@PostMapping(ConstantesPlantillador.PATH_BASE + "/proforma/html/excel")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<ByteArrayResource> proforma(@RequestBody HtmlExcelListDto inputs) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=proforma-excel.xlsx");
		return new ResponseEntity<>(new ByteArrayResource(excelService.proformaExcel(inputs.getIdSubPlantillador(), inputs.getDatosGenerales(), inputs.getDatos())),
				headers, HttpStatus.OK);
	}
	
	@PostMapping(ConstantesPlantillador.PATH_BASE + "/proforma/html/pdf")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> proformaPDF(@RequestBody HtmlExcelListDto inputs) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.attachment().filename("proforma-pdf.pdf").build());
		return new ResponseEntity<>(excelService.proformaPDF(inputs.getIdSubPlantillador(), inputs.getDatosGenerales(),inputs.getDatos()), headers,
				HttpStatus.OK);
	}
}
