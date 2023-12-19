package ru.nsu.dolgov.findstring.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileCreator {
    static String getAlphaNumericString(int size) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public static void createFile(int size) {
        String fileName = "large.txt";
        long sizeInBytes = size * 1024L * 1024L * 1024L;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            StringBuilder line = new StringBuilder();

            while (sizeInBytes > 0) {
                line.append(getAlphaNumericString(1024 * 1024));
                writer.write(line.toString());
                line.setLength(0);
                sizeInBytes -= 1024 * 1024;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Large file created: " + fileName);
    }
}