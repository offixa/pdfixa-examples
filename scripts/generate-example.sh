#!/usr/bin/env bash

set -euo pipefail

MODULE="${1:-invoice-generator}"
SUPPORTED_MODULES=(
  invoice-generator
  report-generator
  multi-language-pdf
  batch-pdf
  images-demo
  hello-world
  table-invoice
  table-report
  pagination-table-report
  spring-boot-download
)

SUPPORTED_MODULES_TEXT="$(IFS=', '; echo "${SUPPORTED_MODULES[*]}")"

case "$MODULE" in
  invoice-generator) MAIN_CLASS="example.InvoiceExample" ;;
  report-generator) MAIN_CLASS="example.ReportExample" ;;
  multi-language-pdf) MAIN_CLASS="example.MultiLanguageExample" ;;
  batch-pdf) MAIN_CLASS="example.BatchExample" ;;
  images-demo) MAIN_CLASS="example.ImageExample" ;;
  hello-world) MAIN_CLASS="example.HelloWorldExample" ;;
  table-invoice) MAIN_CLASS="example.TableInvoiceExample" ;;
  table-report) MAIN_CLASS="example.TableReportExample" ;;
  pagination-table-report) MAIN_CLASS="example.PaginationTableReportExample" ;;
  spring-boot-download)
    echo "Module '$MODULE' is a Spring Boot application."
    echo "Start it with:"
    echo "mvn -pl spring-boot-download spring-boot:run"
    exit 0
    ;;
  *)
    echo "Unknown module: $MODULE"
    echo "Supported modules: $SUPPORTED_MODULES_TEXT"
    exit 1
    ;;
esac

echo "Running $MODULE ($MAIN_CLASS)..."
mvn -pl "$MODULE" exec:java "-Dexec.mainClass=$MAIN_CLASS"
echo "Done. Check PDF output in ./$MODULE/"
