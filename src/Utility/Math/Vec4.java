package Utility.Math;

public class Vec4 {

    public float x;
    public float y;
    public float z;
    public float w;

    public Vec4() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 0.0f;
    }

    public Vec4(float value) {
        x = value;
        y = value;
        z = value;
        w = value;
    }

    public Vec4(float X, float Y, float Z, float W) {
        x = X;
        y = Y;
        z = Z;
        w = W;
    }

    public Vec4(Vec3 vec, float W) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
        w = W;
    }

    public String toString() {
        return "[" + x + ", " + y + ", " + z + ", " + w + "]";
    }

}
