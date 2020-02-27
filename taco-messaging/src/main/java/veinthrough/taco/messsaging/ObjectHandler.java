package veinthrough.taco.messsaging;

public interface ObjectHandler<T> {
    default void handle(T object) {
        new MessagePrinter().printMessage(object);
    }
}
