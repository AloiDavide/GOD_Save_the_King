import java.awt.image.BufferedImage;

public class Tile {
    static double scale = 2.5;
    static int size = (int) (16*scale);

    private BufferedImage image;
    private boolean collision = false;

    public BufferedImage getImage() {
        return image;
    }

    public boolean hasCollision() {
        return collision;
    }

    public Tile(BufferedImage image, boolean collision){
        this.image = image;
        this.collision = collision;
    }

    public Tile(BufferedImage image){
        this.image = image;
        this.collision = false;
    }

}
