import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener {
    // Images
    private Image background;

    // Game objects
    private Bird bird;

    // Game loop timer (~60 FPS)
    private Timer gameTimer;

    public GamePanel() {
        setPreferredSize(new Dimension(FlappyBird.WIDTH, FlappyBird.HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        // Load assets
        background = new ImageIcon(getClass().getResource("assets/flappybirdbg.png")).getImage();

        // Create bird at horizontal 1/4, vertical center
        bird = new Bird(FlappyBird.WIDTH / 4, FlappyBird.HEIGHT / 2);

        // Game loop ~60 FPS
        gameTimer = new Timer(17, e -> {
            update();
            repaint();
        });
        gameTimer.start();
    }

    /** Update game state each frame */
    private void update() {
        // Apply gravity
        bird.applyGravity();

        // Clamp: bird cannot fly above the top
        if (bird.y < 0) {
            bird.y = 0;
            bird.velocity = 0;
        }

        // Clamp: bird cannot fall below the bottom (temporary until Bài 4 game over)
        if (bird.y + bird.height > FlappyBird.HEIGHT) {
            bird.y = FlappyBird.HEIGHT - bird.height;
            bird.velocity = 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.drawImage(background, 0, 0, FlappyBird.WIDTH, FlappyBird.HEIGHT, this);

        // Bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, this);
    }

    // ---- KeyListener ----
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER) {
            bird.jump();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
