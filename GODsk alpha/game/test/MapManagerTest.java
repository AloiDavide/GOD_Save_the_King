
import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapManagerTest {

    MapManager map = new MapManager("tutorial.txt");
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        assertNotNull(map);


    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {


    }

    @org.junit.jupiter.api.Test
    void loadTiles() {

    }

    @org.junit.jupiter.api.Test
    void loadMap() {
        //questo metodo fu testato con una mappa 3x3 e una 4x4 per poterne realisticamente ricreare il risultato come expected
        int[][] mp = new int[25][20];
        try {
            // Apertura del file di testo in lettura
            FileReader file = new FileReader("test/resources/map/tutorial.txt");
            BufferedReader reader = new BufferedReader(file);


            // Chiudi il file
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        assertArrayEquals(mp, map.getMapGrid());
    }


    @org.junit.jupiter.api.Test
    public void testfindReachableTiles() {

        map.loadMap("map01.txt");

        // Creo la posizione iniziale
        GridPoint startPos = new GridPoint(1, 1);
        int moveDistance = 2;


        List<GridPoint> reachableTiles = map.findReachableTiles(startPos, moveDistance);

        //Il risultato che mi aspetto
        List<GridPoint> expectedTiles = Arrays.asList(
                new GridPoint(1, 1),
                new GridPoint(1, 0),
                new GridPoint(1, 2),
                new GridPoint(0, 1),
                new GridPoint(2, 1),
                new GridPoint(0, 0),
                new GridPoint(2, 0),
                new GridPoint(1, 3),
                new GridPoint(0, 2),
                new GridPoint(2, 2),
                new GridPoint(3, 1)


        );


        assertEquals(expectedTiles, reachableTiles);
    }

}