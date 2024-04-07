
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InfoPanelTest {
    InfoPanel infoPanel = InfoPanel.getInfoPanel();
    // test che il pannello di informazioni viene creato correttamente
   @BeforeEach
    public void setup(){
       assertNotNull(infoPanel);
    }

    @AfterAll
    public void tearDown(){
       infoPanel = null;
       assertNull(infoPanel);
    }

    // test che il pannello di informazioni ha i componenti (label, etc.) attesi
    @Test
    public void testInfoPanelComponents() {
        assertNotNull(infoPanel.getExp());
        assertNotNull(infoPanel.getHp());
        assertNotNull(infoPanel.getAtk());
        assertNotNull(infoPanel.getDef());
        assertNotNull(infoPanel.getMov());
        assertNotNull(infoPanel.getPos());

    }

    // test che il pannello di informazioni visualizza correttamente le informazioni di un personaggio
    @Test
    public void testPrintCharacterInfo() {
        // crea un personaggio di prova
       Character c = new Character.Builder(Job.ARCHER, new GridPoint(0,0), "res/sprites/archer.png").name("Olivia").isPlayer().build();


        // imposta le informazioni del personaggio nel pannello di informazioni

        infoPanel.printCharacterInfo(c);
        // verifica che le informazioni siano state impostate correttamente

        assertEquals("LV : 1  -  Exp : 0 / 100", infoPanel.getExp().getText());
        assertEquals("HP : 12/12", infoPanel.getHp().getText());
        assertEquals("Atk : 10", infoPanel.getAtk().getText());
        assertEquals("Def : 1", infoPanel.getDef().getText());
        assertEquals("Mov : 6", infoPanel.getMov().getText());
    }
}
