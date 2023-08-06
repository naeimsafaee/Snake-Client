package Client;

import java.awt.*;

public class Food {

    public int id;
    public int x;
    public int y;

    public String color;

    public Food() {
    }

    public Color getColor() {
        return new Color(Integer.parseInt(color), true);
    }

}
