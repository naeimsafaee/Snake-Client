package Utility;

public class HeartBeat extends Thread{


    private Socket socket;

    private long delayTime = 3000;

    public HeartBeat(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();

        while (true){

            if (System.currentTimeMillis() - socket.lastHeartBeat > 3000)
                socket.setConnected(false);

            try {
                sleep(delayTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            delayTime = 1000;

            if (socket.isConnected()) {


            } else {
                System.out.println("can't connect to server. trying again...");

                if (socket.socketId == -1)
                    socket.emit("connection");
            }

        }


    }
}
