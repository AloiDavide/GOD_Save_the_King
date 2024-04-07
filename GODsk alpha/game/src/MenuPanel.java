import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuPanel extends JPanel {
    private JButton startBtn = new JButton("Nuova partita");
    private JButton loadBtn = new JButton("Carica livello");
    private JButton exitBtn = new JButton("Esci");

    ButtonsPanel pannelloBottoni = ButtonsPanel.getButtonsPanel();
    InfoPanel pannelloInformazioni = InfoPanel.getInfoPanel();
    TopPanel topPanel = TopPanel.getTopPanel();
    JFrame gameWindow;


    public boolean gameWindowOpen = false;

    public MenuPanel(){

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        setVisible(true);

        this.repaint();

        Color color = new Color(120, 100, 80);
        add(Box.createVerticalStrut(440));
        for (Component c : List.of(startBtn,loadBtn,exitBtn)){
            JButton b = (JButton) c;
            b.setBackground(color);
            b.setForeground(Color.black);
            b.setFont(new Font("Arial", Font.BOLD, 40));
            b.setAlignmentX(0.5f);
            b.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
            add(b);
            add(Box.createVerticalStrut(30));
        }


        startBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                /*if utile a prevenire errori nella creazione
                di una nuova partita avendone già una aperta*/
                if (gameWindowOpen) {
                    JOptionPane.showMessageDialog(null, "Non puoi iniziare una nuova partita ! ",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }//close if per non iniziare una nuova istanza di gioco con una corrente già aperta

                createGameWindow();
                gameWindowOpen= true;


            }//close method actionPerformed
        });//close actionListener button 1 / GAME

        loadBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){

                Game.loadGame(new File("save.dat"));
                createGameWindow();
            }//close method actionPerformed / EXIT

        });


        exitBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                System.exit(0);
            }//close method actionPerformed / EXIT
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage bg = null;
        try {
            bg = ImageIO.read(getClass().getResourceAsStream("res/backgrounds/menu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
    }


    public void createGameWindow(){
        gameWindow= new JFrame();
        gameWindow.setLayout(new GridBagLayout());
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setTitle("GOD save the king");
        gameWindow.setBackground(Color.black);
        GridBagConstraints gbc = new GridBagConstraints();

        MouseHandler mouseH = new MouseHandler();
        GamePanel gamePanel = new GamePanel(mouseH);

        gameWindow.setMinimumSize(new Dimension(Tile.size*25 + 200+150, Tile.size*18 + 136));
        gameWindow.setMaximumSize(new Dimension(Tile.size*25 + 200+150, Tile.size*18 + 136));
        gameWindow.setPreferredSize(new Dimension(Tile.size*25 + 200+150, Tile.size*18 + 136));


        pannelloInformazioni.setMinimumSize(new Dimension(200, Tile.size*18));
        pannelloInformazioni.setMaximumSize(new Dimension(200, Tile.size*18));
        pannelloInformazioni.setPreferredSize(new Dimension(200, Tile.size*18));

        gbc.gridx = 0;
        gbc.gridy = 1;

        gameWindow.add(pannelloInformazioni, gbc);

        gamePanel.setMinimumSize(new Dimension(Tile.size*25, Tile.size*18)); // 1200 960
        gamePanel.setMaximumSize(new Dimension(Tile.size*25, Tile.size*18));
        gamePanel.setPreferredSize(new Dimension(Tile.size*25, Tile.size*18));

        gbc.gridx = 1;
        gbc.gridy = 1;
        gameWindow.add(gamePanel, gbc);

        pannelloBottoni.setMinimumSize(new Dimension(150, Tile.size*18));
        pannelloBottoni.setMaximumSize(new Dimension(150, Tile.size*18));
        pannelloBottoni.setPreferredSize(new Dimension(150, Tile.size*18));

        gbc.gridx = 2;
        gbc.gridy = 1;
        gameWindow.add(pannelloBottoni, gbc);


        topPanel.setMinimumSize(new Dimension(Tile.size*25, 100));
        topPanel.setMaximumSize(new Dimension(Tile.size*25, 100));
        topPanel.setPreferredSize(new Dimension(Tile.size*25, 100));

        gbc.gridx = 1;
        gbc.gridy = 0;
        //gbc.gridwidth = 3;
        gameWindow.add(topPanel, gbc);



        //TURN COUNTER IN ALTO A DESTRA
        gbc.gridx = 2;
        gbc.gridy = 0;
        JPanel pauseP = new JPanel();
        pauseP.setLayout(new BoxLayout(pauseP, BoxLayout.Y_AXIS ));
        pauseP.setMinimumSize(new Dimension(150, 100));
        pauseP.setMaximumSize(new Dimension(150, 100));
        pauseP.setPreferredSize(new Dimension(150, 100));
        pauseP.setVisible(true);
        pauseP.setBackground(Color.BLACK);

        JButton pause = new JButton("Pausa");
        pause.setAlignmentX(0.5f);
        pause.setBackground(Color.ORANGE);
        pause.setForeground(Color.BLACK);
        pause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                new PauseMenu();
            }
        });

        pauseP.add(pause);
        gameWindow.add(pauseP, gbc);




        //TURN COUNTER IN ALTO A DESTRA
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel orderP = new JPanel();
        orderP.setLayout(new BoxLayout(orderP, BoxLayout.Y_AXIS ));
        orderP.setMinimumSize(new Dimension(200, 100));
        orderP.setMaximumSize(new Dimension(200, 100));
        orderP.setPreferredSize(new Dimension(200, 100));
        //orderP.setVisible(true);
        orderP.setBackground(Color.BLACK);

        JLabel order = new JLabel("Ordine dei Turni:");

        order.setForeground(Color.WHITE);
        order.setFont(new Font("Arial", Font.BOLD, 20));
        order.setAlignmentX(0.5f);

        orderP.add(Box.createVerticalStrut(40));
        orderP.add(order);

        gameWindow.add(orderP, gbc);


        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        gamePanel.startGameThread();




        gameWindow.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                gameWindowOpen = false;

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public JButton getStartBtn() {
        return startBtn;
    }

    public JFrame getGameWindow() {
        return gameWindow;
    }
}
