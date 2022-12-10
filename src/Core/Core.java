package Core;

import Core.EngineObjects.Actor.Actor3D;
import Core.Rendering.RenderMode;
import Core.Rendering.Screen;
import Utility.Axis;
import Utility.Math.NMath;
import Utility.Math.Vec3;
import Utility.Math.Vec4;
import Utility.Utility;

import java.io.FileNotFoundException;

public class Core {

    public static void main(String[] args) throws FileNotFoundException {

        Screen screen = new Screen(RenderMode.WIREFRAME);
        Game game = screen.getDisplay();
        game.getWorld().AddActor(new Actor3D(ModelProcessor.GetShapeFromObj("src/Assets/Models/Cube.obj"), new Vec3()));
        //game.getWorld().getObjects().get(0).MoveTo(new Vec3(5f, 6f, -2));
        /*
        for (float i = -10; i < 10; i++) {
            for (float z = -10; z < 10; z++) {
                //game.getWorld().AddActor(new Actor2D("src/Assets/Sprites/Zelda.jpg", new Vec3(i, z, 0), new Vec2(100, 100)));
                game.getWorld().AddActor(new Player());
            }
        }
         */
        boolean bRunning = true;
        while (bRunning) {
            game.repaint();
        }
    }

}
