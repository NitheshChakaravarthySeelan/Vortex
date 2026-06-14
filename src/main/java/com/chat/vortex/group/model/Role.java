package com.chat.vortex.group.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN("admin"),
    MEMBER("member"),
    MODERATOR("moderator");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
