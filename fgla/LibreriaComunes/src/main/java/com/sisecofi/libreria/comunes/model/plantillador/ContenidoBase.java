package com.sisecofi.libreria.comunes.model.plantillador;



import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class ContenidoBase {

	@Column(name = "header", nullable = false, length = 2000000)
	private String header;

	@Column(name = "footer", nullable = false, length = 2000000)
	private String footer;

	@Column(name = "contenido", nullable = false, length = 2000000)
	private String contenido;
	
}
