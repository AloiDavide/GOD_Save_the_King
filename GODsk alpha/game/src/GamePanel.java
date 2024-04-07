import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.math.BigInteger;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable {

    public static final int screenWidth = Tile.size * MapManager.maxCol;
    public static final int screenHeight = Tile.size * MapManager.maxRow;
    public final int FPS = 30;

    Thread gameThread;
    TurnManager tm;


    public GamePanel(MouseHandler mouseH) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addMouseListener(mouseH);
        this.addMouseWheelListener(mouseH);

        this.tm = new TurnManager(mouseH);


    }

    public void startGameThread() {
        //fa partire il thread in cui gira il gioco, e con start chiama il metodo run sottostante
        gameThread = new Thread(this);
        gameThread.start();


    }

    @Override
    public void run() {
        double time = 10E14;
        double drawInterval = time / FPS;

        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        //game loop, chiama update e repaint 30 volte al secondo
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            if (delta >= 1) {

                //---------
                update();
                repaint();
                //---------

                delta --;
                lastTime = System.nanoTime();
            }
        }
    }


    public void update() {
        tm.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        tm.paintComponent(g);
    }
}
