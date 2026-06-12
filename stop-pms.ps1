$ErrorActionPreference = "Continue"

$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot"
$env:CATALINA_HOME = "D:\WorkSpace\apache-tomcat-9.0.107\apache-tomcat-9.0.107"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

if (!(Test-Path $env:CATALINA_HOME)) {
    Write-Warning "Tomcat not found at $env:CATALINA_HOME"
}

Write-Host "Stopping Tomcat cleanly via shutdown.bat..." -ForegroundColor Cyan
if (Test-Path "$env:CATALINA_HOME\bin\shutdown.bat") {
    & "$env:CATALINA_HOME\bin\shutdown.bat" 2>$null
    Start-Sleep -Seconds 3
}

# Force kill process on port 8080 if it's still running
Write-Host "Checking if any process is still using port 8080..." -ForegroundColor Cyan
try {
    $connections = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
    if ($connections) {
        Write-Host "Force stopping processes running on port 8080..." -ForegroundColor Yellow
        foreach ($conn in $connections) {
            $pidToKill = $conn.OwningProcess
            if ($pidToKill) {
                Write-Host "Killing Process ID: $pidToKill" -ForegroundColor DarkYellow
                Stop-Process -Id $pidToKill -Force -ErrorAction SilentlyContinue
            }
        }
        Write-Host "Successfully stopped all processes on port 8080." -ForegroundColor Green
    } else {
        Write-Host "Port 8080 is free. Tomcat is stopped." -ForegroundColor Green
    }
} catch {
    Write-Warning "Could not retrieve/kill process on port 8080: $_"
}
