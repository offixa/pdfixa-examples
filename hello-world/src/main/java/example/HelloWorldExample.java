package example;

import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfPage;

import java.io.FileOutputStream;

public class HelloWorldExample {

    public static void main(String[] args) throws Exception {
        PdfDocument doc = new PdfDocument();
        PdfPage page = doc.addPage();

        page.drawTextBox(50, 700, 400, 30, "Helvetica-Bold", 24, "Hello PDFixa");

        try (FileOutputStream out = new FileOutputStream("hello.pdf")) {
            doc.save(out);
        }
        System.out.println("Saved: hello.pdf");
    }
}
