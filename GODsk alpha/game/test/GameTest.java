import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameTest {
    Game game = Game.getGameInstance();

    @BeforeEach
    void setup(){

        assertNotNull(Game.getGameInstance());
    }

    @Test
    void saveGameTest() {
        // Salva il gioco
        game.saveSnapshot();
        File saveFile = game.saveGame("save.dat");

        // Verifica che il file di salvataggio sia stato creato
        assertTrue(saveFile.exists());
    }

    @Test
    void loadGameTest() {

        // Crea una nuova istanza del gioco e salvala in un file di prova
        Game game = Game.getGameInstance();
        File saveFile = game.saveGame("save.dat");

        // Carica il gioco dal file di prova
        Game loadedGame = Game.loadGame(saveFile);

        // Verifica che la lista dei personaggi del gioco carico sia uguale a quella dell'istanza originale
        assertEquals(game.getCharacters().get(0),loadedGame.getCharacters().get(0));
    }

    @Test
    public void killCharacterTest(){
        // creo un player, per provare che quando viene ucciso, la funzione isDown diventa true
        Character p;
        p = new Character.Builder(Job.KNIGHT, new GridPoint(0,0), "res/sprites/knight.png").name("Garlan").isPlayer().build();
        game.killCharacter(p);
        assertTrue(p.isDown);

        // for che controlla che la funzione isDown, funzioni soltanto per i player
        for(Character c : game.getCharacters()){
            if(!c.isPlayer){
                game.killCharacter(c);
                assertFalse(c.isDown);

            }
        }
        //provo che nextstage funzioni dopo la morte di tutti gli NPC
        assertEquals(game.getStage(),Stage.TUTORIAL);

    }

}

