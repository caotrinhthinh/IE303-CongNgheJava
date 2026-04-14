import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FlappyBird extends JFrame {
    // Window dimensions
    static final int WIDTH = 360;
    static final int HEIGHT = 640;

    public FlappyBird() {
        setTitle("Flappy Bird");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add game panel
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);

        // Request focus after making visible so KeyListener works
        gamePanel.requestFocusInWindow();
    }
}
