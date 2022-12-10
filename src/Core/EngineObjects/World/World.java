package Core.EngineObjects.World;

import Core.EngineObjects.Actor.Actor;
import Utility.Math.Vec2;

import java.util.ArrayList;

public class World {

    private ArrayList<Actor> objects = new ArrayList<>();

    public World() {

    }

    public void UpdateCollisions() {
        for (int a = 0; a < objects.size(); a++) {
            if (objects.get(a) != null && objects.get(a).getCollisionComponent() != null) {
                for (int b = a + 1; b < objects.size(); b++) {
                    // Check collision
                    if (CheckCollision(objects.get(a), objects.get(b))) {
                        System.out.println("Collision");
                    }
                }
            }
        }
    }
    private boolean CheckCollision(Actor actorA, Actor actorB) {
        // Index 0 = X, Index 1 = Y, Index 2 = Z && x = min, y = max
        Vec2[] extremesA = actorA.getCollisionComponent().getCoordinateExtremes();
        Vec2[] extremesB = actorB.getCollisionComponent().getCoordinateExtremes();
        return extremesA[0].x <= extremesB[0].y && extremesA[0].y >= extremesB[0].x &&
                extremesA[1].x <= extremesB[1].y && extremesA[1].y >= extremesB[1].x &&
                extremesA[2].x <= extremesB[2].y && extremesA[2].y >= extremesB[2].x;
    }

    public void AddActor(Actor actor) {
        objects.add(actor);
    }

    public ArrayList<Actor> getObjects() {
        return objects;
    }
}
