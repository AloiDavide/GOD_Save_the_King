import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseHandler implements MouseListener, MouseWheelListener {
    private Game g = Game.getGameInstance();
    private boolean rightClick;
    private boolean leftClick;
    private Point pixel;
    private GridPoint leftCell;
    private GridPoint rightCell;

    private boolean down = false;
    private boolean up = false;


    public boolean scrollDown(){
        if (down){
            down = false;
            return true;
        }
        else return  false;
    }

    public GridPoint getRightClick(){
        if (rightCell != null && Game.getGameInstance().getGameState()!= Game.State.DIALOGUE) {
            GridPoint cell = rightCell;
            rightCell = null;
            rightClick = false;
            return cell;
        }
        else return null;
    }

    public GridPoint getLeftClick(){

        if (leftCell != null && Game.getGameInstance().getGameState()!= Game.State.DIALOGUE) {
            GridPoint cell = leftCell;
            leftCell = null;
            leftClick = false;
            return cell;
        }
        else return null;
    }

    public boolean scrollUp(){
        if (up){
            up = false;
            return false;
        }
        else return  false;
    }

    public boolean checkRightClick(){
        return rightClick;
    }

    public boolean checkleftClick(){
        return leftClick;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            pixel = new Point(e.getX(), e.getY());

            int x = (int) Math.floor(pixel.getX() / Tile.size);
            int y = (int) Math.floor(pixel.getY() / Tile.size);
            leftCell = new GridPoint(x, y);
        }
        if(e.getButton() == 3){
            pixel = new Point(e.getX(), e.getY());

            int x = (int) Math.floor(pixel.getX() / Tile.size);
            int y = (int) Math.floor(pixel.getY() / Tile.size);
            rightCell = new GridPoint(x, y);
            rightClick = true;
        }
        if(e.getButton() == 3){
            pixel = new Point(e.getX(), e.getY());

            int x = (int) Math.floor(pixel.getX() / Tile.size);
            int y = (int) Math.floor(pixel.getY() / Tile.size);
            rightCell = new GridPoint(x, y);
            rightClick = true;
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0) down = true;
        if (e.getWheelRotation() < 0) up = true;
    }

    public void setLeftCell(GridPoint leftCell) {
        this.leftCell = leftCell;
    }
}
