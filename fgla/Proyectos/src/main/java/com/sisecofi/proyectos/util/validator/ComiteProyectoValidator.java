package com.sisecofi.proyectos.util.validator;

import com.sisecofi.proyectos.util.Constantes;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
public class ComiteProyectoValidator {


    public boolean decimalesValidacion(BigDecimal monto, BigDecimal tipoCambio, BigDecimal montoAutorizado){

        if (monto != null && monto.scale() > 2){
            log.info(Constantes.ERROR +Constantes.DECIMALES_INCORRECTOS, monto, tipoCambio);
            throw new ProyectoException(ErroresEnum.DECIMALES_INCORRECTOS, "monto erroneo");
        } else if (tipoCambio!= null && tipoCambio.scale() > 4) {
            log.info(Constantes.ERROR +Constantes.DECIMALES_INCORRECTOS, monto, tipoCambio);
            throw new ProyectoException(ErroresEnum.DECIMALES_INCORRECTOS, "tipo de cambio erroneo");
        } else if (montoAutorizado != null && montoAutorizado.scale() > 2) {
            log.info(Constantes.ERROR +Constantes.DECIMALES_INCORRECTOS, monto, tipoCambio);
            throw new ProyectoException(ErroresEnum.DECIMALES_INCORRECTOS, "monto autorizado erroneo");
        }else {
            return true;
        }
    }

    public boolean fechaValidacion(LocalDateTime fechaSesion){

        LocalDate fechaActual = LocalDate.now();

        LocalDate fechaFormateada = fechaSesion.toLocalDate();

        if (fechaFormateada.isAfter(fechaActual)){
            log.info("Error, fecha de sesion incorrecta: {}", fechaFormateada);
            throw new ProyectoException(ErroresEnum.FECHA_NO_PERMITIDA);
        }
        return true;
    }

}


