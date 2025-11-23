package com.sisecofi.reportedocumental.dto.pistareporte;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import com.sisecofi.libreria.comunes.dto.dinamico.PageGeneric;
import com.sisecofi.reportedocumental.dto.CommonPageHelper;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

public class PagePista implements CommonPageHelper<Pistas> {

	private List<Pistas> contentPista;
	private int sizePista;
	private int totalPagesPista;
	private long totalElementsPista;
	private boolean firstPista;
	private int numberPista;
	private int numberOfElementsPista;
	private boolean lastPista;


	public PagePista(PageGeneric page) {
		initializeContent(page);
	}

	private void initializeContent(PageGeneric page) {
		setContentPista(page.getContent().stream().map(m -> {
			Pistas p1 = new Pistas();
			p1.setIdPista(Integer.parseInt(String.valueOf(m[0])));
			p1.setModulo(String.valueOf(m[1]));
			p1.setSeccion(String.valueOf(m[2]));
			p1.setFechaHora(String.valueOf(m[3]));
			p1.setEmpleado(String.valueOf(m[4]));
			p1.setRfc(String.valueOf(m[5]));
			p1.setTipoMovimiento(String.valueOf(m[6]));
			return p1;
		}).toList());
		setFirstPista(page.isFirst());
		setLastPista(page.isLast());
		setNumberPista(page.getNumber());
		setNumberOfElementsPista(page.getNumberOfElements());
		setSizePista(page.getSize());
		setTotalElementsPista(page.getTotalElements());
		setTotalPagesPista(page.getTotalPages());
	}

	public void setContentPista(List<Pistas> contentPista) {
		this.contentPista = contentPista;
	}

	public void setSizePista(int sizePista) {
		this.sizePista = sizePista;
	}

	public void setTotalPagesPista(int totalPagesPista) {
		this.totalPagesPista = totalPagesPista;
	}

	public void setTotalElementsPista(long totalElementsPista) {
		this.totalElementsPista = totalElementsPista;
	}

	public void setFirstPista(boolean firstPista) {
		this.firstPista = firstPista;
	}

	public void setNumberPista(int numberPista) {
		this.numberPista = numberPista;
	}

	public void setNumberOfElementsPista(int numberOfElementsPista) {
		this.numberOfElementsPista = numberOfElementsPista;
	}

	public void setLastPista(boolean lastPista) {
		this.lastPista = lastPista;
	}

	@Override
	public int getNumber() {
		return numberPista;
	}

	@Override
	public int getSize() {
		return sizePista;
	}

	@Override
	public int getNumberOfElements() {
		return numberOfElementsPista;
	}

	@Override
	public List<Pistas> getContent() {
		return contentPista;
	}
	
	@Override
	public boolean isFirst() {
		return firstPista;
	}

	@Override
	public boolean isLast() {
		return lastPista;
	}

	@Override
	public Iterator<Pistas> iterator() {
		return contentPista.iterator();
	}

	@Override
	public int getTotalPages() {
		return totalPagesPista;
	}

	@Override
	public long getTotalElements() {
		return totalElementsPista;
	}

	@Override
	public <U> Page<U> map(Function<? super Pistas, ? extends U> converter) {
		return null;
	}

}
