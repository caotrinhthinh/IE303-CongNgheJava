import java.awt.*;
import javax.swing.*;

public class Pipe {
    private static final int TARGET_WIDTH = 52;

    // Position & size
    int x, y;
    int width, height;

    // Sprite
    Image img;

    // Scoring flag
    boolean passed = false;
    boolean isTop; // true = top pipe, false = bottom pipe

    // Speed (pixels per frame)
    static final int SPEED = 4;

    public Pipe(int x, int y, boolean isTop) {
        this.isTop = isTop;
        String assetName = isTop ? "assets/toppipe.png" : "assets/bottompipe.png";
        Image original = new ImageIcon(getClass().getResource(assetName)).getImage();
        int originalWidth = original.getWidth(null);
        int originalHeight = original.getHeight(null);

        width = TARGET_WIDTH;
        height = (int) Math.round((double) originalHeight * TARGET_WIDTH / originalWidth);
        img = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
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
