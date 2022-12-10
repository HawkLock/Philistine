package Utility.Math;

public class Mat3 {

    float[][] elements;

    // Values must fit into a 3x3 matrix
    public Mat3() {
        elements = new float[3][3];
    }

    public Mat3(float[][] values) {
        if (values.length != 3 || values[0].length != 3) {
            throw new ArithmeticException();
        }
        elements = new float[3][3];
        for (int i = 0; i < values.length; i++) {
            for (int z = 0; z < values[0].length; z++) {
                elements[i][z] = values[i][z];
            }
        }
    }

    public float[] getRow(int index) {
        float[] returnArr = new float[3];
        for (int i = 0; i < 3; i++) {
            returnArr[i] = elements[index][i];
        }
        return returnArr;
    }

    public float[] getColumn(int index) {
        float[] returnArr = new float[3];
        for (int i = 0; i < 3; i++) {
            returnArr[i] = elements[i][index];
        }
        return returnArr;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < 3; i++) {
            str += "[";
            for (int z = 0; z < 3; z++) {
                str += z < 2 ? elements[i][z] + ", " : elements[i][z] + "]\n";
            }
        }
        return str;
    }

}
