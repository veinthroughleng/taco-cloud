package veinthrough.tacocloud.data.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import veinthrough.tacocloud.data.event.OrderEventHandler;
import veinthrough.tacocloud.messsaging.MessageSender;
import veinthrough.tacocloud.model.Order;
import veinthrough.utils.MethodLog;

@Configuration
@Slf4j
public class EventConfig {
    private MessageSender<Order> orderMessageSender;

    @Autowired
    public void setOrderMessageSender(MessageSender<Order> orderMessageSender) {
        this.orderMessageSender = orderMessageSender;
    }

    @Bean
    OrderEventHandler getOrderEventHandler() {
        log.info(MethodLog.inLog(
                Thread.currentThread().getStackTrace()[1].getMethodName()));
        return new OrderEventHandler(orderMessageSender);
    }
}
