package veinthrough.taco.messsaging.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import veinthrough.taco.messsaging.MessageReceiver;
import veinthrough.taco.utils.MethodLog;

@Slf4j
public class RabbitReceiver<T> implements MessageReceiver<T> {
    private RabbitTemplate rabbit;
    private String routingKey;

    RabbitReceiver(RabbitTemplate rabbit, String routingKey) {
        this.rabbit = rabbit;
        this.routingKey = routingKey;
        log.debug(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "routing key", routingKey));
    }

    @Override
    public T receiveObject() {
        return rabbit.receiveAndConvert(routingKey,
                new ParameterizedTypeReference<T>() {});
    }
}
