package ru.nsu.dolgov.notebook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class for notebook implementation
 */
public class Notebook {
    private final Map<String, Note> notes;

    Notebook(Map<String, Note> notes) {
        this.notes = notes;
    }

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
     * Method to get an ID by summary.
     *
     * @param summary keywords to search in summary.
     * @return id of a note.
     */
    public String getIdBySummary(String summary) {
        return this.notes.values().stream().filter(
                (each) -> each.getSummary()
                        .toLowerCase()
                        .contains(summary.toLowerCase())
        ).collect(Collectors.toList()).get(0).getId();
    }

    /**
     * Method to get a map of notes.
     *
     * @return map of notes.
     */
    public Map<String, Note> getNotesMap() {
        return this.notes;
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
        Date from,
        Date to,
        List<String> keyWords
    ) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return this.notes.values()
            .stream()
            .filter(
                (each) -> {
                    try {
                        return format.parse(each.getCreationDate()).before(to)
                            && format.parse(each.getCreationDate()).after(from)
                            && keyWords.stream().anyMatch(
                            (keyWord) -> each.getSummary()
                                .toLowerCase()
                                .contains(keyWord.toLowerCase())
                        );
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            )
            .sorted()
            .collect(Collectors.toList());
    }
}
