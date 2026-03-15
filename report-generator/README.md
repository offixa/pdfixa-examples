# report-generator

A single-page multi-section business report.
Demonstrates how to structure a document with numbered sections,
body paragraphs, a monospaced metrics block, and a confidentiality footer.

---

## Concepts demonstrated

- Setting PDF metadata with `PdfInfo` (title, author)
- Mixing font families: `Helvetica` for prose, `Courier` for tabular key-value data
- Structuring content into named sections using section headings
- Drawing a horizontal rule to separate the footer from the body
- Positioning a small-print footer at a fixed Y coordinate

---

## How to run

```bash
mvn -pl report-generator exec:java -Dexec.mainClass="example.ReportExample"
```

---

## Expected output

```
Report saved to: report-output.pdf
```

File created: `report-generator/report-output.pdf`
