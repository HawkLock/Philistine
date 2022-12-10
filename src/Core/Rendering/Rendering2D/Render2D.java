package Core.Rendering.Rendering2D;

import Core.EngineObjects.Actor.Actor;
import Core.EngineObjects.Actor.Actor2D;
import Core.Rendering.RenderBus;

public class Render2D {

    public static void Render_Sprite(RenderBus bus) {
        for (Actor actor : bus.world.getObjects()) {
            if (actor != null) {
                Actor2D actor2D = (Actor2D) actor;
                // If the actor has a sprite renderer
                if (actor2D.getSpriteRenderer() != null) {
                    actor2D.getSpriteRenderer().Render(bus.camera, bus.g2D);
                }
            }
        }
    }

}
