package Tools;

import Client.Brain;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter {

    Brain brain;

    public KeyboardListener(Brain brain) {
        this.brain = brain;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 39:    // -> Right
                //if it's not the opposite direction
                if (brain.current_snake.direction != 2)
                    brain.current_snake.direction = 1;
                break;
            case 38:    // -> Top
                if (brain.current_snake.direction != 4)
                    brain.current_snake.direction = 3;
                break;

            case 37:    // -> Left
                if (brain.current_snake.direction != 1)
                    brain.current_snake.direction = 2;
                break;

            case 40:    // -> Bottom
                if (brain.current_snake.direction != 3)
                    brain.current_snake.direction = 4;
                break;

            default:
                break;
        }
    }

}
