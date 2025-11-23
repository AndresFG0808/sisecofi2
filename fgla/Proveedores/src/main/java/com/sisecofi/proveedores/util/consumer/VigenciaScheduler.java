package com.sisecofi.proveedores.util.consumer;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sisecofi.libreria.comunes.model.proveedores.TituloServicioProveedorModel;
import com.sisecofi.proveedores.repository.TituloServicioProveedorServiceRepository;
import com.sisecofi.proveedores.util.enums.VigenciaEnum;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VigenciaScheduler {

	private final TituloServicioProveedorServiceRepository tituloServicioProveedorServiceRepository;

	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void actualizarVigencias() {
		List<TituloServicioProveedorModel> titulos = tituloServicioProveedorServiceRepository.findAll();
		for (TituloServicioProveedorModel titulo : titulos) {
			calcularVigencia(titulo);
			tituloServicioProveedorServiceRepository.save(titulo);
		}
	}

	public void calcularVigencia(TituloServicioProveedorModel tituloServicioProveedorModel) {
		if (tituloServicioProveedorModel == null || tituloServicioProveedorModel.getVencimientoTitulo() == null) {
			return;
		}
		String[] vigenciaData = calcularVigenciaInterno(tituloServicioProveedorModel.getVencimientoTitulo());
		tituloServicioProveedorModel.setVigencia(vigenciaData[0]);
		tituloServicioProveedorModel.setColorVigencia(vigenciaData[1]);
	}

	public String calcularVigencia(Date vencimientoTitulo) {
		if (vencimientoTitulo == null) {
			return "0.00 m";
		}
		return calcularVigenciaInterno(vencimientoTitulo)[0];
	}

	private String[] calcularVigenciaInterno(Date vencimientoTitulo) {
		LocalDate today = LocalDate.now();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(vencimientoTitulo);
		LocalDate vencimiento = calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		long totalDaysBetween = ChronoUnit.DAYS.between(today, vencimiento);
		double daysInMonth = 30.4166;
		double vigenciaDecimal = totalDaysBetween / daysInMonth;

		if (vencimiento.isBefore(today)) {
			return new String[] { "0.00 m", null };
		}

		VigenciaEnum vigenciaEnum = (vigenciaDecimal < 3.0) ? VigenciaEnum.MENOR_TRES_MESES
				: VigenciaEnum.MAYOR_TRES_MESES;
		String vigencia = String.format("%.2f m", vigenciaDecimal);

		return new String[] { vigencia, vigenciaEnum.getColor() };
	}

}
