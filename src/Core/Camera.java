package Core;

import Utility.Math.NMath;
import Utility.Math.Orientation;
import Utility.Math.Vec3;

public class Camera {

    private Vec3 Pos;
    private int FocalLength;
    private int Width;
    private int Height;
    private Orientation Rotation;

    public Camera(Vec3 initialPosition, Orientation initialRotation, int initialFocalLength, int initialWidth, int initialHeight) {
        Pos = initialPosition;
        FocalLength = initialFocalLength;
        Width = initialWidth;
        Height = initialHeight;
        Rotation = initialRotation;
    }

    public Vec3 getPos() {
        return Pos;
    }

    public void setPos(Vec3 pos) {
        Pos = pos;
    }

    public Orientation getRotation() {
        return Rotation;
    }

    public void setRotation(Orientation rotation) {
        Rotation = rotation;
    }

    public void Move(Vec3 movementVector) {
        Pos = NMath.Add(Pos, movementVector);
    }

    public int getFocalLength() {
        return FocalLength;
    }

    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }
}
