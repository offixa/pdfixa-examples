package example;

import io.offixa.pdfixa.core.content.ContentStream;
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfInfo;
import io.offixa.pdfixa.core.document.PdfPage;

import java.io.FileOutputStream;
import java.io.IOException;

public class Latin1Example {

    public static void main(String[] args) throws IOException {
        PdfDocument doc = new PdfDocument();
        doc.setInfo(PdfInfo.builder()
                .title("Latin-1 Support Demo")
                .author("PDFixa")
                .build());

        PdfPage page = doc.addPage();
        ContentStream cs = page.getContent();

        double left = 50;
        double right = 550;
        double y = 740;

        page.drawTextBox(left, y, 450, 28, "Helvetica-Bold", 22, "Latin-1 Support Demo");
        y -= 22;
        page.drawTextBox(left, y, 450, 14, "Helvetica", 11,
                "PDFixa 1.1.0 - WinAnsiEncoding with Base-14 fonts");

        y -= 24;
        drawLine(cs, left, y, right, y);
        y -= 28;

        // --- Accented characters ------------------------------------------------

        page.drawTextBox(left, y, 150, 16, "Helvetica-Bold", 12, "French");
        page.drawTextBox(200,   y, 320, 16, "Helvetica",      12,
                "R\u00e9sum\u00e9 - caf\u00e9 - na\u00efve - \u00e9l\u00e8ve");
        y -= 26;

        page.drawTextBox(left, y, 150, 16, "Helvetica-Bold", 12, "Spanish");
        page.drawTextBox(200,   y, 320, 16, "Helvetica",      12,
                "Espa\u00f1ol - ma\u00f1ana - \u00e1\u00e9\u00ed\u00f3\u00fa - \u00bfQu\u00e9 tal?");
        y -= 26;

        page.drawTextBox(left, y, 150, 16, "Helvetica-Bold", 12, "Turkish");
        page.drawTextBox(200,   y, 320, 16, "Helvetica",      12,
                "T\u00fcrk\u00e7e - \u00e7orba - k\u00f6pr\u00fc - m\u00fczik");
        y -= 26;

        page.drawTextBox(left, y, 150, 16, "Helvetica-Bold", 12, "German");
        page.drawTextBox(200,   y, 320, 16, "Helvetica",      12,
                "Deutsch - M\u00fcnchen - Stra\u00dfe - \u00e4\u00f6\u00fc\u00c4\u00d6\u00dc");
        y -= 26;

        page.drawTextBox(left, y, 150, 16, "Helvetica-Bold", 12, "Portuguese");
        page.drawTextBox(200,   y, 320, 16, "Helvetica",      12,
                "Portugu\u00eas - cora\u00e7\u00e3o - informa\u00e7\u00e3o");
        y -= 26;

        page.drawTextBox(left, y, 150, 16, "Helvetica-Bold", 12, "Norwegian");
        page.drawTextBox(200,   y, 320, 16, "Helvetica",      12,
                "Norsk - bl\u00e5b\u00e6r - s\u00f8t - \u00e6ble");
        y -= 26;

        page.drawTextBox(left, y, 150, 16, "Helvetica-Bold", 12, "Romanian");
        page.drawTextBox(200,   y, 320, 16, "Helvetica",      12,
                "Rom\u00e2nia - copil - c\u00e2mp - p\u00e2ine");
        y -= 38;

        // --- Combined sentence --------------------------------------------------

        drawLine(cs, left, y, right, y);
        y -= 22;
        page.drawTextBox(left, y, 450, 16, "Helvetica-Bold", 12, "Combined");
        y -= 20;
        page.drawTextBox(left, y, 500, 16, "Times-Roman", 13,
                "R\u00e9sum\u00e9 - Espa\u00f1ol - T\u00fcrk\u00e7e - Fran\u00e7ais");
        y -= 36;

        // --- Note ---------------------------------------------------------------

        drawLine(cs, left, y, right, y);
        y -= 18;
        page.drawTextBox(left, y, 500, 28, "Helvetica", 9,
                "WinAnsiEncoding covers the full Latin-1 range (U+0000-U+00FF). " +
                "For Cyrillic, CJK, Arabic, or full Unicode, see PDFixa Pro.");

        String outputPath = "latin1-example.pdf";
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            doc.save(fos);
        }
        System.out.println("Saved: " + outputPath);
    }

    private static void drawLine(ContentStream cs, double x1, double y1, double x2, double y2) {
        cs.setLineWidth(0.5);
        cs.moveTo(x1, y1);
        cs.lineTo(x2, y2);
        cs.stroke();
    }
}
