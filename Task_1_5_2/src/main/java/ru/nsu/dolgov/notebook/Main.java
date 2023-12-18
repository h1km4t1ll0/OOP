package ru.nsu.dolgov.notebook;

import java.io.IOException;
import java.text.ParseException;
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
        CommandHandler commandHandler = new CommandHandler();

        try {
            parser.parseArgument(args);

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

        if (addCommand) {
            addCommand = false;
            System.out.println(commandHandler.addCommand(arguments));
        }

        if (showCommand) {
            showCommand = false;
            System.out.println(commandHandler.showCommand(arguments));
        }

        if (rmCommand) {
            rmCommand = false;
            System.out.println(commandHandler.removeCommand(arguments));
        }
    }
}
