package veinthrough.taco.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "security.oauth2.client")
@Component
@Data
public class OAuth2ClientProps {
    private String clientId;
    private String clientSecret;
    private String accessTokenUri;
    private List<String> grantTypes;
    private List<String> authorities;
    private List<String> scopes;
    private Long accessTokenValiditySeconds;
//    private String clientAuthenticationScheme;
}
