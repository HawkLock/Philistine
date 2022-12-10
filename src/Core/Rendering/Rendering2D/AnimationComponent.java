package Core.Rendering.Rendering2D;

import Core.EngineObjects.Actor.Actor2D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class AnimationComponent implements ActionListener {

    LinkedList<AnimationAsset> Animations;
    AnimationAsset CurrentAnimation;
    AnimationAsset DefaultAnimation;
    Actor2D BoundActor;

    static Timer timer;
    static int FPS = 6;
    static int tickSpeed = 1000 / FPS; // In milliseconds
    private int CurrentFrame;

    public AnimationComponent(Actor2D targetObject) {
        Animations = new LinkedList<>();

        BoundActor = targetObject;
        CurrentFrame = 0;

        timer = new Timer(tickSpeed, this);
        timer.start();
    }

    public void SetFPS(int newFPS) {
        FPS = newFPS;
        tickSpeed = 1000 / FPS;
        timer.setDelay(tickSpeed);
    }

    public void SetDefaultAnimation(String animName, boolean changeCurrent) {
        // Animation must already be in the component
        for (AnimationAsset anim : Animations) {
            if (anim.GetName() == animName) {
                DefaultAnimation = anim;
                if (changeCurrent) {
                    CurrentAnimation = DefaultAnimation;
                }
                return;
            }
        }
        // If it gets here then an animation with that name couldn't be found
        System.out.printf("Animation Error: Couldn't find animation named %s\n", animName);
    }

    public void SetCurrentAnimation(String animName) {
        if (animName != CurrentAnimation.GetName()) {
            for (AnimationAsset anim : Animations) {
                if (anim.GetName() == animName) {
                    CurrentAnimation = anim;
                    CurrentFrame = 0;
                    return;
                }
            }
            // If it gets here then an animation with that name couldn't be found
            System.out.printf("Animation Error: Couldn't find animation named %s\n", animName);
        }
    }

    public void AddAnimation(AnimationAsset newAnim) {
        // Checks to make sure there isn't already an animation with that name in the animations
        for (AnimationAsset anim : Animations) {
            if (anim.GetName() == newAnim.GetName()) {
                System.out.printf("Animation Error: Animation of name %s already exists\n", anim.GetName());
                return;
            }
        }
        // Can be added if it reaches here
        Animations.add(newAnim);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Called every frame of the animation
        if (CurrentAnimation != null && BoundActor.getSpriteRenderer() != null) {
            BoundActor.getSpriteRenderer().setSprite(CurrentAnimation.GetFrame(CurrentFrame));
            // Still has frames to render
            if (CurrentFrame + 1 < CurrentAnimation.GetFrameCount()) {
                CurrentFrame++;
            }
            // If animation can loop, it will reset frame to zero
            else if (CurrentAnimation.IsLooped()) {
                CurrentFrame = 0;
            } else {
                CurrentAnimation = DefaultAnimation; // Makes it the default animation if it can't do anything else
                CurrentFrame = 0;
            }
        }
    }
}
