import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ComputerTurnStrategy implements TurnStrategy{
    List<Character> players;
    Character active;


    public ComputerTurnStrategy(Character active){
        this.active = active;
        players = Game.getGameInstance().getCharacters().stream().filter(c->c.isPlayer && !c.isDown)
                .sorted(Comparator.comparing(this::battleRating).reversed()).toList();

    }

    public GridPoint move(List<GridPoint> activeMove){

        for (int i=0 ; i < players.size(); i++){
            GridPoint playerLocation = players.get(i).getPosition();
            Optional<GridPoint> newLocation = activeMove.stream().filter(p->p.distanceTo(playerLocation) <= active.getStat("atkRangeMax")).findFirst();
            if (newLocation.isPresent()) return newLocation.get();
        }
        Game.sleep(2);
        return active.getPosition();
    }
    public Character getTarget(List<GridPoint> enemiesInRange, List<GridPoint> alliesInRange){
        for (int i=0 ; i < players.size(); i++){
            if (enemiesInRange.contains(players.get(i).getPosition())) return players.get(i);
        }

        return active;
    }

    public Action act(Character target){
        if(target == active) return Action.WAIT;
        Game.sleep(2);
        return Action.ATTACK;
    }

    private double battleRating(Character target){
        double dealt = active.getStat("atk") - target.getStat("def");
        double taken = target.getStat("atk") - active.getStat("def");
        if (taken == 0) taken =0.1;

        if (target.getHP() <= dealt) return 1000;

        return dealt/taken;
    }

    public List<Character> getPlayers() {
        return players;
    }
}
