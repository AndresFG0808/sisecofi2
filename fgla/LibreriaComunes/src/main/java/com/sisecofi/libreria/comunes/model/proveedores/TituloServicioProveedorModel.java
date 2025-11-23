package com.sisecofi.libreria.comunes.model.proveedores;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sisecofi.libreria.comunes.dto.reportedinamico.TituloServicioPreveedorDto;
import com.sisecofi.libreria.comunes.model.catalogo.CatEstatusTituloServicio;
import com.sisecofi.libreria.comunes.model.catalogo.CatTituloServicio;
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

@Entity
@Table(name = ConstantesComunes.PREFIX_CAT + "tituloServicioProveedor")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedNativeQuery(name = "TituloServicioProveedorModel.findByIdProveedor", query = "select p.id_titulo_servicio_proveedor as idTituloServicioProveedor,p.numero_titulo as numeroTitulo from sisecofi.sscfc_titulo_servicio_proveedor p where p.id_proveedor IN(:idProveedor) ", resultSetMapping = "tituloServicioPreveedorDto")
@SqlResultSetMapping(name = "tituloServicioPreveedorDto", classes = @ConstructorResult(targetClass = TituloServicioPreveedorDto.class, columns = {
		@ColumnResult(name = "idTituloServicioProveedor"), @ColumnResult(name = "numeroTitulo") }))
public class TituloServicioProveedorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTituloServicioProveedor;

	@NotBlank(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Size(max = 10)
	@Column(name = "numeroTitulo")
	private String numeroTitulo;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "vencimientoTitulo")
	private Date vencimientoTitulo;

	@Size(max = 250)
	@Column(name = "comentarios")
	private String comentarios;

	@Column(name = "vigencia")
	private String vigencia;

	@Column(name = "colorVigencia")
	private String colorVigencia;

	
	@ManyToOne
	@JoinColumn(name = "idTituloServicio", foreignKey = @ForeignKey(name = "FK_idTituloServicio"), nullable = false)
	private CatTituloServicio tituloServicioModel;

	@ManyToOne
	@JoinColumn(name = "idEstatusTituloServicio", foreignKey = @ForeignKey(name = "FK_idEstatusTituloServicio"), nullable = false)
	private CatEstatusTituloServicio catEstatusTitulosServicioModel;

	@ManyToOne
	@JoinColumn(name = "idProveedor", foreignKey = @ForeignKey(name = "FK_idProveedor"), nullable = true)
	@JsonBackReference
	private ProveedorModel proveedorModel;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean estatusEliminacionLogicaTitulo;

	@Column(name="orden_titulo")
	private Integer ordenTitulo;

	@Column(name = "fechaBajaTitulo")
	private LocalDateTime fechaBajaTitulo;

}
