package Core.EngineObjects.World;

import Core.EngineObjects.Shape;
import Utility.Math.Vec3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class ProceduralWorldGenerator {

    // The (index+1)*amplitude is the height
    private static Color[] heightColors = {
            Color.BLUE,
            Color.GREEN,
            new Color(80, 60, 20),
            Color.DARK_GRAY,
            Color.WHITE
    };

    public static Shape GenerateMap(int width, int height, float scale, float amplitude, float maxHeight) {
        //float[][] heightMap = GenerateHeightMapFromPicture("src/Assets/References/Perlin Noise Low Res.png");
        float[][] heightMap = GenerateHeightMap(width, height, maxHeight);
        Vec3[] vertices = GenerateMapVertices(heightMap, scale, amplitude);
        Color[] polygonColors = new Color[(width-1)*(height-1)*2];
        int[][] drawOrder = GenerateMapDrawOrder(width, height, vertices, polygonColors);
        return new Shape(vertices, drawOrder, polygonColors);
    }

    public static Shape GenerateMapFromNoiseInput(String path, float amplitude, float maxHeight) {
        BufferedImage img = getImageFromPath(path);
        assert img != null;
        float[][] heightMap = GenerateHeightMapFromPicture(img, amplitude, maxHeight);
        Vec3[] vertices = GenerateMapVertices(heightMap, 1, amplitude);
        int width = img.getWidth();
        int height = img.getHeight();
        Color[] polygonColors = new Color[(width-1)*(height-1)*2];
        int[][] drawOrder = GenerateMapDrawOrder(width, height, vertices, polygonColors);
        return new Shape(vertices, drawOrder, polygonColors);
    }

    public static float[][] GenerateHeightMap(int width, int height, float maxHeight) {
        float[][] heightMap = new float[height][width];
        Random rand = new Random();
        for (int i = 0; i < height; i++) {
            for (int z = 0; z < width; z++) {
                heightMap[i][z] = rand.nextFloat(0, maxHeight);
            }
        }
        return heightMap;
    }

    public static float[][] GenerateHeightMapFromPicture(BufferedImage img, float amplitude, float maxHeight) {

        float[][] heightMap = new float[img.getHeight()][img.getWidth()];

        for (int z = 0; z < img.getHeight(); z++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color pixelColor = new Color(img.getRGB(x, z));
                int height = (int) ((pixelColor.getRed()/50-1) * amplitude);
                height = (int) Math.min(height, maxHeight);
                heightMap[z][x] = height;
            }
        }

        return heightMap;
    }

    public static Vec3[] GenerateMapVertices(float[][] heightMap, float scale, float amplitude) {
        Vec3[] vertices = new Vec3[heightMap.length * heightMap[0].length];

        int currentIndex = 0; // Keeps track of the vertices index
        for (int z = 0; z < heightMap.length; z++) {
            for (int x = 0; x < heightMap[z].length; x++) {
                vertices[currentIndex] = new Vec3(x*scale, heightMap[z][x] * amplitude, z*scale);
                currentIndex++;
            }
        }

        return vertices;
    }

    // Stored the same as with a .obj file face. All faces are stored as triangles for the map
    public static int[][] GenerateMapDrawOrder(int width, int height, Vec3[] vertices, Color[] polygonColors) {
        int[][] drawOrder = new int[(width-1)*(height-1)*2][];
        // Doesn't have to attempt triangle construction on the far right or very bottom
        int index = 0;
        int vertexIndex = 1;
        for (int y = 0; y < height-1; y++) {
            for (int x = 0; x < width-1; x++) {
                //drawOrder[index] = new int[]{vertexIndex, vertexIndex+width+1, vertexIndex+width};
                ConstructPolygon(drawOrder, index, new int[]{vertexIndex, vertexIndex+width+1, vertexIndex+width}, vertices, polygonColors);
                index++;
                //drawOrder[index] = new int[]{vertexIndex+width+1, vertexIndex, vertexIndex+1};
                ConstructPolygon(drawOrder, index, new int[]{vertexIndex+width+1, vertexIndex, vertexIndex+1}, vertices, polygonColors);
                index++;
                vertexIndex++;
            }
        }
        return drawOrder;
    }

    public static void ConstructPolygon(int[][] drawOrder, int orderIndex, int[] vertexIndices, Vec3[] vertices, Color[] polygonColors) {
        drawOrder[orderIndex] = vertexIndices;
        float averageHeight = 0;
        for (int i = 0; i < vertexIndices.length; i++) {
            averageHeight += vertices[vertexIndices[i]].y;
        }
        // Assign polygon color based on the height of the polygon's highest vertices
        Color polygonColor;
        averageHeight = (int)averageHeight;
        if (averageHeight >= heightColors.length) {
            polygonColor = heightColors[heightColors.length-1];
        } else if (averageHeight < 0) {
            polygonColor = heightColors[0];
        }
        else {
            polygonColor = heightColors[(int) averageHeight];
        }
        polygonColors[orderIndex] = polygonColor;
    }

    private static BufferedImage getImageFromPath(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.printf("Error Getting Input Image: %s\n", path);
        }
        return null;
    }

}
