package Core.EngineObjects.World;

import Core.EngineObjects.Shape;
import Utility.Math.Vec3;

import java.util.Arrays;
import java.util.Random;

public class ProceduralWorldGenerator {

    public static Shape GenerateMap(int width, int height, float scale) {
        Vec3[] vertices = GenerateMapVertices(GenerateHeightMap(width, height), scale);
        int[][] drawOrder = GenerateMapDrawOrder(width, height, vertices);
        System.out.println(Arrays.deepToString(drawOrder));
        return new Shape(vertices, drawOrder);
    }

    public static float[][] GenerateHeightMap(int width, int height) {
        float[][] heightMap = new float[height][width];
        Random rand = new Random();
        for (int i = 0; i < height; i++) {
            for (int z = 0; z < width; z++) {
                heightMap[i][z] = rand.nextFloat(0, 10);
            }
        }
        return heightMap;
    }

    public static Vec3[] GenerateMapVertices(float[][] heightMap, float scale) {
        Vec3[] vertices = new Vec3[heightMap.length * heightMap[0].length];

        int currentIndex = 0; // Keeps track of the vertices index
        for (int z = 0; z < heightMap.length; z++) {
            for (int x = 0; x < heightMap[z].length; x++) {
                vertices[currentIndex] = new Vec3(x*scale, heightMap[z][x], z*scale);
                currentIndex++;
            }
        }

        return vertices;
    }

    // Stored the same as with a .obj file face. All faces are stored as triangles for the map
    public static int[][] GenerateMapDrawOrder(int width, int height, Vec3[] vertices) {
        int[][] drawOrder = new int[(width-1)*(height-1)][];
        // Doesn't have to attempt triangle construction on the far right or very bottom
        int index = 0;
        int vertexIndex = 1;
        for (int y = 0; y < height-1; y++) {
            for (int x = 0; x < width-1; x++) {
                drawOrder[index] = new int[]{vertexIndex, vertexIndex+width+1, vertexIndex+width};
                vertexIndex++;
                index++;
            }
        }
        return drawOrder;
    }

}
