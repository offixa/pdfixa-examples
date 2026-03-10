#!/usr/bin/env bash

set -euo pipefail

MODULE="${1:-invoice-generator}"

case "$MODULE" in
  invoice-generator) MAIN_CLASS="example.InvoiceExample" ;;
  report-generator) MAIN_CLASS="example.ReportExample" ;;
  multi-language-pdf) MAIN_CLASS="example.MultiLanguageExample" ;;
  batch-pdf) MAIN_CLASS="example.BatchExample" ;;
  images-demo) MAIN_CLASS="example.ImageExample" ;;
  *)
    echo "Unknown module: $MODULE"
    echo "Supported modules: invoice-generator, report-generator, multi-language-pdf, batch-pdf, images-demo"
    exit 1
    ;;
esac

echo "Running $MODULE ($MAIN_CLASS)..."
mvn -pl "$MODULE" exec:java "-Dexec.mainClass=$MAIN_CLASS"
echo "Done. Check PDF output in ./$MODULE/"
