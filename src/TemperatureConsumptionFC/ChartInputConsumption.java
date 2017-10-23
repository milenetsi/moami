/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.Finite;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class ChartInputConsumption extends ApplicationFrame {

    /**
     * A demonstration application showing a normal distribution.
     *
     * @param title the frame title.
     */
    public ChartInputConsumption(final String title, Settings s) {

        super(title);
        ModifierInputImplC mInput = new ModifierInputImplC(new FiniteInputValuesImplC(), s.getA1Input2(), s.getA2Input2(), s.getB1Input2(), s.getB2Input2(), s.getuInput().getSecond(),
                s.getHighestInputValue().getSecond(), s.getLowestInputValue().getSecond(), s.getOptimalValueA().getSecond(), s.getOptimalValueB().getSecond());
        LRel<Finite, Object, Pair<Float, Float>> Lin = mInput.getL();

        XYDataset dataset = createDataset(Lin);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Linguistic Input Consumption",
                "Consumption",
                "Membership degree",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(
                new java.awt.Dimension(800, 570));
        setContentPane(chartPanel);

    }

    private boolean seriesList(List<Pair<String, XYSeries>> seriesList, String name, Integer val, Float degree) {

        for (Pair<String, XYSeries> e : seriesList) {

            if (e.getFirst().equals(name)) {
                e.getSecond().add(val, degree);
                return true;
            }
        }
        return false;
    }

    private XYDataset createDataset(LRel<Finite, Object, Pair<Float, Float>> Lin) {

        List<Pair<String, XYSeries>> seriesList = new ArrayList();
        final XYSeriesCollection collection = new XYSeriesCollection();

        Set<Pair<Finite, Object>> keys = Lin.getTable().keySet();
        Iterator<Pair<Finite, Object>> itr = keys.iterator();

        while (itr.hasNext()) {

            Pair<Finite, Object> key = itr.next();
            Pair<Float, Float> pairValue = Lin.getValue(key);

            String serieName = key.getFirst().toString();
            Integer a = (Integer) key.getSecond();
            if (pairValue.getFirst() > 0.0f) {
                boolean existent = false;
                existent = seriesList(seriesList, serieName + "a", a, pairValue.getFirst());

                if (!existent) {
                    //Create new series
                    XYSeries seriesA = new XYSeries(serieName + "a");
                    seriesA.add(a, pairValue.getFirst());
                    seriesList.add(new Pair(serieName + "a", seriesA));
                }
            }
            if (pairValue.getSecond() > 0.0f) {
                boolean existent = false;
                existent = seriesList(seriesList, serieName + "b", a, pairValue.getSecond());

                if (!existent) {
                    //Create new series
                    XYSeries seriesB = new XYSeries(serieName + "b");
                    seriesB.add(a, pairValue.getSecond());
                    seriesList.add(new Pair(serieName + "b", seriesB));
                }
            }

        }

        //add all series to collection
        for (Pair<String, XYSeries> e : seriesList) {
            collection.addSeries(e.getSecond());
        }

        return collection;

    }
}
