package Core.Rendering;

import Core.Camera;
import Core.EngineObjects.World.World;

import java.awt.*;

public class RenderBus {

    public Camera camera;
    public World world;
    public Graphics2D g2D;

    public RenderBus(Camera inputCamera, World inputWorld, Graphics2D inputG2D) {
        camera = inputCamera;
        world = inputWorld;
        g2D = inputG2D;
    }

}
