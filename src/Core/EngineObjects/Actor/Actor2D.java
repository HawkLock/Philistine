package Core.EngineObjects.Actor;

import Core.EngineObjects.Physics.CollisionComponent;
import Core.EngineObjects.Shape;
import Core.Rendering.Rendering2D.AnimationComponent;
import Core.Rendering.Rendering2D.SpriteRenderer;
import Utility.Math.NMath;
import Utility.Math.Vec2;
import Utility.Math.Vec3;
import Utility.Math.Vec4;

public class Actor2D implements Actor{
    public Vec3 pos;
    private final SpriteRenderer spriteRenderer;
    private final AnimationComponent animComp;
    private final CollisionComponent collisionComp;

    public Actor2D(String spritePath, Vec3 initialPos, Vec2 initialSize) {
        pos = initialPos;
        spriteRenderer = new SpriteRenderer(spritePath, this);
        spriteRenderer.setSpriteDimensions(initialSize);
        animComp = new AnimationComponent(this);
        collisionComp = new CollisionComponent(new Vec3(spriteRenderer.getSpriteDimensions()), this);
    }

    public SpriteRenderer getSpriteRenderer() {
        return spriteRenderer;
    }

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public Vec3 getPos() {
        return pos;
    }

    @Override
    public CollisionComponent getCollisionComponent() {
        return null;
    }

    @Override
    public void Move(Vec3 movementVector) {
        pos = NMath.Add(pos, movementVector);
    }

    @Override
    public void MoveTo(Vec3 destination) {
        pos = destination;
    }

    @Override
    public Vec3 getPosition() {
        return pos;
    }

    @Override
    public void setPosition(Vec3 position) {
        pos = position;
    }

    @Override
    public Vec4 coordinateToWorldSpace(Vec3 cord) {
        return null;
    }
}
