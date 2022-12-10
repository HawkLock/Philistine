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

        boolean bRunning = true;
        while (bRunning) {
            game.repaint();
        }
    }

}
