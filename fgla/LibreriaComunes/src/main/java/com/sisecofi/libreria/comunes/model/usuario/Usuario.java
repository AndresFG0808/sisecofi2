package com.sisecofi.libreria.comunes.model.usuario;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.dto.reportedinamico.EmpleadoDto;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "usuario", indexes = {
		@Index(name = "IDX_ESTATUS_USUARIO", columnList = "estatus", unique = false) })
@NamedNativeQuery(name = "Usuario.buscarUsuariosActivos", query = "select p.id_user as idUser,p.nombre as nombre from sisecofi.sscft_usuario p where p.estatus=:estatus", resultSetMapping = "empleadoDto")
@SqlResultSetMapping(name = "empleadoDto", classes = @ConstructorResult(targetClass = EmpleadoDto.class, columns = {
		@ColumnResult(name = "idUser"), @ColumnResult(name = "nombre") }))
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "rfcCorto", nullable = false, unique = true)
	private String rfcCorto;

	@Column(name = "rfcLargo", nullable = true, unique = true)
	private String rfcLargo;

	@Column(name = "administracion", nullable = false)
	private String administracion;

	@Column(name = "estatus")
	private boolean estatus;

	@Column(name = "correo")
	private String correo;

	@Column(name = "telefono")
	private Integer telefono;

	@Column(name = "puesto")
	private String puesto;

	/* se utilizan para springsecurity se borran despues */
	@JsonIgnore
	@Column(name = "userName", nullable = true, length = 50, unique = false)
	private String userName;

	@JsonIgnore
	@Column(name = "password", nullable = true, length = 200)
	private String password;

	@JsonIgnore
	@Column(name = "enabled", nullable = true)
	private boolean enabled;
	/* se utilizan para springsecurity se borran despues */

	@Column(name = "nombres")
	private String nombres;

	@Column(name = "apellido_paterno")
	private String apellidoPaterno;

	@Column(name = "apellido_materno")
	private String apellidoMaterno;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = ConstantesComunes.PREFIX_REL
			+ "usuario_rol", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "idUser"), inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol"))
	private List<RolModel> rolModels;

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRfcCorto() {
		return rfcCorto;
	}

	public void setRfcCorto(String rfcCorto) {
		this.rfcCorto = rfcCorto;
	}

	public String getAdministracion() {
		return administracion;
	}

	public void setAdministracion(String administracion) {
		this.administracion = administracion;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEstatus() {
		return estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	public List<RolModel> getRolModels() {
		return rolModels;
	}

	public void setRolModels(List<RolModel> rolModels) {
		this.rolModels = rolModels;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	
	

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUser);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(idUser, other.idUser);
	}

	@Override
	public String toString() {
		return nombre;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public String getRfcLargo() {
		return rfcLargo;
	}

	public void setRfcLargo(String rfcLargo) {
		this.rfcLargo = rfcLargo;
	}
}
