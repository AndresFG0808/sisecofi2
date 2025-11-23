package com.sisecofi.admingeneral.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */
@Slf4j
public class Base64Util {

	private static final Pattern IMAGE_PATTERN = Pattern.compile("<img[^>]+src\s*=\s*['\"]([^'\"]+)['\"][^>]*>");
	private static final Pattern IMAGE_SRC_PATTERN = Pattern.compile("src\s*=\s*['\"]([^'\"]+)['\"][^>]");

	private Base64Util() {
	}

	public static Optional<List<String>> guardarImagen(String str) {
		Matcher m = IMAGE_PATTERN.matcher(str);
		log.info("groupCount: {}", m.groupCount());
		List<String> lista = new ArrayList<>();
		while (m.find()) {
			Matcher m1 = IMAGE_SRC_PATTERN.matcher(m.group(0));
			while (m1.find()) {
				log.info("groupCount: {}", m1.groupCount());
				lista.add(m1.group(0));
			}
		}
		return Optional.of(lista);
	}

}
