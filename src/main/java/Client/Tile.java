package Client;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Tile extends JPanel {

    int x;
    int y;

    public enum Type {
        EMPTY(new Color(255,255,255,255)),
        FOOD(),
        SNAKE();

        private final Color color;

        Type(Color color) {
            this.color = color;
        }

        Type() {
            Random rand = new Random();

            this.color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        }

        public Color get_color() {
            return this.color;
        }

    }

    Type current_type;

    public Tile(Type type , int x , int y) {
        current_type = type;
        this.x = x;
        this.y = y;

        setBackground(current_type.get_color());
    }

    public void set_type(Type new_type) {
        setBackground(new_type.get_color());
        repaint();
    }

    public void set_type(Color color) {
        setBackground(color);
        repaint();
    }

}
