package Utility.Math;

public class Quaternion {

    public float w;
    public float x;
    public float y;
    public float z;

    public Quaternion() {
        w = 0;
        x = 0;
        y = 0;
        z = 0;
    }

    public Quaternion(float W, float X, float Y, float Z) {
        w = W;
        x = X;
        y = Y;
        z = Z;
    }

    public Quaternion(float W, Vec3 vec) {
        w = W;
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }

    public String toString() {
        return "[" + w + ", " + x + ", " + y + ", " + z + "]";
    }

    // Gives a quaternion representing a rotation of "angle" degrees around the given axis
    // Roll (1, 0, 0)
    // Pitch (1, 0, 0)
    // Yaw (0, 1, 0)
    public static Quaternion GetQuaternionRotation(float angle, Vec3 axis) {
        angle = NMath.toRadians(angle);
        float halfAngleCos = (float) Math.cos(angle/2);
        float halfAngleSin = (float) Math.sin(angle/2);
        float x = halfAngleSin * axis.x;
        float y = halfAngleSin * axis.y;
        float z = halfAngleSin * axis.z;
        return new Quaternion(halfAngleCos, x, y, z);
    }

    public float GetLength() {
        return (float) Math.sqrt(Math.pow(w, 2) + Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public Quaternion GetInverse() {
        return NMath.Divide(new Quaternion(w, -x, -y, -z), (float) Math.pow(GetLength(), 2));
    }

}
