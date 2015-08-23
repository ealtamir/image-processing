package ar.com.itba;


import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.SwingWrapper;

import javax.swing.*;

public class Histogram  {


    public static void main(String[] args) {
        // Create Chart
        Chart chart = new ChartBuilder().chartType(StyleManager.ChartType.Bar).width(800).height(600).title("Score Histogram").xAxisTitle("Score").yAxisTitle("Number").build();
        chart.addSeries("test 1", new double[] { 0, 1, 2, 3, 4 }, new double[] { 4, 5, 9, 6, 5 });

    // Customize Chart
        chart.getStyleManager().setLegendPosition(StyleManager.LegendPosition.InsideNW);

        JFrame wrapper = new SwingWrapper(chart).displayChart();
    }

}
