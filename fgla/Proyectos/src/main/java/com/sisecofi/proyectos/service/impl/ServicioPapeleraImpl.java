package com.sisecofi.proyectos.service.impl;

import com.sisecofi.libreria.comunes.dto.PapeleraDto;
import com.sisecofi.proyectos.microservicio.PapeleraServico;
import com.sisecofi.proyectos.service.ServicioPapelera;
import com.sisecofi.proyectos.util.enums.ErroresEnum;
import com.sisecofi.proyectos.util.exception.ProyectoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServicioPapeleraImpl implements ServicioPapelera {

    private final PapeleraServico papeleraServico;

    @Override
    public PapeleraDto obtenerArchivoPapelera(Long idPapelera) {
        try {
           return papeleraServico.obtenerArchivo(idPapelera);
        }catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
        }
    }

    @Override
    public List<PapeleraDto> obtenerPapelera() {
        try {
            return papeleraServico.obtenerPapelera();
        }catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
        }
    }

    @Override
    public PapeleraDto restaurarArchivo(Long idPapelera) {
        try {
            return papeleraServico.restaurarArchivo(idPapelera);
        }catch (Exception e){
            throw new ProyectoException(ErroresEnum.ERROR_AL_CONSULTAR);
        }
    }
}
