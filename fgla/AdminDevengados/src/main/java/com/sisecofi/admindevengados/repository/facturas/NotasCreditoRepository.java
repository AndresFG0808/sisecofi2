package com.sisecofi.admindevengados.repository.facturas;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sisecofi.libreria.comunes.model.dictamenes.factura.NotaCreditoModel;

public interface NotasCreditoRepository extends JpaRepository<NotaCreditoModel, Long> {
	
	@Query("SELECT n FROM NotaCreditoModel n " +
	           "WHERE n.idDictamen = :dictamenId " +
	           "AND n.estatus = true ")
	    List<NotaCreditoModel> findByIdDictamenAndEstatusTrue(@Param("dictamenId") Long dictamenId);
	
	List<NotaCreditoModel> findByDictamenContratoModelIdContrato(Long idContrato);
		
	@Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM NotaCreditoModel d WHERE d.idDictamen = :dictamenId AND d.catEstatusNotaCredito.nombre <> 'Cancelada'")
	boolean existsByDictamenIdAndEstatusNombre(@Param("dictamenId")Long dictamenId);
	
	@Query("SELECT MAX(f.consecutivo) FROM NotaCreditoModel f WHERE f.idDictamen = :dictamenId AND f.estatus = true")
    Integer obtenerMaxConsecutivoActivoPorDictamen(@Param("dictamenId")Long dictamenId);
		
	Optional<NotaCreditoModel> findByFolioAndComprobanteFiscalAndDictamenContratoModelIdContratoNot(String folio,
			String comprobanteFiscal, Long idContrato);
	
	@Query("SELECT d FROM NotaCreditoModel d WHERE d.dictamen.idDictamen = :dictamenId AND d.dictamen.estatus = true AND d.catEstatusNotaCredito.nombre <> 'Cancelada'")
	List<NotaCreditoModel> findByDictamenIdAndDictamenEstatusTrue(Long dictamenId);

	@Query("SELECT d FROM NotaCreditoModel d WHERE d.idDictamen = :dictamenId AND d.catEstatusNotaCredito.nombre <> 'Cancelada'")
	  List<NotaCreditoModel> notasNoCanceladas(Long dictamenId);
	
	@Query("SELECT n FROM NotaCreditoModel n WHERE n.folio = :folio AND n.comprobanteFiscal = :comprobanteFiscal AND n.catEstatusNotaCredito.nombre <> 'Cancelada'")
	Optional<NotaCreditoModel> findByFolioAndComprobanteFiscal(@Param("folio") String folio,
	                                                           @Param("comprobanteFiscal") String comprobanteFiscal);
	
	List<NotaCreditoModel> findByDictamenContratoModelProyectoIdProyectoAndCatEstatusNotaCreditoNombreNot(Long idProyecto, String estatus);

}
