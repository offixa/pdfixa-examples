package example;

import io.offixa.pdfixa.core.content.ContentStream;
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfInfo;
import io.offixa.pdfixa.core.document.PdfPage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TableReportExample {

    static class SalesRow {
        String product;
        int units;
        double revenue;

        SalesRow(String product, int units, double revenue) {
            this.product = product;
            this.units = units;
            this.revenue = revenue;
        }
    }

    public static void main(String[] args) throws IOException {
        List<SalesRow> data = List.of(
                new SalesRow("Laptop Pro 15",     134,  267_866.00),
                new SalesRow("Wireless Mouse",    580,   14_500.00),
                new SalesRow("USB-C Hub",         312,   18_408.00),
                new SalesRow("Mechanical Keyboard", 198, 23_562.00),
                new SalesRow("Monitor 27\"",       87,   52_113.00),
                new SalesRow("Webcam HD",         245,    9_555.00),
                new SalesRow("Headset Pro",       163,   21_027.00)
        );

        PdfDocument doc = new PdfDocument();
        doc.setInfo(PdfInfo.builder()
                .title("Monthly Sales Report")
                .author("Offixa")
                .build());

        PdfPage page = doc.addPage();
        ContentStream cs = page.getContent();

        double left = 50;
        double right = 550;
        double y = 750;

        // --- Report title ---
        page.drawTextBox(left, y, 400, 30, "Helvetica-Bold", 24, "Monthly Sales Report");
        y -= 24;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Period: March 2026");
        y -= 16;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Generated: 2026-03-11");

        y -= 30;
        drawLine(cs, left, y, right, y);

        // --- Table columns ---
        double colProduct = left;
        double colUnits = 300;
        double colRevenue = 420;
        double rowHeight = 22;

        // Table header
        y -= rowHeight;
        page.drawTextBox(colProduct, y, 220, 16, "Helvetica-Bold", 12, "Product");
        page.drawTextBox(colUnits,   y, 100, 16, "Helvetica-Bold", 12, "Units Sold");
        page.drawTextBox(colRevenue, y, 120, 16, "Helvetica-Bold", 12, "Revenue");

        y -= 6;
        drawLine(cs, left, y, right, y);

        // Table rows
        double totalRevenue = 0;
        int totalUnits = 0;

        for (SalesRow row : data) {
            y -= rowHeight;
            page.drawTextBox(colProduct, y, 220, 16, "Helvetica", 11, row.product);
            page.drawTextBox(colUnits,   y, 100, 16, "Helvetica", 11, String.format("%,d", row.units));
            page.drawTextBox(colRevenue, y, 120, 16, "Helvetica", 11, formatMoney(row.revenue));
            totalRevenue += row.revenue;
            totalUnits += row.units;
        }

        y -= 10;
        drawLine(cs, left, y, right, y);

        // --- Summary ---
        y -= rowHeight;
        page.drawTextBox(colProduct, y, 220, 18, "Helvetica-Bold", 12, "TOTAL");
        page.drawTextBox(colUnits,   y, 100, 18, "Helvetica-Bold", 12, String.format("%,d", totalUnits));
        page.drawTextBox(colRevenue, y, 120, 18, "Helvetica-Bold", 12, formatMoney(totalRevenue));

        // --- Save ---
        String outputPath = "sales-report.pdf";
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            doc.save(fos);
        }
        System.out.println("Report saved to: " + outputPath);
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
