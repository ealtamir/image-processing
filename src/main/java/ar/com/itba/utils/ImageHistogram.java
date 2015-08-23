package ar.com.itba.utils;


import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.SwingWrapper;

import java.awt.image.BufferedImage;

public class ImageHistogram {

    private BufferedImage image;
    private String title;

    private final int BLUE_MASK = 0x000000FF;
    private final int GRAY_LEVELS = 256;

    public ImageHistogram(String title, BufferedImage image) {
        this.image = image;
        this.title = title;

        Chart chart = new ChartBuilder().chartType(StyleManager.ChartType.Bar)
            .width(800).height(600).title("Score Histogram")
            .xAxisTitle("Grises").yAxisTitle("Cantidad").build();

        populateChart(chart);
        new SwingWrapper(chart).displayChart();
    }

    private void populateChart(Chart chart) {
        int[] grayLevels = new int[GRAY_LEVELS];
        int[] xAxis = new int[GRAY_LEVELS];
        int buffer = 0;

        for (int i = 1; i < GRAY_LEVELS; i++) {
            xAxis[i] += xAxis[i - 1] + 1;
        }

        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                buffer = BLUE_MASK & image.getRGB(x, y);
                grayLevels[buffer] += 1;
            }
        }
        chart.addSeries("Levels", xAxis, grayLevels);
    }

}
