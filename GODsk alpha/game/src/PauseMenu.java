import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class PauseMenu extends JFrame{
public JButton saveAndExit;
public JButton reLoadLevel;



public PauseMenu(){
    super("Menù di pausa");
    setLayout(new GridLayout(2,2));
    saveAndExit= new JButton("Salva ed esci");
    reLoadLevel= new JButton("Ricomincia Livello");
    
    Color color1 =new Color(87, 186, 245);
    saveAndExit.setBackground(color1);
    saveAndExit.setForeground(Color.black);
    reLoadLevel.setBackground(color1);
    reLoadLevel.setForeground(Color.black);

    saveAndExit.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent event){

        Game.saveGame("save.dat");

        System.exit(0);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

        });

    
        add(saveAndExit);
        add(reLoadLevel);

        setSize(200,100);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}