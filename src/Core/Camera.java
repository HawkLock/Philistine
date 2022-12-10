package Core;

import Utility.Axis;
import Utility.Math.NMath;
import Utility.Math.Orientation;
import Utility.Math.Vec3;
import Utility.Math.Vec4;

public class Camera {

    private Vec3 Pos;
    private int FocalLength;
    private int Width;
    private int Height;
    private Orientation Rotation;

    private final Vec4 forwardReference = new Vec4(0, 0, 1, 0);

    private float vFOV = 45; // In degrees
    private float near = 5.0f;
    private float far = 10.0f;

    public Camera(Vec3 initialPosition, Orientation initialRotation, int initialFocalLength, int initialWidth, int initialHeight) {
        Pos = initialPosition;
        FocalLength = initialFocalLength;
        Width = initialWidth;
        Height = initialHeight;
        Rotation = initialRotation;
    }

    public void Rotate(Orientation rotationChange) {
        Rotation = NMath.Add(Rotation, rotationChange);
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

    public float getvFOV() {
        return vFOV;
    }

    public void setvFOV(float vFOV) {
        this.vFOV = vFOV;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }

    public Vec4 getForwardReference() {
        return forwardReference;
    }
}
