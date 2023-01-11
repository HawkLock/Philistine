package Core;

import Utility.Math.*;

public class Camera {

    private Vec3 Pos;
    private final int FocalLength;
    private final int Width;
    private final int Height;
    private Quaternion Rotation;

    private float vFOV = 60; // In degrees
    private float near = 0.1f;
    private float far = 100000.0f;


    public Camera(Vec3 initialPosition, Quaternion initialRotation, int initialFocalLength, int initialWidth, int initialHeight) {
        Pos = initialPosition;
        FocalLength = initialFocalLength;
        Width = initialWidth;
        Height = initialHeight;
        Rotation = initialRotation;
    }

    public Vec3 getFront() {
        Vec3 direction = new Vec3();
        direction.x = (float) (Math.sin(NMath.toRadians(Rotation.y)) * Math.cos(NMath.toRadians(Rotation.x)));
        direction.y = (float) Math.sin(NMath.toRadians(Rotation.x));
        direction.z = (float) (Math.cos(NMath.toRadians(Rotation.y)) * Math.cos(NMath.toRadians(Rotation.x)));
        return NMath.Normalize(direction);
    }

    public Vec3 getRight() {
        return NMath.Normalize(NMath.CrossProduct(getFront(), new Vec3(0, 1, 0)));
    }

    public Vec3 getUp() {
        return NMath.Normalize(NMath.CrossProduct(getRight(), getFront()));
    }

    public void Rotate(Quaternion rotationChange) {
        Rotation = NMath.Add(Rotation, rotationChange);
    }

    public Vec3 getPos() {
        return Pos;
    }

    public void setPos(Vec3 pos) {
        Pos = pos;
    }

    public Quaternion getRotation() {
        return Rotation;
    }

    public void setRotation(Quaternion rotation) {
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

}
