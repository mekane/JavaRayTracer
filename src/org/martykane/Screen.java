package org.martykane;

import java.awt.Color;

/**
 * The screens draw a visual representation of everything in the World.
 * It keeps an internal list of all the entities it is supposed to draw.
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Screen extends JComponent implements KeyListener {
    private boolean draw = true;

    //Global Scene Data
    private Vector<Object3d> objectList;

    private Vector<Light> lightList;
    private float ambientLight;

    private Camera cam;

    //Constructors
    public Screen() {
        this(640, 480);
    }

    public Screen(int width, int height) {
        super();

        //CAMERA
        cam = new Camera(new Point3d(20, 20, 20),
                new Point3d(50, 5, 45));
        cam.setViewDist(600);

        //LIGHTS
        this.lightList = new Vector<Light>();
        lightList.add(new Light(0, 45, 0, 0.95, new Color(255, 255, 255)));
        lightList.add(new Light(-45, 25, 45, 0.5, new Color(255, 100, 100)));

        this.ambientLight = 0.1f;

        //SCENE OBJECTS
        this.objectList = new Vector<Object3d>();

        Quad3d q = new Quad3d(
                new Point3d(90, 0d, -90),
                new Point3d(-90, 0d, -90),
                new Point3d(-90, 0d, 90),
                new Point3d(90, 0d, 90)
        );

        q.setMaterial(new Color(32, 32, 48), 0.65);
        q.setName("Blue surface");
        objectList.add(q);

        Sphere s1 = new Sphere(new Point3d(45, 5, 45), 5);
        s1.setColor(new Color(100, 170, 100));
        s1.setDiffuse(0.7d);
        objectList.add(s1);

        Sphere s2 = new Sphere(new Point3d(56, 5, 45), 5);
        s2.setColor(new Color(50, 100, 200));
        s2.setDiffuse(0.4d);
        objectList.add(s2);

        Sphere s3 = new Sphere(new Point3d(56, 5, 30), 5);
        s3.setColor(new Color(200, 100, 50));
        s3.setDiffuse(0.6d);
        objectList.add(s3);

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
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);

        Point3d eye = cam.getPosition();

        Color background = new Color(ambientLight, ambientLight, ambientLight);

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
                    g.setColor(Color.BLACK);
                } else //shade according to object
                {
                    Object3d obj = best.getHitObject();
                    Color hue = obj.getColor();
                    double diffuse = best.getHitObject().getDiffuse();

                    Color nhue = background;
                    Point3d P = best.getHitPoint();
                    Ray3d N = best.getHitNormal().normalize();

                    //Adjust hit point for shadow check - move slightly back towards eye
                    Point3d start = P.minus(next.times(0.0001));

                    for (Light L : lightList) //find contribution from each light
                    {
                        //Check for shadows by sending a ray to each light and checking for
                        //intersections with that ray for each object. If the ray hits an
                        //object, then that object is blocking this light, so we skip it
                        Ray3d toLight = L.getPosition().minus(start);
                        if (isInShadow(start, toLight)) {
                            double id = ambientLight;
                            nhue = addColors(nhue, mulColor(hue, id));
                        } else {
                            Ray3d S = L.getPosition().minus(P).normalize();

                            double val = Math.max(S.dot(N), 0);

                            double id = val * diffuse * L.getBrightness() + ambientLight;

                            nhue = addColors(nhue, mulColor(hue, id));
                        }
                    }

                    g.setColor(nhue);

                    //System.out.println("pixel: " + nhue);
                }
                g.drawLine(w - c, h - r, w - c, h - r);
            }//C
        }//R
    }


    /**
     * Check each object to see if it blocks the given ray. If any of the objects
     * does block the ray (intersect with the object), then the point in question
     * must be in shadow.
     */
    private boolean isInShadow(Point3d point, Ray3d ray) {
        for (Object3d o : objectList)//check each object for shadow
            if (o.blocks(point, ray))
                return true;
        return false;
    }


    /**
     * Private utility method to add two colors together
     */
    private Color addColors(Color c1, Color c2) {
        return new Color((int) Math.min((c1.getRed() + c2.getRed()), 255),
                (int) Math.min((c1.getGreen() + c2.getGreen()), 255),
                (int) Math.min((c1.getBlue() + c2.getBlue()), 255));
    }

    /**
     * Private utility method to multiple a color by a scalar
     */
    private Color mulColor(Color c1, double num) {
        return new Color((int) Math.min((c1.getRed() * num), 255),
                (int) Math.min((c1.getGreen() * num), 255),
                (int) Math.min((c1.getBlue() * num), 255));
    }

    /**
     * Private utility method to multiple a color by a scalar
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
        if (ke.getKeyChar() == 'a') {
            System.out.println("All Scene Values");
            System.out.println("camera: " + this.sceneToJson());
        } else if (ke.getKeyChar() == 'f') {
            cam.setViewDist(cam.getViewDist() - 10);
            System.out.println("Farther, view dist = " + cam.getViewDist());
        } else if (ke.getKeyChar() == 'h' || ke.getKeyChar() == '?') {
            this.printKeyboardHelp();
        } else if (ke.getKeyChar() == 'n') {
            cam.setViewDist(cam.getViewDist() + 10);
            System.out.println("Nearer, view dist = " + cam.getViewDist());
        } else if (ke.getKeyChar() == 'p') {
            draw = !draw;
            System.out.println("Draw: " + draw);
        } else if (ke.getKeyChar() == 'q' || ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (ke.getKeyChar() == 's') {
            this.exportToJson();
            System.out.println("Scene exported to scene.json");
        } else if (ke.getKeyChar() == 'x') {
            Point3d pos = cam.getPosition();
            pos.setX(pos.getX() + 1);
            cam.setPosition(pos);
        } else if (ke.getKeyChar() == 'X') {
            Point3d pos = cam.getPosition();
            pos.setX(pos.getX() - 1);
            cam.setPosition(pos);
        } else if (ke.getKeyChar() == 'y') {
            Point3d pos = cam.getPosition();
            pos.setY(pos.getY() + 1);
            cam.setPosition(pos);
        } else if (ke.getKeyChar() == 'Y') {
            Point3d pos = cam.getPosition();
            pos.setY(pos.getY() - 1);
            cam.setPosition(pos);
        } else if (ke.getKeyChar() == 'z') {
            Point3d pos = cam.getPosition();
            pos.setZ(pos.getZ() + 1);
            cam.setPosition(pos);
        } else if (ke.getKeyChar() == 'Z') {
            Point3d pos = cam.getPosition();
            pos.setZ(pos.getZ() - 1);
            cam.setPosition(pos);
        } else {
            System.out.println(ke.getKeyChar());
        }

        if (draw)
            repaint();
    }

    private void printKeyboardHelp() {
        System.out.println(String.join("\n",
                "Keyboard Commands:",
                "a - print All scene information to console",
                "f - decrease view distance, making scene appear Farther away",
                "h - print this help to the console",
                "n - increase view distance, making scene appear Nearer",
                "p - Pause drawing - screen will not refresh while paused",
                "q - Quit",
                "s - Save all scene data to scene.json",
                "x - move the camera along the X axis",
                "X - move the camera backwards along the X axis",
                "y - move the camera along the Y axis",
                "Y - move the camera backwards along the Y axis",
                "z - move the camera along the Z axis",
                "Z - move the camera backwards along the Z axis",
                ""
        ));
    }


    public static void main(String args[]) {
        JFrame jf = new JFrame("3D Raytracer");
        Screen screen = new Screen();

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().add(screen);
        jf.addKeyListener(screen);
        screen.addKeyListener(screen);

        jf.pack();
        jf.setVisible(true);
        //jf.setResizable(false);
    }

    public String sceneToJson() {
        StringBuilder lightJson = new StringBuilder("[");
        for (Light l : this.lightList) {
            lightJson.append(l.toJson()).append(",");
        }
        lightJson.setCharAt(lightJson.length() - 1, ']');

        StringBuilder objectJson = new StringBuilder("[");
        for (Object3d o : this.objectList) {
            objectJson.append(o.toJson()).append(",");
        }
        objectJson.setCharAt(lightJson.length() - 1, ']');

        return String.join("\n",
                "{",
                "camera: " + this.cam.toJson() + ",",
                "lights: " + lightJson + ",",
                "objects: " + objectJson,
                "}"
        );
    }

    public void exportToJson() {
        File outputFile = new File("scene.json");
        PrintWriter fileOut = null;

        try {
            fileOut = new PrintWriter(new FileOutputStream(outputFile));
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error: could not open file " + outputFile.getName());
            return;
        }

        fileOut.println(this.sceneToJson());
        fileOut.println();
        fileOut.flush();
        fileOut.close();
    }
}
