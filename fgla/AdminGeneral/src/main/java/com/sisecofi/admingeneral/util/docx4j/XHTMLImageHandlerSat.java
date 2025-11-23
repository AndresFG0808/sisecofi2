package com.sisecofi.admingeneral.util.docx4j;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.docx4j.convert.in.xhtml.XHTMLImageHandler;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.properties.Property;
import org.docx4j.model.properties.PropertyFactory;
import org.docx4j.model.properties.paragraph.AbstractParagraphProperty;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.org.xhtmlrenderer.css.parser.PropertyValue;
import org.docx4j.org.xhtmlrenderer.css.value.FSCssValue;
import org.docx4j.org.xhtmlrenderer.docx.Docx4jUserAgent;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSSValue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

@Slf4j
public class XHTMLImageHandlerSat implements XHTMLImageHandler {

	private Part targetPart;

	protected HashMap<String, BinaryPartAbstractImage> imagePartCache = new HashMap<>();

	public XHTMLImageHandlerSat(Part targetPart) {
		this.targetPart = targetPart;
	}

	@Override
	public void addImage(Docx4jUserAgent docx4jUserAgent, WordprocessingMLPackage wordMLPackage, P p, Element e,
			Long cx, Long cy) {
		BinaryPartAbstractImage imagePart = null;
		boolean isError = false;
		try {
			byte[] imageBytes = null;

			if (e.getAttribute("src").startsWith("data:image")) {
				imageBytes = handleDataImage(e, p);
			} else {
				imagePart = handleExternalImage(e);

				if (imagePart == null) {
					imageBytes = fetchImageFromUrl(docx4jUserAgent, e);
				}
			}

			if (imageBytes == null && imagePart == null) {
				isError = true;
			} else {
				if (imagePart == null) {
					imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, targetPart, imageBytes);
					if (!e.getAttribute("src").startsWith("data:image")) {
						imagePartCache.put(e.getAttribute("src"), imagePart);
					}
				}

				Inline inline;
				ByteArrayInputStream bais = new ByteArrayInputStream(imagePart.getBytes());
				BufferedImage bufferedImage = ImageIO.read(bais);
				long tempCx = (9525 * bufferedImage.getWidth());
				long tempCy = (9525 * bufferedImage.getHeight());

				inline = imagePart.createImageInline(null, e.getAttribute("alt"), 0, 1, tempCx, tempCy, false);
				applyTextAlignmentStyle(e, p);

				org.docx4j.wml.R run = Context.getWmlObjectFactory().createR();
				p.getContent().add(run);
				org.docx4j.wml.Drawing drawing = Context.getWmlObjectFactory().createDrawing();
				run.getContent().add(drawing);
				drawing.getAnchorOrInline().add(inline);
			}
		} catch (Exception e1) {
			log.error(MessageFormat.format("Error during image processing: ''{0}'', insert default text.",
					e.getAttribute("alt")));
			isError = true;
		}

		if (isError) {
			org.docx4j.wml.R run = Context.getWmlObjectFactory().createR();
			p.getContent().add(run);

			org.docx4j.wml.Text text = Context.getWmlObjectFactory().createText();
			text.setValue("[MISSING IMAGE: " + e.getAttribute("alt") + ", " + e.getAttribute("alt") + " ]");

			run.getContent().add(text);
		}
	}

	private void applyTextAlignmentStyle(Element e, P p) {
		String parentStyle = Optional.ofNullable(e.getParentNode().getAttributes().getNamedItem("style"))
				.map(Node::getNodeValue).orElseGet(() -> e.getAttribute("style"));

		if (parentStyle.contains("text-align:")) {
			PPr pPr = Context.getWmlObjectFactory().createPPr();
			var textIndex = parentStyle.indexOf("text-align:");
			var endTextIndex = parentStyle.indexOf(";", textIndex);
			var textAlign = parentStyle.substring(textIndex + 11, endTextIndex);
			Map<String, CSSValue> cssMap = Map.of("text-align",
					new FSCssValue(new PropertyValue(Short.valueOf("0"), 0f, "left"), textAlign));
			addParagraphProperties(pPr, cssMap);
			p.setPPr(pPr);
		}
	}

	private byte[] handleDataImage(Element e, P p) {
		String base64String = e.getAttribute("src");
		int commaPos = base64String.indexOf(",");
		if (commaPos < 6) {
			org.docx4j.wml.R run = Context.getWmlObjectFactory().createR();
			p.getContent().add(run);

			org.docx4j.wml.Text text = Context.getWmlObjectFactory().createText();
			text.setValue("[INVALID DATA URI: " + e.getAttribute("src"));

			run.getContent().add(text);

			return new byte[0];
		}
		base64String = base64String.substring(commaPos + 1);
		log.debug(base64String);
		return Base64.decodeBase64(base64String.getBytes(StandardCharsets.UTF_8));
	}

	private BinaryPartAbstractImage handleExternalImage(Element e) {
		return imagePartCache.get(e.getAttribute("src"));
	}

	private byte[] fetchImageFromUrl(Docx4jUserAgent docx4jUserAgent, Element e) {
		byte[] imageBytes = null;
		try {
			String url = e.getAttribute("src");
			if (url.substring(1, 2).equals(":")) {
				url = "file:/" + url;
			}

			var docx4JFSImage = docx4jUserAgent.getDocx4JImageResource(url);
			if (docx4JFSImage == null) {
				log.error("Couldn't fetch " + url);
			} else {
				imageBytes = docx4JFSImage.getBytes();
			}
		} catch (Exception ex) {
			log.error("Error fetching image from URL");
		}
		return imageBytes;
	}

	@Override
	public void setMaxWidth(int maxWidth, String tableStyle) {
		// SE PASAN LOS PARAMETROS PERO NO SE USAN
	}

	private void addParagraphProperties(PPr pPr, Map<String, CSSValue> cssMap) {
		// NB, not invoked in CLASS_TO_STYLE_ONLY case

		for (Object o : cssMap.keySet()) {

			String cssName = (String) o;
			CSSValue cssValue = (CSSValue) cssMap.get(cssName);

			Property p = PropertyFactory.createPropertyFromCssName(cssName, cssValue);

			if (p != null) {
				if (p instanceof AbstractParagraphProperty ab) {
					ab.set(pPr);
				} else {
					// try specific method
					p = PropertyFactory.createPropertyFromCssNameForPPr(cssName, cssValue);
					if (p instanceof AbstractParagraphProperty abstractParagraphProperty) {
						abstractParagraphProperty.set(pPr);
					}
				}
			}

		}

	}

}
