package veinthrough.taco.messsaging.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import veinthrough.taco.messsaging.MessagingPropertiesRetriever;
import veinthrough.taco.model.Order;
import veinthrough.taco.utils.MethodLog;

@Slf4j
@Component
@Profile("rabbit-sender")
public class RabbitSenderRegistrar {
    private RabbitTemplate rabbit;
    private MessagingPropertiesRetriever properties;

    @Autowired
    public void setRabbit(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Autowired
    public void setProperties(MessagingPropertiesRetriever properties) {
        this.properties = properties;
    }

    @Bean
    @Profile("rabbit-sender")
    public RabbitSender<Order> getRabbitSender() {
        //[DEBUG]
        log.info(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName()));
        return new RabbitSender<>(rabbit,
                properties.getRoutingKey(Order.class),
                properties.getSource(Order.class));
    }
}
