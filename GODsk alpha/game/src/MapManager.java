import javax.imageio.ImageIO;
import java.util.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;


public class MapManager {

    public static int maxCol = 25;
    public static int maxRow = 18;
    private Tile[] tiles;
    private Tile movTile;
    private Tile rangeTile;
    private Tile enemyTile;
    private Tile allyTile;
    private int[][] mapGrid;

    public MapManager(String mapFile) {
        this.tiles = new Tile[15];
        this.mapGrid = new int[maxCol][maxRow];

        loadMap("res/map/"+mapFile);
        loadTiles();
    }

    public int[][] getMapGrid() {
        return mapGrid;
    }

    public void loadTiles() {
        //crea un oggetto per ogni tile diversa e li mette nell'array delle tile
        //NON sono uno per ogni posizione, tutte le caselle di erba ad esempio fanno riferimento
        //a un solo oggetto tile con quella texture
        try {
            tiles[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/0.png")));


            tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/1.png")), true);


            tiles[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/2.png")), true);

            tiles[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/3.png")), true);

            tiles[4] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/4.png")), true);

            tiles[5] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/5.png")));

            tiles[6] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/6.png")));

            tiles[7] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/7.png")));

            tiles[8] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/8.png")), true);

            tiles[9] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/9.png")), true);

            tiles[10] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/10.png")), true);

            tiles[11] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/11.png")), true);

            tiles[12] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/12.png")), true);

            tiles[13] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/13.png")), true);

            tiles[14] = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/14.png")));


            movTile = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/move.png")));

            rangeTile = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/range.png")));

            enemyTile = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/enemy.png")));

            allyTile = new Tile(ImageIO.read(getClass().getResourceAsStream("res/tileset/ally.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String path) {
        //prende il file di testo e dispone quei numeri in una matrice mapGrid che definisce il terreno della mappa
        try {
            InputStream in = getClass().getResourceAsStream(path);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            int col = 0;
            int row = 0;

            String line;
            while ((line = reader.readLine()) != null ) {

                while (col < maxCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapGrid[col][row] = num;
                    col++;
                }
                if (col == maxCol) {
                    col = 0;
                    row++;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }




    }

    public void drawBG(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;


        while (col < maxCol && row < maxRow) {
            int tileNum = mapGrid[col][row];

            g2.drawImage(tiles[tileNum].getImage(), x, y, Tile.size, Tile.size, null);
            col++;
            x += Tile.size;

            if (col == maxCol) {
                col = 0;
                x = 0;
                row++;
                y += Tile.size;
            }
        }

    }

    public void drawMovement(Graphics2D g2, List<GridPoint> moveList) {
        for (GridPoint p : moveList){
                    g2.drawImage(movTile.getImage(), p.x * Tile.size, p.y * Tile.size, Tile.size, Tile.size, null);
        }

    }

    public void drawTarget(Graphics2D g2, List<GridPoint> tilesInRange, List<GridPoint> npcsInRange, List<GridPoint> pcsInRange) {
        if (tilesInRange!=null) {
            for (GridPoint p : tilesInRange) {
                g2.drawImage(rangeTile.getImage(), p.x * Tile.size, p.y * Tile.size, Tile.size, Tile.size, null);
            }
            for(GridPoint p : npcsInRange){
                g2.drawImage(enemyTile.getImage(), p.x * Tile.size, p.y * Tile.size, Tile.size, Tile.size, null);
            }
            for(GridPoint p : pcsInRange){
                g2.drawImage(allyTile.getImage(), p.x * Tile.size, p.y * Tile.size, Tile.size, Tile.size, null);
            }
        }
    }



    public List<GridPoint> findReachableTiles(GridPoint pos, int range){
        List<GridPoint> Q = new ArrayList<>();
        List<GridPoint> nextQ = new ArrayList<>();
        List<GridPoint> done = new ArrayList<>();
        List<GridPoint> ad;

        Q.add(pos);
        done.add(pos);

        while (range > 0){
            while(!Q.isEmpty()) {

                ad = Q.remove(0).getAdjacent();

                for (GridPoint p : ad) {

                    /*Controllare che la tile selezionata:
                        - non è già stata aggiunta
                        - non è invalicabile
                        - non è occupata da un personaggio
                    */
                    if (done.stream().noneMatch(d -> d.equals(p)) &&
                        !tiles[mapGrid[p.x][p.y]].hasCollision() &&
                        Game.getGameInstance().getCharacters().stream().noneMatch(c -> c.getPosition().equals(p))) {

                        nextQ.add(p);
                        done.add(p);

                    }
                }
            }
            Q = nextQ;
            nextQ = new ArrayList<>();
            range--;
        }

        return done;
    }

    public List<GridPoint> findRangeTiles(GridPoint pos, int range) {
        List<GridPoint> done = new ArrayList<>();
        List<GridPoint> q = new ArrayList<>();
        List<GridPoint> nextQ = new ArrayList<>();
        done.add(pos);
        q.add(pos);
        while (range>0){
            List<GridPoint> finalNextQ = nextQ;
            q.stream().forEach(p-> finalNextQ.addAll(p.getAdjacent()));

            q = new HashSet<>(finalNextQ).stream().toList();
            done.addAll(q);
            nextQ = new ArrayList<>();
            range --;
        }
        return done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapManager that = (MapManager) o;
        return Arrays.deepEquals(mapGrid, that.mapGrid);
    }

}
