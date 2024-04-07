import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlayerTurnStrategyTest {
    MouseHandler mouseH;
    PlayerTurnStrategy pts;


    @BeforeEach
    protected void setUp(){
        mouseH = new MouseHandler();
        pts = new PlayerTurnStrategy(Game.getGameInstance().getActive(), mouseH);
        Game.getGameInstance().setGameState(Game.State.MOVING);
        assertNotNull(mouseH);
        assertNotNull(pts);
    }

    @AfterAll
    protected void tearDown(){
        pts = null;
        mouseH = null;
        assertNull(pts);
        assertNull(mouseH);

    }

    @Test
    void moveTest() {
        List<GridPoint> activeMove = List.of(
                new GridPoint(1,1),
                new GridPoint(2,2),
                new GridPoint(3,3));

        mouseH.setLeftCell(new GridPoint(2,2));
        assertEquals(new GridPoint(2,2), pts.move(activeMove));
    }

    @Test
    void getTargetTest() {
        List<Character> characters = Game.getGameInstance().getCharacters();

        List<GridPoint> enemiesInRange = List.of(
                new GridPoint(4,9));
        List<GridPoint> alliesInRange = List.of(
                new GridPoint(8,5));

        mouseH.setLeftCell(enemiesInRange.get(0));
        assertEquals(characters.get(2), pts.getTarget(enemiesInRange,alliesInRange));

        mouseH.setLeftCell(alliesInRange.get(0));
        assertEquals(characters.get(1), pts.getTarget(enemiesInRange,alliesInRange));
    }


    //testare questa versione di act() equivale a testare getAction() di ButtonsPanel

}