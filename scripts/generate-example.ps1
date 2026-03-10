param(
    [string]$Module = "invoice-generator"
)

$mainClasses = @{
    "invoice-generator"  = "example.InvoiceExample"
    "report-generator"   = "example.ReportExample"
    "multi-language-pdf" = "example.MultiLanguageExample"
    "batch-pdf"          = "example.BatchExample"
    "images-demo"        = "example.ImageExample"
}

if (-not $mainClasses.ContainsKey($Module)) {
    Write-Host "Unknown module: $Module" -ForegroundColor Red
    Write-Host "Supported modules: $($mainClasses.Keys -join ', ')" -ForegroundColor Yellow
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
