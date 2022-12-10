package Utility;

import Core.EngineObjects.Shape;
import Utility.Math.Mat3;
import Utility.Math.Mat4;
import Utility.Math.NMath;
import Utility.Math.Vec3;

public class Utility {

    public static Vec3[] GetTriangleVertices() {
        return new Vec3[] {
                new Vec3(-0.5f, -0.5f, 0.0f),
                new Vec3(0.0f, 0.5f, 0.0f),
                new Vec3(0.5f, -0.5f, 0.0f)
        };
    }

    public static Vec3[] GetSquareVertices() {
        return new Vec3[] {
                new Vec3(0.5f,  0.5f, 0.0f),
                new Vec3(0.5f, -0.5f, 0.0f),
                new Vec3(-0.5f,  0.5f, 0.0f),
                new Vec3(0.5f, -0.5f, 0.0f),
                new Vec3(-0.5f, -0.5f, 0.0f),
                new Vec3(-0.5f,  0.5f, 0.0f)
        };
    }

    public static Vec3[] GetCubeVertices() {
        return new Vec3[]{
                new Vec3(-0.5f, -0.5f, -0.5f),
                new Vec3(0.5f, -0.5f, -0.5f),
                new Vec3(0.5f, 0.5f, -0.5f),
                new Vec3(0.5f, 0.5f, -0.5f),
                new Vec3(-0.5f, 0.5f, -0.5f),
                new Vec3(-0.5f, -0.5f, -0.5f),

                new Vec3(-0.5f, -0.5f, 0.5f),
                new Vec3(0.5f, -0.5f, 0.5f),
                new Vec3(0.5f, 0.5f, 0.5f),
                new Vec3(0.5f, 0.5f, 0.5f),
                new Vec3(-0.5f, 0.5f, 0.5f),
                new Vec3(-0.5f, -0.5f, 0.5f),

                new Vec3(-0.5f, 0.5f, 0.5f),
                new Vec3(-0.5f, 0.5f, -0.5f),
                new Vec3(-0.5f, -0.5f, -0.5f),
                new Vec3(-0.5f, -0.5f, -0.5f),
                new Vec3(-0.5f, -0.5f, 0.5f),
                new Vec3(-0.5f, 0.5f, 0.5f),

                new Vec3(0.5f, 0.5f, -0.5f),
                new Vec3(0.5f, -0.5f, -0.5f),
                new Vec3(0.5f, -0.5f, -0.5f),
                new Vec3(0.5f, -0.5f, -0.5f),
                new Vec3(0.5f, -0.5f, 0.5f),
                new Vec3(0.5f, 0.5f, 0.5f),

                new Vec3(-0.5f, -0.5f, -0.5f),
                new Vec3(0.5f, -0.5f, -0.5f),
                new Vec3(0.5f, -0.5f,  0.5f),
                new Vec3(0.5f, -0.5f, 0.5f),
                new Vec3(-0.5f, -0.5f, 0.5f),
                new Vec3(-0.5f, -0.5f, -0.5f),

                new Vec3(-0.5f, 0.5f, -0.5f),
                new Vec3(0.5f, 0.5f, -0.5f),
                new Vec3(0.5f, 0.5f, 0.5f),
                new Vec3(0.5f, 0.5f, 0.5f),
                new Vec3(-0.5f, 0.5f, 0.5f),
                new Vec3(-0.5f, 0.5f, -0.5f)
        };
    }

    public static Mat4 GetIdentityMatrix() {
        return new Mat4(new float[][] {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        });
    }

    public static Mat4 GetScalingMatrix(float x, float y, float z) {
        return new Mat4(new float[][] {
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}
        });
    }

    public static Mat4 GetTranslationMatrix(float x, float y, float z) {
        return new Mat4(new float[][] {
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}
        });
    }

    public static Mat4 GetRotationMatrixX(float angle) {
        float cos = (float) Math.cos(NMath.toRadians(angle));
        float sin = (float) Math.sin(NMath.toRadians(angle));
        return new Mat4(new float[][] {
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        });
    }

    public static Mat4 GetRotationMatrixY(float angle) {
        float cos = (float) Math.cos(NMath.toRadians(angle));
        float sin = (float) Math.sin(NMath.toRadians(angle));
        return new Mat4(new float[][] {
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}
        });
    }

    public static Mat4 GetRotationMatrixZ(float angle) {
        float cos = (float) Math.cos(NMath.toRadians(angle));
        float sin = (float) Math.sin(NMath.toRadians(angle));
        return new Mat4(new float[][] {
                {cos, -sin, 0, 0},
                {sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public static Mat3 TrimMat4ToMat3(Mat4 mat) {
        return new Mat3(new float[][]{mat.getRow(0), mat.getRow(1), mat.getRow(2)});
    }

    public Shape GetShape(ShapeType shapeType) {
        switch (shapeType) {
            case TRIANGLE:
                return new Shape(GetTriangleVertices());
            case SQUARE:
                return new Shape(GetSquareVertices());
            case CUBE:
                return new Shape(GetCubeVertices());
            default:
                return null;
        }
    }

    public Shape GetShape(Vec3[] vertices) {
        return new Shape(vertices);
    }

    public Shape GetShape(Vec3[] vertices, int[][] drawOrder) {
        return new Shape(vertices, drawOrder);
    }

}
