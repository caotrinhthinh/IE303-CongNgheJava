import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener {
    // ---- Constants ----
    static final int GAP = 150;             // vertical gap between top & bottom pipe
    static final int PIPE_SPAWN_MS = 1500;  // spawn a new pair every 1.5 s

    // ---- Images ----
    private Image background;

    // ---- Game objects ----
    private Bird bird;
    private java.util.List<Pipe> pipes = new ArrayList<>();

    // ---- Timers ----
    private javax.swing.Timer gameTimer;   // ~60 FPS update loop
    private javax.swing.Timer spawnTimer;  // pipe spawning

    // ---- Game state ----
    private int score = 0;
    private boolean gameOver = false;
    private boolean started = false;  // wait for first key press before game begins

    // ---- Random ----
    private Random random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(FlappyBird.WIDTH, FlappyBird.HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        // Load assets
        background = new ImageIcon(getClass().getResource("assets/flappybirdbg.png")).getImage();

        // Create bird
        resetBird();

        // Game loop ~60 FPS
        gameTimer = new javax.swing.Timer(17, e -> {
            update();
            repaint();
        });
        gameTimer.start();

        // Pipe spawn timer (starts only when game starts)
        spawnTimer = new javax.swing.Timer(PIPE_SPAWN_MS, e -> spawnPipePair());
    }

    // ---- Initialisation helpers ----

    private void resetBird() {
        bird = new Bird(FlappyBird.WIDTH / 4, FlappyBird.HEIGHT / 2);
    }

    private void startGame() {
        started = true;
        gameOver = false;
        score = 0;
        pipes.clear();
        resetBird();
        spawnTimer.start();
    }

    /** Spawn one pair of pipes with a randomised gap position */
    private void spawnPipePair() {
        // Bottom of the top pipe is randomised between 1/4 and 3/4 of screen height
        int topPipeBottomY = FlappyBird.HEIGHT / 4
                + random.nextInt(FlappyBird.HEIGHT / 2);

        // Get pipe natural height from a temp instance
        Pipe tempTop = new Pipe(FlappyBird.WIDTH, 0, true);
        int pipeH = tempTop.height;

        Pipe top    = new Pipe(FlappyBird.WIDTH, topPipeBottomY - pipeH, true);
        Pipe bottom = new Pipe(FlappyBird.WIDTH, topPipeBottomY + GAP,   false);

        pipes.add(top);
        pipes.add(bottom);
    }

    // ---- Update ----

    private void update() {
        if (!started || gameOver) return;

        // Bird physics
        bird.applyGravity();

        // Bird hits top
        if (bird.y < 0) {
            bird.y = 0;
            bird.velocity = 0;
        }

        // Bird hits bottom → game over
        if (bird.y + bird.height >= FlappyBird.HEIGHT) {
            triggerGameOver();
            return;
        }

        // Move pipes, remove off-screen, check collisions & score
        pipes.removeIf(Pipe::isOffScreen);
        Rectangle birdRect = bird.getBounds();

        for (Pipe pipe : pipes) {
            pipe.move();

            // Collision check
            if (birdRect.intersects(pipe.getBounds())) {
                triggerGameOver();
                return;
            }

        }

        // Score: bird fully passes a top pipe → +1
        for (Pipe pipe : pipes) {
            if (!pipe.passed && pipe.isTop && pipe.x + pipe.width < bird.x) {
                pipe.passed = true;
                score++;
            }
        }
    }

    private void triggerGameOver() {
        gameOver = true;
        spawnTimer.stop();
    }

    // ---- Paint ----

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g.drawImage(background, 0, 0, FlappyBird.WIDTH, FlappyBird.HEIGHT, this);

        // Pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, this);
        }

        // Bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, this);

        // HUD: score
        if (started) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 32));
            FontMetrics fm = g2.getFontMetrics();
            String scoreStr = String.valueOf(score);
            g2.drawString(scoreStr, (FlappyBird.WIDTH - fm.stringWidth(scoreStr)) / 2, 50);
        }

        // Start screen
        if (!started) {
            drawCenteredMessage(g2, "Flappy Bird", "Press SPACE or ENTER to start");
        }

        // Game-over overlay
        if (gameOver) {
            drawCenteredMessage(g2, "Game Over!", "Score: " + score + "  |  Press SPACE or ENTER to restart");
        }
    }

    /** Draw a semi-transparent overlay with a title and subtitle */
    private void drawCenteredMessage(Graphics2D g2, String title, String subtitle) {
        // Overlay
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(20, FlappyBird.HEIGHT / 2 - 70, FlappyBird.WIDTH - 40, 130, 20, 20);

        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 36));
        FontMetrics fmTitle = g2.getFontMetrics();
        g2.drawString(title,
                (FlappyBird.WIDTH - fmTitle.stringWidth(title)) / 2,
                FlappyBird.HEIGHT / 2 - 10);

        // Subtitle
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        FontMetrics fmSub = g2.getFontMetrics();
        g2.drawString(subtitle,
                (FlappyBird.WIDTH - fmSub.stringWidth(subtitle)) / 2,
                FlappyBird.HEIGHT / 2 + 30);
    }

    // ---- KeyListener ----

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER) {
            if (!started) {
                startGame();
            } else if (gameOver) {
                startGame();   // restart
            } else {
                bird.jump();   // normal jump
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
