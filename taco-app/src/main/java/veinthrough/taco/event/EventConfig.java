package veinthrough.taco.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import veinthrough.taco.messsaging.MessageSender;
import veinthrough.taco.model.Order;
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
        log.info(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName()));
        return new OrderEventHandler(orderMessageSender);
    }
}
