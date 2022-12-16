package Core.EngineObjects.Actor;

import Core.EngineObjects.Physics.CollisionComponent;
import Core.EngineObjects.Shape;
import Utility.Axis;
import Utility.Math.NMath;
import Utility.Math.Vec3;
import Utility.Math.Vec4;

public interface Actor {
    Shape getShape();
    Vec3 getPos();
    CollisionComponent getCollisionComponent();

    void Move(Vec3 movementVector);

    void MoveTo(Vec3 destination);

    Vec3 getPosition();

    void setPosition(Vec3 position);

    Vec4 coordinateToWorldSpace(Vec3 cord);
}
