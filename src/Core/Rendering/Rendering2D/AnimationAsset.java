package Core.Rendering.Rendering2D;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AnimationAsset {

    private final String Name;
    private final Image[] Frames;
    private final int FrameCount;
    private final boolean bLooped;

    public AnimationAsset(String directory, String animName, boolean loopable) {
        // Get frames from supplied directory
        File dir = new File(directory);
        File[] children = dir.listFiles();
        if (children != null) {
            Frames = new Image[children.length];
            for (int i = 0; i < children.length; i++) {
                Frames[i] = new ImageIcon(children[i].getAbsolutePath()).getImage();
            }
        } else {
            Frames = new Image[0];
        }

        FrameCount = Frames.length;
        Name = animName;
        bLooped = loopable;
    }

    public String GetName() {
        return Name;
    }

    public Image GetFrame(int index) {
        return Frames[index];
    }

    public int GetFrameCount() {
        return FrameCount;
    }

    public boolean IsLooped() {
        return bLooped;
    }

}
