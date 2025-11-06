package com.pluralsight;

import java.awt.*;

public class Square extends Shape {
    double size;

    public Square(double x, double y, int borderWidth, Turtle turtle, Color color, double size) {
        super(x, y, borderWidth, turtle, color);
        this.size = size;
    }
    @Override
    public void paint() {

    }
}
