package com.sisecofi.reportedocumental.dto.financiero;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginadorDTO {
    @Min(value = 0, message = "El número de página debe ser mayor o igual a 0")
    private int pageNumber;

    @Min(value = 1, message = "El tamaño de página debe ser mayor o igual a 1")
    private int pageSize;
}
