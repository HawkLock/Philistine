package Core;

import Core.Camera;
import Core.EngineObjects.World.World;
import Core.Rendering.RenderBus;
import Core.Rendering.RenderMode;
import Core.Rendering.Rendering2D.Render2D;
import Core.Rendering.Rendering3D.Render3D;
import Utility.Axis;
import Utility.Math.NMath;
import Utility.Math.Orientation;
import Utility.Math.Vec3;
import Utility.Math.Vec4;
import Utility.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Game extends JPanel {

    private int ScreenWidth = 1000;
    private int ScreenHeight = 1000;

    private Camera camera;
    private static World world;

    private float cameraMoveSpeed = 0.25f;
    private float zoomSpeed = 0.5f;
    private float modelTurnSpeed = 10f;
    private Vec3 cameraMovementModifier = new Vec3(1, -1, 1); // Modification because of the coordinate system mismatch
    private Vec3 cameraNegativeMovementModifier = new Vec3(-1, 1, -1); // Modification because of the coordinate system mismatch

    private static double deltaTime = 0.0f;
    private double lastFrame = 0.0f;

    private static int renderModeIndex; // 1 = TEXTURE, 2 = SOLID, 3 = WIREFRAME
    private static Consumer<RenderBus> renderModeReference;


    List<Integer> PressedKeys = new ArrayList<>();

    public Game(RenderMode mode) {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.lightGray);

        camera = new Camera(new Vec3(0, 0, -3), new Orientation(0, 0, 0), 2, ScreenWidth, ScreenHeight);
        world = new World();

        SetRenderMode(mode);
    }

    public void paintComponent(Graphics g) {
        double currentTime = System.currentTimeMillis();
        deltaTime = currentTime - lastFrame;
        lastFrame = currentTime;
        int currentFrameRate = (int) (1000 / deltaTime);

        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.black);
        g2D.setStroke(new BasicStroke(1));

        renderModeReference.accept(new RenderBus(camera, world, g2D));

        // Renders the frame rate
        g2D.drawString("FPS: " + currentFrameRate, 20, 20);
        g2D.drawString("Rotation: " + camera.getRotation(), 20, 50);
        g2D.drawString("Forward: " + camera.getFront(), 20, 80);

    }

    public void Update(int currentTick) {
        HandleInput();
    }

    private void SetRenderMode(RenderMode newMode) {
        switch (newMode) {
            case TEXTURE:
                renderModeReference = Render3D::Render_Solid; // CURRENTLY SETS RENDER MODE TO SOLID UNTIL TEXTURES ARE IMPLEMENTED
                renderModeIndex = 1;
                break;
            case SOLID:
                renderModeReference = Render3D::Render_Solid;
                renderModeIndex = 2;
                break;
            case WIREFRAME:
                renderModeReference = Render3D::Render_Wireframe;
                renderModeIndex = 3;
                break;
            case SPRITE:
                renderModeReference = Render2D::Render_Sprite;
                renderModeIndex = 4;
                break;
        }
    }

    private void CycleRenderMode() {
        renderModeIndex = (renderModeIndex + 1) % 4 + 1; // Increments mode by one and loops around if it has to
        switch (renderModeIndex) {
            case 1:
                SetRenderMode(RenderMode.TEXTURE);
                break;
            case 2:
                SetRenderMode(RenderMode.SOLID);
                break;
            case 3:
                SetRenderMode(RenderMode.WIREFRAME);
                break;
            case 4:
                SetRenderMode(RenderMode.SPRITE);
                break;
        }
    }

    public void HandleInput() {
        // MOVEMENT
        if (PressedKeys.contains((int) 'W')) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getFront(), cameraMoveSpeed), cameraMovementModifier));
        }
        if (PressedKeys.contains((int) 'S')) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getFront(), cameraMoveSpeed), cameraNegativeMovementModifier));
        }
        if (PressedKeys.contains((int) 'D')) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getRight(), cameraMoveSpeed), cameraNegativeMovementModifier));
        }
        if (PressedKeys.contains((int) 'A')) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getRight(), cameraMoveSpeed), cameraMovementModifier));
        }

        // OBJECT ROTATION
        if (PressedKeys.contains(Integer.valueOf(39))) {
            camera.Rotate(new Orientation(0, modelTurnSpeed, 0));
            //world.getObjects().get(0).Rotate(modelTurnSpeed, Axis.Y);
        }
        if (PressedKeys.contains(Integer.valueOf(37))) {
            camera.Rotate(new Orientation(0, -modelTurnSpeed, 0));
            //world.getObjects().get(0).Rotate(-modelTurnSpeed, Axis.Y);
        }
        if (PressedKeys.contains(Integer.valueOf(40))) {
            camera.Rotate(new Orientation(-modelTurnSpeed, 0, 0));
            //world.getObjects().get(0).Rotate(-modelTurnSpeed, Axis.X);
        }
        if (PressedKeys.contains(Integer.valueOf(38))) {
            camera.Rotate(new Orientation(modelTurnSpeed, 0, 0));
            //world.getObjects().get(0).Rotate(modelTurnSpeed, Axis.X);
        }

        // ZOOMING IN AND OUT
        if (PressedKeys.contains(Integer.valueOf(69))) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getFront(), zoomSpeed), cameraMovementModifier));
        }
        if (PressedKeys.contains(Integer.valueOf(81))) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getFront(), zoomSpeed), cameraNegativeMovementModifier));
        }
        // CYCLE THROUGH THE DIFFERENT RENDERING MODES (T)
        if (PressedKeys.contains(Integer.valueOf(84))) {
            CycleRenderMode();
        }
    }

    public void KeyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!PressedKeys.contains(Integer.valueOf(keyCode))) {
            PressedKeys.add(Integer.valueOf(keyCode));
        }
    }

    public void KeyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (PressedKeys.contains(Integer.valueOf(keyCode))) {
            PressedKeys.remove(Integer.valueOf(keyCode));
        }
    }

    public void HandleMouseWheelInput(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getFront(), zoomSpeed), cameraMovementModifier));
        }
        else {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getFront(), zoomSpeed),cameraNegativeMovementModifier));
        }
    }

    public static double getDeltaTime() {
        return deltaTime;
    }

    public int getWidth() {return ScreenWidth;}
    public int getHeight() {return ScreenHeight;}
    public World getWorld() {return world;}
}
