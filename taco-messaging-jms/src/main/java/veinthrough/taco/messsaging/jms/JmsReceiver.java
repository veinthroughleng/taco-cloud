package veinthrough.taco.messsaging.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import veinthrough.taco.messsaging.MessageReceiver;
import veinthrough.taco.utils.MethodLog;

@Slf4j
public class JmsReceiver<T> implements MessageReceiver<T> {
    private JmsTemplate jms;
    private String queue;

    JmsReceiver(JmsTemplate jms, String queue) {
        this.jms = jms;
        this.queue = queue;

        log.debug(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "queue", queue));
    }

    @Override
    public T receiveObject() {
        return (T) jms.receiveAndConvert(queue);
    }
}
