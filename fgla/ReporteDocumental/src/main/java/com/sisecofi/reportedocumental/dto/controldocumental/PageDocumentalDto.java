package com.sisecofi.reportedocumental.dto.controldocumental;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Data
public class PageDocumentalDto<T> {

	private Page<T> page;
	private List<String> etiquetas;

}
