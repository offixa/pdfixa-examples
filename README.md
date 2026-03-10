# pdfixa-examples

Code examples for [PDFixa](https://github.com/offixa/pdfixa-core) — a deterministic PDF generation library for Java.

---

## Getting Started

### Prerequisites

Make sure these are installed before you begin:

- **Java 17+** — check with `java -version`
- **Maven 3.8+** — check with `mvn -version`
- **Git** — check with `git --version`

### Step 1 — Clone this repository

```bash
git clone https://github.com/offixa/pdfixa-examples
cd pdfixa-examples
```

`pdfixa-core` is available on Maven Central — Maven will download it automatically.

### Step 2 — Run your first example

```bash
mvn -pl hello-world exec:java -Dexec.mainClass="example.HelloWorldExample"
```

You should see:

```
Saved: hello.pdf
```

Open `hello-world/hello.pdf` — you just generated your first PDF with PDFixa.

---

## Mental Model

PDFixa has three building blocks:

```
PdfDocument          ← the file itself
 └─ PdfPage          ← one page inside the document
     └─ ContentStream  ← low-level drawing surface (lines, shapes)
```

| Concept | What it is | How you get it |
|---|---|---|
| `PdfDocument` | The PDF file in memory | `new PdfDocument()` |
| `PdfPage` | A single page | `doc.addPage()` |
| `ContentStream` | Raw drawing commands for a page | `page.getContent()` |
| `save()` | Writes the finished file | `doc.save(outputStream)` |

For most tasks you only need `PdfDocument` and `PdfPage`.  
`ContentStream` is used when you need to draw lines or shapes directly.

---

## Hello World

```java
import io.offixa.pdfixa.core.document.PdfDocument;
import io.offixa.pdfixa.core.document.PdfPage;

import java.io.FileOutputStream;

public class HelloWorld {

    public static void main(String[] args) throws Exception {
        PdfDocument doc = new PdfDocument();

        PdfPage page = doc.addPage();
        page.drawTextBox(50, 740, 400, 30, "Helvetica-Bold", 24, "Hello, PDFixa!");
        page.drawTextBox(50, 700, 400, 16, "Helvetica",      12, "Your first PDF generated with PDFixa.");

        try (FileOutputStream out = new FileOutputStream("hello.pdf")) {
            doc.save(out);
        }

        System.out.println("Saved: hello.pdf");
    }
}
```

`drawTextBox(x, y, width, height, font, fontSize, text)` — coordinates are in points from the bottom-left corner of the page.

---

## Examples

| Module | Main Class | Demonstrates | Output |
|--------|------------|--------------|--------|
| `hello-world` | `example.HelloWorldExample` | Minimal first PDF — one page, one line of text | `hello.pdf` |
| `invoice-generator` | `example.InvoiceExample` | Line items, totals, section headers | `invoice-output.pdf` |
| `report-generator` | `example.ReportExample` | Multi-section layout, body text, confidentiality footer | `report-output.pdf` |
| `multi-language-pdf` | `example.MultiLanguageExample` | Latin-script languages: English, Uzbek, French, German, Spanish, Italian, Portuguese, Indonesian | `multi-language-output.pdf` |
| `batch-pdf` | `example.BatchExample` | Generating multiple PDFs in a loop | `batch-output-01.pdf` … `batch-output-10.pdf` |
| `images-demo` | `example.ImageExample` | Embedding PNG/JPEG/BMP images with position and size control | `images-demo-output.pdf` |
| `table-invoice` | `example.TableInvoiceExample` | Generate a business invoice with a table of items | `invoice-table.pdf` |
| `table-report` | `example.TableReportExample` | Generate a simple analytics report with tabular data | `sales-report.pdf` |

> **Note for `images-demo`:** a sample PNG image is generated in memory at runtime — no external file needed.

### Run any example

```bash
mvn -pl <module-name> exec:java -Dexec.mainClass="<main-class>"
```

For instance:

```bash
mvn -pl invoice-generator exec:java -Dexec.mainClass="example.InvoiceExample"
mvn -pl report-generator  exec:java -Dexec.mainClass="example.ReportExample"
mvn -pl multi-language-pdf exec:java -Dexec.mainClass="example.MultiLanguageExample"
mvn -pl batch-pdf          exec:java -Dexec.mainClass="example.BatchExample"
mvn -pl images-demo        exec:java -Dexec.mainClass="example.ImageExample"
mvn -pl table-invoice      exec:java -Dexec.mainClass="example.TableInvoiceExample"
mvn -pl table-report       exec:java -Dexec.mainClass="example.TableReportExample"
```

---

## Optional helper scripts

Windows (PowerShell):

```powershell
.\scripts\generate-example.ps1
.\scripts\generate-example.ps1 report-generator
```

Linux/macOS (bash):

```bash
chmod +x ./scripts/generate-example.sh
./scripts/generate-example.sh
./scripts/generate-example.sh report-generator
```

Supported modules: `hello-world`, `invoice-generator`, `report-generator`, `multi-language-pdf`, `batch-pdf`, `images-demo`, `table-invoice`, `table-report`.

---

## Related

- [PDFixa Core](https://github.com/offixa/pdfixa-core) — library source, API reference, and Maven install instructions
