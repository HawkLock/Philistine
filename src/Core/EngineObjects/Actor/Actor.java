package Core.EngineObjects.Actor;

import Core.EngineObjects.Physics.CollisionComponent;
import Core.EngineObjects.Shape;
import Utility.Axis;
import Utility.Math.Vec3;

public interface Actor {
    Shape getShape();
    Vec3 getPos();
    CollisionComponent getCollisionComponent();

    void Rotate(float angle, Axis axis);

    void Move(Vec3 movementVector);

    Vec3 getPosition();

    void setPosition(Vec3 position);
}
