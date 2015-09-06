package ar.com.itba.utils;


import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.SwingWrapper;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

public class ImageHistogram extends WindowAdapter {

    private JFrame swingWrapper;
    private BufferedImage image;
    private String title;

    private final int BLUE_MASK = 0x000000FF;
    private final int GRAY_LEVELS = 256;

    public ImageHistogram(String title, BufferedImage image) {
        this.image = image;
        this.title = title;

        Chart chart = new ChartBuilder().chartType(StyleManager.ChartType.Bar)
            .width(700).height(500).title("Score Histogram")
            .xAxisTitle("Grises").yAxisTitle("Cantidad").build();

        populateChart(chart);
        swingWrapper = new SwingWrapper(chart).displayChart();
        swingWrapper.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        swingWrapper.setResizable(true);
    }

    private void populateChart(Chart chart) {
        double[] grayLevels = new double[GRAY_LEVELS];
        double[] xAxis = new double[GRAY_LEVELS];
        int buffer = 0;
        int totalPixels = image.getHeight() * image.getWidth();

        for (int i = 1; i < GRAY_LEVELS; i++) {
            xAxis[i] += xAxis[i - 1] + 1;
        }

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                buffer = BLUE_MASK & image.getRGB(x, y);
                grayLevels[buffer] += 1;
            }
        }

        for (int i = 0; i < GRAY_LEVELS; i++) {
            grayLevels[i] /= totalPixels;
        }
        chart.addSeries("Levels", xAxis, grayLevels);
    }

    public void close() {
        swingWrapper.setVisible(false);
        swingWrapper.dispose();
    }

    public void hideHistogram() {
        swingWrapper.setVisible(false);
    }

    public boolean isVisible() {
        return swingWrapper.isVisible();
    }

    public void showHistogram() {
        swingWrapper.setVisible(true);
    }

    @Override
    public void windowClosed(WindowEvent e) {
       swingWrapper.setVisible(false);
    }

    public void setVisibility(boolean visibility) {
        swingWrapper.setVisible(visibility);
    }
}
