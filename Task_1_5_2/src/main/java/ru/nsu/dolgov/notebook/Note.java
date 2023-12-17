package ru.nsu.dolgov.notebook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * Class for note implementation.
 */
public class Note {
    private final LocalDateTime createdAt;
    private final UUID id;
    private final String summary;
    private final String content;

    Note(String summary, String content) {
        this.summary = summary;
        this.content = content;
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public String getSummary() {
        return this.summary;
    }

    public String getContent() {
        return this.content;
    }

    public String getId() {
        return this.id.toString();
    }
}
