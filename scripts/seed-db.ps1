Param(
  [ValidateSet('simple','complete','admin','dump')]
  [string]$Dataset = 'simple',
  [string]$File = ''
)

Write-Host "[seed-db] Starting..." -ForegroundColor Cyan

# Resolve SQL file path
if ([string]::IsNullOrWhiteSpace($File)) {
  switch ($Dataset) {
    'simple'   { $File = Join-Path $PSScriptRoot '..\src\main\resources\data\simple-data.sql' }
    'complete' { $File = Join-Path $PSScriptRoot '..\src\main\resources\data\complete-data.sql' }
    'admin'    { $File = Join-Path $PSScriptRoot '..\src\main\resources\data\create-admin-user.sql' }
    'dump'     { $File = Join-Path $PSScriptRoot '..\src\main\resources\data\pingou-dump.sql' }
  }
}

$File = [System.IO.Path]::GetFullPath($File)
if (-not (Test-Path $File)) {
  Write-Error "[seed-db] SQL file not found: $File"
  exit 1
}

$containerName = 'postgres'
$dbName = 'pingou'
$dbUser = 'admin'

# Wait for container to be healthy
Write-Host "[seed-db] Waiting for container '$containerName' to be healthy..." -ForegroundColor Yellow
$deadline = (Get-Date).AddMinutes(2)
while ((Get-Date) -lt $deadline) {
  $status = (& docker inspect -f '{{.State.Health.Status}}' $containerName 2>$null)
  if ($LASTEXITCODE -ne 0) {
    Write-Host "[seed-db] Container '$containerName' not found or not started yet..." -ForegroundColor DarkYellow
  } elseif ($status -eq 'healthy' -or $status -eq 'running') {
    break
  } else {
    Write-Host "[seed-db] Current status: $status" -ForegroundColor DarkYellow
  }
  Start-Sleep -Seconds 3
}

# Final status check
$final = (& docker inspect -f '{{.State.Health.Status}}' $containerName 2>$null)
if ($LASTEXITCODE -ne 0 -or -not ($final -eq 'healthy' -or $final -eq 'running')) {
  Write-Error "[seed-db] Container '$containerName' is not healthy/running. Current: $final"
  exit 1
}

Write-Host "[seed-db] Seeding database '$dbName' with file: $File" -ForegroundColor Cyan

# Feed SQL file through STDIN to psql inside the container
Get-Content -Raw $File |
  docker exec -i $containerName psql -U $dbUser -d $dbName -v ON_ERROR_STOP=1 -1

if ($LASTEXITCODE -ne 0) {
  Write-Error "[seed-db] Seeding failed with exit code $LASTEXITCODE"
  exit $LASTEXITCODE
}

Write-Host "[seed-db] Done." -ForegroundColor Green


