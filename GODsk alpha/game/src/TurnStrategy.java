import java.util.List;

public interface TurnStrategy {
    GridPoint move(List<GridPoint> activeMove);


    Character getTarget(List<GridPoint> enemiesInRange, List<GridPoint> alliesInRange);

    Action act(Character target);
}
