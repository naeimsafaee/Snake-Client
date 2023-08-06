package Utility;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.util.ArrayList;

public class Socket extends Emitter implements UDP.ReceiverInterface{

    int socketId;
    private boolean isConnected;

    long lastHeartBeat;

    UDP udp;
    ArrayList<Event> events;
    ArrayList<SocketData> sockets;

    private static Socket instance;


    public Socket(String ip, int port) throws IOException {
        init(InetAddress.getByName(ip), port);
    }

    public Socket() throws IOException {
        System.out.println(InetAddress.getLocalHost().getHostAddress() + "");
        init(InetAddress.getLocalHost(), 1234);

        Runtime.getRuntime().addShutdownHook(new ShutDownThread(this));
    }

    private void init(InetAddress hostAddress, int serverPort) throws IOException {
        instance = this;

        events = new ArrayList<>();
        sockets = new ArrayList<>();

        udp = new UDP(hostAddress, serverPort);
        udp.receiverInterface = this;
        udp.listen();


        on("connection", new SocketDataInterface() {
            @Override
            public void data(SocketData socket) {
                System.out.println("Connected to server successfully");
                socketId = socket.socketId;

                setConnected(true);
            }
        });

        on("heartbeat", new SocketDataInterface() {
            @Override
            public void data(SocketData socket) {
                socket.emit("heartbeat");

                setConnected(true);
            }
        });

        emit("connection");

        new HeartBeat(this).start();
    }


    void on(String event, SocketInterface socketInterface, Type type) {
        events.add(new Event(event, socketInterface, type));
    }

    void emit(SocketData data) {
        try {
            udp.send(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void on(String event, SocketInterface socketInterface) {
        on(event, socketInterface, Tools.getGeneric(socketInterface));
    }

    public void emit(String event, String payload) {
        emit(new SocketData(
                socketId, event, payload, "client_to_server"
        ));
    }

    public void emit(String event, Object object) {
        Gson gson = new Gson();

        emit(new SocketData(
                socketId, event, gson.toJson(object), "client_to_server"
        ));
    }

    public void emit(String event) {
        emit(event, "");
    }

    @Override
    public void dataReceived(SocketData data) {

        /*if(data.socketId == socketId){
            System.out.println("event: " + data.event + " true ");
        } else {
            System.out.println("event: " + data.event + " false ");
        }*/

        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);

            if ((event.name.equals(data.event)) ||
                    (event.name.equals("data") &&
                            !data.event.equals("heartbeat"))) {

                if (event.socketInterface instanceof SocketObjectInterface<?>) {
                    ((SocketObjectInterface<Object>) event.socketInterface).data(Tools.ParseJsonData(data.payload, event.type));
                } else if(event.socketInterface instanceof SocketObjectAndDataInterface<?>){
                    ((SocketObjectAndDataInterface<Object>) event.socketInterface).data(Tools.ParseJsonData(data.payload, event.type) , data);
                } else {
                    ((SocketDataInterface) event.socketInterface).data(data);
                }

            }
        }
    }


    public static synchronized Socket getInstance() {
        return instance;
    }

    public boolean isConnected() {
        return isConnected;
    }

    void setConnected(boolean connected) {
        isConnected = connected;

        if (isConnected) {
            lastHeartBeat = System.currentTimeMillis();
        } else {
            socketId = -1;
        }
    }

    public int getSocketId(){
        return socketId;
    }

}
