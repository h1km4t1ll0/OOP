package ru.nsu.dolgov.pizzeria.service;

import java.util.Random;

public class Utils {
    public static int getRandomDuration(int from, int to) {
        Random random = new Random();
        return random.nextInt((to - from) + 1) + from;
    }
}
