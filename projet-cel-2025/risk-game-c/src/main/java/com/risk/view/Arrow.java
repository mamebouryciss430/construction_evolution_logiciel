package com.risk.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;


/**
 * Able to form a arrow shape and draw it on the map
 */
public class Arrow extends Group {

    // common attributes
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private String arrowType;


    // attributes for default arrow
    private Line line;
    private Polygon triangle;


    /**
     * Ctor, create an Arrow
     * @param arrowType determines how arrow is going to be shaped
     */
    public Arrow(String arrowType) {
        this.arrowType = arrowType;

        switch(arrowType) {
            case "DEFAULT":
                Color attackColor = Color.color(0, 0, 0);

                line = new Line();
                line.setStroke(attackColor);
                line.setStrokeWidth(2);

                triangle = new Polygon();
                triangle.setStroke(attackColor);
                triangle.setStrokeWidth(2);

                getChildren().addAll(line, triangle);
                break;
            case "ATTACK":

                break;
            case "FORTIFICATION":

                break;
        }
    }


    /**
     * Dynamically update the Arrow length, rotation, etc.
     */
    private void update() {
        // calculate arrow parameters
        int countryViewHalfSize = 30;
        double degree = Math.atan((endY - startY) / (endX - startX)) / (2 * Math.PI) * 360;
        double endXIndent = 0; // used for fixed arrow
        double positiveDegree = degree < 0 ? degree * -1 : degree;
        if (0 <= positiveDegree && positiveDegree < 45) {
            endXIndent = countryViewHalfSize / cos(Math.toRadians(positiveDegree));
        } else if (45 <= positiveDegree && positiveDegree <= 90) {
            endXIndent = countryViewHalfSize / sin(Math.toRadians(positiveDegree));
        }

        double width = Math.sqrt(Math.pow((endY - startY), 2) + Math.pow((endX - startX), 2));
        if (startX > endX) degree += 180;
        Rotate rotate = new Rotate(degree, startX, startY);

        switch (arrowType) {
            case "DEFAULT":
                // clear previous drawing components' parameters
                triangle.getPoints().clear();
                line.getTransforms().clear();
                triangle.getTransforms().clear();

                // calculate new parameters

                double horizontalEndX = startX + width - endXIndent;
                double arrowHeadWidth = 10;
                double arrowHeadHeight = 6;

                // set new parameters for all components
                line.setStartX(startX);
                line.setStartY(startY);
                line.setEndX(startX + width);
                line.setEndY(startY);
                line.getTransforms().add(rotate);

                Double [] trianglePoints = new Double[] {
                        horizontalEndX - arrowHeadWidth, startY + arrowHeadHeight / 2,
                        horizontalEndX - arrowHeadWidth, startY - arrowHeadHeight / 2,
                        horizontalEndX, startY
                };
                triangle.getPoints().addAll(trianglePoints);
                triangle.getTransforms().add(rotate);
                break;
            case "ATTACK":

                break;
            case "FORTIFICATION":

                break;
        }
  }


    /**
     * Set start point for the Arrow
     * @param startX is the start X position
     * @param startY is the start Y position
     */
    public void setStart(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
    }


    /**
     * Set end point for the Arrow
     * @param endX is the end X position
     * @param endY is the end Y position
     */
    public void setEnd(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
        update();
    }
}