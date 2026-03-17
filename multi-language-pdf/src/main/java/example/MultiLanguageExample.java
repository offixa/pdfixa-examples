package example;

import io.offixa.pdfixa.core.content.ContentStream;
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfInfo;
import io.offixa.pdfixa.core.document.PdfPage;

import java.io.FileOutputStream;
import java.io.IOException;

public class MultiLanguageExample {

    public static void main(String[] args) throws IOException {
        PdfDocument doc = new PdfDocument();
        doc.setInfo(PdfInfo.builder()
                .title("Multi-Language PDF Demo")
                .author("PDFixa")
                .build());

        PdfPage page = doc.addPage();
        ContentStream cs = page.getContent();

        page.drawTextBox(50, 740, 450, 28, "Helvetica-Bold", 22, "Multi-Language PDF Demo");
        page.drawTextBox(50, 712, 450, 14, "Helvetica", 11,
                "Latin-1 (WinAnsiEncoding) with Base-14 fonts - accents work out of the box.");

        drawLine(cs, 50, 698, 550, 698);

        page.drawTextBox(50, 672, 200, 18, "Helvetica-Bold", 14, "English");
        page.drawTextBox(70, 650, 450, 16, "Helvetica", 13,
                "Hello World - PDF generation made simple.");

        page.drawTextBox(50, 624, 200, 18, "Helvetica-Bold", 14, "Uzbek (Latin)");
        page.drawTextBox(70, 602, 450, 16, "Helvetica", 13,
                "Salom Dunyo - PDF yaratish juda oson.");

        page.drawTextBox(50, 576, 200, 18, "Helvetica-Bold", 14, "French");
        page.drawTextBox(70, 554, 450, 16, "Helvetica", 13,
                "Bonjour le monde - g\u00e9n\u00e9ration de PDF simple et rapide.");

        page.drawTextBox(50, 528, 200, 18, "Helvetica-Bold", 14, "German");
        page.drawTextBox(70, 506, 450, 16, "Helvetica", 13,
                "Hallo Welt - PDF-Erstellung einfach und schnell. Sch\u00f6n!");

        page.drawTextBox(50, 480, 200, 18, "Helvetica-Bold", 14, "Spanish");
        page.drawTextBox(70, 458, 450, 16, "Helvetica", 13,
                "Hola Mundo - generaci\u00f3n de PDF r\u00e1pida y sencilla.");

        page.drawTextBox(50, 432, 200, 18, "Helvetica-Bold", 14, "Italian");
        page.drawTextBox(70, 410, 450, 16, "Helvetica", 13,
                "Ciao Mondo - creazione di PDF semplice e veloce.");

        page.drawTextBox(50, 384, 200, 18, "Helvetica-Bold", 14, "Portuguese");
        page.drawTextBox(70, 362, 450, 16, "Helvetica", 13,
                "Ol\u00e1 Mundo - cria\u00e7\u00e3o de PDF simples e eficiente.");

        page.drawTextBox(50, 336, 200, 18, "Helvetica-Bold", 14, "Indonesian");
        page.drawTextBox(70, 314, 450, 16, "Helvetica", 13,
                "Halo Dunia - pembuatan PDF sederhana dan cepat.");

        drawLine(cs, 50, 292, 550, 292);
        page.drawTextBox(50, 268, 500, 30, "Helvetica", 10,
                "All text above uses Latin-1 (WinAnsiEncoding). " +
                "For Cyrillic, CJK, Arabic, or full Unicode, see PDFixa Pro.");

        String outputPath = "multi-language-output.pdf";
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            doc.save(fos);
        }
        System.out.println("Multi-language PDF saved to: " + outputPath);
    }

    private static void drawLine(ContentStream cs, double x1, double y1, double x2, double y2) {
        cs.setLineWidth(0.5);
        cs.moveTo(x1, y1);
        cs.lineTo(x2, y2);
        cs.stroke();
    }
}
