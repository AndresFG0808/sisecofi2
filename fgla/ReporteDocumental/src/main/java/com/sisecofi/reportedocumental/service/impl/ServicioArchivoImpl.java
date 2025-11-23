package com.sisecofi.reportedocumental.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.libreria.comunes.model.papelera.PapeleraModel;
import com.sisecofi.libreria.comunes.repository.papelera.PapeleraRepository;
import com.sisecofi.libreria.comunes.util.exception.NexusException;
import com.sisecofi.reportedocumental.service.ServicioArchivo;
import com.sisecofi.reportedocumental.util.enums.ErroresPapeleraEnum;
import com.sisecofi.reportedocumental.util.exception.PapeleraException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author omartinezj
 *
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ServicioArchivoImpl implements ServicioArchivo {

	
	private final NexusImpl nexusImpl;
	private final PapeleraRepository papeleraRepository;
	
	private static final String ERROR = "Error:{}";
	private static final String PAPELERA = "/PAPELERA";

	@Override
	public void enviarArchivoSatCloudPapelera(PapeleraDto papeleraDto) {
		try {

			nexusImpl.generarCarpetasVacias(PAPELERA+papeleraDto.getPathOriginal());
			nexusImpl.renameFile(papeleraDto.getPathOriginal(), PAPELERA+papeleraDto.getPathOriginal());
			papeleraDto.setFecha(horaActual());
			papeleraDto.setPathNuevo(PAPELERA+papeleraDto.getPathOriginal());
			
			
			//Guardado en la Papelera de Reciclaje
			PapeleraModel papelera = null;

			List<PapeleraModel> resultados = papeleraRepository.findByPathOriginal(papeleraDto.getPathOriginal());
			
			if(!resultados.isEmpty()) { //Existe archivo.
				papelera = resultados.get(0);
				papelera.setIdArchivo(papeleraDto.getIdArchivo());
				papelera.setTipoArchivo(papeleraDto.getTipoArchivo());
				papelera.setIdUsuario(papeleraDto.getIdUsuario());
				papelera.setUsuarioElimina(papeleraDto.getUsuarioElimina());
				papelera.setEstatus(true);
				papeleraRepository.save(papelera);
			}else {
				PapeleraModel papeleraModel = new PapeleraModel();
				
				papeleraModel.setComentarios(papeleraDto.getComentarios());
				papeleraModel.setFecha(papeleraDto.getFecha());
				papeleraModel.setPathNuevo(papeleraDto.getPathNuevo());
				papeleraModel.setPathOriginal(papeleraDto.getPathOriginal());
				papeleraModel.setIdProyecto(papeleraDto.getIdProyecto());
				papeleraModel.setIdUsuario(papeleraDto.getIdUsuario());
				papeleraModel.setNombreCorto(papeleraDto.getNombreCorto());
				papeleraModel.setFase(papeleraDto.getFase());
				papeleraModel.setPlantilla(papeleraDto.getPlantilla());
				papeleraModel.setNombreDocumento(papeleraDto.getNombreDocumento());
				papeleraModel.setDescripcion(papeleraDto.getDescripcion());
				papeleraModel.setUsuarioElimina(papeleraDto.getUsuarioElimina());
				papeleraModel.setTamano(papeleraDto.getTamano());
				papeleraModel.setIdArchivo(papeleraDto.getIdArchivo());
				papeleraModel.setTipoArchivo(papeleraDto.getTipoArchivo());

				papeleraModel.setEstatus(true);
				papeleraRepository.save(papeleraModel);
			}
			

		} catch (Exception e) {
			log.error(ERROR);
			throw new PapeleraException(ErroresPapeleraEnum.ERROR_AL_GUARDAR);
		}
	}

	@Override
	public void eliminarArchivoSatCloud(PapeleraDto eliminado) {
		try {

			//Eliminado de Archivo SAT Cloud
			nexusImpl.eliminarArchivoSat(eliminado.getPathNuevo());
			nexusImpl.borrarFolder(generarPathArchivoBorrado(eliminado.getPathNuevo()));

			
			PapeleraModel papeleraModel = papeleraRepository.findById(eliminado.getIdPapelera())
					.orElseThrow(() -> new PapeleraException(ErroresPapeleraEnum.ERROR_AL_CONSULTAR));
			
			papeleraModel.setEstatus(false);
			papeleraRepository.save(papeleraModel);

		} catch (NexusException e) {
			log.error(ERROR);
			throw new PapeleraException(ErroresPapeleraEnum.ERROR_AL_ELIMINAR);
		}
	}
	
	private String generarPathArchivoBorrado(String path) {
		int lastSlashIndex = path.lastIndexOf('/');
		if (lastSlashIndex == -1) {
			throw new IllegalArgumentException("Invalid file path");
		}
		
		return path.substring(0,lastSlashIndex + 1);
	}

	private LocalDateTime horaActual() {
		ZoneId zoneId = ZoneId.of("America/Mexico_City");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
		return zonedDateTime.toLocalDateTime();
	}

	@Override
	public void restaurarArchivoPapelera(PapeleraDto restaurado) {
		try {
			
			PapeleraModel papeleraModel = papeleraRepository.findById(restaurado.getIdPapelera())
					.orElseThrow(() -> new PapeleraException(ErroresPapeleraEnum.ERROR_AL_CONSULTAR));
			
			//Resturación del Archivo
			nexusImpl.restautarArchivo(papeleraModel.getPathNuevo(), papeleraModel.getPathOriginal());
			
			//Cambio de Estatus registro Papelera (True = Activo, False = Restaurado)
			papeleraModel.setEstatus(false);
			papeleraRepository.save(papeleraModel);

		} catch (Exception e) {
			log.error(ERROR);
			throw new PapeleraException(ErroresPapeleraEnum.ERROR_AL_RESTAURAR);
		}
		
	}
}
