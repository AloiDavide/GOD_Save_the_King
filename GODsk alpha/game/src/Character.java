import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import static java.lang.Math.floor;

public class Character implements Comparable<Character>, Serializable {
    public transient BufferedImage sprite;
    private String spriteAddress;
    public final Job job;
    public transient BufferedImage portrait;
    private final String portraitAddress;
    public final String name;
    public final boolean isPlayer;
    public boolean isDown = false;
    private Map<String, Integer> stats;
    private GridPoint pos;
    public int currentHP;
    public int level; //default is one
    public int exp;


    public void restoreImages(){
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream(spriteAddress));
            if(portrait != null) this.portrait = ImageIO.read(getClass().getResourceAsStream(portraitAddress));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHP(){return currentHP;}

    public void setHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public GridPoint getPosition() {return pos;}

    public void setPosition(GridPoint p) {this.pos = p;}

    public void gainExp(int newExp){
        if(!isPlayer) return;
        InfoPanel.getInfoPanel().printThis(this.name + " ha guadagnato "+ newExp+" EXP\n");
        int totExp = this.exp + newExp;
        if (totExp >= 100){
            this.exp = totExp - 100;
            this.levelUp(1);
            InfoPanel.getInfoPanel().printThis(this.name + " ha guadagnato un livello!\n");
        }
        else this.exp = totExp;
    }
    public void levelUp(int levels){
        this.level += levels;

        this.modifyStat("maxHP", 5 * levels);
        this.modifyStat("atk", 2 * levels);
        this.modifyStat("def", 2 * levels);
        this.modifyStat("speed", 2 * levels);

        this.setHP(currentHP+5*levels);

    }

    @Override
    public String toString() {
        return name;
    }

    public void draw(Graphics2D g2) {
        //DISEGNA LO SPRITE
        g2.drawImage(sprite,Tile.size * pos.x, Tile.size * pos.y, Tile.size, Tile.size, null);

        //DISEGNA LA HEALTH BAR
        double currentOverMaxHp = (double) this.currentHP / this.getStat("maxHP");
        int hpPixels = (int) Math.round(Tile.size * currentOverMaxHp);
        int scale = (int) Math.round(Tile.scale);


        if (this.isPlayer) g2.setColor(new Color(255,135,0));
        else g2.setColor(new Color(135, 0, 255));

        g2.fillRect(Tile.size * pos.x, Tile.size * (pos.y+1)-scale*2, hpPixels, scale);
        g2.setColor(Color.BLACK);
        g2.drawRect(Tile.size * pos.x, Tile.size * (pos.y+1)-scale*2, Tile.size, scale);
    }



    @Override
    public int compareTo(Character c) {
        return getStat("speed") - c.getStat("speed");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return isPlayer == character.isPlayer && isDown == character.isDown && currentHP == character.currentHP &&
                level == character.level && exp == character.exp &&
                spriteAddress.equals(character.spriteAddress) && job == character.job &&
                Objects.equals(name, character.name) &&
                stats.equals(character.stats) && Objects.equals(pos, character.pos);
    }


    //aggiunge value al valore attuale della statistica passata
    public void modifyStat(String label, int value) {
        stats.put(label,this.getStat(label) + value);
    }

    public int getStat(String label) {
        return stats.get(label);
    }

    public void resetStats(){
        stats = new HashMap<>(job.baseStats);
        if (currentHP > stats.get("maxHP")) currentHP = stats.get("maxHP");
    }






    private Character(Builder b) {
        this.sprite = b.sprite;
        this.spriteAddress = b.spriteAddress;
        this.portrait = b.portrait;
        this.portraitAddress = b.portraitAddress;
        this.stats = b.stats;
        this.pos = b.pos;
        this.job = b.job;
        this.currentHP = b.currentHP;
        this.name = b.name;
        this.level = b.level;
        this.isPlayer = b.isPlayer;
        this.exp = 0;
        this.levelUp(level-1);

    }

    public BufferedImage getSprite() {
        return sprite;
    }


    /*------------------------
    | BUILDER DEI PERSONAGGI |
    ------------------------*/
    public static class Builder{
        private BufferedImage sprite;
        private String spriteAddress;
        private BufferedImage portrait = null;
        private String portraitAddress;
        private Job job;
        private Map<String, Integer> stats;
        private GridPoint pos;
        private int currentHP;
        private String name;
        private int level = 1;
        private boolean isPlayer = false;


        public Builder(Job job, GridPoint pos, String spritePath) {
            this.pos = pos;
            this.job = job;
            //baseStats deve essere non modificabile, per cui il personaggio si crea una sua copia.
            this.stats = new HashMap<>(job.baseStats);
            this.currentHP = this.stats.get("maxHP");
            this.name = this.job.name();


            this.spriteAddress = spritePath;
            try {
                this.sprite = ImageIO.read(getClass().getResourceAsStream(spriteAddress));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        public Builder portrait(String path){
            this.portraitAddress = path;
            try {
                this.portrait = ImageIO.read(getClass().getResourceAsStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder level(int lv){
            this.level = lv;
            return this;
        }

        public Builder isPlayer(){
            this.isPlayer = true;
            return this;
        }


        public Character build(){
            return new Character(this);
        }
    }


}