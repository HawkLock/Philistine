package Utility.Math;

public class Vec2 {

    public float x;
    public float y;

    public Vec2() {
        x = 0.0f;
        y = 0.0f;
    }

    public Vec2(float value) {
        x = value;
        y = value;
    }

    public Vec2(float X, float Y) {
        x = X;
        y = Y;
    }

    public Vec2(int[] values) {
        x = values[0];
        y = values[1];
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

}
