import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ButtonsPanelTest {
    ButtonsPanel buttonsPanel =ButtonsPanel.getButtonsPanel();

    Game game = Game.getGameInstance();

    @Before
    public void setUp(){

    }

    @After
    public void tearDown(){

    }

    @Test
    void getButtonsPanel() {
        assertSame(buttonsPanel,ButtonsPanel.getButtonsPanel());
    }

    @Test
    void showActions() {
        List<Action> a= new ArrayList<>();
        a.add(Action.ATTACK);
        a.add(Action.WAIT);
        a.add(Action.HEAL);
        a.add(Action.HELP);
        buttonsPanel.showActions(a);
        assertTrue(buttonsPanel.getAttack().isVisible());
        assertTrue(buttonsPanel.getWait().isVisible());
        assertTrue(buttonsPanel.getHeal().isVisible());
        assertTrue(buttonsPanel.getHelp().isVisible());

        }

    @Test
    void hideActions() {
        buttonsPanel.hideActions();
        assertFalse(buttonsPanel.getAttack().isVisible());
        assertFalse(buttonsPanel.getWait().isVisible());
        assertFalse(buttonsPanel.getHeal().isVisible());
        assertFalse(buttonsPanel.getHelp().isVisible());

    }

    @Test
    void getAction(){
        buttonsPanel.setAction(null);
        assertNull(buttonsPanel.getAction());

        buttonsPanel.getWait().doClick();
        assertEquals(Action.WAIT,buttonsPanel.getActionTest());

        buttonsPanel.getAttack().doClick();
        assertEquals(Action.ATTACK,buttonsPanel.getActionTest());

        buttonsPanel.getHeal().doClick();
        assertEquals(Action.HEAL,buttonsPanel.getActionTest());

        buttonsPanel.getHelp().doClick();
        assertEquals(Action.HELP,buttonsPanel.getActionTest());
    }


}