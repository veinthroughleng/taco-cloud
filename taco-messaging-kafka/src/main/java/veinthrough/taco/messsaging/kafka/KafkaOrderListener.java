package veinthrough.taco.messsaging.kafka;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;
import veinthrough.taco.model.Order;
import veinthrough.taco.utils.DateFormat;
import veinthrough.taco.utils.MethodLog;

@Slf4j
@Component
@Profile("kafka-listener")
public class KafkaOrderListener implements MessageListener<Integer, Order> {
    @Getter
    private String topic;

    @Autowired
    public void setRetriever() {
        this.topic = "#{taco.message.topic.order}";

        log.debug(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "topic", topic));
    }

    @Override
    @KafkaListener(topics = "#{taco.message.topic.order}")
    public void onMessage(ConsumerRecord<Integer, Order> record) {
        log.debug(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "topic", record.topic(),
                "key", record.key() == null ? null : String.valueOf(record.key()),
                "partition", String.valueOf(record.partition()),
                "timestamp", DateFormat.formatDate(record.timestamp())),
                "value", record.value().toString());
    }
}
