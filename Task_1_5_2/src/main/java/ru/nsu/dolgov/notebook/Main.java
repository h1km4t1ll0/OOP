package ru.nsu.dolgov.notebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.util.Scanner;

/**
 * An entrypoint for a notebook.
 */
public class Main {
    /**
     * Optional arguments for operations with the notebook.
     */
    @Option(name = "-add", usage = "Command to add an element: -add [key] [value]")
    private boolean addCommand;

    @Option(name = "-show", usage = "Command to show all: -show")
    private boolean showCommand;

    @Option(name = "-rm", usage = "Command to delete an element: -rm [key]")
    private boolean rmCommand;

    @Argument
    private List<String> arguments = new ArrayList<>();

    /**
     * An entrypoint.
     */
    public static void main() {

    }
}
