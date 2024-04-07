import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.List;

public class Dialogue implements Serializable {

    private List<Line> lines;
    private Line currentLine;

    //posizione e grandezza del textbox
    private final int x = Tile.size * 5, y= Tile.size * 13;
    private final int width =Tile.size * 19, height=Tile.size * 4;

    private Scene scene;

    private final Color bgColor = new Color(0x6E2701);
    private final Color textColor = Color.WHITE;
    private final Font font = new Font("Medieval Sharp", Font.PLAIN, 20) ;

    public Dialogue(Scene scene, Game game) {
        this.scene = scene;
        this.lines = scene.getScript();
        this.currentLine = lines.get(0);

        InfoPanel.getInfoPanel().printNothing();
        TopPanel.getTopPanel().showNothing();
        game.setGameState(Game.State.DIALOGUE);

    }



    public void drawScene(Graphics2D g2) {
        g2.setFont(font);

        //DISEGNA SFONDO SE PRESENTE
        BufferedImage background = scene.getBg();
        if (background != null) {
            background.getScaledInstance(Tile.size * 25, Tile.size * 19, Image.SCALE_SMOOTH);
            g2.drawImage(background, 0, 0, Tile.size * 25, Tile.size * 19, null);
        }



        //DISEGNA PORTRAIT SE PRESENTE
        BufferedImage portrait;
        try {
            portrait = ImageIO.read(getClass().getResourceAsStream("res/portraits/" + currentLine.getName() + ".png"));
        } catch (IOException e) {
            portrait = null;
        }


        if (portrait != null){
            g2.drawImage(portrait, x - 5 * Tile.size, y, Tile.size * 5, Tile.size * 5, null);
        }

        //DISEGNA NOME PERSONAGGIO
        g2.setColor(bgColor);
        g2.fillRect(20, y, 160, 40);
        g2.setColor(textColor);
        g2.drawString(currentLine.getName(), 60, y+30);

        //DISEGNA TESTO
        g2.setColor(textColor);

        int i = 0;
        for (String l : currentLine.getText().split("\n")) {
            if(i%5 == 0){
                g2.setColor(bgColor);
                g2.fillRect(x, y, width, height);
                g2.setColor(textColor);
            }
            g2.drawString(l, x + 10, y + 40 + (i%5) * 40);
            i++;
        }

    }


    public void nextLine() {
        currentLine = lines.get(lines.lastIndexOf(currentLine)+1 % lines.size());

    }


    public boolean isDone() {
        return currentLine == lines.get(lines.size()-1);
    }

    public void callEndScene(){
        scene.endScene();
    }



}
