import java.io.Serializable;

public class Line implements Serializable {
    String name;
    String text;


    public Line(String name, String text) {
        this.name = name;
        this.text = text;

    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

}