package com.sisecofi.contratos.service;

import com.sisecofi.contratos.repository.contrato.ContratoRepository;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final Session session;

    public void actualizarUltimaMod(Long idContrato) {
        ContratoModel contrato = contratoRepository.findByIdContrato(idContrato)
                .orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
        contrato.setFechaUltimaModificacion(horaActual());
        contrato.setUltimoModificador(obtenerUsuario().getNombre());
        contratoRepository.save(contrato);
    }

    private LocalDateTime horaActual() {
        ZoneId zoneId = ZoneId.of("America/Mexico_City");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return zonedDateTime.toLocalDateTime();
    }

    private Usuario obtenerUsuario() {
	    return session.retornarUsuario()
	                  .orElseThrow(() -> new ContratoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
	}
}
