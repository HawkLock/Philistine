package Core.EngineObjects;

import Utility.Math.NMath;
import Utility.Math.Vec3;

import java.awt.*;

public class Shape {

    private Vec3 position = new Vec3();
    private final Vec3[] vertices;
    private int[][] drawOrder;
    private float boundingRadius; // This radius is used for the simple (inaccurate) occlusion culling
    private Color[] polygonColors;

    public Shape(Vec3[] initialVertices, int[][] initialDrawOrder) {
        vertices = initialVertices;
        drawOrder = initialDrawOrder;
        CalculateBoundingRadius();
    }

    public Shape(Vec3[] initialVertices, int[][] initialDrawOrder, Color[] initialPolygonColors) {
        vertices = initialVertices;
        drawOrder = initialDrawOrder;
        CalculateBoundingRadius();
        polygonColors = initialPolygonColors;
    }

    // Could be moved to model processor for faster initialization
    private void CalculateBoundingRadius() {
        Vec3 center = new Vec3();
        float largestDistance = Integer.MIN_VALUE;
        for (int i = 0; i < vertices.length; i++) {
            float currentDistance = NMath.Distance(center, vertices[i]);
            if (currentDistance > largestDistance) {
                largestDistance = currentDistance;
            }
        }
        boundingRadius = largestDistance;
    }

    public void Move(Vec3 moveVector) {
        position = NMath.Add(position, moveVector);
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public int[][] getDrawOrder() {
        return drawOrder;
    }

    public Vec3[] getVertices() {
        return vertices;
    }

    public void Scale(float scalar) {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = NMath.Multiply(vertices[i], scalar);
        }
    }

    public float getBoundingRadius() {
        return boundingRadius;
    }

    public Color[] getPolygonColors() {
        return polygonColors;
    }
}
