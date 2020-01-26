package com.chazle.com.gui;

/* Simple graph drawing class
Credit: Bert Huang (COMS 3137 Data Structures and Algorithms, Spring 2009)

"This class is really elementary, but lets you draw 
reasonably nice graphs/trees/diagrams. Feel free to 
improve upon it!"

Modified by Smit Rao
 */

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import javax.swing.*;

import com.chazle.com.Graph;
import com.chazle.com.Vertex;

public class GraphDraw extends JFrame {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	int width;
	int height;

	ArrayList<Node> nodes;
	ArrayList<edge> edges;

	public GraphDraw() { // Constructor
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<edge>();
		width = 80;
		height = 80;
	}

	public GraphDraw(String name) { // Construct with label
		this.setTitle(name);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<edge>();
		width = 80;
		height = 80;
	}

	class Node {
		int x, y;
		String name;

		public Node(String myName, int myX, int myY) {
			x = myX;
			y = myY;
			name = myName;
		}
	}

	class edge {
		int i, j, weight;

		public edge(int ii, int jj, int weight) {
			i = ii;
			j = jj;
			this.weight = weight;
		}
	}

	public void addNode(String name, int x, int y) {
		// add a node at pixel (x,y)
		nodes.add(new Node(name, x, y));
		this.repaint();
	}

	public void addEdge(int i, int j, int weight) {
		// add an edge between nodes i and j
		edges.add(new edge(i, j, weight));
		this.repaint();
	}

	public void paint(Graphics g) { // draw the nodes and edges
		FontMetrics f = g.getFontMetrics();
		g.setFont(new Font("Arial", Font.PLAIN, 16));

		int nodeHeight = Math.max(height, f.getHeight());

		g.setColor(Color.black);
		for (edge e : edges) {
			g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y, nodes.get(e.j).x, nodes.get(e.j).y);

			int xMax = Math.max(nodes.get(e.i).x, nodes.get(e.j).x);
			int yMax = Math.max(nodes.get(e.i).y, nodes.get(e.j).y);
			int xMin = Math.min(nodes.get(e.i).x, nodes.get(e.j).x);
			int yMin = Math.min(nodes.get(e.i).y, nodes.get(e.j).y);
			int labelX = xMin + ((xMax - xMin) / 2);
			int labelY = yMin + ((yMax - yMin) / 2);
			g.setColor(Color.WHITE);
			g.fillRect(labelX - 25, labelY - 25, 50, 50);
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(e.weight), labelX, labelY);
		}

		for (Node n : nodes) {
			int nodeWidth = Math.max(width, f.stringWidth(n.name) + width / 2);
			g.setColor(Color.white);
			g.fillOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);
			g.setColor(Color.black);
			g.drawOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);

			g.drawString(n.name, n.x - f.stringWidth(n.name) / 2, n.y + f.getHeight() / 2);
		}
	}
}

class testGraphDraw {
	// Here is some example syntax for the GraphDraw class

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
		private GraphDraw frame;

		private EdgeWeightRandomizer(Graph g, GraphDraw frame) {
			this.g = g;
			this.frame = frame;
		}

		public void randomizeEdgeWeights() {
			Random rand = new Random();

			for (Vertex v : g.getVertices()) {
				int sourceVertexId = v.getId();

				for (int connectionId : v.getConnections()) {
					int nextWeight = rand.nextInt(10) + 1;
					g.addEdge(sourceVertexId, connectionId, nextWeight); // randomize every connection weight
																			// to values between 1 - 10
					frame.addEdge(sourceVertexId, connectionId, nextWeight);
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
		GraphDraw frame = new GraphDraw("Floyd Warshall Traffic");

		frame.setSize(1000, 1000);

		frame.setVisible(true);

		Graph g = new Graph();
		g.enableDelay(10);

		frame.addNode("McMaster", 320, 160);
		frame.addNode("Wendy's", 500, 890);
		frame.addNode("Library", 890, 600);
		frame.addNode("BestBuy", 125, 400);
		frame.addNode("Home", 850, 340);

		int MCMASTER = 0;
		int WENDYS = 1;
		int LIBRARY = 2;
		int BESTBUY = 3;
		int HOME = 4;

		Vertex mcMaster = new Vertex(MCMASTER);
		Vertex wendys = new Vertex(WENDYS);
		Vertex library = new Vertex(LIBRARY);
		Vertex bestBuy = new Vertex(BESTBUY);
		Vertex home = new Vertex(HOME);

		g.addVertex(mcMaster);
		g.addVertex(wendys);
		g.addVertex(library);
		g.addVertex(bestBuy);
		g.addVertex(home);

		g.addEdge(mcMaster, library, 4);
		frame.addEdge(mcMaster.getId(), library.getId(), 4);

		g.addEdge(wendys, library, 3);
		frame.addEdge(wendys.getId(), library.getId(), 3);

		g.addEdge(wendys, home, 6);
		frame.addEdge(wendys.getId(), home.getId(), 3);

		g.addEdge(wendys, bestBuy, 2);
		frame.addEdge(wendys.getId(), bestBuy.getId(), 3);

		g.addEdge(bestBuy, library, 4);
		frame.addEdge(bestBuy.getId(), library.getId(), 3);

		g.addEdge(library, home, 1);
		frame.addEdge(library.getId(), home.getId(), 3);

		g.getShortestPathDistanceMap();

		if (g.hasDelay()) {
			EdgeWeightRandomizer randomizer = new EdgeWeightRandomizer(g, frame);
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(randomizer, g.getDelay(), g.getDelay(), TimeUnit.SECONDS);
		}

		// frame.addEdge(0, 1, 5);
		// frame.addEdge(0, 1, 4);
		// frame.addEdge(0, 2, 4);
		// frame.addEdge(2, 3, 4);
		// frame.addEdge(1, 4, 4);

	}
}