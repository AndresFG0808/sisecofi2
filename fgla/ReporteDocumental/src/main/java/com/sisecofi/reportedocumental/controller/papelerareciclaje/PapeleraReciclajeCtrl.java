package com.sisecofi.reportedocumental.controller.papelerareciclaje;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisecofi.libreria.comunes.dto.CompartidoCloudModel;
import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.papelera.PapeleraModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.anotaciones.ConsumoInterno;
import com.sisecofi.reportedocumental.dto.papelera.BusquedaPapeleraDto;
import com.sisecofi.reportedocumental.dto.papelera.PagePapelera;
import com.sisecofi.reportedocumental.service.controldocumental.UsuarioService;
import com.sisecofi.reportedocumental.service.papelerareciclaje.PapeleraService;
import com.sisecofi.reportedocumental.util.Constantes;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author omartinezj
 *
 */
@RestController
@RequestMapping("/" + Constantes.PATH_BASE)
@RequiredArgsConstructor
public class PapeleraReciclajeCtrl {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PapeleraService papeleraService;
	
	/**
	 * Envía el archivo eliminado a la Papelera en BD y SAT Cloud
	 * @param archivos Lista de Archivos eliminados
	 * @return
	 */
	@DeleteMapping(Constantes.PATH_BASE_INTERNO + "/papelera-reciclaje/enviar-papelera")
	@ConsumoInterno
	public ResponseEntity<Boolean> enviarPapelera(@RequestBody List<Archivo> archivos) {
		return new ResponseEntity<>(papeleraService.eliminarArchivos(archivos),
				org.springframework.http.HttpStatus.OK);
	}
	
	/**
	 * Obtiene Usuarios SAT que han enviado archivos a la Papelera
	 * @return
	 */
	@GetMapping("/papelera-reciclaje/usuarioSAT")
    @PreAuthorize("@seguridad.validarRolAdminSistema()")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return new ResponseEntity<>(usuarioService.obtenerUsuariosSAT(), HttpStatus.OK);
    }
	
	/**
	 * Busca archivos guardados en la Papelera de Reciclaje
	 * @param papeleraDto
	 * @return
	 */
	
	@PostMapping("/papelera-reciclaje/buscar-papelera")
    @PreAuthorize("@seguridad.validarRolAdminSistema()")
    public ResponseEntity<PagePapelera> buscarPapelera(@RequestBody BusquedaPapeleraDto papeleraDto) {
		return new ResponseEntity<>(papeleraService.obtenerReporte(papeleraDto), HttpStatus.OK);
    }
	
	
	
	@PutMapping("/papelera-reciclaje/restaurar-archivo-papelera")
    @PreAuthorize("@seguridad.validarRolAdminSistema()")
    public ResponseEntity<List<PapeleraModel>> restaurarArchivo(@RequestBody List<PapeleraDto> archivosPapelera) {
		if(papeleraService.restaurarArchivoPapelera(archivosPapelera)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
    }
	
	/**
	 * Eliminar de forma permanente los archivos de la Papelera de Reciclaje
	 * @param archivosPapelera
	 * @return
	 */
	@DeleteMapping("/papelera-reciclaje/eliminar-archivo-papelera")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<Boolean> eliminarArchivoPapelera(@RequestBody List<PapeleraDto> archivosPapelera) {
		papeleraService.eliminarArchivosPapelera(archivosPapelera);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Descarga archivo Excel con los Ids de Papelera Seleccionados
	 * @param busquedaPapeleraDto
	 * @return
	 */
	@PostMapping("/papelera-reciclaje/exportar-excel")
    @PreAuthorize("@seguridad.validarRolAdminSistema()")
    public ResponseEntity<byte[]> exportarExcel(@RequestBody BusquedaPapeleraDto busquedaPapeleraDto) {		
		return new ResponseEntity<>(papeleraService.exportarExcel(busquedaPapeleraDto), HttpStatus.OK);
    }
    
    /**
     * Para la desarga en ZIP de los archivos seleccionados
     * @param busquedaPapeleraDto
     * @return
     */
    @PostMapping("/papelera-reciclaje/descargar-zip")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> descargaZip(@RequestBody BusquedaPapeleraDto busquedaPapeleraDto) {
		return new ResponseEntity<>(papeleraService.descargarZip(busquedaPapeleraDto), HttpStatus.OK);
	}
	
	/**
	 * Descargar sólo el archivo de la fila
	 * @param idPapelera
	 * @return
	 */
	
	@GetMapping("/papelera-reciclaje/descargar-archivo/{idPapelera}")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<byte[]> descargaArchivo(@PathVariable("idPapelera") Long idPapelera) {
		return new ResponseEntity<>(papeleraService.descargarArchivo(idPapelera), HttpStatus.OK);
	}

	/**
	 * Para la descarga de SAT Cloud de los archivos seleccionados
	 * @param busquedaPapeleraDto
	 * @return
	 */
	@PostMapping("/papelera-reciclaje/descargar-cloud")
	@PreAuthorize("@seguridad.validarRolAdminSistema()")
	public ResponseEntity<List<CompartidoCloudModel>> descargaCloud(@RequestBody BusquedaPapeleraDto busquedaPapeleraDto) {
		return new ResponseEntity<>(papeleraService.descargarCloud(busquedaPapeleraDto), HttpStatus.OK);
	}
	
}
