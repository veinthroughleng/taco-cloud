package veinthrough.taco.messsaging.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import veinthrough.taco.messsaging.MessageSender;
import veinthrough.taco.utils.MethodLog;

@Slf4j
public class JmsSender<T> implements MessageSender<T> {
    private JmsTemplate jms;
    private String queue;
    private String source;

    JmsSender(JmsTemplate jms, String queue, String source) {
        this.jms = jms;
        this.queue = queue;
        this.source = source;
        //[DEBUG]
        log.info(MethodLog.log("constructor",
                "queue", queue,
                "source", source));
    }


    @Override
    public void sendObject(T object) {
        jms.convertAndSend(queue, object,
                message -> {
                    message.setStringProperty("X_SOURCE", source);
                    return message;});
    }
}
