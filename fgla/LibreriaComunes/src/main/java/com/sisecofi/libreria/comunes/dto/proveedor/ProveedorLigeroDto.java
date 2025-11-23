package com.sisecofi.libreria.comunes.dto.proveedor;

import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "proveedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorLigeroDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProveedor;

	@Column(name = "nombreProveedor", unique = true)
	private String nombreProveedor;

	@Column(name = "nombreComercial")
	private String nombreComercial;

    @Column(name = "rfc", length = 13, nullable = true, unique = false)
    private String rfc;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "comentarios")
	private String comentarios;


	@Column(name = "estatus")
	private Boolean estatus;

	@Column(name = "idAgs", length = 9, unique = false, nullable = true)
	private String idAgs;
		
	}