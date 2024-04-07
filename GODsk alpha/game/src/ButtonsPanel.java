import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ButtonsPanel extends JPanel {


    private Map<Action, JButton> buttonsMap;



    private Action action = null;

    private JButton attack;
    private JButton heal;
    private JButton help;
    private JButton wait;



    private static ButtonsPanel instance;

    public static ButtonsPanel getButtonsPanel(){
        if(instance == null){
            instance = new ButtonsPanel();
        }
        return instance;
    }

    private ButtonsPanel(){
        //set del pannello

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS ));
        setBackground(Color.black);
        setVisible(true);





        attack= new JButton("Attack");
        heal= new JButton("Heal");
        help= new JButton("Help");
        wait= new JButton("Wait");

        //dizionario azione - bottone
        buttonsMap = Map.of(
                Action.ATTACK, attack,
                Action.HEAL, heal,
                Action.HELP, help,
                Action.WAIT, wait);


        attack.setBackground(Color.ORANGE);
        attack.setForeground(Color.BLACK);
        heal.setBackground(Color.ORANGE);
        heal.setForeground(Color.BLACK);
        help.setBackground(Color.ORANGE);
        help.setForeground(Color.BLACK);
        wait.setBackground(Color.ORANGE);
        wait.setForeground(Color.BLACK);



        attack.setAlignmentX(0.5f);
        heal.setAlignmentX(0.5f);
        help.setAlignmentX(0.5f);
        wait.setAlignmentX(0.5f);



        add(attack);
        add(heal);
        add(help);
        add(wait);


        hideActions();




        attack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = Action.ATTACK;
            }
        });

        heal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = Action.HEAL;
            }
        });

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = Action.HELP;
            }
        });

        wait.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = Action.WAIT;
            }
        });

    }

    //mostra i bottoni richiesti
    public void showActions(List<Action> actions){

        for(Action a: actions){
            buttonsMap.get(a).setVisible(true);
        }
    }

    //nascondi tutti i bottoni
    public void hideActions(){

        for(JButton b : buttonsMap.values()){
        b.setVisible(false);
        }
    }

    public Action getAction(){
        if(action != null){
            Action res = action;
            action = null;
            return res;
        }
        else{
            return null;
        }
    }



    public JButton getAttack() {
        return attack;
    }

    public JButton getHeal() {
        return heal;
    }

    public JButton getHelp() {
        return help;
    }

    public JButton getWait() {
        return wait;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getActionTest(){
        return action;
    }

    public Map<Action, JButton> getButtonsMap() {
        return buttonsMap;
    }
}