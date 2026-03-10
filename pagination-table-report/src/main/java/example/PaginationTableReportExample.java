package example;

import io.offixa.pdfixa.core.content.ContentStream;
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfInfo;
import io.offixa.pdfixa.core.document.PdfPage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates multi-page table pagination with PDFixa.
 *
 * Key concepts shown:
 *  - detecting page overflow (Y position vs bottom margin)
 *  - creating a new page when the table runs out of space
 *  - repeating the table header on every page
 *  - drawing a page footer with page number on every page
 *  - rendering a summary section after the last row
 */
public class PaginationTableReportExample {

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

    // --- Page layout constants ---
    static final double LEFT = 50;
    static final double RIGHT = 550;
    static final double PAGE_TOP = 760;
    static final double BOTTOM_MARGIN = 70;
    static final double ROW_HEIGHT = 18;
    static final double FOOTER_Y = 30;

    // --- Table column positions ---
    static final double COL_NUM = LEFT;
    static final double COL_PRODUCT = 85;
    static final double COL_UNITS = 320;
    static final double COL_REVENUE = 430;

    public static void main(String[] args) throws IOException {
        List<SalesRow> data = generateSampleData();

        PdfDocument doc = new PdfDocument();
        doc.setInfo(PdfInfo.builder()
                .title("Quarterly Sales Report - Q1 2026")
                .author("Offixa Analytics")
                .build());

        // --- First page: report header + table ---
        PdfPage page = doc.addPage();
        ContentStream cs = page.getContent();
        int pageNumber = 1;

        double y = PAGE_TOP;
        y = drawReportHeader(page, cs, y);
        y = drawTableHeader(page, cs, y);

        // --- Render all rows, paginating when needed ---
        int totalUnits = 0;
        double totalRevenue = 0;

        for (int i = 0; i < data.size(); i++) {
            if (y - ROW_HEIGHT < BOTTOM_MARGIN) {
                drawLine(cs, LEFT, y - 4, RIGHT, y - 4);
                drawPageFooter(page, pageNumber);

                page = doc.addPage();
                cs = page.getContent();
                pageNumber++;
                y = PAGE_TOP;
                y = drawTableHeader(page, cs, y);
            }

            SalesRow row = data.get(i);
            y -= ROW_HEIGHT;

            page.drawTextBox(COL_NUM,     y, 30,  14, "Helvetica",  10, String.valueOf(i + 1));
            page.drawTextBox(COL_PRODUCT, y, 220, 14, "Helvetica",  10, row.product);
            page.drawTextBox(COL_UNITS,   y, 90,  14, "Helvetica",  10, String.format("%,d", row.units));
            page.drawTextBox(COL_REVENUE, y, 110, 14, "Helvetica",  10, formatMoney(row.revenue));

            totalUnits += row.units;
            totalRevenue += row.revenue;
        }

        // --- Summary section ---
        double summarySpace = 80;
        if (y - summarySpace < BOTTOM_MARGIN) {
            drawLine(cs, LEFT, y - 4, RIGHT, y - 4);
            drawPageFooter(page, pageNumber);

            page = doc.addPage();
            cs = page.getContent();
            pageNumber++;
            y = PAGE_TOP;
        }

        y -= 8;
        drawLine(cs, LEFT, y, RIGHT, y);

        y -= ROW_HEIGHT + 2;
        page.drawTextBox(COL_NUM,     y, 220, 16, "Helvetica-Bold", 11, "TOTAL");
        page.drawTextBox(COL_UNITS,   y, 90,  16, "Helvetica-Bold", 11, String.format("%,d", totalUnits));
        page.drawTextBox(COL_REVENUE, y, 110, 16, "Helvetica-Bold", 11, formatMoney(totalRevenue));

        y -= 30;
        drawLine(cs, LEFT, y, RIGHT, y);
        y -= 18;
        page.drawTextBox(LEFT, y, 400, 14, "Helvetica", 10,
                "Report generated automatically. Data covers Q1 2026 (Jan-Mar).");
        y -= 14;
        page.drawTextBox(LEFT, y, 400, 14, "Helvetica", 10,
                "Confidential - For internal use only.");

        drawPageFooter(page, pageNumber);

        // --- Save ---
        String outputPath = "pagination-table-report-output.pdf";
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            doc.save(fos);
        }
        System.out.println("Report saved to: " + outputPath + " (" + pageNumber + " pages)");
    }

    // -----------------------------------------------------------------------
    // Drawing helpers
    // -----------------------------------------------------------------------

    private static double drawReportHeader(PdfPage page, ContentStream cs, double y) {
        page.drawTextBox(LEFT, y, 400, 28, "Helvetica-Bold", 22, "Quarterly Sales Report");
        y -= 22;
        page.drawTextBox(LEFT, y, 300, 16, "Helvetica", 11, "Period: January - March 2026");
        y -= 16;
        page.drawTextBox(LEFT, y, 300, 16, "Helvetica", 11, "Prepared by: Analytics Team");
        y -= 24;
        drawLine(cs, LEFT, y, RIGHT, y);
        y -= 6;
        return y;
    }

    private static double drawTableHeader(PdfPage page, ContentStream cs, double y) {
        y -= ROW_HEIGHT;
        page.drawTextBox(COL_NUM,     y, 30,  14, "Helvetica-Bold", 10, "#");
        page.drawTextBox(COL_PRODUCT, y, 220, 14, "Helvetica-Bold", 10, "Product");
        page.drawTextBox(COL_UNITS,   y, 90,  14, "Helvetica-Bold", 10, "Units Sold");
        page.drawTextBox(COL_REVENUE, y, 110, 14, "Helvetica-Bold", 10, "Revenue");
        y -= 5;
        drawLine(cs, LEFT, y, RIGHT, y);
        return y;
    }

    private static void drawPageFooter(PdfPage page, int pageNumber) {
        page.drawTextBox(LEFT, FOOTER_Y, 200, 12, "Helvetica", 9,
                "Quarterly Sales Report - Q1 2026");
        page.drawTextBox(470, FOOTER_Y, 80, 12, "Helvetica", 9,
                "Page " + pageNumber);
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

    // -----------------------------------------------------------------------
    // Sample data (50 rows - enough to span at least 2 pages)
    // -----------------------------------------------------------------------

    private static List<SalesRow> generateSampleData() {
        String[] products = {
                "Laptop Pro 15",        "Laptop Air 13",        "Desktop Workstation",
                "Wireless Mouse",       "Wired Mouse",          "Ergonomic Mouse",
                "Mechanical Keyboard",  "Compact Keyboard",     "Wireless Keyboard",
                "Monitor 27\" 4K",      "Monitor 24\" FHD",     "Portable Monitor 15\"",
                "USB-C Hub 7-in-1",     "USB-C Hub 10-in-1",   "Thunderbolt Dock",
                "Webcam HD 1080p",      "Webcam 4K",           "Streaming Camera",
                "Headset Pro",          "Headset Wireless",     "Earbuds Pro",
                "External SSD 1TB",     "External SSD 2TB",    "External HDD 4TB",
                "RAM Module 16GB",      "RAM Module 32GB",     "RAM Module 64GB",
                "Power Bank 20000mAh",  "Power Bank 10000mAh", "Wall Charger 65W",
                "Laptop Stand",         "Monitor Arm",          "Desk Pad XL",
                "Cable Kit USB-C",      "HDMI Cable 2m",        "Ethernet Adapter",
                "Surge Protector",      "UPS 600VA",            "UPS 1500VA",
                "Printer Laser B&W",    "Printer Laser Color",  "Ink Cartridge Set",
                "Scanner Portable",     "Shredder",             "Label Printer",
                "Wireless Presenter",   "Stylus Pen",           "Drawing Tablet",
                "Cooling Pad",          "Privacy Screen 15\""
        };

        int[] units = {
                134, 87, 23, 580, 312, 198, 245, 163, 201,
                76, 142, 58, 410, 185, 44, 320, 95, 31,
                275, 188, 520, 340, 210, 89, 150, 72, 18,
                430, 610, 295, 168, 94, 355, 480, 390, 215,
                125, 37, 12, 45, 28, 260, 33, 19, 88,
                110, 73, 42, 305, 62
        };

        double[] prices = {
                1999.00, 1299.00, 3499.00, 25.00, 15.00, 79.00,
                149.00, 69.00, 89.00, 449.00, 199.00, 299.00,
                45.00, 69.00, 299.00, 59.00, 149.00, 249.00,
                129.00, 89.00, 79.00, 109.00, 179.00, 89.00,
                65.00, 120.00, 240.00, 35.00, 19.00, 45.00,
                49.00, 129.00, 29.00, 15.00, 12.00, 25.00,
                35.00, 149.00, 399.00, 299.00, 549.00, 45.00,
                89.00, 149.00, 69.00, 39.00, 49.00, 249.00,
                29.00, 39.00
        };

        List<SalesRow> rows = new ArrayList<>(products.length);
        for (int i = 0; i < products.length; i++) {
            rows.add(new SalesRow(products[i], units[i], units[i] * prices[i]));
        }
        return rows;
    }
}
