package veinthrough.taco.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "taco")
@Component
@Data
public class PageSizeProps {
    private Map<String, Integer> pageSizes = new HashMap<>();
}
