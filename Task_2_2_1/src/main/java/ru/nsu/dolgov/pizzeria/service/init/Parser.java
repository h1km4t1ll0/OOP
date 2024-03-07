package ru.nsu.dolgov.pizzeria.service.init;

import com.google.gson.Gson;
import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.Config;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Parser {
    private final String filename;

    public Parser(String filename) {
        this.filename = filename;
    }

    public Config parse() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(this.filename)) {
            Config staff = gson.fromJson(reader, Config.class);
            System.out.println(staff);
            return staff;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
