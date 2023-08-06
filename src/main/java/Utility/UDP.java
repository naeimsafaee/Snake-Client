package Utility;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDP extends DatagramSocket {

    InetAddress hostAddress;
    int port;

    public ReceiverInterface receiverInterface;

    //starting as a server
    public UDP(int port) throws IOException {
        super(port);
    }

    //starting as a client , connecting to hostAddress server
    public UDP(InetAddress hostAddress, int port) throws IOException {
        super();
        this.hostAddress = hostAddress;
        this.port = port;
    }

    public void listen() {

        Thread input = new Thread(() -> {

            byte[] receive = new byte[65535];

            DatagramPacket DpReceive;

            while (true) {

                DpReceive = new DatagramPacket(receive, receive.length , hostAddress , port);

                try {
                    receive(DpReceive);

                    String received = new String(DpReceive.getData(), 0, DpReceive.getLength());

//                    System.out.println(received);

                    SocketData received_data = ParseJsonData(received);
                    received_data.address = DpReceive.getAddress();
                    received_data.port = DpReceive.getPort();

                    receiverInterface.dataReceived(received_data);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }


                receive = new byte[65535];
            }
        });

        input.start();
    }

    public void send(SocketData socket) throws IOException {

        Gson gson = new Gson();
        String payload = gson.toJson(socket);

        byte[] buf = payload.getBytes(StandardCharsets.UTF_8);

        DatagramPacket send_packet = new DatagramPacket(buf, buf.length,
                hostAddress, port);

        send(send_packet);
    }

    private SocketData ParseJsonData(String received) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(received);
        return gson.fromJson(object, SocketData.class);
    }

    public interface ReceiverInterface {
        void dataReceived(SocketData socket);
    }

}
