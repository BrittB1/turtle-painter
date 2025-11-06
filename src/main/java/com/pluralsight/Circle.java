package com.pluralsight;

import java.awt.*;

public class Circle extends Shape {
    double radius;

    public Circle(double x, double y, int borderWidth, Turtle turtle, Color color, double radius) {
        super(x, y, borderWidth, turtle, color);
        this.radius = radius;
    }
    @Override
    public void paint() {

    }
}
