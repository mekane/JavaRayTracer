/**
 * A class that encapsulates a group of 3 doubles
 * <p>
 * They can be referenced by X,Y,Z or A,B,C or 1,2,3 or R,G,B
 */

public class Triple {
    double x;
    double y;
    double z;

    public Triple() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Triple(double nx, double ny, double nz) {
        this.x = nx;
        this.y = ny;
        this.z = nz;
    }

    //XYZ
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    //ABC
    public double getA() {
        return this.x;
    }

    public double getB() {
        return this.y;
    }

    public double getC() {
        return this.z;
    }

    //123
    public double get1() {
        return this.x;
    }

    public double get2() {
        return this.y;
    }

    public double get3() {
        return this.z;
    }

    //RGB
    public double getRed() {
        return this.x;
    }

    public double getGreen() {
        return this.y;
    }

    public double getBlue() {
        return this.z;
    }
}
