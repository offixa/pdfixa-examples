package example;

import io.offixa.pdfixa.core.content.ContentStream;
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfImage;
import io.offixa.pdfixa.core.document.PdfPage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageExample {

    public static void main(String[] args) throws IOException {
        byte[] pngBytes = generateSamplePng();

        PdfDocument doc = new PdfDocument();
        PdfImage image = doc.addPngImage(pngBytes);

        PdfPage page = doc.addPage();
        ContentStream cs = page.getContent();

        page.drawTextBox(50, 740, 450, 28, "Helvetica-Bold", 22, "Images Demo");
        page.drawTextBox(50, 714, 450, 14, "Helvetica", 11,
                "Embedding an image into a PDF with PDFixa");

        drawLine(cs, 50, 700, 550, 700);

        page.drawImage(image, 50, 440, 250, 250);

        page.drawTextBox(50, 418, 400, 14, "Helvetica", 10,
                "Original size: " + image.getWidth() + " x " + image.getHeight() + " px");

        drawLine(cs, 50, 385, 550, 385);
        page.drawTextBox(50, 362, 500, 30, "Helvetica", 11,
                "PDFixa supports PNG and JPEG formats. Provide the image bytes, " +
                "then draw it on any page with position and size.");

        String outputPath = "images-demo-output.pdf";
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            doc.save(fos);
        }
        System.out.println("Image PDF saved to: " + outputPath);
    }

    /**
     * Generates a simple 200x200 PNG in memory — no file I/O needed.
     */
    private static byte[] generateSamplePng() throws IOException {
        int size = 200;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(0, 122, 204));
        g.fillRect(0, 0, size, size);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 28));
        g.drawString("PDFixa", 42, 110);

        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        return baos.toByteArray();
    }

    private static void drawLine(ContentStream cs, double x1, double y1, double x2, double y2) {
        cs.setLineWidth(0.5);
        cs.moveTo(x1, y1);
        cs.lineTo(x2, y2);
        cs.stroke();
    }
}
