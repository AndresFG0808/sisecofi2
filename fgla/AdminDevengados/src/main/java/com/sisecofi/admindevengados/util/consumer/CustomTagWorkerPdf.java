package com.sisecofi.admindevengados.util.consumer;

import java.util.Set;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.styledxmlparser.node.IElementNode;

public class CustomTagWorkerPdf extends DefaultTagWorkerFactory {

    @Override
    public ITagWorker getCustomTagWorker(IElementNode tag, ProcessorContext context) {
        Set<String> supportedTags = Set.of("figure", "td", "p", "colgroup", "tbody", "img", "table");

        if (supportedTags.contains(tag.name())) {
            return super.getCustomTagWorker(tag, context);
        }

        return null;
    }


}
