package com.sisecofi.contratos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Embeddable
public class ContratoProveedorPk {

	@Column(name = "id_proveedor", nullable = false)
	private Long idProveedor;

	@Column(name = "id_contrato", nullable = false)
	private Long idContrato;
}
