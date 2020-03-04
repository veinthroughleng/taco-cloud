package veinthrough.taco.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import static veinthrough.utils.Constants.DEFAULT_TOKEN_EXPIRATION_MINUTES;

@ConfigurationProperties(prefix = "security")
@Component
@Data
public class SecurityProps {
    private String signingKey = "";
    private Long tokenExpirationMinutes = DEFAULT_TOKEN_EXPIRATION_MINUTES;
    private boolean h2ConsoleSecured = true;
}
