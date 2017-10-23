/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ---------------------------
 * NormalDistributionDemo.java
 * ---------------------------
 * (C) Copyright 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: NormalDistributionDemo.java,v 1.1 2004/05/26 13:02:03 mungady Exp $
 *
 * Changes
 * -------
 * 25-May-2004 : Version 1 (DG);
 *
 */
package TemperatureFC;

import LFuzzyLibrary.Finite;
import LFuzzyLibrary.FiniteOutputLinguistic;
import LFuzzyLibrary.GenericComposition;
import LFuzzyLibrary.HeytAFloat;
import LFuzzyLibrary.HeytAPair;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.Sum;
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
import org.jfree.ui.RefineryUtilities;

/**
 * This demo shows a normal distribution function.
 */
public class ChartOutput extends ApplicationFrame {

    /**
     * A demonstration application showing a normal distribution.
     *
     * @param title the frame title.
     */
    public ChartOutput(final String title, Float aOut, Float bOut, Integer uOut) {

        super(title);

        ModifierOutputImpl mOutput = new ModifierOutputImpl(new FiniteOutputValuesImpl(), 0, aOut, bOut, uOut, -200, 200);
        LRel<Finite, Object, Pair<Float, Float>> Lout = mOutput.getL();

        XYDataset dataset = createDataset(Lout);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Linguistic Output",
                "Setting",
                "Membership degree",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 570));
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

    private XYDataset createDataset(LRel<Finite, Object, Pair<Float, Float>> Lout) {

        List<Pair<String, XYSeries>> seriesList = new ArrayList();
        final XYSeriesCollection collection = new XYSeriesCollection();

        Set<Pair<Finite, Object>> keys = Lout.getTable().keySet();
        Iterator<Pair<Finite, Object>> itr = keys.iterator();

        while (itr.hasNext()) {
            //if(count>800)break;
            Pair<Finite, Object> key = itr.next();
            Pair<Float, Float> pairValue = Lout.getValue(key);

            String serieName = key.getFirst().toString();
            Sum a = (Sum) key.getSecond();
            if (pairValue.getFirst() > 0.0f) {
                if (a.getValue().toString().equals("AL")) {
                    //<<?this one doesnt have an X value in the chart. only Y
                    //System.out.println("chart ALERT!");
                } else {
                    boolean existent = false;
                    existent = seriesList(seriesList, serieName + "a", Integer.valueOf(a.getValue().toString()), pairValue.getFirst());

                    if (!existent) {
                        //Create new series
                        XYSeries seriesA = new XYSeries(serieName + "a");
                        seriesA.add(Integer.valueOf(a.getValue().toString()), pairValue.getFirst());
                        seriesList.add(new Pair(serieName + "a", seriesA));
                    }
                }
            }
            if (pairValue.getSecond() > 0.0f) {
                if (a.getValue().toString().equals("AL")) {
                    //<<?
                    //System.out.println("chart ALERT2!");
                } else {
                    boolean existent = false;
                    existent = seriesList(seriesList, serieName + "b", Integer.valueOf(a.getValue().toString()), pairValue.getSecond());

                    if (!existent) {
                        //Create new series
                        XYSeries seriesB = new XYSeries(serieName + "b");
                        seriesB.add(Integer.valueOf(a.getValue().toString()), pairValue.getSecond());
                        seriesList.add(new Pair(serieName + "b", seriesB));
                    }
                }
            }
        }
        //add all series to collection
        for (Pair<String, XYSeries> e : seriesList) {
            collection.addSeries(e.getSecond());
        }

        return collection;

    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */
//    public static void main(final String[] args) {
//
//        final ChartOutput demo = new ChartOutput("Normal Distribution Demo");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//
//    }
}
