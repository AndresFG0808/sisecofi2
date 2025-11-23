package com.sisecofi.admindevengados.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisecofi.admindevengados.dto.DictamenBusquedaDTO;
import com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

public interface DictamenRepository extends JpaRepository<Dictamen, Long> {

	Optional<Dictamen> findByIdDictamen(Long dictamenId);
	
	List<Dictamen> findByIdDictamenAndEstatusTrue(Long dictamenId);
	
	@Query("SELECT COALESCE(MAX(d.consecutivo), 0) + 1 " +
		       "FROM Dictamen d " +
		       "WHERE d.idContrato = :idContrato " +
		       "AND d.idProveedor = :idProveedor " +
		       "AND d.estatus = true")
		Integer obtenerSiguienteConsecutivo(@Param("idContrato") Long idContrato,
		                                    @Param("idProveedor") Long idProveedor);
	
	@Query("SELECT COALESCE(MAX(d.consecutivo), 0) + 1 " +
		       "FROM Dictamen d " +
		       "WHERE d.contratoModel.nombreContrato = :nombreCorto " +
		       "AND d.idProveedor = :idProveedor " +
		       "AND d.estatus = true")
		Integer obtenerSiguienteConsecutivoNombreCorto(@Param("nombreCorto") String nombreCorto,
		                                    @Param("idProveedor") Long idProveedor);
	
	boolean existsByIdDictamen(Long dictamenId);


	List<Dictamen> findByIdContratoAndIdProveedorOrderByIdDictamenAsc(Long idContrato, Long idProveedor);

	List<Dictamen> findByIdContratoAndEstatusTrueOrderByIdDictamenAsc(Long idContrato);

	List<Dictamen> findByCatEstatusDictamenNombreAndIdContrato(String nombreEstatus, Long idContrato);

	List<Dictamen> findByContratoModelIdContratoAndIdDictamen(Long idContrato, Long dictamenId);

	List<Dictamen> findByIdContratoAndIdEstatusDictamenAndIdProveedorAndEstatusOrderByIdDictamenAsc(Long idContrato,
			Integer idEstatusDictamen, Long idProveedor, boolean estatus);
	
			@Query("SELECT new com.sisecofi.admindevengados.dto.DictamenBusquedaDTO(" +
			"d.idDictamen, " +
			"d.idPeriodoControlAnio," +
			"d.idPeriodoControlMes, " +
			"d.catPeriodoControlMes.nombre, " +
			"d.catPeriodoControlAnio.nombre, " +
			"CONCAT(d.catPeriodoControlMes.nombre, ' ', d.catPeriodoControlAnio.nombre), " +
			"d.periodoInicio," +
			"d.periodoFin," +
			"e.nombre, " +
			"d.idContrato, d.idProveedor, d.contratoModel.nombreCorto, d.consecutivo) " +
			"FROM Dictamen d " +
			"JOIN d.catEstatusDictamen e " +  
			"JOIN d.contratoModel c " +  
			"WHERE d.idContrato = :idContrato " +
			"AND (COALESCE(:idEstatusDictamen, d.idEstatusDictamen) = d.idEstatusDictamen) " +
			"AND d.idProveedor = :idProveedor " +
			"AND d.estatus = true " +
			"ORDER BY d.idDictamen ASC")
	 List<DictamenBusquedaDTO> findDictamenesOptimizado(
			 @Param("idContrato") Long idContrato,
			 @Param("idEstatusDictamen") Integer idEstatusDictamen,
			 @Param("idProveedor") Long idProveedor);

	List<Dictamen> findByIdContratoAndIdProveedorAndEstatusOrderByIdDictamenAsc(Long idContrato, Long idProveedor, boolean estatus);

	default Dictamen findLastDictamen() {
		Page<Dictamen> page = findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "idDictamen")));
		return page.getContent().stream().findFirst().orElse(null);
	}

	List<Dictamen> findByIdContratoAndIdProveedorAndIdPeriodoControlAnioAndIdPeriodoControlMesAndEstatusTrueOrderByIdDictamenAsc(
			Long idContrato, Long idProveedor, Integer idPeriodoAnio, Integer idPeriodoMes);
	
	List<Dictamen> findByContratoModelNombreCortoAndIdProveedorAndIdPeriodoControlAnioAndIdPeriodoControlMesAndEstatusTrue(
			String nombreCorto, Long idProveedor, Integer idPeriodoAnio, Integer idPeriodoMes);
	
	List<Dictamen> findByContratoModelNombreCortoAndIdProveedorAndAndEstatusTrueAndCatEstatusDictamenNombreNotIn(
			String nombreCorto, Long idProveedor,  List<String> estados);
	
	List<Dictamen> findByContratoModelProyectoIdProyecto(Long idProyecto);
	
	@Query("SELECT new com.sisecofi.libreria.comunes.dto.dictamen.dictamenDto( " +
		       "d.idDictamen, d.idEstatusDictamen, d.estatus, " +
		       "d.idContrato, d.idProveedor, c.nombreCorto, " +
		       "d.periodoInicio, d.periodoFin, d.idPeriodoControlMes, d.idPeriodoControlAnio, " +
		       "m.idPeriodoControlMes, CAST(a.nombre AS integer), " +
		       "d.idIva, d.tipoCambioReferencial, " +
		       "v.idTipoMoneda, tm.nombre, " +
		       "d.descripcion, d.ultimaModificacion, " +
		       "d.checkContractual, d.checkConvencional, d.checkDeducciones, " +
		       "d.plantillaAsignada, p.idProyecto, d.consecutivo, CAST(NULL AS BigDecimal), d.idConvenioCodificatorio) " +  
		       "FROM Dictamen d " +
		       "JOIN d.contratoModel c " +  
		       "LEFT JOIN c.proyecto p " +  
		       "JOIN c.vigencia v " +
		       "JOIN v.catTipoMoneda tm " +
		       "JOIN d.catPeriodoControlAnio a " +
		       "JOIN d.catPeriodoControlMes m " +
		       "WHERE d.idDictamen = :dictamenId")
		Optional<dictamenDto> findDictamenDtoById(@Param("dictamenId") Long dictamenId);


		
		@Modifying
		@Query("UPDATE Dictamen d SET d.plantillaAsignada = true WHERE d.idDictamen = :idDictamen")
		int actualizarPlantillaDictamen(@Param("idDictamen") Long idDictamen);
		
		
	Long countByEstatusTrueAndCatEstatusDictamenIdEstatusDictamenNotInAndContratoModelProyectoIdProyecto(
			    List<Integer> estatus, Long idProyecto);
	
	@Modifying
	@Query("UPDATE Dictamen d " +
	       "SET d.checkContractual = CASE WHEN :checkContractual IS NOT NULL THEN :checkContractual ELSE d.checkContractual END, " +
	       "    d.checkConvencional = CASE WHEN :checkConvencional IS NOT NULL THEN :checkConvencional ELSE d.checkConvencional END, " +
	       "    d.checkDeducciones = CASE WHEN :checkDeducciones IS NOT NULL THEN :checkDeducciones ELSE d.checkDeducciones END " +
	       "WHERE d.idDictamen = :idDictamen")
	void actualizarCheckPenasDeducciones(
	        @Param("idDictamen") Long idDictamen,
	        @Param("checkContractual") Boolean checkContractual,
	        @Param("checkConvencional") Boolean checkConvencional,
	        @Param("checkDeducciones") Boolean checkDeducciones
	);

	boolean existsByIdContratoAndEstatusTrue(Long idContrato);
	
}
