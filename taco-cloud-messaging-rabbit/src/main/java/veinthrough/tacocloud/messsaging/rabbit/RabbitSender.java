package veinthrough.tacocloud.messsaging.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import veinthrough.tacocloud.messsaging.MessageSender;
import veinthrough.utils.MethodLog;

import static veinthrough.utils.Constants.DEFAULT_DIRECT_EXCHANGE;

@Slf4j
public class RabbitSender<T> implements MessageSender<T> {
    private RabbitTemplate rabbit;
    private String routingKey;
    private String source;

    RabbitSender(RabbitTemplate rabbit, String routingKey, String source) {
        this.rabbit = rabbit;
        this.routingKey = routingKey;
        this.source = source;
        //[DEBUG]
        log.info(MethodLog.inLog("constructor",
                "routingKey", routingKey,
                "source", source));
    }


    @Override
    public void sendObject(T object) {
        //routing key
        rabbit.convertAndSend(DEFAULT_DIRECT_EXCHANGE, routingKey, object,
                message -> {
                    message.getMessageProperties().setHeader(
                            "X_SOURCE", source);
                    return message;});
    }
}
