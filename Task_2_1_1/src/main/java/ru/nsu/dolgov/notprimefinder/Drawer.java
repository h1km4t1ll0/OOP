package ru.nsu.dolgov.notprimefinder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Class to draw a chart using JFree.
 */
public class Drawer {
    private final JFreeChart freeChart;

    public Drawer(XYSeriesCollection dataset) {
        freeChart = chartBuilder(dataset);
    }

    /**
     * Creates a file with a chart in a root of the project.
     *
     * @throws java.io.IOException if ChartUtils cant save a file.
     */
    public void createFile() throws java.io.IOException {
        java.io.File file = new java.io.File("chart.png");
        ChartUtils.saveChartAsPNG(file, freeChart, 1000, 1000);
    }

    /**
     * Builder for a chart.
     *
     * @param dataset dataset to be displayed.
     * @return a chart.
     */
    private JFreeChart chartBuilder(XYSeriesCollection dataset) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "График зависимости времени исполнения вычислений от количества данных",
            "Длина массива, шт",
            "Время, с",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            false,
            false
        );
        XYPlot xyPlot = chart.getXYPlot();
        xyPlot.setAxisOffset(new RectangleInsets(1, 1, 1, 1));
        ValueAxis axis = xyPlot.getDomainAxis();
        axis.setAxisLineVisible(false);
        return chart;
    }
}
