package ru.nsu.dolgov.pizzeria;

import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Runner;
import ru.nsu.dolgov.pizzeria.service.init.Init;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Init initializer = new Init("config.json", "dump.json");
        Runner runner = new Runner(
                initializer,
                initializer.getConfiguration().bakery.durationOfTheDayInSeconds * 1000L
        );
        runner.start();
        runner.join();
    }
}
