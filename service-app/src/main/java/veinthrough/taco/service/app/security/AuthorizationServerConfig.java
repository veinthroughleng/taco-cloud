package veinthrough.taco.service.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import veinthrough.taco.property.OAuth2ClientProps;
import veinthrough.taco.property.OAuth2ResourceProps;
import veinthrough.taco.utils.MethodLog;

@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private OAuth2ClientProps oAuth2ClientProps;
    private OAuth2ResourceProps oAuth2ResourceProps;

    @Autowired
    public AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                     PasswordEncoder passwordEncoder,
                                     OAuth2ClientProps oAuth2ClientProps,
                                     OAuth2ResourceProps oAuth2ResourceProps) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.oAuth2ClientProps = oAuth2ClientProps;
        this.oAuth2ResourceProps = oAuth2ResourceProps;
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "oAuth2 client props", oAuth2ClientProps.toString(),
                "oAuth2 resource props", oAuth2ResourceProps.toString()));
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // in memory
        clients.inMemory().withClient(oAuth2ClientProps.getClientId())
                .resourceIds(oAuth2ResourceProps.getResourceName())
                .authorizedGrantTypes(oAuth2ClientProps.getGrantTypes().toArray(new String[0]))
                .scopes(oAuth2ClientProps.getScopes().toArray(new String[0]))
                .authorities(oAuth2ClientProps.getAuthorities().toArray(new String[0]))
                .secret(passwordEncoder.encode(oAuth2ClientProps.getClientSecret()))
                .accessTokenValiditySeconds(oAuth2ClientProps.getAccessTokenValiditySeconds().intValue())
                .autoApprove(true);

        // oauth_client_details db table
//        clients.jdbc(this.dataSource)
//                .withClient(oAuth2ClientProps.getClientId())
//                .resourceIds(oAuth2ResourceProps.getResourceName())
//                .authorizedGrantTypes(oAuth2ClientProps.getGrantTypes().toArray(new String[0]))
//                .scopes(oAuth2ClientProps.getScopes().toArray(new String[0]))
//                .authorities(oAuth2ClientProps.getAuthorities().toArray(new String[0]))
//                .secret(passwordEncoder.encode(oAuth2ClientProps.getClientSecret()))
//                .accessTokenValiditySeconds(oAuth2ClientProps.getAccessTokenValiditySeconds().intValue())
//                .autoApprove(true);

    }

    @Override
    // only "password" authorizedGrantTypes need a authenticationManager
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
//                .tokenStore(tokenStore);
    }

    @Override
    // allow form authentication
    // allow /oauth/check_token/ endpoint, default denyAll()
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()");
    }
}