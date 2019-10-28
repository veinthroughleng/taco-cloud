package veinthrough.tacocloud.messsaging.jms;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.NonNull;
import veinthrough.tacocloud.messsaging.MessagingPropertiesRetriever;
import veinthrough.tacocloud.model.Order;

import java.util.List;

@Configuration
@Profile("jms-listener")
@Slf4j
public class JmsListenerRegistrar implements JmsListenerConfigurer {
    private MessagingPropertiesRetriever retriever;
    private JmsListenerEndpointRegistrar registrar;
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
    public void configureJmsListeners(@NonNull JmsListenerEndpointRegistrar registrar) {
        this.registrar = registrar;
        register(getJmsListeners());
    }

    public void register(List<JmsListener> listeners) {
        listeners.forEach(
                listener -> {
                    SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
                    String destination = listener.getQueue();
                    endpoint.setDestination(destination);
                    endpoint.setMessageListener(listener);
                    registrar.registerEndpoint(endpoint);
                }
        );
    }

    private List<JmsListener> getJmsListeners() {
        return Lists.newArrayList(
                new JmsListener<>(
                        retriever.getQueue(Order.class), Order.class, converter)
        );
    }
}
