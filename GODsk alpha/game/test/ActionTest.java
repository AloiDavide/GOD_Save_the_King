import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    //Action.[azione che vuoi]
    Character active = new Character.Builder(Job.KNIGHT, new GridPoint(0,0), "res/sprites/garlan idle.png").name("Garlan").portrait("res/portraits/Garlan.png").isPlayer().build();
    Character target = new Character.Builder(Job.ARCHER, new GridPoint(0,0), "res/sprites/olivia idle.png").name("Olivia").portrait("res/portraits/Olivia.png").isPlayer().build();
    //Action.ATTACK.execute(active,target);
    @Test
    void executeAttack() {
        InfoPanel info = InfoPanel.getInfoPanel();
     assertNotNull(Action.ATTACK.info);
        active.setHP(10);
        target.setHP(10);
        //int damage = active.getStat("atk") - target.getStat("def");
        //target.setHP(target.getHP() - damage);
        Action.ATTACK.execute(active,target);
        assertTrue(target.getHP()<10);
        assertFalse(active.isDown);
    }

    @Test
    void executeWait() {
        InfoPanel info = InfoPanel.getInfoPanel();
        assertNotNull(Action.WAIT.info);
    }

    @Test
    void executeHelp() {
        InfoPanel info = InfoPanel.getInfoPanel();
        assertNotNull(Action.HELP.info);
        assertFalse(target.isDown);
        target.isDown=true;
        Action.HELP.execute(active,target);
        assertTrue(target.getHP()==1);
        assertFalse(target.isDown);

    }

    @Test
    void executeHeal() {
        InfoPanel info = InfoPanel.getInfoPanel();
        active.setHP(10);
        target.setHP(10);
        Action.HEAL.execute(active,target);
        assertTrue(target.getHP()>10);
        assertFalse(active.isDown);
        assertNotNull(Action.HEAL.info);
       // assertTrue(active.gainExp()==10);
    }
    @Test
    void expFormula() {
        assertNotNull(Action.expFormula(active,target));
        //add exp
    }
}