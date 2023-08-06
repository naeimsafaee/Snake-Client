package Tools;

import java.awt.*;
import java.util.Random;

public class Rand extends Random {

    public int rand(int min, int max) {
        return this.nextInt(max) + max - min + 1;
    }

    public int rand() {
        return this.nextInt();
    }

    public String randColor() {
        Color _color = new Color(this.nextFloat(), this.nextFloat(), this.nextFloat());

        return Integer.toString(_color.getRGB());
    }

}
