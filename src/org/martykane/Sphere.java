package org.martykane;

/**
 * Base class for an sphere that exists in our 3d world
 */

public class Sphere extends Object3d {
    private double x;
    private double y; /* The sphere's location in space */
    private double z;

    private double radius;

    private Sphere() {
    }

    /**
     * Create a new Sphere
     *
     * @param nx   the X coordinate of the sphere's location
     * @param ny   the Y coordinate of the sphere's location
     * @param nz   the Z coordinate of the sphere's location
     * @param size the radius of the Sphere
     */
    public Sphere(double nx, double ny, double nz, double size) {
        this.x = nx;
        this.y = ny;
        this.z = nz;
        this.radius = size;
    }

    public Sphere(Point3d np, int size) {
        this(np.getX(), np.getY(), np.getZ(), size);
    }


    public Point3d getPosition() {
        return new Point3d(this.x, this.y, this.z);
    }


    /**
     * Determine when (whether) the given Ray, starting from the given Point,
     * intersects with this Sphere.
     *
     * @param S the origin of the Ray
     * @param c the Ray to check
     * @return one or more Intersection objects containing information about
     * where the ray hit this Sphere, or null if there was no intersection.
     */
    public Intersection[] intersectWith(Point3d S, Ray3d c) {
        Intersection[] result = null;

        double A = c.getX() * c.getX() + c.getY() * c.getY() + c.getZ() * c.getZ();

        double bx = (this.x - S.getX()); //Account for the Sphere's position
        double by = (this.y - S.getY());
        double bz = (this.z - S.getZ());

        double B = -(c.getX() * bx + c.getY() * by + c.getZ() * bz);

        double r = this.radius * this.radius;

        double C = bx * bx + by * by + bz * bz - r;

        double D = (B * B - A * C);
        if (D < 0) //no intersection
        {
            return null;
        }

        if (D == 0) //'graze' the sphere
        {
            result = new Intersection[1];
            Point3d P = S.plus(c.times(-B / A));
            result[0] = new Intersection(-B / A, this, true, 0, P, new Ray3d(P));
            /****/return null;
        }

        result = new Intersection[2];
        int hit = 0;

        double t1 = (-B - Math.sqrt(D)) / A;
        if (t1 > 0.00001) //The first hit
        {
            Point3d P = S.plus(c.times(t1));
            Ray3d N = P.minus(new Point3d(this.x, this.y, this.z));
            result[0] = new Intersection(t1, this, true, 0, P, N);
            hit++;
        }

        double t2 = (-B / A) + Math.sqrt(D) / A;
        if (t2 > 0.00000001) {
            Point3d P = S.plus(c.times(t2));
            Ray3d N = P.minus(new Point3d(this.x, this.y, this.z));
            result[hit] = new Intersection(t2, this, false, 0, P, N.reverse());
        }

        return result;
    }

    //see Object3d blocks() method
    public boolean blocks(Point3d S, Ray3d c) {
        double A = c.getX() * c.getX() + c.getY() * c.getY() + c.getZ() * c.getZ();

        double bx = (this.x - S.getX()); //Account for the Sphere's position
        double by = (this.y - S.getY());
        double bz = (this.z - S.getZ());
        double B = -(c.getX() * bx + c.getY() * by + c.getZ() * bz);
        double r = this.radius * this.radius;
        double C = bx * bx + by * by + bz * bz - r;

        double D = (B * B - A * C);
        if (D < 0) //no intersection
        {
            return false;
        }

        if (D == 0) //'graze' the sphere
        {
            /****/return false;
        }

        double t1 = (-B - Math.sqrt(D)) / A;
        if (t1 > 0.00001 && t1 < 1.0) //The first hit
        {
            return true; //But need to check if it's less than 1, also!
        }

        double t2 = (-B / A) + Math.sqrt(D) / A;
        if (t2 > 0.00000001 && t2 < 1.0) {
            return true;
        }

        return false;
    }

    @Override
    public String toJson() {
        return String.join("\n",
                "{",
                this.baseJsonStrings() + ",",
                this.getPosition().toTriple().toJsonObjectWithLabel("center", 1) + ",",
                "  \"radius\": " + this.radius,
                "}"
        );
    }

    @Override
    public Object fromJson() {
        return null;
    }
}
