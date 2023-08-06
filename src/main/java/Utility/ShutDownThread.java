package Utility;


public class ShutDownThread extends Thread{

    private Socket socket;

    public ShutDownThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();

        socket.emit("disconnect");
    }

}
