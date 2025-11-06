package com.pluralsight;// Abstract Window Toolkit - Java's library for making GUIs
import java.awt.*;

public class MainApp
{
    public static void main(String[] args)
    {
        // This starter code to get you familiar with how
        // the TurtleLogo application works

        //Creates com.pluralsight.World object (canvas) and sets height + width at 200 pixels
        World world = new World(200, 200);

        // Creates com.pluralsight.Turtle object and sets starting position
        Turtle turtle = new Turtle(world,-100, -100);

        // storing dimensions for diagonal calculation
        int width = 200;
        int height = 200;

        // calculate the hypotenuse (diagonal)
        // a2 + b2 = c2
/* pythagorean theorem: calculates diagonal distance across the canvas
/* Math.pow(width,2) - width² | Math.sqrt - square root | c² = a² + b² → c = √(a² + b²)
 */
        double widthSquared = Math.pow(width, 2);
        double heightSquared = Math.pow(height, 2);
        double hypotenuse = Math.sqrt(widthSquared + heightSquared);


        turtle.setPenWidth(3);
        turtle.setColor(Color.GREEN);

        turtle.turnRight(45);
        turtle.forward(hypotenuse);

        // pen.Up - lifts been so it doesn't draw
        turtle.penUp();
        turtle.goTo(100, 100);
        turtle.turnRight(90);

        turtle.penDown();
        turtle.forward(hypotenuse);
    }
}
