package com.sisecofi.libreria.comunes.model.pista;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_PISTA + "pista", indexes = {
		@Index(name = "IDX_PISTA_BUSQUEDA", columnList = "idPista, id_modulo_pista, id_seccion_pista", unique = true) })
@Getter
@Setter
public class PistaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPista;

	@ManyToOne
	@JoinColumn(name = "id_modulo_pista", foreignKey = @ForeignKey(name = "FK_id_modulo_pista"), nullable = false)
	private CatModuloPistaModel moduloPistaModel;

	@ManyToOne
	@JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "FK_id_user"), nullable = false)
	@JsonIgnore
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_tipo_mov_pista", foreignKey = @ForeignKey(name = "FK_id_tipo_mov_pista"), nullable = false)
	private CatTipoMovPistaModel tipoMovPistaModel;

	@ManyToOne
	@JoinColumn(name = "id_seccion_pista", foreignKey = @ForeignKey(name = "FK_id_seccion_pista"))
	private CatSeccionPistaModel seccionPistaModel;

	@Column(name = "fechaMovimiento", nullable = false)
	private LocalDateTime fechaMovimiento;

	@Column(name = "objectoAfectado")
	@JsonIgnore
	private String objectoAfectado;

	@Column(name = "ip", nullable = false)
	private String ip;

	@Column(name = "idAfectado")
	@JsonIgnore
	private String idAfectado;

	@Column(name = "movimiento", nullable = false, length = 120000)
	private String movimiento;

	private transient Object pista;

	@Override
	public String toString() {
		return "PistaModel [idPista=" + idPista + ", moduloPistaModel=" + moduloPistaModel + ", usuario=" + usuario
				+ ", tipoMovPistaModel=" + tipoMovPistaModel + ", fechaMovimiento=" + fechaMovimiento
				+ ", objectoAfectado=" + objectoAfectado + ", ip=" + ip + ", idAfectado=" + idAfectado + ", movimiento="
				+ movimiento + "]";
	}

}
