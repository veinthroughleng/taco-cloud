package veinthrough.taco.security;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import veinthrough.taco.property.SecurityProps;
import veinthrough.taco.utils.MethodLog;

import java.util.Arrays;
import java.util.List;

import static veinthrough.taco.utils.Constants.*;

@Component
@Slf4j
public class SecurityUtils {
    private SecurityProps securityProps;

    @Autowired
    public SecurityUtils(SecurityProps securityProps) {
        this.securityProps = securityProps;
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "security props", securityProps.toString()));
    }

    public String[] getWhiteList() {
        List<String> whiteList = Lists.newArrayList(URL_LOGIN);
        whiteList.addAll(Lists.newArrayList(URL_SWAGGER));

        // data should be handled in resource server
//        whiteList.addAll(Lists.newArrayList(URL_DATA_INGREDIENTS));
//        whiteList.addAll(Lists.newArrayList(URL_DATA_TACOS));

        // debug purpose: don't secure h2-console
        if (!securityProps.isH2ConsoleSecured()) {
            whiteList.addAll(Lists.newArrayList(URL_H2_CONSOLE));
        }

        // debug purpose: don't secure actuator endpoints
        if (!securityProps.isActuatorSecured()) {
            whiteList.addAll(Lists.newArrayList(URL_ACTUATOR));
        }

        String[] whiteArray = whiteList.toArray(new String[0]);
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(),
                "isH2ConsoleSecured", String.valueOf(securityProps.isH2ConsoleSecured()),
                "URL WHITELIST", Arrays.toString(whiteArray)));
        return whiteArray;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
