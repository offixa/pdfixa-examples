# pdfixa-examples

Code examples for [PDFixa](https://github.com/offixa/pdfixa-core) — a deterministic PDF generation library for Java.

## Examples

| Module | Main Class | Demonstrates | Output |
|--------|------------|--------------|--------|
| `invoice-generator` | `example.InvoiceExample` | Line items, totals, section headers | `invoice-output.pdf` |
| `report-generator` | `example.ReportExample` | Multi-section layout, body text, confidentiality footer | `report-output.pdf` |
| `multi-language-pdf` | `example.MultiLanguageExample` | Unicode text across Latin, Cyrillic, CJK, Arabic, Hangul | `multi-language-output.pdf` |
| `batch-pdf` | `example.BatchExample` | Generating multiple PDFs in a loop | `batch-output-01.pdf` … `batch-output-10.pdf` |
| `images-demo` | `example.ImageExample` | Embedding PNG/JPEG/BMP images with position and size control | `images-demo-output.pdf` |

> **Note for `images-demo`:** a sample PNG image is generated in memory at runtime — no external file needed.

## Requirements

- Java 17+
- Maven 3.8+
- `pdfixa-core:1.0.0` available in your local Maven repository (see [PDFixa Core](https://github.com/offixa/pdfixa-core) for install instructions)

## Quick start (Maven)

From the repository root, run one example:

```bash
mvn -pl invoice-generator exec:java -Dexec.mainClass="example.InvoiceExample"
```

You should see `invoice-output.pdf` in `invoice-generator/`.

If dependencies are not downloaded yet, run this once first:

```bash
mvn clean install
```

Other examples:

```bash
mvn -pl report-generator exec:java -Dexec.mainClass="example.ReportExample"
mvn -pl multi-language-pdf exec:java -Dexec.mainClass="example.MultiLanguageExample"
mvn -pl batch-pdf exec:java -Dexec.mainClass="example.BatchExample"
mvn -pl images-demo exec:java -Dexec.mainClass="example.ImageExample"
```

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

Supported modules: `invoice-generator`, `report-generator`, `multi-language-pdf`, `batch-pdf`, `images-demo`.

## Related

- [PDFixa Core](https://github.com/offixa/pdfixa-core) — library source, API reference, and Maven install instructions
