# command for force stopping pid running on port 8080
# Stop-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess -Force )

$ErrorActionPreference = "Stop"

$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot"
$env:CATALINA_HOME = "D:\WorkSpace\apache-tomcat-9.0.107\apache-tomcat-9.0.107"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

if (!(Test-Path $env:CATALINA_HOME)) {
    throw "Tomcat not found at $env:CATALINA_HOME"
}

if (!(Test-Path $env:JAVA_HOME)) {
    throw "JDK not found at $env:JAVA_HOME"
}

Write-Host "Stopping Tomcat if running..."
& "$env:CATALINA_HOME\bin\shutdown.bat" 2>$null

Start-Sleep -Seconds 2

Write-Host "Building project with Maven..."
mvn clean package

Write-Host "Finding WAR file..."
$war = Get-ChildItem ".\target\*.war"

if ($war.Count -eq 0) {
    throw "No WAR file found in target directory."
}

$war = $war[0]

Write-Host "Cleaning old Tomcat deployment..."
Remove-Item "$env:CATALINA_HOME\webapps\pms" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "$env:CATALINA_HOME\webapps\pms.war" -Force -ErrorAction SilentlyContinue

Write-Host "Deploying $($war.Name) as pms.war..."
Copy-Item $war.FullName "$env:CATALINA_HOME\webapps\pms.war"

Write-Host "Checking port 8080..."
$port8080 = netstat -ano | findstr ":8080"

if ($port8080) {
    Write-Host "Port 8080 is currently in use:"
    Write-Host $port8080
    throw "Port 8080 is already in use. Stop the existing process before starting Tomcat."
}

Write-Host "Starting Tomcat..."
& "$env:CATALINA_HOME\bin\catalina.bat" run