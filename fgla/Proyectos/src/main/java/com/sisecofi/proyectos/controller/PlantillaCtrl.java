package com.sisecofi.proyectos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.dto.plantilla.PlantillaDto;
import com.sisecofi.libreria.comunes.model.plantilla.PlantillaVigenteModel;
import com.sisecofi.proyectos.dto.PlantillaVigenteModelDto;
import com.sisecofi.proyectos.service.ServicioPlantilla;
import com.sisecofi.proyectos.util.Constantes;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
public class PlantillaCtrl {

    private final ServicioPlantilla servicioPlantilla;

    @SuppressWarnings("rawtypes")
	@GetMapping("/plantilla/{idPlantilla}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolParticipantesAdmonVerificacion()")
    public ResponseEntity<PlantillaDto>  obtenerPlantillaPorId(@PathVariable("idPlantilla") Integer idPlantilla) {
        return new ResponseEntity<>(servicioPlantilla.obtenerPlantillaPorId(idPlantilla), HttpStatus.OK);
    }

    @GetMapping("/plantilla/platilla-vigente")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta()|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<PlantillaVigenteModel>>  obtenerPlantillasVigentes() {

        return new ResponseEntity<>(servicioPlantilla.obtenerPlantillas(), HttpStatus.OK);
    }
    
    @GetMapping("/plantilla-fase/{idFaseProyecto}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolParticipantesAdmonVerificacion()")
    public ResponseEntity<List<PlantillaVigenteModel>>  obtenerPlantillaPorFase(@PathVariable Integer idFaseProyecto) {
        return new ResponseEntity<>(servicioPlantilla.plantillasPorFase(idFaseProyecto), HttpStatus.OK);
    }

    @GetMapping("/plantilla-carpetas/{idComiteProyecto}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta()|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<PlantillaVigenteModelDto>  obtenerEstructura(@PathVariable Integer idComiteProyecto) {
        return new ResponseEntity<>(servicioPlantilla.obtenerEstructura(idComiteProyecto), HttpStatus.OK);
    }

    @GetMapping("/plantillas-carpetas/{idPlantillaVigente}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolUsuarioConsulta()|| @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<PlantillaVigenteModelDto>  obtenerEstructuraPorIdPlantilla(@PathVariable Integer idPlantillaVigente) {
        return new ResponseEntity<>(servicioPlantilla.obtenerEstructuraPorIdPlantilla(idPlantillaVigente), HttpStatus.OK);
    }
}
