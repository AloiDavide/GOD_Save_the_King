import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComputerTurnStrategyTest {
    List<Character> players;
    ComputerTurnStrategy cts;


    @BeforeEach
    protected void setUp(){
        cts = new ComputerTurnStrategy(Game.getGameInstance().getActive());
        players = cts.getPlayers();
        Game.getGameInstance().setGameState(Game.State.MOVING);
        assertNotNull(cts);
    }

    @AfterAll
    protected void tearDown(){
        cts = null;
        assertNull(cts);

    }

    @Test
    void moveTest() {
        List<GridPoint> activeMove = List.of(
                new GridPoint(6,7),
                new GridPoint(7,6),
                new GridPoint(8,5));

        assertEquals(players.get(0).getPosition(), cts.move(activeMove));
    }

    @Test
    void getTargetTest() {
        List<Character> characters = Game.getGameInstance().getCharacters();

        List<GridPoint> enemiesInRange = List.of(
                new GridPoint(8,5));
        List<GridPoint> alliesInRange = List.of(
                new GridPoint(8,5));


        assertEquals(players.get(1), cts.getTarget(enemiesInRange,alliesInRange));

    }

    @Test
    void actionTest(){
        assertEquals(Action.ATTACK, cts.act(players.get(0)));
    }

}