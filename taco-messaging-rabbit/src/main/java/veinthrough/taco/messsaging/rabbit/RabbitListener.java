package veinthrough.taco.messsaging.rabbit;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import veinthrough.taco.messsaging.ObjectHandler;
import veinthrough.taco.utils.MethodLog;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RabbitListener<T> implements MessageListener, ObjectHandler<T> {
    @Getter
    private List<String> queues = new ArrayList<>();
    @Getter
    private Class<T> type;
    private MessageConverter converter;

    RabbitListener(List<String> queues, Class<T> type,  MessageConverter converter) {
        this.type = type;
        this.queues.addAll(queues);
        this.converter = converter;
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "queues", queues.toString(),
                "type", type.getName(),
                "converter", converter.toString()));
    }

    @Override
    public void onMessage(Message message) {
        T object = (T)converter.fromMessage(message);
        log.debug(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "message", message.toString(),
                "properties", message.getMessageProperties().getHeaders().get("X_SOURCE").toString(),
                "body", object.toString()));
        handle(object);

    }
}
