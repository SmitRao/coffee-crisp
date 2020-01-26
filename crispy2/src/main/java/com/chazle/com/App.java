package com.chazle.com;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Experiments with graph stuff.
 *
 */
public class App {

    public static void p(String s) {
        System.out.println(s);
    }

    public static void p(int s) {
        System.out.println(s);
    }

    public static void p(Object s) {
        System.out.println(s);
    }

    private static class EdgeWeightRandomizer implements Runnable {
        private Graph g;

        private EdgeWeightRandomizer(Graph g) {
            this.g = g;
        }

        public void randomizeEdgeWeights() {
            Random rand = new Random();

            for (Vertex v : g.getVertices()) {
                int sourceVertexId = v.getId();

                for (int connectionId : v.getConnections()) {
                    g.addEdge(sourceVertexId, connectionId, rand.nextInt(50) + 1); // randomize every connection weight
                                                                                   // to values between 1 - 50
                }
            }

            g.getShortestPathDistanceMap();
        }

        @Override
        public void run() {
            randomizeEdgeWeights();
        }

    }

    public static void main(String[] args) {

        Graph g = new Graph();
        g.enableDelay(10);

        Vertex v0 = new Vertex(0);
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);

        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(v0, v2, 4);
        g.addEdge(v1, v2, 3);
        g.addEdge(v1, v4, 6);
        g.addEdge(v1, v3, 2);
        g.addEdge(v3, v2, 4);
        g.addEdge(v2, v4, 1);

        g.getShortestPathDistanceMap();

        if (g.hasDelay()) {
            EdgeWeightRandomizer randomizer = new EdgeWeightRandomizer(g);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(randomizer, g.getDelay(), g.getDelay(), TimeUnit.SECONDS);
        }

    }
}
