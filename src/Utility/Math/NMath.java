package Utility.Math;

public class NMath {

    //
    //** NORMAL MATH **//
    //
    public static float toRadians(float degrees) {
        return (float) (degrees * (Math.PI/180));
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        } else if (value > max){
            return max;
        } else {
            return value;
        }
    }

    //
    //** VECTOR MATH **//
    //
    public static Vec3 Add(Vec3 vecA, Vec3 vecB) {
        return new Vec3(vecA.x + vecB.x, vecA.y + vecB.y, vecA.z + vecB.z);
    }

    public static Vec3 Subtract(Vec3 vecA, Vec3 vecB) {
        return new Vec3(vecA.x - vecB.x, vecA.y - vecB.y, vecA.z - vecB.z);
    }

    public static Vec3 Multiply(Vec3 vecA, Vec3 vecB) {
        return new Vec3(vecA.x * vecB.x, vecA.y * vecB.y, vecA.z * vecB.z);
    }

    public static Vec3 Multiply(Vec3 vecA, float factorToMulti) {
        return new Vec3(vecA.x * factorToMulti, vecA.y * factorToMulti, vecA.y * factorToMulti);
    }

    public static Vec3 Divide(Vec3 vecA, Vec3 vecB) {
        return new Vec3(vecA.x / vecB.x, vecA.y / vecB.y, vecA.z / vecB.z);
    }

    public static Vec3 Divide(Vec3 vecA, float factorToDiv) {
        return new Vec3(vecA.x / factorToDiv, vecA.y / factorToDiv, vecA.z / factorToDiv);
    }

    public static Vec3 Pow(Vec3 vecA, int pow) {
        Vec3 tempVec = vecA;
        for (int i = 0; i < pow; i++) {
            tempVec = Multiply(tempVec, vecA);
        }
        return tempVec;
    }

    public static float GetLength(Vec3 vecA) {
        return (float) Math.sqrt(vecA.x*vecA.x + vecA.y*vecA.y + vecA.z*vecA.z);
    }

    public static Vec3 Normalize(Vec3 vecA) {
        return Divide(vecA, GetLength(vecA)); // Divides itself by its magnitude
    }

    public static float DotProduct(Vec3 vecA, Vec3 vecB) {
        return (vecA.x * vecB.x) + (vecA.y * vecB.y) + (vecA.z *vecB.z);
    }

    public static Vec3 CrossProduct(Vec3 a, Vec3 b) {
        return new Vec3(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }

    //
    //** LINE MATH **//
    //

    public static boolean areClockwise(Vec2 pointA, Vec2 pointB, Vec2 pointC) {
        return (pointC.y-pointA.y)*(pointB.x-pointA.x) > (pointB.y-pointA.y)*(pointC.x-pointA.x);
    }

    public static boolean lineLineIntersection(Line2 lineA, Line2 lineB) {
        return areClockwise(lineA.start, lineB.start, lineB.end) != areClockwise(lineA.end, lineB.start, lineB.end) &&
                areClockwise(lineA.start, lineA.end, lineB.start) != areClockwise(lineA.start, lineA.end, lineB.end);
    }

    public static boolean lineRectIntersection(Line2 lineA, Vec2 rectOrigin, int width, int height) {
        Line2 top = new Line2(rectOrigin, new Vec2(rectOrigin.x + width, rectOrigin.y));
        Line2 bottom = new Line2(new Vec2(rectOrigin.x, rectOrigin.y + height), new Vec2(rectOrigin.x + width, rectOrigin.y + height));
        Line2 left = new Line2(rectOrigin, new Vec2(rectOrigin.x, rectOrigin.y + height));
        Line2 right = new Line2(new Vec2(rectOrigin.x + width, rectOrigin.y), new Vec2(rectOrigin.x + width, rectOrigin.y + height));
        return lineLineIntersection(lineA, top) || lineLineIntersection(lineA, bottom) || lineLineIntersection(lineA, left) || lineLineIntersection(lineA, right);
    }

    //
    //** MATRIX MATH **//
    //

    private static float MultiplySubMatrix(float[] subA, float[] subB) {
        float value = 0;
        for (int i = 0; i < subA.length; i++) {
            value += subA[i] * subB[i];
        }
        return value;
    }

    public static Mat3 MultiplyMat3(Mat3 matA, Mat3 matB) {
        Mat3 newMat = new Mat3();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                // get row of matA, and get column of matB
                newMat.elements[y][x] = MultiplySubMatrix(matA.getRow(y), matB.getColumn(x));
            }
        }
        return newMat;
    }

    public static Mat4 MultiplyMat4(Mat4 matA, Mat4 matB) {
        Mat4 newMat = new Mat4();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                // get row of matA, and get column of matB
                newMat.elements[y][x] = MultiplySubMatrix(matA.getRow(y), matB.getColumn(x));
            }
        }
        return newMat;
    }

    public static Vec4 MultiplyVec4ByMat4(Vec4 vec, Mat4 mat) {
        float[] subMat = {vec.x, vec.y, vec.z, vec.w}; // Converts vector to a sub-matrix for computation
        float[] values = new float[4];
        for (int y = 0; y < 4; y++) {
            values[y] = MultiplySubMatrix(subMat, mat.getRow(y));
        }
        return new Vec4(values[0], values[1], values[2], values[3]);
    }

    public static Vec3 MultiplyVec3ByMat3(Vec3 vec, Mat3 mat) {
        float[] subMat = {vec.x, vec.y, vec.z}; // Converts vector to a sub-matrix for computation
        float[] values = new float[3];
        for (int y = 0; y < 3; y++) {
            values[y] = MultiplySubMatrix(subMat, mat.getRow(y));
        }
        return new Vec3(values[0], values[1], values[2]);
    }

    //
    //** ORIENTATION MATH **//
    //

    public static Orientation Add(Orientation rotA, Orientation rotB) {
        return new Orientation(clamp(rotA.x + rotB.x, -180, 180), (rotA.y + rotB.y) % 360, clamp(rotA.z + rotB.z, -90, 90));
    }

}
