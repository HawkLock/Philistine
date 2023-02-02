package Core.Rendering.Rendering3D;

import Core.Camera;
import Utility.Math.NMath;
import Utility.Math.RenderVec;
import Utility.Math.Vec3;

import java.awt.*;
import java.util.ArrayList;


public class Polygon implements Comparable<Polygon> {

    private RenderVec[] vertices;
    private Color color;
    private float distanceFromCamera;

    public Polygon(RenderVec[] initVertices, Color initColor, Camera camera) {
        vertices = initVertices;
        color = initColor;
        distanceFromCamera = getShortestDistance(camera);
    }

    public Polygon(ArrayList<RenderVec> initVertices, Color initColor, Camera camera) {
        vertices = initVertices.toArray(new RenderVec[0]);
        color = initColor;
        distanceFromCamera = getShortestDistance(camera);
    }

    private float getShortestDistance(Camera camera) {
        float shortest = Integer.MAX_VALUE;
        for (int i = 0; i < vertices.length; i++) {
            float dist = NMath.Distance(camera.getPos(), new Vec3(vertices[i].getWorldVec()));
            if (dist < shortest) {
                shortest = dist;
            }
        }
        return shortest;
    }

    public RenderVec[] getVertices() {
        return vertices;
    }

    public Color getColor() {
        return color;
    }

    public float getDistanceFromCamera() {
        return distanceFromCamera;
    }

    @Override
    public int compareTo(Polygon o) {
        if (distanceFromCamera < o.getDistanceFromCamera()) {
            return -1;
        } else if (o.getDistanceFromCamera() < distanceFromCamera) {
            return 1;
        } else {
            return 0;
        }
        //return (int) (distanceFromCamera-o.getDistanceFromCamera());
    }

}
