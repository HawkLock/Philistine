package Core;

import Core.EngineObjects.Shape;
import Utility.Math.Vec3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ModelProcessor {

    public static Shape GetShapeFromObj(String path) throws FileNotFoundException {
        return new Shape(GetVerticesFromObj(path), GetFacesVerticesOnly(path));
    }

    public static Vec3[] GetVerticesFromObj(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        // Gets number of vertices
        ArrayList<Vec3> vertices = new ArrayList<>();
        while (scanner.hasNextLine()) {
            // Checks if the current line should contain a vertex coordinate
            String line = scanner.nextLine();
            if (line.startsWith("v ")) {
                float[] pos = ParseFloatsFromString(line.substring(2));
                vertices.add(new Vec3(pos[0], pos[2], -pos[1]));
            }
        }
        scanner.close();
        // Converts arraylist of vertices into easier to use array
        return convertArraylistToArrayVec3(vertices);
    }

    public static int[][] GetFacesVerticesOnly(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        // Gets number of vertices
        ArrayList<int[]> order = new ArrayList<>();
        while (scanner.hasNextLine()) {
            // Checks if the current line should contain a vertex coordinate
            String line = scanner.nextLine();
            if (line.startsWith("f ")) {
                order.add(ParseOrderFromString(line.substring(2)));
            }
        }
        scanner.close();
        // Converts arraylist of vertices into easier to use array
        return convertArraylistToArrayArr(order);
    }

    public static int[][] GetFacesWithTexturesAndNormals(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        // Gets number of vertices
        ArrayList<int[]> order = new ArrayList<>();
        ArrayList<int[]> textures = new ArrayList<>();
        ArrayList<int[]> normals = new ArrayList<>();
        while (scanner.hasNextLine()) {
            // Checks if the current line should contain a vertex coordinate
            String line = scanner.nextLine();
            if (line.startsWith("f ")) {
                order.add(ParseOrderFromString(line.substring(2)));
            }
        }
        scanner.close();
        // Converts arraylist of vertices into easier to use array
        return convertArraylistToArrayArr(order);
    }
    /*
    private static int[] ParseAllFaceDataFromString(String str) {
        String[] spaceSplit = str.split(" ");
        ArrayList<int[]>
        for (int i = 0; i < spaceSplit.length; i++) {
            String[] data = spaceSplit[i].split("/");

        }
        return order;
    }
     */

    private static int[] ParseOrderFromString(String str) {
        // Splits into the order
        String[] spaceSplit = str.split(" ");
        int[] order = new int[spaceSplit.length];
        for (int i = 0; i < spaceSplit.length; i++) {
            order[i] = Integer.parseInt(spaceSplit[i]);
        }
        return order;
    }

    // Floats must be separated by a space
    private static float[] ParseFloatsFromString(String str) {
        String[] stringValues = str.split(" ");
        float[] values = new float[stringValues.length];
        for (int i = 0; i < stringValues.length; i++) {
            values[i] = Float.parseFloat(stringValues[i]);
        }
        return values;
    }

    private static Vec3[] convertArraylistToArrayVec3(ArrayList<Vec3> list) {
        Vec3[] arr = new Vec3[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    private static int[][] convertArraylistToArrayArr(ArrayList<int[]> list) {
        int[][] arr = new int[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        System.out.println(Arrays.deepToString(arr));
        return arr;
    }

}
