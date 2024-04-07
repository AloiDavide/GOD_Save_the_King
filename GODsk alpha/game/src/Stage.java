import java.util.ArrayList;
import java.util.List;

public enum Stage {
    //Questo enum rappresenta un determinato livello.
    //Memorizza che mappa usare, crea tutti nemici, e mette i PC nelle posizioni iniziali a vita piena.

    TUTORIAL("tutorial.txt",
            List.of(new GridPoint(8,5),new GridPoint(7,6),new GridPoint(6,7)),
            List.of(new Character.Builder(Job.BRIGAND, new GridPoint(4,9), "res/sprites/enemy brigand.png").build())),

    STAGE_1("stage1.txt",
            List.of(new GridPoint(3,5),new GridPoint(3,7),new GridPoint(3,9)),
            List.of(
        new Character.Builder(Job.KNIGHT, new GridPoint(8,4), "res/sprites/enemy knight.png").build(),
        new Character.Builder(Job.ARCHER, new GridPoint(14,3), "res/sprites/enemy archer.png").build(),
        new Character.Builder(Job.CASTER, new GridPoint(12,12), "res/sprites/enemy caster.png").build(),
        new Character.Builder(Job.BRIGAND, new GridPoint(8,13), "res/sprites/enemy brigand.png").build(),
        new Character.Builder(Job.KNIGHT, new GridPoint(24,9),"res/sprites/boss1.png").level(3).name("Ion").build()
        ));


    private String mapFile;
    private List<GridPoint> playerPos;
    private List<Character> enemies;


    Stage(String mapFile, List<GridPoint> playerPos, List<Character> enemies){
        this.mapFile = mapFile;
        this.enemies = enemies;
        this.playerPos = playerPos;
    }

    public List<Character> getCharacters(List<Character> players){
        List<Character> chars = new ArrayList<>(players);
        chars.forEach(c->c.setHP(c.getStat("maxHP")));
        chars.get(0).setPosition(playerPos.get(0));
        chars.get(1).setPosition(playerPos.get(1));
        chars.get(2).setPosition(playerPos.get(2));


        chars.addAll(enemies);

        return chars;
    }

    public MapManager getMap(){
        return new MapManager(mapFile);
    }

    public Stage getNextStage(){
        if(this==TUTORIAL) return STAGE_1;
        else return null;

    }
}
