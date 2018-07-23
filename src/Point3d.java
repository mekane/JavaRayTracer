/**
 * A class to represent a 3-dimensional point.
 */

public class Point3d {
    protected double x;
    protected double y;
    protected double z;

    private Point3d() {
    }

    ;//no default (empty) constructor

    public Point3d(double nx, double ny, double nz) {
        this.x = nx;
        this.y = ny;
        this.z = nz;
    }

    /**
     * Create a new Point, using a Triple for the location values
     *
     * @param values the Triple object to get values from
     */
    public Point3d(Triple values) {
        this(values.getX(), values.getY(), values.getZ());
    }

    public Point3d(Ray3d ray) {
        this(ray.toTriple());
    }

    public Point3d(Point3d point) {
        this(point.toTriple());
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    //
    public void setX(double nx) {
        this.x = nx;
    }

    public void setY(double ny) {
        this.y = ny;
    }

    public void setZ(double nz) {
        this.z = nz;
    }

    /**
     * Translate this point
     *
     * @param dx the distance to move this Point in the X direction
     * @param dy the distance to move this Point in the Y direction
     * @param dz the distance to move this Point in the Z direction
     */
    public void move(double dx, double dy, double dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
    }

    /**
     * Add this Point and another Point
     *
     * @param add the Point to add to this point
     * @return the Point resulting from the addition of the two Points
     */
    public Point3d plus(Triple add) {
        return new Point3d(this.x + add.getX(), this.y + add.getY(), this.z + add.getZ());
    }

    public Point3d plus(Point3d add) {
        return this.plus(add.toTriple());
    }

    public Point3d plus(Ray3d add) {
        return this.plus(add.toTriple());
    }

    /**
     * Subtract this Point from another Point
     *
     * @param add the Point to subtract from this point
     * @return the Point resulting from the subtraction of the two Points
     */
    public Point3d minus(Triple add) {
        return new Point3d(this.x - add.getX(), this.y - add.getY(), this.z - add.getZ());
    }

    public Point3d minus(Ray3d add) {
        return this.minus(add.toTriple());
    }

    public Ray3d minus(Point3d add) {
        double dx = this.x - add.x;
        double dy = this.y - add.y;
        double dz = this.z - add.z;
        return new Ray3d(dx, dy, dz);
    }

    /**
     * Get the distance between this Point and another
     *
     * @param point the point to get the distance from
     * @return the distance to the given point
     */
    public double distanceTo(Point3d point) {
        double dx = point.x - this.x;
        double dy = point.y - this.y;
        double dz = point.z - this.z;

        return (double) (Math.sqrt((dx * dx) + (dy * dy) + (dz * dz)));
    }


    public void print() {
        System.out.println("Point3d (" + x + "," + y + "," + z + ")");
    }

    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    public Triple toTriple() {
        return new Triple(this.x, this.y, this.z);
    }


    /**
     * Decide whether the given ray intersects with this object
     *
     * @param ray the ray to be tested for intersection
     * @return whether or not the ray intersects with this object
     */
    public boolean intersect(Ray3d ray) {
        return false;
    }

}
