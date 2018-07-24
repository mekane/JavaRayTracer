package org.martykane;

import java.awt.Color;

public abstract class Object3d {
    //All 3d Objects have various material properties
    protected Color color;
    protected double diffuse; //The coefficient of diffuse reflection
    protected String name;

    public Color getColor() {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public void setColor(Color nc) {
        this.color = new Color(nc.getRed(), nc.getGreen(), nc.getBlue());
    }

    public double getDiffuse() {
        return this.diffuse;
    }

    public void setDiffuse(double nd) {
        this.diffuse = nd;
    }


    public String getName() {
        return (this.name == null ? "" : name);
    }

    public void setName(String nn) {
        this.name = nn;
    }


    /**
     * Set all the material properties of this Object. If this method is not
     * called on an object it will have default dull white surface properties.
     *
     * @param nc the base hue of the object
     * @param nd the diffuse reflection coefficient. Determines what percentage
     *           of the light hitting this object is diffusely reflected. Valid range is
     *           0.0 to 0.1. Other values will be bounded (truncated).
     */
    public void setMaterial(Color nc, double nd) {
        this.color = nc;
        this.diffuse = nd;
    }

    /**
     * Determine when (whether) the given Ray, starting from the given Point,
     * intersects with this Object. Intended to provide detailed information
     * about the given Ray intersecting with this object.
     *
     * @param S the origin of the Ray
     * @param c the Ray to check
     * @return an array of Intersection objects containing the details of the
     * ray hitting this object, or null if there is no intersection. The array
     * should be sorted according to hit 'time', so the first Intersection should
     * be item [0].
     */
    public abstract Intersection[] intersectWith(Point3d S, Ray3d c);

    /**
     * Determine whether the given Ray, starting from the given Point, intersects
     * with this object. This method is intended to provide only a 'yes' or 'no'
     * - suitable for checking if this object blocks the light to a point, or
     * such.
     * Only return true if the hit is between 0 and 1 - objects outside of the
     * line between this object and the light will not block the ray. Returns
     * true or false immediately, without any calculation of hit data.
     *
     * @param S the origin of the Ray
     * @param c the Ray to check
     * @return true if the ray hits this object, false otherwise
     */
    public abstract boolean blocks(Point3d S, Ray3d c);


    public static Color getColorTextureCheckerboard(Point3d p) {
        if ((Math.abs(p.getX()) % 2 <= 1 &&
                Math.abs(p.getZ()) % 2 > 1) ||
                (Math.abs(p.getZ()) % 2 <= 1 &&
                        Math.abs(p.getX()) % 2 > 1))
            return Color.green;
        else
            return Color.red;
    }
}
