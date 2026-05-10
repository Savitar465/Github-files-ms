package com.githubx.githubfilesms.model.enums;

public enum GitObjectType {
    FILE("file"),
    DIRECTORY("dir");

    private final String value;

    GitObjectType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GitObjectType fromValue(String value) {
        for (GitObjectType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown GitObjectType: " + value);
    }
}
