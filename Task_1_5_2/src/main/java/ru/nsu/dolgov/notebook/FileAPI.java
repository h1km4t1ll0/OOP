package ru.nsu.dolgov.notebook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to implement work with a .json file.
 */
public class FileAPI {
    private final String filename;

    FileAPI(String filename) {
        this.filename = filename;
    }

    /**
     * Method to serialize an internal map to a .json file.
     *
     * @param payload map with notes.
     * @throws IOException when cant write a file.
     */
    public void serialize(Map<String, Note> payload) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(
            new File(this.filename),
            payload
        );
    }

    /**
     * Method to deserialize data from a provided file.
     *
     * @return Map with notes.
     * @throws IOException when cant read a file.
     */
    public Map<String, Note> deserialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(this.filename), new TypeReference<>(){});
        } catch (FileNotFoundException e) {
            File file = new File(this.filename);
            file.createNewFile();
            return new HashMap<>();
        }
    }
}