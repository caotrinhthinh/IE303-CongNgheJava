import java.awt.*;
import javax.swing.*;

public class Pipe {
    // Position & size
    int x, y;
    int width, height;

    // Sprite
    Image img;

    // Scoring flag
    boolean passed = false;

    // Speed (pixels per frame)
    static final int SPEED = 4;

    public Pipe(int x, int y, boolean isTop) {
        String assetName = isTop ? "assets/toppipe.png" : "assets/bottompipe.png";
        img = new ImageIcon(getClass().getResource(assetName)).getImage();
        width = img.getWidth(null);
        height = img.getHeight(null);
        this.x = x;
        this.y = y;
    }

    /** Move pipe left each frame */
    public void move() {
        x -= SPEED;
    }

    /** True when pipe has scrolled off screen */
    public boolean isOffScreen() {
        return x + width < 0;
    }

    /** Collision rectangle */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
