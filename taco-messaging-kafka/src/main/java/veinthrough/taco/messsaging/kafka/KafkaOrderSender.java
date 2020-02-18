package veinthrough.taco.messsaging.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import veinthrough.taco.messsaging.MessageSender;
import veinthrough.taco.model.Order;
import veinthrough.utils.MethodLog;

@Slf4j
@Service
@Profile("kafka-sender")
public class KafkaOrderSender implements MessageSender<Order> {
    // Able to deal with domain directly, no need of converter
    private KafkaTemplate<String, Order> kafkaTemplate;
    private String topic;

    @Autowired
    public KafkaOrderSender(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = "#{taco.message.topic.order}";
        //[DEBUG]
        log.info(MethodLog.inLog("constructor",
                "topic", topic));
    }

    @Override
    public void sendObject(Order object) {
        kafkaTemplate.send(topic, object);
    }
}
