package veinthrough.taco.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "security")
@Component
@Data
public class SecurityProps {
    private boolean h2ConsoleSecured = true;
    private boolean actuatorSecured = true;
}
