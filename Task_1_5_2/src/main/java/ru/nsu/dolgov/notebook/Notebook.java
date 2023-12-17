package ru.nsu.dolgov.notebook;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class for notebook implementation
 */
public class Notebook {
    private final Map<String, Note> notes = new HashMap<>();

    /**
     * Method to add a note.
     *
     * @param summary summary of a note.
     * @param content content of a note.
     */
    public void addNote(String summary, String content) {
        Note note = new Note(summary, content);
        notes.put(note.getId(), note);

    }

    /**
     * Method to remove a note.
     *
     * @param id id of a note to delete.
     */
    public void removeNote(String id) {
        notes.remove(id);
    }
}
