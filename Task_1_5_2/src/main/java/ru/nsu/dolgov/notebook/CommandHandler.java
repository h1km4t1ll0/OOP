package ru.nsu.dolgov.notebook;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Class for command handling.
 */
public class CommandHandler {
    private final FileAPI fileAPI;
    private final Notebook notebook;

    CommandHandler() throws IOException {
        this.fileAPI = new FileAPI("notes.json");
        this.notebook = new Notebook(fileAPI.deserialize());
    }

    /**
     * Add command implementation.
     *
     * @param arguments list of args from user.
     * @return result of the operation.
     * @throws IOException when cant serialize.
     */
    public String addCommand(List<String> arguments) throws IOException {
        if (arguments.size() != 2) {
            return "Too little arguments!";
        }

        notebook.addNote(arguments.get(0), arguments.get(1));
        fileAPI.serialize(notebook.getNotesMap());
        return "Created!";
    }

    /**
     * Show command implementation.
     *
     * @param arguments list of args from user.
     * @return requested notes.
     * @throws ParseException when cant parse.
     */
    public String showCommand(List<String> arguments) throws ParseException {
        StringBuilder sb = new StringBuilder();
        if (arguments.isEmpty()) {
            List<Note> notes = notebook.getNotes();
            notes.forEach(
                    (each) -> sb.append("Summary: ")
                            .append(each.getSummary())
                            .append("\n")
                            .append("Content: ")
                            .append(each.getContent())
                            .append("\n")
            );
            return sb.toString();
        }

        if (arguments.size() < 3) {
            return "Invalid number of arguments!";
        }

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        List<Note> notes = notebook.getNotes(
                format.parse(arguments.get(0)),
                format.parse(arguments.get(1)),
                arguments.subList(2, arguments.size())
        );

        notes.forEach(
                (each) -> sb.append("Summary: ")
                        .append(each.getSummary())
                        .append("\n")
                        .append("Content: ")
                        .append(each.getContent())
                        .append("\n")
        );
        return sb.toString();
    }

    /**
     * Remove command implementation.
     *
     * @param arguments list of args from user.
     * @return result of the operation.
     * @throws IOException when cant serialize.
     */
    public String removeCommand(List<String> arguments) throws IOException {
        notebook.removeNote(notebook.getIdBySummary(arguments.get(0)));
        fileAPI.serialize(notebook.getNotesMap());
        return "Removed!\n";
    }
}
