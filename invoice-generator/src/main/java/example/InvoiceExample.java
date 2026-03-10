package example;

import io.offixa.pdfixa.core.content.ContentStream;
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfInfo;
import io.offixa.pdfixa.core.document.PdfPage;

import java.io.FileOutputStream;
import java.io.IOException;

public class InvoiceExample {

    public static void main(String[] args) throws IOException {
        PdfDocument doc = new PdfDocument();
        doc.setInfo(PdfInfo.builder()
                .title("Invoice INV-2026-001")
                .author("Offixa")
                .build());

        PdfPage page = doc.addPage();
        ContentStream cs = page.getContent();

        page.drawTextBox(50, 740, 250, 30, "Helvetica-Bold", 24, "INVOICE");
        page.drawTextBox(50, 710, 250, 16, "Helvetica", 12, "Invoice #: INV-2026-001");
        page.drawTextBox(50, 692, 250, 16, "Helvetica", 12, "Date: 2026-03-10");

        drawLine(cs, 50, 675, 550, 675);

        page.drawTextBox(50, 648, 200, 18, "Helvetica-Bold", 14, "Bill To:");
        page.drawTextBox(50, 628, 300, 16, "Helvetica", 12, "Akmal Toshmatov");
        page.drawTextBox(50, 612, 300, 16, "Helvetica", 12, "Tashkent, Uzbekistan");

        drawLine(cs, 50, 595, 550, 595);

        page.drawTextBox(50, 570, 200, 16, "Helvetica-Bold", 12, "Item");
        page.drawTextBox(300, 570, 60, 16, "Helvetica-Bold", 12, "Qty");
        page.drawTextBox(380, 570, 80, 16, "Helvetica-Bold", 12, "Price");
        page.drawTextBox(470, 570, 80, 16, "Helvetica-Bold", 12, "Total");

        drawLine(cs, 50, 560, 550, 560);

        page.drawTextBox(50, 538, 200, 14, "Helvetica", 11, "Website Development");
        page.drawTextBox(300, 538, 60, 14, "Helvetica", 11, "1");
        page.drawTextBox(380, 538, 80, 14, "Helvetica", 11, "$2,500.00");
        page.drawTextBox(470, 538, 80, 14, "Helvetica", 11, "$2,500.00");

        page.drawTextBox(50, 518, 200, 14, "Helvetica", 11, "UI/UX Design");
        page.drawTextBox(300, 518, 60, 14, "Helvetica", 11, "1");
        page.drawTextBox(380, 518, 80, 14, "Helvetica", 11, "$800.00");
        page.drawTextBox(470, 518, 80, 14, "Helvetica", 11, "$800.00");

        page.drawTextBox(50, 498, 200, 14, "Helvetica", 11, "Hosting (12 months)");
        page.drawTextBox(300, 498, 60, 14, "Helvetica", 11, "12");
        page.drawTextBox(380, 498, 80, 14, "Helvetica", 11, "$15.00");
        page.drawTextBox(470, 498, 80, 14, "Helvetica", 11, "$180.00");

        drawLine(cs, 50, 485, 550, 485);

        page.drawTextBox(380, 462, 80, 14, "Helvetica", 12, "Subtotal:");
        page.drawTextBox(470, 462, 80, 14, "Helvetica", 12, "$3,480.00");

        page.drawTextBox(380, 442, 80, 14, "Helvetica", 12, "Tax (12%):");
        page.drawTextBox(470, 442, 80, 14, "Helvetica", 12, "$417.60");

        drawLine(cs, 380, 430, 550, 430);

        page.drawTextBox(380, 408, 80, 18, "Helvetica-Bold", 14, "TOTAL:");
        page.drawTextBox(470, 408, 80, 18, "Helvetica-Bold", 14, "$3,897.60");

        page.drawTextBox(50, 350, 300, 14, "Helvetica", 10, "Payment Terms: Net 30");
        page.drawTextBox(50, 332, 300, 14, "Helvetica", 10, "Thank you for your business!");

        String outputPath = "invoice-output.pdf";
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            doc.save(fos);
        }
        System.out.println("Invoice saved to: " + outputPath);
    }

    private static void drawLine(ContentStream cs, double x1, double y1, double x2, double y2) {
        cs.setLineWidth(0.5);
        cs.moveTo(x1, y1);
        cs.lineTo(x2, y2);
        cs.stroke();
    }
}
