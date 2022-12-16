package Core;

import Core.EngineObjects.Actor.Actor3D;
import Core.Rendering.RenderMode;
import Core.Rendering.Screen;
import Utility.Axis;
import Utility.Math.*;
import Utility.Utility;

import java.io.FileNotFoundException;

public class Core {

    public static void main(String[] args) throws FileNotFoundException {

        Screen screen = new Screen(RenderMode.WIREFRAME);
        Game game = screen.getDisplay();
        game.getWorld().AddActor(new Actor3D(ModelProcessor.GetShapeFromObj("src/Assets/Models/Cube2.obj"), new Vec3()));
        game.getWorld().AddActor(new Actor3D(ModelProcessor.GetShapeFromObj("src/Assets/Models/torus.obj"), new Vec3()));
        game.getWorld().getObjects().get(0).MoveTo(new Vec3(4, 0, 0));
        game.getWorld().getObjects().get(0).Rotate(45, new Vec3(1, 0, 0));

        boolean bRunning = true;
        while (bRunning) {
            game.repaint();
        }

    }

}
