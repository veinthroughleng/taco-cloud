package veinthrough.taco.messsaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import veinthrough.taco.property.MessagingProps;
import veinthrough.utils.MethodLog;

@Component
@Slf4j
public class MessagingPropertiesRetriever {
    private MessagingProps messagingProps;

    @Autowired
    public MessagingPropertiesRetriever(
            MessagingProps messagingProps) {
        //[DEBUG]
        log.info(MethodLog.inLog("constructor",
                "messagingProps", messagingProps.toString()));
        this.messagingProps = messagingProps;
    }


    public String getQueue(Class<?> clazz) {
        return getQueue(classToIdentifier(clazz));
    }
    private String getQueue(String identifier) {
        return messagingProps.getQueues().get(identifier);
    }

    public String getSource(Class<?> clazz) {
        return getSource(classToIdentifier(clazz));
    }
    private String getSource(String identifier) {
        return messagingProps.getSources().get(identifier);
    }

    public String getRoutingKey(Class<?> clazz) {
        return getRoutingKey(classToIdentifier(clazz));
    }
    private String getRoutingKey(String identifier) {
        return messagingProps.getRoutingKeys().get(identifier);
    }

    public String getTopic(Class<?> clazz) {
        return getTopic(classToIdentifier(clazz));
    }
    private String getTopic(String identifier) {
        return messagingProps.getTopics().get(identifier);
    }


    private String classToIdentifier(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }
}
