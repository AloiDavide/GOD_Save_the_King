import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;


public class Gui extends JFrame {

    MenuPanel menu = new MenuPanel();

    //per favorire l'ordine, tutto ciò che ha a che fare con i bottoni (set, actionlistener, etc) li ho messi direttamente in MenuPanel
    //lasciando a Gui solo il compito di crearlo e fare da frame.
    public Gui(){
        super("GOD save the king - Menu");
        setPreferredSize(new Dimension(1109,763));
        setMinimumSize(new Dimension(1109,763));
        setMaximumSize(new Dimension(1109,763));

        add(menu);
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

}//close class
