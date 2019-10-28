package veinthrough.tacocloud.messsaging;

public interface MessageSender<T> {
    void sendObject(T object);
}
