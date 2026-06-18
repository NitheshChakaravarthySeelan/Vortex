#!/usr/bin/env bash
set -euo pipefail

PORT=${1:-8080}

echo "==> Vortex dev mode (embedded Kafka + H2 on port $PORT)"
echo ""

fuser -k "$PORT/tcp" 2>/dev/null || true

mvn -Pdev spring-boot:run \
  -Dspring-boot.run.profiles=dev \
  -Dspring-boot.run.jvmArguments="-Dserver.port=$PORT"
