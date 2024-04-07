import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TopPanel extends JPanel {
    List<JLabel> turnLabels = new ArrayList<>();
    private BufferedImage arrowIcon;

    private static TopPanel instance;

    public static TopPanel getTopPanel(){
        if(instance == null){
            instance = new TopPanel();
        }
        return instance;
    }

    private TopPanel(){
        //set del pannello
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.black);
        setVisible(true);

        try {
            this.arrowIcon = ImageIO.read(getClass().getResourceAsStream("res/sprites/arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showTurnOrder(List<Character> characters){
        int c=0;
        for (Component comp : this.getComponents()) {
            c++;
            remove(comp);
        }
        revalidate();
        repaint();


        while (turnLabels.size() < characters.size()) turnLabels.add(new JLabel());

        for(int i=0; i<characters.size(); i++){
            if(characters.get(i).isDown) continue;

            turnLabels.get(i).setIcon(new ImageIcon(characters.get(i).getSprite()));
            JLabel arrow = new JLabel();
            arrow.setIcon(new ImageIcon(arrowIcon));
            add(turnLabels.get(i));
            add(Box.createRigidArea(new Dimension(20,100)));
            add(arrow);
            add(Box.createRigidArea(new Dimension(20,100)));
        }
        revalidate();
        repaint();
    }

    public void showNothing(){
        for (Component comp : this.getComponents()){
            remove(comp);
        }
        revalidate();
        repaint();
    }

    public List<JLabel> getTurnLabels() {
        return turnLabels;
    }
}