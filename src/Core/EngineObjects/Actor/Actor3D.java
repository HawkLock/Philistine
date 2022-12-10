package Core.EngineObjects.Actor;

import Core.EngineObjects.Physics.CollisionComponent;
import Core.EngineObjects.Shape;
import Utility.Axis;
import Utility.Math.Vec3;

public class Actor3D implements Actor{
    public Shape shape;
    Vec3 pos;

    public Actor3D(Shape initialShape, Vec3 initialPos) {
        shape = initialShape;
        pos = initialPos;
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

    public void Rotate(float angle, Axis axis) {
        shape.Rotate(angle, axis);
    }

    public void Move(Vec3 movementVector) {
        shape.Move(movementVector);
    }

    public Vec3 getPosition() {
        return pos;
    }

    public void setPosition(Vec3 position) {
        this.pos = position;
    }
}
