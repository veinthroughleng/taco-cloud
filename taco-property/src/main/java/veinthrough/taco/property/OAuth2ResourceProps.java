package veinthrough.taco.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "security.oauth2.resource")
@Component
@Data
public class OAuth2ResourceProps {
    private String resourceName;
}
