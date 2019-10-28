package veinthrough.tacocloud.messsaging.rabbit;

import com.google.common.collect.Lists;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import veinthrough.tacocloud.messsaging.MessagingPropertiesRetriever;
import veinthrough.tacocloud.model.Order;

import java.util.List;

@Profile("rabbit-listener")
@Configuration
public class RabbitListenerRegistrar implements RabbitListenerConfigurer {
    private RabbitListenerEndpointRegistrar registrar;
    private MessagingPropertiesRetriever retriever;
    private MessageConverter converter;

    @Autowired
    public void setRetriever(MessagingPropertiesRetriever retriever) {
        this.retriever = retriever;
    }

    @Autowired
    public void setConverter(MessageConverter converter) {
        this.converter = converter;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        this.registrar = registrar;
        register(getRabbitListeners());
    }

    private void register(List<RabbitListener<?>> listeners) {
        listeners.forEach(
                listener -> {
                    List<String> queues = listener.getQueues();
                    SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
                    endpoint.setId(listener.getType().getSimpleName());
                    endpoint.setQueueNames(queues.toArray(new String[0]));
                    endpoint.setMessageListener(listener);
                    registrar.registerEndpoint(endpoint);
                }
        );
    }

    private List<RabbitListener<?>> getRabbitListeners() {
        return Lists.newArrayList(
                new RabbitListener<>(
                        Lists.newArrayList(retriever.getQueue(Order.class)),
                        Order.class,
                        converter)
        );

    }
}
