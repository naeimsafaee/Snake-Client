package Client;

import GUI.GUI;
import Utility.*;

import java.io.IOException;
import GUI.SnakeChangeInterface;

public class Game {

    public static void main(String[] args) {

        GUI gui = new GUI();

        try {
            Socket socket = new Socket();

            socket.on("connection", new SocketDataInterface() {
                @Override
                public void data(SocketData connection) {

                    System.out.println("Connected to server");

                    connection.emit("create snake", "");

                    connection.on("create snake", new SocketObjectAndDataInterface<Snake>() {
                        @Override
                        public void data(Snake snake , SocketData socketData) {

                            System.out.println("snake created : " + snake.id + " self: " + (socketData.socketId == socket.getSocketId()));

                            if(socketData.socketId == socket.getSocketId()){
                                snake.snakeChangeInterface = new SnakeChangeInterface() {
                                    @Override
                                    public void move() {
                                        socketData.emit("move" , snake);
                                    }

                                    @Override
                                    public void die() {
                                        socketData.emit("snake died" , snake);
                                    }
                                };


                            }

                            gui.board.snake_created(snake , socketData.socketId == socket.getSocketId());

                        }
                    });
                }
            });

            socket.on("previous snakes", new SocketObjectInterface<Snake>() {
                @Override
                public void data(Snake snake) {

                    System.out.println("previous snakes created : " + snake.id + " direction: " + snake.direction);

                    gui.board.snake_created(snake , false);

                }
            });

            socket.on("create food", new SocketObjectInterface<Food>() {
                @Override
                public void data(Food food) {
                    gui.board.food_created(food);
                }
            });

            socket.on("eat", new SocketObjectInterface<Snake>() {
                @Override
                public void data(Snake snake) {
                    gui.board.eat(snake);
                }
            });

            socket.on("remove snake", new SocketObjectInterface<Snake>() {
                @Override
                public void data(Snake snake) {
                    System.out.println("snake removed with id: " + snake.id);
                    gui.board.removeSnake(snake);
                }
            });

            socket.on("remove food", new SocketObjectInterface<Food>() {
                @Override
                public void data(Food food) {
                    gui.board.removeFood(food);
                }
            });

            socket.on("move", new SocketObjectInterface<Snake>() {
                @Override
                public void data(Snake snake) {
//                    System.out.println("snake moved : " +  snake.id);
                    gui.board.snakeMove(snake);
                }
            });

            /* socket.on("data", new SocketDataInterface() {
                @Override
                public void data(SocketData socket) {
                    System.out.println("data received " + socket.event);
                }
            });*/


        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }


    }

}
