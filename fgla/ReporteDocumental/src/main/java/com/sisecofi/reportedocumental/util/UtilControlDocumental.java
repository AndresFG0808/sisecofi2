package com.sisecofi.reportedocumental.util;

import java.util.Locale;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class UtilControlDocumental {

	private UtilControlDocumental() {
	}

	public static boolean hasPdfExtension(String str) {
		return str != null && str.toLowerCase(Locale.ROOT).endsWith(".pdf");
	}
}
