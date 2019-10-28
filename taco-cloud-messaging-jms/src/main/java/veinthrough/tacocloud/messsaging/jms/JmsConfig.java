package veinthrough.tacocloud.messsaging.jms;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import veinthrough.tacocloud.model.Order;
import veinthrough.utils.MethodLog;

@Configuration
@Slf4j
@Profile({"jms-sender", "jms-receiver", "jms-listener"})
public class JmsConfig {
    @Bean
    @Profile({"jms-sender", "jms-receiver", "jms-listener"})
    public MappingJackson2MessageConverter jmsMessageConverter() {
        //[DEBUG]
        log.info(MethodLog.inLog(Thread.currentThread().getStackTrace()[1].getMethodName()));
        MappingJackson2MessageConverter messageConverter =
                new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");
        messageConverter.setTypeIdMappings(
                ImmutableMap.<String, Class<?>>builder()
                        .put("order", Order.class)
                        .build());

        return messageConverter;
    }
}
