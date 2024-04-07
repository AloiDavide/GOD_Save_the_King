import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GridPointTest{
    private GridPoint g1, g2, g3;
    @BeforeEach
    protected void setUp() throws Exception{
        g1 = new GridPoint(1,2);
        g2 = new GridPoint(3,4);
        g3 = new GridPoint(3,4);
        Assertions.assertNotNull(g1);
        Assertions.assertNotNull(g2);
    }

    @AfterAll
    protected void tearDown(){
        g1 = null;
        g2 = null;
        Assertions.assertNull(g1);
        Assertions.assertNull(g2);
    }

    @Test
    public void testDistanceTo(){
       assertEquals(4, g1.distanceTo(g2));
        assertEquals(4, g2.distanceTo(g1));
        assertEquals(0, g1.distanceTo(g1));

    }

    @Test
    public void testGetAdjacent(){
        GridPoint gInterno = new GridPoint(1,2);
        GridPoint gAlBordo = new GridPoint(0,3);
        GridPoint gAllAngolo = new GridPoint(0,10);

        Set<GridPoint> adjInterno = Set.of(
            new GridPoint(0,2),
            new GridPoint(2,2),
            new GridPoint(1,1),
            new GridPoint(1,3)
        );

        Set<GridPoint> adjAlBordo = Set.of(
                new GridPoint(1,3),
                new GridPoint(0,2),
                new GridPoint(0,4)
        );


        Assertions.assertEquals(adjInterno, new HashSet<>(gInterno.getAdjacent()));
        Assertions.assertEquals(adjAlBordo, new HashSet<>(gAlBordo.getAdjacent()));



    }

    @Test
    public void testEquals(){
        Assertions.assertEquals(g1, g1);
        Assertions.assertEquals(g2, g3);
        Assertions.assertEquals(g3, g2);

    }

    @Test
    public void testToString(){
        String expected = "1:2";
        Assertions.assertEquals(expected, g1.toString());
    }
}