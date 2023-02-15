package Utility.Math;

public class RenderVec {

    private Vec3 DrawVec; // Includes a z axis with whether it was behind the view
    private Vec4 WorldVec;

    public RenderVec(Vec3 dVec, Vec4 wVec) {
        DrawVec = dVec;
        WorldVec = wVec;
    }

    public Vec3 getDrawVec() {
        return DrawVec;
    }

    public void setDrawVec(Vec3 drawVec) {
        DrawVec = drawVec;
    }

    public Vec4 getWorldVec() {
        return WorldVec;
    }

    public Vec3 getWorldVec3() {
        return new Vec3(WorldVec);
    }

    public void setWorldVec(Vec4 worldVec) {
        WorldVec = worldVec;
    }
}
