package veinthrough.taco.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "message")
@Component
@Data
@Profile("message")
public class MessagingProps {
    private Map<String, String> queues = new HashMap<>();
    private Map<String, String> routingKeys = new HashMap<>();
    private Map<String, String> sources = new HashMap<>();
    private Map<String, String> topics = new HashMap<>();
}
