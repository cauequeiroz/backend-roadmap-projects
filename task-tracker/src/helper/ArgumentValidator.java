package helper;

import domain.Status;

public class ArgumentValidator {
    public static String checkValidString(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }

    public static int checkValidNumber(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(message);
        }
    }

    public static Status checkValidStatus(String value, String message) {
        return switch (value) {
            case "done" -> Status.DONE;
            case "todo" -> Status.TODO;
            case "in-progress" -> Status.IN_PROGRESS;
            default -> throw new IllegalArgumentException(message);
        };
    }
}
