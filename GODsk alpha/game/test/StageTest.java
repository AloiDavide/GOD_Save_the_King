import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StageTest {
    private List<Character> players;
    private Stage currentStage;



    @org.junit.jupiter.api.BeforeEach
    void setUp() {
         players = Arrays.asList(
                new Character.Builder(Job.KNIGHT, new GridPoint(8,0), "res/sprites/enemy knight.png").build(),
                new Character.Builder(Job.ARCHER, new GridPoint(7,6), "res/sprites/enemy archer.png").build(),
                new Character.Builder(Job.CASTER, new GridPoint(6,11), "res/sprites/enemy caster.png").build()
        );
        currentStage = Stage.TUTORIAL;
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        players = null;
        currentStage = null;
    }

    @org.junit.jupiter.api.Test
    void getCharactersTest() {
        //mettiamo i giocatori in expected
        List<Character> expectedCharacters = new ArrayList<>(players);
        //aggiungiamogli il brigante
        expectedCharacters.add(new Character.Builder(Job.BRIGAND, new GridPoint(4,9), "res/sprites/enemy brigand.png").build());
        //controlliamo che il metodo dia una lista uguale a quella appena creata
        assertEquals(expectedCharacters, currentStage.getCharacters(players));
    }

    @org.junit.jupiter.api.Test
    void getMapTest() {
        MapManager exptMap = new MapManager("tutorial.txt");
        assertEquals(exptMap, currentStage.getMap());
    }

    @org.junit.jupiter.api.Test
    void getNextStageTest() {
        Stage exptStage = Stage.STAGE_1;
        currentStage = currentStage.getNextStage();
        assertEquals(exptStage, currentStage);

    }
}