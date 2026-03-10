package example;

import io.offixa.pdfixa.core.content.ContentStream;
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfInfo;
import io.offixa.pdfixa.core.document.PdfPage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TableInvoiceExample {

    static class InvoiceItem {
        String name;
        int qty;
        double price;

        InvoiceItem(String name, int qty, double price) {
            this.name = name;
            this.qty = qty;
            this.price = price;
        }

        double total() {
            return qty * price;
        }
    }

    public static void main(String[] args) throws IOException {
        List<InvoiceItem> items = List.of(
                new InvoiceItem("Website Development", 1, 2500.00),
                new InvoiceItem("UI/UX Design", 1, 800.00),
                new InvoiceItem("Hosting (12 months)", 12, 15.00),
                new InvoiceItem("Domain Registration", 2, 12.00),
                new InvoiceItem("SSL Certificate", 1, 45.00)
        );

        PdfDocument doc = new PdfDocument();
        doc.setInfo(PdfInfo.builder()
                .title("Table Invoice Example")
                .author("Offixa")
                .build());

        PdfPage page = doc.addPage();
        ContentStream cs = page.getContent();

        double left = 50;
        double right = 550;
        double y = 750;

        // --- Header ---
        page.drawTextBox(left, y, 200, 30, "Helvetica-Bold", 26, "INVOICE");
        y -= 30;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Invoice #: INV-2026-042");
        y -= 16;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Date: 2026-03-11");

        y -= 30;
        drawLine(cs, left, y, right, y);

        // --- Customer ---
        y -= 22;
        page.drawTextBox(left, y, 200, 18, "Helvetica-Bold", 13, "Bill To:");
        y -= 18;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Javohir Karimov");
        y -= 16;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Samarkand, Uzbekistan");

        y -= 28;
        drawLine(cs, left, y, right, y);

        // --- Table columns ---
        double colItem = left;
        double colQty = 300;
        double colPrice = 390;
        double colTotal = 480;
        double rowHeight = 20;

        // Table header
        y -= rowHeight;
        page.drawTextBox(colItem,  y, 200, 16, "Helvetica-Bold", 11, "Item");
        page.drawTextBox(colQty,   y, 70,  16, "Helvetica-Bold", 11, "Qty");
        page.drawTextBox(colPrice, y, 80,  16, "Helvetica-Bold", 11, "Price");
        page.drawTextBox(colTotal, y, 70,  16, "Helvetica-Bold", 11, "Total");

        y -= 6;
        drawLine(cs, left, y, right, y);

        // Table rows
        double grandTotal = 0;
        for (InvoiceItem item : items) {
            y -= rowHeight;
            page.drawTextBox(colItem,  y, 200, 16, "Helvetica", 11, item.name);
            page.drawTextBox(colQty,   y, 70,  16, "Helvetica", 11, String.valueOf(item.qty));
            page.drawTextBox(colPrice, y, 80,  16, "Helvetica", 11, formatMoney(item.price));
            page.drawTextBox(colTotal, y, 70,  16, "Helvetica", 11, formatMoney(item.total()));
            grandTotal += item.total();
        }

        y -= 10;
        drawLine(cs, left, y, right, y);

        // --- Grand Total ---
        y -= rowHeight;
        page.drawTextBox(colPrice, y, 80, 18, "Helvetica-Bold", 13, "TOTAL:");
        page.drawTextBox(colTotal, y, 70, 18, "Helvetica-Bold", 13, formatMoney(grandTotal));

        // --- Footer ---
        y -= 40;
        page.drawTextBox(left, y, 400, 14, "Helvetica", 10, "Payment Terms: Net 30");
        y -= 16;
        page.drawTextBox(left, y, 400, 14, "Helvetica", 10, "Thank you for your business!");

        // --- Save ---
        String outputPath = "invoice-table.pdf";
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

    private static String formatMoney(double amount) {
        return String.format("$%,.2f", amount);
    }
}
