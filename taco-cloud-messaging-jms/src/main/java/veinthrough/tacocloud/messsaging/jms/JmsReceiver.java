package veinthrough.tacocloud.messsaging.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import veinthrough.tacocloud.messsaging.MessageReceiver;
import veinthrough.utils.MethodLog;

@Slf4j
public class JmsReceiver<T> implements MessageReceiver<T> {
    private JmsTemplate jms;
    private String queue;

    JmsReceiver(JmsTemplate jms, String queue) {
        this.jms = jms;
        this.queue = queue;
        //[DEBUG]
        log.info(MethodLog.inLog("constructor",
                "queue", queue));
    }

    @Override
    public T receiveObject() {
        return (T) jms.receiveAndConvert(queue);
    }
}
