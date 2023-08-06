package Utility;

abstract public class Emitter {

    abstract public void emit(String event);

    abstract public void emit(String event, String payload);

    abstract public void emit(String event, Object object);

    abstract public void on(String event, SocketInterface socketInterface);
}
