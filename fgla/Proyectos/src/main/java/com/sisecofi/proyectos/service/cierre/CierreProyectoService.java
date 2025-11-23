package com.sisecofi.proyectos.service.cierre;

import java.util.List;

import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.model.gestionDocumental.archivos.Archivo;
import com.sisecofi.libreria.comunes.model.plantillador.PlantilladorModel;
import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.proyectos.dto.DescargaSatCloudRequest;
import com.sisecofi.proyectos.dto.cierre.DatosPlantillaRcp;
import com.sisecofi.proyectos.dto.cierre.ObtenerProyectodto;
import com.sisecofi.proyectos.model.cierre.CierreModel;
import org.springframework.core.io.ByteArrayResource;

public interface CierreProyectoService {

	boolean validarEstatus(Long idProyecto);

	ObtenerProyectodto obtenerDatosProyecto(Long idProyecto);

	String descargarArchivo(String path, String nombreCorto, String documentoSeleccionado, String fase);

	List<Usuario> obtenerUsuarios();

	List<Archivo> obtenerArchivosSeccion(Long idProyecto);

	List<Double> porcentajes(List<Archivo> archivos);

	CierreModel guardarCierre(CierreModel cierreModel, List<Archivo> archivos);

	CierreModel estatusEnProceso(Long idCierre);

	Boolean cancelarCierre(Long idCierre);

	CierreModel obtenerCierre(Long idProyecto);

	CierreModel revisadoAP(Long idCierre, List<Archivo> archivos);

	DatosPlantillaRcp generarMapas(Long idProyecto, Long idContenidoPlantillador);

	boolean validadoLp(Long idCierre);

	String descargaMasiva(DescargaSatCloudRequest descargaSatCloudRequest);

	CarpetaCompartidaDto descargaSatCloud(DescargaSatCloudRequest descargaSatCloudRequest);

	boolean generarRcp(Long idCierre);

	List<PlantilladorModel> obtenerPlantillasRcp();

	List<Archivo> obtenerOtrosComite(Long idProyecto);

	CierreModel modificarCierre(CierreModel cierreModel, List<Archivo> archivos);

	ByteArrayResource cierreProyectoDescargaArchivo(long idProyecto, long idPlantillador, String typeFile, Boolean plantilla);

	Boolean pistaVerPdf(String nombreCortoProyecto, String entregable, Boolean estatus);

	Boolean crearPistaPlantilla(String nombreCortoProyecto, String tipoPlantilla, Long idProyecto);

}
