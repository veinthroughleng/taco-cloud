package veinthrough.tacocloud.messsaging.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import veinthrough.tacocloud.messsaging.MessagingPropertiesRetriever;
import veinthrough.tacocloud.model.Order;
import veinthrough.utils.MethodLog;

@Slf4j
@Component
@Profile("rabbit-receiver")
public class RabbitReceiverRegistrar {
    private RabbitTemplate rabbit;
    private MessagingPropertiesRetriever retriever;

    @Autowired
    public void setRabbit(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Autowired
    public void setRetriever(MessagingPropertiesRetriever retriever) {
        this.retriever = retriever;
    }

    @Bean
    @Profile("rabbit-receiver")
    public RabbitReceiver<Order> getRabbitReceiver() {
        //[DEBUG]
        log.info(MethodLog.inLog(Thread.currentThread().getStackTrace()[1].getMethodName()));
        return new RabbitReceiver<>(rabbit,
                retriever.getRoutingKey(Order.class));
    }
}
