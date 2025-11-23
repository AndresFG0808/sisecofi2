package com.sisecofi.libreria.comunes.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sisecofi.libreria.comunes.service.PathService;

@Service
public class PathServiceImpl implements PathService {

	@Override
	public boolean comprobarArchivo(MultipartFile file) {
	    String filename = Optional.ofNullable(file)
	        .map(MultipartFile::getOriginalFilename)
	        .map(StringUtils::cleanPath)
	        .orElse("");

	    return filename.isEmpty() || filename.matches(
	        "^[\\p{L}\\p{N} _\\-\\.\\(\\)]+\\.(pdf|PDF|jpg|jpeg|png|docx|doc|xlsx|xls|pptx|ppt|txt|csv|xml|XML|zip|rar)$"
	    );
	}


}

