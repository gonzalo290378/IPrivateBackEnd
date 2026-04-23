#!/bin/bash

echo "🚀 Iniciando ecosistema de microservicios..."

# Función para levantar servicio
start_service() {
  name=$1
  echo "▶️  Iniciando $name..."
  (cd $name && mvn spring-boot:run) &
  sleep 5
}

# 🔥 ORDEN IMPORTANTE (respeta dependencias)

start_service "config-server"
start_service "eureka"
start_service "ms-auth-server"
start_service "ms-users"
start_service "ms-free-area"
start_service "ms-private-area"
start_service "ms-messages"
start_service "ms-follow"
start_service "ms-api-ext"
start_service "ms-gateway"
start_service "ms-resource-server"

echo "⏳ Todos los servicios lanzados en background"
echo "📡 Revisar logs en esta terminal"

wait