# Vortex

Real-time chat backend built with Spring Boot 4, WebFlux (reactive WebSockets), and Kafka. Runs **without Docker** — uses embedded Kafka (KRaft) and H2 database locally.

## Prerequisites

- Java 21+
- Maven wrapper (included — `./mvnw`)

## Run

```bash
./mvnw -Pdev spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-Dserver.port=8090"
```

Or use the helper script:

```bash
./run-dev.sh 8090
```

**You must use `-Pdev`** — it pulls `spring-kafka-test` at compile scope so the embedded Kafka broker starts. Without it, Kafka won't be available and the app won't function.

## What runs where

| Service | Implementation |
|---|---|
| Database | H2 in-memory (replaces Postgres) |
| Message broker | Embedded Kafka KRaft (random port) |
| Web server | Netty (reactive) on port 8090 |
| Auth | Placeholder `AUTHENTICATE` op (no OAuth2 yet) |

## WebSocket Protocol

Endpoint: `ws://localhost:8090/ws`

All messages are JSON with the shape:

```json
{"operation": "<OP>", "data": { ... }}
```

### Operations

| Op | Data | Description |
|---|---|---|
| `AUTHENTICATE` | `{"userId": "<uuid>"}` | Register session |
| `JOIN_CHANNEL` | `{"channelId": "<uuid>"}` | Subscribe to channel |
| `CHAT_MESSAGE` | `{"content": "...", "channelId": "<uuid>"}` | Send and broadcast message |

### Response

After `AUTHENTICATE`, the server responds with:

```json
{"operation":"AUTHENTICATED","data":{"status":"authenticated","userId":"..."}}
```

Incoming `CHAT_MESSAGE` events broadcast to all channel members:

```json
{"operation":"CHAT_MESSAGE","data":{"messageId":"...","userId":"...","channelId":"...","content":"...","createdAt":...}}
```

## Test

```bash
python3 -m venv /tmp/ws-venv
/tmp/ws-venv/bin/pip install websockets
/tmp/ws-venv/bin/python3 test-ws.py
```
