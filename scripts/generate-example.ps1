param(
    [string]$Module = "invoice-generator"
)

$supportedModules = @(
    "invoice-generator",
    "report-generator",
    "multi-language-pdf",
    "batch-pdf",
    "images-demo",
    "hello-world",
    "table-invoice",
    "table-report",
    "pagination-table-report",
    "spring-boot-download"
)

$mainClasses = [ordered]@{
    "invoice-generator"  = "example.InvoiceExample"
    "report-generator"   = "example.ReportExample"
    "multi-language-pdf" = "example.MultiLanguageExample"
    "batch-pdf"          = "example.BatchExample"
    "images-demo"        = "example.ImageExample"
    "hello-world"        = "example.HelloWorldExample"
    "table-invoice"      = "example.TableInvoiceExample"
    "table-report"       = "example.TableReportExample"
    "pagination-table-report" = "example.PaginationTableReportExample"
}

if ($Module -eq "spring-boot-download") {
    Write-Host "Module '$Module' is a Spring Boot application." -ForegroundColor Yellow
    Write-Host "Start it with:" -ForegroundColor Yellow
    Write-Host "mvn -pl spring-boot-download spring-boot:run" -ForegroundColor Cyan
    exit 0
}

if (-not $supportedModules.Contains($Module)) {
    Write-Host "Unknown module: $Module" -ForegroundColor Red
    Write-Host "Supported modules: $($supportedModules -join ', ')" -ForegroundColor Yellow
    exit 1
}

$mainClass = $mainClasses[$Module]

Write-Host "Running $Module ($mainClass)..." -ForegroundColor Cyan
mvn -pl $Module exec:java "-Dexec.mainClass=$mainClass"

if ($LASTEXITCODE -ne 0) {
    Write-Host "Example failed." -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "Done. Check PDF output in ./$Module/" -ForegroundColor Green
