package com.sisecofi.contratos.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DescargaSatCloudRequest extends RepresentationModel<DescargaSatCloudRequest> {
	@EqualsAndHashCode.Include
	private Long idContrato;
	private Long idConvenio;
	private Long idContratoReintegro;
	private String path;
}
