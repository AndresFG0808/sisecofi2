package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.util.Constantes;
import com.sisecofi.contratos.dto.CatalogoProveedorDto;
import com.sisecofi.contratos.dto.FiltroSelect;
import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import com.sisecofi.contratos.microservicios.ProveedorMicroservicio;
import com.sisecofi.contratos.repository.administradores.AdministradorAdministracionRepository;
import com.sisecofi.contratos.repository.administradores.AdministradorGeneralRepository;
import com.sisecofi.contratos.repository.administradores.AdministradoresRepository;
import com.sisecofi.contratos.repository.administradores.CatEmpleadoAdministracionRepository;
import com.sisecofi.contratos.service.ServicioCatalogos;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.CatalogoException;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.dto.contrato.UsuarioInfoDto;
import com.sisecofi.libreria.comunes.model.catalogo.BaseCatalogoModel;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatEmpleadoAdministracion;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServicioCatalogosImpl implements ServicioCatalogos {

	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ProveedorMicroservicio proveedorMicroservicio;
	private final UsuarioRepository usuarioRepository;
	private final AdministradorGeneralRepository adminGeneralRepository;
	private final AdministradoresRepository adminCentralRepository;
	private final AdministradorAdministracionRepository adminAdministracionRepository;
	private final CatEmpleadoAdministracionRepository empleadoRepository;

	@Override
	public List<BaseCatalogoModel> obtenerAdministracionCentral() {

		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerEstatatusContrato() {

		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.ESTATUS_CONTRATO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<CatalogoProveedorDto> obtenerProovedor() {
		try {
			return proveedorMicroservicio.obtenerTodoslosProveedores();
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoConsumo() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.TIPO_CONSUMO.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}

	}

	@Override
	public List<BaseCatalogoModel> obtenerContraroVigente() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.CONTRATO_VIGENTE.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}

	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoUnidad() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.TIPO_UNIDAD.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerTipoMoneda() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogo(CatalogosComunes.TIPO_MONEDA.getIdCatalogo());

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerIeps() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.IEPS.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerIva() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.IVA.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenetTipoProcedimiento() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.TIPO_PROCEDIMIENTO.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerDominioTecnologico() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.DOMINIOS.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerFondeContrato() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.FONDEO.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		try {
			return usuarioRepository.findByEstatusOrderByNombreAsc(true);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerAdministracionGeneral() {
		return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
				CatalogosComunes.ADMINISTRACION_GENERAL.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
	}

	@Override
	public List<BaseCatalogoModel> obtenerResponsabilidad() {
		return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.RESPONSABILIDAD.getIdCatalogo(),
				Constantes.VALIDACION_ESTATUS);
	}

	@Override
	public List<BaseCatalogoModel> obtenerPeriodicidad() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(CatalogosComunes.PERIODICIDAD.getIdCatalogo(),
					Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerConvenioColaboracion() {
		try {
			return catalogoMicroservicio.obtenerInformacionCatalogoCampo(
					CatalogosComunes.CONVENIO_COLABORACION.getIdCatalogo(), Constantes.VALIDACION_ESTATUS);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<BaseCatalogoModel> obtenerAdmonCentralPorGeneral(Integer idAdmonGeneral) {
		try {
			String json = String.format(Constantes.BASE_JSON_ADMON_CENTRAL, idAdmonGeneral);
			return catalogoMicroservicio
					.obtenerInformacionCatalogoCampo(CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(), json);
		} catch (Exception e) {
			throw new CatalogoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public List<UsuarioInfoDto> obtenerAdministradoresCentrales(FiltroSelect filtro) {
        List<UsuarioInfoDto> usuarios = new ArrayList<>();

        Integer idResponsabilidad = filtro.getIdResponsabilidad();
        Integer idGeneral = filtro.getIdGeneral();
        Integer idCentral = filtro.getIdCentral();

        if (idResponsabilidad != null) {
            if (idResponsabilidad < 6 && idCentral != null) {
                // Caso: idResponsabilidad < 6 y se filtra por idCentral para administradores de area y empleados
                List<CatAdministradorAdministracion> administradores =
                        adminAdministracionRepository.findByCatAdministracionCatAdmonCentralIdAdmonCentral(idCentral);
                administradores.forEach(admin -> usuarios.add(convertirAdministrador(admin)));

                List<CatEmpleadoAdministracion> empleados =
                        empleadoRepository.findByCatAdministracionCatAdmonCentralIdAdmonCentral(idCentral);
                empleados.forEach(emp -> usuarios.add(convertirEmpleado(emp)));

            } else if (idResponsabilidad == 7 && idGeneral != null) {
                // Caso: idResponsabilidad = 7 y se filtra por idGeneral para administradores generales
                List<CatAdministradorGeneral> generales =
                        adminGeneralRepository.findByCatAdmonGeneralIdAdmonGeneral(idGeneral);
                generales.forEach(general -> usuarios.add(convertirGeneral(general)));

            } else if (idResponsabilidad == 6 && idCentral != null) {
                // Caso: idResponsabilidad = 6 y se filtra por idCentral para administradores centrales
                List<CatAdministradorCentral> centrales =
                        adminCentralRepository.findByCatAdmonCentralIdAdmonCentral(idCentral);
                centrales.forEach(central -> usuarios.add(convertirCentral(central)));
            }
        }

        return usuarios;
    }
	
	@Override
	public List<UsuarioInfoDto> obtenerTodosLosUsuarios() {
	    List<UsuarioInfoDto> usuarios = new ArrayList<>();

	    // Administradores Generales
	    List<CatAdministradorGeneral> generales = adminGeneralRepository.findAll();
	    generales.forEach(general -> usuarios.add(convertirGeneral(general)));

	    // Administradores Centrales
	    List<CatAdministradorCentral> centrales = adminCentralRepository.findAll();
	    centrales.forEach(central -> usuarios.add(convertirCentral(central)));

	    // Administradores de Área
	    List<CatAdministradorAdministracion> administradores = adminAdministracionRepository.findAll();
	    administradores.forEach(admin -> usuarios.add(convertirAdministrador(admin)));

	    // Empleados
	    List<CatEmpleadoAdministracion> empleados = empleadoRepository.findAll();
	    empleados.forEach(emp -> usuarios.add(convertirEmpleado(emp)));

	    return usuarios;
	}


    private UsuarioInfoDto convertirGeneral(CatAdministradorGeneral general) {
        UsuarioInfoDto dto = new UsuarioInfoDto();
        dto.setIdUsuario(general.getIdAdministrador());
        dto.setCorreo(general.getCorreo());
        dto.setNombre(general.getAdministrador());
        dto.setTelefono(general.getTelefono());
        dto.setNivel(1);
        return dto;
    }

    private UsuarioInfoDto convertirCentral(CatAdministradorCentral central) {
        UsuarioInfoDto dto = new UsuarioInfoDto();
        dto.setIdUsuario(central.getIdAdministrador());
        dto.setCorreo(central.getCorreo());
        dto.setNombre(central.getAdministrador());
        dto.setTelefono(central.getTelefono());
        dto.setNivel(2);
        return dto;
    }

    private UsuarioInfoDto convertirAdministrador(CatAdministradorAdministracion admin) {
        UsuarioInfoDto dto = new UsuarioInfoDto();
        dto.setIdUsuario(admin.getIdAdministrador());
        dto.setCorreo(admin.getCorreo());
        dto.setNombre(admin.getAdministrador());
        dto.setTelefono(admin.getTelefono());
        dto.setNivel(3);
        return dto;
    }

    private UsuarioInfoDto convertirEmpleado(CatEmpleadoAdministracion empleado) {
        UsuarioInfoDto dto = new UsuarioInfoDto();
        dto.setIdUsuario(empleado.getIdEmpleadoAdministracion());
        dto.setCorreo(empleado.getCorreo());
        dto.setNombre(empleado.getNombre());
        dto.setTelefono(empleado.getTelefono());
        dto.setNivel(4);
        return dto;
    }

}