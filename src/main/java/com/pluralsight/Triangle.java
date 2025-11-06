package com.pluralsight;

import java.awt.*;

public class Triangle extends Shape {
    double side;

    public Triangle(double x, double y, int borderWidth, Turtle turtle, Color color,double side) {
        super(x, y, borderWidth, turtle, color);
        this.side = side;
    }


    @Override
    public void paint() {

    }
}
