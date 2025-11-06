package com.pluralsight;

import java.awt.*;

public abstract class Shape {

    double x, y;
    int borderWidth;
    Turtle turtle;
    Color color;

    public Shape(double x, double y, int borderWidth, Turtle turtle, Color color) {
        this.x = x;
        this.y = y;
        this.borderWidth = borderWidth;
        this.turtle = turtle;
        this.color = color;
    }
    public abstract void paint();
}
