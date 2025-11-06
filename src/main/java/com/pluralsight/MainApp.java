package com.pluralsight;// Abstract Window Toolkit - Java's library for making GUIs
import java.awt.*;
import java.util.Scanner;

public class MainApp {

    static Scanner keyboard = new Scanner(System.in);
    static  World world;
    static Turtle turtle;

    public static void main(String[] args) {

        System.out.println("Please enter canvas height (px): ");
        int canvasHeight = keyboard.nextInt();

        System.out.println("Please enter canvas width (px): ");
        int canvasWidth = keyboard.nextInt();

        //Creates com.pluralsight.World object (canvas) and sets height + width at 200 pixels
        world = new World(canvasWidth,canvasHeight);

        // Creates com.pluralsight.Turtle object and sets starting position
        turtle = new Turtle(world,0, 0);
        turtle.penUp();

        boolean running = true;
        while (running) {
            System.out.println("---------------------------");
            System.out.println(          "\n MENU "          );
            System.out.println("---------------------------");
            System.out.println("1. Add Shape");
            System.out.println("2. Save image");
            System.out.println("0. EXIT");
            System.out.print("Enter your choice: ");

            int choice = keyboard.nextInt();
            keyboard.nextLine();

            switch (choice){
                case 1:
                    addShape();
                    break;
                case 2:
                    //TODO: make saveImage();
                    break;
                case 0:
                    System.out.println("See ya later");
                    running = false;
                    break;
                default:
                    System.out.println("OOPS! Invalid option");

            }
        }

    }

    public static void addShape () {
        System.out.println("\n--- Add Shape ---");
        System.out.println("Which shape?");
        System.out.println("1. Square");
        System.out.println("2. Circle");
        System.out.println("3. Triangle");
        System.out.print("Enter choice: ");

        int shapeChoice = keyboard.nextInt();
        keyboard.nextLine();

        System.out.println("Enter border width: ");
        System.out.print("Width: ");
        int borderWidth = keyboard.nextInt();
        keyboard.nextLine();

        System.out.println("Pick a border color by name (Red \uD83D\uDD34 | Blue \uD83D\uDD35 | Green \uD83D\uDFE2 | Yellow \uD83D\uDFE1 | Black ⚫) : ");
        System.out.print("Color: ");
        String borderColor = keyboard.nextLine();
        Color color = getColorFromString(borderColor);

        System.out.print("Enter x coordinate: ");
        System.out.print("X: ");
        double x = keyboard.nextDouble();

        System.out.print("Enter y coordinate: ");
        System.out.print("Y: ");
        double y = keyboard.nextDouble();
        keyboard.nextLine();

        Shape shape = null;

        switch (shapeChoice){
            case 1:
                System.out.println("Enter side length: ");
                double size = keyboard.nextDouble();
                keyboard.nextLine();
                shape = new Square(x,y,borderWidth,turtle,color,size);
                break;
            case 2:
                System.out.println("Enter radius: ");
                double radius = keyboard.nextDouble();
                keyboard.nextLine();
                shape = new Circle(x,y,borderWidth,turtle,color,radius);
                break;
            case 3:
                System.out.println("Enter side length: ");
                double side = keyboard.nextDouble();
                keyboard.nextLine();
                shape = new Triangle(x,y,borderWidth,turtle,color,side);
                break;

            default:
                System.out.println("OOPS! Invalid shape choice");
                return;
        }
        shape.paint();
        System.out.println("Shape added to canvas!");
    }
    private static Color getColorFromString(String borderColor) {

        return switch (borderColor.toLowerCase()) {
            case "red" -> Color.RED;
            case "blue" -> Color.BLUE;
            case "green" -> Color.GREEN;
            case "yellow" -> Color.YELLOW;
            case "black" -> Color.BLACK;
            default -> {
                System.out.println("Invalid color choice. Using black");
                yield Color.BLACK;
            }
        };
    }

}

