package Utility.Math;

public class Vec3 {

    public float x;
    public float y;
    public float z;

    public Vec3() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public Vec3(float value) {
        x = value;
        y = value;
        z = value;
    }

    public Vec3(float X, float Y, float Z) {
        x = X;
        y = Y;
        z = Z;
    }

    public Vec3(Vec2 vec) {
        x = vec.x;
        y = vec.y;
        z = 0.0f;
    }

    public Vec3(Vec4 vec) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }

    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

}
