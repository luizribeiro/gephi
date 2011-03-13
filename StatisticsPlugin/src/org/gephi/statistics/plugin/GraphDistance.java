/*
Copyright 2008-2011 Gephi
Authors : Patick J. McSweeney <pjmcswee@syr.edu>, Sebastien Heymann <seb@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gephi.statistics.plugin;

import java.io.IOException;
import java.util.HashMap;
import org.gephi.statistics.spi.Statistics;
import org.gephi.graph.api.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeRow;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.utils.TempDirUtils;
import org.gephi.utils.TempDirUtils.TempDir;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 *
 * @author pjmcswee
 */
public class GraphDistance implements Statistics, LongTask {

    public static final String BETWEENNESS = "betweenesscentrality";
    public static final String CLOSENESS = "closnesscentrality";
    public static final String ECCENTRICITY = "eccentricity";
    /** */
    private double[] betweenness;
    /** */
    private double[] closeness;
    /** */
    private double[] eccentricity;
    /** */
    private int diameter;
    private int radius;
    /** */
    private double avgDist;
    /** */
    private int N;
    /** */
    private boolean isDirected;
    /** */
    private ProgressTicket progress;
    /** */
    private boolean isCanceled;
    private int shortestPaths;
    private boolean isNormalized;

    public GraphDistance() {
        GraphController graphController = Lookup.getDefault().lookup(GraphController.class);
        if (graphController != null && graphController.getModel() != null) {
            isDirected = graphController.getModel().isDirected();
        }
    }

    public double getPathLength() {
        return avgDist;
    }

    /**
     * 
     * @return
     */
    public double getDiameter() {
        return diameter;
    }

    /**
     *
     * @param graphModel
     */
    public void execute(GraphModel graphModel, AttributeModel attributeModel) {
        HierarchicalGraph graph = null;
        if (isDirected) {
            graph = graphModel.getHierarchicalDirectedGraphVisible();
        } else {
            graph = graphModel.getHierarchicalUndirectedGraphVisible();
        }
        execute(graph, attributeModel);
    }

    public void execute(HierarchicalGraph hgraph, AttributeModel attributeModel) {
        isCanceled = false;
        AttributeTable nodeTable = attributeModel.getNodeTable();
        AttributeColumn eccentricityCol = nodeTable.getColumn(ECCENTRICITY);
        AttributeColumn closenessCol = nodeTable.getColumn(CLOSENESS);
        AttributeColumn betweenessCol = nodeTable.getColumn(BETWEENNESS);
        if (eccentricityCol == null) {
            eccentricityCol = nodeTable.addColumn(ECCENTRICITY, "Eccentricity", AttributeType.DOUBLE, AttributeOrigin.COMPUTED, new Double(0));
        }
        if (closenessCol == null) {
            closenessCol = nodeTable.addColumn(CLOSENESS, "Closeness Centrality", AttributeType.DOUBLE, AttributeOrigin.COMPUTED, new Double(0));
        }
        if (betweenessCol == null) {
            betweenessCol = nodeTable.addColumn(BETWEENNESS, "Betweenness Centrality", AttributeType.DOUBLE, AttributeOrigin.COMPUTED, new Double(0));
        }

        hgraph.readLock();

        N = hgraph.getNodeCount();

        betweenness = new double[N];
        eccentricity = new double[N];
        closeness = new double[N];
        diameter = 0;
        avgDist = 0;
        shortestPaths = 0;
        radius = Integer.MAX_VALUE;
        HashMap<Node, Integer> indicies = new HashMap<Node, Integer>();
        int index = 0;
        for (Node s : hgraph.getNodes()) {
            indicies.put(s, index);
            index++;
        }

        Progress.start(progress, hgraph.getNodeCount());
        int count = 0;
        for (Node s : hgraph.getNodes()) {
            Stack<Node> S = new Stack<Node>();

            LinkedList<Node>[] P = new LinkedList[N];
            double[] theta = new double[N];
            int[] d = new int[N];
            for (int j = 0; j < N; j++) {
                P[j] = new LinkedList<Node>();
                theta[j] = 0;
                d[j] = -1;
            }

            int s_index = indicies.get(s);

            theta[s_index] = 1;
            d[s_index] = 0;

            LinkedList<Node> Q = new LinkedList<Node>();
            Q.addLast(s);
            while (!Q.isEmpty()) {
                Node v = Q.removeFirst();
                S.push(v);
                int v_index = indicies.get(v);

                EdgeIterable edgeIter = null;
                if (isDirected) {
                    edgeIter = ((HierarchicalDirectedGraph) hgraph).getOutEdgesAndMetaOutEdges(v);
                } else {
                    edgeIter = hgraph.getEdgesAndMetaEdges(v);
                }

                for (Edge edge : edgeIter) {
                    Node reachable = hgraph.getOpposite(v, edge);

                    int r_index = indicies.get(reachable);
                    if (d[r_index] < 0) {
                        Q.addLast(reachable);
                        d[r_index] = d[v_index] + 1;
                    }
                    if (d[r_index] == (d[v_index] + 1)) {
                        theta[r_index] = theta[r_index] + theta[v_index];
                        P[r_index].addLast(v);
                    }
                }
            }
            double reachable = 0;
            for (int i = 0; i < N; i++) {
                if (d[i] > 0) {
                    avgDist += d[i];
                    eccentricity[s_index] = (int) Math.max(eccentricity[s_index], d[i]);
                    closeness[s_index] += d[i];
                    diameter = Math.max(diameter, d[i]);
                    reachable++;
                }
            }

            radius = (int) Math.min(eccentricity[s_index], radius);

            if (reachable != 0) {
                closeness[s_index] /= reachable;
            }

            shortestPaths += reachable;

            double[] delta = new double[N];
            while (!S.empty()) {
                Node w = S.pop();
                int w_index = indicies.get(w);
                ListIterator<Node> iter1 = P[w_index].listIterator();
                while (iter1.hasNext()) {
                    Node u = iter1.next();
                    int u_index = indicies.get(u);
                    delta[u_index] += (theta[u_index] / theta[w_index]) * (1 + delta[w_index]);
                }
                if (w != s) {
                    betweenness[w_index] += delta[w_index];
                }
            }
            count++;
            if (isCanceled) {
                hgraph.readUnlockAll();
                return;
            }
            Progress.progress(progress, count);
        }

        avgDist /= shortestPaths;//mN * (mN - 1.0f);

        for (Node s : hgraph.getNodes()) {
            AttributeRow row = (AttributeRow) s.getNodeData().getAttributes();
            int s_index = indicies.get(s);

            if (!isDirected) {
                betweenness[s_index] /= 2;
            }
            if (isNormalized) {
                closeness[s_index] = (closeness[s_index] == 0) ? 0 : 1.0 / closeness[s_index];
                betweenness[s_index] /= isDirected ? (N - 1) * (N - 2) : (N - 1) * (N - 2) / 2;
            }
            row.setValue(eccentricityCol, eccentricity[s_index]);
            row.setValue(closenessCol, closeness[s_index]);
            row.setValue(betweenessCol, betweenness[s_index]);
        }
        hgraph.readUnlock();
    }

    public void setNormalized(boolean isNormalized) {
        this.isNormalized = isNormalized;
    }

    public boolean isNormalized() {
        return isNormalized;
    }

    public void setDirected(boolean isDirected) {
        this.isDirected = isDirected;
    }

    public boolean isDirected() {
        return isDirected;
    }

    private String createImageFile(TempDir tempDir, double[] pVals, String pName, String pX, String pY) {
        //distribution of values
        Map<Double, Integer> dist = new HashMap<Double, Integer>();
        for (int i = 0; i < N; i++) {
            Double d = pVals[i];
            if (dist.containsKey(d)) {
                Integer v = dist.get(d);
                dist.put(d, v + 1);
            } else {
                dist.put(d, 1);
            }
        }

        //Distribution series
        XYSeries dSeries = ChartUtils.createXYSeries(dist, pName);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(dSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                pName,
                pX,
                pY,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        ChartUtils.decorateChart(chart);
        ChartUtils.scaleChart(chart, dSeries, isNormalized);
        return ChartUtils.renderChart(chart, pName + ".png");
    }

    /**
     *
     * @return
     */
    public String getReport() {
        String htmlIMG1 = "";
        String htmlIMG2 = "";
        String htmlIMG3 = "";
        try {
            TempDir tempDir = TempDirUtils.createTempDir();
            htmlIMG1 = createImageFile(tempDir, betweenness, "Betweenness Centrality Distribution", "Value", "Count");
            htmlIMG2 = createImageFile(tempDir, closeness, "Closeness Centrality Distribution", "Value", "Count");
            htmlIMG3 = createImageFile(tempDir, eccentricity, "Eccentricity Distribution", "Value", "Count");
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        String report = "<HTML> <BODY> <h1>Graph Distance  Report </h1> "
                + "<hr>"
                + "<br>"
                + "<h2> Parameters: </h2>"
                + "Network Interpretation:  " + (isDirected ? "directed" : "undirected") + "<br />"
                + "<br /> <h2> Results: </h2>"
                + "Diameter: " + diameter + "<br />"
                + "Radius: " + radius + "<br />"
                + "Average Path length: " + avgDist + "<br />"
                + "Number of shortest paths: " + shortestPaths + "<br /><br />"
                + htmlIMG1 + "<br /><br />"
                + htmlIMG2 + "<br /><br />"
                + htmlIMG3
                + "</BODY></HTML>";

        return report;
    }

    /**
     * 
     * @return
     */
    public boolean cancel() {
        this.isCanceled = true;
        return true;
    }

    /**
     *
     * @param progressTicket
     */
    public void setProgressTicket(ProgressTicket progressTicket) {
        this.progress = progressTicket;
    }
}
