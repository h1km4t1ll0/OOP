package ru.nsu.dolgov.pizzeria;

import org.jfree.chart.plot.ThermometerPlot;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Baker;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Deliverer;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Runner;
import ru.nsu.dolgov.pizzeria.service.init.Init;
import ru.nsu.dolgov.pizzeria.service.queues.BaseQueue;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Init initializer = new Init("config.json");
        Runner runner = new Runner(
            initializer,
            initializer.getConfiguration().bakery.durationOfTheDayInSeconds * 1000L
        );
        runner.start();
        runner.join();
    }
}
