package ru.nsu.dolgov.notebook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * Class for note implementation.
 */
public class Note implements Comparable<Note>{
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

    /**
     * Getter fot a summary.
     *
     * @return summary of a note.
     */
    public String getSummary() {
        return this.summary;
    }

    /**
     * Getter fot a content.
     *
     * @return content of a note.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Getter fot an id.
     *
     * @return id of a note.
     */
    public String getId() {
        return this.id.toString();
    }

    /**
     * Getter fot a creation date.
     *
     * @return creation date.
     */
    public LocalDateTime getCreationDate() {
        return this.createdAt;
    }

    /**
     * Method to compare two notes.
     * @param o the object to be compared.
     * @return result of the comparison.
     */
    @Override
    public int compareTo(Note o) {
        return this.getCreationDate().compareTo(o.getCreationDate());
    }
}
