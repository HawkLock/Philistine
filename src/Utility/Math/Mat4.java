package Utility.Math;

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
