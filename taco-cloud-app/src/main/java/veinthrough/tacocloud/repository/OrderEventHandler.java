package veinthrough.tacocloud.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import veinthrough.tacocloud.messsaging.MessageSender;
import veinthrough.tacocloud.model.Order;
import veinthrough.utils.MethodLog;

@RepositoryEventHandler(Order.class)
@Slf4j
public class OrderEventHandler {
    private MessageSender<Order> orderMessageSender;

    public OrderEventHandler(MessageSender<Order> orderMessageSender) {
        this.orderMessageSender = orderMessageSender;
    }

    @HandleAfterCreate
    public void handleOrderCreated(Order order) {
        log.info(MethodLog.inLog(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "order", order.toString()));
        orderMessageSender.sendObject(order);
    }
}
