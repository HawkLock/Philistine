package Core.Rendering.Rendering3D;

import Core.Camera;
import Core.EngineObjects.Actor.Actor;
import Core.EngineObjects.Actor.Actor2D;
import Core.Rendering.RenderBus;
import Utility.Math.*;
import Utility.Utility;

import java.awt.*;
import java.util.ArrayList;

public class Render3D {

    public static void Render_Solid(RenderBus bus) {
        // Draws each vertex
        for (Actor actor : bus.world.getObjects()) {
            // Transforms the vertices to a form that can be rendered on screen
            if (actor != null) {
                Vec2[] drawVertices = new Vec2[actor.getShape().getVertices().length];
                for (int i = 0; i < actor.getShape().getVertices().length; i++) {
                    int[] cords = vertexToScreenSpace(NMath.Add(actor.getShape().getVertices()[i], actor.getShape().getPosition()), bus.camera);
                    drawVertices[i] = new Vec2(cords[0], cords[1]);
                }
                // Draws each polygon (no depth culling currently)
                drawFace(drawVertices, actor.getShape().getDrawOrder(), bus.g2D);
            }
        }
    }

    public static void Render_Wireframe(RenderBus bus) {
        // Draws each vertex
        for (Actor actor : bus.world.getObjects()) {
            // Transforms the vertices to a form that can be rendered on screen
            if (actor != null && actor.getShape() != null) {
                Vec2[] drawVertices = new Vec2[actor.getShape().getVertices().length];
                for (int i = 0; i < actor.getShape().getVertices().length; i++) {
                    int[] cords = vertexToScreenSpace(NMath.Add(actor.getShape().getVertices()[i], actor.getShape().getPosition()), bus.camera);
                    drawVertices[i] = new Vec2(cords[0], cords[1]);
                }
                // Connects the draw vertices by a line
                drawConnectingLines(drawVertices, actor.getShape().getDrawOrder(), bus.g2D);
            }
        }
    }

    private static void drawConnectingLines(Vec2[] drawVertices,  int[][] drawOrder, Graphics2D g2D) {
        for (int i = 0 ; i < drawOrder.length; i++) {
            for (int z = 0; z < drawOrder[i].length; z++) {
                Vec2 pointA = drawVertices[drawOrder[i][z]-1];
                Vec2 pointB = drawVertices[drawOrder[i][(z + 1) % 3]-1];
                drawLine(pointA, pointB, g2D);
            }
        }
    }

    private static void drawFace(Vec2[] drawVertices,  int[][] drawOrder, Graphics2D g2D) {
        for (int i = 0 ; i < drawOrder.length; i++) {
            Vec2[] currentVertices = new Vec2[drawOrder[i].length];
            for (int z = 0; z < drawOrder[i].length; z++) {
                currentVertices[z] = drawVertices[drawOrder[i][z]-1];
            }
            int[][] cords = getDrawPolygonCords(currentVertices);
            g2D.fillPolygon(cords[0], cords[1], 3);
        }
    }

    private static int[][] getDrawPolygonCords(Vec2[] vectors) {
        int[] xCords = new int[vectors.length];
        for (int i = 0; i < xCords.length; i++) {
            xCords[i] = (int) vectors[i].x;
        }
        int[] yCords = new int[vectors.length];
        for (int i = 0; i < yCords.length; i++) {
            yCords[i] = (int) vectors[i].y;
        }
        return new int[][] {xCords, yCords};
    }

    private static void drawLine(Vec2 pointA, Vec2 pointB, Graphics2D g2D) {
        g2D.drawLine((int)pointA.x, (int)pointA.y, (int)pointB.x, (int)pointB.y);
    }

    private static int[] vertexToScreenSpace(Vec3 vertexPos, Camera camera) {
        // Gets their position based off of the projection rendering formula
        float xCord = NMath.Subtract(vertexPos, camera.getPos()).x * (camera.getFocalLength() / Math.abs(NMath.Subtract(vertexPos, camera.getPos()).z));
        float yCord = NMath.Subtract(vertexPos, camera.getPos()).y * (camera.getFocalLength() / Math.abs(NMath.Subtract(vertexPos, camera.getPos()).z)); // Transforms the coordinates to the screen dimensions
        xCord = (xCord * camera.getWidth() + camera.getWidth()) / 2;
        yCord = (yCord * camera.getHeight() + camera.getHeight()) / 2;
        return new int[]{(int) xCord, (int) yCord};
    }

    private static int[] vertexToScreenSpace_Perspective (Vec3 vertexPos, Camera camera) {
        /* KINDA BROKEN
        Orientation cameraRotation = camera.getRotation();
        Vec3 d = NMath.MultiplyVec3ByMat3(NMath.Subtract(vertexPos, camera.getPos()), NMath.MultiplyMat3(NMath.MultiplyMat3(Utility.TrimMat4ToMat3(Utility.GetRotationMatrixX(-cameraRotation.x)), Utility.TrimMat4ToMat3(Utility.GetRotationMatrixY(-cameraRotation.y))), Utility.TrimMat4ToMat3(Utility.GetRotationMatrixZ(-cameraRotation.z))));
        int xCord = (int) (camera.getFocalLength()/d.z * d.x);
        int yCord = (int) (camera.getFocalLength()/d.z * d.y);
        return new int[]{xCord, yCord};
         */
        return null;
    }

}
