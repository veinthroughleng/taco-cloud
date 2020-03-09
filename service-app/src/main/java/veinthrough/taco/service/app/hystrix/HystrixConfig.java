package veinthrough.taco.service.app.hystrix;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import veinthrough.taco.utils.MethodLog;

@Configuration
@Slf4j
public class HystrixConfig {
    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet(){
        log.debug(MethodLog.log(Thread.currentThread().getStackTrace()[1].getMethodName()));

        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<>(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");

        return registrationBean;
    }
}
