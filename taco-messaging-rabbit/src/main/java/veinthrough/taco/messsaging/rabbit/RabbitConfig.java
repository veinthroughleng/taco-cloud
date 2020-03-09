package veinthrough.taco.messsaging.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import veinthrough.taco.utils.MethodLog;

@Configuration
@Slf4j
public class RabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter rabbitMessageConverter() {
        //[DEBUG]
        log.info(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName()));
        return new Jackson2JsonMessageConverter();
    }
}
