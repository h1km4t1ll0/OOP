package ru.nsu.dolgov.notebook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * Class for Notebook class test.
 */
public class NotebookTest {

    @Test
    void testMain() {
        Main.main();
        assertTrue(true);
    }
}
