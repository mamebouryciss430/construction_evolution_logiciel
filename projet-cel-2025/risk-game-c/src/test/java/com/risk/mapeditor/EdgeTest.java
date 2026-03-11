package com.risk.mapeditor;

import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeTest {

    Edge e;
    Country c1, c2;

    static {
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        View.continents = FXCollections.observableArrayList();;
        View.continents.add("TestContinent");

        c1 = new Country(40, 60);
        c2 = new Country(30, 90);

        e = new Edge(c1, c2);
    }

    @Test
    void testGetP1() {
        assertEquals(c1, e.getP1());
    }

    @Test
    void testGetP2() {
        assertEquals(c2, e.getP2());
    }

    @Test
    void testStartCoordinates() {
        assertEquals(c1.getLayoutX() + 60, e.getStartX());
        assertEquals(c1.getLayoutY() + 40, e.getStartY());
    }

    @Test
    void testEndCoordinates() {
        assertEquals(c2.getLayoutX() + 60, e.getEndX());
        assertEquals(c2.getLayoutY() + 40, e.getEndY());
    }
}
