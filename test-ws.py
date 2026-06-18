#!/usr/bin/env python3
"""Test the Vortex WebSocket chat backend with multiple simulated users."""

import json
import sys
import threading
import time
import uuid
from websocket import WebSocketApp

import os
WS_URL = os.environ.get("WS_URL", "ws://localhost:8090/ws")
CHANNEL_ID = uuid.UUID("00000000-0000-0000-0000-000000000001")


class ChatClient:
    def __init__(self, name):
        self.name = name
        self.user_id = uuid.uuid4()
        self.ws = None
        self.received = []
        self.connected = threading.Event()
        self._stop = False

    def on_message(self, ws, message):
        print(f"  [{self.name}] << {message}")
        self.received.append(message)

    def on_error(self, ws, error):
        print(f"  [{self.name}] ERROR: {error}")

    def on_close(self, ws, close_status_code, close_msg):
        print(f"  [{self.name}] connection closed")

    def on_open(self, ws):
        print(f"  [{self.name}] connected as {self.user_id}")
        self.connected.set()
        self._auth()

    def _auth(self):
        payload = json.dumps({
            "operation": "AUTHENTICATE",
            "data": {"userId": str(self.user_id)}
        })
        self.ws.send(payload)

    def join_channel(self, channel_id):
        payload = json.dumps({
            "operation": "JOIN_CHANNEL",
            "data": {"channelId": str(channel_id)}
        })
        self.ws.send(payload)
        print(f"  [{self.name}] joined channel {channel_id}")

    def send_message(self, channel_id, content):
        payload = json.dumps({
            "operation": "CHAT_MESSAGE",
            "data": {"channelId": str(channel_id), "content": content}
        })
        self.ws.send(payload)
        print(f"  [{self.name}] sent: {content}")

    def start(self):
        self.ws = WebSocketApp(
            WS_URL,
            on_open=self.on_open,
            on_message=self.on_message,
            on_error=self.on_error,
            on_close=self.on_close,
        )
        t = threading.Thread(target=self.ws.run_forever, daemon=True)
        t.start()
        if not self.connected.wait(timeout=5):
            raise TimeoutError(f"{self.name} failed to connect")
        time.sleep(0.3)  # let auth process

    def stop(self):
        if self.ws:
            self.ws.close()


def main():
    print("Starting Vortex WebSocket test...")
    print()

    clients = [ChatClient(f"User-{i}") for i in range(1, 4)]
    for c in clients:
        c.start()
        time.sleep(0.2)

    print()

    # All join the same channel
    for c in clients:
        c.join_channel(CHANNEL_ID)
        time.sleep(0.2)

    print()

    # User-1 sends a message
    clients[0].send_message(CHANNEL_ID, "Hello from User-1!")
    time.sleep(2)

    print()
    print("--- Results ---")
    for c in clients:
        print(f"  {c.name} received {len(c.received)} messages: {c.received}")

    # Check that other users got the message
    if len(clients[1].received) >= 1 and len(clients[2].received) >= 1:
        print("\n  PASS: Other users received the broadcast!")
    else:
        print("\n  FAIL: Broadcast did not reach all users")

    for c in clients:
        c.stop()

    print("\nTest complete.")


if __name__ == "__main__":
    main()
