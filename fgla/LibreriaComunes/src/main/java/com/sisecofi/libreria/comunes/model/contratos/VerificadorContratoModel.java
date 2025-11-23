package com.sisecofi.libreria.comunes.model.contratos;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "verificadorContrato")
@Getter
@Setter
public class VerificadorContratoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idVerificadorContrato;

	@Column(name = "nombreVerificador")
	private String nombreVerificador;
}
