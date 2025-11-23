package com.sisecofi.libreria.comunes.model.proveedores;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sisecofi.libreria.comunes.dto.reportedinamico.ProveedorRazonSocialDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatGiroEmpresarial;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "proveedor")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedNativeQuery(name = "ProveedorModel.findByEstatus", query = "select p.id_proveedor as idProveedor,p.nombre_proveedor as nombreComercial from sisecofi.sscft_proveedor p where p.estatus=:estatus", resultSetMapping = "proveedorRazonSocialDto")
@SqlResultSetMapping(name = "proveedorRazonSocialDto", classes = @ConstructorResult(targetClass = ProveedorRazonSocialDto.class, columns = {
		@ColumnResult(name = "idProveedor"), @ColumnResult(name = "nombreComercial") }))
public class ProveedorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProveedor;

	@NotBlank(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Size(max = 250)
	@Column(name = "nombreProveedor", unique = true)
	private String nombreProveedor;

	@NotBlank(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Size(max = 100)
	@Column(name = "nombreComercial")
	private String nombreComercial;

    @Column(name = "rfc", length = 13, nullable = true, unique = false)
    private String rfc;

	@Size(max = 250)
	@Column(name = "direccion")
	private String direccion;

	@Size(max = 250)
	@Column(name = "comentarios")
	private String comentarios;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "estatus")
	private Boolean estatus;

	@Column(name = "idAgs", length = 9, unique = false, nullable = true)
	private String idAgs;

	@ManyToOne
	@JoinColumn(name ="idGiroEmpresarial", foreignKey=@ForeignKey(name ="FK_idGiroEmpresarial"), nullable = false)
	private CatGiroEmpresarial giroEmpresarialModel;

	@OneToMany(mappedBy = "proveedorModel")
	private List<DirectorioProveedorModel> directorio;

	@OneToMany(mappedBy = "proveedorModel")
	private List<TituloServicioProveedorModel> tituloServicioProveedores;

	@OneToMany(mappedBy = "proveedorModel")
	private List<DictamenTecnicoModel> dictamenTecnico;
	
	public List<DirectorioProveedorModel> getDirectorio() {
	    if (directorio == null || directorio.isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<DirectorioProveedorModel> listaOrdenada = directorio.stream()
	            .filter(DirectorioProveedorModel::isEstatus)
	            .sorted(Comparator.comparing(DirectorioProveedorModel::getIdDirectorioContacto, Comparator.nullsLast(Comparator.naturalOrder())))
	            .toList();

	    for (int i = 0; i < listaOrdenada.size(); i++) {
	        listaOrdenada.get(i).setOrdenDirectorio(i + 1);
	    }

	    return listaOrdenada;
	}

	public List<TituloServicioProveedorModel> getTituloServicioProveedores() {
	    if (tituloServicioProveedores == null || tituloServicioProveedores.isEmpty()) {
	        return Collections.emptyList(); // Evita NullPointerException retornando una lista vacía
	    }

	    List<TituloServicioProveedorModel> listaOrdenada = tituloServicioProveedores.stream()
	            .filter(TituloServicioProveedorModel::isEstatusEliminacionLogicaTitulo)
	            .sorted(Comparator.comparing(TituloServicioProveedorModel::getIdTituloServicioProveedor, Comparator.nullsLast(Comparator.naturalOrder())))
	            .toList();

	    for (int i = 0; i < listaOrdenada.size(); i++) {
	        listaOrdenada.get(i).setOrdenTitulo(i + 1);
	    }

	    return listaOrdenada;
	}


	public List<DictamenTecnicoModel> getDictamenTecnico() {
	    if (dictamenTecnico == null || dictamenTecnico.isEmpty()) {
	        return Collections.emptyList(); 
	    }

	    List<DictamenTecnicoModel> listaOrdenada = dictamenTecnico.stream()
	            .filter(DictamenTecnicoModel::isEstatusEliminacionLogicaDictamen)
	            .sorted(Comparator.comparing(DictamenTecnicoModel::getIdDictamenTecnicoProveedor, Comparator.nullsLast(Comparator.naturalOrder())))
	            .toList();

	    for (int i = 0; i < listaOrdenada.size(); i++) {
	        listaOrdenada.get(i).setOrdenDictamenProveedor(i + 1);
	    }

	    return listaOrdenada;
	}

}
