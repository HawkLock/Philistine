package Core.Rendering;

import Core.Camera;
import Utility.Math.*;
import Utility.Utility;

import java.awt.*;
import java.util.ArrayList;

public class Polygon implements Comparable<Polygon> {
    private int[][] screenCords;
    private float distanceFromCamera;
    private Color polygonColor;

    public Polygon(int[][] renderCords, Mat4 camera, ArrayList<RenderVec> renderVecs, Mat4 model, Color color) {
        screenCords = renderCords;
        distanceFromCamera = getShortestDistanceFromCamera(camera, renderVecs, model);
        polygonColor = color;
    }

    public Polygon(int[][] renderCords, Mat4 camera, RenderVec[] renderVecs, Mat4 model, Color color) {
        screenCords = renderCords;
        distanceFromCamera = getShortestDistanceFromCamera(camera, renderVecs, model);
        polygonColor = color;
    }

    private float getShortestDistanceFromCamera(Mat4 camera, ArrayList<RenderVec> renderVecs, Mat4 model) {
        float shortestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < renderVecs.size(); i++) {
            Vec4 testVec = NMath.MultiplyVec4ByMat4(NMath.MultiplyVec4ByMat4(renderVecs.get(i).getWorldVec(), model), camera);
            float newDistance = testVec.z;
            if (newDistance < shortestDistance) {
                shortestDistance = newDistance;
            }
        }
        return shortestDistance;
    }

    private float getShortestDistanceFromCamera(Mat4 camera, RenderVec[] renderVecs, Mat4 model) {
        float shortestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < renderVecs.length; i++) {
            Vec4 testVec = NMath.MultiplyVec4ByMat4(NMath.MultiplyVec4ByMat4(renderVecs[i].getWorldVec(), model), camera);
            float newDistance = testVec.z;
            if (newDistance < shortestDistance) {
                shortestDistance = newDistance;
            }
        }
        return shortestDistance;
    }

    public int[][] getScreenCords() {return screenCords;}
    public float getDistanceFromCamera() {return distanceFromCamera;}
    public Color getPolygonColor() {return polygonColor;}

    @Override
    public int compareTo(Polygon o) {
        return (int) (o.getDistanceFromCamera() - getDistanceFromCamera());
    }
}
