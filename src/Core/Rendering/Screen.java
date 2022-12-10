package Core.Rendering;

import javax.swing.*;
import java.awt.event.*;

public class Screen extends JFrame implements ActionListener, KeyListener, MouseWheelListener {

    public Game mainGame;

    static Timer timer;
    static int tickSpeed = 50; // In milliseconds
    static int currentTick; // Used for time keeping

    public Screen(RenderMode mode) {

        mainGame = new Game(mode);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(mainGame);
        this.pack();
        this.addKeyListener(this);
        this.addMouseWheelListener(this);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        timer = new Timer(tickSpeed, this);
        timer.start();
    }

    public Game getDisplay() {
        return mainGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentTick += 1; // For time keeping

        mainGame.Update(currentTick);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        mainGame.KeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        mainGame.KeyReleased(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mainGame.HandleMouseWheelInput(e);
    }
}