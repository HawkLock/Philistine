package Core.Rendering.Rendering3D;

import Core.Camera;
import Core.EngineObjects.Actor.Actor3D;
import Core.Rendering.RenderBus;
import Utility.Math.*;
import Utility.Utility;

import java.awt.*;
import java.util.ArrayList;

public class Render3D {

    // Most computationally expensive
    public static void Render_WithPolygonOutlines(RenderBus bus) {
        Render_Solid(bus);
        Render_Wireframe(bus);
    }

    public static void Render_Solid(RenderBus bus) {
        // Initializes the view matrix and projection matrix because those do not change throughout the frame
        Mat4 view = Utility.GetWorldToCameraSpaceConversionMatrix(bus.camera);
        Mat4 projection = Utility.GetPerspectiveProjectionMatrix_OpenGL(bus.camera.getNear(), bus.camera.getFar(), bus.camera.getvFOV(), bus.camera.getWidth(), bus.camera.getHeight());

        bus.world.getObjects().sort(null);
        ArrayList<Actor3D> actors = bus.world.getObjects();

        for (int z = 0; z < actors.size(); z++) {
            // Initializes the model matrix for the specific model once
            Mat4 model = Utility.GetModelMatrix(actors.get(z));

            // Transforms the vertices to a form that can be rendered on screen
            RenderVec[] drawVertices = new RenderVec[actors.get(z).getShape().getVertices().length];
            for (int i = 0; i < actors.get(z).getShape().getVertices().length; i++) {
                RenderVec cords = getViewportCoordinates(new Vec4(actors.get(z).getShape().getVertices()[i], 1), bus.camera, model, view, projection);
                drawVertices[i] = cords;
            }
            // Draws each polygon (no depth culling currently)
            bus.g2D.setColor(actors.get(z).getColor());
            drawFace(drawVertices, actors.get(z), bus.camera, model, view, projection, bus.g2D);
        }
    }

    private static void drawFace(RenderVec[] drawVertices, Actor3D actor, Camera camera, Mat4 model, Mat4 view, Mat4 projection, Graphics2D g2D) {
        int[][] drawOrder = actor.getShape().getDrawOrder();
        Color[] polygonColors = actor.getShape().getPolygonColors();
        for (int i = 0 ; i < drawOrder.length; i++) {
            RenderVec[] currentVertices = new RenderVec[drawOrder[i].length];
            for (int z = 0; z < drawOrder[i].length; z++) {
                currentVertices[z] = drawVertices[drawOrder[i][z]-1];
            }
            if (polygonColors != null) {
                if (polygonColors.length > i) {
                    g2D.setColor(polygonColors[i]);
                } else {
                    g2D.setColor(actor.getColor());
                }
            }
            ArrayList<RenderVec> outOfViewVertices = new ArrayList<>();
            for (int v = 0; v < currentVertices.length; v++) {
                if (currentVertices[v] == null) {
                    return;
                }
                if (currentVertices[v].getDrawVec().z == 0) {
                    outOfViewVertices.add(currentVertices[v]);
                }
            }
            // If all the vertices are out of view there is no point in rendering it, so it does not
            if (outOfViewVertices.size() < currentVertices.length) {
                if (outOfViewVertices.size() > 0) {
                    ArrayList<RenderVec> visiblePolygon = new ArrayList<>();
                    // Goes through vertices and acts like its connecting lines, it adds the valid vertex to a new array that makes up the visible polygon
                    byte previousID = 0; // 0 means no intersection, 1 means previous was an intersection, 2 means previous and the one before were intersections
                    for (int a = 0; a < currentVertices.length; a++) {
                        // If the previous was an intersection (it gets added) then it must add the start and end points of this iteration or else it will skip the start
                        RenderVec start = currentVertices[a];
                        RenderVec end = currentVertices[(a+1)%currentVertices.length];

                        // Determine if one point must be clipped, else simply add the end point
                        if (start.getDrawVec().z == 0 ^ end.getDrawVec().z == 0) {
                            previousID++;
                            RenderVec visibleVec;
                            RenderVec clippingVec;
                            if (start.getDrawVec().z == 0) {
                                clippingVec = start;
                                visibleVec = end;
                            } else {
                                visibleVec = start;
                                clippingVec = end;
                            }
                            Vec3 clippedTransformedVec = ClipVertexBasedOnViewIntersection(visibleVec, clippingVec, camera, model, view, projection);
                            visiblePolygon.add(new RenderVec(clippedTransformedVec, clippingVec.getWorldVec()));
                            if (end.getDrawVec().z != 0) {
                                visiblePolygon.add(end);
                            }
                        } else if (start.getDrawVec().z == 0) {
                        } else {
                            visiblePolygon.add(end);
                        }
                    }
                    int[][] cords = getDrawPolygonCords(visiblePolygon);
                    g2D.fillPolygon(cords[0], cords[1], visiblePolygon.size());
                    // System.out.println(visiblePolygon.size());
                    continue;
                }
                int[][] cords = getDrawPolygonCords(currentVertices);
                g2D.fillPolygon(cords[0], cords[1], drawOrder[i].length);
            }
        }
    }

    private static int[][] getDrawPolygonCords(RenderVec[] vectors) {
        int[] xCords = new int[vectors.length];
        for (int i = 0; i < xCords.length; i++) {
            xCords[i] = (int) vectors[i].getDrawVec().x;
        }
        int[] yCords = new int[vectors.length];
        for (int i = 0; i < yCords.length; i++) {
            yCords[i] = (int) vectors[i].getDrawVec().y;
        }
        return new int[][] {xCords, yCords};
    }

    private static int[][] getDrawPolygonCords(ArrayList<RenderVec> vectors) {
        int[] xCords = new int[vectors.size()];
        for (int i = 0; i < xCords.length; i++) {
            xCords[i] = (int) vectors.get(i).getDrawVec().x;
        }
        int[] yCords = new int[vectors.size()];
        for (int i = 0; i < yCords.length; i++) {
            yCords[i] = (int) vectors.get(i).getDrawVec().y;
        }
        return new int[][] {xCords, yCords};
    }

    public static void Render_Wireframe(RenderBus bus) {
        // Initializes the view matrix and projection matrix because those do not change throughout the frame
        Mat4 view = Utility.GetWorldToCameraSpaceConversionMatrix(bus.camera);
        Mat4 projection = Utility.GetPerspectiveProjectionMatrix_OpenGL(bus.camera.getNear(), bus.camera.getFar(), bus.camera.getvFOV(), bus.camera.getWidth(), bus.camera.getHeight());

        // Draws each vertex
        for (Actor3D actor : bus.world.getObjects()) {
            // Initializes the model matrix for the specific model once
            Mat4 model = Utility.GetModelMatrix(actor);

            // Transforms the vertices to a form that can be rendered on screen
            if (actor.getShape() != null) {
                RenderVec[] drawVertices = new RenderVec[actor.getShape().getVertices().length];
                for (int i = 0; i < actor.getShape().getVertices().length; i++) {
                    //int[] cords = vertexToScreenSpace_Perspective(actor.coordinateToWorldSpace(actor.getShape().getVertices()[i]), bus.camera);
                    RenderVec cords = getViewportCoordinates(new Vec4(actor.getShape().getVertices()[i], 1), bus.camera, model, view, projection);
                    drawVertices[i] = cords;
                }
                // Connects the draw vertices by a line
                bus.g2D.setColor(Color.black);
                drawConnectingLinesBoolean(drawVertices, actor.getShape().getDrawOrder(), bus.camera, model, view, projection, bus.g2D);
            }
        }
    }

    private static void drawConnectingLinesBoolean(RenderVec[] drawVertices, int[][] drawOrder, Camera camera, Mat4 model, Mat4 view, Mat4 projection, Graphics2D g2D) {
        for (int i = 0 ; i < drawOrder.length; i++) {
            for (int z = 0; z < drawOrder[i].length; z++) {
                RenderVec renderVecA = drawVertices[drawOrder[i][z]-1];
                RenderVec renderVecB = drawVertices[drawOrder[i][(z + 1) % drawOrder[i].length]-1];
                if (renderVecA != null && renderVecB != null) {
                    Vec3 drawPointA = renderVecA.getDrawVec();
                    Vec3 drawPointB = renderVecB.getDrawVec();
                    if ((drawPointA != null && drawPointB != null)) {
                        // True if only one point is behind the view (camera). If both are then it doesn't have to get drawn at all.
                        if (drawPointA.z == 0 ^ drawPointB.z == 0) {
                            RenderVec visibleVec;
                            RenderVec clippingVec;
                            if (drawPointA.z == 0) {
                                clippingVec = renderVecA;
                                visibleVec = renderVecB;
                            } else {
                                visibleVec = renderVecA;
                                clippingVec = renderVecB;
                            }
                            drawLineBasedOnIntersection(visibleVec, clippingVec, camera, model, view, projection, g2D);
                            continue;
                        } else if (drawPointA.z == 0) {
                            continue;
                        }
                        drawLine(new Vec2(drawPointA), new Vec2(drawPointB), g2D);
                    }
                }
            }
        }
    }

    // Draws the line based on where it would be if the end point was right where the camera stopped while preserving the angle/slope
    private static void drawLineBasedOnIntersection(RenderVec visibleVec, RenderVec clippingVec, Camera camera, Mat4 model, Mat4 view, Mat4 projection, Graphics2D g2D) {
        Vec3 clippedTransformedVec = ClipVertexBasedOnViewIntersection(visibleVec, clippingVec, camera, model, view, projection);
        if (clippedTransformedVec != null) {
            Vec3 drawPointA = visibleVec.getDrawVec();
            if ((drawPointA != null)) {
                drawLine(new Vec2(drawPointA), new Vec2(clippedTransformedVec), g2D);
            }
        }
    }

    private static Vec3 ClipVertexBasedOnViewIntersection(RenderVec vecA, RenderVec vecB, Camera camera, Mat4 model, Mat4 view, Mat4 projection) {
        Vec4 viewPointA = NMath.MultiplyVec4ByMat4(NMath.MultiplyVec4ByMat4(vecA.getWorldVec(), model), view);
        Vec4 viewPointB = NMath.MultiplyVec4ByMat4(NMath.MultiplyVec4ByMat4(vecB.getWorldVec(), model), view);
        Vec4 clippedVec = IntersectionBetweenLineAndPlane(viewPointA, viewPointB);
        clippedVec = NMath.MultiplyVec4ByMat4(clippedVec, projection);
        return getViewportCoordinates(clippedVec, camera);
    }

    private static void drawLine(Vec2 pointA, Vec2 pointB, Graphics2D g2D) {
        g2D.drawLine((int)pointA.x, (int)pointA.y, (int)pointB.x, (int)pointB.y);
    }

    private static Vec3 toNormalizedDeviceCoordinates(Vec4 cord) {
        return new Vec3(cord.x/cord.w, cord.y/cord.w, cord.z/cord.w);
    }

    private static Vec2 NDCToWindow(Vec3 ndc, Camera camera) {
        int xCord = (int) ((ndc.x * camera.getWidth() + camera.getWidth())/2);
        int yCord = (int) ((ndc.y * camera.getHeight() + camera.getHeight())/2);
        return new Vec2(xCord, yCord);
    }

    private static RenderVec getViewportCoordinates(Vec4 vertex, Camera camera, Mat4 model, Mat4 view, Mat4 projection) {
        Vec4 outputVertex = NMath.MultiplyVec4ByMat4(NMath.MultiplyVec4ByMat4(NMath.MultiplyVec4ByMat4(vertex, model), view), projection);
        return new RenderVec(getViewportCoordinates(outputVertex, camera), vertex);
    }

    // This version takes in an already fully transformed vertex up until the projection coordinate system
    private static Vec3 getViewportCoordinates(Vec4 transformedVertex, Camera camera) {
        transformedVertex = NMath.Add(transformedVertex, new Vec4(0, 0, 0, 0.01f));
        Vec3 output = toNormalizedDeviceCoordinates(transformedVertex);
        int zValue = 1; // Successful
        if (Float.isInfinite(output.z)) {
            System.out.println(transformedVertex);
            return null;
        }
        if (output.z > 1) {
            zValue = 0;
        }
        return new Vec3(NDCToWindow(output, camera), zValue);
    }


    // Assumes the plane is always filling the x and y-axis (used exclusively for view plane intersection in view space)
    public static Vec4 IntersectionBetweenLineAndPlane(Vec4 p1, Vec4 p2) {
        float z = 0;
        float t = (z - p1.z) / (p2.z - p1.z);
        float x = p1.x + t * (p2.x - p1.x);
        float y = p1.y + t * (p2.y - p1.y);
        return new Vec4(x, y, z, 1);
    }

}
