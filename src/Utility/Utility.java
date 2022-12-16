package Utility;

import Core.Camera;
import Core.EngineObjects.Actor.Actor3D;
import Core.EngineObjects.Shape;
import Utility.Math.Mat3;
import Utility.Math.Mat4;
import Utility.Math.NMath;
import Utility.Math.Vec3;

import java.util.Arrays;

public class Utility {

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
                {cos, 0, -sin, 0},
                {0, 1, 0, 0},
                {sin, 0, cos, 0},
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

    public static Mat4 GetAdvancedRotationMatrix(float angle, Vec3 axis) {
        float cos = (float) Math.cos(NMath.toRadians(angle));
        float sin = (float) Math.sin(NMath.toRadians(angle));
        // Reference axis components
        float rX = axis.x;
        float rY = axis.y;
        float rZ = axis.z;
        return new Mat4(new double[][] {
                {cos+Math.pow(rX, 2)*(1-cos),   rX*rY*(1-cos)-rZ*sin,           rX*rZ*(1-cos)+rY*sin,               0},
                {rY*rX*(1-cos)+rZ*sin,          cos+Math.pow(rY, 2)*(1-cos),    rY*rZ*(1-cos)-rX*sin,               0},
                {rZ*rX*(1-cos)-rY*sin,          rZ*rY*(1-cos)+rX*sin,           cos+Math.pow(rZ, 2)*(1-cos),        0},
                {0,                             0,                              0,                                  1}
        });
    }

    // Loses bottom row and right column
    public static Mat3 TrimMat4ToMat3(Mat4 mat) {
        float[] top = Arrays.copyOfRange(mat.getRow(0), 0, 3);
        float[] middle = Arrays.copyOfRange(mat.getRow(1), 0, 3);
        float[] bottom = Arrays.copyOfRange(mat.getRow(2), 0, 3);
        return new Mat3(new float[][]{top, middle, bottom});
    }

    public static Mat4 GetPerspectiveProjectionMatrix_OpenGL(float near, float far, float fov, int width, int height) {
        float top = (float) -(near * Math.tan(NMath.toRadians(fov)/2));
        float right = (float) (near * width/height * Math.tan(NMath.toRadians(fov)/2));
        return new Mat4(new float[][] {
                {near/right, 0, 0, 0},
                {0, near/top, 0, 0},
                {0, 0, -(far + near)/(far-near), -2*far*near/(far - near)},
                {0, 0, -1, 0}
        });
    }

    public static Mat4 GetWorldToCameraSpaceConversionMatrix(Camera camera) {
        Vec3 lookAt = camera.getFront();
        Vec3 right = camera.getRight();
        Vec3 up = camera.getUp();
        Mat4 rotationMatrix = new Mat4(new float[][] {
                {right.x, up.x, lookAt.x, 0},
                {right.y, up.y, lookAt.y, 0},
                {right.z, up.z, lookAt.z, 0},
                {0, 0, 0, 1}
        });
        Mat4 translationMatrix = GetTranslationMatrix(camera.getPos().x, camera.getPos().y, camera.getPos().z);
        return NMath.MultiplyMat4(rotationMatrix, translationMatrix);

    }

    public static Mat4 GetModelMatrix(Actor3D actor) {
        Mat4 rotationMatrix = GetIdentityMatrix();
        rotationMatrix = NMath.MultiplyMat4(GetAdvancedRotationMatrix(actor.getOrientation().x, new Vec3(1, 0, 0)), rotationMatrix);
        rotationMatrix = NMath.MultiplyMat4(GetAdvancedRotationMatrix(actor.getOrientation().y, new Vec3(0, 1, 0)), rotationMatrix);
        rotationMatrix = NMath.MultiplyMat4(GetAdvancedRotationMatrix(actor.getOrientation().z, new Vec3(0, 0, 1)), rotationMatrix);
        Mat4 translationMatrix = new Mat4(new float[][] {
                {1, 0, 0, actor.getPos().x},
                {0, 1, 0, actor.getPos().y},
                {0, 0, 1, actor.getPos().z},
                {0, 0, 0, 1}
        });
        return NMath.MultiplyMat4(rotationMatrix, translationMatrix);
    }

}
