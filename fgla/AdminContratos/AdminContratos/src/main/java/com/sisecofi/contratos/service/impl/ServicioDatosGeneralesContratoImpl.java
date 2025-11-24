package com.sisecofi.contratos.service.impl;

import com.sisecofi.contratos.dto.EliminarParticipantesDto;
import com.sisecofi.contratos.dto.ProveedoresDto;
import com.sisecofi.contratos.microservicios.CatalogoMicroservicio;
import com.sisecofi.contratos.microservicios.ProveedorMicroservicio;
import com.sisecofi.contratos.repository.administradores.AdministradorAdministracionRepository;
import com.sisecofi.contratos.repository.administradores.AdministradorGeneralRepository;
import com.sisecofi.contratos.repository.administradores.AdministradoresRepository;
import com.sisecofi.contratos.repository.administradores.CatEmpleadoAdministracionRepository;
import com.sisecofi.contratos.repository.contrato.AsociacionContratoProovedorRepository;
import com.sisecofi.contratos.repository.contrato.ContratoRepository;
import com.sisecofi.contratos.repository.contrato.DatosGeneralesContratoRepository;
import com.sisecofi.contratos.service.ContratoService;
import com.sisecofi.contratos.service.ParticipantesAdministracionContratoRepository;
import com.sisecofi.contratos.service.PistaService;
import com.sisecofi.contratos.service.ServicioDatosGeneralesContrato;
import com.sisecofi.contratos.util.consumer.ContratosPistasConsumer;
import com.sisecofi.contratos.util.enums.ErroresEnum;
import com.sisecofi.contratos.util.exception.ContratoException;
import com.sisecofi.libreria.comunes.dto.contrato.DatosGeneralesContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.DatosGeneralesResponseDto;
import com.sisecofi.libreria.comunes.dto.contrato.ParticipantesAdminContratoDto;
import com.sisecofi.libreria.comunes.dto.contrato.UsuarioInfoDto;
import com.sisecofi.libreria.comunes.model.catalogo.*;
import com.sisecofi.libreria.comunes.model.contratos.AsociacionContratoProveedorModel;
import com.sisecofi.libreria.comunes.model.contratos.ContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.DatosGeneralesContratoModel;
import com.sisecofi.libreria.comunes.model.contratos.ParticipantesAdministracionModel;
import com.sisecofi.libreria.comunes.model.proveedores.ProveedorModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.enums.CatalogosComunes;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;
import com.sisecofi.libreria.comunes.util.sesion.Session;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ServicioDatosGeneralesContratoImpl implements ServicioDatosGeneralesContrato {

	private final DatosGeneralesContratoRepository datosGeneralesContratoRepository;
	private final AsociacionContratoProovedorRepository asociacionContratoProovedorRepository;
	private final ParticipantesAdministracionContratoRepository participantesAdministracionContratoRepository;
	private final ProveedorMicroservicio proveedorMicroservicio;
	private final CatalogoMicroservicio catalogoMicroservicio;
	private final ContratoRepository contratoRepository;
	private final Session session;
	private final PistaService pistaService;
	private final ContratosPistasConsumer contratosPistasConsumer;
	private final ContratoService contratoService;
	private final AdministradorGeneralRepository adminGeneralRepository;
	private final AdministradoresRepository adminCentralRepository;
	private final AdministradorAdministracionRepository adminAdministracionRepository;
	private final CatEmpleadoAdministracionRepository empleadoRepository;

	@Override
	public DatosGeneralesContratoModel guardarDatosGenerales(DatosGeneralesContratoDto datosGeneralesContrato) {

		DatosGeneralesContratoModel datosGenerales = datosGeneralesContratoRepository
				.findByIdContratoAndEstatusTrue(datosGeneralesContrato.getIdContrato());

		if (datosGenerales != null) {
			throw new ContratoException(ErroresEnum.ERROR_DATOS_GENERALES_REGISTRADO);
		}
		Long idContrato = datosGeneralesContrato.getIdContrato();
		guardarUltimaMoodificacion(idContrato);

		guardarProvedores(datosGeneralesContrato.getIdsProveedores(), idContrato);

		DatosGeneralesContratoModel datosGeneralesContratoModel = new DatosGeneralesContratoModel();
		datosGeneralesContratoModel.setEstatus(true);
		datosGeneralesContratoModel.setNumetoContratoCompraNet(datosGeneralesContrato.getNumeroContratoCompraNet());
		datosGeneralesContratoModel.setNumeroContrato(datosGeneralesContrato.getNumeroContrato());
		datosGeneralesContratoModel.setAcuerdo(datosGeneralesContrato.getAcuerdo());
		datosGeneralesContratoModel.setIdContrato(idContrato);
		datosGeneralesContratoModel.setIdTipoProcedimiento(datosGeneralesContrato.getIdTipoProcedimiento());
		datosGeneralesContratoModel.setNumeroProcedimiento(datosGeneralesContrato.getNumeroProcedimiento());
		datosGeneralesContratoModel.setConvenioColaboracion(datosGeneralesContrato.getConvenioColaboracion()); // eliminar
		datosGeneralesContratoModel.setIdConvenioColaboracion(datosGeneralesContrato.getIdCatConvenioColaboracion());
		datosGeneralesContratoModel.setIdDominosTecnologicos(datosGeneralesContrato.getIdDominioTecnologico());
		datosGeneralesContratoModel.setIdFondeoContrato(datosGeneralesContrato.getIdFondeoContrato());
		datosGeneralesContratoModel.setObjetivoServicio(datosGeneralesContrato.getObjetivoServicio());
		datosGeneralesContratoModel.setAlcanceServicio(datosGeneralesContrato.getAlcanceServicio());
		datosGeneralesContratoModel.setTituloServicio(datosGeneralesContrato.getTitulosServicio());
		datosGeneralesContratoRepository.save(datosGeneralesContratoModel);

		String movimiento = contratosPistasConsumer.movimientosContratosDatosGenerales(datosGeneralesContratoModel);

		this.contratoService.actualizarUltimaMod(idContrato);



		// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),


		// TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), movimiento, Optional.empty());

		return datosGeneralesContratoModel;

	}

	@Override
	public DatosGeneralesResponseDto obtenerDatosGnerales(Long idContrato) {
		try {
			DatosGeneralesContratoModel datosGeneralesContratoModel = datosGeneralesContratoRepository
					.findByIdContratoAndEstatusTrue(idContrato);

			List<ProveedorModel> provedores = new ArrayList<>();

			List<AsociacionContratoProveedorModel> asociacionContrato = asociacionContratoProovedorRepository
					.findByIdContratoAndEstatusTrue(idContrato);

			for (AsociacionContratoProveedorModel asociacion : asociacionContrato) {
				Long idProveedor = asociacion.getIdProveedor();
				ProveedorModel proveedorModel = proveedorMicroservicio.buscarProveedor(idProveedor);
				provedores.add(proveedorModel);
			}

			Integer idTipoProcedimiento = datosGeneralesContratoModel.getIdTipoProcedimiento();
			Integer idDominioTecnologico = datosGeneralesContratoModel.getIdDominosTecnologicos();
			Integer idFondeo = datosGeneralesContratoModel.getIdFondeoContrato();

			CatTipoProcedimiento tipoProcedimiento = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.TIPO_PROCEDIMIENTO.getIdCatalogo(), idTipoProcedimiento,
					new CatTipoProcedimiento());
			CatFondeoContrato fondeoContrato = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
					CatalogosComunes.FONDEO.getIdCatalogo(), idFondeo, new CatFondeoContrato());

			DatosGeneralesResponseDto datosGeneralesResponseDto = new DatosGeneralesResponseDto();
			datosGeneralesResponseDto.setIdContrato(idContrato);
			datosGeneralesResponseDto.setProveedores(provedores);
			datosGeneralesResponseDto.setNumeroContrato(datosGeneralesContratoModel.getNumeroContrato());
			datosGeneralesResponseDto
					.setNumeroContratoCompraNet(datosGeneralesContratoModel.getNumetoContratoCompraNet());
			datosGeneralesResponseDto.setAcuerdo(datosGeneralesContratoModel.getAcuerdo());
			datosGeneralesResponseDto.setNumeroProcedimiento(datosGeneralesContratoModel.getNumeroProcedimiento());
			datosGeneralesResponseDto.setObjetivoServicio(datosGeneralesContratoModel.getObjetivoServicio());
			datosGeneralesResponseDto.setAlcanceServicio(datosGeneralesContratoModel.getAlcanceServicio());
			datosGeneralesResponseDto.setCatFondeoContrato(fondeoContrato);
			datosGeneralesResponseDto.setCatTipoProcedimiento(tipoProcedimiento);
			datosGeneralesResponseDto.setIdTipoProcedimiento(datosGeneralesContratoModel.getIdTipoProcedimiento());
			datosGeneralesResponseDto.setConvenioColaboracion(datosGeneralesContratoModel.getConvenioColaboracion());
			datosGeneralesResponseDto.setTitulosServicio(datosGeneralesContratoModel.getTituloServicio());
			datosGeneralesResponseDto
					.setCatConvenioColaboracion(datosGeneralesContratoModel.getCatConvenioColaboracion());
			datosGeneralesResponseDto
					.setIdCatConvenioColaboracion(datosGeneralesContratoModel.getIdConvenioColaboracion());

			if (datosGeneralesContratoModel.getIdConvenioColaboracion() != null) {
				CatConvenioColaboracion convenioColaboracion = catalogoMicroservicio
						.obtenerInformacionCatalogoIdReference(CatalogosComunes.CONVENIO_COLABORACION.getIdCatalogo(),
								datosGeneralesContratoModel.getIdConvenioColaboracion(), new CatConvenioColaboracion());
				datosGeneralesResponseDto.setCatConvenioColaboracion(convenioColaboracion);
			}

			if (datosGeneralesContratoModel.getIdDominosTecnologicos() != null) {
				CatDominiosTecnologicos dominiosTecnologicos = catalogoMicroservicio
						.obtenerInformacionCatalogoIdReference(CatalogosComunes.DOMINIOS.getIdCatalogo(),
								idDominioTecnologico, new CatDominiosTecnologicos());
				datosGeneralesResponseDto.setCatDominiosTecnologicos(dominiosTecnologicos);
			}

			return datosGeneralesResponseDto;

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR, e);
		}
	}

	@Override
	public List<ProveedorModel> agregarProveedor(ProveedoresDto proveedoresDto) {
		Usuario usuario = obtenerUsuario();
		Long idContrato = proveedoresDto.getIdContrato();

		ContratoModel contratoModel = contratoRepository.findByIdContratoAndEstatusTrue(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));

		List<Long> idsProveedores = asociacionContratoProovedorRepository.findIdProveedorByIdContrato(idContrato);

		for (Long idProveedor : proveedoresDto.getIdsProveedores()) {

			if (!idsProveedores.contains(idProveedor)) {
				AsociacionContratoProveedorModel asociacionContratoProveedor = new AsociacionContratoProveedorModel();
				asociacionContratoProveedor.setIdContrato(idContrato);
				asociacionContratoProveedor.setEstatus(true);
				asociacionContratoProveedor.setIdProveedor(idProveedor);
				asociacionContratoProovedorRepository.save(asociacionContratoProveedor);
			}
		}

		List<ProveedorModel> proveedorModels = new ArrayList<>();

		for (Long idProveedor : proveedoresDto.getIdsProveedores()) {
			ProveedorModel proveedorModel = proveedorMicroservicio.buscarProveedor(idProveedor);
			proveedorModels.add(proveedorModel);
		}

		contratoModel.setFechaUltimaModificacion(LocalDateTime.now());
		contratoModel.setUltimoModificador(usuario.getNombre());
		contratoRepository.save(contratoModel);

		String movimiento = contratosPistasConsumer.movimientoContratoModel(contratoModel);
		this.contratoService.actualizarUltimaMod(idContrato);

		// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

		// TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), movimiento, Optional.empty());

		return proveedorModels;
	}

	@Override
	public String eliminarProveedor(ProveedoresDto proveedoresDto) {

		Long idContrato = proveedoresDto.getIdContrato();

		List<AsociacionContratoProveedorModel> asociacionContratoProveedorModels = asociacionContratoProovedorRepository
				.findByIdContratoAndEstatusTrue(idContrato);
		if (asociacionContratoProveedorModels.size() <= 1) {
			throw new ContratoException(ErroresEnum.ERROR_ULTIMO_PROVEEDOR);
		}

		for (Long idProvedor : proveedoresDto.getIdsProveedores()) {
			AsociacionContratoProveedorModel asociacionContratoProveedorModel = asociacionContratoProovedorRepository
					.findByIdContratoAndIdProveedor(idContrato, idProvedor);
			asociacionContratoProveedorModel.setEstatus(false);
			asociacionContratoProovedorRepository.save(asociacionContratoProveedorModel);
			this.contratoService.actualizarUltimaMod(idContrato);
		}

		return "OK";
	}

	@Override
	public DatosGeneralesContratoModel actualizarDatosGenerales(DatosGeneralesContratoDto datosGeneralesContratoDto) {
		Long idContrato = datosGeneralesContratoDto.getIdContrato();
		guardarUltimaMoodificacion(idContrato);
		DatosGeneralesContratoModel datosGeneralesContratoModel = datosGeneralesContratoRepository
				.findByIdContratoAndEstatusTrue(idContrato);

		try {
			datosGeneralesContratoModel.setEstatus(true);
			datosGeneralesContratoModel
					.setNumetoContratoCompraNet(datosGeneralesContratoDto.getNumeroContratoCompraNet());
			datosGeneralesContratoModel.setNumeroContrato(datosGeneralesContratoDto.getNumeroContrato());
			datosGeneralesContratoModel.setAcuerdo(datosGeneralesContratoDto.getAcuerdo());
			datosGeneralesContratoModel.setIdContrato(idContrato);
			datosGeneralesContratoModel.setIdTipoProcedimiento(datosGeneralesContratoDto.getIdTipoProcedimiento());
			datosGeneralesContratoModel.setNumeroProcedimiento(datosGeneralesContratoDto.getNumeroProcedimiento());
			datosGeneralesContratoModel.setConvenioColaboracion(datosGeneralesContratoDto.getConvenioColaboracion());
			datosGeneralesContratoModel.setIdDominosTecnologicos(datosGeneralesContratoDto.getIdDominioTecnologico());
			datosGeneralesContratoModel.setIdFondeoContrato(datosGeneralesContratoDto.getIdFondeoContrato());
			datosGeneralesContratoModel.setObjetivoServicio(datosGeneralesContratoDto.getObjetivoServicio());
			datosGeneralesContratoModel.setAlcanceServicio(datosGeneralesContratoDto.getAlcanceServicio());
			datosGeneralesContratoModel.setTituloServicio(datosGeneralesContratoDto.getTitulosServicio());
			datosGeneralesContratoModel
					.setIdConvenioColaboracion(datosGeneralesContratoDto.getIdCatConvenioColaboracion());
			datosGeneralesContratoRepository.save(datosGeneralesContratoModel);

			guardarProvedores(datosGeneralesContratoDto.getIdsProveedores(), idContrato);
			eliminarProveedores(datosGeneralesContratoDto.getIdsProveedoresEliminados(), idContrato);

			String movimiento = contratosPistasConsumer.movimientosContratosDatosGenerales(datosGeneralesContratoModel);
			this.contratoService.actualizarUltimaMod(idContrato);

			// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

			// TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), movimiento, Optional.empty());

			return datosGeneralesContratoModel;

		} catch (ContratoException e) {
			throw e;
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR, e);
		}
	}

	public void validarFechasParticipante(ParticipantesAdministracionModel participantesAdministracion) {
		if (participantesAdministracion.getFechaTermino() != null
				&& participantesAdministracion.getVigente().equals(false) && participantesAdministracion
						.getFechaTermino().isBefore(participantesAdministracion.getFechaInicio())) {
			throw new ContratoException(ErroresEnum.FECHAS_VALIDACION);
		}
	}

	@Transactional
	@Override
	public String guardarParticipantesAdminContrato(
			List<ParticipantesAdministracionModel> participantesAdministracionModel) {

		try {
			Long idContrato = 0L;

			for (ParticipantesAdministracionModel participantesAdministracion : participantesAdministracionModel) {

				validarFechasParticipante(participantesAdministracion);
				Integer idResponsabilidad = participantesAdministracion.getIdResponsabilidad();
				CatResponsabilidad responsabilidad = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.RESPONSABILIDAD.getIdCatalogo(), idResponsabilidad, new CatResponsabilidad());

				if (Objects.equals(responsabilidad.getNombre(), "Administrador del contrato")
						&& Boolean.TRUE.equals(participantesAdministracion.getVigente())) {

					List<ParticipantesAdministracionModel> participantes = participantesAdministracionContratoRepository
							.findByIdResponsabilidadAndIdContratoAndEstatusTrueAndVigenteTrue(
									participantesAdministracion.getIdResponsabilidad(),
									participantesAdministracion.getIdContrato());
					if (!participantes.isEmpty()) {
						throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR_PARTICIPANTE);

					}

				}
				participantesAdministracion.setEstatus(true);
				participantesAdministracionContratoRepository.save(participantesAdministracion);

				String movimiento = contratosPistasConsumer
						.movimientoParticipantesContrato(participantesAdministracion);
				this.contratoService.actualizarUltimaMod(participantesAdministracion.getIdContrato());

				// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.INSERTA_REGISTRO.getId(),

				// TipoSeccionPista.CONTRATOS_DATOS_GENERALES_PARTICIPANTES.getIdSeccionPista(), movimiento,

				// Optional.empty());

				idContrato = participantesAdministracion.getIdContrato();
			}

			guardarUltimaMoodificacion(idContrato);

			return "OK";
		} catch (ContratoException e) {
			throw e;
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR, e);
		}
	}

	@Override
	public String editarParticipantesAdminContrato(
			List<ParticipantesAdministracionModel> participantesAdministracionModels) {

		try {
			for (ParticipantesAdministracionModel participante : participantesAdministracionModels) {
				if (participante.getIdParticipantesAdministracionContrato() == null) {
					throw new ContratoException(ErroresEnum.ERROR_AL_ACTUALIZAR_ARCHIVO);
				}

				validarFechasParticipante(participante);
				participante.setEstatus(true);
				participantesAdministracionContratoRepository.save(participante);

				String movimiento = contratosPistasConsumer.movimientoParticipantesContrato(participante);
				this.contratoService.actualizarUltimaMod(participante.getIdContrato());

				// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.ACTUALIZA_REGISTRO.getId(),

				// TipoSeccionPista.CONTRATOS_DATOS_GENERALES_PARTICIPANTES.getIdSeccionPista(), movimiento,

				// Optional.empty());
			}
			return "OK";
		} catch (ContratoException e) {
			throw e;
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

	@Override
	public List<ParticipantesAdminContratoDto> obtenerParticipantesAdminContrato(Long idContrato) {
		try {
			List<ParticipantesAdministracionModel> participantesAdministracionModels = participantesAdministracionContratoRepository
					.findAllByIdContratoAndEstatusTrue(idContrato);

			return setParticipantesAdminContrato(participantesAdministracionModels);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_CONSULTAR);
		}
	}

	@Override
	public String eliminarParticipantesAdminContrato(EliminarParticipantesDto eliminarParticipantesDto) {
		Long idContrato = eliminarParticipantesDto.getIdContrato();
		guardarUltimaMoodificacion(idContrato);
		try {
			for (Long idParticipanteContrato : eliminarParticipantesDto.getIdsParticipantes()) {
				ParticipantesAdministracionModel participantesAdministracion = participantesAdministracionContratoRepository
						.findByIdParticipantesAdministracionContrato(idParticipanteContrato);
				participantesAdministracion.setEstatus(false);
				participantesAdministracionContratoRepository.save(participantesAdministracion);

				String movimiento = contratosPistasConsumer
						.movimientoParticipantesContrato(participantesAdministracion);
				this.contratoService.actualizarUltimaMod(idContrato);

				// pistaService.guardarPista(ModuloPista.ADMIN_CONTRATOS.getId(), TipoMovPista.BORRA_REGISTRO.getId(),

				// TipoSeccionPista.CONTRATO_DATOS_GENERALES.getIdSeccionPista(), movimiento, Optional.empty());
			}

			return "OK";

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_ELIMINAR);
		}
	}

	private List<ParticipantesAdminContratoDto> setParticipantesAdminContrato(
			List<ParticipantesAdministracionModel> participantesAdministracionModels) {

		List<ParticipantesAdminContratoDto> participantesAdminContratoDtos = new ArrayList<>();

		for (ParticipantesAdministracionModel participantes : participantesAdministracionModels) {

			Integer idAdminGeneral = participantes.getIdAdmonGeneral();
			Integer idAdminCentral = participantes.getIdAdmonCentral();
			Integer idRentabilidad = participantes.getIdResponsabilidad();

			UsuarioInfoDto usuarioInfoDto = obtenerUsuarioDto(participantes);

			ParticipantesAdminContratoDto participantesAdminContratoDto = new ParticipantesAdminContratoDto();
			participantesAdminContratoDto
					.setIdParticipantesAdministracionContrato(participantes.getIdParticipantesAdministracionContrato());

			if (idAdminCentral != null) {
				CatAdmonCentral admonCentral = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.ADMINISTRACION_CENTRAL.getIdCatalogo(), idAdminCentral, new CatAdmonCentral());
				participantesAdminContratoDto.setAdministracionCentral(admonCentral);
			}
			if (idAdminGeneral != null) {
				CatAdmonGeneral admonGeneral = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.ADMINISTRACION_GENERAL.getIdCatalogo(), idAdminGeneral, new CatAdmonGeneral());
				participantesAdminContratoDto.setAdministracionGeneral(admonGeneral);
			}
			if (idRentabilidad != null) {
				CatResponsabilidad catResponsabilidad = catalogoMicroservicio.obtenerInformacionCatalogoIdReference(
						CatalogosComunes.RESPONSABILIDAD.getIdCatalogo(), idRentabilidad, new CatResponsabilidad());
				participantesAdminContratoDto.setResponsabolidad(catResponsabilidad);
			}

			participantesAdminContratoDto.setUsuarioInformacion(usuarioInfoDto);
			participantesAdminContratoDto.setVigente(participantes.getVigente());
			participantesAdminContratoDto.setFechaTermino(participantes.getFechaTermino());
			participantesAdminContratoDto.setFechaInicio(participantes.getFechaInicio());

			participantesAdminContratoDtos.add(participantesAdminContratoDto);
		}
		return participantesAdminContratoDtos;
	}

	@Override
	public UsuarioInfoDto obtenerUsuarioDto(ParticipantesAdministracionModel participante) {
		UsuarioInfoDto dto= new UsuarioInfoDto();
		Integer nivel = participante.getNivel();
		Integer idReferencia = participante.getIdReferencia();
		if (idReferencia != null && nivel != null) {
			dto.setIdUsuario(idReferencia);
			switch (nivel) {
			case 1:
				CatAdministradorGeneral general = adminGeneralRepository.findById(idReferencia).orElseThrow(() -> new ContratoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
				dto.setCorreo(general.getCorreo());
				dto.setNombre(general.getAdministrador());
				dto.setTelefono(general.getTelefono());
				dto.setNivel(1);
				break;

			case 2:
				CatAdministradorCentral central = adminCentralRepository.findById(idReferencia).orElseThrow(() -> new ContratoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
				dto.setCorreo(central.getCorreo());
				dto.setNombre(central.getAdministrador());
				dto.setTelefono(central.getTelefono());
				dto.setNivel(2);
				break;

			case 3:
				CatAdministradorAdministracion area = adminAdministracionRepository.findById(idReferencia).orElseThrow(() -> new ContratoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
				dto.setCorreo(area.getCorreo());
				dto.setNombre(area.getAdministrador());
				dto.setTelefono(area.getTelefono());
				dto.setNivel(2);
				break;

			case 4:
				CatEmpleadoAdministracion empleado = empleadoRepository.findById(idReferencia).orElseThrow(() -> new ContratoException(ErroresEnum.USUARIO_NO_ENCONTRADO));
				dto.setCorreo(empleado.getCorreo());
				dto.setNombre(empleado.getNombre());
				dto.setTelefono(empleado.getTelefono());
				dto.setNivel(4);
				break;
			default:
				return null;
			}
			return dto;
			
		}
		return null;
	}

	private void guardarProvedores(List<Long> proveedores, Long idContrato) {

		try {
			if (!proveedores.isEmpty()) {
				List<Long> idsProveedores = asociacionContratoProovedorRepository
						.findIdProveedorByIdContrato(idContrato);

				for (Long idProveedor : proveedores) {

					if (!idsProveedores.contains(idProveedor)) {
						AsociacionContratoProveedorModel asociacionContratoProveedor = new AsociacionContratoProveedorModel();
						asociacionContratoProveedor.setIdContrato(idContrato);
						asociacionContratoProveedor.setEstatus(true);
						asociacionContratoProveedor.setIdProveedor(idProveedor);
						asociacionContratoProovedorRepository.save(asociacionContratoProveedor);
					} else {
						AsociacionContratoProveedorModel aso = asociacionContratoProovedorRepository
								.findByIdContratoAndIdProveedor(idContrato, idProveedor);
						if (aso != null) {
							aso.setEstatus(true);
							asociacionContratoProovedorRepository.save(aso);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR_PROVEEDORES);
		}
	}

	public void eliminarProveedores(List<Long> proveedoresDto, Long idContrato) {

		List<AsociacionContratoProveedorModel> asociacionContratoProveedorModels = asociacionContratoProovedorRepository
				.findByIdContratoAndEstatusTrue(idContrato);
		if (asociacionContratoProveedorModels.isEmpty()) {
			throw new ContratoException(ErroresEnum.ERROR_ULTIMO_PROVEEDOR);
		}

		if (!proveedoresDto.isEmpty()) {
			for (Long idProvedor : proveedoresDto) {
				AsociacionContratoProveedorModel asociacionContratoProveedorModel = asociacionContratoProovedorRepository
						.findByIdContratoAndIdProveedor(idContrato, idProvedor);
				if (asociacionContratoProveedorModel != null) {
					asociacionContratoProveedorModel.setEstatus(false);
					asociacionContratoProovedorRepository.save(asociacionContratoProveedorModel);
				}
			}
		}
	}

	private Usuario obtenerUsuario() {
		Optional<Usuario> usuario = session.retornarUsuario();
		if (usuario.isPresent()) {
			return usuario.get();
		} else {
			throw new ContratoException(ErroresEnum.ERROR_USUARIO_NO_ENCONTRADO);
		}
	}

	private void guardarUltimaMoodificacion(Long idContrato) {
		ContratoModel contratoModel = contratoRepository.findByIdContratoAndEstatusTrue(idContrato)
				.orElseThrow(() -> new ContratoException(ErroresEnum.CONTRATO_NO_ENCONTRADO));
		try {
			Usuario usuario = obtenerUsuario();
			contratoModel.setUltimoModificador(usuario.getNombre());
			contratoModel.setFechaUltimaModificacion(LocalDateTime.now());
			contratoModel.setEstatus(true);
			contratoRepository.save(contratoModel);
		} catch (Exception e) {
			throw new ContratoException(ErroresEnum.ERROR_AL_GUARDAR);
		}
	}

}
