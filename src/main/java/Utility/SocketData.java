package Utility;

import com.google.gson.Gson;
import java.net.InetAddress;

public class SocketData extends Emitter{

    public int socketId;
    public String event;
    public String payload;
    public String type;

    public transient int port;

    public transient InetAddress address;

    SocketData(int socketId, String event, String payload, String type) {
        this.socketId = socketId;
        this.event = event;
        this.payload = payload;
        this.type = type;
    }

    public void emit(String event) {
        emit(event , "");
    }

    public void emit(String event, String payload) {
        Socket.getInstance().emit(new SocketData(
                socketId, event, payload, "client_to_server"
        ));
    }

    public void emit(String event, Object object) {
        Gson gson = new Gson();

        emit(event , gson.toJson(object));
    }

    public void on(String event, SocketInterface socketInterface) {
        Socket.getInstance().on(event, socketInterface , Tools.getGeneric(socketInterface));
    }


}
