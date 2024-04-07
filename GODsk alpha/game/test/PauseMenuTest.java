import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.File;

public class PauseMenuTest {
    private PauseMenu pauseMenu;

    Game game = Game.getGameInstance();

    @Before
    public void setUp() {
        pauseMenu = new PauseMenu();
    }

    @After
    public void tearDown() {
        pauseMenu.dispose();
    }

    @Test
    public void testSaveAndExit() {
        // Simula il click del pulsante "Salva ed esci"
        game.saveSnapshot();
        pauseMenu.saveAndExit.doClick();
        // Verifica che il gioco sia stato salvato
        assertTrue(game.saveGame("save.dat").exists());

        // Verifica che la finestra sia visibile
        assertTrue(pauseMenu.isVisible());
    }


}