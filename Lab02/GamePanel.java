import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener {
    // ---- Constants ----
    static final int GAP = 150;            // gap between top and bottom pipe
    static final int PIPE_SPAWN_MS = 1500; // spawn a new pipe pair every 1.5s

    // ---- Images ----
    private Image background;

    // ---- Game objects ----
    private Bird bird;
    private java.util.List<Pipe> pipes = new ArrayList<>();

    // ---- Timers ----
    private javax.swing.Timer gameTimer;   // ~60 FPS update loop
    private javax.swing.Timer spawnTimer;  // pipe spawning

    // ---- Random ----
    private Random random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(FlappyBird.WIDTH, FlappyBird.HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        // Load assets
        background = new ImageIcon(getClass().getResource("assets/flappybirdbg.png")).getImage();

        // Create bird at horizontal 1/4, vertical center
        bird = new Bird(FlappyBird.WIDTH / 4, FlappyBird.HEIGHT / 2);

        // Game loop ~60 FPS
        gameTimer = new javax.swing.Timer(17, e -> {
            update();
            repaint();
        });
        gameTimer.start();

        // Pipe spawn timer
        spawnTimer = new javax.swing.Timer(PIPE_SPAWN_MS, e -> spawnPipePair());
        spawnTimer.start();
    }

    /** Spawn one pair of pipes (top + bottom) with randomised gap position */
    private void spawnPipePair() {
        // The bottom of the top pipe is randomised between 1/4 and 3/4 of screen height
        int topPipeBottomY = FlappyBird.HEIGHT / 4
                + random.nextInt(FlappyBird.HEIGHT / 2);

        // Get pipe natural height from a temp instance
        Pipe tempTop = new Pipe(FlappyBird.WIDTH, 0, true);
        int pipeH = tempTop.height;

        // Top pipe: positioned so its bottom edge is at topPipeBottomY
        Pipe top = new Pipe(FlappyBird.WIDTH, topPipeBottomY - pipeH, true);

        // Bottom pipe: starts right after the gap
        Pipe bottom = new Pipe(FlappyBird.WIDTH, topPipeBottomY + GAP, false);

        pipes.add(top);
        pipes.add(bottom);
    }

    /** Update game state each frame */
    private void update() {
        // Bird physics
        bird.applyGravity();

        // Clamp bird to top
        if (bird.y < 0) {
            bird.y = 0;
            bird.velocity = 0;
        }

        // Clamp bird to bottom (temporary – Bài 4 handles game over)
        if (bird.y + bird.height > FlappyBird.HEIGHT) {
            bird.y = FlappyBird.HEIGHT - bird.height;
            bird.velocity = 0;
        }

        // Move pipes & remove off-screen ones
        pipes.removeIf(Pipe::isOffScreen);
        for (Pipe pipe : pipes) {
            pipe.move();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.drawImage(background, 0, 0, FlappyBird.WIDTH, FlappyBird.HEIGHT, this);

        // Pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, this);
        }

        // Bird (drawn on top of pipes)
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
