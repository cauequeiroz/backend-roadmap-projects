package domain;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Task {
    private final int id;
    private String description;
    private Status status;
    private final ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public Task(int id, String description) {
        if (description.isBlank()) {
            throw new IllegalArgumentException("You must pass a description at model.Task creation.");
        }

        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public Task(int id, String description, int status, String createdAt, String updatedAt) {
        this.id = id;
        this.description = description;
        this.status = Status.values()[status];
        this.createdAt = ZonedDateTime.parse(createdAt);
        this.updatedAt = ZonedDateTime.parse(updatedAt);
    }

    public void update(String description) {
        if (description.isBlank()) {
            throw new IllegalArgumentException("You must pass a description at model.Task update.");
        }

        this.description = description;
        this.updatedAt = ZonedDateTime.now();
    }

    public void markInProgress() {
        this.status = Status.IN_PROGRESS;
        this.updatedAt = ZonedDateTime.now();
    }

    public void markComplete() {
        this.status = Status.DONE;
        this.updatedAt = ZonedDateTime.now();
    }

    public Status getStatus() {
        return this.status;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public String toString() {
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.US);

        return String.format(
                "[%d] %s | model.Status: %s | createdAt: %s | updatedAt: %s",
                this.id, this.description, this.status, this.createdAt.format(customFormatter), this.updatedAt.format(customFormatter)
        );
    }
}
