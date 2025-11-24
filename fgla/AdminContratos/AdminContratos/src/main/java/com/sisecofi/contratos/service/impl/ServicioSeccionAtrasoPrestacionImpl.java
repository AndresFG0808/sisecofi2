package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.service.ContratoService;
import com.sisecofi.libreria.comunes.model.contratos.AtrasoPrestacionModel;
import com.sisecofi.contratos.repository.contrato.AtrasoPresentacionRepository;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.ServicioSeccionAtrasoPresentacion;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioSeccionAtrasoPrestacionImpl implements ServicioSeccionAtrasoPresentacion {

    private final AtrasoPresentacionRepository atrasoPresentacionRepository;
    private final PistaService pistaService;
    private final ContratoService contratoService;

    @Override
    public AtrasoPrestacionModel obtenerAtrasoPresentacion(Long idAtrasoPresentacion) {
        try {
            return atrasoPresentacionRepository.findByIdAtrasoPrestacion(idAtrasoPresentacion);
        }catch (Exception e){
            throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
        }
    }

    @Override
    public String guardarAtrasoPresentacion(List<AtrasoPrestacionModel> atrasoPresentacionModel) {
        try {
            for (AtrasoPrestacionModel atrasoPresentacion : atrasoPresentacionModel){
                atrasoPresentacion.setEstatus(true);
                atrasoPresentacionRepository.save(atrasoPresentacion);

                this.contratoService.actualizarUltimaMod(atrasoPresentacion.getIdContrato());



                // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


                // TipoSeccionPista.ASIGNAR_PROYECTO_POR_PROYECTO.getIdSeccionPista(),


                // TipoMovPista.INSERTA_REGISTRO.getClave(), Optional.empty());
            }
            return "OK";
        }catch (Exception e){
            throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
        }
    }

    @Override
    public String editarAtrasoPresnetacion(List<AtrasoPrestacionModel> atrasoPresentacionList) {
        try {

            for (AtrasoPrestacionModel atrasoPresentacion : atrasoPresentacionList){
                Long idAtrasoPresentacion = atrasoPresentacion.getIdAtrasoPrestacion();
                AtrasoPrestacionModel atrasoPresentacionModel = atrasoPresentacionRepository.findByIdAtrasoPrestacion(idAtrasoPresentacion);

                atrasoPresentacionModel.setDescripcion(atrasoPresentacion.getDescripcion());
                atrasoPresentacionModel.setFechaPrestacion(atrasoPresentacion.getFechaPrestacion());
                atrasoPresentacionModel.setPenasDeducciones(atrasoPresentacion.getPenasDeducciones());
                atrasoPresentacionRepository.save(atrasoPresentacionModel);

                this.contratoService.actualizarUltimaMod(atrasoPresentacionModel.getIdContrato());
            }



            // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),


            // TipoSeccionPista.ASIGNAR_PROYECTO_POR_PROYECTO.getIdSeccionPista(),


            // TipoMovPista.ACTUALIZA_REGISTRO.getClave(), Optional.empty());

            return "OK";
        }catch (Exception e){
            throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
        }
    }

    @Override
    public String eliminarAtrasoPresentacion(List<Long> idsAtrasoPresentacion) {

        try {
            for (Long idAtrasoPrestacion : idsAtrasoPresentacion){
                AtrasoPrestacionModel atrasoPresentacionModel = atrasoPresentacionRepository.findByIdAtrasoPrestacion(idAtrasoPrestacion);
                atrasoPresentacionModel.setEstatus(false);
                atrasoPresentacionRepository.save(atrasoPresentacionModel);

                this.contratoService.actualizarUltimaMod(atrasoPresentacionModel.getIdContrato());
            }

            // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),

            // TipoSeccionPista.ASIGNAR_PROYECTO_POR_PROYECTO.getIdSeccionPista(),

            // TipoMovPista.BORRA_REGISTRO.getClave(), Optional.empty());
            return "OK";
        }catch (Exception e){
            throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
        }
    }

    @Override
    public List<AtrasoPrestacionModel> obtenerAtrasosPrestaciones(Long idContrato) {
        List<AtrasoPrestacionModel> atrasoPresentacionModels = atrasoPresentacionRepository.findAllByIdContratoAndEstatusTrueOrderByIdAtrasoPrestacion(idContrato)
                .orElseThrow(() ->  new ContratoException(ErroresEnum.ERROR_REGISTROS_NO_ENCONTRADO));

        try {

            // pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.CONSULTA_REGISTRO.getId(),

            // TipoSeccionPista.ASIGNAR_PROYECTO_POR_PROYECTO.getIdSeccionPista(),

            // TipoMovPista.CONSULTA_REGISTRO.getClave(), Optional.empty());

            return atrasoPresentacionModels;
        }catch (Exception e){
            throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
        }
    }
    
    @Override
	public AtrasoPrestacionModel obtenerAtrasoPrestacionIndividual(Long idInforme) {
    	AtrasoPrestacionModel informe = new AtrasoPrestacionModel();
			Optional<AtrasoPrestacionModel> informeOp = atrasoPresentacionRepository.findById(idInforme);
			if (informeOp.isPresent()) {
				return informeOp.get();
			}
		return informe;
	}

    @Override
    public List<AtrasoPrestacionModel> obtenerAtrasosPrestacionesPorIdContrato(Long idContrato) {
        return atrasoPresentacionRepository.findAllByIdContratoAndEstatusTrueOrderByIdAtrasoPrestacion(idContrato)
                .orElseThrow(() -> new ContratoException(ErroresEnum.ERROR_REGISTROS_NO_ENCONTRADO));
    }
}
