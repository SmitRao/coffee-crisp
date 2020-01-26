package com.chazle.com.gui;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Draws a simple house using the Java Swing library.
 */
public class HouseCreator {

    private double x;
    private double y;
    private int measurement;

    /**
     * Constructor.
     *
     * @param x             The desired x-coordinate.
     * @param y             The desired y-coordinate.
     * @param measurement   The size of the house.
     */
    public HouseCreator(double x, double y, int measurement) {
        this.x = x;
        this.y = y;
        this.measurement = measurement;
    }

    /**
     * Draws a house that has a roof, a door and a window.
     *
     * @param graphics  A graphics instance that is used for drawing.
     */
    public void draw(Graphics2D graphics) {
        int halfMeasurement = measurement / 2;

        // Reference points of the roof.
        Point2D.Double topRoof = new Point2D.Double(x, y - halfMeasurement);
        Point2D.Double leftBottomRoof = new Point2D.Double(x - halfMeasurement, y);
        Point2D.Double rightBottomRoof = new Point2D.Double(x + halfMeasurement, y);

        // Roof lines.
        Line2D.Double leftRoof = new Line2D.Double(topRoof, leftBottomRoof);
        Line2D.Double rightRoof = new Line2D.Double(topRoof, rightBottomRoof);

        // Creates the house itself.
        int xCoord = (int) leftBottomRoof.getX();
        int yCoord = (int) leftBottomRoof.getY();

        Rectangle house = new Rectangle(xCoord, yCoord, measurement, measurement);

        // Creating the rectangle that represents the door of the house.
        xCoord += measurement / 5;
        yCoord += halfMeasurement;

        Rectangle door = new Rectangle(xCoord, yCoord,measurement / 4, halfMeasurement);

        // Creating the rectangle that represents the window of the house.
        xCoord += measurement / 2.5;
        yCoord += measurement / 10;

        Rectangle window = new Rectangle(xCoord, yCoord, measurement / 4, measurement / 4);

        // Draws all the components.
        graphics.draw(leftRoof);
        graphics.draw(rightRoof);
        graphics.draw(house);
        graphics.draw(door);
        graphics.draw(window);
    }
}
