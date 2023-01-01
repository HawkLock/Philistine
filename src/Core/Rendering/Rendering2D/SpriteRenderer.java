package Core.Rendering.Rendering2D;

import Core.Camera;
import Utility.Math.NMath;
import Utility.Math.Vec2;
import Utility.Math.Vec3;
import Core.EngineObjects.Actor.Actor2D;

import javax.swing.*;
import java.awt.*;

public class SpriteRenderer {

    private final String spriteSource;
    private Image sprite;
    private Vec2 spriteDimensions;
    private final Actor2D parent;

    public SpriteRenderer(String spritePath, Actor2D parentActor) {
        parent = parentActor;
        spriteSource = spritePath;
        InitializeSprite();
    }

    private void InitializeSprite() {
        if (spriteSource != null) {
            ImageIcon icon = new ImageIcon(spriteSource);
            sprite = icon.getImage();
            spriteDimensions = new Vec2(icon.getIconWidth(), icon.getIconHeight());
        }
    }

    public void Render(Camera camera, Graphics2D g2D) {
        int[] pos = positionToScreenSpace(parent.pos, camera);
        int[] size = sizeToCameraDistance(parent.pos, camera);
        g2D.drawImage(sprite, pos[0], pos[1], size[0], size[1], null);
    }

    private int[] positionToScreenSpace(Vec3 position, Camera camera) {
        // Gets their position based off of the projection rendering formula
        float xCord = NMath.Subtract(position, camera.getPos()).x * (camera.getFocalLength() / Math.abs(NMath.Subtract(position, camera.getPos()).z));
        float yCord = NMath.Subtract(position, camera.getPos()).y * (camera.getFocalLength() / Math.abs(NMath.Subtract(position, camera.getPos()).z)); // Transforms the coordinates to the screen dimensions
        xCord = (xCord * camera.getWidth() + camera.getWidth()) / 2 - spriteDimensions.x/2;
        yCord = (yCord * camera.getHeight() + camera.getHeight()) /2 - spriteDimensions.y/2;
        return new int[] {(int) xCord, (int) yCord};
    }

    private int[] sizeToCameraDistance(Vec3 position, Camera camera) {
        // Gets distance from camera to actor
        float sizeFactor = NMath.Subtract(position, camera.getPos()).z;
        return new int[] {(int) (spriteDimensions.x / sizeFactor), (int) (spriteDimensions.y / sizeFactor)};
    }

    public void setSpriteDimensions(Vec2 spriteDimensions) {
        this.spriteDimensions = spriteDimensions;
    }
    public Vec2 getSpriteDimensions() {
        return spriteDimensions;
    }

    public Image getSprite() {
        return sprite;
    }
    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }
}
