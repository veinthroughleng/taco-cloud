package veinthrough.tacocloud.messsaging.jms;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.support.converter.MessageConverter;
import veinthrough.tacocloud.messsaging.ObjectHandler;
import veinthrough.utils.MethodLog;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Slf4j
public class JmsListener<T> implements MessageListener, ObjectHandler<T> {
    @Getter
    private String queue;
    private Class<T> type;
    private MessageConverter converter;

    JmsListener(String queue, Class<T> type, MessageConverter converter) {
        this.queue = queue;
        this.type = type;
        this.converter = converter;
        log.info(MethodLog.inLog("constructor",
                "queue", queue,
                "type", type.getName(),
                "converter", converter.toString()));
    }

    @Override
    public void onMessage(Message message) {
        //[DEBUG]
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        try {
            log.info(MethodLog.inLog(method,
                    "message", message.toString(),
                    "property", message.getStringProperty("X_SOURCE"),
                    "body", message.getBody(type).toString()));
            T object = (T)converter.fromMessage(message);
            handle(object);

        } catch (JMSException e) {
            log.info(e.getMessage());
        }
    }
}
