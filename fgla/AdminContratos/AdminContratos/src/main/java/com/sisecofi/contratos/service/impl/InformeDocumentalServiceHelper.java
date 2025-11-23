package com.sisecofi.contratos.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.ToLongFunction;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesPeriodicosModel;
import com.sisecofi.libreria.comunes.model.contratos.InformesDocumentalesUnicaVezModel;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InformeDocumentalServiceHelper {
	private static final String ERROR_ELIMINAR = "Ocurrio un error al eliminar el Informe Documental Periodico";

	public <T> String eliminarInformes(
	        List<Long> ids,
	        LongPredicate existsById,               
	        LongFunction<Optional<T>> findById,    
	        Consumer<T> saveEntity,
	        ToLongFunction<T> getIdContrato,        
	        LongConsumer actualizarUltimaMod) {

		StringBuilder resultado = new StringBuilder();

		try {
	        for (long id : ids) {                  
	            if (existsById.test(id)) {        
	                T informesModel = findById.apply(id).orElse(null); 
	                if (informesModel != null) {
	                    actualizarEstatus(informesModel, false);
	                    saveEntity.accept(informesModel); 
	                    long contratoId = getIdContrato.applyAsLong(informesModel); 
	                    actualizarUltimaMod.accept(contratoId);
	                    resultado.append("Ok\n");           
	                   
	                }
	            } else {
	                resultado.append("No existe ningún registro con ID ").append(id).append(" en la base de datos.\n");
	            }
	        }
	    }catch (DataAccessException e) {
			log.error("Error de acceso a datos al eliminar el Informe Documental");
			return ERROR_ELIMINAR;
		} catch (IllegalArgumentException e) {
			log.error("Argumento inválido al eliminar el Informe Documental");
			return ERROR_ELIMINAR;
		} catch (NullPointerException e) {
			log.error("Se encontró un valor nulo al eliminar el Informe Documental");
			return ERROR_ELIMINAR;
		}

		return resultado.toString().trim();
	}

	private <T> void actualizarEstatus(T informeModel, boolean estatus) {
	    if (informeModel instanceof InformesDocumentalesPeriodicosModel periodicosModel) {
	        periodicosModel.setEstatus(estatus);
	    } else if (informeModel instanceof InformesDocumentalesUnicaVezModel unicaVezModel) {
	        unicaVezModel.setEstatus(estatus);
	    }
	}
	
	public void logExcepcion(Exception e, String mensajeBase) {
	    if (e instanceof DataAccessException) {
	        log.error("Error de acceso a datos:");
	    } else if (e instanceof IllegalArgumentException) {
	        log.error("Argumento inválido: {}", mensajeBase);
	    } else if (e instanceof NullPointerException) {
	        log.error("Valor nulo encontrado: {}", mensajeBase);
	    } else {
	        log.error("Excepción no manejada: {}", mensajeBase);
	    }
	}


}
