/**
 * A class to represent a 3-dimensional ray.
 */

public class Ray3d {
    private double x;
    private double y; /* The Ray's direction components */
    private double z;

    private Ray3d() {
    }

    /**
     * Form a Ray given X, Y and Z values for the direction
     *
     * @param nx the x value for the new Ray's direction
     * @param ny the y value for the new Ray's direction
     * @param nx the z value for the new Ray's direction
     */
    public Ray3d(double nx, double ny, double nz) {
        this.x = nx;
        this.y = ny;
        this.z = nz;
    }

    /**
     * Create a new Ray, using a Triple for the direction values
     *
     * @param values the Triple object to get values from
     */
    public Ray3d(Triple values) {
        this(values.getX(), values.getY(), values.getZ());
    }

    /**
     * Create a new Ray, using a Point for the direction values
     *
     * @param values the Triple object to get values from
     */
    public Ray3d(Point3d values) {
        this(values.toTriple());
    }//


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
     * Add another Ray to this Ray and return the result
     */
    public Ray3d plus(Triple add) {
        return new Ray3d(this.x + add.getX(),
                this.y + add.getY(),
                this.z + add.getZ());
    }

    public Ray3d plus(Point3d add) {
        return this.plus(add.toTriple());
    }

    public Ray3d plus(Ray3d add) {
        return this.plus(add.toTriple());
    }

    /**
     * Subtract another Ray from this Ray and return the result
     */
    public Ray3d minus(Triple addRay) {
        return new Ray3d(this.x - addRay.getX(),
                this.y - addRay.getY(),
                this.z - addRay.getZ());
    }

    public Ray3d minus(Point3d add) {
        return this.minus(add.toTriple());
    }

    public Ray3d minus(Ray3d add) {
        return this.minus(add.toTriple());
    }

    /**
     * Multiply this Ray by a scalar and return the result
     */
    public Ray3d times(double num) {
        return new Ray3d(this.x * num, this.y * num, this.z * num);
    }

    /**
     * Divide this Ray by a scalar and return the result
     */
    public Ray3d divideBy(double num) {
        return new Ray3d(this.x / num, this.y / num, this.z / num);
    }


    /**
     * Negate this Ray and return the result
     */
    public Ray3d reverse() {
        return new Ray3d(-this.x, -this.y, -this.z);
    }

    /**
     * Get the magnitude of this Ray
     */
    public double magnitude() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
    }

    /**
     * Get a normalized version of this Ray
     */
    public Ray3d normalize() {
        return this.divideBy(this.magnitude());
    }

    /**
     * Return the dot product of this Ray and another Ray (a scalar)
     *
     * @param b the Triple with which to 'dot' this Ray
     * @return the dot product of this Ray and b
     */
    public double dot(Triple b) {
        // A dot B = AxBx+AyBy+AzBz
        Ray3d a = this;
        return (a.x * b.x + a.y * b.y + a.z * b.z);
    }

    /**
     * Return the dot product of this Ray and another Ray (a scalar)
     *
     * @param ray the Ray with which to 'dot' this Ray
     * @return the dot product of this Ray and b
     */
    public double dot(Ray3d b) {
        return this.dot(b.toTriple());
    }

    /**
     * Return the dot product of this Ray and another Ray (a scalar). Note that
     * this is not really legal, but it's just here for convenience.
     *
     * @param ray the Point with which to 'dot' this Ray
     * @return the dot product of this Ray and b
     */
    public double dot(Point3d b) {
        return this.dot(b.toTriple());
    }

    /**
     * Return the 3d cross product of this Ray and another Ray (a Ray
     * perpendicular to both this Ray and the argument)
     *
     * @param b the Ray with which to 'dot' this Ray
     * @return the dot product of this Ray and b
     */
    public Ray3d cross(Ray3d b) {
        // A cross B =
        Ray3d a = this;

        double x = ((a.y * b.z) - (a.z * b.y));
        double y = ((a.z * b.x) - (a.x * b.z));
        double z = ((a.x * b.y) - (a.y * b.x));

        return new Ray3d(x, y, z);
    }


    /**
     * Return the Ray that results when this Ray is reflected off of the given
     * Ray
     *
     * @param n the Ray to reflect this Ray off of
     * @return the reflection of this Ray from n
     */
    public Ray3d reflect(Ray3d n) {
        //Ray3d nhat = n.perp().normalize();
        //Ray3d result = nhat.times(this.dot(nhat)).times(2);
        //return this.minus(result);
        return this; /** Fix Me **/
    }


    /**
     * Print this Ray to standard out
     */
    public void print() {
        System.out.println("Ray3d (" + x + "," + y + "," + z + ")");
    }

    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    public Triple toTriple() {
        return new Triple(this.x, this.y, this.z);
    }


    //Some tests
    public static void main(String args[]) {
        System.out.println("3D Ray Testing\n");

        Ray3d r = new Ray3d(1, 2, 1);
        r.print();

        System.out.println("magnitute: " + r.magnitude());
        System.out.println("normalized: " + r.normalize());
        System.out.println("r / 12: " + r.divideBy(12));
        System.out.println("");

        Ray3d a = new Ray3d(-1d, 0d, 0d);
        Ray3d b = new Ray3d(0d, 1d, 0d);
        a.print();
        b.print();
        System.out.println("Cross Product:" + a.cross(b));

    }

}
