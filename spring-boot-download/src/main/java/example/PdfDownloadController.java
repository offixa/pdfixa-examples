package example;

import io.offixa.pdfixa.core.content.ContentStream;
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfPage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
public class PdfDownloadController {

    @GetMapping("/invoice")
    public void downloadInvoice(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"invoice.pdf\"");

        PdfDocument doc = new PdfDocument();
        PdfPage page = doc.addPage();
        ContentStream cs = page.getContent();

        double left = 50;
        double right = 550;
        double y = 750;

        page.drawTextBox(left, y, 250, 30, "Helvetica-Bold", 26, "INVOICE");
        y -= 30;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Invoice #: INV-2026-100");
        y -= 16;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Date: 2026-03-11");

        y -= 28;
        drawLine(cs, left, y, right, y);

        y -= 22;
        page.drawTextBox(left, y, 200, 18, "Helvetica-Bold", 13, "Bill To:");
        y -= 18;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Abdulloh Rakhimov");
        y -= 16;
        page.drawTextBox(left, y, 300, 16, "Helvetica", 11, "Tashkent, Uzbekistan");

        y -= 28;
        drawLine(cs, left, y, right, y);

        double colItem = left;
        double colQty = 300;
        double colPrice = 400;
        double colTotal = 490;

        y -= 20;
        page.drawTextBox(colItem,  y, 220, 16, "Helvetica-Bold", 11, "Item");
        page.drawTextBox(colQty,   y, 80,  16, "Helvetica-Bold", 11, "Qty");
        page.drawTextBox(colPrice, y, 80,  16, "Helvetica-Bold", 11, "Price");
        page.drawTextBox(colTotal, y, 60,  16, "Helvetica-Bold", 11, "Total");
        y -= 6;
        drawLine(cs, left, y, right, y);

        String[][] items = {
                {"API Integration",  "1", "$3,200.00", "$3,200.00"},
                {"Database Setup",   "1", "$1,500.00", "$1,500.00"},
                {"Testing & QA",     "2",   "$600.00", "$1,200.00"},
                {"Deployment",       "1",   "$400.00",   "$400.00"},
        };

        for (String[] item : items) {
            y -= 20;
            page.drawTextBox(colItem,  y, 220, 14, "Helvetica", 11, item[0]);
            page.drawTextBox(colQty,   y, 80,  14, "Helvetica", 11, item[1]);
            page.drawTextBox(colPrice, y, 80,  14, "Helvetica", 11, item[2]);
            page.drawTextBox(colTotal, y, 60,  14, "Helvetica", 11, item[3]);
        }

        y -= 10;
        drawLine(cs, left, y, right, y);

        y -= 22;
        page.drawTextBox(colPrice, y, 80, 18, "Helvetica-Bold", 13, "TOTAL:");
        page.drawTextBox(colTotal, y, 60, 18, "Helvetica-Bold", 13, "$6,300.00");

        doc.save(response.getOutputStream());
    }

    private void drawLine(ContentStream cs, double x1, double y1, double x2, double y2) {
        cs.setLineWidth(0.5);
        cs.moveTo(x1, y1);
        cs.lineTo(x2, y2);
        cs.stroke();
    }
}
