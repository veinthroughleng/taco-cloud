package veinthrough.taco.messsaging;

public interface MessageSender<T> {
    void sendObject(T object);
}
