package com.sisecofi.proyectos.controller;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.ResponseGeneric;
import com.sisecofi.libreria.comunes.service.PathService;
import com.sisecofi.proyectos.dto.ArchivosEliminadosDto;
import com.sisecofi.proyectos.dto.EstructuraComiteActualizarArchivosDto;
import com.sisecofi.proyectos.dto.EstructuraComiteCargarArchivosDto;
import com.sisecofi.proyectos.dto.EstructuraComiteEliminarArchivosDto;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.sisecofi.proyectos.util.Constantes;
import org.springframework.web.multipart.MultipartFile;
import com.sisecofi.proyectos.service.ServicioArchivo;


@RestController
@RequestMapping("/"+Constantes.PATH_BASE)
@RequiredArgsConstructor
public class ArchivosCtrl {

    private final ServicioArchivo archivo;
    private final PathService pathService;

    @PutMapping("/cargar-archivo/{idComiteProyecto}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolLiderProyecto() ")
    public ResponseEntity<ResponseGeneric<String>>  cargarArchivo(@RequestBody MultipartFile file, @PathVariable("idComiteProyecto") Integer idProyecto) {
        String nombreArchivo = archivo.cargarArchivo(idProyecto, file);
        if (!pathService.comprobarArchivo(file)) {
            return ResponseEntity.badRequest().body(null);
        }
        ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
        data.setData(nombreArchivo);
        data.setMsj(ErroresEnum.ARCHIVO_CARGADO);

        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @PostMapping("/cargar-archivos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolLiderProyecto() ")
    public ResponseEntity<ResponseGeneric<String>>  cargarArchivos(@RequestBody EstructuraComiteCargarArchivosDto estructura) {
        String nombreArchivo = archivo.cargarArchivosComite(estructura.getArchivosCargados());
        ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
        data.setData(nombreArchivo);
        data.setMsj(ErroresEnum.ARCHIVO_CARGADO);

        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @PutMapping("/actualizar-archivos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() " +
			"|| @seguridad.validarRolAdminSistemaSecundario() " +
			"|| @seguridad.validarRolAdminMatrizDocumental() " +
			"|| @seguridad.validarRolApoyoAcppi() " +
			"|| @seguridad.validarRolApoyoAlLiderTecnicoProyeto() " +
			"|| @seguridad.validarRolLiderProyecto() ")
    public ResponseEntity<ResponseGeneric<String>>  actualizarArchivos(@RequestBody EstructuraComiteActualizarArchivosDto estructura) {
        String nombreArchivo = archivo.actualizarArchivo(estructura.getArchivosActualizados());
        ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
        data.setData(nombreArchivo);
        data.setMsj(ErroresEnum.ARCHIVO_CARGADO);

        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @PostMapping("/eliminar-archivos")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() || " +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() || " +
            "@seguridad.validarRolLiderProyecto() || " +
            "@seguridad.validarRolGestorDocumentalContrato() || " +
            "@seguridad.validarRolAdministradorContrato() || " +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() || " +
            "@seguridad.validarRolParticipantesAdmonDictamen() || " +
            "@seguridad.validarRolVerificadorGeneral()")
    public ResponseEntity<ResponseGeneric<String>>  eliminarArchivos(@RequestBody EstructuraComiteEliminarArchivosDto estructura) {
        String nombreArchivo = archivo.eliminarArchivos(estructura.getArchivosEliminados());
        ResponseGeneric<String> data = ResponseGeneric.<String>builder().build();
        data.setData(nombreArchivo);
        data.setMsj(ErroresEnum.ARCHIVO_CARGADO);

        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @PutMapping("/eliminar")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() || " +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() || " +
            "@seguridad.validarRolLiderProyecto() || " +
            "@seguridad.validarRolGestorDocumentalContrato() || " +
            "@seguridad.validarRolAdministradorContrato() || " +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() || " +
            "@seguridad.validarRolParticipantesAdmonDictamen() || " +
            "@seguridad.validarRolVerificadorGeneral()")
    public ResponseEntity<String> eliminar(@RequestParam("path") ArchivosEliminadosDto archivosEliminadosDto){
        return new ResponseEntity<>(archivo.eliminarArchivo(archivosEliminadosDto), HttpStatus.CREATED);
    }

    @PutMapping("/descargar-archivo")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || " +
            "@seguridad.validarRolAdminSistemaSecundario() || " +
            "@seguridad.validarRolAdminMatrizDocumental() || " +
            "@seguridad.validarRolApoyoAcppi() || " +
            "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() || " +
            "@seguridad.validarRolLiderProyecto() || " +
            "@seguridad.validarRolGestorDocumentalContrato() || " +
            "@seguridad.validarRolAdministradorContrato() || " +
            "@seguridad.validarRolParticipantesAdmonEstimaciones() || " +
            "@seguridad.validarRolParticipantesAdmonDictamen() || " +
            "@seguridad.validarRolVerificadorGeneral()")
    public ResponseEntity<String> descargar(@RequestParam("path") String path){
        return new ResponseEntity<>(archivo.descargarArchivo(path, null), HttpStatus.OK);
    }

    @PutMapping("/descargar-folder")
    @PreAuthorize(
    	    "@seguridad.validarRolAdminSistema() || " +
    	    "@seguridad.validarRolAdminSistemaSecundario() || " +
    	    "@seguridad.validarRolAdminMatrizDocumental() || " +
    	    "@seguridad.validarRolApoyoAcppi() || " +
    	    "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() || " +
    	    "@seguridad.validarRolLiderProyecto() || " +
    	    "@seguridad.validarRolGestorDocumentalContrato() || " +
    	    "@seguridad.validarRolAdministradorContrato() || " +
    	    "@seguridad.validarRolParticipantesAdmonEstimaciones() || " +
    	    "@seguridad.validarRolParticipantesAdmonDictamen() || " +
    	    "@seguridad.validarRolVerificadorGeneral()"
    	)
    public ResponseEntity<String> descargarFolder(@RequestParam("path") String path){
        return new ResponseEntity<>(archivo.desgargarFolderComite(path), HttpStatus.OK);
    }

    @PutMapping("/descargar-folder-sat-cloud")
    @PreAuthorize(
    	    "@seguridad.validarRolAdminSistema() || " +
    	    "@seguridad.validarRolAdminSistemaSecundario() || " +
    	    "@seguridad.validarRolAdminMatrizDocumental() || " +
    	    "@seguridad.validarRolApoyoAcppi() || " +
    	    "@seguridad.validarRolApoyoAlLiderTecnicoProyeto() || " +
    	    "@seguridad.validarRolLiderProyecto() || " +
    	    "@seguridad.validarRolGestorDocumentalContrato() || " +
    	    "@seguridad.validarRolAdministradorContrato() || " +
    	    "@seguridad.validarRolParticipantesAdmonEstimaciones() || " +
    	    "@seguridad.validarRolParticipantesAdmonDictamen() || " +
    	    "@seguridad.validarRolVerificadorGeneral()"
    	)
    public ResponseEntity<CarpetaCompartidaDto> descargarFolderSatCloud(@RequestParam("path") String path) {
        return new ResponseEntity<>(archivo.descargarFolderSatCloudComites(path), HttpStatus.OK);
    }
    
    
    @PutMapping("/descargar-archivo-tablero")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> descargarTablero(@RequestParam("path") String path){
        return new ResponseEntity<>(archivo.descargarArchivo(path, null), HttpStatus.OK);
    }

    @PutMapping("/descargar-folder-tablero")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<String> descargarFolderTablero(@RequestParam("path") String path){
        return new ResponseEntity<>(archivo.descargarFolder(path), HttpStatus.OK);
    }

    @PutMapping("/descargar-folder-sat-cloud-tablero")
	@PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolGestorTitutulos() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<CarpetaCompartidaDto> descargarFolderSatCloudTablero(@RequestParam("path") String path) {
        return new ResponseEntity<>(archivo.descargarFolderSatCloud(path), HttpStatus.OK);
    }
}
