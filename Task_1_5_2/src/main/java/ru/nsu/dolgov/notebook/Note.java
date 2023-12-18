package ru.nsu.dolgov.notebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Class for note implementation.
 */
public class Note implements Comparable<Note> {
    private final String creationDate;
    private final String id;
    private final String summary;

    private final String content;

    Note(String summary, String content) {
        this.summary = summary;
        this.content = content;
        this.id = UUID.randomUUID().toString();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        this.creationDate = format.format(new java.util.Date());
    }

    Note(
        @JsonProperty("summary") String summary,
        @JsonProperty("content") String content,
        @JsonProperty("creationDate") String creationDate,
        @JsonProperty("id") String id
    ) {
        this.summary = summary;
        this.content = content;
        this.id = id;
        this.creationDate = creationDate;
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
    public String getCreationDate() {
        return this.creationDate;
    }

    /**
     * Method to compare two notes.
     *
     * @param o the object to be compared.
     * @return result of the comparison.
     */
    @Override
    public int compareTo(Note o) {
        return this.getCreationDate().compareTo(o.getCreationDate());
    }
}
