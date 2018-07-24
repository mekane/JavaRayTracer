package org.martykane;

/**
 * The screens draw a visual representation of everything in the World.
 * It keeps an internal list of all the entities it is supposed to draw.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Screen extends JComponent implements KeyListener {
    private boolean draw = false;
    private boolean debug = false;

    //Global Scene Data
    private Vector<Object3d> objectList;

    private Vector<Light> lightList;
    private float ambientLight;

    private Camera cam;

    /*test*/ double thresh = 0.000001;

    //some contant colors
    private final Color sky_blue = new Color(10, 10, 80);
    private final Color green_1 = new Color(10, 90, 10);
    private final Color green_2 = Color.green.darker();


    //Constructors
    public Screen() {
        this(640, 480);
    }

    public Screen(int width, int height) {
        super();

        //Setup Scene Variables
        this.objectList = new Vector<Object3d>();

        Quad3d q = new Quad3d(3d, 0d, -2d, -2d, 0d, -2d, -2d, 0d, 2d, 3d, 0d, 2d);
        q.setMaterial(Color.blue, 0.95);
        q.setName("blue quad");
        objectList.add(q);

        q = new Quad3d(2d, 0d, 1d, 1d, 0d, 1d, 1d, 1d, 1d, 2d, 1d, 1d);
        q.setMaterial(Color.blue, 0.85);
        q.setName("blue quad");
        objectList.add(q);
        System.out.println(q.getName() + " " + q.getNormal());


        //lights
        this.lightList = new Vector<Light>();
        lightList.add(new Light(0d, 10d, -4d, 0.45));
        lightList.add(new Light(0d, 10d, 6d, 0.45));

        //lightList.add( new Light( 0d, 10d, -10d, 0.3 ) );
        //lightList.add( new Light( -5d, 10d, 0d, 0.2 ) );
        this.ambientLight = 0.1f;

        //Defining the camera viewing the Scene
        cam = new Camera(new Point3d(-8.01d, 6d, 0d),
                new Point3d(0d, 1d, 0d));
        cam.setViewDist(600);


        //Window Setup
        this.setMinimumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        this.setVisible(true);
    }


    /**
     * Custom Painting - draw everything in the world
     */
    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();

        if (!draw) {
            System.out.println("Waiting, Press [P] to draw...");
            return;
        }

        //clear screen
        g.setColor(Color.black);
        g.fillRect(0, 0, w, h);

        Point3d eye = cam.getPosition();

        if (debug) {
            System.out.println("\nTesting Camera\n");
            System.out.println("position: " + cam.getPosition());
            System.out.println("look at: " + cam.getLookPoint());
            System.out.println("up vec: " + cam.getUpVector());
            System.out.println("N = " + cam.getN());
            System.out.println("U = " + cam.getU());
            System.out.println("V = " + cam.getV());
        }
        System.out.println("Starting Trace: [" + w + " x " + h + "]");

        Color background = new Color(ambientLight, ambientLight, ambientLight);
        //Color background = Color.black;

        for (int c = 0; c < w; c++) //x
        {
            for (int r = 0; r < h; r++) //y
            {
                Ray3d next = cam.getPixel(c, r, w, h); //make next ray
                Intersection best = new Intersection(9999, null, false, 0, null, null);

                for (Object3d o : objectList) //find hits for ray with each object
                {
                    Intersection[] hit = o.intersectWith(eye, next);
                    if (hit == null)
                        continue;
                    if (hit[0].getHitTime() < best.getHitTime())
                        best = hit[0];
                }

                //Now use the best Intersection to shade pixel
                if (best.getHitObject() == null) {
                    g.setColor(new Color(4, 8, 16));
                    g.setColor(Color.black);
                } else //shade according to object
                {
                    Object3d obj = best.getHitObject();
                    Color hue = obj.getColor();
                    Color nhue = background;
                    Point3d P = best.getHitPoint();
                    Ray3d N = best.getHitNormal().normalize();

                    //Adjust hit point for shadow check - move slightly back towards eye
                    Point3d start = P.minus(next.times(thresh));

                    /** TEST **/
                    boolean test = false;
                    if (c == 320 && r > 300 && r < 305) {
                        test = true;
                        System.out.println("\ny (" + c + "," + r + ")");
                        System.out.println("Hit Point = (" + P + ")");
                        System.out.println("Start Pt  = (" + start + ")");
                    }
                    /** TEST **/

                    for (Light L : lightList) //find contribution from each light
                    {
                        if (test)
                            System.out.println("checking light at " + L.getPosition());
                        //Check for shadows by sending a ray to each light and checking for
                        //intersections with that ray for each object. If the ray hits an
                        //object, then that object is blocking this light, so we skip it
                        Ray3d toLight = L.getPosition().minus(start);
                        if (isInShadow(start, toLight, test))
                            continue;

                        Ray3d S = L.getPosition().minus(P).normalize();

                        double val = Math.max(S.dot(N), 0);
                        double dif = best.getHitObject().getDiffuse();

                        double id = val * dif * L.getBrightness() + ambientLight;

                        nhue = addColors(nhue, mulColor(hue, id));
                    }

                    g.setColor(nhue);
                    //if ( test )
                    //g.setColor(Color.yellow);
                }

                g.drawLine(w - c, h - r, w - c, h - r);

            }//C
        }//R

    /**/
        System.out.println("Trace Done");

    }//end - paint


    /**
     * Check each object to see if it blocks the given ray. If any of the objects
     * does block the ray (intersect with the object), then the point in question
     * must be in shadow.
     */
    private boolean isInShadow(Point3d point, Ray3d ray, boolean test) {
        for (Object3d o : objectList)//check each object for shadow
            if (o.blocks(point, ray))
                return true;
        return false;
    }


    /**
     * Private utility method to add two colors together
     *
     */
    private Color addColors(Color c1, Color c2) {
        return new Color((int) Math.min((c1.getRed() + c2.getRed()), 255),
                (int) Math.min((c1.getGreen() + c2.getGreen()), 255),
                (int) Math.min((c1.getBlue() + c2.getBlue()), 255));
    }

    /**
     * Private utility method to multiple a color by a scalar
     *
     */
    private Color mulColor(Color c1, double num) {
        return new Color((int) Math.min((c1.getRed() * num), 255),
                (int) Math.min((c1.getGreen() * num), 255),
                (int) Math.min((c1.getBlue() * num), 255));
    }

    /**
     * Private utility method to multiple a color by a scalar
     *
     */
    private Color mulColor3(Color c1, double nr, double ng, double nb) {
        return new Color((int) Math.min((c1.getRed() * nr), 255),
                (int) Math.min((c1.getGreen() * ng), 255),
                (int) Math.min((c1.getBlue() * nb), 255));
    }


    //Keyboard Listeners
    public void keyReleased(KeyEvent ke) {
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyChar() == 'p') {
            draw = !draw;
            System.out.println("Draw: " + draw);
            if (draw)
                repaint();
        } else if (ke.getKeyChar() == 'f') {
            cam.setViewDist(cam.getViewDist() + 10);
            System.out.println("Farther, view dist = " + cam.getViewDist());
            if (draw)
                repaint();
        } else if (ke.getKeyChar() == 'n') {
            cam.setViewDist(cam.getViewDist() - 10);
            System.out.println("Nearer, view dist = " + cam.getViewDist());
            if (draw)
                repaint();
        } else if (ke.getKeyChar() == 'y') {
            Point3d pos = cam.getPosition();
            pos.setY(pos.getY() + 1);
            cam.setPosition(pos);
            System.out.println("Up, y = " + cam.getPosition().getY());
            if (draw)
                repaint();
        }//more Y
        else if (ke.getKeyChar() == 'Y') {
            Point3d pos = cam.getPosition();
            pos.setY(pos.getY() - 1);
            cam.setPosition(pos);
            System.out.println("Down, y = " + cam.getPosition().getY());
            if (draw)
                repaint();
        }//less Y
        else if (ke.getKeyChar() == 'x') {
            Point3d pos = cam.getPosition();
            pos.setX(pos.getX() + 1);
            cam.setPosition(pos);
            System.out.println("more, x = " + cam.getPosition().getX());
            if (draw)
                repaint();
        }//more x
        else if (ke.getKeyChar() == 'X') {
            Point3d pos = cam.getPosition();
            pos.setX(pos.getX() - 1);
            cam.setPosition(pos);
            System.out.println("less, x = " + cam.getPosition().getX());
            if (draw)
                repaint();
        }//less X
        else if (ke.getKeyChar() == 'z') {
            Point3d pos = cam.getPosition();
            pos.setZ(pos.getZ() + 1);
            cam.setPosition(pos);
            System.out.println("more, z = " + cam.getPosition().getZ());
            if (draw)
                repaint();
        }//more z
        else if (ke.getKeyChar() == 'Z') {
            Point3d pos = cam.getPosition();
            pos.setZ(pos.getZ() - 1);
            cam.setPosition(pos);
            System.out.println("less, z = " + cam.getPosition().getZ());
            if (draw)
                repaint();
        }//less Z
        else if (ke.getKeyChar() == 't') {
            thresh *= 10.0;
            System.out.println("more, t = " + thresh);
            if (draw)
                repaint();
        }//more thresh
        else if (ke.getKeyChar() == 'T') {
            thresh /= 10.0;
            System.out.println("less, t = " + thresh);
            if (draw)
                repaint();
        }//less Z
        else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println(ke.getKeyChar());
            System.exit(0);
        } else {
            System.out.println(ke.getKeyChar());
        }
    }


    public static void main(String args[]) {
        JFrame jf = new JFrame("Test");
        Screen screen = new Screen();

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().add(screen);
        jf.addKeyListener(screen);
        screen.addKeyListener(screen);

        jf.pack();
        jf.setVisible(true);
        //jf.setResizable(false);
    }
}
