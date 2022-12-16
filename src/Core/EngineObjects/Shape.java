package Core.EngineObjects;

import Utility.Axis;
import Utility.Math.NMath;
import Utility.Math.Vec3;
import Utility.Math.Vec4;
import Utility.Utility;

public class Shape {

    private Vec3 position = new Vec3();
    private final Vec3[] vertices;
    private int[][] drawOrder;

    public Shape(Vec3[] initialVertices) {
        vertices = initialVertices;
    }

    public Shape(Vec3[] initialVertices, int[][] initialDrawOrder) {
        vertices = initialVertices;
        drawOrder = initialDrawOrder;
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
}
