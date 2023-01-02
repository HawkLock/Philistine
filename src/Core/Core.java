package Core;

import Core.EngineObjects.Actor.Actor3D;
import Core.EngineObjects.World.ProceduralWorldGenerator;
import Core.Rendering.RenderMode;
import Core.Rendering.Screen;
import Utility.Axis;
import Utility.Math.*;
import Utility.Utility;

import java.awt.*;
import java.io.FileNotFoundException;

public class Core {

    public static void main(String[] args) throws FileNotFoundException {

        Screen screen = new Screen(RenderMode.WIREFRAME);
        Game game = screen.getDisplay();
        //game.getWorld().AddActor(new Actor3D(ModelProcessor.GetShapeFromObj("src/Assets/Models/Plane.obj"), new Vec3(0, -3, 2), Color.DARK_GRAY));
        float objectSpacing = 10.0f;
        game.getWorld().AddActor(new Actor3D(ModelProcessor.GetShapeFromObj("src/Assets/Models/Cube2.obj"), new Vec3(0, 0, objectSpacing), Color.blue));
        game.getWorld().AddActor(new Actor3D(ModelProcessor.GetShapeFromObj("src/Assets/Models/Icosphere.obj"), new Vec3(-objectSpacing, 0, 0), Color.yellow));
        game.getWorld().AddActor(new Actor3D(ModelProcessor.GetShapeFromObj("src/Assets/Models/Sphere.obj"), new Vec3(objectSpacing, 0, 0), Color.green));
        game.getWorld().AddActor(new Actor3D(ModelProcessor.GetShapeFromObj("src/Assets/Models/Torus.obj"), new Vec3(0, 0, -objectSpacing), Color.red));
        //game.getWorld().getObjects().get(0).Rotate(270, new Vec3(1, 0, 0));
        //game.getWorld().getObjects().get(0).Scale(5);
        int mapWidth = 10;
        int mapDepth = 10;
        game.getWorld().AddActor(new Actor3D(ProceduralWorldGenerator.GenerateMap(mapWidth, mapDepth, 5), new Vec3(mapWidth, 0, mapDepth), Color.DARK_GRAY));

        boolean bRunning = true;
        while (bRunning) {
            game.repaint();
        }

    }

}
