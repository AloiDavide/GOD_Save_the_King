import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TurnManager {
    TurnStrategy strat;
    Game game;
    MouseHandler mouseH;
    Character active;
    Character target;
    List<GridPoint> activeMove;
    List<GridPoint> activeRange;
    List<GridPoint> npcsInRange;
    List<GridPoint> pcsInRange;
    Action act;
    InfoPanel info = InfoPanel.getInfoPanel();
    ButtonsPanel buttons = ButtonsPanel.getButtonsPanel();
    TopPanel top = TopPanel.getTopPanel();




    public TurnManager(MouseHandler mouseH) {
        this.game = Game.getGameInstance();
        this.active = game.getActive();
        this.mouseH = mouseH;
        this.activeMove = game.getMap().findReachableTiles(active.getPosition(), active.getStat("moveRange"));
        this.activeRange = null;

        if(active.isPlayer) this.strat = new PlayerTurnStrategy(active, mouseH);
        else this.strat = new ComputerTurnStrategy(active);

        info.printCharacterInfo(active);

    }


    public void changeTurn(){
        info.printThis("");
        target = null;
        buttons.hideActions();

        //Ad "active" viene assegnato il personaggio subito dopo in lista.
        List<Character> chars = game.getCharacters();
        active = chars.get((chars.lastIndexOf(game.getActive())+1) % chars.size());
        game.setActive(active);

        System.out.println("\nActive: "+ active);


        if (active.isDown){
            info.printThis(active.name+ " non può muoversi.");
            changeTurn();
        }



        //viene calcolato il suo movimento
        activeMove = game.getMap().findReachableTiles(active.getPosition(), active.getStat("moveRange"));

        //viene aggiornata la strategia
        if(active.isPlayer) this.strat = new PlayerTurnStrategy(active, mouseH);
        else{
            this.strat = new ComputerTurnStrategy(active);
        }

        info.printCharacterInfo(active);
        game.refreshTurnOrder();

    }

    public void update() {

        if (mouseH.checkRightClick()){
            GridPoint cell = mouseH.getRightClick();
            boolean charFound = false;
            for (Character c : game.getCharacters()){
                if (c.getPosition().equals(cell)) {
                    charFound = true;
                    info.printCharacterInfo(c);
                }

            }
            if(!charFound) info.printCharacterInfo(active);
        }


        switch (game.getGameState()) {
            case MOVING:

                GridPoint moveTarget = strat.move(activeMove);

                if (moveTarget != null) {
                    info.printThis(active.name + " si è mosso/a");
                    info.printCharacterInfo(active);
                    game.saveSnapshot();
                    active.setPosition(moveTarget);
                    game.setGameState(Game.State.JUST_MOVED);
                }

                break;


            case JUST_MOVED:

                if (activeRange == null) {
                    buttons.showActions(List.of(Action.WAIT));

                    GridPoint activePos = active.getPosition();

                    activeRange = game.getMap().findRangeTiles(active.getPosition(), active.getStat("atkRangeMax"));
                    activeRange.removeAll(game.getMap().findRangeTiles(active.getPosition(), active.getStat("atkRangeMin") - 1));

                    Predicate<Character> enemyCheck;
                    if(active.isPlayer){
                        enemyCheck = (c->!c.isPlayer);
                    }else {
                        enemyCheck = (c -> c.isPlayer);
                    }


                    npcsInRange = game.getCharacters().stream().filter(enemyCheck).map(Character::getPosition)
                            .filter(p -> (p.distanceTo(activePos) <= active.getStat("atkRangeMax") &&
                                    p.distanceTo(activePos) >= active.getStat("atkRangeMin"))).collect(Collectors.toList());


                    if (active.job == Job.CASTER) {
                        pcsInRange = game.getCharacters().stream().filter(Predicate.not(enemyCheck).and(c -> c.getHP()<c.getStat("maxHP"))).map(Character::getPosition)
                                .filter(p -> (p.distanceTo(activePos) < 3)).collect(Collectors.toList());
                    }
                    else{
                        pcsInRange = game.getCharacters().stream().filter(Predicate.not(enemyCheck).and(c -> c.getHP() == 0)).map(Character::getPosition)
                                .filter(p -> (p.distanceTo(activePos) == 1)).collect(Collectors.toList());
                    }

                }

                if (buttons.getAction() == Action.WAIT){
                    activeRange = null;
                    changeTurn();
                    game.setGameState(Game.State.MOVING);
                }

                if (mouseH.scrollUp()) {
                    game.restoreSnapshot();
                }

                target = strat.getTarget(npcsInRange,pcsInRange);

                if (target != null) {
                    info.printCharacterInfo(active);
                    game.saveSnapshot();
                    activeRange = null;
                    game.setGameState(Game.State.ACTION);
                }

                break;


            case ACTION:

                List<Action> actions = this.getActions();
                buttons.showActions(actions);

                if (mouseH.scrollUp()) {
                    game.restoreSnapshot();
                }

                act = strat.act(target);
                if(act != null){
                    act.execute(active, target);
                    if (game.getGameState()!= Game.State.ACTION) break;
                    changeTurn();
                    game.setGameState(Game.State.MOVING);

                }
                break;


            case DIALOGUE:
                if(mouseH.scrollDown()){
                    if (game.dialogue.isDone()){
                        game.dialogue.callEndScene();
                        if(Scene.PROLOGUE_2.turnChange) {

                        }
                    }else{
                        game.dialogue.nextLine();
                    }
                }
                break;

            case VICTORY:
                if (mouseH.getLeftClick()!=null){
                    game.nextStage();
                    if (game.getGameState()!= Game.State.DIALOGUE) {
                        changeTurn();
                        game.setGameState(Game.State.MOVING);
                    }
                }
                break;
            case DEFEAT:
                if (mouseH.getLeftClick()!=null){
                    game.gameOverPopup();
                }
                break;

        }
    }


    private List<Action> getActions() {
        List<Action> actions = new ArrayList<>();
        actions.add(Action.WAIT);

        if (active == target) {
            if(active.job == Job.CASTER && target.getHP() <= target.getStat("maxHP")) actions.add(Action.HEAL);
            return actions;
        }
        if((active.isPlayer && !target.isPlayer) || (!active.isPlayer && target.isPlayer)){
            actions.add(Action.ATTACK);
        }
        else{
            if(target.getHP() <= 0) actions.add(Action.HELP);
            if(active.job == Job.CASTER && target.getHP() <= target.getStat("maxHP")) actions.add(Action.HEAL);
        }

        return actions;
    }



    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        game.getMap().drawBG(g2);

        switch(game.getGameState()) {
            case MOVING:
                if (active.isPlayer) game.getMap().drawMovement(g2, activeMove);
                for (Character c : game.getCharacters()) {
                    c.draw(g2);
                }
                break;
            case JUST_MOVED:
                if (active.isPlayer) game.getMap().drawTarget(g2, activeRange, npcsInRange, pcsInRange);
                for (Character c : game.getCharacters()) {
                    c.draw(g2);
                }
                break;
            case ACTION:
                for (Character c : game.getCharacters()) {
                    c.draw(g2);
                }
                break;
            case DIALOGUE:
                game.dialogue.drawScene(g2);
                break;
            case VICTORY:
                try {
                    BufferedImage winScreen = ImageIO.read(getClass().getResourceAsStream("res/backgrounds/victory.png"));

                    g2.drawImage(winScreen,0,0,Tile.size*25,Tile.size*18,null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case DEFEAT:
                try {
                    BufferedImage loseScreen = ImageIO.read(getClass().getResourceAsStream("res/backgrounds/defeat.png"));
                    g2.drawImage(loseScreen,0,0,Tile.size*25,Tile.size*18,null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }

        g2.dispose();
    }



}
