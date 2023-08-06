package Client;


import GUI.Header;
import Tools.Global;
import Tools.KeyboardListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Board extends JPanel {

    public static ArrayList<ArrayList<Tile>> tiles;
    public static ArrayList<Snake> snakes;

    private final Header header;
    JFrame frame;

    Snake current_snake = null;


    public Board(JFrame frame, Header header) {
        tiles = new ArrayList<>();
        snakes = new ArrayList<>();

        this.frame = frame;
        this.header = header;

        init_board();
    }

    private void init_board() {

        ArrayList<Tile> data;

        for (int i = 0; i < Global.rows; i++) {
            data = new ArrayList<>();
            for (int j = 0; j < Global.cols; j++) {
                Tile c = new Tile(Tile.Type.EMPTY, i, j);
                data.add(c);
            }
            tiles.add(data);
        }

        setLayout(new GridLayout(Global.rows, Global.cols, 0, 0));

        for (int i = 0; i < Global.rows; i++) {
            for (int j = 0; j < Global.cols; j++) {
                add(tiles.get(i).get(j));
            }
        }
    }

    public void snake_created(Snake snake, boolean self) {
        Brain brain = new Brain(snake);
        snake.brain = brain;

        if (self) {
            current_snake = snake;

            frame.addKeyListener(new KeyboardListener(brain));

            header.setSize(current_snake.size);

            brain.setRunning(true);
        } else {
            snakes.add(snake);
        }

        updateOnlineSnakes();

        brain.start();
    }

    public void food_created(Food food) {
        Tile foodTile = findFood(food);
        if (foodTile != null) {
            foodTile.set_type(food.getColor());
        }
    }

    public void eat(Snake snake) {

        System.out.println(snake.id + " eat a food " + current_snake);

        if (current_snake != null && snake.id == current_snake.id){
            current_snake.increase_size();

            header.setSize(current_snake.size);

        } else
            for (int i = 0; i < snakes.size(); i++) {
                if (snakes.get(i).id == snake.id) {
                    snakes.get(i).increase_size();
                    return;
                }
            }
    }

    public void removeFood(Food food) {
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {
                if (i == food.x && j == food.y)
                    tiles.get(i).get(j).set_type(Tile.Type.EMPTY);
            }
        }
    }

    private Tile findFood(Food food) {
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {
                if (i == food.x && j == food.y)
                    return tiles.get(i).get(j);
            }
        }
        return null;
    }

    public void snakeMove(Snake snake) {
        if ((current_snake == null) || snake.id != current_snake.id) {
            for (int i = 0; i < snakes.size(); i++) {
                if (snakes.get(i).id == snake.id) {
                    snakes.get(i).move(snake.x, snake.y , snake.direction);
                    return;
                }
            }
        }
    }

    public void removeSnake(Snake snake) {

        for (int i = 0; i < snakes.size(); i++) {
            if (snakes.get(i).id == snake.id) {
                snakes.get(i).die();

                snakes.remove(i);

                updateOnlineSnakes();
                return;
            }
        }
    }


    void updateOnlineSnakes(){
        header.setOnlineSnakes(snakes.size() + (current_snake != null ? 1 : 0));
    }


}
