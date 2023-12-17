package ru.nsu.dolgov.notebook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * Method to get sorted notes.
     *
     * @return list of sorted notes.
     */
    public List<Note> getNotes() {
        return this.notes.values().stream().sorted().collect(Collectors.toList());
    }

    /**
     * Method to get notes with filters.
     *
     * @return list of notes.
     */
    public List<Note> getNotes(
        LocalDateTime from,
        LocalDateTime to,
        ArrayList<String> keyWords
    ) {
        return this.notes.values().stream().filter(
            (each) -> each.getCreationDate().isBefore(to)
                    && each.getCreationDate().isAfter(from)
                    && keyWords.stream().anyMatch(
                    (keyWord) -> each.getSummary()
                            .toLowerCase()
                            .contains(keyWord.toLowerCase())
            )
        ).collect(Collectors.toList());
    }
}
