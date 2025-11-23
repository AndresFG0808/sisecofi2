package com.sisecofi.catalogos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sisecofi.catalogos.repository.AdministradorAdministracionRepository;
import com.sisecofi.catalogos.repository.AdministradorCentralRepository;
import com.sisecofi.catalogos.repository.AdministradorGeneralRepository;
import com.sisecofi.catalogos.service.PistaService;
import com.sisecofi.catalogos.service.ReporteService;
import com.sisecofi.catalogos.service.ServicioCatalogo;
import com.sisecofi.catalogos.service.ServicioCatalogoComp;
import com.sisecofi.catalogos.util.consumer.ReporteCatConsumer;
import com.sisecofi.catalogos.util.enums.Catalogos;
import com.sisecofi.catalogos.util.enums.ErroresEnum;
import com.sisecofi.catalogos.util.exception.CatalogoException;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorAdministracion;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorCentral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdministradorGeneral;
import com.sisecofi.libreria.comunes.model.catalogo.CatAdmonCentral;
import com.sisecofi.libreria.comunes.util.enums.ModuloPista;
import com.sisecofi.libreria.comunes.util.enums.TipoMovPista;
import com.sisecofi.libreria.comunes.util.enums.TipoSeccionPista;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

	private static final String CAT = "Catálogo administradores";

	private final ServicioCatalogo catalogo;
	private final ReporteCatConsumer catConsumer;
	private final ServicioCatalogoComp catalogoComp;
	private final AdministradorGeneralRepository administradorGeneralRepository;
	private final AdministradorCentralRepository administradorCentralRepository;
	private final AdministradorAdministracionRepository administracionRepository;
	private final PistaService pistaService;
	private static final String NOMBRE_CATALOGO = "Nombre del catálogo o sección que lo invoca: ";
	private static final String ACRONIMO = "Acrónimo: ";
	private static final String FECHA_MODIFICACION = "fecha modificación: ";
	private static final String ESTADO = "estado: ";
	private static final String ACTIVO = "Activo";
	private static final String INACTIVO = "Inactivo";
	private static final String ADMINISTRADOR = "administrador: ";
	private static final String FECHA_INICIO = "Fecha inicio vigencia: ";
	private static final String FECHA_FIN = "Fecha fin vigencia: ";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public byte[] obtenerReporte(int idCatalogo) {
		Optional<Catalogos> cat = Catalogos.obtenerCatalogo(idCatalogo);
		if (cat.isPresent()) {
			List informacion = catalogo.obtenerInformacionReporte(idCatalogo);
			if (!informacion.isEmpty()) {
				catConsumer.inializar("Catálogo " + cat.get().getCatalogosComunes().getNombreCatalogo());
				catConsumer.agregarCabeceras(informacion.get(0));
				informacion.stream().forEach(catConsumer);
				return catConsumer.cerrarBytes();
			}
		}
		throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE);
	}

	@Override
	public byte[] obtenerAdministracion(int id) {
		List<CatAdministracion> administracions = catalogoComp.obtenerAdministracion(id);

		if (!administracions.isEmpty()) {
			catConsumer.inializar("Catálogo administración");
			catConsumer.agregarCabeceras(administracions.get(0));
			administracions.forEach(catConsumer);

			StringBuilder registros = new StringBuilder();
			administracions.forEach(admon -> registros.append(NOMBRE_CATALOGO)
					.append(admon.getCatAdmonCentral().getAdministracion()).append(" | ").append(ACRONIMO)
					.append(admon.getAcronimo()).append(" | ").append("id: ").append(admon.getIdAdministracion())
					.append(" | ").append("administración: ").append(admon.getAdministracion()).append(" | ")
					.append("acrónimo: ").append(admon.getAcronimo()).append(" | ").append("puesto: ")
					.append(admon.getPuesto()).append(" | ").append("fecha creación: ").append(admon.getFechaCreacion())
					.append(" | ").append(FECHA_MODIFICACION).append(admon.getFechaModificacion()).append(" | ")
					.append(ESTADO).append(admon.isEstatus() ? ACTIVO : INACTIVO).append(" | ")
					.append(ADMINISTRADOR).append(admon.obtenerAdministrador()).append("|"));

			pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), registros.toString(),
					Optional.empty());

			return catConsumer.cerrarBytes();
		}

		throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE);
	}

	@Override
	public byte[] obtenerCentral(int id) {
		List<CatAdmonCentral> admonCentrals = catalogoComp.obtenerCentral(id);
		if (!admonCentrals.isEmpty()) {
			catConsumer.inializar("Catálogo central");
			catConsumer.agregarCabeceras(admonCentrals.get(0));
			admonCentrals.stream().forEach(catConsumer);
			
			StringBuilder registros = new StringBuilder();
			admonCentrals.forEach(admon -> registros.append(NOMBRE_CATALOGO)
					.append(admon.getCatAdmonGeneral().getAdministracion()).append(" | ").append(ACRONIMO)
					.append(admon.getAcronimo()).append(" | ").append("id: ").append(admon.getIdAdmonCentral())
					.append(" | ").append("administración: ").append(admon.getAdministracion()).append(" | ")
					.append("acrónimo: ").append(admon.getAcronimo()).append(" | ").append("puesto: ")
					.append(admon.getPuesto()).append(" | ").append("fecha creación: ").append(admon.getFechaCreacion())
					.append(" | ").append(FECHA_MODIFICACION).append(admon.getFechaModificacion()).append(" | ")
					.append(ESTADO).append(admon.isEstatus() ? ACTIVO : INACTIVO).append(" | ")
					.append(ADMINISTRADOR).append(admon.obtenerAdministrador()).append("|"));

			pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), registros.toString(),
					Optional.empty());
			
			return catConsumer.cerrarBytes();
		}
		throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] obtenerMapas(int idCentral) {
		List informacion = catalogoComp.obtenerMapas(idCentral);
		if (!informacion.isEmpty()) {
			catConsumer.inializar("Catálogo mapas");
			catConsumer.agregarCabeceras(informacion.get(0));
			informacion.stream().forEach(catConsumer);
			return catConsumer.cerrarBytes();
		}
		throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE);
	}

	@Override
	public byte[] obtenerAdministradoresGeneral(int id) {
		List<CatAdministradorGeneral> admin = administradorGeneralRepository.findByCatAdmonGeneralIdAdmonGeneralOrderByIdAdministradorGeneralAsc(id);
		if (!admin.isEmpty()) {
			catConsumer.inializar(CAT);
			catConsumer.agregarCabeceras(admin.get(0));
			admin.stream().forEach(catConsumer);
			
			StringBuilder registros = new StringBuilder();
			admin.forEach(admon -> registros.append(NOMBRE_CATALOGO)
					.append(admon.getCatAdmonGeneral().getAdministracion()).append(" | ")
					.append(ACRONIMO).append(admon.getCatAdmonGeneral().getAcronimo()).append(" | ")
					.append("id: ").append(admon.getIdAdministradorGeneral()).append(" | ")
					.append(ADMINISTRADOR).append(admon.getAdministrador()).append(" | ")
					.append(FECHA_INICIO).append(admon.getFechaInicioVigencia()).append(" | ")
					.append(FECHA_FIN).append(admon.getFechaFinVigencia()).append(" | ")
					.append(FECHA_MODIFICACION).append(admon.getFechaModificacion()).append(" | ")
					.append(ESTADO).append(admon.isEstatus() ? ACTIVO : INACTIVO).append(" | "));

			pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), registros.toString(),
					Optional.empty());
			
			return catConsumer.cerrarBytes();
		}
		throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE);
	}

	@Override
	public byte[] obtenerAdministradoresCentral(int id) {
		List<CatAdministradorCentral> admin = administradorCentralRepository.findByCatAdmonCentralIdAdmonCentralOrderByIdAdministradorCentralAsc(id);
		if (!admin.isEmpty()) {
			catConsumer.inializar(CAT);
			catConsumer.agregarCabeceras(admin.get(0));
			admin.stream().forEach(catConsumer);
			
			StringBuilder registros = new StringBuilder();
			admin.forEach(admon -> registros.append(NOMBRE_CATALOGO)
					.append(admon.getCatAdmonCentral().getAdministracion()).append(" | ")
					.append(ACRONIMO).append(admon.getCatAdmonCentral().getAcronimo()).append(" | ")
					.append("id: ").append(admon.getIdAdministradorCentral()).append(" | ")
					.append(ADMINISTRADOR).append(admon.getAdministrador()).append(" | ")
					.append(FECHA_INICIO).append(admon.getFechaInicioVigencia()).append(" | ")
					.append(FECHA_FIN).append(admon.getFechaFinVigencia()).append(" | ")
					.append(FECHA_MODIFICACION).append(admon.getFechaModificacion()).append(" | ")
					.append(ESTADO).append(admon.isEstatus() ? ACTIVO : INACTIVO).append(" | "));

			pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), registros.toString(),
					Optional.empty());
			
			return catConsumer.cerrarBytes();
		}
		throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE);
	}

	@Override
	public byte[] obtenerAdministradoresAdministracion(int id) {
		List<CatAdministradorAdministracion> admin = administracionRepository
				.findByCatAdministracionIdAdministracionOrderByIdAdministradorAdministracionAsc(id);
		if (!admin.isEmpty()) {
			catConsumer.inializar(CAT);
			catConsumer.agregarCabeceras(admin.get(0));
			admin.stream().forEach(catConsumer);

			StringBuilder registros = new StringBuilder();
			admin.forEach(admon -> registros.append(NOMBRE_CATALOGO)
					.append(admon.getCatAdministracion().getCatAdmonCentral().getAdministracion()).append(" | ")
					.append(ACRONIMO).append(admon.getCatAdministracion().getAcronimo()).append(" | ")
					.append("id: ").append(admon.getIdAdministradorAdministracion()).append(" | ")
					.append(ADMINISTRADOR).append(admon.getAdministrador()).append(" | ")
					.append(FECHA_INICIO).append(admon.getFechaInicioVigencia()).append(" | ")
					.append(FECHA_FIN).append(admon.getFechaFinVigencia()).append(" | ")
					.append(FECHA_MODIFICACION).append(admon.getFechaModificacion()).append(" | ")
					.append(ESTADO).append(admon.isEstatus() ? ACTIVO : INACTIVO).append(" | "));

			pistaService.guardarPista(ModuloPista.ADMIN_CATALOGOS.getId(), TipoMovPista.IMPRIME_REGISTRO.getId(),
					TipoSeccionPista.CATALOGOS_COMPLEMENTARIOS.getIdSeccionPista(), registros.toString(),
					Optional.empty());

			return catConsumer.cerrarBytes();
		}
		throw new CatalogoException(ErroresEnum.ERROR_MSJ_REPORTE);
	}

}
