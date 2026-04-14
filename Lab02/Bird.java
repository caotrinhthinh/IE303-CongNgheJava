import java.awt.*;
import javax.swing.*;

public class Bird {
    // Position & size
    int x, y;
    int width, height;

    // Physics
    double velocity = 0;
    static final double GRAVITY = 0.5;
    static final double JUMP_STRENGTH = -9;

    // Sprite
    Image img;

    public Bird(int startX, int startY) {
        img = new ImageIcon(getClass().getResource("assets/flappybird.png")).getImage();
        width = img.getWidth(null);
        height = img.getHeight(null);
        x = startX - width / 2;
        y = startY - height / 2;
    }

    /** Apply gravity each frame */
    public void applyGravity() {
        velocity += GRAVITY;
        y += (int) velocity;
    }

    /** Jump upward */
    public void jump() {
        velocity = JUMP_STRENGTH;
    }

    /** Return collision rectangle */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
