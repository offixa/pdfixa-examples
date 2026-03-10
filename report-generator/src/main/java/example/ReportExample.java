package example;

import io.offixa.pdfixa.core.content.ContentStream;
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfInfo;
import io.offixa.pdfixa.core.document.PdfPage;

import java.io.FileOutputStream;
import java.io.IOException;

public class ReportExample {

    public static void main(String[] args) throws IOException {
        PdfDocument doc = new PdfDocument();
        doc.setInfo(PdfInfo.builder()
                .title("Quarterly Sales Report - Q1 2026")
                .author("Analytics Team")
                .build());

        PdfPage page = doc.addPage();
        ContentStream cs = page.getContent();

        page.drawTextBox(50, 750, 450, 28, "Helvetica-Bold", 22, "Quarterly Sales Report");
        page.drawTextBox(50, 720, 450, 14, "Helvetica", 11, "Q1 2026 | Prepared by Analytics Team");

        drawLine(cs, 50, 706, 550, 706);

        page.drawTextBox(50, 684, 450, 20, "Helvetica-Bold", 16, "1. Executive Summary");
        page.drawTextBox(50, 660, 500, 14, "Helvetica", 11,
                "This report covers sales performance for Q1 2026. Overall revenue");
        page.drawTextBox(50, 644, 500, 14, "Helvetica", 11,
                "grew by 18% compared to Q4 2025, driven by strong demand in the");
        page.drawTextBox(50, 628, 500, 14, "Helvetica", 11,
                "Central Asian market.");

        page.drawTextBox(50, 598, 450, 20, "Helvetica-Bold", 16, "2. Key Metrics");
        page.drawTextBox(70, 574, 400, 14, "Courier", 11, "Total Revenue:       $1,240,000");
        page.drawTextBox(70, 556, 400, 14, "Courier", 11, "New Customers:       342");
        page.drawTextBox(70, 538, 400, 14, "Courier", 11, "Retention Rate:      91%");
        page.drawTextBox(70, 520, 400, 14, "Courier", 11, "Avg. Deal Size:      $3,625");

        page.drawTextBox(50, 490, 450, 20, "Helvetica-Bold", 16, "3. Regional Breakdown");
        page.drawTextBox(70, 466, 400, 14, "Courier", 11, "Tashkent Office:     $620,000  (50%)");
        page.drawTextBox(70, 448, 400, 14, "Courier", 11, "Samarkand Office:    $310,000  (25%)");
        page.drawTextBox(70, 430, 400, 14, "Courier", 11, "Remote Sales:        $310,000  (25%)");

        page.drawTextBox(50, 400, 450, 20, "Helvetica-Bold", 16, "4. Outlook");
        page.drawTextBox(50, 376, 500, 14, "Helvetica", 11,
                "We project Q2 revenue to reach $1,400,000 based on the current");
        page.drawTextBox(50, 360, 500, 14, "Helvetica", 11,
                "pipeline. Focus areas include expanding partnerships and launching");
        page.drawTextBox(50, 344, 500, 14, "Helvetica", 11,
                "the new product line.");

        drawLine(cs, 50, 322, 550, 322);
        page.drawTextBox(50, 304, 400, 12, "Helvetica", 9, "Confidential - For internal use only");

        String outputPath = "report-output.pdf";
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
}
