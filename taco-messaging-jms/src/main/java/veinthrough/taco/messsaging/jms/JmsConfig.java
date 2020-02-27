package veinthrough.taco.messsaging.jms;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import veinthrough.taco.model.Order;
import veinthrough.utils.MethodLog;

@Configuration
@Slf4j
public class JmsConfig {
    @Bean
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
