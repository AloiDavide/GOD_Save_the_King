import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GridPoint extends Point {

    public GridPoint(int x, int y){
        super(x, y);
    }

    public int distanceTo(GridPoint g){
        return Math.abs(this.x - g.x) + Math.abs(this.y - g.y);
    }

    public List<GridPoint> getAdjacent(){
        //restituisce una lista con le 4 caselle adiacenti a questa (meno di 4 se siamo al bordo)


        List<GridPoint> adj = new ArrayList<>();
        if(y > 0){
            adj.add(new GridPoint(this.x,this.y-1));
        }

        if(y < MapManager.maxRow-1){
            adj.add(new GridPoint(this.x,this.y+1));
        }

        if(x > 0){
            adj.add(new GridPoint(this.x-1,this.y));
        }

        if(x < MapManager.maxCol-1){
            adj.add(new GridPoint(this.x+1,this.y));
        }

        return adj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridPoint pos = (GridPoint) o;
        return x == pos.x && y == pos.y;
    }

    @Override
    public String toString() {
        return x + ":" + y;
    }
}
