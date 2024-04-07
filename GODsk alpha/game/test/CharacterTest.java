import org.junit.jupiter.api.*;
import org.junit.*;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CharacterTest {
    Character c;
    Character war;
    @BeforeEach
    public void setUp() throws Exception{
        c = new Character.Builder(Job.KNIGHT, new GridPoint(3,5), "res/sprites/warrior.png").build();
        war = new Character.Builder(Job.ARCHER, new GridPoint( 3,5), "res/sprites/archer.png").portrait("res/sprites/archer.png").isPlayer().level(200).name("Giancarlo").build();
        assertNotNull(c);
    }

    @AfterAll
    public void tearDown(){
        c = null;
        war = null;
        assertNull(c);
    }


    @Test
    void testBuilder(){
        /*BufferedImage p =null;
        try {
            p = ImageIO.read(getClass().getResourceAsStream("sprites/archer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int lv =200;
        byte[] byteArray = ((DataBufferByte) p.getData().getDataBuffer()).getData();
        String nom = "Giancarlo";
        assertArrayEquals(byteArray,((DataBufferByte) war.getSprite().getData().getDataBuffer()).getData());
        assertEquals(new GridPoint(3,5), war.getPosition());
        assertEquals(Job.ARCHER,war.job);
        assertEquals(nom,war.name);
        assertArrayEquals(byteArray,((DataBufferByte) war.portrait.getData().getDataBuffer()).getData());
        assertEquals(lv,war.level);*/
    }
    @Test
    void testGetPosition() {
        assertEquals(new GridPoint(3,5), c.getPosition());
    }

    @Test
    void testSetPosition() {
        GridPoint newPos = new GridPoint(4,1);
        c.setPosition(newPos);
        assertEquals(newPos, c.getPosition());
    }


    @Test
    void testCompareTo() {
        Character c2 = new Character.Builder(Job.ARCHER, new GridPoint(6,5), "res/sprites/archer.png").build();
        Character c3 = new Character.Builder(Job.CASTER, new GridPoint(9,5), "res/sprites/healer.png").build();
        assertTrue(c.compareTo(c2)<0);
        assertTrue(c.compareTo(c3)>0);
        assertEquals(0, c.compareTo(c));
    }


    @Test
    void testGetStat(){
        assertEquals(15, c.getStat("maxHP"));
    }

    @Test
    void testModifyStat(){
        c.modifyStat("atk", 3);
        assertEquals(10, c.getStat("atk"));
    }

    @Test
    void testResetStats() {
        Map<String,Integer> expected = c.job.baseStats;
        c.modifyStat("def", 8);
        c.resetStats();
        assertEquals(5,c.getStat("def"));
    }
}