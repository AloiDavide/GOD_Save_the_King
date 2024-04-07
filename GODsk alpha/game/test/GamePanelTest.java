import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GamePanelTest {
    GamePanel gp;
    Game game;

    @BeforeEach
    protected void setUp(){
        gp = new GamePanel(new MouseHandler());
        assertNotNull(gp);
    }

    @AfterAll
    protected void tearDown(){
        gp = null;
        assertNull(gp);

    }


    @Test
    void testStartGameThread() {
        gp.startGameThread();
        assertTrue(gp.gameThread.isAlive());
    }

    @Test
    void testRun() {

    }


}