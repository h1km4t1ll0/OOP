package ru.nsu.dolgov.notebook;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Notebook {
    private final Map<String, Note> notes = new HashMap<>();

    public void addNote(String summary, String content) {
        Note note = new Note(summary, content);
        notes.put(note.getId(), note);

    }

    public void removeNote(String id) {
        notes.remove(id);
    }
}
