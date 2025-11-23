package com.sisecofi.admindevengados.util.consumer;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class EndPosition implements ILineDrawer {
    protected float y;

    public float getY() {
        return y;
    }

    @Override
    public void draw(PdfCanvas pdfCanvas, Rectangle rect) {
        this.y = rect.getY();
    }
    @Override
    public Color getColor() {
        return null;
    }
    @Override
    public float getLineWidth() {
        return 0;
    }
    @Override
    public void setColor(Color color) {
    	log.info("setColor: {}");
    }

	@Override
	public void setLineWidth(float lineWidth) {
		log.info("setLineWidth: {}");
	}
    
}
