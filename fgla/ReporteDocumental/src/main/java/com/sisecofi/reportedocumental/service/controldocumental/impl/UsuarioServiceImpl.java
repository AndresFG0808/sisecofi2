package com.sisecofi.reportedocumental.service.controldocumental.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisecofi.libreria.comunes.model.usuario.Usuario;
import com.sisecofi.libreria.comunes.repository.UsuarioRepository;
import com.sisecofi.reportedocumental.service.controldocumental.UsuarioService;
import com.sisecofi.reportedocumental.util.enums.ErroresEnum;
import com.sisecofi.reportedocumental.util.exception.ControlDocumentalException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired 
	UsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> obtenerUsuariosSAT() {
		try {
			return usuarioRepository.findByEstatus(true);
        }catch (Exception e){
            throw new ControlDocumentalException(ErroresEnum.ERROR_OBTENER_USUARIOS_SAT);
        }
	}

}
