package ProjectObjects;

import Core.EngineObjects.Actor.Actor2D;
import Utility.Math.Vec2;
import Utility.Math.Vec3;

public class Player extends Actor2D {

    private static final String spritePath = "src/Assets/Sprites/Zelda.jpg";
    private static final Vec2 dimensions = new Vec2(500, 500);

    public Player() {
        super(spritePath, new Vec3(), dimensions);
    }

}
