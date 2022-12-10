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

    // Loses bottom row and right column
    public static Mat3 TrimMat4ToMat3(Mat4 mat) {
        float[] top = Arrays.copyOfRange(mat.getRow(0), 0, 3);
        float[] middle = Arrays.copyOfRange(mat.getRow(1), 0, 3);
        float[] bottom = Arrays.copyOfRange(mat.getRow(2), 0, 3);
        return new Mat3(new float[][]{top, middle, bottom});
    }

    public static Mat4 GetPerspectiveProjectionMatrix_Vulkan(Camera camera) {
        float bottom = (float) (camera.getNear() * Math.tan(camera.getvFOV()/2));
        float right = (float) (camera.getNear() * camera.getWidth()/camera.getHeight() * Math.tan(camera.getvFOV()/2));
        return new Mat4(new float[][] {
                {camera.getNear()/right, 0, 0, 0},
                {0, camera.getNear()/bottom, 0, 0},
                {0, 0, camera.getFar()/(camera.getFar()- camera.getNear()), -camera.getFar()*camera.getNear()/(camera.getFar()- camera.getNear())},
                {0, 0, 1, 0}
        });
    }

    public static Mat4 GetPerspectiveProjectionMatrix_OpenGL(float near, float far, float fov, int width, int height) {
        float top = (float) -(near * Math.tan(fov/2));
        float right = (float) (near * width/height * Math.tan(fov/2));
        return new Mat4(new float[][] {
                {near/right, 0, 0, 0},
                {0, near/top, 0, 0},
                {0, 0, -(far + near)/(far-near), -2*far*near/(far - near)},
                {0, 0, -1, 0}
        });
    }

    public static Mat4 GetWorldToCameraSpaceConversionMatrix(Camera camera) {
        Vec3 lookAt = NMath.Normalize(new Vec3(NMath.MultiplyVec4ByMat4(camera.getForwardReference(), NMath.MultiplyMat4(NMath.MultiplyMat4(Utility.GetRotationMatrixX(camera.getRotation().x), Utility.GetRotationMatrixY(camera.getRotation().y)), Utility.GetRotationMatrixZ(camera.getRotation().z)))));;
        Vec3 right = NMath.CrossProduct(lookAt, new Vec3(0, 1, 0));
        Vec3 up = NMath.CrossProduct(right, lookAt);
        return new Mat4(new float[][] {
                {right.x, right.y, right.z, 0},
                {up.x, up.y, up.z, 0},
                {lookAt.x, lookAt.y, lookAt.z, 0},
                {0, 0, 0, 1}
        });
    }

    public static Mat4 GetModelMatrix(Actor3D actor) {
        Mat4 rotationMatrix = NMath.MultiplyMat4(GetRotationMatrixZ(actor.getOrientation().z), NMath.MultiplyMat4(GetRotationMatrixX(actor.getOrientation().x), GetRotationMatrixY(actor.getOrientation().y)));
        Mat4 translationMatrix = new Mat4(new float[][] {
                {1, 0, 0, actor.getPos().x},
                {0, 1, 0, actor.getPos().y},
                {0, 0, 1, actor.getPos().z},
                {0, 0, 0, 1}
        });
        return NMath.MultiplyMat4(rotationMatrix, translationMatrix);
    }

}
