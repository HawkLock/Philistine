package Core.Input;

import Core.Rendering.Game;

import java.util.ArrayList;

public class InputSystem {

    ArrayList<VirtualKey> boundKeys;
    ArrayList<Runnable> boundFunctions = new ArrayList<>();

    private Game parent;

    public InputSystem(Game initParent) {
        parent = initParent;
    }


}
