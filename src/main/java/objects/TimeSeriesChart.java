package objects;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TimeSeriesChart extends JFrame {

    private static final long serialVersionUID = 1L;

    public TimeSeriesChart(String title, ArrayList<Integer> neutralList, ArrayList<Integer> aggressiveList) {
        super(title);
        // Create dataset
        XYDataset dataset = createDataset(neutralList, aggressiveList);
        // Create chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Number of persons", // Chart
                "Date", // X-Axis Label
                "Number", // Y-Axis Label
                dataset);

        //Changes background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,228,196));

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(ArrayList<Integer> neutralList, ArrayList<Integer> aggressiveList) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        TimeSeries seriesNeutral = new TimeSeries("Neutral");
        Day k = new Day(1, 1, 2000);
        for (Integer integer : neutralList) {
            seriesNeutral.add(k, integer);
            k = (Day) k.next();
        }
        dataset.addSeries(seriesNeutral);

        TimeSeries seriesAggressive = new TimeSeries("Aggressive");
        Day h = new Day(1, 1, 2000);
        for (Integer integer : aggressiveList) {
            seriesAggressive.add(h, integer);
            h = (Day) h.next();
        }
        dataset.addSeries(seriesAggressive);

        return dataset;
    }
}