package Core.EngineObjects.Actor;

import Core.EngineObjects.Physics.CollisionComponent;
import Core.EngineObjects.Shape;
import Core.Game;
import Utility.Axis;
import Utility.Math.NMath;
import Utility.Math.Orientation;
import Utility.Math.Vec3;
import Utility.Math.Vec4;

import java.awt.*;

public class Actor3D implements Actor, Comparable<Actor3D>{
    public Shape shape;
    private Orientation orientation;
    private Vec3 pos;
    private Color color;

    public Actor3D(Shape initialShape, Vec3 initialPos) {
        shape = initialShape;
        pos = initialPos;
        orientation = new Orientation(0);
        color = Color.BLACK;
    }

    public Actor3D(Shape initialShape, Vec3 initialPos, Color initialColor) {
        shape = initialShape;
        pos = initialPos;
        orientation = new Orientation(0);
        color = initialColor;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public Vec3 getPos() {
        return pos;
    }

    @Override
    public CollisionComponent getCollisionComponent() {
        return null;
    }

    public void Rotate(float angle, Vec3 referenceAxis) {
        orientation = NMath.Add(orientation, new Orientation(NMath.Multiply(referenceAxis, angle)));
    }

    public void Move(Vec3 movementVector) {
        shape.Move(movementVector);
    }

    public void MoveTo(Vec3 destination) {shape.setPosition(destination); pos = destination;}

    public Vec3 getPosition() {
        return pos;
    }

    public void setPosition(Vec3 position) {
        this.pos = position;
    }

    @Override
    public Vec4 coordinateToWorldSpace(Vec3 cord) {
        return new Vec4(NMath.Add(pos, cord), 1);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void Scale(float scalar) {
        shape.Scale(scalar);
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int compareTo(Actor3D o) {
        return (int) (NMath.Distance(this.getPos(), Game.getCamera().getPos()) - NMath.Distance(o.getPos(), Game.getCamera().getPos()));
    }
}
