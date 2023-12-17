package ru.nsu.dolgov.notebook;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * An entrypoint for a notebook.
 */
public class Main {
    @Option(name = "-add", usage = "Command to add a note: -add [summary] [content]")
    private boolean addCommand;

    @Option(name = "-show", usage = "Command to show all notes: -show")
    private boolean showCommand;

    @Option(name = "-rm", usage = "Command to delete a note: -rm [summary]")
    private boolean rmCommand;

    @Argument
    private List<String> arguments = new ArrayList<>();

    /**
     * An entrypoint.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        new Main().handleArguments(args);
    }

    /**
     * Method to handle command line args.
     *
     * @param args arguments to be processed.
     * @throws IOException when cant parse arguments.
     */
    public void handleArguments(String[] args) throws IOException, ParseException {
        CmdLineParser parser = new CmdLineParser(this);
        FileAPI fileAPI = new FileAPI("notes.json");
        Notebook notebook = new Notebook(fileAPI.deserialize());

        try {
            parser.parseArgument(args);

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

        if (addCommand) {
            addCommand = false;

            if (arguments.size() > 2) {
                System.out.println("Too many arguments!");
                return;
            }

            if (arguments.size() < 2) {
                System.out.println("Too little arguments!");
                return;
            }

            notebook.addNote(arguments.get(0), arguments.get(1));
            fileAPI.serialize(notebook.getNotesMap());
            return;
        }

        if (showCommand) {
            showCommand = false;

            if (arguments.isEmpty()) {
                List<Note> notes = notebook.getNotes();
                notes.forEach(
                        (each) -> {
                            System.out.println("Summary: " + each.getSummary());
                            System.out.println("Content: " + each.getContent());
                            System.out.println();
                        }
                );
                return;
            }

            if (arguments.size() < 3) {
                System.out.println("Invalid number of arguments!");
                return;
            }


            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            List<Note> notes = notebook.getNotes(
                format.parse(arguments.get(0)),
                format.parse(arguments.get(1)),
                arguments.subList(2, arguments.size() - 1)
            );

            notes.forEach(
                    (each) -> {
                        System.out.println("Summary: " + each.getSummary());
                        System.out.println("Content: " + each.getContent());
                        System.out.println();
                    }
            );
            return;
        }

        if (rmCommand) {
            rmCommand = false;

            notebook.removeNote(notebook.getIdBySummary(arguments.get(0)));
            fileAPI.serialize(notebook.getNotesMap());
        }
    }
}
