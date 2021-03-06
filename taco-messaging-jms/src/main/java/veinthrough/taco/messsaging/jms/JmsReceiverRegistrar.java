package veinthrough.taco.messsaging.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import veinthrough.taco.messsaging.MessagingPropertiesRetriever;
import veinthrough.taco.model.Order;

@Configuration
@Slf4j
@Profile("jms-receiver")
public class JmsReceiverRegistrar {
    private JmsTemplate jms;
    private MessagingPropertiesRetriever retriever;

    @Autowired
    public void setJms(JmsTemplate jms) {
        this.jms = jms;
    }

    @Autowired
    public void setRetriever(MessagingPropertiesRetriever retriever) {
        this.retriever = retriever;
    }

    @Bean
    @Profile("jms-receiver")
    public JmsReceiver<Order> getJmsJmsReceiver() {
        return new JmsReceiver<>(jms,
                retriever.getQueue(Order.class));
    }
}
