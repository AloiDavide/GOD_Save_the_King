import org.junit.jupiter.api.*;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TurnManagerTest {
    MouseHandler mouseH;
    TurnManager tm;
    GamePanel gp;
    Game game = Game.getGameInstance();
    ButtonsPanel buttons;
    Character c;
    Character war;
    List<GridPoint> activeRange;
    List<GridPoint> npcsInRange;
    List<GridPoint> pcsInRange;


    @BeforeEach
    protected void setUp() {
        mouseH = new MouseHandler();
        gp = new GamePanel(mouseH);
        tm = gp.tm;
        npcsInRange = new ArrayList<>();
        pcsInRange = new ArrayList<>();
        assertNotNull(mouseH);
        assertNotNull(gp);
        assertNotNull(tm);
    }

    @AfterAll
    protected void tearDown() {
        gp = null;
        tm = null;
        mouseH = null;
        assertNull(gp);
        assertNull(tm);
        assertNull(mouseH);


    }

    @Test
    void testChangeTurn() {
        List<Character> chars = game.getCharacters();

        tm.active = chars.get((chars.lastIndexOf(tm.active) + 1) % chars.size());
        game.setActive(tm.active);
        assertEquals(game.getActive(), tm.active);

    }

    @Test
    void testUpdate() {
        c = new Character.Builder(Job.KNIGHT, new GridPoint(3,4), "res/sprites/warrior.png").build();
        war = new Character.Builder(Job.ARCHER, new GridPoint( 3,5), "res/sprites/archer.png").portrait("res/sprites/archer.png").isPlayer().level(20).name("Michele").build();
        tm.strat = new PlayerTurnStrategy(Game.getGameInstance().getActive(), mouseH);
        List<GridPoint> activeMove = List.of(
                new GridPoint(1, 1),
                new GridPoint(2, 2),
                new GridPoint(3, 3));
        tm.activeMove = activeMove;

        mouseH.setLeftCell(new GridPoint(2,2));
        tm.update();
        assertEquals(Game.State.JUST_MOVED, game.getGameState());




    }

}