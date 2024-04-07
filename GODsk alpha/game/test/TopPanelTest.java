import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

public class TopPanelTest {
    private TopPanel panel = TopPanel.getTopPanel();;
    private List<Character> characters = new ArrayList<>();;



    @Test
    public void testShowTurnOrder() {
        characters.add(new Character.Builder(Job.KNIGHT, new GridPoint(0,0), "res/sprites/knight.png").name("Fulvio").isPlayer().build());
        characters.add(new Character.Builder(Job.CASTER, new GridPoint(0,0), "res/sprites/caster.png").name("Davide").isPlayer().build());
        panel.showTurnOrder(characters);
        assertEquals(2, panel.getTurnLabels().size());
    }


}




