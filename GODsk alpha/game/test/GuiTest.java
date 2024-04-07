import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.*;


import javax.swing.*;
import java.awt.*;

public class GuiTest {
    Gui gui = new Gui();
    MenuPanel menu = new MenuPanel();
    @BeforeEach
    public void setup(){

    assertNotNull(gui);

    }
    @After
    void tearDown(){
        assertNull(gui);
        assertNull(menu);
    }
    @Test
    public void testGuiConstructor() {

        // Conferma del titolo
        String expectedTitle = "GOD save the king - Menu";
        String actualTitle = gui.getTitle();
        assertEquals(expectedTitle, actualTitle);

        //Controllo se Menupanel è aperto
        assertNotNull(menu);

        // Se il jframe si chiude correttamente
        int expectedCloseOperation = JFrame.EXIT_ON_CLOSE;
        int actualCloseOperation = gui.getDefaultCloseOperation();
        assertEquals(expectedCloseOperation, actualCloseOperation);

    }



}