package com.sisecofi.contratos.repository.contrato;

import com.sisecofi.libreria.comunes.dto.contrato.ProveedorDto;
import com.sisecofi.libreria.comunes.model.contratos.AsociacionContratoProveedorModel;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AsociacionContratoProovedorRepository extends JpaRepository<AsociacionContratoProveedorModel, Long> {

	List<AsociacionContratoProveedorModel> findByIdContratoAndEstatusTrue(Long idContrato);

	List<AsociacionContratoProveedorModel> findByIdContrato(Long idContrato);

	@Query("SELECT a.idProveedor FROM AsociacionContratoProveedorModel a WHERE a.idContrato = :idContrato")
	List<Long> findIdProveedorByIdContrato(@Param("idContrato") Long idContrato);

	Optional<List<AsociacionContratoProveedorModel>> findByIdProveedor(Integer idProveedor);

	AsociacionContratoProveedorModel findByIdContratoProveedor(Long idContratoProveedor);

	AsociacionContratoProveedorModel findByIdContratoAndIdProveedor(Long idContrato, Long idProveedor);

	@Query("""
			    SELECT new com.sisecofi.libreria.comunes.dto.contrato.ProveedorDto(
			        p.idProveedor,
			        p.nombreProveedor
			    )
			    FROM AsociacionContratoProveedorModel acp
			    JOIN acp.proveedorModel p
			    WHERE acp.idContrato = :idContrato
			    AND acp.estatus = true
			    ORDER BY LOWER(p.nombreProveedor) ASC
			""")
	List<ProveedorDto> findProveedoresByContrato(@Param("idContrato") Long idContrato);

}
