package ru.nsu.dolgov.notprimefinder;


import java.io.IOException;
import java.util.Arrays;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

/**
 * Class for Drawer class test.
 */
public class DrawerTest {
    /**
     * Method to build an array.
     *
     * @param length length of an array.
     * @return built array.
     */
    private int[] buildArray(int length) {
        int[] array = new int[length];
        Arrays.fill(array, 0, length - 1, 150000001);
        array[length - 1] = 4;
        return array;
    }

    /**
     * Calculates an average time to do the equation.
     *
     * @param nonPrimeChecker Given checker.
     * @param array           An array to check.
     * @return Average time to do the equation.
     * @throws InterruptedException if the current thread was interrupted.
     */
    private long getAverageTime(NonPrime nonPrimeChecker, int[] array) throws InterruptedException {
        long time = 0;

        for (int i = 0; i < 3; ++i) {
            long startTime = System.nanoTime();
            nonPrimeChecker.check(array);
            long endTime = System.nanoTime();
            time += endTime - startTime;
        }

        return time / 3;
    }


    /**
     * Method to draw a chart and test Drawer class.
     *
     * @throws InterruptedException if the current thread was interrupted while waiting.
     * @throws IOException          if an I/O error occurs during file creation or saving.
     */
    @Test
    public void drawerTest() throws InterruptedException, IOException {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries linearSeries = new XYSeries("Линейно");
        XYSeries threaded2Series = new XYSeries("2 потока");
        XYSeries threaded4Series = new XYSeries("4 потока");
        XYSeries threaded8Series = new XYSeries("8 потоков");
        XYSeries threaded16Series = new XYSeries("16 потоков");
        XYSeries threaded32Series = new XYSeries("32 потока");
        XYSeries threaded64Series = new XYSeries("64 потока");
        XYSeries streamSeries = new XYSeries("Параллельный стрим");

        for (int sizeOfArray = 40; sizeOfArray <= 4000000; sizeOfArray *= 10) {
            int[] array = buildArray(sizeOfArray);
            linearSeries.add(sizeOfArray, getAverageTime(new NonPrimeLinearChecker(), array));
            threaded2Series.add(sizeOfArray, getAverageTime(new NonPrimeThreadChecker(2), array));
            threaded4Series.add(sizeOfArray, getAverageTime(new NonPrimeThreadChecker(4), array));
            threaded8Series.add(sizeOfArray, getAverageTime(new NonPrimeThreadChecker(8), array));
            threaded16Series.add(sizeOfArray, getAverageTime(new NonPrimeThreadChecker(16), array));
            threaded32Series.add(sizeOfArray, getAverageTime(new NonPrimeThreadChecker(32), array));
            threaded64Series.add(sizeOfArray, getAverageTime(new NonPrimeThreadChecker(64), array));
            streamSeries.add(sizeOfArray, getAverageTime(new NonPrimeStreamChecker(), array));
            System.out.println(sizeOfArray);
        }

        dataset.addSeries(linearSeries);
        dataset.addSeries(threaded2Series);
        dataset.addSeries(threaded4Series);
        dataset.addSeries(threaded8Series);
        dataset.addSeries(threaded16Series);
        dataset.addSeries(threaded32Series);
        dataset.addSeries(threaded64Series);
        dataset.addSeries(streamSeries);
        Drawer drawer = new Drawer(dataset);
        drawer.createFile();
    }
}
