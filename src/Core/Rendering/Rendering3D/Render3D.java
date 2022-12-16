package Core.Rendering.Rendering3D;

import Core.Camera;
import Core.EngineObjects.Actor.Actor3D;
import Core.Rendering.RenderBus;
import Utility.Math.*;
import Utility.Utility;

import java.awt.*;
import java.util.Arrays;

public class Render3D {

    public static void Render_Solid(RenderBus bus) {
        // Draws each vertex
        for (Actor3D actor : bus.world.getObjects()) {
            // Transforms the vertices to a form that can be rendered on screen
            if (actor != null) {
                Vec2[] drawVertices = new Vec2[actor.getShape().getVertices().length];
                for (int i = 0; i < actor.getShape().getVertices().length; i++) {
                    //int[] cords = vertexToScreenSpace(NMath.Add(actor.getShape().getVertices()[i], actor.getShape().getPosition()), bus.camera);
                    Vec2 cords = getViewportCoordinates(new Vec4(actor.getShape().getVertices()[i], 1), actor, bus.camera);
                    drawVertices[i] = cords;
                }
                // Draws each polygon (no depth culling currently)
                drawFace(drawVertices, actor.getShape().getDrawOrder(), bus.g2D);
            }
        }
    }

    public static void Render_Wireframe(RenderBus bus) {
        // Draws each vertex
        for (Actor3D actor : bus.world.getObjects()) {
            // Transforms the vertices to a form that can be rendered on screen
            if (actor != null && actor.getShape() != null) {
                Vec2[] drawVertices = new Vec2[actor.getShape().getVertices().length];
                for (int i = 0; i < actor.getShape().getVertices().length; i++) {
                    //int[] cords = vertexToScreenSpace_Perspective(actor.coordinateToWorldSpace(actor.getShape().getVertices()[i]), bus.camera);
                    Vec2 cords = getViewportCoordinates(new Vec4(actor.getShape().getVertices()[i], 1), actor, bus.camera);
                    drawVertices[i] = cords;
                }
                // Connects the draw vertices by a line
                drawConnectingLines(drawVertices, actor.getShape().getDrawOrder(), bus.g2D, bus.camera);
            }
        }
    }

    private static void drawConnectingLines(Vec2[] drawVertices, int[][] drawOrder, Graphics2D g2D, Camera camera) {
        for (int i = 0 ; i < drawOrder.length; i++) {
            for (int z = 0; z < drawOrder[i].length; z++) {
                Vec2 pointA = drawVertices[drawOrder[i][z]-1];
                Vec2 pointB = drawVertices[drawOrder[i][(z + 1) % drawOrder[i].length]-1];
                if (pointA != null && pointB != null) {
                    drawLine(pointA, pointB, g2D);
                }
            }
        }
    }

    private static void drawFace(Vec2[] drawVertices,  int[][] drawOrder, Graphics2D g2D) {
        for (int i = 0 ; i < drawOrder.length; i++) {
            Vec2[] currentVertices = new Vec2[drawOrder[i].length];
            for (int z = 0; z < drawOrder[i].length; z++) {
                currentVertices[z] = drawVertices[drawOrder[i][z]-1];
            }
            for (int v = 0; v < currentVertices.length; v++) {
                if (currentVertices[v] == null) {
                    return;
                }
            }
            int[][] cords = getDrawPolygonCords(currentVertices);
            g2D.fillPolygon(cords[0], cords[1], drawOrder[i].length);
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
        //System.out.printf("%f, %f\n", xCord, yCord);
        xCord = (xCord * camera.getWidth() + camera.getWidth()) / 2;
        yCord = (yCord * camera.getHeight() + camera.getHeight()) / 2;
        return new int[]{(int) xCord, (int) yCord};
    }

    private static int[] vertexToScreenSpace_Perspective (Vec4 vertexPos, Camera camera) {
        Vec4 vertexInCameraSpace = NMath.MultiplyVec4ByMat4(vertexPos, Utility.GetWorldToCameraSpaceConversionMatrix(camera));
        Vec4 vertexInClipSpace = NMath.MultiplyVec4ByMat4(vertexInCameraSpace, Utility.GetPerspectiveProjectionMatrix_OpenGL(camera.getNear(), camera.getFar(), camera.getvFOV(), camera.getWidth(), camera.getHeight()));
        return vertexToScreenSpace(new Vec3(vertexInClipSpace), camera);
    }

    private static Vec3 toNormalizedDeviceCoordinates(Vec4 cord) {
        return new Vec3(cord.x/cord.w, cord.y/cord.w, cord.z/cord.w);
    }

    private static Vec2 NDCToWindow(Vec3 ndc, Camera camera) {
        int xCord = (int) ((ndc.x * camera.getWidth() + camera.getWidth())/2);
        int yCord = (int) ((ndc.y * camera.getHeight() + camera.getHeight())/2);
        return new Vec2(xCord, yCord);
    }

    private static Vec2 getViewportCoordinates(Vec4 vertex, Actor3D actor, Camera camera) {
        Mat4 model = Utility.GetModelMatrix(actor);
        Mat4 view = Utility.GetWorldToCameraSpaceConversionMatrix(camera);
        Mat4 projection = Utility.GetPerspectiveProjectionMatrix_OpenGL(camera.getNear(), camera.getFar(), camera.getvFOV(), camera.getWidth(), camera.getHeight());
        Vec4 outputVertex = NMath.MultiplyVec4ByMat4(NMath.MultiplyVec4ByMat4(NMath.MultiplyVec4ByMat4(vertex, model), view), projection);
        Vec3 output = toNormalizedDeviceCoordinates(outputVertex);
        if (Float.isInfinite(output.z)) {
            return null;
        }
        return NDCToWindow(toNormalizedDeviceCoordinates(outputVertex), camera);
    }

    private static boolean outsideClipPlane(Vec2 point, Camera camera) {
        return point.x < 0 || point.x > camera.getWidth() || point.y < 0 || point.y > camera.getHeight();
    }

    private static boolean shouldClip(Vec2 point, Camera camera) {
        Vec3 objectToCamera = NMath.Subtract(new Vec3(point.x, point.y, 0), camera.getPos());

// Normalize the vector
        objectToCamera = NMath.Normalize(new Vec3(objectToCamera.x, objectToCamera.y, 0));

// Compute the dot product of the objectToCamera vector and the camera's view direction vector
        float dotProduct = NMath.DotProduct(objectToCamera, camera.getFront());

// If the dot product is positive, then the object is within the camera's field of view and can be rendered
        return !(dotProduct > 0);
    }

}
