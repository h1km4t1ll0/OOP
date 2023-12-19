package ru.nsu.dolgov.findstring;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for searching substrings.
 */
public class StringFinder implements Finder {
    String filename;
    String searchString;
    int chunkSize;
    int outputArraySize;
    boolean writeInFile;
    FileWriter outputFile = null;

    /**
     * StringFinder constructor.
     *
     * @param filename        filename to read from.
     * @param searchString    string to search.
     * @param chunkSize       chunk size.
     * @param outputArraySize maximal output array size.
     * @param writeInFile     flag indicating whether write data to file or not.
     */
    StringFinder(String filename, String searchString, int chunkSize, int outputArraySize, boolean writeInFile) {
        this.filename = filename;
        this.searchString = searchString;
        this.chunkSize = chunkSize;
        this.outputArraySize = outputArraySize;
        this.writeInFile = writeInFile;
    }

    /**
     * StringFinder constructor.
     *
     * @param filename        filename to read from.
     * @param searchString    string to search.
     * @param chunkSize       chunk size.
     * @param outputArraySize maximal output array size.
     */
    StringFinder(String filename, String searchString, int chunkSize, int outputArraySize) {
        this.filename = filename;
        this.searchString = searchString;
        this.chunkSize = chunkSize;
        this.outputArraySize = outputArraySize;
        this.writeInFile = false;
    }

    /**
     * StringFinder constructor.
     *
     * @param filename     filename to read from.
     * @param searchString string to search.
     * @param writeInFile  flag indicating whether write data to file or not.
     */
    StringFinder(String filename, String searchString, boolean writeInFile) {
        this.filename = filename;
        this.searchString = searchString;
        this.writeInFile = writeInFile;
        this.chunkSize = 1024;
        this.outputArraySize = 10000;
    }

    // вынести общую часть
    // gt


    /**
     * StringFinder constructor.
     *
     * @param filename     filename to read from.
     * @param searchString string to search.
     */
    StringFinder(String filename, String searchString) {
        this.filename = filename;
        this.searchString = searchString;
        this.chunkSize = 1024;
        this.outputArraySize = 10000;
        this.writeInFile = false;
    }

    /**
     * Mrthod to get resources.
     *
     * @param resourceName name of the resource.
     * @return stream of the resource.
     */
    private static InputStream getResourceFileStream(String resourceName) {
        // Get the ClassLoader for the SubstringFinder class
        ClassLoader classLoader = StringFinder.class.getClassLoader();
        // Get an InputStream for the resource file using the ClassLoader
        return classLoader.getResourceAsStream(resourceName);
    }

    /**
     * Method to search substring in a large file.
     *
     * @return array of entry indices.
     * @throws IOException when a problem with a file occurred.
     */
    public List<Long> search() throws IOException {

        char[] firstChunk = new char[this.chunkSize];
        char[] secondChunk = new char[this.chunkSize];

        List<Long> entryArray = new ArrayList<>();

        int foundCounter = 0;
        int currentChunk = 2;
        long chunkCounter = 0;
        int indexInFile = 0;

        BufferedReader in = new BufferedReader(
                new InputStreamReader(getResourceFileStream(this.filename), StandardCharsets.UTF_8)
        );

        in.read(firstChunk);
        in.read(secondChunk);

        while (true) {
            StringBuilder line = new StringBuilder();
            line.append(firstChunk);
            line.append(secondChunk);

            int indexInChunk;
            if (indexInFile - this.chunkSize < 0) {
                indexInChunk = line.indexOf(this.searchString, 0);
            } else {
                indexInChunk = line.indexOf(this.searchString, indexInFile - this.chunkSize);
            }

            while (indexInChunk != -1) {
                long entryInFile;

                if (foundCounter == this.outputArraySize - 1) {
                    System.out.println("Entries number is larger than " +
                            "provided outputArraySize. Starting to write in a file output.txt...");
                    this.writeInFile(entryArray);
                    this.writeInFile = true;
                }

                if (chunkCounter == 0) {
                    entryInFile = indexInChunk;
                } else {
                    entryInFile = chunkCounter * this.chunkSize + indexInChunk;
                }

                if (this.writeInFile) {
                    this.writeInFile(entryInFile);
                } else {
                    entryArray.add(entryInFile);
                }

                foundCounter++;
                indexInFile = indexInChunk;
                indexInChunk = line.indexOf(this.searchString, indexInChunk + 1);
            }
            chunkCounter++;

            if (currentChunk == 1) {
                if (in.read(secondChunk) == -1) {
                    break;
                }
                currentChunk = 2;
            } else {
                if (in.read(firstChunk) == -1) {
                    break;
                }
                currentChunk = 1;
            }
        }

        in.close();

        if (this.outputFile != null) {
            this.outputFile.close();
        }

        System.out.println("Search completed!");

        if (this.writeInFile) {
            return new ArrayList<>();
        }

        if (foundCounter > 0) {
            return entryArray;
        }

        return new ArrayList<>();
    }

    /**
     * Method to write an array with indices to a file.
     *
     * @param arrayOfEntries array of entries.
     * @throws IOException when a problem occurred with writing to a file.
     */
    private void writeInFile(List<Long> arrayOfEntries) throws IOException {
        this.outputFile = new FileWriter("output.txt");
        for (long entryIndex : arrayOfEntries) {
            this.outputFile.write(Long.toString(entryIndex));
            outputFile.write("\n");
        }
    }

    /**
     * Method to write an array with indices to a file.
     *
     * @param entryIndex single entry.
     * @throws IOException when a problem occurred with writing to a file.
     */
    private void writeInFile(long entryIndex) throws IOException {
        this.outputFile.write(Long.toString(entryIndex));
        this.outputFile.write("\n");
    }
}