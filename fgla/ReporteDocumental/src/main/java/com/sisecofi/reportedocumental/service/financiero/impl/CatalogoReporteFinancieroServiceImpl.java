package com.sisecofi.reportedocumental.service.financiero.impl;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ProyectoNombreDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusDictamen;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.service.security.SeguridadService;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import com.sisecofi.reportedocumental.dto.financiero.ConceptoServicioDTO;
import com.sisecofi.reportedocumental.dto.financiero.FiltroNombreContratoBaseDTO;
import com.sisecofi.reportedocumental.dto.financiero.NombreContratoDTO;
import com.sisecofi.reportedocumental.dto.financiero.VerificadorContratoDto;
import com.sisecofi.reportedocumental.microservicio.CatalogoMicroservicio;
import com.sisecofi.reportedocumental.model.CatEstatusVolumetria;
import com.sisecofi.reportedocumental.repository.dinamico.ProyectoRepository;
import com.sisecofi.reportedocumental.repository.financiero.CatEstatusVolumetriaRepository;
import com.sisecofi.reportedocumental.repository.financiero.ConceptoServicioRepository;
import com.sisecofi.reportedocumental.repository.financiero.ContratoRepositoryImpl;
import com.sisecofi.reportedocumental.repository.financiero.VerificadorContratoRepository;
import com.sisecofi.reportedocumental.service.financiero.CatalogoReporteFinancieroService;
import com.sisecofi.reportedocumental.util.Constantes;
import com.sisecofi.reportedocumental.util.enums.ErroresEnum;
import com.sisecofi.reportedocumental.util.exception.ReporteException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CatalogoReporteFinancieroServiceImpl implements CatalogoReporteFinancieroService {
    public static final String TODOS = "Todos";
    private final CatalogoMicroservicio catalogoMicroservicio;
    private final ProyectoRepository proyectoRepository;
    private final ContratoRepositoryImpl contratoRepositoryImpl;
    private final VerificadorContratoRepository verificadorContratoRepository;
    private final CatEstatusVolumetriaRepository catEstatusVolumetriaRepository;
    private final ConceptoServicioRepository conceptoServicioRepository;
    private final SeguridadService seguridadService;
    private final Session session;

    public Optional<Usuario> obtenerUsuario() {
        return session.retornarUsuario();
    }

    @Override
    public List<BaseCatalogoModel> estatusProyecto() {
        try {
            return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
                    CatalogosComunes.ESTATUS_PROYECTO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
        } catch (Exception e) {
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    @Override
    public List<BaseCatalogoModel> contratoVigente() {
        try {
            return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
                    CatalogosComunes.CONTRATO_VIGENTE.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
        } catch (Exception e) {
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    @Override
    public List<BaseCatalogoModel> dominiosTecnologicos() {
        try {
            return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.DOMINIOS.getIdCatalogo(),
                    Constantes.VALIDACION_ESTATUS);
        } catch (Exception e) {
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    @Override
    public List<BaseCatalogoModel> convenioColaboracion() {
        try {
            return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
                    CatalogosComunes.CONVENIO_COLABORACION_REPORTE.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
        } catch (Exception e) {
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    private boolean validarAccesos() {
        return (seguridadService.validarRolVerificadorGeneral() || seguridadService.validarRolAdminSistema()
                || seguridadService.validarRolAdminSistemaSecundario() || seguridadService.validarRolTodosProyectos()
                || seguridadService.validarRolAdminMatrizDocumental());
    }

    @Override
    public List<ProyectoNombreDto> buscarTodosProyectos() {
        try {
            Optional<Usuario> usuarioLogueado = obtenerUsuario();
            Long userIdLogueado = usuarioLogueado.map(Usuario::getIdUser).orElse(null);
            var todos = new ProyectoNombreDto();
            if (validarAccesos()) {
                var lista = parseNombreProyecto(proyectoRepository.buscarTodosProyectos());

                todos.setIdProyecto(0L);
                todos.setNombreCorto(TODOS);

                lista.add(0, todos);

                return lista;
            }
            var lista = parseNombreProyecto(
                    proyectoRepository.buscarTodosProyectosAndUser(Integer.parseInt("" + userIdLogueado)));

            todos.setIdProyecto(0L);
            todos.setNombreCorto(TODOS);

            lista.add(0, todos);
            return lista;

        } catch (Exception e) {
            log.info("Error en buscarTodosProyectos");
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    @Override
    public List<ProyectoNombreDto> buscarProyectosPorEstatus(Integer idEstatusProyecto) {
        try {
            Optional<Usuario> usuarioLogueado = obtenerUsuario();
            Long userIdLogueado = usuarioLogueado.map(Usuario::getIdUser).orElse(null);
            List<Integer> ids = new ArrayList<>();
            if (validarAccesos()) {
                ids.add(idEstatusProyecto);
                return parseNombreProyecto(proyectoRepository.findByIdEstatusProyectoOrdenado(ids));
            }

            ids.add(idEstatusProyecto);
            return parseNombreProyecto(proyectoRepository
                    .findByIdEstatusProyectoAndUserOrdenado(Integer.parseInt("" + userIdLogueado), ids));

        } catch (Exception e) {
            log.info("Error en buscarProyectosPorEstatus");
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    @Override
    public List<NombreContratoDTO> filtrarContratos(FiltroNombreContratoBaseDTO dto) {
        try {
            return contratoRepositoryImpl.filtrarContratos(dto);
        } catch (Exception e) {
            log.info("Error en filtrarContratos", e);
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    @Override
    public List<BaseCatalogoModel> estatusDictamen() {
        try {
            var lista = catalogoMicroservicio.obtenerInformacionCatalogoCampo(
                    CatalogosComunes.ESTATUS_DICTAMEN.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

            var todos = new CatEstatusDictamen();
            todos.setIdEstatusDictamen(0);
            todos.setEstatus(true);
            todos.setNombre(TODOS);
            todos.setDescripcion(TODOS);
            lista.add(0, todos);

            return lista;
        } catch (Exception e) {
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    @Override
    public List<VerificadorContratoDto> listarVerificadoresPorContrato(String nombreCortoContrato) {
        try {
            return verificadorContratoRepository.listarVerificadoresPorContrato(nombreCortoContrato);
        } catch (Exception e) {
            log.info("Error en listarVerificadoresPorContrato", e);
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    @Override
    public List<CatEstatusVolumetria> estatusVolumetria() {
        try {
            return catEstatusVolumetriaRepository.findAllByEstatusIsTrue();
        } catch (Exception e) {
            log.info("Error en findAllByEstatusIsTrue", e);
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    @Override
    public List<ConceptoServicioDTO> conceptoServicio(Long idContrato) {
        try {
            return conceptoServicioRepository.buscar(idContrato);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info("Error en conceptoServicio", e);
            throw new ReporteException(ErroresEnum.ERRO_AL_CONSULTAR_CATALOGO);
        }
    }

    private List<ProyectoNombreDto> parseNombreProyecto(List<Object[]> results) {
        List<ProyectoNombreDto> proyectos = new ArrayList<>();
        results.forEach(item -> proyectos.add(new ProyectoNombreDto((Long) item[0], (String) item[1])));
        return proyectos;
    }

}
