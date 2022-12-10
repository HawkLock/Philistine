package Core.EngineObjects;

import Utility.Axis;
import Utility.Math.NMath;
import Utility.Math.Vec3;
import Utility.Math.Vec4;
import Utility.Utility;

public class Shape {

    private Vec3 position = new Vec3();
    private Vec3[] vertices;
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

    public void Rotate(float angle, Axis axis) {
        switch (axis) {
            case X:
                for (int i = 0; i < vertices.length; i++) {
                    vertices[i] = new Vec3(NMath.MultiplyVec4ByMat4(new Vec4(vertices[i], 1), Utility.GetRotationMatrixX(angle)));
                }
                break;
            case Y:
                for (int i = 0; i < vertices.length; i++) {
                    vertices[i] = new Vec3(NMath.MultiplyVec4ByMat4(new Vec4(vertices[i], 1), Utility.GetRotationMatrixY(angle)));
                }
                break;
            case Z:
                for (int i = 0; i < vertices.length; i++) {
                    vertices[i] = new Vec3(NMath.MultiplyVec4ByMat4(new Vec4(vertices[i], 1), Utility.GetRotationMatrixZ(angle)));
                }
                break;
        }
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
}
