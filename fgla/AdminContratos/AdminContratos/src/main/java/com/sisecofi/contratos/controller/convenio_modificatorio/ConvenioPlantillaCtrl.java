package com.sisecofi.contratos.controller.convenio_modificatorio;

import com.sisecofi.libreria.comunes.model.gestionDocumental.asociaciones.contratos.ConvenioPlantilla;
import com.sisecofi.contratos.service.carpetas.ServicioGestionDocumental;
import com.sisecofi.contratos.service.convenio_modificatorio.ServicioConvenioPlantilla;
import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.libreria.comunes.dto.carpeta.CarpetaDtoResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("/"+ Constantes.PATH_BASE)
@RequiredArgsConstructor
@RestController
public class ConvenioPlantillaCtrl {

    private final ServicioConvenioPlantilla servicioConvenioPlantilla;
    private final ServicioGestionDocumental servicioGestionDocumental;

    
    @PostMapping("/"+Constantes.PATH_BASE_CONVENIO+"/asociar-plantilla/{idConvenio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolGestorDocumentalContrato()")
    public ResponseEntity<Boolean> crearPena(@Valid @RequestBody ConvenioPlantilla request, @PathVariable Long idConvenio) {
        return new ResponseEntity<>(servicioConvenioPlantilla.asociarPlantillas(request, idConvenio), HttpStatus.OK);
    }
    
    @GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/obtener-asociaciones/{idConvenio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<ConvenioPlantilla>> obtenerAsociaciones(@PathVariable Long idConvenio) {
        return new ResponseEntity<>(servicioConvenioPlantilla.obtenerAsociaciones(idConvenio), HttpStatus.OK);
    }
    
    @PutMapping("/"+Constantes.PATH_BASE_CONVENIO+"/eliminar-asociaciones")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolVerificadorGeneral()")
    public ResponseEntity<Boolean> eliminarAsociacionPlantillas(@RequestBody List<Long> ids) {
        return new ResponseEntity<>(servicioConvenioPlantilla.eliminarAsociacionPlantillas(ids), HttpStatus.OK);
    }
    
    @SuppressWarnings("rawtypes")
	@GetMapping("/"+Constantes.PATH_BASE_CONVENIO+"/obtener-estructura-documental/{idConvenio}")
    @PreAuthorize("@seguridad.validarRolAdminSistema() || @seguridad.validarRolAdminSistemaSecundario() || @seguridad.validarRolAdminMatrizDocumental() || @seguridad.validarRolApoyoAcppi() || @seguridad.validarRolApoyoAlLiderTecnicoProyeto() || @seguridad.validarRolGestorDocumentalContrato() || @seguridad.validarRolUsuarioConsulta() || @seguridad.validarRolLiderProyecto() || @seguridad.validarRolAdministradorContrato() || @seguridad.validarRolParticipantesAdmonEstimaciones() || @seguridad.validarRolParticipantesAdmonDictamen() || @seguridad.validarRolParticipantesAdmonVerificacion() || @seguridad.validarRolVerificadorGeneral() || @seguridad.validarRolVerificadorEspecificoContrato()")
    public ResponseEntity<List<CarpetaDtoResponse>> obtenerEstructuraDocumental(@PathVariable Long idConvenio) {
        return new ResponseEntity<>(servicioGestionDocumental.obtenerEstructuraDocumentalConvenio(idConvenio), HttpStatus.OK);
    }
    

}
