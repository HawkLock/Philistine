package Core;

import Utility.Axis;
import Utility.Math.*;
import Utility.Utility;

import java.awt.image.renderable.RenderableImage;

public class Camera {

    private Vec3 Pos;
    private int FocalLength;
    private int Width;
    private int Height;
    private Orientation Rotation;

    private final Vec4 forwardReference = new Vec4(0, 0, 1, 1);

    private float vFOV = 45; // In degrees
//    private float near = 0.1f;
//    private float far = 100.0f;
    private float near = 0.1f;
    private float far = 100.0f;


    public Camera(Vec3 initialPosition, Orientation initialRotation, int initialFocalLength, int initialWidth, int initialHeight) {
        Pos = initialPosition;
        FocalLength = initialFocalLength;
        Width = initialWidth;
        Height = initialHeight;
        Rotation = initialRotation;
    }

    public Vec3 getFront() {
        Vec3 direction = new Vec3();
        direction.z = (float) (Math.cos(NMath.toRadians(Rotation.y)) * Math.cos(NMath.toRadians(Rotation.x)));
        direction.y = (float) Math.sin(NMath.toRadians(Rotation.x));
        direction.x = (float) (Math.sin(NMath.toRadians(Rotation.y)) * Math.cos(NMath.toRadians(Rotation.x)));
        return NMath.Normalize(direction);
    }

    public Vec3 getRight() {
        return NMath.Normalize(NMath.CrossProduct(getFront(), new Vec3(0, 1, 0)));
    }

    public Vec3 getUp() {
        return NMath.Normalize(NMath.CrossProduct(getRight(), getFront()));
    }


    public void Rotate(Orientation rotationChange) {
        Rotation = NMath.Add(Rotation, rotationChange);
    }

    public void Rotate(Vec3 rotationChange) {
        Rotation = NMath.Add(Rotation, new Orientation(rotationChange));
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

    public void setRotation(Vec3 rotation) {
        Rotation = new Orientation(rotation);
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
