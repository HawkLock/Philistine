package Utility.Math;

import Utility.Utility;

public class Mat4 {

    float[][] elements;

    // Values must fit into a 3x3 matrix
    public Mat4() {
        elements = new float[4][4];
    }

    public Mat4(float[][] values) {
        if (values.length != 4 || values[0].length != 4) {
            throw new ArithmeticException();
        }
        elements = new float[4][4];
        for (int i = 0; i < values.length; i++) {
            for (int z = 0; z < values[0].length; z++) {
                elements[i][z] = values[i][z];
            }
        }
    }

    public Mat4(double[][] values) {
        if (values.length != 4 || values[0].length != 4) {
            throw new ArithmeticException();
        }
        elements = new float[4][4];
        for (int i = 0; i < values.length; i++) {
            for (int z = 0; z < values[0].length; z++) {
                elements[i][z] = (float) values[i][z];
            }
        }
    }

    public float[] getRow(int index) {
        float[] returnArr = new float[4];
        for (int i = 0; i < 4; i++) {
            returnArr[i] = elements[index][i];
        }
        return returnArr;
    }

    public float[] getColumn(int index) {
        float[] returnArr = new float[4];
        for (int i = 0; i < 4; i++) {
            returnArr[i] = elements[i][index];
        }
        return returnArr;
    }

    public void Negate() {
        for (int i = 0; i < elements.length; i++) {
            for (int z = 0; z < elements[i].length; z++) {
                elements[i][z] = -elements[i][z];
            }
        }
    }

    public float[][] getElements() {
        return elements;
    }

    public void set(int column, int row, float value) {
        elements[row][column] = value;
    }

    public static Mat4 lookAt(Vec3 eye, Vec3 center, Vec3 up) {
        Vec3 f = NMath.Normalize(NMath.Subtract(center, eye));
        Vec3 u = NMath.Normalize(up);
        Vec3 s = NMath.Normalize(NMath.CrossProduct(f, u));
        u = NMath.CrossProduct(s, f);

        Mat4 rotation = new Mat4();
        rotation.set(0,0,s.x);
        rotation.set(1,0,s.y);
        rotation.set(2,0,s.z);
        rotation.set(0,1,u.x);
        rotation.set(1,1,u.y);
        rotation.set(2,1,u.z);
        rotation.set(0,2,-f.x);
        rotation.set(1,2,-f.y);
        rotation.set(2,2,-f.z);

        Mat4 translation = Utility.GetTranslationMatrix(-eye.x, -eye.y, -eye.z);
        return NMath.MultiplyMat4(rotation, translation);
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < 4; i++) {
            str += "[";
            for (int z = 0; z < 4; z++) {
                str += z < 3 ? elements[i][z] + ", " : elements[i][z] + "]\n";
            }
        }
        return str;
    }

}
