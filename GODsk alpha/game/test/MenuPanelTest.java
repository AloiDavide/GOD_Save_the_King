import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MenuPanelTest {
    MenuPanel menu = new MenuPanel();
    @Before
    public void setup(){

        assertNotNull(menu);
    }

    @After
    public void tearDown(){
        assertNull(menu);
    }

    @Test
    public void testStartButtonActionListener() {
        // vado su "Inizia Nuova Partita"  e simulo il mouse click

        menu.getStartBtn().doClick();

        // Controllo se il nuovo panello è aperto dopo il click
        assertTrue(menu.isVisible());
    }

    @Test
    public void testCreateGameWindow(){
        assertFalse(menu.gameWindowOpen);

        menu.createGameWindow();

        // Verifico che la finestra di gioco sia stata aperta
        menu.getStartBtn().doClick();
        assertTrue(menu.gameWindowOpen);

        // Verifico il titolo della finestra di gioco
        assertEquals("GOD save the king", menu.getGameWindow().getTitle());


        // Verifico che il pannello delle informazioni sia stato aggiunto alla finestra di gioco
        //Verifica che il panellobottoni sia stato aggiungo alla finestra di gioco
        //Verifica che il toppanel sia stato aggiunto alla finestra di gioco
        assertNotNull(menu.pannelloInformazioni);
        assertNotNull(menu.pannelloBottoni);
        assertNotNull(menu.topPanel);
        assertTrue(menu.topPanel.isVisible());
        assertTrue(menu.pannelloBottoni.isVisible());
        assertTrue(menu.pannelloInformazioni.isVisible());
    }



}