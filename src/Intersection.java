/**
 * A class to group hit data for an Intersection of a Ray with a 3d Object
 *
 */
public class Intersection
{
  private double hitTime; //t
  private Object3d hitObject;
  private boolean entering;
  private int surface;
  private Point3d hitPoint; //point of intersection
  private Ray3d hitNormal; //Normal to Object at point of intersection 
  

  /**
   * Construct a new Intersection Object
   * @param time the time (Ray parameter) of the hit
   * @param obj reference to the object that was hit
   * @param enter whether or not the intersecting Ray is entering the Object
   * @param surface the index of the surface that was hit
   * @param p the Point of intersection
   * @param n the Normal vector to the Object at the Point of intersection
   */
  public Intersection( double time, Object3d obj, boolean enter, int surface, 
		       Point3d p, Ray3d n )
  {
    this.hitTime = time;
    this.hitObject = obj;
    this.entering = enter;
    this.surface = surface;
    this.hitPoint = p;
    this.hitNormal = n;
  }


  public double getHitTime()
  {
    return this.hitTime;
  }

  public Object3d getHitObject()
  {
    return this.hitObject;
  }

  public boolean isEntering()
  {
    return this.entering;
  }

  public int getSurface()
  {
    return this.surface;
  }

  public Point3d getHitPoint()
  {
    return this.hitPoint;
  }

  public Ray3d getHitNormal()
  {
    return this.hitNormal;
  }

  public String toString()
  {
    return "T:"+hitTime+" point: "+hitPoint+" N: "+hitNormal;
  }
}
