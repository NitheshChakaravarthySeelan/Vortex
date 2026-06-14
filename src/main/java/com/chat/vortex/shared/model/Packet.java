package com.chat.vortex.shared.model;

import com.fasterxml.jackson.databind.JsonNode;

public record Packet(String operation, JsonNode data){}