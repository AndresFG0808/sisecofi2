package com.sisecofi.admindevengados.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DescargaSatCloudRequest extends RepresentationModel<DescargaSatCloudRequest> {
	@EqualsAndHashCode.Include
	private Long idDictamen;
	private String path;
}
