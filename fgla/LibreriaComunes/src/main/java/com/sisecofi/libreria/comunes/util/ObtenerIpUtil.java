package com.sisecofi.libreria.comunes.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
public class ObtenerIpUtil {

	private static final String[] HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	private ObtenerIpUtil() {
	}

	public static String obtenerIp(HttpServletRequest httpRequest) {
		String clientIp = httpRequest.getHeader("x-forwarded-for") == null ? httpRequest.getRemoteAddr()
				: httpRequest.getHeader("x-forwarded-for");
		String[] ips = null;
		if (clientIp != null) {
			log.info("Ips del cliente: {}", clientIp);
			ips = clientIp.split(",");
		}
		if (ips != null && ips.length > 1) {
			log.info("Ip obtenida: {}", ips[0]);
			return ips[0];
		}
		log.info("Ip final: {}", clientIp != null && !clientIp.equals("") ? clientIp : httpRequest.getRemoteAddr());
		return clientIp != null && !clientIp.equals("") ? clientIp : httpRequest.getRemoteAddr();
	}

	public static String getClientIpAddress(HttpServletRequest request) {
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}

		return request.getRemoteAddr();
	}
}
