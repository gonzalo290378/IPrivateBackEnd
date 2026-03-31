Write-Host "🚀 Levantando config-server..."
Start-Process powershell -ArgumentList "cd config-server; ./mvnw spring-boot:run"
Start-Sleep -Seconds 20

Write-Host "🚀 Levantando eureka..."
Start-Process powershell -ArgumentList "cd eureka; ./mvnw spring-boot:run"
Start-Sleep -Seconds 20

Write-Host "🚀 Levantando ms-auth-server..."
Start-Process powershell -ArgumentList "cd ms-auth-server; ./mvnw spring-boot:run"
Start-Sleep -Seconds 15

Write-Host "🚀 Levantando ms-users..."
Start-Process powershell -ArgumentList "cd ms-users; ./mvnw spring-boot:run"
Start-Sleep -Seconds 15

Write-Host "🚀 Levantando ms-api-ext..."
Start-Process powershell -ArgumentList "cd ms-api-ext; ./mvnw spring-boot:run"
Start-Sleep -Seconds 15

Write-Host "🚀 Levantando ms-free-area..."
Start-Process powershell -ArgumentList "cd ms-free-area; ./mvnw spring-boot:run"
Start-Sleep -Seconds 15

Write-Host "🚀 Levantando ms-private-area..."
Start-Process powershell -ArgumentList "cd ms-private-area; ./mvnw spring-boot:run"
Start-Sleep -Seconds 15

Write-Host "🚀 Levantando ms-gateway..."
Start-Process powershell -ArgumentList "cd ms-gateway; ./mvnw spring-boot:run"
Start-Sleep -Seconds 15

Write-Host "🚀 Levantando ms-resource-server..."
Start-Process powershell -ArgumentList "cd ms-resource-server; ./mvnw spring-boot:run"

Write-Host "✅ Todos los servicios iniciados"