package Core.Input;

import Core.Game;

import java.util.ArrayList;

public class InputSystem {

    ArrayList<VirtualKey> boundKeys;
    ArrayList<Runnable> boundFunctions = new ArrayList<>();

    private final Game parent;

    public InputSystem(Game initParent) {
        parent = initParent;
    }


}
