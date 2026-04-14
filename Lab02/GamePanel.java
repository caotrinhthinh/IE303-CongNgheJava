import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Image background;

    public GamePanel() {
        setPreferredSize(new Dimension(FlappyBird.WIDTH, FlappyBird.HEIGHT));
        setFocusable(true);

        // Load background image
        background = new ImageIcon(getClass().getResource("assets/flappybirdbg.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background to fill entire panel
        g.drawImage(background, 0, 0, FlappyBird.WIDTH, FlappyBird.HEIGHT, this);
    }
}
