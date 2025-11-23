package com.sisecofi.proveedores.service;

import org.springframework.stereotype.Service;

import com.sisecofi.proveedores.dto.DictamenTecnicoDto;
import com.sisecofi.proveedores.dto.DictamenTecnicoResponseDto;


@Service
public interface DictamenTecnicoService {

    DictamenTecnicoResponseDto crearDictamenTecnico(DictamenTecnicoDto dictamenTecnicoDto);

    DictamenTecnicoResponseDto actaulizarDictamenTecnico(Long idDictamenTecnicoProveedor, DictamenTecnicoDto dictamenTecnicoDto);

    void eliminacionLogicaDictamenTecnico(Long idDictamenTecnicoProveedor);

    DictamenTecnicoResponseDto consultarDictamenPorId(Long idDictamenTecnicoProveedor);

    
    


}
