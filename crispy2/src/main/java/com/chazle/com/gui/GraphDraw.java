package com.chazle.com.gui;

/* Simple graph drawing class
Credit: Bert Huang (COMS 3137 Data Structures and Algorithms, Spring 2009)

"This class is really elementary, but lets you draw 
reasonably nice graphs/trees/diagrams. Feel free to 
improve upon it!"
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GraphDraw extends JFrame {
	int width;
	int height;

	ArrayList<Node> nodes;
	ArrayList<edge> edges;

	public GraphDraw() { // Constructor
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<edge>();
		width = 50;
		height = 50;
	}

	public GraphDraw(String name) { // Construct with label
		this.setTitle(name);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<edge>();
		width = 50;
		height = 50;
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
		g.setFont(new Font("Arial", Font.PLAIN, 14)); 

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
	public static void main(String[] args) {
		GraphDraw frame = new GraphDraw("Floyd Warshall Traffic");

		frame.setSize(1000, 1000);

		frame.setVisible(true);

		frame.addNode("a", 50, 50);
		frame.addNode("b", 100, 100);
		frame.addNode("b", 120, 103);
		frame.addNode("b", 49, 88);

		frame.addNode("longNode", 200, 200);
		frame.addEdge(0, 1, 4);
		frame.addEdge(0, 2, 4);
		frame.addEdge(2, 3, 4);
		frame.addEdge(1, 4, 4);

	}
}