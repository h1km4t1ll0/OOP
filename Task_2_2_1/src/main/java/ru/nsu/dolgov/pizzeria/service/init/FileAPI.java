package ru.nsu.dolgov.pizzeria.service.init;

import com.google.gson.Gson;
import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.Config;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;

import java.io.*;
import java.util.List;

public class FileAPI {
    private final String filename;

    public FileAPI(String filename) {
        this.filename = filename;
    }

    public Config parse() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(this.filename)) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void serialize(List<Order> payload) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(payload);
        try (Writer writer = new FileWriter(this.filename)) {
           writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
