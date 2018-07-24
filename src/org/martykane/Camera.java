package org.martykane;

/**
 * A class to encapsulate a camera in 3d
 * <p>
 * Has a position in space, as well as some vectors indicating which way it
 * is oriented.
 */

public class Camera implements Exportable {
    private double x;
    private double y; //position
    private double z;

    private double lx;
    private double ly; //point the camera is looking at
    private double lz;

    private double ux;
    private double uy; //components for 'up' vector
    private double uz;

    private int viewDist; //Distance from eye to viewing plane

    /**
     * Make a camera with the specified position and orientation
     *
     * @param px the x coordinate of the camera's position
     * @param py the y coordinate of the camera's position
     * @param pz the z coordinate of the camera's position
     * @param cx the x coordinate of the point the camera is pointed at
     * @param cy the y coordinate of the point the camera is pointed at
     * @param cz the z coordinate of the point the camera is pointed at
     * @param vx the x coordinate of the UP direction vector
     * @param vy the y coordinate of the UP direction vector
     * @param vz the z coordinate of the UP direction vector
     */
    public Camera(double px, double py, double pz,  /* position */
                  double cx, double cy, double cz,  /* point at */
                  double vx, double vy, double vz) /*    up    */ {
        this.x = px;
        this.y = py; //position
        this.z = pz;

        this.lx = cx;
        this.ly = cy; //point at
        this.lz = cz;

        this.ux = vx;
        this.uy = vy; //up
        this.uz = vz;

        this.viewDist = 320;
    }

    /**
     * Make a camera with the specified position and orientation
     *
     * @param pos   the camera's position
     * @param point the point the camera is pointed at
     * @param up    the UP direction vector
     */
    public Camera(Point3d pos, Point3d point, Ray3d up) {
        this(pos.getX(), pos.getY(), pos.getZ(),
                point.getX(), point.getY(), point.getZ(),
                up.getX(), up.getY(), up.getZ());
    }

    /**
     * Make a camera at the given location, pointed at the given point, with
     * a default UP vector of (0,1,0)
     */
    public Camera(Point3d pos, Point3d point) {
        this(pos.getX(), pos.getY(), pos.getZ(),
                point.getX(), point.getY(), point.getZ(),
                0d, 1d, 0d);
    }


    //Get
    public Point3d getPosition() {
        return new Point3d(x, y, z);
    }

    public Point3d getLookPoint() {
        return new Point3d(lx, ly, lz);
    }

    public Ray3d getUpVector() {
        return new Ray3d(ux, uy, uz);
    }

    public void setPosition(Point3d pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void setLookPoint(Ray3d lp) {
        this.lx = lp.getX();
        this.ly = lp.getY();
        this.lz = lp.getZ();
    }

    public void setUpVector(Ray3d up) {
        this.ux = up.getX();
        this.uy = up.getY();
        this.uz = up.getZ();
    }


    public int getViewDist() {
        return this.viewDist;
    }

    public void setViewDist(int nvd) {
        this.viewDist = nvd;
    }

    /**
     * Get the look Vector (minus n)
     */
    public Ray3d getN() {
        //eye - look
        Point3d eye = getPosition();
        Point3d look = getLookPoint();
        return eye.minus(look);
    }

    /**
     * Get the right vector (U)
     */
    public Ray3d getU() {
        //up cross n
        Ray3d up = getUpVector();
        Ray3d n = getN();

        return up.cross(n);
    }

    /**
     * Get the perpendicular up vector (V)
     */
    public Ray3d getV() {
        //n cross u
        Ray3d n = getN();
        Ray3d u = getU();
        return n.cross(u);
    }


    /**
     *  +------> [c] = x
     *  |
     *  |
     *  |
     *  v [r] = y
     *
     */


    /**
     * Get the Ray passing through the RCth pixel, beginning at the eye.
     * This method is called in the main raytracing loop to get the next ray to
     * trace through the scene.
     *
     * @param c which column to use
     * @param r which row to use
     * @param w the width of the screen
     * @param h the height of the screen
     */
    public Ray3d getPixel(int c, int r, int w, int h) {
        //dir = -Nn + W( 2c/nCols-1)u + H(2r/nRows-1)v

        int nCols = w;
        int nRows = h;
        int W = w / 2;
        int H = h / 2;

        Ray3d n = getN().normalize();
        Ray3d u = getU().normalize();
        Ray3d v = getV().normalize();

        double N = this.viewDist;

        Ray3d r1 = n.times(N).reverse();
        Ray3d r2 = u.times(-W + c);
        Ray3d r3 = v.times(-H + r);

        //Diagnostic
        //System.out.println(r1+" + "+r2+" + "+r3);
        //System.out.println("ray to pixel ("+c+","+r+") "+r1.plus(r2).plus(r3));
        //System.out.println();

        return r1.plus(r2).plus(r3);
    }

    public String toJson() {
        return String.join("\n",
                "{",
                this.getPosition().toTriple().toJsonObjectWithLabel("position", 1) + ",",
                this.getLookPoint().toTriple().toJsonObjectWithLabel("center", 1) + ",",
                this.getUpVector().toTriple().toJsonObjectWithLabel("up", 1),
                "}");
    }

    public Object fromJson() {
        return null;
    }
}

