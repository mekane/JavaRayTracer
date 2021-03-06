package org.martykane;

import org.json.JSONObject;

import java.awt.*;

/**
 * A class to encapsulate a triangular polygon in 3 dimensions
 */

public class Tri3d extends Object3d {
    private Point3d v1;
    private Point3d v2; //Vertices of triangle
    private Point3d v3;

    private Tri3d() {
    }

    /**
     * Construct a new Tri3d. Define vertices in a clockwise direction.
     *
     * @param x1 the x coordinate of the first Vertex
     * @param y1 the y coordinate of the first Vertex
     * @param z1 the z coordinate of the first Vertex
     * @param x2 the x coordinate of the second Vertex
     * @param y2 the y coordinate of the second Vertex
     * @param z2 the z coordinate of the second Vertex
     * @param x3 the x coordinate of the third Vertex
     * @param y3 the y coordinate of the third Vertex
     * @param z3 the z coordinate of the third Vertex
     */
    public Tri3d(double x1, double y1, double z1,
                 double x2, double y2, double z2,
                 double x3, double y3, double z3) {
        this.v1 = new Point3d(x1, y1, z1);
        this.v2 = new Point3d(x2, y2, z2);
        this.v3 = new Point3d(x3, y3, z3);
    }

    /**
     * Construct a new Tri3d. Define vertices in a clockwise direction.
     *
     * @param p1 the first Vertex
     * @param p2 the second Vertex
     * @param p3 the third Vertex
     */
    public Tri3d(Point3d p1, Point3d p2, Point3d p3) {
        this.v1 = new Point3d(p1);
        this.v2 = new Point3d(p2);
        this.v3 = new Point3d(p3);
    }


    public Point3d getV1() {
        return new Point3d(this.v1);
    }

    public Point3d getV2() {
        return new Point3d(this.v2);
    }

    public Point3d getV3() {
        return new Point3d(this.v3);
    }

    public Color getColor() {
        return this.color;
    }


    /**
     * Get a Ray normal to the plane of this Tri
     *
     * @return a Ray3d that is perpendicular to the plane of this Triangle
     */
    public Ray3d getNormal() {
        Ray3d U = getV2().minus(getV1());
        Ray3d W = getV3().minus(getV1());

        return (U.cross(W)).normalize();
    }


    /**
     * Determine when (whether) the given Ray, starting from the given Point,
     * intersects with this Tri.
     *
     * @param S the origin of the Ray
     * @param c the Ray to check
     * @return an array of Intersection objects containing the details of the
     * ray hitting this object, or null if there is no intersection. The array
     * should be sorted according to hit 'time', so the first Intersection should
     * be item [0].
     */
    public Intersection[] intersectWith(Point3d S, Ray3d c) {
    /* To determine whether the ray intersects this triangle, we first find 
     * the intersection time of the ray with the plane that the ray lies on,
     * and then determine whether the hit point lies within the triangle.
     *
     * We find the hit point below. If the hit point is negative, we return
     * null because the plane is behind the eye. If we get a positive hit, we
     * move on to testing whether the point is within the triangle. To do this,
     * we construct normal rays for each side of the triangle, and if the angle
     * between the normal ray and the point is less than zero we know the point
     * lies on the outside of the side, so it must not be in our triangle.
     */
        Ray3d n = this.getNormal();

        Ray3d BA = v1.minus(S);
        double top = n.dot(BA);
        double bot = n.dot(c);
        if (bot == 0)
            return null; //ray and plane are parallel - no hit

        double t = top / bot;
        if (t < 0)
            return null; //intersection behind eye - no hit

        Point3d P = S.plus(c.times(t)); //intersection point

        boolean s1 = true;
        boolean s2 = true; //Check the point against each side
        boolean s3 = true;

        //check a half plane
        Ray3d U = n.cross(v2.minus(v1));
        Ray3d V = P.minus(v1);
        s1 = (U.dot(V) > 0);

        //check another half plane
        U = n.cross(v3.minus(v2));
        V = P.minus(v2);
        s2 = (U.dot(V) > 0);

        //check third half plane
        U = n.cross(v1.minus(v3));
        V = P.minus(v3);
        s3 = (U.dot(V) > 0);

        if (s1 && s2 && s3) {
            Intersection[] result = new Intersection[1];
            result[0] = new Intersection(t, this, true, 1, P, n);
            return result;
        }

        return null;
    }

    //see Object3d blocks() method
    public boolean blocks(Point3d S, Ray3d c) {
        return (this.intersectWith(S, c) != null);
    }

    @Override
    public String toJson() {
        return String.join("\n",
                "{",
                this.baseJsonStrings() + ",",
                this.v1.toTriple().toJsonObjectWithLabel("v1", 1) + ",",
                this.v2.toTriple().toJsonObjectWithLabel("v2", 1) + ",",
                this.v3.toTriple().toJsonObjectWithLabel("v3", 1),
                "}"
        );
    }

    public static Tri3d fromJson(String json) {
        JSONObject triJson = new JSONObject(json);

        JSONObject colorJson = triJson.getJSONObject("color");
        JSONObject v1Json = triJson.getJSONObject("v1");
        JSONObject v2Json = triJson.getJSONObject("v2");
        JSONObject v3Json = triJson.getJSONObject("v3");

        String name = triJson.getString("name");
        Color color = new Color(colorJson.getInt("r"), colorJson.getInt("g"), colorJson.getInt("b"));
        double diffuse = triJson.getDouble("diffuse");
        Point3d v1 = new Point3d(v1Json.getDouble("x"), v1Json.getDouble("y"), v1Json.getDouble("z"));
        Point3d v2 = new Point3d(v2Json.getDouble("x"), v2Json.getDouble("y"), v2Json.getDouble("z"));
        Point3d v3 = new Point3d(v3Json.getDouble("x"), v3Json.getDouble("y"), v3Json.getDouble("z"));

        Tri3d result = new Tri3d(v1, v2, v3);
        result.setName(name);
        result.setColor(color);
        result.setDiffuse(diffuse);

        return result;
    }
}
