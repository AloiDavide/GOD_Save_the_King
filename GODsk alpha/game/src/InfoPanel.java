import javax.swing.*;
import java.awt.*;
public class InfoPanel extends JPanel {
    //tutti i label per mostrare i dati di un personaggio.
    private JLabel sprite = new JLabel();
    private JLabel name = new JLabel();
    private JLabel exp = new JLabel();
    private JLabel hp = new JLabel();
    private JLabel atk = new JLabel();
    private JLabel def = new JLabel();
    private JLabel mov = new JLabel();
    private JLabel pos = new JLabel();

    //un label per mostrare un qualsiasi messaggio.
    private JTextArea message = new JTextArea();
    private JScrollPane scroll = new JScrollPane(message, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


    private static InfoPanel instance;

    public static InfoPanel getInfoPanel(){
        if(instance == null){
            instance = new InfoPanel();
        }
        return instance;
    }

    private InfoPanel(){
        //set del pannello
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS ));
        this.setBackground(Color.BLACK);
        this.setVisible(true);


        //set di tutti i label
        sprite.setAlignmentX(0.5f);

        exp.setBackground(Color.BLACK);
        exp.setForeground(Color.WHITE);
        exp.setAlignmentX(0.5f);

        hp.setBackground(Color.BLACK);
        hp.setForeground(Color.WHITE);
        hp.setAlignmentX(0.5f);

        atk.setBackground(Color.BLACK);
        atk.setForeground(Color.WHITE);
        atk.setAlignmentX(0.5f);

        def.setBackground(Color.BLACK);
        def.setForeground(Color.WHITE);
        def.setAlignmentX(0.5f);

        mov.setBackground(Color.BLACK);
        mov.setForeground(Color.WHITE);
        mov.setAlignmentX(0.5f);

        name.setBackground(Color.BLACK);
        name.setForeground(Color.WHITE);
        name.setAlignmentX(0.5f);

        pos.setBackground(Color.BLACK);
        pos.setForeground(Color.WHITE);
        pos.setAlignmentX(0.5f);

        message.setBackground(Color.BLACK);
        message.setForeground(Color.WHITE);
        message.setEditable(false);

        scroll.setAlignmentX(0.5f);


        //Add di tutti i label.
        add(sprite);
        add(name);
        add(exp);
        add(hp);
        add(atk);
        add(def);
        add(mov);
        add(pos);
        //add(message);
        add(scroll);



    }//close constructor



    //Questo metodo compila tutti i JLabel con le informazioni prese dal Character che gli viene passato.
    public void printCharacterInfo(Character c){
        for (Component comp : this.getComponents()){
            comp.setVisible(true);
        }

        sprite.setIcon(new ImageIcon(c.getSprite().getScaledInstance(128,128,Image.SCALE_SMOOTH)));
        name.setText((c.name));
        exp.setText("LV : "+c.level + "  -  Exp : "+c.exp+" / 100");
        hp.setText("HP : "+c.getHP() + "/" + c.getStat("maxHP"));
        atk.setText("Atk : "+c.getStat("atk"));
        def.setText("Def : "+c.getStat("def"));
        mov.setText("Mov : "+c.getStat("moveRange"));
        //pos.setText("Posizione : "+active.getPosition().toString());
    }

    //Questo scrive in message la stringa che gli viene passata.
    public void printThis(String s){
        message.append(s);
        message.append("\n");

    }

    public void printNothing(){
        for (Component comp : this.getComponents()){
            comp.setVisible(false);
        }
    }


    public JLabel getExp() {
        return exp;
    }

    public JLabel getHp() {
        return hp;
    }

    public JLabel getAtk() {
        return atk;
    }

    public JLabel getDef() {
        return def;
    }

    public JLabel getMov() {
        return mov;
    }

    public JLabel getPos() {
        return pos;
    }
}