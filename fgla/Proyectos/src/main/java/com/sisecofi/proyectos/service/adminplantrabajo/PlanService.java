package com.sisecofi.proyectos.service.adminplantrabajo;



import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


import com.sisecofi.proyectos.dto.adminplantrabajo.ListaTareas;


/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public interface PlanService  {

	

	List<ListaTareas> cargarPlan(MultipartFile file, Long idProyecto) throws IOException;

	

}
