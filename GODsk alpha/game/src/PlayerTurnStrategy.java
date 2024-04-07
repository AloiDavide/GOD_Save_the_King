import java.util.List;

public class PlayerTurnStrategy implements TurnStrategy {
    private Character active;
    private MouseHandler mouseH;
    public PlayerTurnStrategy(Character active, MouseHandler mouseH){
        this.active = active;
        this.mouseH = mouseH;
    }

    public GridPoint move(List<GridPoint> activeMove) {
        GridPoint cell = mouseH.getLeftClick();
        if (cell != null && activeMove.contains(cell)) {
            return cell;
        }
        else return null;
    }

    public Character getTarget(List<GridPoint> enemiesInRange, List<GridPoint> alliesInRange) {
        //se non ci sono target possibili, viene restituito il personaggio attivo.
        Character target = null;

        if (enemiesInRange.isEmpty() && alliesInRange.isEmpty()) target = active;

        GridPoint cell = mouseH.getLeftClick();
        if (cell != null && (enemiesInRange.contains(cell) || alliesInRange.contains(cell))){
            List <Character> characters = Game.getGameInstance().getCharacters();
            for (Character c : characters) {
                if (cell.equals(c.getPosition())) {
                    target = c;
                }
            }
        }

        return target;
    }

    public Action act(Character target){
        return ButtonsPanel.getButtonsPanel().getAction();
    }


}