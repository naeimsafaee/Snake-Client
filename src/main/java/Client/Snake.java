package Client;


import GUI.SnakeChangeInterface;
import Tools.Global;

import java.awt.*;
import java.util.ArrayList;

public class Snake {

    public int id;

    public int x;
    public int y;

    public int size;

    public int direction;

    public String color;

    public transient ArrayList<Tile> snake_body = new ArrayList<>();
    public transient boolean isDead = false;

    transient SnakeChangeInterface snakeChangeInterface;

    transient Brain brain;

    public void moveByBrain(int x, int y) {
        move(x, y , direction);

        if (snakeChangeInterface != null)
            snakeChangeInterface.move(/*this*/);

    }

    public void move(int x, int y , int direction) {

        if (y < 0) {
            this.y = Global.cols - 1;
        } else {
            this.y = y % Global.cols;
        }

        if (x < 0) {
            this.x = Global.rows - 1;
        } else {
            this.x = x % Global.rows;
        }

        if(this.direction != direction)
            System.out.println("change direction");

        this.direction = direction;
    }


    public void increase_size() {
        size += 1;
    }


    public int get_size() {
        return size;
    }

    public Color getColor() {
        return new Color(Integer.parseInt(color), true);
    }

    public void paintSnake() {
        //Refresh the squares color
        for (Tile t : snake_body) {
            Board.tiles.get(t.x).get(t.y).set_type(getColor());
        }
    }

    public void die(){
        isDead = true;
        for (Tile t : snake_body) {
            Board.tiles.get(t.x).get(t.y).set_type(Tile.Type.EMPTY);
        }
    }

    /*void nextMove(){
        switch (direction) {
            //right
            case 1 -> current_snake.moveByBrain(current_snake.x, current_snake.y + 1);

            //left
            case 2 -> current_snake.moveByBrain(current_snake.x, current_snake.y - 1);

            //top
            case 3 -> current_snake.moveByBrain(current_snake.x - 1, current_snake.y);

            //bottom
            case 4 -> current_snake.moveByBrain(current_snake.x + 1, current_snake.y);

            //null
//            default -> current_snake.moveByBrain(current_snake.x + 1, current_snake.y);
        }
    }
*/
}
