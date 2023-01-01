package Core.EngineObjects.Physics;

import Core.EngineObjects.Actor.Actor;
import Utility.Math.Vec2;
import Utility.Math.Vec3;

public class CollisionComponent {
    private final Vec3 collisionVolume;
    private final Actor parent;

    public CollisionComponent(Vec3 initialCollisionVolume, Actor parentActor) {
        collisionVolume = initialCollisionVolume;
        parent = parentActor;
    }

    public Vec2[] getCoordinateExtremes() {
        return new Vec2[] {getMinAndMaxX(), getMinAndMaxY(), getMinAndMaxZ()};
    }

    public Vec2 getMinAndMaxX() {
        return new Vec2(parent.getPos().x - collisionVolume.x/2, parent.getPos().x + collisionVolume.x/2);
    }

    public Vec2 getMinAndMaxY() {
        return new Vec2(parent.getPos().y - collisionVolume.y/2, parent.getPos().y + collisionVolume.y/2);
    }

    public Vec2 getMinAndMaxZ() {
        return new Vec2(parent.getPos().z - collisionVolume.z/2, parent.getPos().z + collisionVolume.z/2);
    }

}
