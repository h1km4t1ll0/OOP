package ru.nsu.dolgov.notebook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for Notebook class test.
 */
public class NotebookTest {
    private Map<String, Note> noteMap;
    private Notebook notebook;
    private FileApi fileApi;

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(Path.of("notes.json"));
    }

    @BeforeEach
    void mockUp() throws IOException, ParseException {
        Main.main(new String[]{"-add", "Summary4", "someData"});
        Main.main(new String[]{"-add", "Summary6", "someData3"});
        Main.main(new String[]{"-add", "Summary5", "someData2"});
        this.fileApi = new FileApi("notes.json");
        this.noteMap = fileApi.deserialize();
        this.notebook = new Notebook(noteMap);
    }

    @Test
    void testAdd() {
        assertEquals(noteMap.get(
                notebook.getIdBySummary("Summary4")
        ).getContent(), "someData");
    }

    @Test
    void testRemove() throws IOException, ParseException {
        Main.main(new String[]{"-rm", "Summary4"});
        this.noteMap = fileApi.deserialize();
        this.notebook = new Notebook(noteMap);
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> noteMap.get(notebook.getIdBySummary("Summary4")));
    }

    @Test
    void testShow() throws IOException, ParseException {
        CommandHandler commandHandler = new CommandHandler();
        String output = commandHandler.showCommand(new ArrayList<>());
        assertTrue(output.contains("Summary4")
                && output.contains("Summary5")
                && output.contains("Summary6")
                && output.contains("someData")
                && output.contains("someData2")
                && output.contains("someData3")
        );
    }

    @Test
    void testFilteredShow() throws IOException, ParseException {
        CommandHandler commandHandler = new CommandHandler();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date currentDate = new java.util.Date();
        String oldDate = format.format(currentDate.getTime() + 120000);
        String grandOldDate = format.format(currentDate.getTime() - 120000);
        List<String> args = new ArrayList<>();

        args.add(grandOldDate);
        args.add(oldDate);
        args.add("Summary4");
        String output = commandHandler.showCommand(args);
        assertTrue(output.contains("Summary4"));
    }

    @Test
    void testFilteredShowError() throws IOException, ParseException {
        CommandHandler commandHandler = new CommandHandler();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date currentDate = new java.util.Date();
        String oldDate = format.format(currentDate.getTime() + 120000);
        String grandOldDate = format.format(currentDate.getTime() - 120000);
        List<String> args = new ArrayList<>();

        args.add(grandOldDate);
        args.add(oldDate);
        String output = commandHandler.showCommand(args);
        assertEquals("Invalid number of arguments!", output);
    }
}
