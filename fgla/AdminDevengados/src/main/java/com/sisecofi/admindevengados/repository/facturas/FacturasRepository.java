package com.sisecofi.admindevengados.repository.facturas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.libreria.comunes.dto.dictamen.FacturasInformacion;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.FacturaModel;

public interface FacturasRepository extends JpaRepository<FacturaModel, Long> {

	List<FacturaModel> findByIdDictamenAndEstatusTrue(Long dictamenId);

	@Query("SELECT f.idFactura AS idFactura, " + 
		       "f.idDictamen AS idDictamen, f.folio AS folio, " +
		       "f.dictamen.catEstatusDictamen.nombre AS estatusDictamen, " +
		       "f.catEstatusFactura.nombre AS estatusFactura, " +
		       "f.dictamen.idProveedor AS idProveedor, " +
		       "f.dictamen.contratoModel.idContrato AS idContrato, " +
		       "f.dictamen.tipoCambioReferencial AS tipoCambioReferencial, " +
		       "f.montoCC AS montoCC, f.montoSat AS montoSat, " +
		       "f.montoPesosCC AS montoPesosCC, f.montoPesosSat AS montoPesosSat " +
		       "FROM FacturaModel f WHERE f.dictamen.contratoModel.idContrato = :idContrato")
		List<FacturasInformacion> findFacturaDataByContrato(@Param("idContrato") Long idContrato);

	
		@Query("SELECT f.idFactura AS idFactura, " + 
		       "f.dictamen.idDictamen AS idDictamen, f.folio AS folio, " +
		       "f.dictamen.catEstatusDictamen.nombre AS estatusDictamen, " +
		       "f.catEstatusFactura.nombre AS estatusFactura, " +
		       "f.dictamen.idProveedor AS idProveedor, " +
		       "f.dictamen.contratoModel.idContrato AS idContrato, " +
		       "f.dictamen.tipoCambioReferencial AS tipoCambioReferencial, " +
		       "f.montoCC AS montoCC, f.montoSat AS montoSat, " +
		       "f.montoPesosCC AS montoPesosCC, f.montoPesosSat AS montoPesosSat, " +
			   "f.catTipoMoneda.nombre AS tipoMoneda " +
		       "FROM FacturaModel f " +
		       "WHERE f.dictamen.idDictamen = :idDictamen " +
		       "AND f.catEstatusFactura.nombre <> 'Cancelado'")
		List<FacturasInformacion> findIdDictamenAndEstatusTrue(@Param("idDictamen") Long idDictamen);

	long countByIdDictamen(Long dictamenId);

	@Query("SELECT MAX(f.consecutivo) FROM FacturaModel f WHERE f.idDictamen = :dictamenId AND f.estatus = true")
	Integer obtenerMaxConsecutivoActivoPorDictamen(@Param("dictamenId") Long dictamenId);

	@Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM FacturaModel d WHERE d.idDictamen = :dictamenId AND d.catEstatusFactura.nombre <> 'Cancelado'")
	boolean existsByDictamenIdAndEstatusNombre(@Param("dictamenId") Long dictamenId);

	@Query("SELECT f FROM FacturaModel f WHERE f.folio = :folio AND f.comprobanteFiscal = :comprobanteFiscal "
			+ "AND f.dictamen.contratoModel.idContrato = :idContrato AND f.dictamen.idProveedor = :idProveedor AND f.catEstatusFactura.nombre <> 'Cancelado'")
	Optional<FacturaModel> findByFolioAndComprobanteFiscalAndDictamenContratoModelIdContratoAndDictamenIdProveedor(
			@Param("folio") String folio, @Param("comprobanteFiscal") String comprobanteFiscal,
			@Param("idContrato") Long idContrato, @Param("idProveedor") Long idProveedor);

	@Query("SELECT f FROM FacturaModel f WHERE f.folio = :folio AND f.comprobanteFiscal = :comprobanteFiscal AND f.dictamen.proveedorModel.idProveedor = :idProveedor AND f.dictamen.contratoModel.idContrato <> :idContrato AND f.catEstatusFactura.nombre <> 'Cancelado'")
	Optional<FacturaModel> findByFolioAndComprobanteFiscalAndDictamenIdProveedorNotInContrato(
			@Param("folio") String folio, @Param("comprobanteFiscal") String comprobanteFiscal,
			@Param("idProveedor") Long idProveedor, @Param("idContrato") Long idContrato);

	Optional<FacturaModel> findByFolioAndComprobanteFiscalAndDictamenContratoModelIdContratoNot(String folio,
			String comprobanteFiscal, Long idContrato);

			@Query("SELECT f.idFactura AS idFactura, " +
		       "f.idDictamen AS idDictamen, f.folio AS folio, " +
		       "f.dictamen.catEstatusDictamen.nombre AS estatusDictamen, " +
		       "f.catEstatusFactura.nombre AS estatusFactura, " +
		       "f.dictamen.idProveedor AS idProveedor, " +
		       "f.dictamen.contratoModel.idContrato AS idContrato, " +
		       "f.dictamen.tipoCambioReferencial AS tipoCambioReferencial, " +
		       "f.montoCC AS montoCC, f.montoSat AS montoSat, " +
		       "f.montoPesosCC AS montoPesosCC, f.montoPesosSat AS montoPesosSat, " +
			   "f.catTipoMoneda.nombre AS tipoMoneda " +
		       "FROM FacturaModel f WHERE f.dictamen IN :lista")
		List<FacturasInformacion> findFacturaDataByDictamen(@Param("lista") List<Dictamen> lista);




	@Query("SELECT d FROM FacturaModel d WHERE d.dictamen.idDictamen = :dictamenId AND d.dictamen.estatus = true AND d.catEstatusFactura.nombre <> 'Cancelado'")
	List<FacturaModel> findByDictamenIdAndDictamenEstatusTrue(Long dictamenId);

	List<FacturaModel> findByIdDictamen(Long idDictamen);

	Optional<FacturaModel> findByIdFactura(Long idFactura);
	
	@Query("SELECT f FROM FacturaModel f " + "WHERE f.idFactura = :idFactura " + "AND f.estatus = true "
			+ "AND f.catEstatusFactura.nombre <> 'Cancelado'")
	FacturaModel findByIdFacturaAndEstatusActivo(@Param("idFactura") Long idFactura);

	@Query("SELECT d FROM FacturaModel d WHERE d.idDictamen = :dictamenId AND d.catEstatusFactura.nombre <> 'Cancelado'")
	List<FacturaModel> facturasNoCanceladas(Long dictamenId);

	@Query("SELECT d FROM FacturaModel d WHERE d.idDictamen IN :dictamenIds AND d.catEstatusFactura.nombre <> 'Cancelado'")
	List<FacturaModel> facturasNoCanceladas(@Param("dictamenIds") List<String> dictamenIds);

	List<FacturaModel> findByDictamenContratoModelProyectoIdProyectoAndCatEstatusFacturaNombreNot(Long idProyecto,
			String estatus);

	@Query("SELECT f FROM FacturaModel f " + "WHERE f.idDictamen = :idDictamen " + "AND f.estatus = true "
			+ "AND f.catEstatusFactura.nombre <> 'Cancelado'")
	List<FacturaModel> findByIdDictamenAndEstatusTrueAndCatEstatusFacturaNombre(@Param("idDictamen") Long idDictamen);
	
	@Query("SELECT f.idDictamen, f.comprobanteFiscal, f.catEstatusFactura.nombre " +
		       "FROM FacturaModel f WHERE f.idDictamen IN :idDictamenes AND f.estatus = true")
		List<Object[]> findFacturasByIdDictamenIn(@Param("idDictamenes") List<Long> idDictamenes);

		@Query("SELECT n FROM FacturaModel n WHERE n.folio = :folio AND n.comprobanteFiscal = :comprobanteFiscal AND n.catEstatusFactura.nombre <> 'Cancelado'")
		Optional<FacturaModel> findByFolioAndComprobanteFiscal(@Param("folio") String folio,
																   @Param("comprobanteFiscal") String comprobanteFiscal);	

}
