package Utility.Math;

public class Orientation extends Vec3 {

    public Orientation() {
        super();
    }

    public Orientation(float value) {
        super(value);
    }

    public Orientation(float X, float Y, float Z) {
        super(X, Y, Z);
    }

    public Orientation(Vec3 axis) {
        x = axis.x;
        y = axis.y;
        z = axis.z;
    }

}
