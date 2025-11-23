package com.sisecofi.admindevengados.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.model.dictamenes.Dictamen;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.dictamenes.ArchivoPlantillaDictamenModel;
import com.sisecofi.libreria.comunes.util.ConstantesComunes;
import com.sisecofi.libreria.comunes.util.enums.ErroresEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = ConstantesComunes.PREFIX_TRAB + "soporte_Documental")
@Getter
@Setter
public class SoporteDocumentalModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSoporteDocumento;

	@Column(name = "pathPenasDeducciones")
	private String pathPenasDeducciones;
	
	@Column(name = "nombrePenasDeducciones")
	private String nombrePenasDeducciones;

	@Column(name = "pathEntregables")
	private String pathEntregables;
	
	@Column(name = "nombreEntregables")
	private String nombreEntregables;

	@Column(name = "numeroOficio")
	private String numeroOficio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "fechaSolicitudDictamen")
	private LocalDateTime fechaSolicitudDictamen;

	@Column(name = "pathOficio")
	private String pathOficio;//SE HARA UN GET AL ARCHIVO DE LA PLANTILLA
	
	@Column(name = "nombreOficio")
	private String nombreOficio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "fechaRecepcionDictamen")
	private LocalDateTime fechaRecepcionDictamen;

	@JoinColumn(name = "idDictamen", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Dictamen.class, fetch = FetchType.LAZY)
	@JsonIgnore
	private Dictamen dictamen;

	@NotNull(message = ErroresEnum.MensajeValidation.MENSAJE)
	@Column(name = "idDictamen")
	private Long idDictamen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_archivo_pdf", foreignKey = @ForeignKey(name = "FK_id_archivo_pdf"))
	@JsonIgnore
	private ArchivoPlantillaDictamenModel archivoPdf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_archivo_xlsx", foreignKey = @ForeignKey(name = "FK_id_archivo_xlsx"))
	@JsonIgnore
	private ArchivoPlantillaDictamenModel archivoXlsx;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_archivo_Zip", foreignKey = @ForeignKey(name = "FK_id_archivo_Zip"))
	@JsonIgnore
	private ArchivoPlantillaDictamenModel archivoZip;
}
