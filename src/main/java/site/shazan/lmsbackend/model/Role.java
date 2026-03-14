package site.shazan.lmsbackend.model;

import java.util.Locale;

public enum Role {
    ADMIN((short) 0),
    USER((short) 1),
    TEACHER((short) 2);

    private final short dbValue;

    Role(short dbValue) {
        this.dbValue = dbValue;
    }

    public short getDbValue() {
        return dbValue;
    }

    public static Role fromDbValue(Short value) {
        if (value == null) {
            return USER;
        }

        for (Role role : values()) {
            if (role.dbValue == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role db value: " + value);
    }

    public static Role fromText(String value) {
        if (value == null || value.isBlank()) {
            return USER;
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT);
        return switch (normalized) {
            case "ADMIN" -> ADMIN;
            case "USER", "STUDENT" -> USER;
            case "TEACHER", "INSTRUCTOR" -> TEACHER;
            default -> throw new IllegalArgumentException("Invalid role: " + value + ". Allowed roles: ADMIN, USER/STUDENT, TEACHER/INSTRUCTOR");
        };
    }
}
