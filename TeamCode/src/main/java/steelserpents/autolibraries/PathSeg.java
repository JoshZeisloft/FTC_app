package steelserpents.autolibraries;

/**
 * Define a "PathSegment" object, used for building a path for the robot to follow.
 */
public class PathSeg
{
    public double mLeft;
    public double mRight;
    public double mSpeed;

    // Constructor
    public PathSeg(double Left, double Right, double Speed)
    {
        mLeft = Left;
        mRight = Right;
        mSpeed = Speed;
    }
}