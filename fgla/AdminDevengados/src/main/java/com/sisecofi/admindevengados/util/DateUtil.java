package com.sisecofi.admindevengados.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtil {


	private DateUtil() {
    }
	
    public static LocalDateTime horaActual() {
        ZoneId zoneId = ZoneId.of("America/Mexico_City");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return zonedDateTime.toLocalDateTime();
    }

    public static boolean areDatesDifferent(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            throw new IllegalArgumentException("Los valores de fecha no pueden ser nulos.");
        }
        return !dateTime1.toLocalDate().equals(dateTime2.toLocalDate());
    }
}
