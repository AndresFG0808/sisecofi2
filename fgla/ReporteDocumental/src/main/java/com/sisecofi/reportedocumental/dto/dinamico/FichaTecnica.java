package com.sisecofi.reportedocumental.dto.dinamico;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sisecofi.libreria.comunes.util.anotaciones.reportes.NameField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FichaTecnica {

	@NameField(nameField = "s12.acronimo", fieldComposite = true, order = 6, nombreFront = "Administración patrocinadora")
	private boolean administracionPatrocinadora;

	@NameField(nameField = "s12.administracion", fieldComposite = true, order = 7, nombreFront = "Nombre de la admón. patrocinadora")
	private boolean nombreAdministracionPatrocinadora;

	@NameField(nameField = "s29.administrador", fieldComposite = true, order = 8, nombreFront = "Administrador patrocinador")
	private boolean administracionPatrocinador;

	@NameField(nameField = "s21.acronimo", fieldComposite = true, order = 9, nombreFront = "Administración central patrocinadora")
	private boolean administracionCentralPatrocinadora;

	@NameField(nameField = "s21.administracion", fieldComposite = true, order = 10, nombreFront = "Nombre de la admón. central patrocinadora")
	private boolean nombreAdministracionCentralPatrocinadora;

	@NameField(nameField = "s30.administrador", fieldComposite = true, order = 11, nombreFront = "Administrador central patrocinador")
	private boolean administradorCentralPatrocinador;

	@NameField(nameField = "s19.acronimo", fieldComposite = true, order = 12, nombreFront = "Administración participante")
	private boolean administracionParticipante;

	@NameField(nameField = "s19.administracion", fieldComposite = true, order = 13, nombreFront = "Nombre de la admón. participante")
	private boolean nombreAdmonParticipante;

	@NameField(nameField = "s31.administrador", fieldComposite = true, order = 14, nombreFront = "Administrador participante")
	private boolean administradorParticipante;

	@NameField(nameField = "s22.nombre", fieldComposite = true, order = 15, nombreFront = "Clasificación proyecto")
	private boolean clasificacionProyecto;

	@NameField(nameField = "s13.nombre", fieldComposite = true, order = 16, nombreFront = "Financiamiento")
	private boolean financiamiento;

	@NameField(nameField = "s14.nombre", fieldComposite = true, order = 17, nombreFront = "Tipo de procedimiento")
	private boolean tipoProcedimiento;

	@NameField(nameField = "s32.nombre", fieldComposite = true, order = 18, nombreFront = "Lider del proyecto")
	private boolean liderProyecto;

	@NameField(nameField = "s32.puesto", fieldComposite = true, order = 19, nombreFront = "Puesto")
	private boolean puesto;

	@NameField(nameField = "s32.correo", fieldComposite = true, order = 20, nombreFront = "Correo")
	private boolean correo;

	@NameField(nameField = "s32.fecha_inicio", fieldComposite = true, order = 21, nombreFront = "Fecha inicio")
	private boolean fechaInicio;

	@NameField(nameField = "s32.fecha_fin", fieldComposite = true, order = 22, nombreFront = "Fecha fin")
	private boolean fechaFin;

	@NameField(nameField = "s32.estatus", fieldComposite = true, order = 23, nombreFront = "Estatus")
	private boolean estatus;

	@NameField(nameField = "s24.nombre", fieldComposite = true, order = 25, nombreFront = "Mapa")
	private boolean mapa;

	@NameField(nameField = "s26.nombre", fieldComposite = true, order = 26, nombreFront = "Periodo")
	private boolean periodo;

	@NameField(nameField = "s25.objetivo", fieldComposite = true, order = 27, nombreFront = "Objetivo")
	private boolean objetivo;

	@NameField(nameField = "fecha_inicio", order = 28, nombreFront = "Fecha inicio")
	private boolean fechaInicioProyecto;

	@NameField(nameField = "fecha_termino", order = 29, nombreFront = "Fecha fin")
	private boolean fechaFinProyecto;

	@NameField(nameField = "s27.administracion", fieldComposite = true, order = 30, nombreFront = "Area planeación")
	private boolean areaPlaneacion;

	@NameField(nameField = "monto_solicitado", order = 31, nombreFront = "Monto solicitado", function = "SUM(%valor)")
	private boolean montoSolicitado;

	@NameField(nameField = "s28.nombre", fieldComposite = true, order = 32, nombreFront = "Tipo moneda")
	private boolean tipoMoneda;

	@NameField(nameField = "objetivo_general", order = 33, nombreFront = "Objetivo general")
	private boolean objetivoGeneral;

	@NameField(nameField = "alcance", order = 34, nombreFront = "Alcance")
	private boolean alcance;

	@JsonIgnore
	private boolean requerido;
}
