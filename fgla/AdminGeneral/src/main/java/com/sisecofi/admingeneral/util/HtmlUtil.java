package com.sisecofi.admingeneral.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HtmlUtil {

    private static final List<String> UNCLOSED_TAGS = List.of("<col ", "<img ","<br>");
    private static final List<String> TAGS_TO_REMOVE_STYLES = List.of("<figure");

    public String htmlFrom(String header, String content, String footer){

        String html = """
                <html>
                    <head>
                        <title>Documento convertido</title>
                    </head>
                    <body>
                        <div class="header">%HEADER%</div>
                        <div class="content">%CONTENT%</div>
                        <div class="footer">%FOOTER%</div>
                    </body>
                </html>
                """;
        return html.replace("%HEADER%", wellFormedHtml(header))
                .replace("%CONTENT%", wellFormedHtml(content))
                .replace("%FOOTER%", wellFormedHtml(footer));
    }

    public String wellFormedHtml(String htmlFragment) {
        StringBuilder sb = new StringBuilder(PlantillaUtil.reemplazarHsl(htmlFragment));

        UNCLOSED_TAGS.forEach(tag -> {
            int i = sb.indexOf(tag);
            while (i != -1) {
                int cierre = sb.indexOf(">", i);
                if (cierre != -1) {
                    sb.insert(cierre, "/");
                    i = sb.indexOf(tag, cierre); // avanzar después del cierre
                } else {
                    break; // si no hay cierre, evita ciclo infinito
                }
            }
        });

        fitToPage(sb);
        return cleanHtmlSpecialCharacters(sb);
    }

    private String cleanHtmlSpecialCharacters(StringBuilder sb) {
        return sb.toString().replace("&aacute;", "á")
                .replace("&eacute;", "é")
                .replace("&iacute;", "í")
                .replace("&oacute;", "ó")
                .replace("&uacute;", "ú")
                .replace("&Aacute;", "Á")
                .replace("&Eacute;", "É")
                .replace("&Iacute;", "Í")
                .replace("&Oacute;", "Ó")
                .replace("&Uacute;", "Ú")
                .replace("&nbsp;", " ")
                .replace("&ldquo;", "“")
                .replace("&rdquo;", "”")
                .replace("<strong>", "<b>")
                .replace("</strong>", "</b>");
    }

    private void fitToPage(StringBuilder sb) {
        TAGS_TO_REMOVE_STYLES.forEach(s -> {
            var i = sb.indexOf(s, 0);
            if(i != -1) {
                var last = sb.indexOf(">", i);
                sb.replace(i, last, s);
            }
        });

    }
}
