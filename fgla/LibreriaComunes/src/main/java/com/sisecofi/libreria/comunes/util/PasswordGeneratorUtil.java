package com.sisecofi.libreria.comunes.util;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class PasswordGeneratorUtil {

	private PasswordGeneratorUtil() {
	}

	public static String generateCommonLangPassword() {
		String numbers = RandomStringUtils.randomNumeric(4);
		String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
		String upperCaseLetters = RandomStringUtils.random(1, 65, 90, true, true);
		String lowerCaseLetters2 = RandomStringUtils.random(2, 97, 122, true, true);
		String upperCaseLetters2 = RandomStringUtils.random(1, 65, 90, true, true);
		String lowerCaseLetters3 = RandomStringUtils.random(2, 97, 122, true, true);
		String combinedChars = numbers.concat("-").concat(lowerCaseLetters).concat(upperCaseLetters)
				.concat(lowerCaseLetters2).concat(upperCaseLetters2).concat(lowerCaseLetters3);
		List<Character> pwdChars = combinedChars.chars().mapToObj(c -> (char) c).toList();
		return pwdChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
	}

}
