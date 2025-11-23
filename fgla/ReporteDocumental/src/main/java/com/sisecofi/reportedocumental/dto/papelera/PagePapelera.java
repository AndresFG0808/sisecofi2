package com.sisecofi.reportedocumental.dto.papelera;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.CommonPageHelper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagePapelera implements CommonPageHelper<Papelera> {

	private List<Papelera> content;
	private int size;
	private int totalPages;
	private long totalElements;
	private boolean first;
	private int number;
	private int numberOfElements;
	private boolean last;
	
	public PagePapelera(PageGeneric page) {
	    initializeContent(page);
	}

	private void initializeContent(PageGeneric page) {
	    setContent(page.getContent().stream().map(m -> {
	        Papelera p1 = new Papelera();
	        p1.setIdPapelera(Long.parseLong(String.valueOf(m[10])));
	        p1.setIdProyecto(Long.parseLong(String.valueOf(m[11])));
	        p1.setIdProyectoFormateado(String.valueOf(m[0]));
	        p1.setNombreCorto(String.valueOf(m[1]));
	        p1.setFase(String.valueOf(m[2]));
	        p1.setPlantilla(String.valueOf(m[3]));
	        p1.setNombreDocumento(String.valueOf(m[4]));
	        p1.setDescripcion(String.valueOf(m[5]));
	        p1.setFechaEliminacion(String.valueOf(m[6]));
	        p1.setUsuarioElimina(String.valueOf(m[7]));
	        p1.setTamano(m[8] == null ? "" : String.format("%.3f",Double.parseDouble(String.valueOf(m[8]))) + "MB");
	        p1.setPathNuevo(String.valueOf(m[9]));
	        p1.setPathOriginal(String.valueOf(m[13]));
	        p1.setIdUsuario(Long.parseLong(String.valueOf(m[12])));
	        p1.setComentarios(String.valueOf(m[14]));
	        p1.setIdArchivo(m[15] == null ? null : Integer.parseInt(String.valueOf(m[15])));
	        p1.setTipoArchivo(m[16] == null ? null : String.valueOf(m[16]));
	        return p1;
	    }).toList());
	    setFirst(page.isFirst());
	    setLast(page.isLast());
	    setNumber(page.getNumber());
	    setNumberOfElements(page.getNumberOfElements());
	    setSize(page.getSize());
	    setTotalElements(page.getTotalElements());
	    setTotalPages(page.getTotalPages());
	}

	@Override
	public <U> Page<U> map(Function<? super Papelera, ? extends U> converter) {
	    return null;
	}

	@Override
	public Iterator<Papelera> iterator() {
	    return content.iterator();
	}

	
}
