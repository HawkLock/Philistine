package Core.EngineObjects.Actor;

import Core.EngineObjects.Physics.CollisionComponent;
import Core.EngineObjects.Shape;
import Core.Rendering.Rendering2D.AnimationComponent;
import Core.Rendering.Rendering2D.SpriteRenderer;
import Utility.Axis;
import Utility.Math.NMath;
import Utility.Math.Vec2;
import Utility.Math.Vec3;

public class Actor2D implements Actor{
    public Vec3 pos;
    private SpriteRenderer spriteRenderer;
    private AnimationComponent animComp;
    private CollisionComponent collisionComp;

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
    public void Rotate(float angle, Axis axis) {

    }

    @Override
    public void Move(Vec3 movementVector) {
        pos = NMath.Add(pos, movementVector);
    }

    @Override
    public Vec3 getPosition() {
        return pos;
    }

    @Override
    public void setPosition(Vec3 position) {
        pos = position;
    }
}
