import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Source: https://codehs.com/sandbox/apcsa/java-turtle
 *
 * A world for turtles to play inside of.
 * Usage example:
 * <pre>
 * World basic = new World();
 * World fancy = new World(640, 480, Color.YELLOW);
 *
 * Turtle t1 = new Turtle(basic);
 * Turtle t2 = new Turtle(fancy);
 *
 * t1.forward(100);
 * basic.erase();
 * t1.turnLeft(90);
 * t1.forward(100);
 *
 * t2.forward(100);
 * t2.turnLeft(90);
 * t2.forward(100);
 *
 * basic.saveAs("basicWorld.png");
 * fancy.saveAs("fancyWorld.png");
 * </pre>
 *
 *
 * @author Luther Tychonievich. Released to the public domain.
 */

// JFrame - a Swing class for creating windows. World is a JFrame w/ added features
public class World extends JFrame {

    /// version number based on date of creation. Used for saving/loading objects. Required by JFrame
    private static final long serialVersionUID = 20130902L;

    // the 'paper'
    private BufferedImage overlay, ground, back, front;

    // the 'paint brushes' used to draw on each BufferedImage
    private Graphics2D og, gg, bg, fg;

    // dynamic list. stores all the turtles in the world
    private ArrayList<Turtle> turtles;

    // immutable values that store half the width/height (center point)
    public final int centerX;
    public final int centerY;


    /**
     * Creates a new World for Turtles to play in.
     * Constructor chaining - calling one constructor from another constructor.
     * this () - call from within same class | super() - call from within base class
     * default constructor (World()) calls another constructor in the same class
     * first public World - another constructor that adds default white background
     * second public World - Calls superclass/parent JFrame constructor. Sets window title
     */
    public World() {
        this(600, 600);
    }
    public World(int width, int height) {
        this(width, height, Color.WHITE);
    }
    public World(int width, int height, Color backgroundColor) {
        super("Turtle World");

        // calculates center point
        this.centerX = width/2;
        this.centerY = height/2;

        /**
         * A - Alpha (transparency)
         * R - Red
         * G - Green
         * B - Blue
         * Each pixel stores these 4 values in an ARGB format
         */
        this.overlay = new BufferedImage(2*this.centerX, 2*this.centerY, BufferedImage.TYPE_INT_ARGB);
        this.ground = new BufferedImage(2*this.centerX, 2*this.centerY, BufferedImage.TYPE_INT_ARGB);
        this.back =  new BufferedImage(2*this.centerX, 2*this.centerY, BufferedImage.TYPE_INT_ARGB);
        this.front = new BufferedImage(2*this.centerX, 2*this.centerY, BufferedImage.TYPE_INT_ARGB);

        // Type casting - Casting getGraphics() to Graphics2D, the moe powerful version
        this.og = (Graphics2D)this.overlay.getGraphics();
        this.gg = (Graphics2D)this.ground.getGraphics();
        this.bg = (Graphics2D)this.back.getGraphics();
        this.fg = (Graphics2D)this.front.getGraphics();
        og.setBackground(new Color(0,0,0,0));
        gg.setBackground(backgroundColor);

        Graphics2D[] gs = {og, gg};

        /**
         * Improves drawing quality
         * Antialiasing - smooths jagged edges
         * Alpha interpolation - transparency blending
         * Rendering quality - higher quality rendering (slower)
         */
        for(Graphics2D g : gs) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        }
        /**
         * this.front - the BufferedImage
         * new ImageIcon(this.front) - wraps the image so it can be displayed
         * new JLabel(...) - makes a label component to hold icon
         * setContentPane (...) - sets the label as the window's content
         * JFrame doesn't directly show images, so it needs to be wrapped
         */
        this.setContentPane(new JLabel(new ImageIcon(this.front)));

        // pack() - sizes windows to fit its contents
        this.pack();

        // Closing - when 'X' button is clicked, window closes + cleans up its resources (DISPOSE)
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /**
         * Anonymous inner class + interface implementation - AIC is used to implement an interface.
         * Provides concise way to define + instantiate a class that implements the interface w/o explicitly naming the class
         * 1. Instantiate interface - use 'new' keyword w/ interface name
         * 2. Provide implementation - After the interface name and (), add {}. Inside braces,
         * add implementation for all abstract methods defined in interface.
         *
         */
        this.addKeyListener(new java.awt.event.KeyListener() {
            @Override public void keyPressed(KeyEvent arg0) { }
            @Override public void keyReleased(KeyEvent arg0) { }
            @Override public void keyTyped(KeyEvent arg0) { World.this.dispose(); }
        });

        this.clearOverlay();
        this.erase();

        this.repaint();
        this.setVisible(true);

        this.turtles = new ArrayList<Turtle>();
    }

    /**
     * Erases all existing paths
     */
    public void erase() {
        this.gg.clearRect(0, 0, this.ground.getWidth(), this.ground.getHeight());
    }
    /**
     * Erases all existing paths
     */
    private void clearOverlay() {
        this.og.clearRect(0, 0, this.overlay.getWidth(), this.overlay.getHeight());
    }



    /**
     * Should only called by the Turtle class constructor
     */
    void addTurtle(Turtle t) {
        this.turtles.add(t);
        this.turtleMoved();
    }
    /**
     * Should only called by Turtle class methods
     */
    void drawLine(Point2D p1, Point2D p2, double width, Color color) {
        // draw the line
        this.gg.setColor(color);
        this.gg.setStroke(new BasicStroke((float)width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        Line2D.Double line = new Line2D.Double(p1, p2);
        this.gg.draw(line);

        // show the drawn lines
        this.blit();
    }

    private void blit() {
        this.bg.drawImage(this.ground,0,0, null);
        this.bg.drawImage(this.overlay, 0,0, null);
        this.fg.drawImage(this.back, 0,0, this);
        this.repaint();
    }

    /**
     * Should only called by Turtle class methods
     */
    void drawLine(Point2D p1, double nx, double ny, double width, Color color) {
        this.drawLine(p1,  new Point2D.Double(nx,ny), width, color);
    }
    /**
     * Should only called by Turtle class methods
     */
    void turtleMoved() {
        // show the drawn lines
        this.clearOverlay();
        // add the turtles over top
        for(Turtle t : this.turtles) {
            t._how_world_draw_turtles(this.og);
        }
        // force the OS to show what's shown
        this.blit();
    }

    /**
     * Saves the current image to the specified file
     *
     * @param filename The name of the file to write
     * @throws IllegalArgumentException if any parameter is null or if the filename is not an image filename
     */
    public void saveAs(String filename) {
        try {
            int dot = filename.lastIndexOf('.');
            if (dot < 0 || dot == filename.length()-1) {
                throw new IllegalArgumentException("The filename must end in a valid image extension, like .png or .jpg");
            }
            String ext = filename.substring(dot+1).toLowerCase();
            File f = new File(filename);
            ImageIO.write(this.front, ext, f);
        } catch(Throwable t) {
            System.err.println("Error saving file: " + t.getMessage());
        }
    }

    /**
     * To be used by Turtle class only
     * @param img the Image to draw
     * @param placement the Affine Transform to use in drawing it
     */
    void drawImage(Image img, AffineTransform placement) {
        this.gg.drawImage(img, placement, this);
        this.blit();
    }

}
