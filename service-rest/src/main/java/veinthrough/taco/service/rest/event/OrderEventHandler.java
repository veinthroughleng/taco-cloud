package veinthrough.taco.service.rest.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import veinthrough.taco.messsaging.MessageSender;
import veinthrough.taco.model.Order;
import veinthrough.taco.utils.MethodLog;

@RepositoryEventHandler(Order.class)
@Slf4j
public class OrderEventHandler {
    private MessageSender<Order> orderMessageSender;

    OrderEventHandler(MessageSender<Order> orderMessageSender) {
        this.orderMessageSender = orderMessageSender;
    }

    @HandleAfterCreate
    public void handleOrderCreated(Order order) {
        log.debug(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "order", order.toString()));
        orderMessageSender.sendObject(order);
    }
}
