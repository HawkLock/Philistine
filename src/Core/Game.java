package Core;

import Core.EngineObjects.World.World;
import Core.Rendering.RenderBus;
import Core.Rendering.Rendering3D.RenderMode;
import Core.Rendering.Rendering3D.Render3D;
import Utility.Math.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Game extends JPanel {

    private final int ScreenWidth = 1000;
    private final int ScreenHeight = 1000;

    private static Camera camera;
    private static World world;

    private final float cameraMoveSpeed = 0.25f;
    private final float cameraTurnSpeed = 1000.0f;
    private final float cameraTurnSpeedModifier = 5; // Used in the rotation axis for camera rotation (Note: I don't know how quaternions work)
    private final float zoomSpeed = 0.5f;

    private static double deltaTime = 0.0f;
    private double lastFrame = 0.0f;

    private static Consumer<RenderBus> renderModeReference;

    List<Integer> PressedKeys = new ArrayList<>();

    public Game(RenderMode mode) {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.lightGray);

        camera = new Camera(new Vec3(0, 0, 0), new Quaternion(0, 0, 6, 0), 2, ScreenWidth, ScreenHeight);
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

        g2D.setColor(Color.black);

        // Renders the frame rate
        g2D.drawString("FPS: " + currentFrameRate, 20, 20);
        g2D.drawString("Rotation: " + camera.getRotation(), 20, 50);
        //g2D.drawString("Camera Forward: " + camera.getFront(), 20, 80);
        g2D.drawString("Position: " + camera.getPos(), 20, 80);
        //System.out.println();

    }

    public void Update(int currentTick) {
        HandleInput();
    }

    private void SetRenderMode(RenderMode newMode) {
        switch (newMode) {
            case SOLID -> renderModeReference = Render3D::Render_Solid;
            case WIREFRAME -> renderModeReference = Render3D::Render_Wireframe;
            case HYBRID -> renderModeReference = Render3D::Render_WithPolygonOutlines;
        }
    }

    public void HandleInput() {
        // MOVEMENT
        if (PressedKeys.contains((int) 'W')) {
            camera.Move(NMath.Multiply(camera.getFront(), cameraMoveSpeed));
        }
        if (PressedKeys.contains((int) 'S')) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getFront(), cameraMoveSpeed), -1));
        }
        if (PressedKeys.contains((int) 'D')) {
            camera.Move(NMath.Multiply(camera.getRight(), cameraMoveSpeed));
        }
        if (PressedKeys.contains((int) 'A')) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getRight(), cameraMoveSpeed), -1));
        }

        // Camera ROTATION
        if (PressedKeys.contains(Integer.valueOf(39))) {
            //camera.Rotate(new Quaternion(0, cameraTurnSpeed, 0));
            camera.Rotate(Quaternion.GetQuaternionRotation(-cameraTurnSpeed, new Vec3(0, cameraTurnSpeedModifier, 0)));
        }
        if (PressedKeys.contains(Integer.valueOf(37))) {
            //camera.Rotate(new Vec3(0, -cameraTurnSpeed, 0));
            camera.Rotate(Quaternion.GetQuaternionRotation(cameraTurnSpeed, new Vec3(0, cameraTurnSpeedModifier, 0)));
        }
        if (PressedKeys.contains(Integer.valueOf(40))) { // DOWN
            //camera.Rotate(new Vec3(-cameraTurnSpeed, 0, 0));
            camera.Rotate(Quaternion.GetQuaternionRotation(-cameraTurnSpeed, new Vec3(cameraTurnSpeedModifier, 0, 0)));
        }
        if (PressedKeys.contains(Integer.valueOf(38))) { // UP
            //camera.Rotate(new Vec3(cameraTurnSpeed, 0, 0));
            camera.Rotate(Quaternion.GetQuaternionRotation(cameraTurnSpeed, new Vec3(cameraTurnSpeedModifier, 0, 0)));
        }

        // ZOOMING IN AND OUT
        if (PressedKeys.contains((int) 'E')) {
            camera.Move(NMath.Multiply(camera.getFront(), zoomSpeed));
        }
        if (PressedKeys.contains((int) 'Q')) {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getFront(), zoomSpeed), -1));
        }

        // SETTING RENDERING MODES
        if (PressedKeys.contains((int) '1')) {
            SetRenderMode(RenderMode.SOLID);
        }
        if (PressedKeys.contains((int) '2')) {
            SetRenderMode(RenderMode.WIREFRAME);
        }
        if (PressedKeys.contains((int) '3')) {
            SetRenderMode(RenderMode.HYBRID);
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
        PressedKeys.remove(Integer.valueOf(keyCode));
    }

    public void HandleMouseWheelInput(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            camera.Move(NMath.Multiply(camera.getFront(), zoomSpeed));
        }
        else {
            camera.Move(NMath.Multiply(NMath.Multiply(camera.getFront(), cameraMoveSpeed), -1));
        }
    }

    public static double getDeltaTime() {
        return deltaTime;
    }

    public int getWidth() {return ScreenWidth;}
    public int getHeight() {return ScreenHeight;}
    public World getWorld() {return world;}
    public static Camera getCamera() {
        return camera;
    }
}
