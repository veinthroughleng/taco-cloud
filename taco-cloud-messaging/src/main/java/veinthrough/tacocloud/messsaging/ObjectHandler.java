package veinthrough.tacocloud.messsaging;

public interface ObjectHandler<T> {
    default void handle(T object) {
        MessagePrinter.printMessage(object);
    }
}
