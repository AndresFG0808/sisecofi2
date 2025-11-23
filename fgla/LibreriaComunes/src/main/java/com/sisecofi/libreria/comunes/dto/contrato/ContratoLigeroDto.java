package com.sisecofi.libreria.comunes.dto.contrato;


import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB  + "contrato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratoLigeroDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idContrato;
	@Column(name = "nombre_corto", length = 30, unique = true)
	private String nombreCorto;
	
	
	@Transient
	private String numeroContrato;
	
}
