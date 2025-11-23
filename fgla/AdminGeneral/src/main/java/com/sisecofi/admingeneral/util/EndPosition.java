package com.sisecofi.admingeneral.util;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.colors.Color;

public class EndPosition implements ILineDrawer {
	private float y;

	public float getY() {
		return y;
	}

	@Override
	public void draw(PdfCanvas pdfCanvas, Rectangle rectangle) {
		y = rectangle.getY();
	}

	@Override
	public float getLineWidth() {
		return 0;
	}

	@Override
	public void setLineWidth(float v) {
		// Método requerido pero no implementado
	}

	@Override
	public Color getColor() {
		return null;
	}

	@Override
	public void setColor(Color color) {
		// Método requerido por la interfaz ILineDrawer pero no implementado en esta
		// clase.
	}
}
