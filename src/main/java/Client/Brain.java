package Client;

import Tools.Global;


public class Brain extends Thread {

    public Snake current_snake;
    private boolean running = false;

    Brain(Snake positionDepart) {
        current_snake = positionDepart;

//        current_snake.direction = 3;

        Tile headPos = new Tile(Tile.Type.SNAKE, current_snake.x, current_snake.y);
        current_snake.snake_body.add(headPos);
    }

    public void run() {
        while (true) {
            if (current_snake.isDead)
                return;

            move_body();
            paintSnake();
            deleteTail();

            crash();

            rest();
        }
    }

    private void paintSnake() {
        current_snake.paintSnake();
    }

    //delay between each move of the snake
    private void rest() {
        try {
            sleep(Global.speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void crash() {
        if(!running)
            return;

        Tile posCritique = current_snake.snake_body.get(current_snake.snake_body.size() - 1);

        for (int i = 0; i <= current_snake.snake_body.size() - 2; i++) {
            boolean biteItself = posCritique.x == current_snake.snake_body.get(i).x
                    && posCritique.y == current_snake.snake_body.get(i).y;

            if (biteItself)
                die();
        }
    }

    //Stops The Game
    private void die() {
        System.out.println("Snake died! \n");
        current_snake.isDead = true;

        if (current_snake.snakeChangeInterface != null)
            current_snake.snakeChangeInterface.die(/*this*/);

        new Thread(() -> {

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            current_snake.die();
        }).start();
        /*while (true) {
            rest();
        }*/
    }

    //Moves the head of the snake and refreshes the positions in the arraylist
    private void move_body() {

        if (running){
            switch (current_snake.direction) {
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

        current_snake.snake_body.add(new Tile(Tile.Type.SNAKE, current_snake.x, current_snake.y));
    }

    public void deleteTail() {

       /* int snakeSize = current_snake.get_size();

        for (int i = current_snake.snake_body.size() - 1; i >= 0; i--) {
            if (snakeSize == 0) {
                Tile t = current_snake.snake_body.get(i);

                Board.tiles.get(t.x).get(t.y).set_type(Tile.Type.EMPTY);

                current_snake.snake_body.remove(i);

            } else {
                snakeSize--;
            }
        }*/

        int cmpt = current_snake.get_size();
        for (int i = current_snake.snake_body.size() - 1; i >= 0; i--) {
            if (cmpt == 0) {
                Tile t = current_snake.snake_body.get(i);

                Board.tiles.get(t.x).get(t.y).set_type(Tile.Type.EMPTY);
            } else {
                cmpt--;
            }
        }

        cmpt = current_snake.get_size();

        for (int i = current_snake.snake_body.size() - 1; i >= 0; i--) {
            if (cmpt == 0) {
                current_snake.snake_body.remove(i);
            } else {
                cmpt--;
            }
        }
    }


    public void setRunning(boolean b) {
        this.running = true;
    }
}
