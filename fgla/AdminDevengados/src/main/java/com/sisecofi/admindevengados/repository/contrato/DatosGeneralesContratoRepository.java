package com.sisecofi.admindevengados.repository.contrato;

import com.sisecofi.libreria.comunes.model.contratos.DatosGeneralesContratoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DatosGeneralesContratoRepository extends JpaRepository<DatosGeneralesContratoModel, Long> {

	@Query(value = """
			SELECT
			    CASE
			        WHEN d.id_convenio_colaboracion IS NULL
			             OR LOWER(cc.nombre) IN ('no aplica', 'sin desglose')
			        THEN FALSE
			        ELSE TRUE
			    END
			FROM sisecofi.sscft_datos_generales_contrato d
			LEFT JOIN sisecofi.sscfc_convenio_colaboracion cc
			    ON d.id_convenio_colaboracion = cc.id_convenio_colaboracion
			WHERE d.id_contrato = :idContrato
			""", nativeQuery = true)
	Boolean existeConvenioValido(@Param("idContrato") Long idContrato);

}
