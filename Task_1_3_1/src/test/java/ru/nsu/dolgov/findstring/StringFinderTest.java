package ru.nsu.dolgov.findstring;

import static java.nio.file.Files.createFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.nsu.dolgov.findstring.utils.FileCreator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Tests for the graph implementation class.
 */
public class StringFinderTest {
    @Test
    void check() throws IOException {
        StringFinder finder = new StringFinder("small.txt", "This is");
        assertEquals(12, finder.search().size());
    }

    @Test
    void checkChinese() throws IOException {
        StringFinder finder = new StringFinder("chinese.txt", "获");
        assertEquals(12, finder.search().get(0));
    }

    @Test
    void checkEmpty() throws IOException {
        StringFinder finder = new StringFinder("empty.txt", "获");
        assertTrue(finder.search().isEmpty());
    }

    // тест на пересечение буфера (в маленьком файле), несуществующий символ
    // в гите создавать большой рандомный файл

    @Test
    void checkLarge() throws IOException {
//        StringFinder finder = new StringFinder("large.txt", "This is ");
//        finder.search();
        FileCreator.createFile(20);
    }
}
