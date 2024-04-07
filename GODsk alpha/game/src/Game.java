import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.ERROR_MESSAGE;


public class Game implements Serializable {
    private static Game gameInstance;
    private State gameState;
    private Stage stage;
    private List<Character> characters;
    private List<Character> players = new ArrayList<>();
    private Character active;
    private transient MapManager map;
    private Memento snapshot;
    public Dialogue dialogue;


    public enum State{
        NOT_IN_COMBAT,
        MOVING,
        JUST_MOVED,
        ACTION,
        DIALOGUE,
        VICTORY(true),
        DEFEAT(true);

        boolean endStage;
        State(boolean end) {this.endStage = end;}
        State() {this.endStage = false;}
    }


    private Game(){
        gameState = State.NOT_IN_COMBAT;
        this.stage = Stage.TUTORIAL;
        this.map = stage.getMap();


        players.add(new Character.Builder(Job.KNIGHT, new GridPoint(0,0), "res/sprites/garlan idle.png").name("Garlan").portrait("res/portraits/Garlan.png").isPlayer().build());
        players.add(new Character.Builder(Job.ARCHER, new GridPoint(0,0), "res/sprites/olivia idle.png").name("Olivia").portrait("res/portraits/Olivia.png").isPlayer().build());
        players.add(new Character.Builder(Job.CASTER, new GridPoint(0,0), "res/sprites/drurd idle.png").name("Drurd").portrait("res/portraits/Drurd.png").isPlayer().build());



        characters = stage.getCharacters(players);

        refreshTurnOrder();

        active = characters.get(0);

        dialogue = new Dialogue(Scene.PROLOGUE_1, this);


    }


    public static File saveGame(String fileName) {

        try {
            if(gameInstance!=null)gameInstance.restoreSnapshot();
            File save = new File(fileName);

            if (!save.exists()) {
                save.createNewFile();
                System.out.println("Nuovo salvataggio creato: "+fileName);
            }
            else{System.out.println("Sovrascritto: "+fileName);}

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(save));
            out.writeObject(gameInstance);

            out.close();

            return save;

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Game loadGame(File save){

        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(save));
            Game loaded = (Game) in.readObject();
            Game.gameInstance = loaded;

            for(Character c : loaded.getCharacters()) {
                c.restoreImages();
            }
            loaded.map = loaded.stage.getMap();
            in.close();
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        gameInstance.restoreSnapshot();
        return gameInstance;
    }

    public void killCharacter(Character victim){
        if (victim.isPlayer){
            victim.isDown = true;
            refreshTurnOrder();
            if (players.stream().allMatch(p -> p.isDown)) {
                TopPanel.getTopPanel().showNothing();
                setGameState(State.DEFEAT);
            }
        }
        else{
            //mi complico la vita per mantenere characters una lista immutabile
            characters = characters.stream().filter(c->!c.equals(victim)).collect(Collectors.toList());
            refreshTurnOrder();
            if(characters.stream().allMatch(c-> c.isPlayer)){
                TopPanel.getTopPanel().showNothing();
                setGameState(State.VICTORY);
            }

        }
    }

    public void nextStage(){


        InfoPanel.getInfoPanel().printThis("");
        players.stream().forEach(p->p.gainExp(100));

        if (stage.getNextStage() == null){
            dialogue = new Dialogue(Scene.EPILOGUE, this);
        }
        else{stage = stage.getNextStage();
            map = stage.getMap();
            characters = stage.getCharacters(players);

            refreshTurnOrder();


            active = characters.get(characters.size() - 1);

            saveSnapshot();
        }



    }

    public void gameOverPopup(){
            String btnString1 = "Carica Partita";
            String btnString2 = "Esci dal gioco";


            String msgString1 = "Tutti i personaggi sono morti";
            Object[] array = {msgString1};

            Object[] options = {btnString1, btnString2};

            int result = JOptionPane.showOptionDialog(null, array, "Hai perso!", JOptionPane.YES_NO_OPTION, ERROR_MESSAGE, null, options, options[0]);
            switch (result) {
                case 0:
                    loadGame(new File("save.dat"));
                    break;
                case 1:
                    System.exit(0);
                    break;
            }
    }

    public Stage getStage() {
        return stage;
    }


    public Character getActive() {
        return active;
    }

    public List<Character> getPlayers(){
        return players;
    }

    public void setActive(Character active) {
        this.active = active;
    }

    public static Game getGameInstance(){
        if (gameInstance == null){
            gameInstance = new Game();
        }
        return gameInstance;
    }

    public State getGameState(){
        return gameState;
    }

    public void setGameState(State s){
        System.out.println(s);
        gameState = s;
    }

    public List<Character> getCharacters(){
        return characters;
    }

    public MapManager getMap() {
        return map;
    }


    public void refreshTurnOrder(){
        characters = characters.stream().sorted(Comparator.reverseOrder()).toList();
        TopPanel.getTopPanel().showTurnOrder(characters);
    }

    public void saveSnapshot(){
        snapshot = new Memento(gameState, characters, players, active, stage, dialogue);
    }

    static void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void restoreSnapshot(){
        this.gameState = snapshot.gameState;
        this.characters = snapshot.characters;
        this.players = snapshot.players;
        this.map = snapshot.stage.getMap();
        this.active = snapshot.active;
        for (int i=0 ;i<characters.size();i++){
            characters.get(i).setPosition(snapshot.positions.get(i));
        }
        this.stage = snapshot.stage;
        this.dialogue = snapshot.dialogue;

    }

    private class Memento implements Serializable{
        private final State gameState;
        private final List<Character> characters;
        private final List<GridPoint> positions;
        private final Character active;
        private final List<Character> players;
        private Stage stage;
        private Dialogue dialogue;

        private Memento(State gameState, List<Character> characters, List<Character> players, Character active, Stage stage, Dialogue dialogue){
            this.gameState = gameState;
            this.characters = characters;
            this.positions = characters.stream().map(Character::getPosition).toList();
            this.active = active;
            this.players = players;
            this.stage = stage;
            this.dialogue = dialogue;

        }

    }


}
